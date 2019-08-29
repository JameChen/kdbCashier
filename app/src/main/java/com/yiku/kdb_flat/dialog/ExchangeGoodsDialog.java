package com.yiku.kdb_flat.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nahuo.library.helper.FunctionHelper;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.stat.StatService;
import com.yiku.kdb_flat.BWApplication;
import com.yiku.kdb_flat.R;
import com.yiku.kdb_flat.custom_controls.ViewHub;
import com.yiku.kdb_flat.model.ExchanegGoodBean;
import com.yiku.kdb_flat.ui.adapter.ExchangeGoodsAdpater;

import java.util.Properties;
import java.util.UUID;


public class ExchangeGoodsDialog extends Dialog implements View.OnClickListener {

    private Activity mActivity;
    private View mRootView;
    private LinearLayout mContentViewBg;
    private TextView tv_search, iv_del;
    private RecyclerView recyclerView;
    private Button mBtnCancel, mBtnOK;
    private PopDialogListener mPositivePopDialogListener;

    private LinearLayout mContentView;
    private boolean isShowDismiss = true;
    static ExchangeGoodsDialog dialog;
    public static final int TYPE_PAY = 1;
    public static final int TYPE_CHECK = 0;
    String content = "", left = "", right;
    private EditText et_scan, et_order, et_sku, et_mobile;
    private String useName = "";
    private int h;
    private int w;
    private String pay = "";
    private ExchanegGoodBean goodBean;
    private ExchangeGoodsAdpater adpater;

    public void setGoodBean(ExchanegGoodBean goodBean) {
        this.goodBean = goodBean;
        if (goodBean != null) {
            for (ExchanegGoodBean.ItemListBean bean : goodBean.getItemList()) {
                bean.setUid(UUID.randomUUID().toString());
                bean.setExchangQty(bean.getQty());
            }
            if (adpater != null)
                adpater.setNewData(goodBean.getItemList());
        } else {
            if (adpater != null)
                adpater.setNewData(null);
        }
    }

    public ExchangeGoodsDialog setPay(String pay) {
        this.pay = pay;
        return this;
    }

    public ExchangeGoodsDialog setUseName(String useName) {
        this.useName = useName;
        return this;
    }
    public void reMoveItem(ExchanegGoodBean.ItemListBean item){
      if (adpater!=null)
          adpater.reMoveItem(item);
    }
    public enum DialogType {
        D_ADD, D_EDIT
    }

    public void setIsShowDismiss(boolean showDismiss) {
        isShowDismiss = showDismiss;
        mBtnCancel.setVisibility(isShowDismiss ? View.VISIBLE : View.GONE);
    }

    public static ExchangeGoodsDialog getInstance(Activity activity) {
        while (activity.getParent() != null) {
            activity = activity.getParent();
        }
        if (dialog == null) {
            dialog = new ExchangeGoodsDialog(activity);
        }
        return dialog;
    }

    public ExchangeGoodsDialog(Activity activity) {
        super(activity, R.style.popDialog);
        this.mActivity = activity;
        initViews();
    }

    public View getmRootView() {
        return mRootView;
    }


    private void initViews() {

        h = mActivity.getResources().getDisplayMetrics().heightPixels;
        w = mActivity.getResources().getDisplayMetrics().widthPixels;
        mRootView = LayoutInflater.from(mActivity).inflate(R.layout.exchange_goods_dialog, null);
        //设置dialog的宽高为屏幕的宽高
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(w * 4 / 5, h * 4 / 5);
        this.setContentView(mRootView, layoutParams);
        tv_search = (TextView) mRootView.findViewById(R.id.tv_search);
        tv_search.setOnClickListener(this);
        recyclerView = (RecyclerView) mRootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        adpater = new ExchangeGoodsAdpater(mActivity);
        adpater.setOkChangQty(new ExchangeGoodsAdpater.OkChangQty() {
            @Override
            public void OnOkQty(ExchanegGoodBean.ItemListBean item) {
                if (item!=null){
                    if (item.getExchangQty()>0){
                        if (mPositivePopDialogListener!=null)
                            mPositivePopDialogListener.onOkAndRemove(item);
//                        dismiss();
//                        dialog = null;
                    }else {
                        ViewHub.showShortToast(mActivity,"请填写大于0换货数量");
                    }
                }else {
                    ViewHub.showShortToast(mActivity,"商品不能为空");
                }
            }
        });
        recyclerView.setAdapter(adpater);
        iv_del = (TextView) mRootView.findViewById(R.id.iv_del);
        iv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_scan != null) {
                    et_scan.setFocusable(false);
                    et_scan.setFocusableInTouchMode(false);
                    et_scan.setEnabled(false);
                }
                dismiss();
                dialog = null;
            }
        });
        et_scan = (EditText) mRootView.findViewById(R.id.et_scan);
        et_order = (EditText) mRootView.findViewById(R.id.et_order);
        et_sku = (EditText) mRootView.findViewById(R.id.et_sku);
        et_mobile = (EditText) mRootView.findViewById(R.id.et_mobile);
        et_scan.setFocusable(true);
        et_scan.setFocusableInTouchMode(true);
        et_scan.requestFocus();
        et_scan.setEnabled(true);
        et_scan.setOnKeyListener(new View.OnKeyListener()

        {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER) {
                    gotoSearch();
//                    dialog = null;
//                    dismiss();
                }
                return false;

            }
        });
        FunctionHelper.hideSoftInput(mActivity);
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                FunctionHelper.hideSoftInput(mActivity);
//                et_scan.setFocusable(false);
//                et_scan.setFocusableInTouchMode(false);
//                et_scan.setEnabled(false);
                finishDialog();
            }
        });
        OnKeyListener keylistener = new OnKeyListener() {
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        };

        setOnKeyListener(keylistener);
        setCancelable(false);
        setCanceledOnTouchOutside(false);

//        mRootView.setOnTouchListener(new OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int height = mContentViewBg.getTop();
//                int bottom = mContentViewBg.getBottom();
//
//                int y = (int)event.getY();
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    if (y < height || y > bottom) {
//                        dismiss();
//                    }
//                }
//                return true;
//            }
//        });

       /* this.setWidth(LayoutParams.MATCH_PARENT);
        this.setHeight(LayoutParams.MATCH_PARENT);*/

//        this.setFocusable(true);
//        ColorDrawable dw = new ColorDrawable(0xb0000000);
//        this.setBackgroundDrawable(dw);
//        setAnimationStyle(R.style.LightPopDialogAnim);
    }

    private void gotoSearch() {
        String scanTxt = et_scan.getText().toString().trim();
//                    et_scan.setHint(scan + "");
//                    et_scan.setText("");
//                    String scanTxt = et_scan.getHint().toString().trim();
//                    et_scan.setFocusable(false);
//                    et_scan.setFocusableInTouchMode(false);
//                    et_scan.clearFocus();
//                    et_scan.setEnabled(false);
        String scanTxt1 = et_order.getText().toString();
        String scanTxt2 = et_sku.getText().toString();
        String scanTxt3 = et_mobile.getText().toString();
        if (mPositivePopDialogListener != null) {
            mPositivePopDialogListener.onGetReturnOrder(scanTxt, scanTxt1, scanTxt2, scanTxt3);
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (dialog != null)
            dialog = null;
    }

    public void showDialog() {
//        if (!TextUtils.isEmpty(content)) {
//            if (tv_title != null)
//                tv_title.setText(content);
//        }   if (mTvMessage!=null)
//        if (type== DialogType.D_EDIT){
//            if (et_use_name!=null) {
//                et_use_name.setText(useName);
//                et_use_name.setCursorVisible(false);
//                et_use_name.setFocusable(false);
//                et_use_name.setFocusableInTouchMode(false);
//            }
//        }
        try {
            Properties prop = new Properties();
            prop.setProperty("activity_is_null", (mActivity == null) + "-时间：" + System.currentTimeMillis());
            prop.setProperty("activity_is_finishing", (mActivity.isFinishing()) + "-时间：" + System.currentTimeMillis());
            StatService.trackCustomKVEvent(BWApplication.getInstance(), "exchange_goods_dialog", prop);
            if (mActivity != null) {
                if (!mActivity.isFinishing()) {

                    if (dialog != null)
                        dialog.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            finishDialog();
            CrashReport.postCatchedException(e);
        }
    }

    private void finishDialog() {
        if (dialog != null) {
            if (dialog.isShowing())
                dialog.dismiss();
            dialog = null;
        }
    }
//    public void show() {
//        DisplayMetrics dm = new DisplayMetrics();
//        mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
//        // int screenWidth = dm.widthPixels;
//        int screenHeight = dm.heightPixels;
//        int top = mContentViewBg.getTop();
//        int bottom = mContentViewBg.getBottom();
//        showAtLocation(mActivity.getWindow().getDecorView(), Gravity.CENTER, 0, screenHeight / 2 - (bottom - top) / 2);
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_search:
                gotoSearch();
                break;
        }

    }

    DialogType type;

    public ExchangeGoodsDialog setDialogType(DialogType type) {
        this.type = type;
        return this;
    }

    public ExchangeGoodsDialog setContent(String content) {
        this.content = content;
        return this;
    }

    public ExchangeGoodsDialog setLeftStr(String left) {
        this.left = left;
        return this;
    }

    public ExchangeGoodsDialog setRightStr(String right) {
        this.right = right;
        return this;
    }


    public ExchangeGoodsDialog setOnExchangeGoodsOncClick(PopDialogListener listener) {
        mPositivePopDialogListener = listener;
        return this;
    }


    public void addContentView(View child) {
        mContentView.addView(child);
    }

    public interface PopDialogListener {
        void onGetReturnOrder(String scanCode, String orderCode, String itemCode, String mobile);
        void  onOkAndRemove(ExchanegGoodBean.ItemListBean item);
    }

}
