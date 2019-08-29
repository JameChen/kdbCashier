package com.yiku.kdb_flat.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.nahuo.library.controls.LoadingDialog;
import com.nahuo.library.helper.FunctionHelper;
import com.nahuo.library.helper.MD5Utils;
import com.tencent.bugly.crashreport.CrashReport;
import com.yiku.kdb_flat.BWApplication;
import com.yiku.kdb_flat.R;
import com.yiku.kdb_flat.custom_controls.ViewHub;
import com.yiku.kdb_flat.di.module.HttpManager;
import com.yiku.kdb_flat.dialog.PayCashDialog;
import com.yiku.kdb_flat.dialog.PayDialog;
import com.yiku.kdb_flat.dialog.PayOnlineDialog;
import com.yiku.kdb_flat.model.bean.CodeBean;
import com.yiku.kdb_flat.model.bean.OrderButton;
import com.yiku.kdb_flat.model.bean.PayBean;
import com.yiku.kdb_flat.model.bean.SaleDetailBean;
import com.yiku.kdb_flat.model.http.CommonSubscriber;
import com.yiku.kdb_flat.model.http.response.KDBResponse;
import com.yiku.kdb_flat.ui.adapter.DetailRefundaDapter;
import com.yiku.kdb_flat.ui.adapter.SaleBaseAdpater;
import com.yiku.kdb_flat.ui.adapter.SaleDetailItemAdapter;
import com.yiku.kdb_flat.ui.base.BaseAppCompatActivity;
import com.yiku.kdb_flat.utils.ListUtils;
import com.yiku.kdb_flat.utils.RxUtil;
import com.yiku.kdb_flat.utils.ScreenUtils;
import com.yiku.kdb_flat.utils.SpManager;
import com.yiku.kdb_flat.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import static com.yiku.kdb_flat.ui.adapter.DetailRefundaDapter.TYPE_CHANGE;
import static com.yiku.kdb_flat.ui.adapter.DetailRefundaDapter.TYPE_DEFUAT;
import static com.yiku.kdb_flat.ui.adapter.DetailRefundaDapter.TYPE_REFUND;


public class SaleDetailActivity extends BaseAppCompatActivity implements DetailRefundaDapter.PrintLister, OnClickListener, PayDialog.PopDialogListener, PayOnlineDialog.PopDialogListener, PayCashDialog.PopDialogListener {

    private static final String TAG = "SaleLogActivity";
    private SaleDetailActivity vThis = this;
    private SaleDetailActivity Vthis = this;
    private RecyclerView listView;
    // private LoadDataTask loadDataTask;
    private LoadingDialog mloadingDialog;
    private SaleDetailItemAdapter adapter;
    private SaleDetailBean data = null;
    private String ordercode;
    private TextView tv_integral, txt1, txt2, txt3, txt4, txt5, txt6, sale_detail_pay_txt1, sale_detail_pay_txt2, tv_order_time, tv_order_status, tv_order_nums, tv_order_price, sale_detail_seller, tv_detail_creater, sale_detail_discount, sale_detail_mobile, sale_detail_price, tv_order_identification;
    private View foot1, head, layout_buttons, layout_pay;
    private LinearLayout layout_order_buttons;
    private Context mContext;
    //private CancelTask cancelTask;

    private int mPrinterIndex = 0;
    private static final int MAIN_QUERY_PRINTER_STATUS = 200;
    private static final int REQUEST_PRINT_RECEIPT = 201;
    private GpService mGpService = null;
    private PrinterServiceConnection conn = null;
    private TextView item_detail_print;
    private static final int REQUEST_ENABLE_BT = 3;
    private TextView titlebar_btnRight;
    private PayOnlineDialog payOnlineDialog;
    private PayCashDialog payCashDialog;
    private int check_count = 1;
    private String payAmount;
    private int pay_type = 1;
    private PayDialog payDialog;
    private String mobile = "";
    private RecyclerView recyclerView;
    private SaleBaseAdpater baseAdapter;
    private View head_product, head_refund, head_change, head_origin;
    private RecyclerView head_refund_rv, head_change_rv, head_origin_rv;
    private DetailRefundaDapter detailRefundaDapter, detailChangADapter, detailOriginADapter;
    private View view_refund_title, view_change_title, view_origin_title;
    //private TextView item_detail_print_refund,item_detail_print_change;
    private int printType = TYPE_DEFUAT;
    private boolean isBlueConnect;
    private SaleDetailBean.RefundListBean currentPrintItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //equestWindowFeature(Window.FEATURE_CUSTOM_TITLE);// 设置自定义标题栏
        setContentView(R.layout.activity_sale_detail);
        Vthis = this;
        vThis = this;
        BWApplication.addActivity(this);
        mContext = this;
//        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
//                R.layout.layout_titlebar_default);// 更换自定义标题栏布局
        ordercode = getIntent().getStringExtra("orderCode");
        mPrinterIndex = BWApplication.PrinterId;
        connection();
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
        initView();
    }

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
                        Toast.makeText(mContext, "检查打印机状态错误码", Toast.LENGTH_SHORT).show();
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
        try {
            if (data != null) {
                if (printType == TYPE_DEFUAT) {
                    EscCommand esc = new EscCommand();
                    esc.addInitializePrinter();
                    //esc.addPrintAndFeedLines((byte) 2);
                    esc.addSelectJustification(JUSTIFICATION.CENTER);// 设置打印居中
                    esc.addSelectPrintModes(FONT.FONTA, ENABLE.OFF, ENABLE.ON, ENABLE.ON, ENABLE.OFF);// 设置为倍高倍宽
                    esc.addText(SpManager.getShopName(Vthis) + "\n"); // 打印文字
                    esc.addPrintAndLineFeed();
            /* 打印文字 */
                    esc.addSelectPrintModes(FONT.FONTA, ENABLE.OFF, ENABLE.OFF, ENABLE.OFF, ENABLE.OFF);// 取消倍高倍宽
                    esc.addSelectJustification(JUSTIFICATION.LEFT);// 设置打印左对齐
                    esc.addText("单号：" + data.getOrderCode() + "\n"); // 打印文字
                    esc.addText("日期：" + data.getCreateTime() + "\n"); // 打印文字
                    esc.addText("会员号：" + FunctionHelper.getStartPhone(data.getMobile()) + "\n"); // 打印文字
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
                    if (data.getPayInfo() != null) {
                        esc.addText("支付方式：" + data.getPayInfo().getType() + "\n");
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
                    if (data.getPoint()>0)
                    esc.addText("\n抵扣积分：" + data.getPoint());
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
                    esc.addSelectJustification(JUSTIFICATION.CENTER);// 设置打印左对齐
                    esc.addText("**** 谢谢惠顾 ****\r\n"); // 打印结束
                    // 开钱箱

                    esc.addPrintAndFeedLines((byte) 7);
                    esc.addGeneratePlus(LabelCommand.FOOT.F5, (byte) 255, (byte) 255);
                    Vector<Byte> datas = esc.getCommand(); // 发送数据
                    byte[] bytes = GpUtils.ByteTo_byte(datas);
                    String sss = Base64.encodeToString(bytes, Base64.DEFAULT);
                    int rs;
                    for (int i = 0; i < 2; i++) {
                        rs = mGpService.sendEscCommand(mPrinterIndex, sss);
                        GpCom.ERROR_CODE r = GpCom.ERROR_CODE.values()[rs];
                        if (r != GpCom.ERROR_CODE.SUCCESS) {
                            Toast.makeText(getApplicationContext(), GpCom.getErrorText(r), Toast.LENGTH_SHORT).show();
                        }
                    }

                } else if (printType == TYPE_REFUND) {


                    EscCommand esc = new EscCommand();
                    esc.addInitializePrinter();
                    //esc.addPrintAndFeedLines((byte) 2);
                    esc.addSelectJustification(JUSTIFICATION.CENTER);// 设置打印居中
                    esc.addSelectPrintModes(FONT.FONTA, ENABLE.OFF, ENABLE.ON, ENABLE.ON, ENABLE.OFF);// 设置为倍高倍宽
                    esc.addText(SpManager.getShopName(Vthis) + "\n"); // 打印文字
                    esc.addPrintAndLineFeed();
            /* 打印文字 */
                    esc.addSelectPrintModes(FONT.FONTA, ENABLE.OFF, ENABLE.OFF, ENABLE.OFF, ENABLE.OFF);// 取消倍高倍宽
                    esc.addSelectJustification(JUSTIFICATION.LEFT);// 设置打印左对齐
                    data.getRefundList();
                    esc.addText("单号：" + currentPrintItem.getCode() + "\n"); // 打印文字
                    esc.addText("日期：" + currentPrintItem.getCreateTimeX() + "\n"); // 打印文字
                    esc.addText("会员号：" + FunctionHelper.getStartPhone(data.getMobile()) + "\n"); // 打印文字
                    esc.addText("---------------------------\n");
                    esc.addText("商品列表\n\n");
                    if (!ListUtils.isEmpty(currentPrintItem.getItems())) {
                        for (SaleDetailBean.RefundListBean.ItemsBean pm : currentPrintItem.getItems()) {
                            esc.addText("#" + pm.getCode() + "#   " + pm.getColor() + "/" + pm.getSize() + "\n");
                            esc.addText("价格：￥" + Utils.moneyFormat(Double.parseDouble(pm.getPrice())) + "*" + pm.getQty() + "\n");
                            esc.addPrintAndLineFeed();
                        }
                    }
                    esc.addText("---------------------------\n");
                    //        esc.addText("数量:" + kd_order.getProductCount() + "     应收" + Utils.moneyFormat(kd_order.getProductAmount()) + "\n");
                    //        esc.addText("优惠:" + Utils.moneyFormat(kd_order.getDiscount()) + "     实收" + Utils.moneyFormat(ss_money) + "\n");
                    //        esc.addPrintAndLineFeed();

                    esc.addText("数量：" + currentPrintItem.getTotalQty());
                    esc.addSetHorAndVerMotionUnits((byte) 4, (byte) 1);
                    esc.addSetAbsolutePrintPosition((short) 4);
                    esc.addText("总额：" + Utils.moneyFormat(Double.parseDouble(currentPrintItem.getAmount())));
                    esc.addPrintAndLineFeed();
                    esc.addText("退款方式：" + currentPrintItem.getRefundPayType() + "\n");
                    esc.addText("退款原因：" + currentPrintItem.getReason());

            /* 打印文字 */
                    esc.addPrintAndFeedLines((byte) 3);
                    esc.addSelectJustification(JUSTIFICATION.RIGHT);// 设置打印左对齐
                    esc.addSelectPrintModes(FONT.FONTA, ENABLE.OFF, ENABLE.ON, ENABLE.ON, ENABLE.OFF);// 设置为倍高倍宽
                    esc.addText("退货单\r\n"); // 打印结束
                    // 开钱箱

                    esc.addPrintAndFeedLines((byte) 7);
                    esc.addGeneratePlus(LabelCommand.FOOT.F5, (byte) 255, (byte) 255);
                    Vector<Byte> datas = esc.getCommand(); // 发送数据
                    byte[] bytes = GpUtils.ByteTo_byte(datas);
                    String sss = Base64.encodeToString(bytes, Base64.DEFAULT);
                    int rs;
                    for (int i = 0; i < 2; i++) {
                        rs = mGpService.sendEscCommand(mPrinterIndex, sss);
                        GpCom.ERROR_CODE r = GpCom.ERROR_CODE.values()[rs];
                        if (r != GpCom.ERROR_CODE.SUCCESS) {
                            Toast.makeText(getApplicationContext(), GpCom.getErrorText(r), Toast.LENGTH_SHORT).show();
                        }
                    }

                } else if (printType == TYPE_CHANGE) {


                    EscCommand esc = new EscCommand();
                    esc.addInitializePrinter();
                    //esc.addPrintAndFeedLines((byte) 2);
                    esc.addSelectJustification(JUSTIFICATION.CENTER);// 设置打印居中
                    esc.addSelectPrintModes(FONT.FONTA, ENABLE.OFF, ENABLE.ON, ENABLE.ON, ENABLE.OFF);// 设置为倍高倍宽
                    esc.addText(SpManager.getShopName(Vthis) + "\n"); // 打印文字
                    esc.addPrintAndLineFeed();
            /* 打印文字 */
                    esc.addSelectPrintModes(FONT.FONTA, ENABLE.OFF, ENABLE.OFF, ENABLE.OFF, ENABLE.OFF);// 取消倍高倍宽
                    esc.addSelectJustification(JUSTIFICATION.LEFT);// 设置打印左对齐
                    data.getRefundList();
                    esc.addText("单号：" + currentPrintItem.getCode() + "\n"); // 打印文字
                    esc.addText("日期：" + currentPrintItem.getCreateTimeX() + "\n"); // 打印文字
                    esc.addText("会员号：" + FunctionHelper.getStartPhone(data.getMobile()) + "\n"); // 打印文字
                    esc.addText("---------------------------\n");
                    esc.addText("商品列表\n\n");
                    if (!ListUtils.isEmpty(currentPrintItem.getItems())) {
                        for (SaleDetailBean.RefundListBean.ItemsBean pm : currentPrintItem.getItems()) {
                            esc.addText("#" + pm.getCode() + "#   " + pm.getColor() + "/" + pm.getSize() + "\n");
                            esc.addText("价格：￥" + Utils.moneyFormat(Double.parseDouble(pm.getPrice())) + "*" + pm.getQty() + "\n");
                            esc.addPrintAndLineFeed();
                        }
                    }
                    esc.addText("---------------------------\n");
                    //        esc.addText("数量:" + kd_order.getProductCount() + "     应收" + Utils.moneyFormat(kd_order.getProductAmount()) + "\n");
                    //        esc.addText("优惠:" + Utils.moneyFormat(kd_order.getDiscount()) + "     实收" + Utils.moneyFormat(ss_money) + "\n");
                    //        esc.addPrintAndLineFeed();

                    esc.addText("数量：" + currentPrintItem.getTotalQty());
                    esc.addSetHorAndVerMotionUnits((byte) 4, (byte) 1);
                    esc.addSetAbsolutePrintPosition((short) 4);
                    esc.addText("总额：" + Utils.moneyFormat(Double.parseDouble(currentPrintItem.getAmount())));
            /* 打印文字 */
                    esc.addPrintAndFeedLines((byte) 3);
                    esc.addSelectJustification(JUSTIFICATION.RIGHT);// 设置打印左对齐
                    esc.addSelectPrintModes(FONT.FONTA, ENABLE.OFF, ENABLE.ON, ENABLE.ON, ENABLE.OFF);// 设置为倍高倍宽
                    esc.addText("换货单\r\n"); // 打印结束
                    // 开钱箱

                    esc.addPrintAndFeedLines((byte) 7);
                    esc.addGeneratePlus(LabelCommand.FOOT.F5, (byte) 255, (byte) 255);
                    Vector<Byte> datas = esc.getCommand(); // 发送数据
                    byte[] bytes = GpUtils.ByteTo_byte(datas);
                    String sss = Base64.encodeToString(bytes, Base64.DEFAULT);
                    int rs;
                    for (int i = 0; i < 2; i++) {
                        rs = mGpService.sendEscCommand(mPrinterIndex, sss);
                        GpCom.ERROR_CODE r = GpCom.ERROR_CODE.values()[rs];
                        if (r != GpCom.ERROR_CODE.SUCCESS) {
                            Toast.makeText(getApplicationContext(), GpCom.getErrorText(r), Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            } else {
                ViewHub.showShortToast(vThis, "打印商品数据为空，请重新获取");
            }
        } catch (Exception e) {
            e.printStackTrace();
            CrashReport.postCatchedException(e);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                } else {
                    ViewHub.showLongToast(vThis, "蓝牙异常");
                }
                break;
            case RefundActivity.REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    loadData();
                }
                break;

        }
    }

    public void printReceiptClicked() {
        goToPrint(TYPE_DEFUAT);
    }

    @Override
    public void printItem(SaleDetailBean.RefundListBean item, int pType) {
        if (item != null) {
            currentPrintItem = item;
            goToPrint(pType);
        } else {
            ViewHub.showShortToast(vThis, "打印商品数据为空");
        }
    }


    class PrinterServiceConnection implements ServiceConnection {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("ServiceConnection", "onServiceDisconnected() called");
            mGpService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mGpService = GpService.Stub.asInterface(service);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //finishPayDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (conn != null) {
            unbindService(conn); // unBindService
        }
        if (mBroadcastReceiver != null)
            unregisterReceiver(mBroadcastReceiver);
        finishPayDialog();
    }

    private void connection() {
        conn = new PrinterServiceConnection();
        Intent intent = new Intent(this, GpPrintService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE); // bindService
    }

    private void initView() {
        // 标题栏
        TextView tvTitle = (TextView) findViewById(R.id.titlebar_tvTitle);
        Button btnLeft = (Button) findViewById(R.id.titlebar_btnLeft);
        item_detail_print = (TextView) findViewById(R.id.item_detail_print);
        titlebar_btnRight = (TextView) findViewById(R.id.titlebar_btnRight);
        titlebar_btnRight.setText("发货");
        titlebar_btnRight.setVisibility(View.GONE);
        titlebar_btnRight.setOnClickListener(this);
        item_detail_print.setOnClickListener(this);
        tvTitle.setText("销售详情");
        btnLeft.setText(R.string.titlebar_btnBack);
        btnLeft.setVisibility(View.VISIBLE);
        btnLeft.setOnClickListener(this);

        mloadingDialog = new LoadingDialog(vThis);
        recyclerView = findKdbViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(vThis) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }

            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        layoutManager.setAutoMeasureEnabled(true);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setHasFixedSize(false);
        baseAdapter = new SaleBaseAdpater();
        recyclerView.setAdapter(baseAdapter);


        LinearLayoutManager linearLayoutManagerx = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        linearLayoutManagerx.setSmoothScrollbarEnabled(true);
        linearLayoutManagerx.setAutoMeasureEnabled(true);
        head_product = LayoutInflater.from(vThis).inflate(R.layout.layout_detail_head_product, null);
        listView = (RecyclerView) head_product.findViewById(R.id.sale_detail_list_view);
        listView.setLayoutManager(linearLayoutManagerx);
        listView.setHasFixedSize(true);
        listView.setNestedScrollingEnabled(false);
        adapter = new SaleDetailItemAdapter(vThis);
        listView.setAdapter(adapter);
        //foot1 = LayoutInflater.from(this).inflate(R.layout.sale_detail_order_info, null);
        head = LayoutInflater.from(this).inflate(R.layout.sale_detail_head, null);
        tv_integral=(TextView) head.findViewById(R.id.tv_integral);
        foot1 = head;
        //foot1.setVisibility(View.GONE);
        head.setVisibility(View.GONE);
        layout_pay = foot1.findViewById(R.id.layout_pay);
        sale_detail_discount = (TextView) head.findViewById(R.id.sale_detail_discount);
        tv_detail_creater = (TextView) head.findViewById(R.id.tv_detail_creater);
        sale_detail_seller = (TextView) head.findViewById(R.id.sale_detail_seller);
        tv_order_time = (TextView) head.findViewById(R.id.tv_order_time);
        sale_detail_price = (TextView) head.findViewById(R.id.sale_detail_price);
        sale_detail_mobile = (TextView) head.findViewById(R.id.sale_detail_mobile);
        tv_order_status = (TextView) head.findViewById(R.id.tv_order_status);
        tv_order_nums = (TextView) head.findViewById(R.id.tv_order_nums);
        tv_order_price = (TextView) head.findViewById(R.id.tv_order_price);
        tv_order_identification = (TextView) head.findViewById(R.id.tv_order_identification);
        layout_buttons = head.findViewById(R.id.layout_buttons);
        layout_order_buttons = (LinearLayout) head.findViewById(R.id.layout_order_buttons);
        txt1 = (TextView) foot1.findViewById(R.id.sale_detail_txt1);
        txt2 = (TextView) foot1.findViewById(R.id.sale_detail_txt2);
        txt3 = (TextView) foot1.findViewById(R.id.sale_detail_txt3);
        txt4 = (TextView) foot1.findViewById(R.id.sale_detail_txt4);
        txt5 = (TextView) foot1.findViewById(R.id.sale_detail_txt5);
        txt6 = (TextView) foot1.findViewById(R.id.sale_detail_txt6);
        sale_detail_pay_txt1 = (TextView) foot1.findViewById(R.id.sale_detail_pay_txt1);
        sale_detail_pay_txt2 = (TextView) foot1.findViewById(R.id.sale_detail_pay_txt2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        linearLayoutManager2.setSmoothScrollbarEnabled(true);
        linearLayoutManager2.setAutoMeasureEnabled(true);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        linearLayoutManager3.setSmoothScrollbarEnabled(true);
        linearLayoutManager3.setAutoMeasureEnabled(true);
        //listView.addFooterView(foot1);
        head_refund = LayoutInflater.from(vThis).inflate(R.layout.layout_detail_head_refund, null);
       /* item_detail_print_refund= (TextView) LayoutInflater.from(this).inflate(R.layout.foot_btn_print, null);
        if (item_detail_print_refund!=null) {
            item_detail_print_refund.setText("打印退货小票");
            item_detail_print_refund.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToPrint(TYPE_REFUND);
                }
            });
        }*/
        view_refund_title = LayoutInflater.from(vThis).inflate(R.layout.layout_tv, null);
        TextView tv_refund = (TextView) view_refund_title.findViewById(R.id.tv);
        if (tv_refund != null)
            tv_refund.setText("退货单");
        head_refund_rv = (RecyclerView) head_refund.findViewById(R.id.recyclerView);
        head_refund_rv.setLayoutManager(linearLayoutManager);
        head_refund_rv.setHasFixedSize(true);
        head_refund_rv.setNestedScrollingEnabled(false);
        detailRefundaDapter = new DetailRefundaDapter(vThis);
        detailRefundaDapter.setPrintLister(this);
        detailRefundaDapter.setType(TYPE_REFUND);
        head_refund_rv.setAdapter(detailRefundaDapter);

        head_change = LayoutInflater.from(vThis).inflate(R.layout.layout_detail_head_refund, null);
       /* item_detail_print_change= (TextView) LayoutInflater.from(this).inflate(R.layout.foot_btn_print, null);
        if (item_detail_print_change!=null) {
            item_detail_print_change.setText("打印换货小票");
            item_detail_print_change.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToPrint(TYPE_CHANGE);
                }
            });
        }*/
        view_change_title = LayoutInflater.from(vThis).inflate(R.layout.layout_tv, null);
        TextView tv_change = (TextView) view_change_title.findViewById(R.id.tv);
        if (tv_change != null)
            tv_change.setText("换货单");
        head_change_rv = (RecyclerView) head_change.findViewById(R.id.recyclerView);
        head_change_rv.setLayoutManager(linearLayoutManager2);
        head_change_rv.setHasFixedSize(true);
        head_change_rv.setNestedScrollingEnabled(false);
        detailChangADapter = new DetailRefundaDapter(vThis);
        detailChangADapter.setType(DetailRefundaDapter.TYPE_CHANGE);
        detailChangADapter.setPrintLister(this);
        head_change_rv.setAdapter(detailChangADapter);

        head_origin = LayoutInflater.from(vThis).inflate(R.layout.layout_detail_head_refund, null);
        view_origin_title = LayoutInflater.from(vThis).inflate(R.layout.layout_tv, null);
        TextView tv_origin = (TextView) view_origin_title.findViewById(R.id.tv);
        if (tv_origin != null)
            tv_origin.setText("原始单");
        head_origin_rv = (RecyclerView) head_origin.findViewById(R.id.recyclerView);
        head_origin_rv.setLayoutManager(linearLayoutManager3);
        head_origin_rv.setHasFixedSize(true);
        head_origin_rv.setNestedScrollingEnabled(false);
        detailOriginADapter = new DetailRefundaDapter(vThis);
        detailOriginADapter.setType(DetailRefundaDapter.TYPE_PRIMARY);
        head_origin_rv.setAdapter(detailOriginADapter);


        loadData();
    }

    private void goToPrint(int pType) {
        printType = pType;
        try {
            if (mGpService.getPrinterConnectStatus(BWApplication.getInstance().PrinterId) == GpDevice.STATE_CONNECTED) {
                int type = mGpService.getPrinterCommandType(mPrinterIndex);
                if (type == GpCom.ESC_COMMAND) {
                    mGpService.queryPrinterStatus(mPrinterIndex, 1000, REQUEST_PRINT_RECEIPT);
                } else {
                    Toast.makeText(this, "Printer is not receipt mode", Toast.LENGTH_SHORT).show();
                }
            } else {
                ViewHub.showOkDialog(vThis, "蓝牙打印机未连接", "连接打印机？", "确定", "取消", new ViewHub.OkDialogListener() {
                    @Override
                    public void onPositiveClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(mContext, BlueToothActivity.class));
                    }

                    @Override
                    public void onNegativeClick(DialogInterface dialog, int which) {

                    }
                });
            }
        } catch (RemoteException e1) {
            e1.printStackTrace();
        }
    }

    private void loadData() {
//        loadDataTask = new LoadDataTask();
//        loadDataTask.execute((Void) null);
        addSubscribe(HttpManager.getInstance().getPinHuoNoCacheApi(TAG
        ).GetOrderDetail(ordercode)
                .compose(RxUtil.<KDBResponse<SaleDetailBean>>rxSchedulerHelper())
                .compose(RxUtil.<SaleDetailBean>handleResult())
                .subscribeWith(new CommonSubscriber<SaleDetailBean>(vThis, true, R.string.loading) {
                    @Override
                    public void onNext(SaleDetailBean data) {
                        super.onNext(data);
                        try {
                            if (data != null) {
                                if (mGpService.getPrinterConnectStatus(BWApplication.getInstance().PrinterId) == GpDevice.STATE_CONNECTED) {
                                    item_detail_print.setBackground(getResources().getDrawable(R.drawable.btn_d_red));
                                    item_detail_print.setEnabled(true);
                                    isBlueConnect = true;
                               /* if (item_detail_print_refund!=null){
                                    item_detail_print_refund.setBackground(getResources().getDrawable(R.drawable.btn_d_red));
                                    item_detail_print_refund.setEnabled(true);
                                }
                                if (item_detail_print_change!=null){
                                    item_detail_print_change.setBackground(getResources().getDrawable(R.drawable.btn_d_red));
                                    item_detail_print_change.setEnabled(true);
                                }*/
                                } else {
                                    isBlueConnect = false;
                                    item_detail_print.setEnabled(false);
                                    item_detail_print.setBackground(getResources().getDrawable(R.drawable.btn_d_gray));
                               /* if (item_detail_print_refund!=null){
                                    item_detail_print_refund.setBackground(getResources().getDrawable(R.drawable.btn_d_gray));
                                    item_detail_print_refund.setEnabled(false);
                                }
                                if (item_detail_print_change!=null){
                                    item_detail_print_change.setBackground(getResources().getDrawable(R.drawable.btn_d_gray));
                                    item_detail_print_change.setEnabled(false);
                                }*/
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            CrashReport.postCatchedException(e);
                        } finally {
                            vThis.data = data;
                            head.setVisibility(View.VISIBLE);
                            baseAdapter.removeAllHeaderView();
                            adapter.removeAllHeaderView();
                            detailRefundaDapter.removeAllHeaderView();
                            detailRefundaDapter.setBlueConnect(isBlueConnect);
                            // detailRefundaDapter.removeAllFooterView();
                            detailChangADapter.removeAllHeaderView();
                            detailChangADapter.setBlueConnect(isBlueConnect);
                            //detailChangADapter.removeAllFooterView();
                            detailOriginADapter.removeAllHeaderView();
                            if (data != null) {
                                if (data.getPoint()>0){
                                    if (tv_integral!=null) {
                                        tv_integral.setVisibility(View.VISIBLE);
                                        tv_integral.setText("(扣"+data.getPoint()+"积分)");
                                    }
                                }else {
                                    if (tv_integral!=null)
                                        tv_integral.setVisibility(View.GONE);
                                }

                                baseAdapter.addHeaderView(head_product);
                                adapter.addHeaderView(head);
                                adapter.setNewData(data.getProducts());
                                if (!ListUtils.isEmpty(data.getRefundList())) {
                                    detailRefundaDapter.addHeaderView(view_refund_title);
                                    baseAdapter.addHeaderView(head_refund);
                                    if (detailRefundaDapter != null) {
                                        detailRefundaDapter.setNewData(data.getRefundList());
                                    }
                                    // detailRefundaDapter.addFooterView(item_detail_print_refund);
                                }
                                if (!ListUtils.isEmpty(data.getChangeList())) {
                                    detailChangADapter.addHeaderView(view_change_title);
                                    baseAdapter.addHeaderView(head_change);
                                    if (detailChangADapter != null) {
                                        detailChangADapter.setNewData(data.getChangeList());
                                    }
                                    //detailChangADapter.addFooterView(item_detail_print_change);
                                }
                                if (!ListUtils.isEmpty(data.getOrgOrderList())) {
                                    detailOriginADapter.addHeaderView(view_origin_title);
                                    baseAdapter.addHeaderView(head_origin);
                                    if (detailOriginADapter != null) {
                                        detailOriginADapter.setNewData(data.getOrgOrderList());
                                    }
                                }
                                //  ListviewUtls.setListViewHeightBasedOnChildren(listView);
                                List<OrderButton> buttons = data.getButtons();
                                layout_order_buttons.removeAllViews();
                                if (ListUtils.isEmpty(buttons)) {
                                    layout_buttons.setVisibility(View.GONE);
                                } else {
                                    layout_buttons.setVisibility(View.VISIBLE);
                                    addOrderDetailButton(layout_order_buttons, data.getButtons(), data);
                                }
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
                                if (tv_order_identification != null)
                                    tv_order_identification.setText(remark);
                                mobile = data.getMobile();
                                sale_detail_seller.setText("销售员：" + data.getSellerUserUserName());
                                tv_detail_creater.setText("收银员：" + data.getCreateUserName());
                                sale_detail_discount.setText("销售折扣：" + data.getGetDiscountPercent());
                                txt1.setText("日期：" + data.getCreateTime());
                                txt2.setText("销售单号：" + data.getOrderCode());
                                txt3.setText("总数：" + data.getProductCount() + "件");
                                txt4.setText("应收：¥" + data.getProductAmount());
                                txt5.setText("优惠：¥" + data.getDiscount());
                                txt6.setText("实收：¥" + data.getPayableAmount());
                                payAmount = data.getPayableAmount();
                                if (data.getPayInfo() != null) {
//                        if (data.getPayInfo().getType().equals("未支付")) {
//                            layout_pay.setVisibility(View.GONE);
//                        } else {
//                            layout_pay.setVisibility(View.VISIBLE);
//                        }
                                    sale_detail_pay_txt1.setText("收款方式：" + data.getPayInfo().getType());
                                    sale_detail_pay_txt2.setText("交易号：" + data.getPayInfo().getCode());
                                } else {
                                    layout_pay.setVisibility(View.GONE);
                                }
                                sale_detail_price.setText("总原价：" + data.getProductAmount());
                                sale_detail_mobile.setText("会员手机号：" + data.getMobile());
                                tv_order_price.setText(data.getPayableAmount());
                                tv_order_time.setText("销售时间：" + data.getCreateTime());
                                tv_order_status.setText(data.getStatu());
                                tv_order_nums.setText("成交数量：" + data.getProductCount());
//                    if (data.getProductCount() > 0 && Double.parseDouble(data.getPayableAmount()) > 0) {
//                        tv_order_nums.setText("销售数量：" + data.getProductCount() + "件    " + "收款金额：¥ " + data.getPayableAmount());
//                    } else if (data.getProductCount() <= 0 && Double.parseDouble(data.getPayableAmount()) > 0) {
//                        tv_order_nums.setText("收款金额：¥ " + data.getPayableAmount());
//                    } else if (data.getProductCount() > 0 && Double.parseDouble(data.getPayableAmount()) <= 0) {
//                        tv_order_nums.setText("销售数量：" + data.getProductCount() + "件    ");
//                    }
                            }
                            baseAdapter.notifyDataSetChanged();
                            item_detail_print.setVisibility(View.VISIBLE);

                        }
                    }
                }));
    }

    private void canCelOrder() {
        addSubscribe(HttpManager.getInstance().getPinHuoNoCacheApi(TAG
        ).CancelOrder(ordercode)
                .compose(RxUtil.<KDBResponse<Object>>rxSchedulerHelper())
                .compose(RxUtil.<Object>handleResult())
                .subscribeWith(new CommonSubscriber<Object>(vThis) {
                    @Override
                    public void onNext(Object saleBean) {
                        super.onNext(saleBean);
                        loadData();
                    }
                }));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.titlebar_btnRight:

                break;
            case R.id.titlebar_btnLeft:
                finish();
                break;
            case R.id.item_detail_print:
                if (!FunctionHelper.isFastClick()) {
                    printReceiptClicked();
                } else {
                    ViewHub.showLongToast(vThis, "点击太快了！");
                }
                break;
        }
    }

//    public class LoadDataTask extends AsyncTask<Void, Void, String> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            mloadingDialog.start(getString(R.string.items_loadData_loading));
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            mloadingDialog.stop();
//            if (!result.equals("OK")) {
//                Toast.makeText(vThis, result, Toast.LENGTH_LONG).show();
//            } else {
//                //foot1.setVisibility(View.VISIBLE);
//                head.setVisibility(View.VISIBLE);
//                if (data != null) {
//                    List<OrderButton> buttons = data.getButtons();
//                    layout_order_buttons.removeAllViews();
//                    if (ListUtils.isEmpty(buttons)) {
//                        layout_buttons.setVisibility(View.GONE);
//                    } else {
//                        layout_buttons.setVisibility(View.VISIBLE);
//                        addOrderDetailButton(layout_order_buttons, data.getButtons(), data);
//                    }
//                    adapter.models = data.getProducts();
//                    sale_detail_seller.setText("销售员：" + data.getSellerUserUserName());
//                    tv_detail_creater.setText("收银员：" + data.getCreateUserName());
//                    sale_detail_discount.setText("销售折扣：" + data.getGetDiscountPercent());
//                    txt1.setText("日期：" + data.getCreateTime());
//                    txt2.setText("销售单号：" + data.getOrderCode());
//                    txt3.setText("总数：" + data.getProductCount() + "件");
//                    txt4.setText("应收：¥" + data.getProductAmount());
//                    txt5.setText("优惠：¥" + data.getDiscount());
//                    txt6.setText("实收：¥" + data.getPayableAmount());
//                    if (data.getPayInfo() != null) {
////                        if (data.getPayInfo().getType().equals("未支付")) {
////                            layout_pay.setVisibility(View.GONE);
////                        } else {
////                            layout_pay.setVisibility(View.VISIBLE);
////                        }
//                        sale_detail_pay_txt1.setText("收款方式：" + data.getPayInfo().getType());
//                        sale_detail_pay_txt2.setText("交易号：" + data.getPayInfo().getCode());
//                    } else {
//                        layout_pay.setVisibility(View.GONE);
//                    }
//                    tv_order_price.setText(data.getPayableAmount());
//                    tv_order_time.setText("销售时间：" + data.getCreateTime());
//                    tv_order_status.setText(data.getStatu());
//                    tv_order_nums.setText("成交数量：" + data.getProductCount());
////                    if (data.getProductCount() > 0 && Double.parseDouble(data.getPayableAmount()) > 0) {
////                        tv_order_nums.setText("销售数量：" + data.getProductCount() + "件    " + "收款金额：¥ " + data.getPayableAmount());
////                    } else if (data.getProductCount() <= 0 && Double.parseDouble(data.getPayableAmount()) > 0) {
////                        tv_order_nums.setText("收款金额：¥ " + data.getPayableAmount());
////                    } else if (data.getProductCount() > 0 && Double.parseDouble(data.getPayableAmount()) <= 0) {
////                        tv_order_nums.setText("销售数量：" + data.getProductCount() + "件    ");
////                    }
//                }
//                adapter.notifyDataSetChanged();
//                try {
//                    item_detail_print.setVisibility(View.VISIBLE);
//                    if (data != null && mGpService.getPrinterConnectStatus(BWApplication.getInstance().PrinterId) == GpDevice.STATE_CONNECTED) {
//                        item_detail_print.setBackground(getResources().getDrawable(R.drawable.btn_d_red));
//                        item_detail_print.setEnabled(true);
//                    } else {
//                        item_detail_print.setEnabled(false);
//                        item_detail_print.setBackground(getResources().getDrawable(R.drawable.btn_d_gray));
//                    }
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        @Override
//        protected String doInBackground(Void... params) {
//
//            try {
//                // data = OtherAPI.getSaleDetail(vThis, ordercode);
//                return "OK";
//            } catch (Exception ex) {
//                Log.e(TAG, "获取销售列表发生异常");
//                ex.printStackTrace();
//                return ex.getMessage() == null ? "未知异常" : ex.getMessage();
//            }
//        }
//
//    }

//    public class CancelTask extends AsyncTask<Void, Void, String> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            if (mloadingDialog != null)
//                mloadingDialog.start("取消中....");
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            finishCheckDialog();
//            if (result.equals("OK")) {
//                Toast.makeText(vThis, "取消成功", Toast.LENGTH_LONG).show();
//                loadDataTask = new LoadDataTask();
//                loadDataTask.execute();
//            }
//        }
//
//        @Override
//        protected String doInBackground(Void... params) {
//            try {
//                // OtherAPI.cancelOrder(vThis, ordercode);
//                return "OK";
//            } catch (Exception ex) {
//                Log.e(TAG, "取消销售订单发生异常");
//                ex.printStackTrace();
//                return ex.getMessage() == null ? "未知异常" : ex.getMessage();
//            }
//        }
//
//    }

    public void addOrderDetailButton(LinearLayout parent, List<OrderButton> buttons
            , SaleDetailBean bean) {
        ButtonOnClickListener l = new ButtonOnClickListener();
        parent.removeAllViews();
        l.setBean(bean);
        if (buttons != null) {
            int margin = ScreenUtils.dip2px(mContext, 10);
            int top_margin = ScreenUtils.dip2px(mContext, 6);
            int top_pad = ScreenUtils.dip2px(mContext, 4);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = top_margin;
            for (OrderButton model : buttons) {
                TextView child = new TextView(parent.getContext());
                child.setPadding(margin, top_pad, margin, top_pad);
                child.setEllipsize(TextUtils.TruncateAt.END);
                child.setSingleLine(true);
                child.setTextSize(15);
                child.setText(model.getTitle());
                child.setGravity(Gravity.CENTER_VERTICAL);
                child.setClickable(model.isEnable());
                // child.getPaint().measureText(text, start, end)
                highlightButton(child, model.isPoint(), model.getType().equals("text"));
                if (model.isEnable()) {
                    child.setTag(model);
                    child.setOnClickListener(l);
                    child.setClickable(true);
                    child.setEnabled(true);
                } else {
                    child.setClickable(false);
                    child.setEnabled(false);
                }
                parent.addView(child, params);
            }
        }
    }

    private void highlightButton(TextView btn, boolean highlight, boolean isText) {

        if (isText) {
            btn.setBackgroundColor(ContextCompat.getColor(mContext, R.color.btn_bg_gray));
            btn.setTextColor(ContextCompat.getColor(mContext, R.color.lightblack));
        } else {
            btn.setBackgroundResource(highlight ? R.drawable.order_button_red_bg : R.drawable.order_button_white_gray_bg);
            btn.setTextColor(highlight ? mContext.getResources().getColor(R.color.white) : mContext.getResources().getColor(R.color.txt_gray));
        }
    }

    private class ButtonOnClickListener implements OnClickListener {
        SaleDetailBean bean;

        public void setBean(SaleDetailBean bean) {
            this.bean = bean;
        }

        @Override
        public void onClick(final View v) {
            final OrderButton btn = (OrderButton) v.getTag();
            String action = btn.getAction();
            if (action.equals(OrderButton.ACTION_PAYORDER)) {
//                Intent intent = new Intent(vThis, OrderPayActivity.class);
//                intent.putExtra(EXTRA_PAYDETAIL, bean);
//                vThis.startActivity(intent);
                if (payDialog != null) {
                    if (payDialog.isShowing())
                        payDialog.dismiss();
                    payDialog = null;
                }
                payDialog = PayDialog.getInstance(vThis).setPayOncClick(vThis);
                payDialog.showDialog();

            } else if (action.equals(OrderButton.ACTION_CANCELORDER)) {
//                cancelTask = new CancelTask();
//                cancelTask.execute();
                ViewHub.showOkDialog(vThis, "提示", "您确定要取消吗？", getString(android.R.string.ok),
                        getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                canCelOrder();
                            }
                        });

            } else if (action.equals(OrderButton.ACTION_REFUNDORDER)) {
                //退款
                startActivityForResult(new Intent(vThis, RefundActivity.class).putExtra(RefundActivity.EXTRA_ORDER, ordercode).putExtra(RefundActivity.EXTRA_ORDER_MOBILE, mobile), RefundActivity.REQUEST_CODE);
            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        // StatService.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // StatService.onResume(this);

    }

    @Override
    public void onCashPayClick() {
        //现金。pos机器
        goCodePay4ShopTrade(pay_type);
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
    public void onPayClick(int type) {
        pay_type = type;
        goToPay(pay_type);
    }

    private void goToPay(int pay_type) {
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

    private void goCodePay4ShopTrade(int pay_type, String payCode) {
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
                .subscribeWith(new CommonSubscriber<CodeBean>(vThis, true, "正在结账中...") {

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
//                                        getProdectList();
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
                .subscribeWith(new CommonSubscriber<CodeBean>(vThis, true, "正在结账中...") {

                    @Override
                    public void onNext(CodeBean codeBean) {
                        super.onNext(codeBean);
                        check_count = 1;
                        CheckOrderPayStatu();
                    }

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
        if (payDialog != null) {
            if (payDialog.isShowing())
                payDialog.dismiss();
            payDialog = null;
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
                .subscribeWith(new CommonSubscriber<PayBean>(vThis) {

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
//                                        getProdectList();
//                                    }
//                                });
                            } else if (payBean.getStatu().equals("已取消")) {
                                finishCheckDialog();
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

    private void getProdectList() {
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
}
