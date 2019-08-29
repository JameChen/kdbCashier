/*     */
package com.gprinter.service;
/*     */ 
/*     */

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.usb.UsbDevice;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.RemoteException;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.gprinter.aidl.GpService;
import com.gprinter.command.GpCom;
import com.gprinter.io.GpDevice;
import com.gprinter.io.PortParameters;
import com.gprinter.save.PortParamDataBase;
import com.gprinter.util.DBUtil;
import com.gprinter.util.LogInfo;
import com.yiku.kdb_flat.BWApplication;

import java.util.List;
import java.util.Vector;

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ 
/*     */ public class GpPrintService extends Service
/*     */ {
    /*     */   private static final String DEBUG_TAG = "GpPrintService";
    /*     */   public static final String ACTION_PORT_OPEN = "action.port.open";
    /*     */   public static final String ACTION_PORT_CLOSE = "action.port.close";
    /*     */   public static final String ACTION_PRINT_TESTPAGE = "action.print.testpage";
    /*     */   public static final String PRINTER_ID = "printer.id";
    /*     */   public static final String PORT_TYPE = "port.type";
    /*     */   public static final String USB_DEVICE_NAME = "usb.devicename";
    /*     */   public static final String BLUETOOT_ADDR = "bluetooth.addr";
    /*     */   public static final String IP_ADDR = "port.addr";
    /*     */   public static final String PORT_NUMBER = "port.number";
    /*     */   public static final String CONNECT_STATUS = "connect.status";
    /*     */   public static final String PRINTER_CALLBACK = "printer.callback";
    /*     */   public static final int MAX_PRINTER_CNT = 20;
    /*     */   public static final String PRINTER_STATUS = "printer.status";
    /*     */   public static final String ACTION_PRINTER_STATUS = "action.printer.status";
    /*  54 */   private static GpDevice[] mDevice = new GpDevice[20];
    /*  55 */   private boolean[] mIsAuth = new boolean[20];
    /*     */
/*     */   public static final String KEY_USER_EXPERIENCE = "UserExperience";
    /*     */
/*     */   public static final String BrocastAction = "com.gprinter.service.ReadBrocastReceiver";
    /*     */
/*     */   public static final String ACTION_CONNECT_STATUS = "action.connect.status";
    /*     */
/*     */   public static final String PRINTER_SERVICE = "com.gprinter.aidl.GpPrintService";
    /*     */
/*     */   public static final String ALLSERVICE = "com.gprinter.service.ALLSERVICE";
    /*     */
/*     */   public static final String SMPRINT_SERVICE = "com.gprinter.service.SmPrintService";
    /*     */
/*     */   public static String IMSI;
    /*  70 */   public static int PrinterId = 1;
    /*     */
/*     */
    @android.annotation.SuppressLint({"SdCardPath"})
/*     */ public static String DB_DIR;
    /*     */
/*     */   public static final String DB_NAME = "smartprint.db";
    /*  76 */   public static DBUtil db = null;
    /*     */
/*     */ 
/*     */ 
/*  80 */   public static String CLIENTNUM = null;
    /*     */
/*  82 */   private WakeLock wakeLock = null;
    /*  83 */   private int mTimeout = 1000;
    /*  84 */   private int mServiceTimeout = 4000;
    /*     */
/*  86 */   public static final Object object = new Object();
    /*     */
/*     */   private boolean mIsReceivedStatus;
    /*     */
/*     */   public static final byte FLAG = 16;

    /*     */
/*     */
    public void onCreate()
/*     */ {
/*  94 */
        super.onCreate();
/*  95 */
        Log.d("GpPrintService", "-Service onCreate-");
/*  96 */
        acquireWakeLock();
/*  97 */
        registerUserPortActionBroadcast();
/*  98 */
        initUpService();
/*  99 */
        for (int i = 0; i < 20; i++) {
/* 100 */
            mDevice[i] = new GpDevice();
/*     */
        }
/*     */
    }

    /*     */
/*     */
    private void initUpService()
/*     */ {
/* 106 */
        DB_DIR = BWApplication.getInstance().getExternalFilesDir(null).toString();
/*     */     
/* 108 */
        String macAddress = getWIFIMacAddress();
/* 109 */
        IMSI = getIMSI(macAddress);
/*     */     
/* 111 */
        LogInfo.out("IMSI:" + IMSI);
/*     */     
/* 113 */
        CLIENTNUM = getClientNUM(IMSI);
/*     */
    }

    /*     */
/*     */
    public void registerUserPortActionBroadcast()
/*     */ {
/* 118 */
        IntentFilter filter = new IntentFilter();
/* 119 */
        filter.addAction("action.port.open");
/* 120 */
        filter.addAction("action.port.close");
/* 121 */
        filter.addAction("action.print.testpage");
/* 122 */
        filter.addAction("android.bluetooth.device.action.ACL_DISCONNECTED");
/* 123 */
        filter.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
/* 124 */
        registerReceiver(this.PortOperateBroadcastReceiver, filter);
/*     */
    }

    /*     */
/*     */
    public void onStart(Intent intent, int startId)
/*     */ {
/* 129 */
        Log.d("GpPrintService", "-Service onStart-");
/*     */
    }

    /*     */
/*     */
    public void onDestroy()
/*     */ {
/* 134 */
        Log.d("GpPrintService", "-Service onDestory-");
/* 135 */
        unregisterReceiver(this.PortOperateBroadcastReceiver);
/* 136 */
        releaseWakeLock();
/*     */     
/* 138 */
        super.onDestroy();
/*     */
    }

    /*     */
/*     */
    public boolean onUnbind(Intent intent)
/*     */ {
/* 143 */
        Log.d("GpPrintService", "-Service onUnbind-");
/* 144 */
        return super.onUnbind(intent);
/*     */
    }

    /*     */
/*     */
    public void onRebind(Intent intent)
/*     */ {
/* 149 */
        super.onRebind(intent);
/* 150 */
        Log.d("GpPrintService", "-Service onRebind-");
/*     */
    }

    /*     */
/*     */
    public int onStartCommand(Intent intent, int flags, int startId)
/*     */ {
/* 155 */
        Log.d("GpPrintService", "-Service onStartCommand-");
/* 156 */
        return 1;
/*     */
    }

    /*     */
/*     */
    public android.os.IBinder onBind(Intent intent)
/*     */ {
/* 161 */
        System.out.println("Service onBind");
/* 162 */
        return this.aidls;
/*     */
    }

    /*     */
/*     */
    public boolean[] getConnectState() {
/* 166 */
        boolean[] state = new boolean[20];
/* 167 */
        for (int i = 0; i < 20; i++) {
/* 168 */
            state[i] = false;
/*     */
        }
/* 170 */
        for (int i = 0; i < 20; i++) {
/* 171 */
            if (mDevice[i] != null) {
/* 172 */
                Log.d("GpPrintService", "getConnectState " + i);
/* 173 */
                if (mDevice[i].getConnectState() == 3) {
/* 174 */
                    state[i] = true;
/*     */
                }
/*     */
            }
/*     */
        }
/* 178 */
        return state;
/*     */
    }

    /*     */
/* 181 */ GpService.Stub aidls = new GpService.Stub()
/*     */ {
        /*     */
        public int openPort(int PrinterId, int PortType, String DeviceName, int PortNumber) throws RemoteException {
/* 184 */
            GpCom.ERROR_CODE retval = GpCom.ERROR_CODE.SUCCESS;
/* 185 */
            int type = PortType;
/* 186 */
            int id = PrinterId;
/* 187 */
            Log.d("GpPrintService", "port type " + type + "PrinterId " + id);
/* 188 */
            switch (type) {
/*     */
                case 2:
/* 190 */
                    String name = DeviceName;
/* 191 */
                    Log.d("GpPrintService", "port addr " + name);
/* 192 */
                    retval = GpPrintService.mDevice[id].openUSBPort(GpPrintService.this, id, name, GpPrintService.this.mHandler);
/* 193 */
                    break;
/*     */
                case 4:
/* 195 */
                    String addr = DeviceName;
/* 196 */
                    retval = GpPrintService.mDevice[id].openBluetoothPort(id, addr, GpPrintService.this.mHandler);
/* 197 */
                    break;
/*     */
                case 3:
/* 199 */
                    int port = PortNumber;
/* 200 */
                    addr = DeviceName;
/* 201 */
                    retval = GpPrintService.mDevice[id].openEthernetPort(id, addr, port, GpPrintService.this.mHandler);
/*     */
            }
/*     */       
/* 204 */
            return retval.ordinal();
/*     */
        }

        /*     */
/*     */
        public void closePort(int PrinterId) throws RemoteException
/*     */ {
/* 209 */
            GpPrintService.mDevice[PrinterId].closePort();
/* 210 */
            GpPrintService.this.mIsAuth[PrinterId] = false;
/*     */
        }

        /*     */
/*     */
        public int printeTestPage(int PrinterId) throws RemoteException
/*     */ {
/* 215 */
            Log.d("GpPrintService", "printeTestPage ");
/* 216 */
            int rel = GpPrintService.this.printTestPage(PrinterId);
/* 217 */
            return rel;
/*     */
        }

        /*     */
/*     */
        public int sendEscCommand(int PrinterId, String b64) throws RemoteException
/*     */ {
/* 222 */
            Log.d("GpPrintService", "sendEscCommand");
/* 223 */
            GpCom.ERROR_CODE retval = GpCom.ERROR_CODE.SUCCESS;
/* 224 */
            if (GpPrintService.mDevice[PrinterId].getCommandType() == 0) {
/* 225 */
                byte[] datas = Base64.decode(b64, 0);
/* 226 */
                Vector<Byte> vector = new Vector();
/* 227 */
                byte[] arrayOfByte1;
                int j = (arrayOfByte1 = datas).length;
                for (int i = 0; i < j; i++) {
                    byte b = arrayOfByte1[i];
/* 228 */
                    vector.add(Byte.valueOf(b));
/*     */
                }
/* 230 */
                retval = GpPrintService.mDevice[PrinterId].sendDataImmediately(vector);
/*     */
            } else {
/* 232 */
                retval = GpCom.ERROR_CODE.FAILED;
/*     */
            }
/* 234 */
            return retval.ordinal();
/*     */
        }

        /*     */
/*     */
        public int sendLabelCommand(int PrinterId, String b64) throws RemoteException
/*     */ {
/* 239 */
            GpCom.ERROR_CODE retval = GpCom.ERROR_CODE.SUCCESS;
/* 240 */
            if (GpPrintService.mDevice[PrinterId].getCommandType() == 1) {
/* 241 */
                byte[] datas = Base64.decode(b64, 0);
/* 242 */
                Vector<Byte> vector = new Vector();
/* 243 */
                byte[] arrayOfByte1;
                int j = (arrayOfByte1 = datas).length;
                for (int i = 0; i < j; i++) {
                    byte b = arrayOfByte1[i];
/* 244 */
                    vector.add(Byte.valueOf(b));
/*     */
                }
/* 246 */
                retval = GpPrintService.mDevice[PrinterId].sendDataImmediately(vector);
/*     */
            } else {
/* 248 */
                retval = GpCom.ERROR_CODE.FAILED;
/*     */
            }
/* 250 */
            return retval.ordinal();
/*     */
        }

        /*     */
/*     */
        public synchronized void queryPrinterStatus(final int PrinterId, int Timesout, int requestCode)
/*     */       throws RemoteException
/*     */ {
/* 256 */
            GpPrintService.this.mIsReceivedStatus = false;
/* 257 */
            GpCom.ERROR_CODE retval = GpCom.ERROR_CODE.SUCCESS;
/* 258 */
            int status = 1;
/* 259 */
            byte[] esc = {16, 4, 2};
/* 260 */
            byte[] tsc = {27, 33, 63};
/* 261 */
            Vector<Byte> data = null;
/* 262 */
            Log.i("GpPrintService", "queryPrintStatus ");
/* 263 */
            if (GpPrintService.mDevice[PrinterId].getConnectState() == 3) {
/* 264 */
                if (GpPrintService.mDevice[PrinterId].getCommandType() == 0) {
/* 265 */
                    data = new Vector(esc.length);
/* 266 */
                    for (int i = 0; i < esc.length; i++) {
/* 267 */
                        data.add(Byte.valueOf(esc[i]));
/*     */
                    }
/*     */
                } else {
/* 270 */
                    data = new Vector(tsc.length);
/* 271 */
                    for (int i = 0; i < tsc.length; i++) {
/* 272 */
                        data.add(Byte.valueOf(tsc[i]));
/*     */
                    }
/*     */
                }
/*     */         
/* 276 */
                GpDevice.mReceiveQueue.offer(Integer.valueOf(requestCode));
/* 277 */
                GpPrintService.mDevice[PrinterId].sendDataImmediately(data);
/* 278 */
                GpPrintService.this.mHandler.postDelayed(new Runnable()
/*     */ {
                    /*     */
                    public void run() {
/* 281 */
                        if (!GpPrintService.this.mIsReceivedStatus) {
/* 282 */
                            byte[] statusBytes = {16};
/* 283 */
                            Message msg = GpPrintService.this.mHandler.obtainMessage(2);
/* 284 */
                            Bundle bundle = new Bundle();
/* 285 */
                            bundle.putInt("printer.id", PrinterId);
/* 286 */
                            bundle.putInt("device.readcnt", 1);
/* 287 */
                            bundle.putByteArray("device.read", statusBytes);
/* 288 */
                            msg.setData(bundle);
/* 289 */
                            GpPrintService.this.mHandler.sendMessage(msg);
/*     */
                        }
/*     */
                    }
/* 292 */
                }, Timesout);
/*     */
            }
/*     */
            else {
/* 295 */
                GpDevice.mReceiveQueue.add(Integer.valueOf(requestCode));
/* 296 */
                byte[] statusBytes = {1};
/* 297 */
                Message msg = GpPrintService.this.mHandler.obtainMessage(6);
/* 298 */
                Bundle bundle = new Bundle();
/* 299 */
                bundle.putInt("printer.id", PrinterId);
/* 300 */
                bundle.putInt("device.readcnt", 1);
/* 301 */
                bundle.putByteArray("device.read", statusBytes);
/* 302 */
                msg.setData(bundle);
/* 303 */
                GpPrintService.this.mHandler.sendMessage(msg);
/*     */
            }
/*     */
        }

        /*     */
/*     */
        public int getPrinterCommandType(int PrinterId) throws RemoteException
/*     */ {
/* 309 */
            int type = GpPrintService.mDevice[PrinterId].getCommandType();
/* 310 */
            return type;
/*     */
        }

        /*     */
/*     */
        public int getPrinterConnectStatus(int PrinterId) throws RemoteException
/*     */ {
/* 315 */
            int status = GpPrintService.mDevice[PrinterId].getConnectState();
/* 316 */
            return status;
/*     */
        }

        /*     */
/*     */
        public void isUserExperience(boolean userExperience) throws RemoteException
/*     */ {
/* 321 */
            SharedPreferences sharedPreference = android.preference.PreferenceManager.getDefaultSharedPreferences(GpPrintService.this);
/* 322 */
            sharedPreference.edit().putBoolean("key_ischecked", userExperience);
/* 323 */
            SendDeviceInfoThread.isChecked(userExperience);
/*     */
        }

        /*     */
/*     */
        public String getClientID()
/*     */       throws RemoteException
/*     */ {
/* 329 */
            return "";
/*     */
        }

        /*     */
/*     */ 
/*     */ 
/*     */ 
/*     */
        public int setServerIP(String ip, int port)
/*     */       throws RemoteException
/*     */ {
/* 338 */
            return 0;
/*     */
        }
/*     */
    };
    /* 341 */   private BroadcastReceiver PortOperateBroadcastReceiver = new BroadcastReceiver()
/*     */ {
        /*     */
        public void onReceive(Context context, Intent intent) {
/* 344 */
            if ("action.port.open".equals(intent.getAction())) {
/* 345 */
                Log.d("GpPrintService", "PortOperateBroadcastReceiver action.port.open");
/* 346 */
                GpCom.ERROR_CODE retval = GpCom.ERROR_CODE.SUCCESS;
/* 347 */
                int type = intent.getIntExtra("port.type", 0);
/* 348 */
                int id = intent.getIntExtra("printer.id", 0);
/* 349 */
                Log.d("GpPrintService", "port type " + type + "PrinterId " + id);
/*     */         
/* 351 */
                switch (type) {
/*     */
                    case 2:
/* 353 */
                        String name = intent.getStringExtra("usb.devicename");
/* 354 */
                        Log.d("GpPrintService", "port addr " + name);
/* 355 */
                        retval = GpPrintService.mDevice[id].openUSBPort(GpPrintService.this, id, name, GpPrintService.this.mHandler);
/* 356 */
                        break;
/*     */
                    case 4:
/* 358 */
                        String addr = intent.getStringExtra("bluetooth.addr");
/* 359 */
                        retval = GpPrintService.mDevice[id].openBluetoothPort(id, addr, GpPrintService.this.mHandler);
/* 360 */
                        break;
/*     */
                    case 3:
/* 362 */
                        int port = intent.getIntExtra("port.number", 9100);
/* 363 */
                        addr = intent.getStringExtra("port.addr");
/* 364 */
                        retval = GpPrintService.mDevice[id].openEthernetPort(id, addr, port, GpPrintService.this.mHandler);
/*     */
                }
/*     */         
/* 367 */
                if (retval != GpCom.ERROR_CODE.SUCCESS) {
/* 368 */
                    GpPrintService.this.showError(retval);
/*     */
                }
/* 370 */
            } else if ("action.port.close".equals(intent.getAction())) {
/* 371 */
                int id = intent.getIntExtra("printer.id", 0);
/* 372 */
                Log.d("GpPrintService", "PrinterId " + id);
/* 373 */
                GpPrintService.mDevice[id].closePort();
/* 374 */
            } else if ("action.print.testpage".equals(intent.getAction())) {
/* 375 */
                int id = intent.getIntExtra("printer.id", 0);
/* 376 */
                GpPrintService.this.printTestPage(id);
/* 377 */
            } else if ("android.bluetooth.device.action.ACL_DISCONNECTED".equals(intent.getAction())) {
/* 378 */
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
/* 379 */
                GpPrintService.this.disconnectBluetoothDevice(device.getAddress());
/* 380 */
            } else if ("android.hardware.usb.action.USB_DEVICE_DETACHED".equals(intent.getAction())) {
/* 381 */
                UsbDevice device = (UsbDevice) intent.getParcelableExtra("device");
/* 382 */
                GpPrintService.this.disconnectUsbDevice(device.getDeviceName());
/*     */
            }
/*     */
        }
/*     */
    };
    /*     */   private boolean isServiceStart;

    /*     */
/* 388 */
    private void showError(GpCom.ERROR_CODE retval) {
        GpCom.getErrorText(retval);
    }

    /*     */
/*     */
    private void disconnectBluetoothDevice(String addr)
/*     */ {
/* 392 */
        for (int i = 0; i < 20; i++) {
/* 393 */
            PortParameters p = mDevice[i].getPortParameters();
/* 394 */
            if ((p.getPortType() == 4) && (p.getBluetoothAddr().equals(addr))) {
/* 395 */
                mDevice[i].closePort();
/* 396 */
                break;
/*     */
            }
/*     */
        }
/*     */
    }

    /*     */
/*     */
    private void disconnectUsbDevice(String name) {
/* 402 */
        for (int i = 0; i < 20; i++) {
/* 403 */
            PortParameters p = mDevice[i].getPortParameters();
/* 404 */
            if ((p.getPortType() == 2) && (p.getUsbDeviceName().equals(name))) {
/* 405 */
                mDevice[i].closePort();
/* 406 */
                break;
/*     */
            }
/*     */
        }
/*     */
    }

    /*     */
/*     */   int printTestPage(int id) {
/* 412 */
        GpCom.ERROR_CODE retval = GpCom.ERROR_CODE.SUCCESS;
/* 413 */
        Vector<Byte> TestPageData = null;
/* 414 */
        if (mDevice[id].getConnectState() == 3) {
/* 415 */
            if (mDevice[id].getCommandType() == 0) {
/* 416 */
                if (getResources().getConfiguration().locale.getCountry().equals("CN")) {
/* 417 */
                    TestPageData = getTestPageData("/esc_CN.txt");
/* 418 */
                    Log.d("GpPrintService", "Send  ESC data ");
/*     */
                } else {
/* 420 */
                    TestPageData = getTestPageData("/esc.txt");
/*     */
                }
/* 422 */
                retval = mDevice[id].sendDataImmediately(TestPageData);
/* 423 */
            } else if (mDevice[id].getCommandType() == 1) {
/* 424 */
                Log.d("GpPrintService", "Send Label data ");
/* 425 */
                if (getResources().getConfiguration().locale.getCountry().equals("CN")) {
/* 426 */
                    TestPageData = getTestPageData("/tsc_CN.txt");
/*     */
                } else {
/* 428 */
                    TestPageData = getTestPageData("/tsc.txt");
/*     */
                }
/* 430 */
                retval = mDevice[id].sendDataImmediately(TestPageData);
/*     */
            } else {
/* 432 */
                retval = GpCom.ERROR_CODE.INVALID_DEVICE_PARAMETERS;
/*     */
            }
/*     */
        } else {
/* 435 */
            Log.d("GpPrintService", "Port is not connect ");
/* 436 */
            retval = GpCom.ERROR_CODE.PORT_IS_NOT_OPEN;
/*     */
        }
/* 438 */
        return retval.ordinal();
/*     */
    }

    /*     */
/*     */   /* Error */
/*     */   Vector<Byte> getTestPageData(String root)
/*     */ {
        return new Vector<Byte>();
    }

    /*     */     // Byte code:
/*     */     //   0: aconst_null
/*     */     //   1: astore_2
/*     */     //   2: ldc 8
/*     */     //   4: new 204	java/lang/StringBuilder
/*     */     //   7: dup
/*     */     //   8: ldc_w 416
/*     */     //   11: invokespecial 208	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
/*     */     //   14: aload_1
/*     */     //   15: invokevirtual 211	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   18: invokevirtual 215	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*     */     //   21: invokestatic 165	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
/*     */     //   24: pop
/*     */     //   25: aload_0
/*     */     //   26: invokevirtual 418	java/lang/Object:getClass	()Ljava/lang/Class;
/*     */     //   29: aload_1
/*     */     //   30: invokevirtual 422	java/lang/Class:getResourceAsStream	(Ljava/lang/String;)Ljava/io/InputStream;
/*     */     //   33: astore_3
/*     */     //   34: sipush 8192
/*     */     //   37: newarray <illegal type>
/*     */     //   39: astore 4
/*     */     //   41: iconst_0
/*     */     //   42: istore 5
/*     */     //   44: new 428	java/io/ByteArrayOutputStream
/*     */     //   47: dup
/*     */     //   48: invokespecial 430	java/io/ByteArrayOutputStream:<init>	()V
/*     */     //   51: astore 6
/*     */     //   53: goto +13 -> 66
/*     */     //   56: aload 6
/*     */     //   58: aload 4
/*     */     //   60: iconst_0
/*     */     //   61: iload 5
/*     */     //   63: invokevirtual 431	java/io/ByteArrayOutputStream:write	([BII)V
/*     */     //   66: aload_3
/*     */     //   67: aload 4
/*     */     //   69: invokevirtual 435	java/io/InputStream:read	([B)I
/*     */     //   72: dup
/*     */     //   73: istore 5
/*     */     //   75: iconst_m1
/*     */     //   76: if_icmpne -20 -> 56
/*     */     //   79: aload 6
/*     */     //   81: invokevirtual 441	java/io/ByteArrayOutputStream:toByteArray	()[B
/*     */     //   84: astore_2
/*     */     //   85: goto +46 -> 131
/*     */     //   88: astore 6
/*     */     //   90: aload 6
/*     */     //   92: invokevirtual 445	java/io/IOException:printStackTrace	()V
/*     */     //   95: aload_3
/*     */     //   96: invokevirtual 450	java/io/InputStream:close	()V
/*     */     //   99: goto +46 -> 145
/*     */     //   102: astore 8
/*     */     //   104: aload 8
/*     */     //   106: invokevirtual 445	java/io/IOException:printStackTrace	()V
/*     */     //   109: goto +36 -> 145
/*     */     //   112: astore 7
/*     */     //   114: aload_3
/*     */     //   115: invokevirtual 450	java/io/InputStream:close	()V
/*     */     //   118: goto +10 -> 128
/*     */     //   121: astore 8
/*     */     //   123: aload 8
/*     */     //   125: invokevirtual 445	java/io/IOException:printStackTrace	()V
/*     */     //   128: aload 7
/*     */     //   130: athrow
/*     */     //   131: aload_3
/*     */     //   132: invokevirtual 450	java/io/InputStream:close	()V
/*     */     //   135: goto +10 -> 145
/*     */     //   138: astore 8
/*     */     //   140: aload 8
/*     */     //   142: invokevirtual 445	java/io/IOException:printStackTrace	()V
/*     */     //   145: new 412	java/util/Vector
/*     */     //   148: dup
/*     */     //   149: aload_2
/*     */     //   150: arraylength
/*     */     //   151: invokespecial 453	java/util/Vector:<init>	(I)V
/*     */     //   154: astore 6
/*     */     //   156: iconst_0
/*     */     //   157: istore 7
/*     */     //   159: goto +20 -> 179
/*     */     //   162: aload 6
/*     */     //   164: aload 4
/*     */     //   166: iload 7
/*     */     //   168: baload
/*     */     //   169: invokestatic 456	java/lang/Byte:valueOf	(B)Ljava/lang/Byte;
/*     */     //   172: invokevirtual 462	java/util/Vector:add	(Ljava/lang/Object;)Z
/*     */     //   175: pop
/*     */     //   176: iinc 7 1
/*     */     //   179: iload 7
/*     */     //   181: aload_2
/*     */     //   182: arraylength
/*     */     //   183: if_icmplt -21 -> 162
/*     */     //   186: aload 6
/*     */     //   188: areturn
/*     */     // Line number table:
/*     */     //   Java source line #442	-> byte code offset #0
/*     */     //   Java source line #443	-> byte code offset #2
/*     */     //   Java source line #444	-> byte code offset #25
/*     */     //   Java source line #445	-> byte code offset #34
/*     */     //   Java source line #446	-> byte code offset #41
/*     */     //   Java source line #448	-> byte code offset #44
/*     */     //   Java source line #449	-> byte code offset #53
/*     */     //   Java source line #450	-> byte code offset #56
/*     */     //   Java source line #449	-> byte code offset #66
/*     */     //   Java source line #452	-> byte code offset #79
/*     */     //   Java source line #453	-> byte code offset #85
/*     */     //   Java source line #454	-> byte code offset #90
/*     */     //   Java source line #457	-> byte code offset #95
/*     */     //   Java source line #458	-> byte code offset #99
/*     */     //   Java source line #459	-> byte code offset #104
/*     */     //   Java source line #455	-> byte code offset #112
/*     */     //   Java source line #457	-> byte code offset #114
/*     */     //   Java source line #458	-> byte code offset #118
/*     */     //   Java source line #459	-> byte code offset #123
/*     */     //   Java source line #461	-> byte code offset #128
/*     */     //   Java source line #457	-> byte code offset #131
/*     */     //   Java source line #458	-> byte code offset #135
/*     */     //   Java source line #459	-> byte code offset #140
/*     */     //   Java source line #462	-> byte code offset #145
/*     */     //   Java source line #463	-> byte code offset #156
/*     */     //   Java source line #464	-> byte code offset #162
/*     */     //   Java source line #463	-> byte code offset #176
/*     */     //   Java source line #466	-> byte code offset #186
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	189	0	this	GpPrintService
/*     */     //   0	189	1	root	String
/*     */     //   1	181	2	data	byte[]
/*     */     //   33	99	3	in	java.io.InputStream
/*     */     //   39	126	4	bs	byte[]
/*     */     //   42	32	5	len	int
/*     */     //   51	29	6	out	java.io.ByteArrayOutputStream
/*     */     //   88	3	6	e	java.io.IOException
/*     */     //   154	33	6	TestPageData	Vector<Byte>
/*     */     //   112	17	7	localObject	Object
/*     */     //   157	27	7	i	int
/*     */     //   102	3	8	e	java.io.IOException
/*     */     //   121	3	8	e	java.io.IOException
/*     */     //   138	3	8	e	java.io.IOException
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   44	85	88	java/io/IOException
/*     */     //   95	99	102	java/io/IOException
/*     */     //   44	95	112	finally
/*     */     //   114	118	121	java/io/IOException
/*     */     //   131	135	138	java/io/IOException
/*     */   //}
/*     */   
/*     */
    private void sendConnectionStatusBroadcastToFront(final int id)
/*     */ {
/* 472 */
        this.isServiceStart = false;
/* 473 */
        Log.d("GpPrintService", "Current state ->[" + id + "]" + mDevice[id].getConnectState());
/* 474 */
        if (mDevice[id].getConnectState() == 3) {
/* 475 */
            final int type = mDevice[id].getCommandType();
/* 476 */
            Intent allServiceIntent = new Intent(this, AllService.class);
/* 477 */
            allServiceIntent.putExtra("mode", type);
/* 478 */
            allServiceIntent.putExtra("printId", id);
/* 479 */
            startService(allServiceIntent);
/*     */       
/* 481 */
            new Thread(new Runnable()
/*     */ {
                /*     */
                public void run()
/*     */ {
/* 485 */
                    long time = System.currentTimeMillis();
/* 486 */
                    long timeout = time + GpPrintService.this.mServiceTimeout;
/* 487 */
                    while ((time < timeout) && (!GpPrintService.this.isServiceStart)) {
/*     */
                        try {
/* 489 */
                            Thread.sleep(100L);
/*     */
                        } catch (InterruptedException e) {
/* 491 */
                            e.printStackTrace();
/*     */
                        }
/* 493 */
                        time = System.currentTimeMillis();
/* 494 */
                        GpPrintService.this.isServiceStart = GpPrintService.this.isServiceRunning(AllService.class);
/*     */
                    }
/* 496 */
                    if (GpPrintService.this.isServiceStart) {
/* 497 */
                        Log.e("GpPrintService", "STATE_VALID_PRINTER");
/* 498 */
                        GpPrintService.mDevice[id].setCommandType(type);
/* 499 */
                        Intent intent = new Intent("action.connect.status");
/* 500 */
                        intent.putExtra("connect.status", 5);
/* 501 */
                        intent.putExtra("printer.id", id);
/* 502 */
                        GpPrintService.this.sendBroadcast(intent);
/*     */
                    } else {
/* 504 */
                        GpPrintService.mDevice[id].closePort();
/* 505 */
                        Intent intent = new Intent("action.connect.status");
/* 506 */
                        intent.putExtra("connect.status", 0);
/* 507 */
                        intent.putExtra("printer.id", id);
/* 508 */
                        GpPrintService.this.sendBroadcast(intent);
/*     */             
/* 510 */
                        Message msg = GpPrintService.this.mHandler.obtainMessage(5);
/* 511 */
                        Bundle bundle = new Bundle();
/* 512 */
                        bundle.putInt("printer.id", id);
/* 513 */
                        bundle.putString("toast", "Please start service");
/* 514 */
                        msg.setData(bundle);
/* 515 */
                        GpPrintService.this.mHandler.sendMessage(msg);
/*     */
                    }
/*     */
                }
/*     */
            })
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 518 */.start();
/*     */
        }
/*     */
    }

    /*     */
/*     */
    private void sendFinishBroadcastToFront(int id)
/*     */ {
/* 524 */
        Intent statusBroadcast = new Intent("action.device.receipt.response");
/* 525 */
        statusBroadcast.putExtra("printer.id", id);
/* 526 */
        sendBroadcast(statusBroadcast);
/*     */
    }

    /*     */
/*     */
    private void sendStatusBroadcastToFront(int id, int r) {
/* 530 */
        Log.i("GpPrintService", "printer disconnect " + r);
/*     */
        int status;
/* 533 */
        if (r == 16) {
/* 534 */
            status = 16;
/*     */
        } else {
/* 536 */
            status = 0;
/*     */
        }
/* 538 */
        if (mDevice[id].getConnectState() == 3)
/*     */ {
/* 540 */
            if (mDevice[id].getCommandType() == 0) {
/* 541 */
                if ((r & 0x20) > 0) {
/* 542 */
                    status |= 0x2;
/*     */
                }
/* 544 */
                if ((r & 0x4) > 0) {
/* 545 */
                    status |= 0x4;
/*     */
                }
/* 547 */
                if ((r & 0x40) > 0) {
/* 548 */
                    status |= 0x8;
/*     */
                }
/*     */
            } else {
/* 551 */
                if ((r & 0x4) > 0) {
/* 552 */
                    status |= 0x2;
/*     */
                }
/* 554 */
                if ((r & 0x40) > 0) {
/* 555 */
                    status |= 0x4;
/*     */
                }
/* 557 */
                if ((r & 0x80) > 0) {
/* 558 */
                    status |= 0x8;
/*     */
                }
/*     */
            }
/*     */
        }
/*     */
        else {
/* 563 */
            status |= 0x1;
/*     */
        }
/*     */     
/* 566 */
        Integer requestCode = (Integer) GpDevice.mReceiveQueue.poll();
/* 567 */
        if (requestCode == null) {
/* 568 */
            return;
/*     */
        }
/* 570 */
        Intent statusBroadcast = new Intent("action.device.real.status");
/* 571 */
        statusBroadcast.putExtra("action.printer.real.status", status);
/* 572 */
        statusBroadcast.putExtra("printer.id", id);
/* 573 */
        statusBroadcast.putExtra("printer.request_code", requestCode);
/* 574 */
        sendBroadcast(statusBroadcast);
/*     */
    }

    /*     */
/*     */
    private void sendResponseBroadcastToFront(int id, byte[] response, int cnt) {
/* 578 */
        Intent statusBroadcast = new Intent("action.device.label.response");
/* 579 */
        statusBroadcast.putExtra("printer.id", id);
/* 580 */
        statusBroadcast.putExtra("printer.label.response", response);
/* 581 */
        statusBroadcast.putExtra("printer.label.response.cnt", cnt);
/* 582 */
        sendBroadcast(statusBroadcast);
/*     */
    }

    /*     */
/* 585 */   private final Handler mHandler = new Handler(new Handler.Callback()
/*     */ {
        /*     */
        public boolean handleMessage(Message msg) {
/* 588 */
            switch (msg.what) {
/*     */
                case 1:
/* 590 */
                    Log.i("GpPrintService", "MESSAGE_STATE_CHANGE: " + msg.arg1);
/* 591 */
                    int type = msg.getData().getInt("device_status");
/* 592 */
                    final int id = msg.getData().getInt("printer.id");
/* 593 */
                    switch (type) {
/*     */
                        case 3:
/* 595 */
                            Log.i("GpPrintService", "STATE_CONNECTED");
/* 596 */
                            Vector<Byte> vector = new Vector();
/* 597 */
                            vector.add(Byte.valueOf((byte) 29));
/* 598 */
                            vector.add(Byte.valueOf((byte) 73));
/* 599 */
                            vector.add(Byte.valueOf((byte) 67));
/* 600 */
                            GpPrintService.mDevice[id].sendDataImmediately(vector);
/* 601 */
                            Log.d("GpPrintUsb", "send auth 1");
/*     */           
/* 603 */
                            GpPrintService.this.mHandler.postDelayed(new Runnable()
/*     */ {
                                /*     */
                                public void run() {
/* 606 */
                                    if (GpPrintService.this.mIsAuth[id] == false) {
/* 607 */
                                        Log.d("GpPrintUsb", "send auth label 3");
/* 608 */
                                        Vector<Byte> tscAuth = new Vector();
/* 609 */
                                        tscAuth.add(Byte.valueOf((byte) 126));
/* 610 */
                                        tscAuth.add(Byte.valueOf((byte) 33));
/* 611 */
                                        tscAuth.add(Byte.valueOf((byte) 84));
/* 612 */
                                        Log.d("GpPrintUsbPort", "send ~!T");
/* 613 */
                                        GpPrintService.mDevice[id].sendDataImmediately(tscAuth);
/* 614 */
                                        GpPrintService.this.mHandler.postDelayed(new Runnable()
/*     */ {
                                            /*     */
                                            public void run() {
/* 617 */
                                                if (GpPrintService.this.mIsAuth[id] == false) {
/* 618 */
                                                    GpPrintService.mDevice[id].closePort();
/*     */
                                                }
/*     */
                                            }
/* 621 */
                                        }, 4000L);
/*     */
                                    }
/*     */
                                }
/* 624 */
                            }, 4000L);
/*     */           
/* 626 */
                            break;
/*     */
                        case 2:
/* 628 */
                            Log.i("GpPrintService", "STATE_CONNECTING");
/* 629 */
                            break;
/*     */
                        case 0:
/*     */
                        case 1:
/* 632 */
                            Log.i("GpPrintService", "STATE_NONE");
/* 633 */
                            GpPrintService.this.mIsAuth[id] = false;
/*     */
                    }
/*     */         
/* 636 */
                    Intent intent = new Intent("action.connect.status");
/* 637 */
                    intent.putExtra("connect.status", type);
/* 638 */
                    intent.putExtra("printer.id", id);
/* 639 */
                    GpPrintService.this.sendBroadcast(intent);
/* 640 */
                    break;
/*     */
                case 3:
/*     */
                    break;
/*     */       
/*     */
                case 2:
/* 645 */
                    int printerId = msg.getData().getInt("printer.id");
/* 646 */
                    int cnt = msg.getData().getInt("device.readcnt");
/* 647 */
                    byte[] readBuf = msg.getData().getByteArray("device.read");
/* 648 */
                    List<Byte> data = new java.util.ArrayList();
/* 649 */
                    for (int i = 0; i < cnt; i++) {
/* 650 */
                        if ((readBuf[i] != 19) && (readBuf[i] != 17)) {
/* 651 */
                            data.add(Byte.valueOf(readBuf[i]));
/*     */
                        }
/*     */
                    }
/* 654 */
                    if (GpPrintService.this.mIsAuth[printerId] == false) {
/* 655 */
                        byte[] device = new byte[cnt];
/* 656 */
                        int size = data.size();
/* 657 */
                        for (int i = 0; i < size; i++) {
/* 658 */
                            device[i] = ((Byte) data.get(i)).byteValue();
/*     */
                        }
/* 660 */
                        String name = new String(device, 0, size);
/* 661 */
                        new PortParamDataBase(GpPrintService.this).insertPrinterName(printerId, name);
/* 662 */
                        GpPrintService.this.mIsAuth[printerId] = GpPrintService.this.IsGprinter(printerId, device);
/* 663 */
                        Log.d("GpPrintService", "RESULT AUTH->" + GpPrintService.this.mIsAuth[printerId]);
/* 664 */
                        Log.d("GpPrintService", "size->" + size);
/* 665 */
                        if (GpPrintService.this.mIsAuth[printerId] == true) {
/* 666 */
                            GpPrintService.this.sendConnectionStatusBroadcastToFront(printerId);
/*     */
                        }
/*     */
                    } else {
/* 669 */
                        GpPrintService.this.mIsReceivedStatus = true;
/* 670 */
                        Log.i("GpPrintService", "readMessage cnt" + cnt);
/* 671 */
                        if (GpPrintService.mDevice[printerId].getCommandType() == 0) {
/* 672 */
                            if (cnt <= 1)
/*     */ {
/*     */ 
/*     */ 
/* 676 */
                                int result = GpPrintService.this.judgeResponseType(readBuf[0]);
/* 677 */
                                if (result == 0) {
/* 678 */
                                    if (readBuf[0] == 0)
/*     */ {
/* 680 */
                                        GpPrintService.this.sendFinishBroadcastToFront(printerId);
/*     */
                                    }
                                    else if (readBuf[0]==18){
                                        GpPrintService.this.sendStatusBroadcastToFront(printerId, readBuf[0]);
                                    }
/* 682 */
                                } else if (result == 1)
/*     */ {
/* 684 */
                                    GpPrintService.this.sendStatusBroadcastToFront(printerId, readBuf[0]);
                                }
/*     */
                            }
/* 686 */
                        } else if (GpPrintService.mDevice[printerId].getCommandType() == 1) {
/* 687 */
                            if (cnt == 1)
/*     */ {
/* 689 */
                                GpPrintService.this.sendStatusBroadcastToFront(printerId, readBuf[0]);
/*     */
                            }
/*     */
                            else {
/* 692 */
                                GpPrintService.this.sendResponseBroadcastToFront(printerId, readBuf, cnt);
/*     */
                            }
/*     */
                        }
/*     */
                    }
/*     */         
/*     */ 
/* 698 */
                    break;
/*     */       
/*     */
                case 4:
/* 701 */
                    Log.i("GpPrintService", "DeviceName: " + msg.getData().getString("device_name"));
/* 702 */
                    break;
/*     */
                case 5:
/* 704 */
                    Log.i("GpPrintService", "MessageToast: " + msg.getData().getString("toast"));
/* 705 */
                    Toast.makeText(GpPrintService.this, msg.getData().getString("toast"), 0).show();
/* 706 */
                    break;
/*     */
                case 6:
/* 708 */
                    GpPrintService.this.mIsReceivedStatus = true;
/* 709 */
                    int pid = msg.getData().getInt("printer.id");
/* 710 */
                    cnt = msg.getData().getInt("device.readcnt");
/* 711 */
                    readBuf = msg.getData().getByteArray("device.read");
/* 712 */
                    Log.i("GpPrintService", "readMessage byte " + readBuf[0]);
/* 713 */
                    Log.i("GpPrintService", "readMessage cnt" + cnt);
/* 714 */
                    GpPrintService.this.sendStatusBroadcastToFront(pid, readBuf[0]);
/*     */
            }
/*     */       
/*     */       
/* 718 */
            return false;
/*     */
        }
/* 585 */
    });

    /*     */
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */
    private int judgeResponseType(byte r)
/*     */ {
///* 726 */     byte result = (byte)((r & 0x10) >> 4);

        byte result = (byte) ((r & 0x12) >> 6);

/* 727 */
        return result;
/*     */
    }

    /*     */
/*     */
    private boolean judgePrinter(byte[] table, byte[] readPrinter)
/*     */ {
/* 732 */
        StringBuilder sb = new StringBuilder();
/* 733 */
        for (int i = 0; i < readPrinter.length; i++) {
/* 734 */
            sb.append(readPrinter[i]);
/* 735 */
            sb.append(" ");
/*     */
        }
/* 737 */
        Log.d("GpPrintService", sb.toString());
/* 738 */
        boolean result = false;
/* 739 */
        int readLength = readPrinter.length;
/* 740 */
        if (readLength < 5) {
/* 741 */
            return result;
/*     */
        }
/*     */     
/* 744 */
        int tableLength = table.length;
/* 745 */
        if (tableLength >= readLength) {
/* 746 */
            for (int i = 0; i < readLength; i++) {
/* 747 */
                if (table[i] != readPrinter[i]) {
/* 748 */
                    return result;
/*     */
                }
/*     */
            }
/*     */
        } else {
/* 752 */
            return false;
/*     */
        }
/*     */     
/* 755 */
        result = true;
/* 756 */
        if (result) {
/* 757 */
            sb.delete(0, sb.length());
/* 758 */
            for (int i = 0; i < table.length; i++) {
/* 759 */
                sb.append(table[i]);
/* 760 */
                sb.append(" ");
/*     */
            }
/*     */       
/* 763 */
            Log.d("GpPrintService", sb.toString());
/*     */
        }
/*     */     
/* 766 */
        return result;
/*     */
    }

    /*     */
/*     */
    private boolean IsGprinter(int id, byte[] readBuf)
/*     */ {
/* 771 */
        boolean result = false;
/*     */     
/* 773 */
        byte[] PRINTER_NAME_TABLE1 = {95, 71, 80, 55, 54, 32, 83, 101, 114, 105, 101, 115};
/* 774 */
        byte[] PRINTER_NAME_TABLE2 = {95, 71, 80, 45, 50, 51, 51, 48, 73, 86, 67};
/* 775 */
        byte[] PRINTER_NAME_TABLE3 = {95, 71, 80, 53, 56, 57, 48, 88, 73, 73, 73};
/* 776 */
        byte[] PRINTER_NAME_TABLE4 = {95, 71, 80, 45, 76, 56, 48, 49, 54, 48};
/* 777 */
        byte[] PRINTER_NAME_TABLE5 = {95, 71, 80, 45, 76, 56, 48, 51, 48, 48};
/* 778 */
        byte[] PRINTER_NAME_TABLE6 = {95, 71, 80, 45, 56, 48, 49, 50, 48, 73};
/* 779 */
        byte[] PRINTER_NAME_TABLE7 = {95, 71, 80, 45, 53, 56, 49, 51, 48};
/* 780 */
        byte[] PRINTER_NAME_TABLE8 = {95, 71, 80, 53, 56, 49, 51, 48};
/* 781 */
        byte[] PRINTER_NAME_TABLE9 = {95, 71, 80, 50, 49, 50, 48, 84};
/* 782 */
        byte[] PRINTER_NAME_TABLE10 = {95, 71, 80, 45, 85, 52, 50, 48};
/* 783 */
        byte[] PRINTER_NAME_TABLE11 = {95, 71, 80, 53, 56, 57, 48};
/* 784 */
        byte[] PRINTER_NAME_TABLE12 = {95, 80, 84, 50, 56, 48};
/* 785 */
        byte[] PRINTER_NAME_TABLE13 = {95, 80, 114, 111, 53};
/* 786 */
        byte[] PRINTER_NAME_TABLE14 = {71, 80, 75, 83, 45};
/*     */
        byte[] PRINTER_NAME_TABLE15 = {95, 84, 77, 45, 84, 56, 56, 73, 73, 73, 0};
        byte[] PRINTER_NAME_TABLE16 = {95, 71, 80, 50, 49, 50, 48, 84, 0};

/*     */ 
/*     */ 
/* 790 */
        byte[] PRINTER_TYPE1 = {77, 79, 68, 69, 76, 58, 71, 80, 45, 51, 49, 50, 48, 84, 76, 13,
/* 791 */       10};
/* 792 */
        byte[] PRINTER_TYPE2 = {77, 79, 68, 69, 76, 58, 71, 80, 45, 57, 48, 51, 52, 84, 13, 10};
/* 793 */
        byte[] PRINTER_TYPE3 = {77, 79, 68, 69, 76, 58, 71, 80, 45, 57, 48, 50, 53, 84, 13, 10};
/* 794 */
        byte[] PRINTER_TYPE4 = {77, 79, 68, 69, 76, 58, 71, 80, 45, 49, 49, 50, 52, 68, 13, 10};
/* 795 */
        byte[] PRINTER_TYPE5 = {77, 79, 68, 69, 76, 58, 71, 80, 45, 49, 49, 50, 52, 84, 13, 10};
/* 796 */
        byte[] PRINTER_TYPE6 = {77, 79, 68, 69, 76, 58, 71, 80, 45, 49, 49, 51, 52, 84, 13, 10};
/* 797 */
        byte[] PRINTER_TYPE7 = {77, 79, 68, 69, 76, 58, 71, 80, 45, 57, 48, 50, 53, 84, 13, 10};
/* 798 */
        byte[] PRINTER_TYPE8 = {77, 79, 68, 69, 76, 58, 71, 80, 45, 57, 49, 51, 52, 84, 13, 10};
/* 799 */
        byte[] PRINTER_TYPE9 = {77, 79, 68, 69, 76, 58, 71, 80, 45, 50, 49, 50, 48, 13, 10};
/*     */
        if (!result) {
/* 802 */
            result = judgePrinter(PRINTER_NAME_TABLE15, readBuf);
/*     */
        }
        if (!result) {
/* 802 */
            result = judgePrinter(PRINTER_NAME_TABLE16, readBuf);
/*     */
        }
/* 801 */
        if (!result) {
/* 802 */
            result = judgePrinter(PRINTER_NAME_TABLE1, readBuf);
/*     */
        }
/*     */     
/* 805 */
        if (!result) {
/* 806 */
            result = judgePrinter(PRINTER_NAME_TABLE2, readBuf);
/*     */
        }
/*     */     
/* 809 */
        if (!result) {
/* 810 */
            result = judgePrinter(PRINTER_NAME_TABLE3, readBuf);
/*     */
        }
/*     */     
/* 813 */
        if (!result) {
/* 814 */
            result = judgePrinter(PRINTER_NAME_TABLE4, readBuf);
/*     */
        }
/*     */     
/* 817 */
        if (!result) {
/* 818 */
            result = judgePrinter(PRINTER_NAME_TABLE5, readBuf);
/*     */
        }
/*     */     
/*     */ 
/* 822 */
        if (!result) {
/* 823 */
            result = judgePrinter(PRINTER_NAME_TABLE6, readBuf);
/*     */
        }
/*     */     
/* 826 */
        if (!result) {
/* 827 */
            result = judgePrinter(PRINTER_NAME_TABLE7, readBuf);
/*     */
        }
/*     */     
/* 830 */
        if (!result) {
/* 831 */
            result = judgePrinter(PRINTER_NAME_TABLE8, readBuf);
/*     */
        }
/*     */     
/* 834 */
        if (!result) {
/* 835 */
            result = judgePrinter(PRINTER_NAME_TABLE9, readBuf);
/*     */
        }
/*     */     
/* 838 */
        if (!result) {
/* 839 */
            result = judgePrinter(PRINTER_NAME_TABLE10, readBuf);
/*     */
        }
/*     */     
/* 842 */
        if (!result) {
/* 843 */
            result = judgePrinter(PRINTER_NAME_TABLE11, readBuf);
/*     */
        }
/*     */     
/* 846 */
        if (!result) {
/* 847 */
            result = judgePrinter(PRINTER_NAME_TABLE12, readBuf);
/*     */
        }
/*     */     
/* 850 */
        if (!result) {
/* 851 */
            result = judgePrinter(PRINTER_NAME_TABLE13, readBuf);
/*     */
        }
/*     */     
/* 854 */
        if (!result) {
/* 855 */
            result = judgePrinter(PRINTER_NAME_TABLE14, readBuf);
/*     */
        }
/*     */     
/* 858 */
        if (result) {
/* 859 */
            mDevice[id].setCommandType(0);
/* 860 */
            return result;
/*     */
        }
/*     */     
/*     */ 
/* 864 */
        result = judgePrinter(PRINTER_TYPE1, readBuf);
/*     */     
/* 866 */
        if (!result) {
/* 867 */
            result = judgePrinter(PRINTER_TYPE2, readBuf);
/*     */
        }
/*     */     
/* 870 */
        if (!result) {
/* 871 */
            result = judgePrinter(PRINTER_TYPE3, readBuf);
/*     */
        }
/* 873 */
        if (!result) {
/* 874 */
            result = judgePrinter(PRINTER_TYPE4, readBuf);
/*     */
        }
/* 876 */
        if (!result) {
/* 877 */
            result = judgePrinter(PRINTER_TYPE5, readBuf);
/*     */
        }
/* 879 */
        if (!result) {
/* 880 */
            result = judgePrinter(PRINTER_TYPE6, readBuf);
/*     */
        }
/* 882 */
        if (!result) {
/* 883 */
            result = judgePrinter(PRINTER_TYPE7, readBuf);
/*     */
        }
/* 885 */
        if (!result) {
/* 886 */
            result = judgePrinter(PRINTER_TYPE8, readBuf);
/*     */
        }
/* 888 */
        if (!result) {
/* 889 */
            result = judgePrinter(PRINTER_TYPE9, readBuf);
/*     */
        }
/*     */     
/* 892 */
        if (result) {
/* 893 */
            mDevice[id].setCommandType(1);
/* 894 */
            return result;
/*     */
        }
/*     */     
/* 897 */
        return result;
/*     */
    }

    /*     */
/*     */
    public boolean useList(byte[] arr, Object targetValue) {
/* 901 */
        return java.util.Arrays.asList(new byte[][]{arr}).contains(targetValue);
/*     */
    }

    /*     */
/*     */
    private void acquireWakeLock()
/*     */ {
/* 906 */
        if (this.wakeLock == null) {
/* 907 */
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
/* 908 */
            this.wakeLock = pm.newWakeLock(536870913,
/* 909 */         getClass().getCanonicalName());
/* 910 */
            if (this.wakeLock != null) {
/* 911 */
                Log.i("-wakeLock-", "wakelock acquireWakeLock");
/* 912 */
                this.wakeLock.acquire();
/*     */
            }
/*     */
        }
/*     */
    }

    /*     */
/*     */
    private void releaseWakeLock()
/*     */ {
/* 919 */
        if ((this.wakeLock != null) && (this.wakeLock.isHeld())) {
/* 920 */
            Log.i("-releaseWakeLock-", "wakelock releaseWakeLock");
/* 921 */
            this.wakeLock.release();
/* 922 */
            this.wakeLock = null;
/*     */
        }
/*     */
    }

    /*     */
/*     */
    public static String getClientNUM(String IMSI) {
/* 927 */
        if (IMSI.length() != 15) {
/* 928 */
            return null;
/*     */
        }
/* 930 */
        String[] map = {"3", "9", "6", "1", "5", "0", "8", "4", "2", "7"};
/* 931 */
        int x = 0;
/* 932 */
        for (int i = 0; i < IMSI.length(); i++) {
/* 933 */
            x = x + IMSI.charAt(i) - 48;
/*     */
        }
/* 935 */
        int y = x + (IMSI.charAt(10) - '0') + (IMSI.charAt(12) - '0') + (IMSI.charAt(14) - '0');
/* 936 */
        int z = y % 10;
/* 937 */
        String n = map[z];
/* 938 */
        String clientNum = IMSI + n;
/*     */     
/* 940 */
        return clientNum;
/*     */
    }

    /*     */
/*     */
    public String getIMSI(String macAddress) {
/* 944 */
        if (android.text.TextUtils.isEmpty(macAddress)) {
/* 945 */
            return "";
/*     */
        }
/* 947 */
        macAddress = macAddress.replaceAll(":", "");
/* 948 */
        long long_IMSI = Long.parseLong(macAddress, 16);
/* 949 */
        long_IMSI += 100000000000000L;
/* 950 */
        String str_IMSI = String.valueOf(long_IMSI);
/* 951 */
        return str_IMSI;
/*     */
    }

    /*     */
/*     */
    private String getWIFIMacAddress() {
/* 955 */
        String macAddress = null;
/* 956 */
        WifiManager wifiMgr = (WifiManager) getSystemService("wifi");
/* 957 */
        WifiInfo info = wifiMgr == null ? null : wifiMgr.getConnectionInfo();
/* 958 */
        if (info != null) {
/* 959 */
            macAddress = info.getMacAddress();
/*     */
        }
/* 961 */
        LogInfo.out("mac:" + macAddress);
/* 962 */
        return macAddress;
/*     */
    }

    /*     */
/*     */
    private boolean isServiceRunning(Class<?> serviceClass) {
/* 966 */
        ActivityManager manager = (ActivityManager) getSystemService("activity");
/* 967 */
        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
/* 968 */
            if (serviceClass.getName().equals(service.service.getClassName())) {
/* 969 */
                return true;
/*     */
            }
/*     */
        }
/* 972 */
        return false;
/*     */
    }
/*     */
}


/* Location:              C:\Users\jame\Desktop\gprintersdkv22.jar!\com\gprinter\service\GpPrintService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */