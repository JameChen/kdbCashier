package com.yiku.kdb_flat.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
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

import java.util.Properties;


public class PayCashDialog extends Dialog implements View.OnClickListener {

    private Activity mActivity;
    private View mRootView;
    private LinearLayout mContentViewBg;
    private TextView tv_money, tv_chang, tv01, tv02, tv03, tv04, tv_settle, iv_del, tv_pos;
    private View ll_content;
    private Button mBtnCancel, mBtnOK;
    private PopDialogListener mPositivePopDialogListener;

    private LinearLayout mContentView;
    private boolean isShowDismiss = true;
    static PayCashDialog dialog;
    public static final int TYPE_PAY = 1;
    public static final int TYPE_CHECK = 0;
    String content = "", left = "", right;
    private EditText et_txt;
    private String useName = "";
    private int h;
    private int w;
    private String pay = "0.00";
    private double mPay;
    private int payType;

    public PayCashDialog setPay(String pay) {
        this.pay = pay;
        if (TextUtils.isEmpty(pay)) {
            this.mPay = 0;
            this.pay = "0.00";
        }
        this.mPay = Double.parseDouble(pay);
        return this;
    }

    public PayCashDialog setPayType(int payType) {
        this.payType = payType;
        return this;
    }

    public PayCashDialog setUseName(String useName) {
        this.useName = useName;
        return this;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (dialog!=null)
            dialog=null;
    }

    public enum DialogType {
        D_ADD, D_EDIT
    }

    public void setIsShowDismiss(boolean showDismiss) {
        isShowDismiss = showDismiss;
        mBtnCancel.setVisibility(isShowDismiss ? View.VISIBLE : View.GONE);
    }

    public static PayCashDialog getInstance(Activity activity) {
        while (activity.getParent()!=null){
            activity=activity.getParent();
        }
        if (dialog == null) {
            dialog = new PayCashDialog(activity);
        }
        return dialog;
    }



    public PayCashDialog(Activity activity) {
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
        mRootView = LayoutInflater.from(mActivity).inflate(R.layout.cash_dialog, null);
        //设置dialog的宽高为屏幕的宽高
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(w * 2 / 3, h * 2 / 3);
        this.setContentView(mRootView, layoutParams);
        tv_money = (TextView) mRootView.findViewById(R.id.tv_money);
        tv_chang = (TextView) mRootView.findViewById(R.id.tv_chang);
        tv_settle = (TextView) mRootView.findViewById(R.id.tv_settle);
        tv_settle.setOnClickListener(this);
        iv_del = (TextView) mRootView.findViewById(R.id.iv_del);
        tv_pos = (TextView) mRootView.findViewById(R.id.tv_pos);
        iv_del.setOnClickListener(this);
        tv01 = (TextView) mRootView.findViewById(R.id.tv01);
        tv02 = (TextView) mRootView.findViewById(R.id.tv02);
        tv03 = (TextView) mRootView.findViewById(R.id.tv03);
        tv04 = (TextView) mRootView.findViewById(R.id.tv04);
        ll_content = mRootView.findViewById(R.id.ll_content);
        tv01.setOnClickListener(this);
        tv02.setOnClickListener(this);
        tv03.setOnClickListener(this);
        tv04.setOnClickListener(this);

        et_txt = (EditText) mRootView.findViewById(R.id.et_txt);
        et_txt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        et_txt.setCursorVisible(true);
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                FunctionHelper.hideSoftInput(mActivity);
            }
        });
        et_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_txt.setFocusable(true);
                et_txt.setFocusableInTouchMode(true);
                et_txt.requestFocus();
            }
        });
        et_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String c = s.toString().trim();
                double p = 0;
                if (TextUtils.isEmpty(c)) {
                    c = "0.00";
                }
                try {
                    p = Double.parseDouble(c);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    p = 0;
                } finally {
                    mPay = p;
                    if (tv_chang != null) {
                        if (p > 0) {
                            double t = p - Double.parseDouble(pay);
                            tv_chang.setText(FunctionHelper.DoubleFormat(t));
                        } else {
                            tv_chang.setText("0");
                        }
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

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
            prop.setProperty("pay_type", payType+"");
            prop.setProperty("activity_is_null", (mActivity==null)+"-时间："+System.currentTimeMillis());
            prop.setProperty("activity_is_finishing", (mActivity.isFinishing())+"-时间："+System.currentTimeMillis());
            StatService.trackCustomKVEvent(BWApplication.getInstance(), "pay_dialog_cash_online", prop);
            if (mActivity != null) {
                if (!mActivity.isFinishing()) {
                    if (payType == 1) {
                        if (tv_pos != null)
                            tv_pos.setVisibility(View.GONE);
                        if (ll_content != null)
                            ll_content.setVisibility(View.VISIBLE);
                    } else if (payType == 4) {
                        if (tv_pos != null)
                            tv_pos.setVisibility(View.VISIBLE);
                        if (ll_content != null)
                            ll_content.setVisibility(View.GONE);
                    }
                    if (tv_money != null)
                        tv_money.setText("应收：" + pay);
                    if (tv_chang != null) {
                        //double t = mPay - Double.parseDouble(pay);
                        tv_chang.setText("0");
                    }
                    if (et_txt != null) {
                        et_txt.setText("0");
                        et_txt.setSelection(et_txt.getText().length());
                        et_txt.setFocusable(false);
                        et_txt.setFocusableInTouchMode(false);
                        et_txt.clearFocus();
                    }
                    FunctionHelper.hideSoftInput(mActivity);
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
            case R.id.iv_del:
                FunctionHelper.hideSoftInput(mActivity);
                dismiss();
                dialog = null;
                break;
            case R.id.tv_settle:
                FunctionHelper.hideSoftInput(mActivity);
                if (mPositivePopDialogListener != null) {
                    mPositivePopDialogListener.onCashPayClick();
                }
//                dismiss();
//                dialog = null;
                break;
            case R.id.tv01:
                mPay = mPay + 10;
                if (et_txt != null) {
                    et_txt.setText(FunctionHelper.DoubleFormat(mPay));
                    et_txt.setSelection(et_txt.getText().length());
                }
                if (tv_chang != null) {
                    double t = mPay - Double.parseDouble(pay);
                    tv_chang.setText(FunctionHelper.DoubleFormat(t));
                }
                break;
            case R.id.tv02:
                mPay = mPay + 20;
                if (et_txt != null) {
                    et_txt.setText(FunctionHelper.DoubleFormat(mPay));
                    et_txt.setSelection(et_txt.getText().length());
                }
                if (tv_chang != null) {
                    double t = mPay - Double.parseDouble(pay);
                    tv_chang.setText(FunctionHelper.DoubleFormat(t));
                }
                break;
            case R.id.tv03:
                mPay = mPay + 50;
                if (et_txt != null) {
                    et_txt.setText(FunctionHelper.DoubleFormat(mPay));
                    et_txt.setSelection(et_txt.getText().length());
                }
                if (tv_chang != null) {
                    double t = mPay - Double.parseDouble(pay);
                    tv_chang.setText(FunctionHelper.DoubleFormat(t));
                }
                break;
            case R.id.tv04:
                mPay = mPay + 100;
                if (et_txt != null) {
                    et_txt.setText(FunctionHelper.DoubleFormat(mPay));
                    et_txt.setSelection(et_txt.getText().length());
                }
                if (tv_chang != null) {
                    double t = mPay - Double.parseDouble(pay);
                    tv_chang.setText(FunctionHelper.DoubleFormat(t));
                }
                break;
        }
    }

    DialogType type;

    public PayCashDialog setDialogType(DialogType type) {
        this.type = type;
        return this;
    }

    public PayCashDialog setContent(String content) {
        this.content = content;
        return this;
    }

    public PayCashDialog setLeftStr(String left) {
        this.left = left;
        return this;
    }

    public PayCashDialog setRightStr(String right) {
        this.right = right;
        return this;
    }


    public PayCashDialog setCashPayOncClick(PopDialogListener listener) {
        mPositivePopDialogListener = listener;
        return this;
    }


    public void addContentView(View child) {
        mContentView.addView(child);
    }

    public interface PopDialogListener {
        void onCashPayClick();
    }

}
