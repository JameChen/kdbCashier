package com.yiku.kdb_flat.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gprinter.aidl.GpService;
import com.gprinter.command.EscCommand;
import com.gprinter.command.EscCommand.ENABLE;
import com.gprinter.command.EscCommand.FONT;
import com.gprinter.command.EscCommand.JUSTIFICATION;
import com.gprinter.command.GpCom;
import com.gprinter.command.GpUtils;
import com.gprinter.command.LabelCommand;
import com.gprinter.io.GpDevice;
import com.gprinter.service.GpPrintService;
import com.nahuo.library.controls.LightPopDialog;
import com.nahuo.library.controls.LoadingDialog;
import com.nahuo.library.helper.FunctionHelper;
import com.nahuo.library.helper.MD5Utils;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.stat.StatService;
import com.yiku.kdb_flat.BWApplication;
import com.yiku.kdb_flat.R;
import com.yiku.kdb_flat.constant.Constant;
import com.yiku.kdb_flat.custom_controls.ViewHub;
import com.yiku.kdb_flat.di.module.HttpManager;
import com.yiku.kdb_flat.dialog.EditDiscountDialog;
import com.yiku.kdb_flat.dialog.ExchangeGoodsDialog;
import com.yiku.kdb_flat.dialog.PayCashDialog;
import com.yiku.kdb_flat.dialog.PayOnlineDialog;
import com.yiku.kdb_flat.dialog.SingleDialog;
import com.yiku.kdb_flat.eventbus.BusEvent;
import com.yiku.kdb_flat.eventbus.EventBusId;
import com.yiku.kdb_flat.model.ExchanegGoodBean;
import com.yiku.kdb_flat.model.bean.CodeBean;
import com.yiku.kdb_flat.model.bean.CreateProduceBean;
import com.yiku.kdb_flat.model.bean.MemBean;
import com.yiku.kdb_flat.model.bean.MenuBean;
import com.yiku.kdb_flat.model.bean.PayBean;
import com.yiku.kdb_flat.model.bean.ProdectBean;
import com.yiku.kdb_flat.model.bean.SaleDetailBean;
import com.yiku.kdb_flat.model.bean.ShopCartModel;
import com.yiku.kdb_flat.model.bean.ShopInfoModel;
import com.yiku.kdb_flat.model.bean.UserModel;
import com.yiku.kdb_flat.model.http.CommonSubscriber;
import com.yiku.kdb_flat.model.http.exception.ApiException;
import com.yiku.kdb_flat.model.http.response.KDBResponse;
import com.yiku.kdb_flat.ui.adapter.ShopCartApdapter;
import com.yiku.kdb_flat.ui.base.BaseAppCompatActivity;
import com.yiku.kdb_flat.utils.ListUtils;
import com.yiku.kdb_flat.utils.RxUtil;
import com.yiku.kdb_flat.utils.SpManager;
import com.yiku.kdb_flat.utils.UserInfoProvider;
import com.yiku.kdb_flat.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import de.greenrobot.event.EventBus;
import okhttp3.RequestBody;

public class MainActivity extends BaseAppCompatActivity implements ExchangeGoodsDialog.PopDialogListener, EditDiscountDialog.PopDialogListener, PayOnlineDialog.PopDialogListener, PayCashDialog.PopDialogListener, View.OnClickListener, ShopCartApdapter.APDOnClick {
    private static String TAG = MainActivity.class.getSimpleName();
    private EditText et_search;
    private ImageView iv_head_icon;
    private MainActivity Vthis = this;
    private TextView my_bluetool_info, tv_exit, tv_find, tv_cashier, tv_clear,
            tv_sale_list, tv_sale, tv_receivable, print_order_zk, tv_summary, tv_refresh_again,
            tv_settle, tv_refresh, tv_card_number, tv_amount_reduction, tv_member, tv_ware_house_list,
            tv_sale_summary, tv_ware_house_exchange, tv_version,
            tv_integral_summary, tv_integral;
    private View layout_left_shelter, layout_right_shelter;
    private RecyclerView mRecyclerView;
    private ShopCartApdapter shopCartApdapter;
    private RadioButton rb_cash_pay, rb_online_pay, rb_pos_pay;
    private RadioGroup rGroup;
    private List<ProdectBean.SellerUsersBean> sList;
    private View tv_translate, layout_right_content;
    private String Discount = "0";
    double payAmount;
    double PayableAmount;
    private String VipDiscount = "0.00";
    private boolean isSmallChange, isVip, SpDiscountFl, InnerBuyFl;
    private String pay = "0", discount = "0";
    private CreateProduceBean createProduceBean;
    private CheckBox cb_small_chang, cb_vip, cb_internal, cb_special_offer;
    private String ShoppingIDS = "";
    private List<Integer> ChangeShoppingIDS = new ArrayList<>();
    private String ordercode = "";
    private int pay_type = 1;
    private int check_count = 1;
    private PayOnlineDialog payOnlineDialog;
    private PayCashDialog payCashDialog;
    private LoadingDialog mloadingDialog;
    private int mPrinterIndex = 0;
    private static final int MAIN_QUERY_PRINTER_STATUS = 102;
    private static final int REQUEST_PRINT_RECEIPT = 101;
    private static final int REQUEST_ENABLE_BT = 3;
    private SaleDetailBean data = null;
    private boolean isSubmitOrder = false;
    private int BuyerUserID;
    private String memBerPhone = "";
    private String FreeAmount = "0";
    private boolean FreeAmountOpt;
    private ExchangeGoodsDialog exchangeGoodsDialog;
    private String payMethod = "";
    private List<CreateProduceBean.ProductsBean> Products;
    private List<ShopCartModel> shopCartModels;
    private int Point,TotalPoint;
    private boolean PointOpt;
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d("TAG", action);
            // GpCom.ACTION_DEVICE_REAL_STATUS 为广播的IntentFilter
            if (action.equals(GpCom.ACTION_DEVICE_REAL_STATUS)) {

                // 业务逻辑的请求码，对应哪里查询做什么操作
                int requestCode = intent.getIntExtra(GpCom.EXTRA_PRINTER_REQUEST_CODE, -1);
                // 判断请求码，是则进行业务操作
                if (requestCode == MAIN_QUERY_PRINTER_STATUS) {

                    int status = intent.getIntExtra(GpCom.EXTRA_PRINTER_REAL_STATUS, 16);
                    String str;
                    if (status == GpCom.STATE_NO_ERR) {
                        str = "打印机正常";
                    } else {
                        str = "打印机 ";
                        if ((byte) (status & GpCom.STATE_OFFLINE) > 0) {
                            str += "脱机";
                        }
                        if ((byte) (status & GpCom.STATE_PAPER_ERR) > 0) {
                            str += "缺纸";
                        }
                        if ((byte) (status & GpCom.STATE_COVER_OPEN) > 0) {
                            str += "打印机开盖";
                        }
                        if ((byte) (status & GpCom.STATE_ERR_OCCURS) > 0) {
                            str += "打印机出错";
                        }
                        if ((byte) (status & GpCom.STATE_TIMES_OUT) > 0) {
                            str += "查询超时";
                        }
                    }

                    Toast.makeText(getApplicationContext(), "打印机：" + mPrinterIndex + " 状态：" + str, Toast.LENGTH_SHORT)
                            .show();
                }
//                else if (requestCode == REQUEST_PRINT_LABEL) {
//                    int status = intent.getIntExtra(GpCom.EXTRA_PRINTER_REAL_STATUS, 16);
//                    if (status == GpCom.STATE_NO_ERR) {
//                        sendLabel();
//                    } else {
//                        Toast.makeText(MainActivity.this, "query printer status error", Toast.LENGTH_SHORT).show();
//                    }
//                }
                else if (requestCode == REQUEST_PRINT_RECEIPT) {
                    int status = intent.getIntExtra(GpCom.EXTRA_PRINTER_REAL_STATUS, 16);
                    if (status == GpCom.STATE_NO_ERR) {
                        sendReceipt();
                    } else {
                        Toast.makeText(Vthis, "检查打印机状态错误码", Toast.LENGTH_SHORT).show();
                    }
                }
            }
//            else if (action.equals(GpCom.ACTION_RECEIPT_RESPONSE)) {
//                if (--mTotalCopies > 0) {
//                    sendReceiptWithResponse();
//                }
//            } else if (action.equals(GpCom.ACTION_LABEL_RESPONSE)) {
//                byte[] data = intent.getByteArrayExtra(GpCom.EXTRA_PRINTER_LABEL_RESPONSE);
//                int cnt = intent.getIntExtra(GpCom.EXTRA_PRINTER_LABEL_RESPONSE_CNT, 1);
//                String d = new String(data, 0, cnt);
//                /**
//                 * 这里的d的内容根据RESPONSE_MODE去判断返回的内容去判断是否成功，具体可以查看标签编程手册SET
//                 * RESPONSE指令
//                 * 该sample中实现的是发一张就返回一次,这里返回的是{00,00001}。这里的对应{Status,######,ID}
//                 * 所以我们需要取出STATUS
//                 */
//                Log.d("LABEL RESPONSE", d);
//
//                if (--mTotalCopies > 0 && d.charAt(1) == 0x00) {
//                    sendLabelWithResponse();
//                }
            //  }
        }
    };

    private void sendReceipt() {
        //  EscCommand esc = new EscCommand();
//        esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
//        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);//设置为倍高倍宽
//        esc.addText(SpManager.getShopName(vThis) + "\n");
//        esc.addPrintAndLineFeed();
//
//        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);//取消倍高倍宽
//        esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
//        esc.addText("单号:" + kd_order.getOrderCode() + "\n");
//        esc.addText("日期:" + kd_order.getCreateTime() + "\n");
//        esc.addText("---------------------------\n");
//        esc.addText("商品\n");
//        for (SubmitOrderModel.OrderProductModel pm : kd_order.getProducts()) {
//            esc.addText(pm.getName() + "\n");
//            esc.addText(pm.getColor() + "   " + pm.getSize() + "   " + Utils.moneyFormat(pm.getPrice()) + "*" + pm.getQty() + "\n");
//            esc.addPrintAndLineFeed();
//        }
//        esc.addText("---------------------------\n");
//        esc.addText("数量:" + kd_order.getProductCount() + "     应收" + Utils.moneyFormat(kd_order.getProductAmount()) + "\n");
//        esc.addText("优惠:" + Utils.moneyFormat(kd_order.getDiscount()) + "     实收" + Utils.moneyFormat(ss_money) + "\n");
//        esc.addPrintAndLineFeed();
//        esc.addText("谢谢惠顾");
//        esc.addPrintAndLineFeed();
//        esc.addPrintAndFeedLines((byte) 8);
        if (data != null) {
            EscCommand esc = new EscCommand();
            esc.addInitializePrinter();
            //esc.addPrintAndFeedLines((byte) 2);
            esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);// 设置打印居中
            esc.addSelectPrintModes(FONT.FONTA, ENABLE.OFF, ENABLE.ON, ENABLE.ON, ENABLE.OFF);// 设置为倍高倍宽
            esc.addText(SpManager.getShopName(Vthis) + "\n"); // 打印文字
            esc.addPrintAndLineFeed();
        /* 打印文字 */
            esc.addSelectPrintModes(FONT.FONTA, ENABLE.OFF, ENABLE.OFF, ENABLE.OFF, ENABLE.OFF);// 取消倍高倍宽
            esc.addSelectJustification(JUSTIFICATION.LEFT);// 设置打印左对齐
            esc.addText("单号：" + data.getOrderCode() + "\n"); // 打印文字
            esc.addText("日期：" + data.getCreateTime() + "\n"); // 打印文字
            esc.addText("会员号：" + FunctionHelper.getStartPhone(memBerPhone) + "\n"); // 打印文字
            esc.addText("---------------------------\n");
            esc.addText("商品列表\n\n");
            if (!ListUtils.isEmpty(data.getProducts())) {
                for (SaleDetailBean.ProductsBean pm : data.getProducts()) {
                    esc.addText("#" + pm.getSku() + "#   " + pm.getColor() + "/" + pm.getSize() + "\n");
                    if (pm.isIsOnSale()) {
                        esc.addText("原价：￥" + Utils.moneyFormat(Double.parseDouble(pm.getOrgPrice())) + "*" + pm.getQty() + "\n");
                        esc.addText(pm.getGetDiscountPercentStr() + "：￥" + Utils.moneyFormat(Double.parseDouble(pm.getDiscountPrice())) + "*" + pm.getQty() + "      特价\n");
                    } else {
                        if (pm.isDiscount()) {
                            esc.addText("原价：￥" + Utils.moneyFormat(Double.parseDouble(pm.getOrgPrice())) + "*" + pm.getQty() + "\n");
                            esc.addText(pm.getGetDiscountPercentStr() + "：￥" + Utils.moneyFormat(Double.parseDouble(pm.getDiscountPrice())) + "*" + pm.getQty() + "\n");
                        } else {
                            esc.addText("价格：￥" + Utils.moneyFormat(Double.parseDouble(pm.getPrice())) + "*" + pm.getQty() + "\n");
                        }
                    }
                    if (pm.getPoint()>0)
                    esc.addText("      (抵扣积分："+pm.getPoint()+")");
                    esc.addPrintAndLineFeed();
                }
            }
            esc.addText("---------------------------\n");
//        esc.addText("数量:" + kd_order.getProductCount() + "     应收" + Utils.moneyFormat(kd_order.getProductAmount()) + "\n");
//        esc.addText("优惠:" + Utils.moneyFormat(kd_order.getDiscount()) + "     实收" + Utils.moneyFormat(ss_money) + "\n");
//        esc.addPrintAndLineFeed();
            String remark = "";
            if (data.isInnerBuyFl() && data.isSpDiscountFl()) {
                remark = "标识：内购/特殊优惠";
            } else if (!data.isInnerBuyFl() && data.isSpDiscountFl()) {
                remark = "标识：特殊优惠";
            } else if (data.isInnerBuyFl() && !data.isSpDiscountFl()) {
                remark = "标识：内购";
            } else if (!data.isInnerBuyFl() && !data.isSpDiscountFl()) {
                remark = "标识：无";
            }
            esc.addText(remark + "\n");
            if (!TextUtils.isEmpty(payMethod)) {
                esc.addText("支付方式：" + payMethod + "\n");
            } else {
                esc.addText("支付方式：" + "无\n");
            }
            esc.addText("数量：" + data.getProductCount());
            esc.addSetHorAndVerMotionUnits((byte) 4, (byte) 1);
            esc.addSetAbsolutePrintPosition((short) 4);
            esc.addText("应收：" + Utils.moneyFormat(Double.parseDouble(data.getProductAmount())));
            esc.addPrintAndLineFeed();
            esc.addText("优惠：" + data.getDiscount());
            esc.addSetHorAndVerMotionUnits((byte) 4, (byte) 1);
            esc.addSetAbsolutePrintPosition((short) 4);
            esc.addText("实收：" + Utils.moneyFormat(Double.parseDouble(data.getPayableAmount())));
            if (data.getPoint()>0){
                esc.addText("\n抵扣积分：" + data.getPoint());
            }

            // esc.addPrintAndLineFeed();
        /* 打印图片 */
//        esc.addText("Print bitmap!\n"); // 打印文字
//        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.gprinter);
//        //esc.addRastBitImage(b, 384, 0); // 打印图片
//
//		/* 打印一维条码 */
//        esc.addText("Print code128\n"); // 打印文字
//        esc.addSelectPrintingPositionForHRICharacters(HRI_POSITION.BELOW);//
//        // 设置条码可识别字符位置在条码下方
//        esc.addSetBarcodeHeight((byte) 60); // 设置条码高度为60点
//        esc.addSetBarcodeWidth((byte) 1); // 设置条码单元宽度为1
//        esc.addCODE128(esc.genCodeB("SMARNET")); // 打印Code128码
//        esc.addPrintAndLineFeed();

		/*
         * QRCode命令打印 此命令只在支持QRCode命令打印的机型才能使用。 在不支持二维码指令打印的机型上，则需要发送二维条码图片
		 */
//        esc.addText("Print QRcode\n"); // 打印文字
//        esc.addSelectErrorCorrectionLevelForQRCode((byte) 0x31); // 设置纠错等级
//        esc.addSelectSizeOfModuleForQRCode((byte) 3);// 设置qrcode模块大小
//        esc.addStoreQRCodeData("www.smarnet.cc");// 设置qrcode内容
//        esc.addPrintQRCode();// 打印QRCode
//        esc.addPrintAndLineFeed();
            // esc.addPrintAndLineFeed();
        /* 打印文字 */
            esc.addPrintAndFeedLines((byte) 3);
            esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);// 设置打印左对齐
            esc.addText("**** 谢谢惠顾 ****\r\n"); // 打印结束
            // 开钱箱

            esc.addPrintAndFeedLines((byte) 7);
            esc.addGeneratePlus(LabelCommand.FOOT.F5, (byte) 255, (byte) 255);
            Vector<Byte> datas = esc.getCommand(); // 发送数据
            byte[] bytes = GpUtils.ByteTo_byte(datas);
            String sss = Base64.encodeToString(bytes, Base64.DEFAULT);
            int rs;
            try {
                for (int i = 0; i < 2; i++) {
                    rs = mGpService.sendEscCommand(mPrinterIndex, sss);
                    GpCom.ERROR_CODE r = GpCom.ERROR_CODE.values()[rs];
                    if (r != GpCom.ERROR_CODE.SUCCESS) {
                        Toast.makeText(getApplicationContext(), GpCom.getErrorText(r), Toast.LENGTH_SHORT).show();
                    }
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                CrashReport.postCatchedException(e);
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            if (EventBus.getDefault() != null)
                EventBus.getDefault().unregister(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().post(BusEvent.getEvent(EventBusId.SEARCH_刷新广告));
        }
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Vthis = this;
        // 注册实时状态查询广播
        registerReceiver(mBroadcastReceiver, new IntentFilter(GpCom.ACTION_DEVICE_REAL_STATUS));
        /**
         * 票据模式下，可注册该广播，在需要打印内容的最后加入addQueryPrinterStatus()，在打印完成后会接收到
         * action为GpCom.ACTION_DEVICE_STATUS的广播，特别用于连续打印，
         * 可参照该sample中的sendReceiptWithResponse方法与广播中的处理
         **/
        registerReceiver(mBroadcastReceiver, new IntentFilter(GpCom.ACTION_RECEIPT_RESPONSE));
        /**
         * 标签模式下，可注册该广播，在需要打印内容的最后加入addQueryPrinterStatus(RESPONSE_MODE mode)
         * ，在打印完成后会接收到，action为GpCom.ACTION_LABEL_RESPONSE的广播，特别用于连续打印，
         * 可参照该sample中的sendLabelWithResponse方法与广播中的处理
         **/
        registerReceiver(mBroadcastReceiver, new IntentFilter(GpCom.ACTION_LABEL_RESPONSE));
        pay_type = 1;
        isSubmitOrder = false;
        mloadingDialog = new LoadingDialog(this);
        connection();
        tv_member = findKdbViewById(R.id.tv_member);
        tv_ware_house_list = findKdbViewById(R.id.tv_ware_house_list);
        tv_ware_house_list.setOnClickListener(this);
        layout_left_shelter = findViewById(R.id.layout_left_shelter);
        layout_left_shelter.setOnClickListener(this);
        layout_right_shelter = findViewById(R.id.layout_right_shelter);
        layout_right_shelter.setOnClickListener(this);
        cb_small_chang = findKdbViewById(R.id.cb_small_chang);
        cb_vip = findKdbViewById(R.id.cb_vip);
        cb_internal = findKdbViewById(R.id.cb_internal);
        cb_special_offer = findKdbViewById(R.id.cb_special_offer);
        tv_receivable = findKdbViewById(R.id.tv_receivable);
        print_order_zk = findKdbViewById(R.id.print_order_zk);
        tv_settle = findKdbViewById(R.id.tv_settle);
        tv_settle.setOnClickListener(this);
        tv_summary = findKdbViewById(R.id.tv_summary);
        print_order_zk.setOnClickListener(this);
        tv_card_number = findKdbViewById(R.id.tv_card_number);
        tv_amount_reduction = findKdbViewById(R.id.tv_amount_reduction);
        tv_sale_summary = findKdbViewById(R.id.tv_sale_summary);
        tv_card_number.setOnClickListener(this);
        tv_amount_reduction.setOnClickListener(this);
        tv_refresh_again = findKdbViewById(R.id.tv_refresh_again);
        tv_refresh_again.setOnClickListener(this);
        tv_translate = findKdbViewById(R.id.tv_translate);
        tv_translate.setOnClickListener(this);
        layout_right_content = findKdbViewById(R.id.layout_right_content);
        tv_exit = (TextView) findViewById(R.id.tv_exit);
        tv_exit.setOnClickListener(this);
        my_bluetool_info = (TextView) findViewById(R.id.tv_bluetooth);
        my_bluetool_info.setOnClickListener(this);
        tv_refresh = (TextView) findViewById(R.id.tv_refresh);
        tv_ware_house_exchange = (TextView) findViewById(R.id.tv_ware_house_exchange);
        tv_version = findKdbViewById(R.id.tv_version);
        tv_ware_house_exchange.setOnClickListener(this);
        tv_integral_summary = findKdbViewById(R.id.tv_integral_summary);
        tv_integral = findKdbViewById(R.id.tv_integral);
        tv_integral.setOnClickListener(this);
        tv_refresh.setOnClickListener(this);
        tv_find = (TextView) findViewById(R.id.tv_find);
        tv_clear = findKdbViewById(R.id.tv_clear);
        tv_sale_list = findKdbViewById(R.id.tv_sale_list);
        tv_sale_list.setOnClickListener(this);
        tv_sale = findKdbViewById(R.id.tv_sale);
        tv_sale.setOnClickListener(this);
        tv_clear.setOnClickListener(this);
        tv_cashier = findKdbViewById(R.id.tv_cashier);
        //tv_cashier.setOnClickListener(this);
        tv_find.setOnClickListener(this);
        rb_cash_pay = findKdbViewById(R.id.rb_cash_pay);
        rb_cash_pay.setChecked(true);
        rb_online_pay = findKdbViewById(R.id.rb_online_pay);
        rb_pos_pay = findKdbViewById(R.id.rb_pos_pay);
        rGroup = findKdbViewById(R.id.rGroup);
        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rb_cash_pay) {
                    pay_type = 1;
                } else if (checkedId == R.id.rb_online_pay) {
                    pay_type = 2;
                } else if (checkedId == R.id.rb_pos_pay) {
                    pay_type = 4;
                }
            }
        });
        et_search = (EditText) findViewById(R.id.et_search);
        //et_search.setInputType(InputType.TYPE_CLASS_NUMBER);
        et_search.setFocusable(true);
        et_search.setFocusableInTouchMode(true);
        et_search.requestFocus();
//        et_search.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View view, int i, KeyEvent keyEvent) {
//                if (i == KeyEvent.KEYCODE_ENTER) {
//                    FunctionHelper.hideSoftInput(Vthis);
//                    String scan = et_search.getText().toString().trim();
//                    et_search.setText("");
//                    et_search.setHint(scan + "");
//                    et_search.setHintTextColor(ContextCompat.getColor(Vthis, R.color.kdb_black));
//                    String scanTxt = et_search.getHint().toString().trim();
//                    if (TextUtils.isEmpty(scanTxt)) {
//                        ViewHub.showLongToast(Vthis, "条形码为空");
//                    } else {
//                        et_search.setFocusable(true);
//                        et_search.setFocusableInTouchMode(true);
//                        et_search.requestFocus();
//                        getScanData(scanTxt);
//                    }
//                }
//                return false;
//
//            }
//        });
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent keyEvent) {
                if (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    if (!isSubmitOrder) {
                        String scan = et_search.getText().toString().trim();
                        et_search.setText("");
                        et_search.setHint(scan + "");
                        et_search.setHintTextColor(ContextCompat.getColor(Vthis, R.color.kdb_gray));
                        String scanTxt = et_search.getHint().toString().trim();
                        if (TextUtils.isEmpty(scanTxt)) {
                            ViewHub.showLongToast(Vthis, "条形码为空");
                        } else {
                            et_search.setFocusable(true);
                            et_search.setFocusableInTouchMode(true);
                            et_search.requestFocus();
                            getScanData(scanTxt);
                        }
                    } else {
                        ViewHub.showLongToast(Vthis, "已点结账的订单无法继续添加商品，请重新刷新创建新订单！");
                    }
                }

                return true;
            }
        });
        cb_small_chang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cb_small_chang.isPressed()) {
                    isSmallChange = isChecked;
                    FreeAmount = "0";
                    //createOrderPreview(isSmallChange, isVip, ShoppingIDS);
                }
            }
        });
        cb_vip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cb_vip.isPressed()) {
                    if (cb_small_chang.isChecked()) {
                        cb_small_chang.setChecked(false);
                        isSmallChange = false;
                    }
                    FreeAmount = "0";
                    isVip = isChecked;
                    // createOrderPreview(isSmallChange, isVip, ShoppingIDS);
                }
            }
        });
        cb_internal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cb_internal.isPressed()) {
                    InnerBuyFl = isChecked;
                    createOrderPreview(ShoppingIDS, discount, pay);
                }
            }
        });
        cb_special_offer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cb_special_offer.isPressed()) {
                    SpDiscountFl = isChecked;
                    createOrderPreview(ShoppingIDS, discount, pay);
                }
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        shopCartApdapter = new ShopCartApdapter(Vthis);
        shopCartApdapter.setApdOnClick(this);
        mRecyclerView.setAdapter(shopCartApdapter);
        showTran(false);
        judeShelter();
        getMyUserInfo();
        getSHOP_INFO();
        if (tv_version != null)
            tv_version.setText("(版本-" + FunctionHelper.getVersionName(Vthis) + ")");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Vthis = this;
    }

    private void judeShelter() {
        if (isSubmitOrder) {
            layout_left_shelter.setVisibility(View.VISIBLE);
            layout_right_shelter.setVisibility(View.VISIBLE);
        } else {
            layout_left_shelter.setVisibility(View.GONE);
            layout_right_shelter.setVisibility(View.GONE);
        }
    }

    private void judeMember(boolean isShow) {
        if (!isShow) {
            if (tv_member != null) {
                tv_member.setVisibility(View.GONE);
            }
            if (tv_card_number != null)
                tv_card_number.setText("");
        } else {
            if (tv_member != null) {
                tv_member.setVisibility(View.VISIBLE);
            }
        }
    }

    private void getScanData(String scanCode) {
        addSubscribe(HttpManager.getInstance().getPinHuoNoCacheApi(TAG
        ).AddFromCode(scanCode)
                .compose(RxUtil.<KDBResponse<Object>>rxSchedulerHelper())
                .compose(RxUtil.<Object>handleResult())
                .subscribeWith(new CommonSubscriber<Object>(Vthis, true, "正在添加....") {
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        try {
                            et_search.setFocusable(false);
                            et_search.setFocusableInTouchMode(false);
                            et_search.clearFocus();
                            et_search.setEnabled(false);
                            SingleDialog.getInstance(Vthis).setContent(e.getMessage())
                                    .setPositive(new SingleDialog.PopDialogListener() {
                                        @Override
                                        public void onPopDialogButtonClick(int ok_cancel, SingleDialog.DialogType type) {
                                            et_search.setFocusable(true);
                                            et_search.setFocusableInTouchMode(true);
                                            et_search.setEnabled(true);
                                            et_search.requestFocus();
                                        }
                                    }).showDialog();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }


                    @Override
                    public void onNext(Object jPayUser) {
                        super.onNext(jPayUser);
                        getProdectList();
                    }

                }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT) {
            // When the request to enable Bluetooth returns
            if (resultCode == Activity.RESULT_OK) {
                // Bluetooth is now enabled, so set up a chat session
            } else {
                ViewHub.showLongToast(Vthis, "蓝牙异常");
            }
        }
        if (resultCode == RESULT_OK) {

            if (requestCode == SallerActivity.RequestCode_01) {
                if (data != null) {
                    ProdectBean.SellerUsersBean sellerUsersBean = (ProdectBean.SellerUsersBean) data.getSerializableExtra(SallerActivity.Extra_Bean);
                    if (sellerUsersBean != null) {
                        if (tv_sale != null)
                            tv_sale.setText(sellerUsersBean.getName());
                    }
                }
            }
        }
    }

    private void showTran(boolean isShowTran) {
        if (isShowTran) {
            layout_right_content.setVisibility(View.VISIBLE);
            tv_translate.setVisibility(View.GONE);
        } else {
            layout_right_content.setVisibility(View.GONE);
            tv_translate.setVisibility(View.VISIBLE);
        }
    }

    public void onEventMainThread(BusEvent event) {

    }

    @Override
    public void onGetReturnOrder(String scanCode, String orderCode, String itemCode, String mobile) {
        getReturnOrder(scanCode, orderCode, itemCode, mobile);
    }

    @Override
    public void onOkAndRemove(ExchanegGoodBean.ItemListBean item) {
        addExchangGoods(item);
    }

    private void addExchangGoods(final ExchanegGoodBean.ItemListBean shopcart) {
        try {
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            jsonObject.put("itemID", shopcart.getItemID());
            jsonObject.put("OrderProductID", shopcart.getOrderProductID());
            jsonObject.put("TypeID", Constant.SHOP_TYPEID.TypeID_Exchang_Goods);
            jsonObject.put("ActionType", 1);
            JSONObject jo = new JSONObject();
            jo.put("Color", shopcart.getColor());
            jo.put("Size", shopcart.getSize());
            jo.put("Qty", shopcart.getExchangQty());
            jsonArray.put(jo);
            jsonObject.put("products", jsonArray);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), jsonObject.toString());
            addSubscribe(HttpManager.getInstance().getPinHuoNoCacheApi(TAG)
                    .upadteShoppingCart(body).compose(RxUtil.<KDBResponse<Object>>rxSchedulerHelper())
                    .compose(RxUtil.handleResult())
                    .subscribeWith(new CommonSubscriber<Object>(Vthis, true, "正在添加换货....") {
                        @Override
                        public void onNext(Object o) {
                            super.onNext(o);
//                            if (exchangeGoodsDialog != null)
//                                exchangeGoodsDialog.reMoveItem(shopcart);
                            finishaExchangeDialog();
                            showTran(false);
                            String txt = shopcart.getMobile();
                            getProdectListAndShowMember(txt);

                        }
                    }));
        } catch (Exception e) {
            e.printStackTrace();
            CrashReport.postCatchedException(e);
        }
    }

    private void getReturnOrder(String scanCode, String orderCode, String itemCode, String mobile) {
        Map<String, Object> map = new HashMap<>();
        map.put("scanCode", scanCode);
        map.put("orderCode", orderCode);
        map.put("itemCode", itemCode);
        map.put("mobile", mobile);
        addSubscribe(HttpManager.getInstance().getPinHuoNoCacheApi(TAG
        ).GetReturnOrder(map)
                .compose(RxUtil.<KDBResponse<ExchanegGoodBean>>rxSchedulerHelper())
                .compose(RxUtil.<ExchanegGoodBean>handleResult())
                .subscribeWith(new CommonSubscriber<ExchanegGoodBean>(Vthis, true, "正在加载换货数据....") {
                    @Override
                    public void onNext(ExchanegGoodBean bean) {
                        super.onNext(bean);
                        if (exchangeGoodsDialog != null) {
                            exchangeGoodsDialog.setGoodBean(bean);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        try {
                            if (exchangeGoodsDialog != null) {
                                exchangeGoodsDialog.setGoodBean(null);
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                }));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_ware_house_exchange:
                exchangeGoodsDialog = ExchangeGoodsDialog.getInstance(Vthis).setOnExchangeGoodsOncClick(this);
                exchangeGoodsDialog.showDialog();
                break;
            case R.id.tv_ware_house_list:
                startActivity(new Intent(Vthis, WarehouseManageActivity.class));
                break;
            case R.id.tv_refresh_again:
            case R.id.tv_refresh:
                getProdectListAndHideMember();
                break;
            case R.id.tv_settle:
                if (isSubmitOrder) {
                    goToPay(pay_type);
                } else {
                    submitOrder(pay_type, ShoppingIDS, discount, pay);
                }
                break;
            case R.id.tv_integral:
                if (PointOpt) {
                    EditDiscountDialog.getInstance(Vthis).setType(EditDiscountDialog.TYPE_INTEGRAL).setPositive(this).showDialog();
                } else {
                    try {
                        ViewHub.showOkDialog(this, "提示", "积分不可修改", "知道了",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.print_order_zk:
                EditDiscountDialog.getInstance(Vthis).setType(EditDiscountDialog.TYPE_DISCOUNT).setPositive(this).showDialog();
                break;
            case R.id.tv_card_number:
                EditDiscountDialog.getInstance(Vthis).setType(EditDiscountDialog.TYPE_CARD_NO).setPositive(this).showDialog();

                break;
            case R.id.tv_amount_reduction:
                if (FreeAmountOpt) {
                    EditDiscountDialog.getInstance(Vthis).setType(EditDiscountDialog.TYPE_REDUE).setPositive(this).showDialog();

                } else {
                    try {
                        ViewHub.showOkDialog(this, "提示", "不可修改", "知道了",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.tv_translate:
                goToOrderPreView();
                break;
            case R.id.tv_sale:
                if (!ListUtils.isEmpty(sList)) {
                    Intent sintent = new Intent(this, SallerActivity.class);
                    sintent.putExtra(SallerActivity.Extra_List, (Serializable) sList);
                    startActivityForResult(sintent, SallerActivity.RequestCode_01);
                } else {
                    ViewHub.showLongToast(Vthis, "列表为空");
                }
                break;
            case R.id.tv_sale_list:
                Intent intent1 = new Intent(Vthis, SaleLogActivity.class);
                startActivity(intent1);
                break;
            case R.id.tv_clear:
                ViewHub.showOkDialog(this, "提示", "您确定要删除全部商品吗？", getString(android.R.string.ok),
                        getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (shopCartApdapter != null) {
                                    List<ShopCartModel> data = shopCartApdapter.getData();
                                    delData(data);
                                }
                            }
                        });

                break;
            case R.id.tv_find:
                //查找款
                String scanTxt = et_search.getText().toString().trim();
                if (TextUtils.isEmpty(scanTxt)) {
                    ViewHub.showLongToast(Vthis, "条形码为空");
                } else {
                    getScanData(scanTxt);
                }
                break;
            case R.id.tv_bluetooth:
                startActivity(new Intent(Vthis, BlueToothActivity.class));
                break;
            case R.id.tv_exit:
                ViewHub.showLightPopDialog(this, getString(R.string.dialog_title),
                        getString(R.string.shopset_exit_confirm), "取消", "退出登录", new LightPopDialog.PopDialogListener() {
                            @Override
                            public void onPopDialogButtonClick(int which) {
                                UserInfoProvider.exitLogin(Vthis);
                                finish();
                            }
                        });
                break;
        }
    }


    private GpService mGpService;
    private PrinterServiceConnection conn = null;

    @Override
    public void OnAPDClick(final int type, final ShopCartModel item) {
        switch (type) {
            case ShopCartApdapter.TYPE_PLUS:
            case ShopCartApdapter.TYPE_REDUCE:
                updateData(item, type);
                break;
            case ShopCartApdapter.TYPE_DEL:
                ViewHub.showOkDialog(this, "提示", "您确定要删除该商品吗？", getString(android.R.string.ok),
                        getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                delData(item, type);
                            }
                        });
                break;
        }

    }

    @Override
    public void OnChangePoints() {
        if (shopCartApdapter != null) {
            shopCartApdapter.notifyDataSetChanged();
            List<ShopCartModel> data = shopCartApdapter.getData();
            if (ChangeShoppingIDS == null)
                ChangeShoppingIDS = new ArrayList<>();
            else
                ChangeShoppingIDS.clear();
            if (!ListUtils.isEmpty(data)) {
                for (ShopCartModel model : data) {
                    if (model.isCheck()) {
                        ChangeShoppingIDS.add(model.getID());
                    }
                }

            }
            if (tv_translate!=null){
                if (tv_translate.getVisibility()!=View.VISIBLE){
                    createOrderPreview(ShoppingIDS, discount, pay);
                }
            }
        }
    }

    private void delData(final ShopCartModel shopcart, final int type) {
        try {
            if (shopcart != null) {
//                JSONObject jsonObject=new JSONObject();
//                jsonObject.put("ids",shopcart.getID() + "");
//                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), jsonObject.toString());
                addSubscribe(HttpManager.getInstance().getPinHuoNoCacheApi(TAG
                ).deleteShoppingCart(shopcart.getID() + "")
                        .compose(RxUtil.<KDBResponse<Object>>rxSchedulerHelper())
                        .compose(RxUtil.<Object>handleResult())
                        .subscribeWith(new CommonSubscriber<Object>(Vthis, true, "正在删除....") {
                            @Override
                            public void onNext(Object jPayUser) {
                                super.onNext(jPayUser);
                                showTran(false);
                                if (shopCartApdapter != null)
                                    shopCartApdapter.upateData(shopcart, type);
                            }

                        }));
            } else {
                ViewHub.showLongToast(Vthis, "商品不能为空");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void delData(final List<ShopCartModel> data) {
        try {
            if (!ListUtils.isEmpty(data)) {
                List<Integer> iList = new ArrayList<>();
                for (ShopCartModel bean : data) {
                    iList.add(bean.getID());
                }
                String ids = iList.toString().substring(1, iList.toString().length() - 1);
//                JSONObject jsonObject=new JSONObject();
//                jsonObject.put("ids",shopcart.getID() + "");
//                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), jsonObject.toString());
                addSubscribe(HttpManager.getInstance().getPinHuoNoCacheApi(TAG
                ).deleteShoppingCart(ids)
                        .compose(RxUtil.<KDBResponse<Object>>rxSchedulerHelper())
                        .compose(RxUtil.<Object>handleResult())
                        .subscribeWith(new CommonSubscriber<Object>(Vthis, true, "正在清除....") {
                            @Override
                            public void onNext(Object jPayUser) {
                                super.onNext(jPayUser);
                                showTran(false);
                                getProdectListAndHideMember();
                            }

                        }));
            } else {
                ViewHub.showLongToast(Vthis, "商品不能为空");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateData(final ShopCartModel shopcart, final int ActionType) {

        try {
            String mess = "";
            switch (ActionType) {
                case ShopCartApdapter.TYPE_PLUS:
                    mess = "正在添加中...";
                    break;
                case ShopCartApdapter.TYPE_REDUCE:
                    mess = "正在删减中...";
                    break;
            }
            if (shopcart != null) {
                JSONObject jsonObject = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                jsonObject.put("itemID", shopcart.getItemID());
                jsonObject.put("ActionType", 2);
                JSONObject jo = new JSONObject();
                jo.put("Color", shopcart.getColor());
                jo.put("Size", shopcart.getSize());
                jo.put("Qty", shopcart.getQty());
                jsonArray.put(jo);
                jsonObject.put("products", jsonArray);
                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), jsonObject.toString());
                addSubscribe(HttpManager.getInstance().getPinHuoNoCacheApi(TAG)
                        .upadteShoppingCart(body).compose(RxUtil.<KDBResponse<Object>>rxSchedulerHelper())
                        .compose(RxUtil.handleResult())
                        .subscribeWith(new CommonSubscriber<Object>(Vthis, true, mess) {
                            @Override
                            public void onNext(Object o) {
                                super.onNext(o);
                                showTran(false);
                                if (shopCartApdapter != null)
                                    shopCartApdapter.upateData(shopcart, ActionType);
                            }
                        }));
            } else {
                ViewHub.showLongToast(Vthis, "商品不能为空");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onlinePayClick(int type, String txt) {
        if (type == PayOnlineDialog.TYPE_CHECK) {
            //取消
            check_count = 1;
            CheckOrderPayStatu();
        } else if (type == PayOnlineDialog.TYPE_PAY) {
            //扫描给钱
            goCodePay4ShopTrade(pay_type, txt);
        }
    }

    @Override
    public void onPopDialogButtonClick(int ok_cancel, int type, String txt) {
        if (ok_cancel == EditDiscountDialog.BUTTON_POSITIVIE) {
            switch (type) {
                case EditDiscountDialog.TYPE_INTEGRAL:
                    int number = 0;
                    try {
                        number = Integer.parseInt(txt);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        number = 0;
                    } finally {
                        createOrderPreview(ShoppingIDS, discount, pay,number);
                    }

                    break;
                case EditDiscountDialog.TYPE_DISCOUNT:
                    double d = 0;
                    try {
                        d = Double.parseDouble(txt);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        d = 0;
                    } finally {
                        String discount = d + "";
                        FreeAmount = "0";
                        createOrderPreview(ShoppingIDS, discount);
                    }

                    break;
                case EditDiscountDialog.TYPE_CARD_NO:
                    //会员号
                    if (tv_card_number != null) {
                        tv_card_number.setText(txt + "");
                    }
                    if (!TextUtils.isEmpty(txt))
                        getMemBerInfo(txt);
                    break;
                case EditDiscountDialog.TYPE_REDUE:
                    //减免
                    if (tv_amount_reduction != null) {
                        tv_amount_reduction.setText(txt + "");
                    }
                    FreeAmount = txt;
                    createOrderPreview(ShoppingIDS, "0", "0");
                    break;
            }
        }
    }

    @Override
    public void onCashPayClick() {
        //现金。pos机器
        goCodePay4ShopTrade(pay_type);
    }


    class PrinterServiceConnection implements ServiceConnection {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            // Log.i(DEBUG_TAG, "onServiceDisconnected() called");
            mGpService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mGpService = GpService.Stub.asInterface(service);
        }
    }

    private void connection() {
        conn = new PrinterServiceConnection();
        // Log.i(DEBUG_TAG, "connection");
        Intent intent = new Intent(this, GpPrintService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE); // bindService
    }

    public void printReceiptClicked() {
        try {
            if (mGpService.getPrinterConnectStatus(BWApplication.getInstance().PrinterId) == GpDevice.STATE_CONNECTED) {
                int type = mGpService.getPrinterCommandType(mPrinterIndex);
                if (type == GpCom.ESC_COMMAND) {
                    mGpService.queryPrinterStatus(mPrinterIndex, 1000, REQUEST_PRINT_RECEIPT);
                } else {
                    Toast.makeText(this, "Printer is not receipt mode", Toast.LENGTH_SHORT).show();
                }
            } else {
                ViewHub.showOkDialog(Vthis, "蓝牙打印机未连接", "连接打印机？", "确定", "取消", new ViewHub.OkDialogListener() {
                    @Override
                    public void onPositiveClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Vthis, BlueToothActivity.class));
                    }

                    @Override
                    public void onNegativeClick(DialogInterface dialog, int which) {

                    }
                });
            }
            getProdectListAndHideMember();
        } catch (RemoteException e1) {
            e1.printStackTrace();
        }
    }

    //    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        Configuration configuration = getResources().getConfiguration();
//        Log.e("text", "onConfigurationChanged configuration.keyboard ==" + configuration.keyboard + " " + "configuration.keyboardHidden ==" + configuration.keyboardHidden);
//   // ViewHub.showLongToast(this,"onConfigurationChanged configuration.keyboard ==" + configuration.keyboard + " " + "configuration.keyboardHidden ==" + configuration.keyboardHidden);
//        et_search.setText("onConfigurationChanged configuration.keyboard ==" + configuration.keyboard + " " + "configuration.keyboardHidden ==" + configuration.keyboardHidden);
//    }
    private String blue_conect = "蓝牙连接状态(未连接)";

    @Override
    protected void onResume() {
        super.onResume();
        try {
            mPrinterIndex = BWApplication.PrinterId;
            if (mGpService != null) {
                if (mGpService.getPrinterConnectStatus(BWApplication.getInstance().PrinterId) == GpDevice.STATE_CONNECTED) {
                    if (my_bluetool_info != null) {
                        my_bluetool_info.setText("蓝牙连接状态：已连接");
                    }
                    blue_conect = "蓝牙连接状态(已连接)";

                } else {
                    if (my_bluetool_info != null) {
                        my_bluetool_info.setText("蓝牙连接状态：未连接");
                    }
                    blue_conect = "蓝牙连接状态(未连接)";
                }
            } else {
                if (my_bluetool_info != null) {
                    my_bluetool_info.setText("蓝牙连接状态：未连接");
                }
                blue_conect = "蓝牙连接状态(未连接)";
            }
//            if (tv_bluetooth!=null)
//                tv_bluetooth.setText(blue_conect);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (conn != null) {
                unbindService(conn); // unBindService
            }
            finishPayDialog();
            finishaExchangeDialog();
        } catch (Exception e) {
        }
    }

    private void finishaExchangeDialog() {
        if (exchangeGoodsDialog != null) {
            if (exchangeGoodsDialog.isShowing())
                exchangeGoodsDialog.dismiss();
            exchangeGoodsDialog = null;
        }
    }

    private void getMainMenus() {
        addSubscribe(HttpManager.getInstance().getPinHuoNoCacheApi(TAG
        ).getMenusV2()
                .compose(RxUtil.<KDBResponse<MenuBean>>rxSchedulerHelper())
                .compose(RxUtil.<MenuBean>handleResult())
                .subscribeWith(new CommonSubscriber<MenuBean>(Vthis) {
                    @Override
                    public void onNext(MenuBean menuBean) {
                        super.onNext(menuBean);
                        if (menuBean != null) {
                            if (tv_cashier != null)
                                tv_cashier.setText("欢迎，" + menuBean.getUserName());
                            SpManager.setUserId(Vthis, menuBean.getUserID());
                            if (menuBean.getPageElement() != null) {
                                SpManager.setPurchase(Vthis, menuBean.getPageElement().isIsShowBuyingPrice());
                                SpManager.setRetail(Vthis, menuBean.getPageElement().isIsShowRetailPrice());
                                SpManager.setEditTitle(Vthis, menuBean.getPageElement().isIsEditableTitle());
                                SpManager.setEditPrice(Vthis, menuBean.getPageElement().isIsEditablePrice());
                                SpManager.setIsadd_Group(Vthis, menuBean.getPageElement().isIsAddGroup());
                            }
                        }
                    }


                }));
    }

    /**
     * 获取用户数据
     *
     * @author James Chen
     * @create time in 2018/5/11 16:30
     */
    private void getMyUserInfo() {
        addSubscribe(HttpManager.getInstance().getPinHuoNoCacheApi(TAG
        ).getMyUserInfo()
                .compose(RxUtil.<KDBResponse<UserModel>>rxSchedulerHelper())
                .compose(RxUtil.<UserModel>handleResult())
                .subscribeWith(new CommonSubscriber<UserModel>(Vthis) {
                    @Override
                    public void onNext(UserModel userinfo) {
                        super.onNext(userinfo);
                        if (userinfo != null) {
                            SpManager.setUserInfo(Vthis, userinfo);
                            if (userinfo != null) {
                                if (tv_cashier != null)
                                    tv_cashier.setText("收银员：" + userinfo.getUserName());
                            }
//                            switch (userinfo.getStatuID()) {
//                                case 3:
//                                    ViewHub.showLongToast(vThis, "禁止登录");
//                                    UserInfoProvider.exitApp(vThis);
//                                    finish();
//                                    break;
//                                case 2:
//                                    ViewHub.showLongToast(vThis, "账户未通过验证");
//                                    break;
//                            }
//                            EventBus.getDefault().post(BusEvent.getEvent(EventBusId.REFRESH_MEFRAGMENT));
//                            EventBus.getDefault().post(BusEvent.getEvent(EventBusId.REFRESH_SHOP_CART));
//                            //EventBus.getDefault().post(BusEvent.getEvent(EventBusId.PINHUO_AD_REFRESH));
//                            EventBus.getDefault().post(BusEvent.getEvent(EventBusId.REFRESH_GROUP_DETAIL_NEW));
                        }
                    }


                }));
    }

    private void getProdectListAndHideMember() {
        addSubscribe(HttpManager.getInstance().getPinHuoNoCacheApi(TAG
        ).GetProdectList()
                .compose(RxUtil.<KDBResponse<ProdectBean>>rxSchedulerHelper())
                .compose(RxUtil.<ProdectBean>handleResult())
                .subscribeWith(new CommonSubscriber<ProdectBean>(Vthis, true, "加载数据中....") {

                    @Override
                    public void onNext(ProdectBean bean) {
                        super.onNext(bean);
                        FreeAmount = "0";
                        Point = 0;
                        setCbInit();
                        showFree();
                        showTran(false);
                        isSubmitOrder = false;
                        judeShelter();
                        judeMember(false);
                        BuyerUserID = 0;
                        List<ShopCartModel> data = new ArrayList<>();
                        if (bean != null) {
                            if (!ListUtils.isEmpty(bean.getProducts()))
                                data.addAll(bean.getProducts());
                            shopCartApdapter.setNewData(data);
                            shopCartApdapter.notifyDataSetChanged();
                            sList = bean.getSellerUsers();
                            judeSaller(sList);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        try {
                            ViewHub.showOkDialog(Vthis, "提示", e.getMessage(), "知道了", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }));
    }

    private void getProdectListAndShowMember(final String txt) {
        addSubscribe(HttpManager.getInstance().getPinHuoNoCacheApi(TAG
        ).GetProdectList()
                .compose(RxUtil.<KDBResponse<ProdectBean>>rxSchedulerHelper())
                .compose(RxUtil.<ProdectBean>handleResult())
                .subscribeWith(new CommonSubscriber<ProdectBean>(Vthis, true, "加载数据中....") {

                    @Override
                    public void onNext(ProdectBean bean) {
                        super.onNext(bean);
                        FreeAmount = "0";
                        Point = 0;
                        setCbInit();
                        showFree();
                        showTran(false);
                        isSubmitOrder = false;
                        judeShelter();
                        //judeMember(false);
                        BuyerUserID = 0;
                        List<ShopCartModel> data = new ArrayList<>();
                        if (bean != null) {
                            if (!ListUtils.isEmpty(bean.getProducts()))
                                data.addAll(bean.getProducts());
                            shopCartApdapter.setNewData(data);
                            shopCartApdapter.notifyDataSetChanged();
                            sList = bean.getSellerUsers();
                            judeSaller(sList);
                        }
                        if (!TextUtils.isEmpty(txt)) {
                            if (tv_card_number != null) {
                                tv_card_number.setText(txt + "");
                            }
                            getMemBerInfo(txt);
                        } else {
                            judeMember(false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        try {
                            ViewHub.showOkDialog(Vthis, "提示", e.getMessage(), "知道了", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }));
    }

    private void getProdectList() {
        addSubscribe(HttpManager.getInstance().getPinHuoNoCacheApi(TAG
        ).GetProdectList()
                .compose(RxUtil.<KDBResponse<ProdectBean>>rxSchedulerHelper())
                .compose(RxUtil.<ProdectBean>handleResult())
                .subscribeWith(new CommonSubscriber<ProdectBean>(Vthis, true, "加载数据中....") {

                    @Override
                    public void onNext(ProdectBean bean) {
                        super.onNext(bean);
                        setCbInit();
                        Point = 0;
                        FreeAmount = "0";
                        showFree();
                        showTran(false);
                        isSubmitOrder = false;
                        judeShelter();
                        List<ShopCartModel> data = new ArrayList<>();
                        if (bean != null) {
                            if (!ListUtils.isEmpty(bean.getProducts()))
                                data.addAll(bean.getProducts());
                            shopCartApdapter.setNewData(data);
                            shopCartApdapter.notifyDataSetChanged();
                            sList = bean.getSellerUsers();
                            judeSaller(sList);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        try {
                            ViewHub.showOkDialog(Vthis, "提示", e.getMessage(), "知道了", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }));
    }

    private void goToOrderPreView() {
        if (shopCartApdapter != null) {
            List<ShopCartModel> data = shopCartApdapter.getData();
            List<Integer> iList = new ArrayList<>();
            if (ChangeShoppingIDS == null)
                ChangeShoppingIDS = new ArrayList<>();
            else
                ChangeShoppingIDS.clear();
            if (!ListUtils.isEmpty(data)) {
                for (ShopCartModel model : data) {
                    if (model.getQty() > 0) {
                        iList.add(model.getID());
                    }
                    if (model.isCheck()) {
                        ChangeShoppingIDS.add(model.getID());
                    }
                }
                ShoppingIDS = iList.toString().substring(1, iList.toString().length() - 1);
                Point = 0;
                createOrderPreview(ShoppingIDS, "0", "0");
            }
        }
    }

    private void submitOrder(final int pay_type, String shopcartIDS,
                             String discount, String PayableAmount) {

//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("shoppingIDS", shopcartIDS);
//        params.put("discount", discount + "");
//        params.put("PayableAmount", PayableAmount + "");
//        params.put("SellerUserID", SpManager.getSellerUsersId(Vthis)+"");
//        params.put("OrderCode", "");
        //SaveOrder(shopcartIDS,discount+"",PayableAmount+"",SpManager.getSellerUsersId(Vthis)+"","")
        addSubscribe(HttpManager.getInstance().getPinHuoNoCacheApi(TAG
        ).SaveOrder(FreeAmount, BuyerUserID, shopcartIDS, discount + "", PayableAmount + "", SpManager.getSellerUsersId(Vthis) + "", "", InnerBuyFl, SpDiscountFl, Point,ChangeShoppingIDS)
                .compose(RxUtil.<KDBResponse<SaleDetailBean>>rxSchedulerHelper())
                .compose(RxUtil.<SaleDetailBean>handleResult())
                .subscribeWith(new CommonSubscriber<SaleDetailBean>(Vthis, true, "正在结算...") {
                    @Override
                    public void onNext(SaleDetailBean bean) {
                        super.onNext(bean);
                        isSubmitOrder = true;
                        judeShelter();
                        data = bean;
                        if (bean != null) {
                            ordercode = bean.getOrderCode();
                        }
                        goToPay(pay_type);
                    }
                }));
    }

    private void goToPay(int pay_type) {
        Properties prop = new Properties();
        prop.setProperty("pay_type", pay_type + "");
        StatService.trackCustomKVEvent(this, "pay_click", prop);
        switch (pay_type) {
            case 4:
            case 1:
                if (payCashDialog != null) {
                    if (payCashDialog.isShowing())
                        payCashDialog.dismiss();
                    payCashDialog = null;
                }
                payCashDialog = PayCashDialog.getInstance(Vthis).setPayType(pay_type).setPay(payAmount + "").setCashPayOncClick(Vthis);
                payCashDialog.showDialog();
                break;
            case 2:
                if (payOnlineDialog != null) {
                    if (payOnlineDialog.isShowing())
                        payOnlineDialog.dismiss();
                    payOnlineDialog = null;
                }
                payOnlineDialog = PayOnlineDialog.getInstance(Vthis).setPay(payAmount + "").setOnlinePayOncClick(Vthis);
                payOnlineDialog.showDialog();
                break;
        }
    }

    private void goCodePay4ShopTrade(final int pay_type, String payCode) {
        long time = System.currentTimeMillis();
        Map<String, Object> params = new HashMap<>();
        if (pay_type == 2) {
            params.put("payCode", payCode);
        }
        params.put("orderCode", ordercode);
        params.put("payableAmount", payAmount);
        params.put("onceStr", time + "");
        params.put("payType", pay_type + "");
        params.put("sign", MD5Utils.encrypt32bit(ordercode + SpManager.getUserId(Vthis) + time + pay_type + payAmount));
        //.goCodePay4ShopTrade("", ordercode, payAmount + "", time + "", pay_type + "",
        //  MD5Utils.encrypt32bit(ordercode + SpManager.getUserId(Vthis) + time + pay_type + payAmount))
        addSubscribe(HttpManager.getInstance().getPinHuoNoCacheApi(TAG
        ).goCodePay4ShopTrade(params)
                .compose(RxUtil.<KDBResponse<CodeBean>>rxSchedulerHelper())
                .compose(RxUtil.<CodeBean>handleResult())
                .subscribeWith(new CommonSubscriber<CodeBean>(Vthis, true, "正在结账中...") {

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        try {
                            if (e != null)
                                ViewHub.showOkDialog(Vthis, "提示", e.getMessage() + "", "关闭");
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(CodeBean codeBean) {
                        super.onNext(codeBean);
                        if (codeBean != null) {
                            payMethod = codeBean.getPayType();
                            if (codeBean.getAction().equals("支付成功")) {
                                finishPayDialog();
                                ViewHub.showLongToast(Vthis, "付款成功,正在打印小票");
                                printReceiptClicked();
//                                ViewHub.showOkDialog(Vthis, "付款成功", "已付款成功，是否打印小票？", "打印", "不了", new ViewHub.OkDialogListener() {
//                                    @Override
//                                    public void onPositiveClick(DialogInterface dialog, int which) {
//
//                                        printReceiptClicked();
//
//                                    }
//
//                                    @Override
//                                    public void onNegativeClick(DialogInterface dialog, int which) {
//                                        getProdectListAndHideMember();
//                                    }
//                                });
                            } else {
                                check_count = 1;
                                CheckOrderPayStatu();
                            }
                        }
                    }
                }));
    }

    private void goCodePay4ShopTrade(int pay_type) {
        long time = System.currentTimeMillis();
        Map<String, Object> params = new HashMap<>();
        params.put("orderCode", ordercode);
        params.put("payableAmount", payAmount);
        params.put("onceStr", time + "");
        params.put("payType", pay_type + "");
        params.put("sign", MD5Utils.encrypt32bit(ordercode + SpManager.getUserId(Vthis) + time + pay_type + payAmount));
        //.goCodePay4ShopTrade("", ordercode, payAmount + "", time + "", pay_type + "",
        //  MD5Utils.encrypt32bit(ordercode + SpManager.getUserId(Vthis) + time + pay_type + payAmount))
        addSubscribe(HttpManager.getInstance().getPinHuoNoCacheApi(TAG
        ).goCodePay4ShopTrade(params)
                .compose(RxUtil.<KDBResponse<CodeBean>>rxSchedulerHelper())
                .compose(RxUtil.<CodeBean>handleResult())
                .subscribeWith(new CommonSubscriber<CodeBean>(Vthis, true, "正在结账中...") {

                    @Override
                    public void onNext(CodeBean codeBean) {
                        super.onNext(codeBean);
                        if (codeBean != null) {
                            payMethod = codeBean.getPayType();
                        }
                        check_count = 1;
                        CheckOrderPayStatu();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (e != null)
                            ViewHub.showOkDialog(Vthis, "提示", e.getMessage() + "", "关闭");
                    }
                }));
    }

    private void finishPayDialog() {
        if (payOnlineDialog != null) {
            if (payOnlineDialog.isShowing())
                payOnlineDialog.dismiss();
            payOnlineDialog = null;
        }
        if (payCashDialog != null) {
            if (payCashDialog.isShowing())
                payCashDialog.dismiss();
            payCashDialog = null;
        }
    }

    private void CheckOrderPayStatu() {
        if (mloadingDialog != null) {
            mloadingDialog.start("检查订单第" + check_count + "次中...");
        } else {
            mloadingDialog = new LoadingDialog(Vthis);
            mloadingDialog.start("检查订单第" + check_count + "次中...");
        }
        addSubscribe(HttpManager.getInstance().getPinHuoNoCacheApi(TAG
        ).CheckOrderPayStatu(ordercode)
                .compose(RxUtil.<KDBResponse<PayBean>>rxSchedulerHelper())
                .compose(RxUtil.<PayBean>handleResult())
                .subscribeWith(new CommonSubscriber<PayBean>(Vthis) {

                    @Override
                    public void onNext(PayBean payBean) {
                        super.onNext(payBean);
                        if (payBean != null) {
                            if (payBean.getStatu().equals("已完成")) {
                                loadFinish();
                                ViewHub.showLongToast(Vthis, "付款成功,正在打印小票");
                                printReceiptClicked();
//                                ViewHub.showOkDialog(Vthis, "付款成功", "已付款成功，是否打印小票？", "打印", "不了", new ViewHub.OkDialogListener() {
//                                    @Override
//                                    public void onPositiveClick(DialogInterface dialog, int which) {
//                                        printReceiptClicked();
//
//                                    }
//
//                                    @Override
//                                    public void onNegativeClick(DialogInterface dialog, int which) {
//                                        getProdectListAndHideMember();
//                                    }
//                                });
                            } else if (payBean.getStatu().equals("已取消")) {
                                //loadFinish();
                                ViewHub.showOkDialog(Vthis, "付款已取消", "用户付款失败或二维码失效，如已付款，请查看订单状态是否已同步！", "查看详情", "关闭", new ViewHub.OkDialogListener() {
                                    @Override
                                    public void onPositiveClick(DialogInterface dialog, int which) {

                                        Intent intent = new Intent(Vthis, SaleDetailActivity.class);
                                        intent.putExtra("orderCode", ordercode);
                                        startActivity(intent);

                                    }

                                    @Override
                                    public void onNegativeClick(DialogInterface dialog, int which) {
                                    }
                                });
                            } else if (payBean.getStatu().equals("待支付")) {
                                if (check_count == 5) {
                                    finishCheckDialog();
                                    ViewHub.showOkDialog(Vthis, "付款待支付", "用户付款待支付或二维码失效，如已付款，请查看订单状态是否已同步！", "查看详情", "关闭", new ViewHub.OkDialogListener() {
                                        @Override
                                        public void onPositiveClick(DialogInterface dialog, int which) {

                                            Intent intent = new Intent(Vthis, SaleDetailActivity.class);
                                            intent.putExtra("orderCode", ordercode);
                                            startActivity(intent);

                                        }

                                        @Override
                                        public void onNegativeClick(DialogInterface dialog, int which) {
                                        }
                                    });
                                } else {
                                    check_count++;
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            CheckOrderPayStatu();
                                        }
                                    }, 3000);
                                }

                            }
                        } else {
                            finishCheckDialog();
                            ViewHub.showLongToast(Vthis, "数据为空");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        finishCheckDialog();
                        ViewHub.showOkDialog(Vthis, "付款失败", "用户付款失败或二维码失效，如已付款，请查看订单状态是否已同步！", "查看详情", "关闭", new ViewHub.OkDialogListener() {
                            @Override
                            public void onPositiveClick(DialogInterface dialog, int which) {

                                Intent intent = new Intent(Vthis, SaleDetailActivity.class);
                                intent.putExtra("orderCode", ordercode);
                                startActivity(intent);

                            }

                            @Override
                            public void onNegativeClick(DialogInterface dialog, int which) {
                            }
                        });
                    }
                }));
    }

    private void loadFinish() {
        finishPayDialog();
        finishCheckDialog();
    }

    private void finishCheckDialog() {
        if (mloadingDialog != null) {
            mloadingDialog.stop();
        }
    }

    private void goToRereshTranslatePrice(CreateProduceBean bean) {
        if (bean != null) {
            Products = bean.getProducts();
            if (shopCartApdapter != null) {
                shopCartModels = shopCartApdapter.getData();
                if (!ListUtils.isEmpty(shopCartModels) && !ListUtils.isEmpty(Products)) {
                    for (ShopCartModel model : shopCartModels) {
                        for (CreateProduceBean.ProductsBean p : Products) {
                            if (model.getID() == p.getShoppingID()) {
                                model.setTransPrice(p.getDiscountPrice());
                            }
                        }
                    }
                    shopCartApdapter.notifyDataSetChanged();
                }
            }
        }
    }

    private void createOrderPreview(String ids
            , String Discount, String PayableAmount) {
        addSubscribe(HttpManager.getInstance().getPinHuoNoCacheApi(TAG
        ).CreateOrderPreview(FreeAmount, BuyerUserID, ids, Discount, PayableAmount, InnerBuyFl, SpDiscountFl, Point,ChangeShoppingIDS)
                .compose(RxUtil.<KDBResponse<CreateProduceBean>>rxSchedulerHelper())
                .compose(RxUtil.<CreateProduceBean>handleResult())
                .subscribeWith(new CommonSubscriber<CreateProduceBean>(Vthis, true, "正在创建订单...") {
                    @Override
                    public void onNext(CreateProduceBean bean) {
                        super.onNext(bean);
                        showTran(true);
                        initPriceView(bean);
                        initOnSaleSummary(bean);
                        goToRereshTranslatePrice(bean);
                    }
                }));
    }
    private void createOrderPreview(String ids
            , String Discount, String PayableAmount,int point) {
        addSubscribe(HttpManager.getInstance().getPinHuoNoCacheApi(TAG
        ).CreateOrderPreview(FreeAmount, BuyerUserID, ids, Discount, PayableAmount, InnerBuyFl, SpDiscountFl, point,ChangeShoppingIDS)
                .compose(RxUtil.<KDBResponse<CreateProduceBean>>rxSchedulerHelper())
                .compose(RxUtil.<CreateProduceBean>handleResult())
                .subscribeWith(new CommonSubscriber<CreateProduceBean>(Vthis, true, "正在创建订单...") {
                    @Override
                    public void onNext(CreateProduceBean bean) {
                        super.onNext(bean);
                        showTran(true);
                        initPriceView(bean);
                        initOnSaleSummary(bean);
                        goToRereshTranslatePrice(bean);
                    }
                }));
    }
//    private void createOrderPreview(final boolean isSmallChange, final boolean isVip, final String ids
//    ) {
//        if (isSmallChange && isVip) {
//            discount = VipDiscount;
//            pay = (int) Math.floor(payAmount) + "";
//        } else if (!isSmallChange && isVip) {
//            discount = VipDiscount;
//            pay = "0";
//        } else if (isSmallChange && !isVip) {
//            pay = (int) Math.floor(PayableAmount) + "";
//            discount = "0";
//        } else if (!isSmallChange && !isVip) {
//            pay = "0";
//            discount = "0";
//        }
//        addSubscribe(HttpManager.getInstance().getPinHuoNoCacheApi(TAG
//        ).CreateOrderPreview(FreeAmount, BuyerUserID, ids, discount, pay, InnerBuyFl, SpDiscountFl,Point)
//                .compose(RxUtil.<KDBResponse<CreateProduceBean>>rxSchedulerHelper())
//                .compose(RxUtil.<CreateProduceBean>handleResult())
//                .subscribeWith(new CommonSubscriber<CreateProduceBean>(Vthis, true, "正在创建订单...") {
//                    @Override
//                    public void onNext(CreateProduceBean bean) {
//                        super.onNext(bean);
//                        initOnSaleSummary(bean);
//                        if (bean != null) {
//                            if (bean != null) {
//                                FreeAmountOpt = bean.isFreeAmountOpt();
//                                FreeAmount = bean.getFreeAmount();
//                                showFree();
////                                    payAmount=Double.parseDouble(createProduceBean.getPayableAmount());
////                                    VipDiscount=createProduceBean.getVipDiscount();
////                                    Discount=createProduceBean.getDiscount();
//                                payAmount = Double.parseDouble(bean.getPayableAmount());
//                                tv_receivable.setText("实收：" + Utils.moneyFormat(payAmount));
//                                print_order_zk.setText(bean.getDiscount() + "");
//                                tv_summary.setText("总数:" + bean.getProductCount() + "        " +
//                                        "金额:" + bean.getProductAmount() + "        " +
//                                        "优惠:" + bean.getDiscountAmount());
//                            }
//                        } else {
//                            ViewHub.showLongToast(Vthis, "获取数据为空");
//                        }
//                        goToRereshTranslatePrice(bean);
//                    }
//                }));
//    }

    private void createOrderPreview(final String ids, String dc
    ) {
        setCbInit();
        discount = dc;
        addSubscribe(HttpManager.getInstance().getPinHuoNoCacheApi(TAG
        ).CreateOrderPreview(FreeAmount, BuyerUserID, ids, discount, pay, InnerBuyFl, SpDiscountFl, Point,ChangeShoppingIDS)
                .compose(RxUtil.<KDBResponse<CreateProduceBean>>rxSchedulerHelper())
                .compose(RxUtil.<CreateProduceBean>handleResult())
                .subscribeWith(new CommonSubscriber<CreateProduceBean>(Vthis, true, "正在创建订单...") {
                    @Override
                    public void onNext(CreateProduceBean bean) {
                        super.onNext(bean);
                        initOnSaleSummary(bean);
                        if (bean != null) {
                            if (bean != null) {
                                FreeAmountOpt = bean.isFreeAmountOpt();
                                FreeAmount = bean.getFreeAmount();
                                showFree();
//                                    payAmount=Double.parseDouble(createProduceBean.getPayableAmount());
//                                    VipDiscount=createProduceBean.getVipDiscount();
//                                    Discount=createProduceBean.getDiscount();
                                payAmount = Double.parseDouble(bean.getPayableAmount());
                                print_order_zk.setText(bean.getDiscount() + "");
                                PointOpt = bean.isPointOpt();
                                Point = bean.getPoint();
                                tv_integral.setText(Point + "");
                                if (TextUtils.isEmpty(bean.getPointSummary())){
                                    tv_integral_summary.setVisibility(View.GONE);
                                }else {
                                    tv_integral_summary.setVisibility(View.VISIBLE);
                                }
                                tv_integral_summary.setText(bean.getPointSummary());
                                TotalPoint=bean.getTotalPoint();
                                if (TotalPoint > 0) {
                                    tv_receivable.setText("实收：" + Utils.moneyFormat(payAmount) + "(扣"
                                            + TotalPoint + "积分)");
                                    tv_summary.setText("总数:" + bean.getProductCount() + "        " +
                                            "金额:" + bean.getProductAmount() + "        " +
                                            "优惠:" + bean.getDiscountAmount() + "("
                                            + TotalPoint + "积分)");
                                } else {
                                    tv_receivable.setText("实收：" + Utils.moneyFormat(payAmount));
                                    tv_summary.setText("总数:" + bean.getProductCount() + "        " +
                                            "金额:" + bean.getProductAmount() + "        " +
                                            "优惠:" + bean.getDiscountAmount());
                                }
                            }
                        } else {
                            ViewHub.showLongToast(Vthis, "获取数据为空");
                        }
                        goToRereshTranslatePrice(bean);
                    }
                }));
    }

    private void setCbInit() {
        if (cb_small_chang != null) {
            cb_small_chang.setChecked(false);
            isSmallChange = false;
        }
        if (cb_vip != null) {
            cb_vip.setChecked(false);
            isVip = false;
        }
        if (cb_internal != null) {
            cb_internal.setChecked(false);
            InnerBuyFl = false;
        }
        if (cb_special_offer != null) {
            cb_special_offer.setChecked(false);
            SpDiscountFl = false;
        }
        pay = "0";
    }

    private void getMemBerInfo(String number) {
        addSubscribe(HttpManager.getInstance().getPinHuoNoCacheApi(TAG
        ).GetMemBerInfo(number)
                .compose(RxUtil.<KDBResponse<MemBean>>rxSchedulerHelper())
                .compose(RxUtil.<MemBean>handleResult())
                .subscribeWith(new CommonSubscriber<MemBean>(Vthis, true, "获取会员信息...") {
                    @Override
                    public void onNext(MemBean bean) {
                        super.onNext(bean);
                        if (bean != null) {
                            BuyerUserID = bean.getMemberID();
                            memBerPhone = bean.getPhone();
                            judeMember(true);
                            if (tv_member != null) {
                                tv_member.setText("姓名：" + bean.getName() + "       级别：" + bean.getLevel() + "        积分：" + bean.getScore());
                            }
                            if (tv_translate.getVisibility() == View.GONE) {
                                setCbInit();
                                FreeAmount = "0";
                                showFree();
                                createOrderPreview(ShoppingIDS, "0", "0");
                            }
                        } else {
                            memBerPhone = "";
                            BuyerUserID = 0;
                            judeMember(false);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        memBerPhone = "";
                    }
                }));
    }

    private void showFree() {
        if (tv_amount_reduction != null) {
            tv_amount_reduction.setText(FreeAmount + "");
        }
    }

    private void initOnSaleSummary(CreateProduceBean createProduceBean) {
        if (createProduceBean != null) {
            if (TextUtils.isEmpty(createProduceBean.getOnSaleSummary())) {
                if (tv_sale_summary != null) {
                    tv_sale_summary.setText("");
                    tv_sale_summary.setVisibility(View.GONE);
                }
            } else {
                if (tv_sale_summary != null) {
                    tv_sale_summary.setText(createProduceBean.getOnSaleSummary());
                    tv_sale_summary.setVisibility(View.VISIBLE);
                }
            }
        } else {
            if (tv_sale_summary != null) {
                tv_sale_summary.setText("");
                tv_sale_summary.setVisibility(View.GONE);
            }
        }
    }

    private void initPriceView(CreateProduceBean createProduceBean) {
        if (createProduceBean != null) {
            FreeAmountOpt = createProduceBean.isFreeAmountOpt();
            FreeAmount = createProduceBean.getFreeAmount();
            showFree();
            PayableAmount = Double.parseDouble(createProduceBean.getPayableAmount());
            payAmount = Double.parseDouble(createProduceBean.getPayableAmount());
            VipDiscount = createProduceBean.getVipDiscount();
            Discount = createProduceBean.getDiscount();
            discount = Discount;
            PointOpt = createProduceBean.isPointOpt();
            Point = createProduceBean.getPoint();
            TotalPoint=createProduceBean.getTotalPoint();
            tv_integral.setText(Point + "");
            if (TextUtils.isEmpty(createProduceBean.getPointSummary())){
                tv_integral_summary.setVisibility(View.GONE);
            }else {
                tv_integral_summary.setVisibility(View.VISIBLE);
            }
            tv_integral_summary.setText(createProduceBean.getPointSummary());
            if (TotalPoint > 0) {
                tv_receivable.setText("应收：" + Utils.moneyFormat(Double.parseDouble(createProduceBean.getPayableAmount()))
                        + "(扣" + TotalPoint + "积分)");
                print_order_zk.setText(createProduceBean.getDiscount() + "");
                tv_summary.setText("总数:" + createProduceBean.getProductCount() + "        " +
                        "金额:" + createProduceBean.getProductAmount() + "        " +
                        "优惠:" + createProduceBean.getDiscountAmount() + "(" + TotalPoint + "积分)");
            } else {
                tv_receivable.setText("应收：" + Utils.moneyFormat(Double.parseDouble(createProduceBean.getPayableAmount())));
                print_order_zk.setText(createProduceBean.getDiscount() + "");
                tv_summary.setText("总数:" + createProduceBean.getProductCount() + "        " +
                        "金额:" + createProduceBean.getProductAmount() + "        " +
                        "优惠:" + createProduceBean.getDiscountAmount());
            }

        }
    }

    private void judeSaller(List<ProdectBean.SellerUsersBean> list) {
        if (!ListUtils.isEmpty(list)) {
            int SellerUserId = SpManager.getSellerUsersId(this);
            int firstId = 0;
            String first_name = "";
            boolean hasSellerId = false;
            for (int i = 0; i < list.size(); i++) {
                ProdectBean.SellerUsersBean bean = list.get(i);
                if (bean != null) {
                    if (i == 0) {
                        firstId = bean.getID();
                        first_name = bean.getName();
                    }
                    if (SellerUserId == bean.getID()) {
                        hasSellerId = true;
                        break;
                    }
                }
            }
            if (hasSellerId) {
                if (tv_sale != null)
                    tv_sale.setText(SpManager.getSellerName(Vthis));
            } else {
                SpManager.setSellerUsersId(Vthis, firstId);
                SpManager.setSellerName(Vthis, first_name);
                if (tv_sale != null)
                    tv_sale.setText(first_name);
            }
        } else {
            tv_sale.setText("请选择");
        }

    }

    /**
     * 获取店铺
     *
     * @author James Chen
     * @create time in 2018/5/11 15:24
     */
    private void getSHOP_INFO() {
        addSubscribe(HttpManager.getInstance().getPinHuoNoCacheApi(TAG
        ).getShopInfo()
                .compose(RxUtil.<KDBResponse<ShopInfoModel>>rxSchedulerHelper())
                .compose(RxUtil.<ShopInfoModel>handleResult())
                .subscribeWith(new CommonSubscriber<ShopInfoModel>(Vthis, true, "获取店铺.....") {
                    @Override
                    public void onNext(ShopInfoModel shopinfo) {
                        super.onNext(shopinfo);
                        SpManager.setShopName(Vthis, shopinfo.getName());
                        SpManager.setShopSignature(Vthis, shopinfo.getSignature());
                        SpManager.setShopMobile(Vthis, shopinfo.getMobile());
                        getProdectList();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (e instanceof ApiException) {
                            ApiException a = (ApiException) e;
                            if (a.getMessage().equals("您不具备权限!")) {
                                UserInfoProvider.clearAllUserInfo(Vthis);
                                Intent intent = new Intent(Vthis, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra(LoginActivity.EXTA_ISSHOWERROR, true);
                                Vthis.startActivity(intent);
                                Vthis.finish();
                            }
                        }
                    }
                }));
    }
}
