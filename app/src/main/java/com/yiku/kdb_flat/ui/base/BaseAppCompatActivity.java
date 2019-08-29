package com.yiku.kdb_flat.ui.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.yiku.kdb_flat.BWApplication;
import com.yiku.kdb_flat.ui.LoginActivity;
import com.yiku.kdb_flat.ui.MainActivity;
import com.yiku.kdb_flat.ui.StartActivity;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by jame on 2018/3/28.
 */

public class BaseAppCompatActivity extends CheckPermissionsActivity {
    protected CompositeDisposable mCompositeDisposable;
    protected <T extends View> T findKdbViewById(int resId){
        return (T) findViewById(resId);
    }
    protected void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    public void addSubscribe(Disposable subscription) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        if (!this.getClass().getSimpleName().equals(MainActivity.class.getSimpleName())) {
            BWApplication.addActivity(this);
        }
        if (this.getClass().getSimpleName().equals(MainActivity.class.getSimpleName()) ||
                this.getClass().getSimpleName().equals(StartActivity.class.getSimpleName())
                || this.getClass().getSimpleName().equals(LoginActivity.class.getSimpleName())){
            transparencyBar(this);
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    public void transparencyBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
            setSystemBarTint(activity);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            setSystemBarTint(activity);
        }
    }

    public void setSystemBarTint(Activity activity) {

//        SystemBarTintManager tintManager = new SystemBarTintManager(this);
//// 激活状态栏设置
//        tintManager.setStatusBarTintEnabled(true);
//// 激活导航栏设置
//        tintManager.setNavigationBarTintEnabled(true);
//// 设置一个颜色给系统栏
//        tintManager.setTintColor(ContextCompat.getColor(activity,R.color.my_colorPrimary));
    }

    public void setBarStyle() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            ToolbarUtil.createStatusBarView(this,R.color.transparent,1);
//            //透明导航栏
//          //  getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            // 创建状态栏的管理实例
//            SystemBarTintManager tintManager = new SystemBarTintManager(this);
//// 激活状态栏设置
//            tintManager.setStatusBarTintEnabled(true);
//// 激活导航栏设置
//            tintManager.setNavigationBarTintEnabled(true);
//// 设置一个颜色给系统栏
//            tintManager.setTintColor(Color.parseColor("#40C4FF"));
        }
    }

    @TargetApi(19)
    public void setStatusBarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            ViewGroup decorViewGroup = (ViewGroup) activity.getWindow().getDecorView();
            //获取自己布局的根视图
            View rootView = ((ViewGroup) (decorViewGroup.findViewById(android.R.id.content))).getChildAt(0);
            //预留状态栏位置
            rootView.setFitsSystemWindows(true);

            //添加状态栏高度的视图布局，并填充颜色
            View statusBarTintView = new View(activity);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    getInternalDimensionSize(activity.getResources(), "status_bar_height"));
            params.gravity = Gravity.TOP;
            statusBarTintView.setLayoutParams(params);
            statusBarTintView.setBackgroundColor(color);
            decorViewGroup.addView(statusBarTintView);
        }
    }

    public static int getInternalDimensionSize(Resources res, String key) {
        int result = 0;
        int resourceId = res.getIdentifier(key, "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }
//
//    /**
//     * 获取订单退款信息
//     *
//     * @author James Chen
//     * @create time in 2018/5/9 14:31
//     */
//    public void getOrderItemForRefund(final Context context, final int oid) {
//        if (oid < 0)
//            return;
//        addSubscribe(HttpManager.getInstance().getPinHuoNetCacheApi("getOrderItemForRefund")
//                .getOrderItemForRefund(oid).compose(RxUtil.<KDBResponse<ReFundBean>>rxSchedulerHelper())
//                .compose(RxUtil.<ReFundBean>handleResult()).subscribeWith(new CommonSubscriber<ReFundBean>(context, true, R.string.loading) {
//                    @Override
//                    public void onNext(final ReFundBean reFundBean) {
//                        super.onNext(reFundBean);
//                        if (reFundBean != null) {
//                            String styledText;
//                            if (TextUtils.isEmpty(reFundBean.getCoin())) {
//                                styledText = "已付商品货款：<font color='red'>¥" + reFundBean.getProductAmount() + "</font>，分摊运费：<font color='red'>¥"
//                                        + reFundBean.getPostFee() + "</font>，总计可退：<font color='#09F709'>¥" + reFundBean.getTotalRefundAmount() + "</font>。";
//                            } else {
//                                styledText = "已付商品货款：<font color='red'>¥" + reFundBean.getProductAmount() + "</font>，换货币：<font color='red'>¥" + reFundBean.getCoin() + "</font>，分摊运费：<font color='red'>¥"
//                                        + reFundBean.getPostFee() + "</font>，总计可退：<font color='#09F709'>¥" + reFundBean.getTotalRefundAmount() + "</font>。";
//                            }
//                            ReFundOderDialog dialog = new ReFundOderDialog((Activity) context);
//                            dialog.setHasTittle(true).setMessage(Html.fromHtml(styledText))
//                                    .setPositive("确认退款", new ReFundOderDialog.PopDialogListener() {
//                                        @Override
//                                        public void onPopDialogButtonClick(int which, ReFundOderDialog oderDialog) {
//                                            buyerApplyRefund(context, oid, oderDialog);
//                                        }
//                                    }).setNegative("我再想想", null).show();
//                        }
//                    }
//                }));
//    }
//
//    /**
//     * 获取订单退款信息
//     *
//     * @author James Chen
//     * @create time in 2018/5/9 14:31
//     */
//    public void getRefundAndBuyerApplySettleRefund(final Context context, final int oid) {
//        if (oid < 0)
//            return;
//        addSubscribe(HttpManager.getInstance().getPinHuoNetCacheApi("getOrderItemForRefund")
//                .getOrderItemForRefund(oid).compose(RxUtil.<KDBResponse<ReFundBean>>rxSchedulerHelper())
//                .compose(RxUtil.<ReFundBean>handleResult()).subscribeWith(new CommonSubscriber<ReFundBean>(context, true, R.string.loading) {
//                    @Override
//                    public void onNext(final ReFundBean reFundBean) {
//                        super.onNext(reFundBean);
//                        if (reFundBean != null) {
//                            String styledText;
//                            if (TextUtils.isEmpty(reFundBean.getCoin())) {
//                                styledText = "已付商品货款：<font color='red'>¥" + reFundBean.getProductAmount() + "</font>，分摊运费：<font color='red'>¥"
//                                        + reFundBean.getPostFee() + "</font>，总计可退：<font color='#09F709'>¥" + reFundBean.getTotalRefundAmount() + "</font>。";
//                            } else {
//                                styledText = "已付商品货款：<font color='red'>¥" + reFundBean.getProductAmount() + "</font>，换货币：<font color='red'>¥" + reFundBean.getCoin() + "</font>，分摊运费：<font color='red'>¥"
//                                        + reFundBean.getPostFee() + "</font>，总计可退：<font color='#09F709'>¥" + reFundBean.getTotalRefundAmount() + "</font>。";
//                            }
//                            ReFundOderDialog dialog = new ReFundOderDialog((Activity) context);
//                            dialog.setHasTittle(true).setMessage(Html.fromHtml(styledText))
//                                    .setPositive("确认退款", new ReFundOderDialog.PopDialogListener() {
//                                        @Override
//                                        public void onPopDialogButtonClick(int which, ReFundOderDialog oderDialog) {
//                                            buyerApplySettleRefund(context, oid, oderDialog);
//                                        }
//                                    }).setNegative("我再想想", null).show();
//                        }
//                    }
//                }));
//    }
//
//    /**
//     * 订单退款
//     *
//     * @author James Chen
//     * @create time in 2018/5/9 14:31
//     */
//    public void buyerApplySettleRefund(final Context context, int oid, final ReFundOderDialog oderDialog) {
//        if (oid < 0)
//            return;
//        Map<String, Object> params = new HashMap<>();
//        params.put("orderid", oid + "");
////        params.put("refundWithProduct", false);
////        params.put("refundType", 1);
////        params.put("refundAmount",0);
////        params.put("refundReason", "");
//        addSubscribe(HttpManager.getInstance().getPinHuoNetCacheApi("buyerApplyRefund")
//                .buyerApplySettleRefund(params).compose(RxUtil.<KDBResponse<Object>>rxSchedulerHelper())
//                .compose(RxUtil.<Object>handleResult()).subscribeWith(new CommonSubscriber<Object>(context, true, R.string.loading_refund) {
//                    @Override
//                    public void onNext(Object reFundBean) {
//                        super.onNext(reFundBean);
//                        ViewHub.showShortToast(context, "退款成功！");
//                        if (oderDialog != null)
//                            oderDialog.dismiss();
//                        EventBus.getDefault().post(BusEvent.getEvent(EventBusId.REFUND_BUYER_AGRESS, "ok"));
//                    }
//                }));
//    }
//
//    /**
//     * 订单退款
//     *
//     * @author James Chen
//     * @create time in 2018/5/9 14:31
//     */
//    public void buyerApplyRefund(final Context context, int oid, final ReFundOderDialog oderDialog) {
//        if (oid < 0)
//            return;
//        Map<String, Object> params = new HashMap<>();
//        params.put("orderid", oid + "");
////        params.put("refundWithProduct", false);
////        params.put("refundType", 1);
////        params.put("refundAmount",0);
////        params.put("refundReason", "");
//        addSubscribe(HttpManager.getInstance().getPinHuoNetCacheApi("buyerApplyRefund")
//                .buyerApplyRefund(params).compose(RxUtil.<KDBResponse<Object>>rxSchedulerHelper())
//                .compose(RxUtil.<Object>handleResult()).subscribeWith(new CommonSubscriber<Object>(context, true, R.string.loading_refund) {
//                    @Override
//                    public void onNext(Object reFundBean) {
//                        super.onNext(reFundBean);
//                        ViewHub.showShortToast(context, "退款成功！");
//                        if (oderDialog != null)
//                            oderDialog.dismiss();
//                        EventBus.getDefault().post(BusEvent.getEvent(EventBusId.REFUND_BUYER_AGRESS, "ok"));
//                    }
//                }));
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unSubscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        StatService.onPause(this);
//        JPushInterface.onPause(this);
//        UMengTestUtls.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        StatService.onResume(this);
//        JPushInterface.onResume(this);
//        UMengTestUtls.onResume(this);
//        if (HttpUtils.ECC_OPEN) {
//            EaseUI.getInstance().getNotifier().reset();
//        }
    }
}
