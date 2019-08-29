package com.yiku.kdb_flat.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.nahuo.library.helper.FunctionHelper;
import com.tencent.bugly.crashreport.CrashReport;
import com.yiku.kdb_flat.R;


public class PayDialog extends Dialog implements View.OnClickListener {

    private Activity mActivity;
    private View mRootView;
    private LinearLayout mContentViewBg;
    private TextView tv_money, tv_chang, tv01, tv02, tv03, tv04, tv_settle, iv_del;
    private Button mBtnCancel, mBtnOK;
    private PopDialogListener mPositivePopDialogListener;

    private LinearLayout mContentView;
    private boolean isShowDismiss = true;
    static PayDialog dialog;
    public static final int TYPE_PAY = 1;
    public static final int TYPE_CHECK = 0;
    String content = "", left = "", right;
    private EditText et_txt;
    private String useName = "";
    private int h;
    private int w;
    private String pay = "0.00";
    private double mPay;
    private RadioGroup rGroup;
    private int pay_type;
    private RadioButton rb_cash_pay;

    public PayDialog setPay(String pay) {
        this.pay = pay;
        if (TextUtils.isEmpty(pay)) {
            this.mPay = 0;
            this.pay = "0.00";
        }
        this.mPay = Double.parseDouble(pay);
        return this;
    }

    public PayDialog setUseName(String useName) {
        this.useName = useName;
        return this;
    }

    public enum DialogType {
        D_ADD, D_EDIT
    }

    public void setIsShowDismiss(boolean showDismiss) {
        isShowDismiss = showDismiss;
        mBtnCancel.setVisibility(isShowDismiss ? View.VISIBLE : View.GONE);
    }

    public static PayDialog getInstance(Activity activity) {
        while (activity.getParent() != null) {
            activity = activity.getParent();
        }
        if (dialog == null) {
            dialog = new PayDialog(activity);
        }
        return dialog;
    }

    public PayDialog(Activity activity) {
        super(activity, R.style.popDialog);
        this.mActivity = activity;
        initViews();
    }

    public View getmRootView() {
        return mRootView;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (dialog != null)
            dialog = null;
    }

    private void initViews() {

        h = mActivity.getResources().getDisplayMetrics().heightPixels;
        w = mActivity.getResources().getDisplayMetrics().widthPixels;
        mRootView = LayoutInflater.from(mActivity).inflate(R.layout.pay_dialog, null);
        //设置dialog的宽高为屏幕的宽高
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(w * 2 / 3, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setContentView(mRootView, layoutParams);
        rGroup = (RadioGroup) mRootView.findViewById(R.id.rGroup);
        rb_cash_pay = (RadioButton) mRootView.findViewById(R.id.rb_cash_pay);
        if (rb_cash_pay != null) {
            rb_cash_pay.setChecked(true);
        }
        pay_type = 1;
        if (rGroup != null) {
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
        }
//        tv_money = (TextView) mRootView.findViewById(R.id.tv_money);
//        tv_chang = (TextView) mRootView.findViewById(R.id.tv_chang);
        tv_settle = (TextView) mRootView.findViewById(R.id.tv_settle);
        tv_settle.setOnClickListener(this);
//        iv_del = (TextView) mRootView.findViewById(R.id.iv_del);
//        iv_del.setOnClickListener(this);
//        tv01 = (TextView) mRootView.findViewById(R.id.tv01);
//        tv02 = (TextView) mRootView.findViewById(R.id.tv01);
//        tv03 = (TextView) mRootView.findViewById(R.id.tv01);
//        tv04 = (TextView) mRootView.findViewById(R.id.tv01);
//        tv01.setOnClickListener(this);
//        tv02.setOnClickListener(this);
//        tv03.setOnClickListener(this);
//        tv04.setOnClickListener(this);
//
//        et_txt = (EditText) mRootView.findViewById(R.id.et_txt);
//        FunctionHelper.hideSoftInput(mActivity);
//        setOnDismissListener(new OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                FunctionHelper.hideSoftInput(mActivity);
//                et_txt.setFocusable(false);
//                et_txt.setFocusableInTouchMode(false);
//                et_txt.setEnabled(false);
//            }
//        });
//        et_txt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                String c = s.toString().trim();
//                double p = 0;
//                if (TextUtils.isEmpty(c)) {
//                    c = "0.00";
//                }
//                try {
//                    p = Double.parseDouble(c);
//                } catch (NumberFormatException e) {
//                    e.printStackTrace();
//                    p = 0;
//                } finally {
//                    mPay = p;
//                    if (tv_chang != null) {
//                        double t = p - Double.parseDouble(pay);
//                        tv_chang.setText(FunctionHelper.DoubleFormat(t));
//                    }
//                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
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

        setCancelable(true);
        setCanceledOnTouchOutside(true);

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
//        if (tv_money != null)
//            tv_money.setText("应收：" + pay);
//        if (tv_chang != null) {
//            double t = mPay - Double.parseDouble(pay);
//            tv_chang.setText(FunctionHelper.DoubleFormat(t));
//        }
//        if (et_txt != null) {
//            et_txt.setText(FunctionHelper.DoubleFormat(mPay));
//            et_txt.setSelection(et_txt.getText().length());
//        }
        try {

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
            case R.id.iv_del:
                FunctionHelper.hideSoftInput(mActivity);
                dismiss();
                dialog = null;
                break;
            case R.id.tv_settle:
                if (mPositivePopDialogListener != null) {
                    mPositivePopDialogListener.onPayClick(pay_type);
                }
                dismiss();
                dialog = null;
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

    public PayDialog setDialogType(DialogType type) {
        this.type = type;
        return this;
    }

    public PayDialog setContent(String content) {
        this.content = content;
        return this;
    }

    public PayDialog setLeftStr(String left) {
        this.left = left;
        return this;
    }

    public PayDialog setRightStr(String right) {
        this.right = right;
        return this;
    }


    public PayDialog setPayOncClick(PopDialogListener listener) {
        mPositivePopDialogListener = listener;
        return this;
    }


    public void addContentView(View child) {
        mContentView.addView(child);
    }

    public interface PopDialogListener {
        void onPayClick(int type);
    }

}
