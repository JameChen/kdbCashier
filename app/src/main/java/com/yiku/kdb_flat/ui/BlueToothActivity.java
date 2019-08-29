package com.yiku.kdb_flat.ui;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gprinter.aidl.GpService;
import com.gprinter.command.GpCom;
import com.gprinter.io.GpDevice;
import com.gprinter.io.PortParameters;
import com.gprinter.save.PortParamDataBase;
import com.gprinter.service.GpPrintService;
import com.yiku.kdb_flat.BWApplication;
import com.yiku.kdb_flat.R;
import com.yiku.kdb_flat.custom_controls.ViewHub;
import com.yiku.kdb_flat.ui.adapter.BlueToolAdapter;
import com.yiku.kdb_flat.ui.base.BaseAppCompatActivity;
import com.yiku.kdb_flat.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

import static com.yiku.kdb_flat.ui.BluetoothDeviceList.EXTRA_DEVICE_ADDRESS;
import static com.yiku.kdb_flat.ui.BluetoothDeviceList.EXTRA_DEVICE_NAME;

/**
 * 我的蓝牙
 *
 * @author James Chen
 * @create time in 2017/10/27 13:45
 */
public class BlueToothActivity extends BaseAppCompatActivity implements View.OnClickListener {
    public static String DEBUG_TAG = "BlueToothActivity";
    BlueToothActivity Vthis;
    Button titlebar_btnLeft;
    TextView titlebar_tvTitle, titlebar_btnRight;
    private GpService mGpService;
    private PrinterServiceConnection conn = null;
    PortParamDataBase database;
    ListView listView;
    BlueToolAdapter adapter;
    List<PortParameters> dataList = new ArrayList<>();
    private static final int INTENT_PORT_SETTINGS = 0;
    private int mPrinterId = 0;
    private int InsertPrinterId = 0;
    public Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case BlueToolAdapter.MESSAGE_CONNECT:
                    connectOrDisConnectToDevice(message.arg1);
            }
            return false;
        }
    });

    void connectOrDisConnectToDevice(int PrinterId) {
        mPrinterId = PrinterId;
        int rel = 0;
        PortParameters mPortParam = dataList.get(mPrinterId);
        if (mPortParam.getPortOpenState() == false) {
            if (CheckPortParamters(mPortParam)) {
                try {
                    mGpService.closePort(mPrinterId);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                switch (mPortParam.getPortType()) {


                    case PortParameters.BLUETOOTH:
                        try {
                            rel = mGpService.openPort(PrinterId, mPortParam.getPortType(),
                                    mPortParam.getBluetoothAddr(), 0);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                GpCom.ERROR_CODE r = GpCom.ERROR_CODE.values()[rel];
                Log.e(DEBUG_TAG, "result :" + String.valueOf(r));
                if (r != GpCom.ERROR_CODE.SUCCESS) {
                    if (r == GpCom.ERROR_CODE.DEVICE_ALREADY_OPEN) {
                        BWApplication.getInstance().PrinterId = mPrinterId;
                        mPortParam.setPortOpenState(true);
                        adapter.notifyDataSetChanged();
                    } else {
                        if (GpCom.getErrorText(r).equals("Please open bluetooth")) {
                            ViewHub.showShortToast(this, "请打开蓝牙");
                        } else if (GpCom.getErrorText(r).equals("Invalid bluetooth address")) {
                            ViewHub.showShortToast(this, "蓝牙端口地址错误");
                        } else {
                            ViewHub.showShortToast(this, GpCom.getErrorText(r));
                        }

                    }
                }
            } else {
                ViewHub.showShortToast(this, "端口参数错误！");
            }
        } else {
            Log.d(DEBUG_TAG, "DisconnectToDevice ");
            setProgressBarIndeterminateVisibility(true);
            mPortParam.setPortOpenState(false);
            adapter.notifyDataSetChanged();
            try {
                mGpService.closePort(PrinterId);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    class PrinterServiceConnection implements ServiceConnection {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(DEBUG_TAG, "onServiceDisconnected() called");
            mGpService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mGpService = GpService.Stub.asInterface(service);
            try {
                if (!ListUtils.isEmpty(dataList))
                    if (mGpService.getPrinterConnectStatus(BWApplication.getInstance().PrinterId) == GpDevice.STATE_CONNECTED) {
                        dataList.get(BWApplication.getInstance().PrinterId).setPortOpenState(true);
                        dataList.get(BWApplication.getInstance().PrinterId).setIpAddr(getString(R.string.cut));
                        if (adapter != null)
                            adapter.notifyDataSetChanged();
                    }

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void connection() {
        conn = new PrinterServiceConnection();
        Log.i(DEBUG_TAG, "connection");
        Intent intent = new Intent(this, GpPrintService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE); // bindService
    }

    private BroadcastReceiver PrinterStatusBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (GpCom.ACTION_CONNECT_STATUS.equals(intent.getAction())) {
                int type = intent.getIntExtra(GpPrintService.CONNECT_STATUS, 0);
                int id = intent.getIntExtra(GpPrintService.PRINTER_ID, 0);
                Log.d(DEBUG_TAG, "connect status " + type);
                if (type == GpDevice.STATE_CONNECTING) {
                    setProgressBarIndeterminateVisibility(true);
                    // SetLinkButtonEnable(BlueToolAdapter.DISABLE);
                    dataList.get(mPrinterId).setPortOpenState(false);
//                    Map<String, Object> map;
//                    map = mList.get(id);
//                    map.put(BlueToolAdapter.STATUS, getString(R.string.connecting));
//                    mList.set(id, map);
                    dataList.get(mPrinterId).setIpAddr(getString(R.string.connecting));
                    adapter.notifyDataSetChanged();

                } else if (type == GpDevice.STATE_NONE) {
                    setProgressBarIndeterminateVisibility(false);
                    // SetLinkButtonEnable(BlueToolAdapter.ENABLE);
                    dataList.get(mPrinterId).setPortOpenState(false);
//                    Map<String, Object> map;
//                    map = mList.get(id);
//                    map.put(BlueToolAdapter.STATUS, getString(R.string.connect));
//                    mList.set(id, map);
                    dataList.get(mPrinterId).setIpAddr(getString(R.string.connect));
                    adapter.notifyDataSetChanged();
                } else if (type == GpDevice.STATE_VALID_PRINTER) {
                    BWApplication.getInstance().PrinterId = mPrinterId;
                    dataList.get(mPrinterId).setPortOpenState(true);
                    dataList.get(mPrinterId).setIpAddr(getString(R.string.cut));
                    adapter.notifyDataSetChanged();
                } else if (type == GpDevice.STATE_INVALID_PRINTER) {
                    setProgressBarIndeterminateVisibility(false);
                    ViewHub.showShortToast(Vthis, "请查看打印机");
//                    SetLinkButtonEnable(BlueToolAdapter.ENABLE);
//                    messageBox("Please use Gprinter!");
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_blue_tooth);

        try {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSION_REQUEST_CONSTANT);
            }
            Vthis = this;
            database = new PortParamDataBase(this);
            registerBroadcast();
            connection();
            titlebar_btnLeft = (Button) findViewById(R.id.titlebar_btnLeft);
            titlebar_btnLeft.setOnClickListener(this);
            titlebar_btnLeft.setVisibility(View.VISIBLE);
            titlebar_tvTitle = (TextView) findViewById(R.id.titlebar_tvTitle);
            titlebar_btnRight = (TextView) findViewById(R.id.titlebar_btnRight);
            titlebar_btnRight.setVisibility(View.VISIBLE);
            titlebar_btnRight.setText("添加蓝牙");
            titlebar_btnRight.setOnClickListener(this);
            titlebar_tvTitle.setText("我的蓝牙");

            listView = (ListView) findViewById(R.id.listview);
            adapter = new BlueToolAdapter(this, mHandler);
            List<PortParameters> list = database.queryAllPortParamDataBase();
            if (list != null) {
                if (list.size() > 0) {
                    InsertPrinterId = list.size();
                }
            }
            dataList = list;
            adapter.setListItems(dataList);
            listView.setAdapter(adapter);
            getEclipseVersionInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        BlueToolSave save=new BlueToolSave(this,"bb");
//        List<BlueToolBean> list=new ArrayList<>();
//        BlueToolBean bean=new BlueToolBean();
//        bean.setName("1");
//        PortParameters pp=new PortParameters();
//        pp.setBluetoothAddr("1333");
//        pp.setIpAddr("dasdsa");
//        pp.setPortType(13212);
//        pp.setPortNumber(3232);
//        bean.setParameters(pp);
//        list.add(bean);
//        list.add(bean);
//        list.add(bean);
//        list.add(bean);
//        list.add(bean);
//        list.add(bean);
//        save.setDataList("a",list);
//        List<BlueToolBean> list2= save.getDataList("a");
//        Log.d("yu",list2.toString());
    }

    public void getEclipseVersionInfo() throws Exception {
        //int versioncode;
        String versionname;
        PackageManager pm = getPackageManager();
        PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
      //  versioncode = packageInfo.versionCode;
        versionname = packageInfo.versionName;
        if (titlebar_tvTitle != null)
            titlebar_tvTitle.setText("我的蓝牙\n版本名：" + versionname);

    }

    public static final int REQUEST_ENABLE_BT = 2;
    public static final int REQUEST_CONNECT_DEVICE = 3;
    public static final int REQUEST_USB_DEVICE = 4;
    public static final int MY_PERMISSION_REQUEST_CONSTANT = 5;

    public void getBluetoothDevice() {
        // Get local Bluetooth adapter
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter
                .getDefaultAdapter();
        // If the adapter is null, then Bluetooth is not supported
        if (bluetoothAdapter == null) {
            ViewHub.showShortToast(this, "手机不支持蓝牙");
        } else {
            if (!bluetoothAdapter.isEnabled()) {
                Intent enableIntent = new Intent(
                        BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableIntent,
                        REQUEST_ENABLE_BT);
            } else {
                Intent intent = new Intent(Vthis,
                        BluetoothDeviceList.class);
                startActivityForResult(intent,
                        REQUEST_CONNECT_DEVICE);
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CONSTANT: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //permission granted!
                }
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
//    Log.d(DEBUG_TAG, "requestCode" + requestCode + "=>" + "resultCode"
//        + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                Intent intent = new Intent(Vthis,
                        BluetoothDeviceList.class);
                startActivityForResult(intent,
                        REQUEST_CONNECT_DEVICE);
            } else {
                Toast.makeText(this, R.string.bluetooth_is_not_enabled,
                        Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_CONNECT_DEVICE) {
            // When DeviceListActivity returns with a device to connect
            if (resultCode == Activity.RESULT_OK) {
                // Get the device MAC address
                String address = data.getExtras().getString(
                        EXTRA_DEVICE_ADDRESS);
                String name = data.getExtras().getString(
                        EXTRA_DEVICE_NAME);
                //设备连接上了
                PortParameters mPortParam = new PortParameters();
                mPortParam.setPortType(PortParameters.BLUETOOTH);
                mPortParam.setBluetoothAddr(address);
                mPortParam.setUsbDeviceName(name);
                mPortParam.setIpAddr(getString(R.string.connect));
                if (CheckPortParamters(mPortParam)) {
                    if (isHasPort(address)) {
                        ViewHub.showShortToast(this, "列表已经有该蓝牙，请点击连接");
                    } else {
                        long id = database.insertPortParam(InsertPrinterId, mPortParam);
                        if (id > 0) {
                            dataList.add(mPortParam);
                            adapter.notifyDataSetChanged();
                            InsertPrinterId++;
                        }

                    }

                }
                // fill in some parameters
//                tvPortInfo.setVisibility(View.VISIBLE);
//                tvPortInfo.setText(getString(R.string.bluetooth_address) + address);
//                btConnect.setVisibility(View.VISIBLE);
//                mPortParam.setBluetoothAddr(address);
            }
        }
    }

    private boolean isHasPort(String name) {
        boolean flag = false;
        if (!ListUtils.isEmpty(dataList)) {
            for (PortParameters bean : dataList) {
                if (bean.getBluetoothAddr().equals(name)) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    private void registerBroadcast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(GpCom.ACTION_CONNECT_STATUS);
        this.registerReceiver(PrinterStatusBroadcastReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        Log.e(DEBUG_TAG, "onDestroy ");
        super.onDestroy();
        if (PrinterStatusBroadcastReceiver != null)
            this.unregisterReceiver(PrinterStatusBroadcastReceiver);
        if (conn != null) {
            unbindService(conn); // unBindService
        }
    }

    Boolean CheckPortParamters(PortParameters param) {
        boolean rel = false;
        int type = param.getPortType();
        if (type == PortParameters.BLUETOOTH) {
            if (!param.getBluetoothAddr().equals("")) {
                rel = true;
            }
        } else if (type == PortParameters.ETHERNET) {
            if ((!param.getIpAddr().equals("")) && (param.getPortNumber() != 0)) {
                rel = true;
            }
        } else if (type == PortParameters.USB) {
            if (!param.getUsbDeviceName().equals("")) {
                rel = true;
            }
        }
        return rel;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.titlebar_btnLeft:
                finish();
                break;
            case R.id.titlebar_btnRight:
                getBluetoothDevice();
//                Intent intent = new Intent(BlueToothActivity.this, PortConfigurationActivity.class);
//                startActivityForResult(intent, INTENT_PORT_SETTINGS);
                break;
        }
    }
}
