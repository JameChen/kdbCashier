package com.yiku.kdb_flat.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.text.InputType;
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


public class PayOnlineDialog extends Dialog implements View.OnClickListener {

    private Activity mActivity;
    private View mRootView;
    private LinearLayout mContentViewBg;
    private TextView tv_money, tv_summary, iv_del;
    private Button mBtnCancel, mBtnOK;
    private PopDialogListener mPositivePopDialogListener;

    private LinearLayout mContentView;
    private boolean isShowDismiss = true;
    static PayOnlineDialog dialog;
    public static final int TYPE_PAY = 1;
    public static final int TYPE_CHECK = 0;
    String content = "", left = "", right;
    private EditText et_txt;
    private String useName = "";
    private int h;
    private int w;
    private String pay = "";

    public PayOnlineDialog setPay(String pay) {
        this.pay = pay;
        return this;
    }

    public PayOnlineDialog setUseName(String useName) {
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

    public static PayOnlineDialog getInstance(Activity activity) {
        while (activity.getParent() != null) {
            activity = activity.getParent();
        }
        if (dialog == null) {
            dialog = new PayOnlineDialog(activity);
        }
        return dialog;
    }

    public PayOnlineDialog(Activity activity) {
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
        mRootView = LayoutInflater.from(mActivity).inflate(R.layout.online_dialog, null);
        //设置dialog的宽高为屏幕的宽高
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(w * 2 / 3, h * 2 / 3);
        this.setContentView(mRootView, layoutParams);
        tv_money = (TextView) mRootView.findViewById(R.id.tv_money);
        tv_summary = (TextView) mRootView.findViewById(R.id.tv_summary);
        iv_del = (TextView) mRootView.findViewById(R.id.iv_del);
        findViewById(R.id.tv_finish).setOnClickListener(this);
        iv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_txt != null) {
                    et_txt.setFocusable(false);
                    et_txt.setFocusableInTouchMode(false);
                    et_txt.setEnabled(false);
                }
                dismiss();
                dialog = null;
            }
        });
        et_txt = (EditText) mRootView.findViewById(R.id.et_txt);
        et_txt.setFocusable(true);
        et_txt.setFocusableInTouchMode(true);
        et_txt.requestFocus();
        et_txt.setEnabled(true);
        et_txt.setInputType(InputType.TYPE_NULL);
        et_txt.setOnKeyListener(new View.OnKeyListener()

        {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER) {
                    if (tv_summary != null)
                        tv_summary.setText("收款中.......");
                    String scan = et_txt.getText().toString().trim();
                    et_txt.setHint(scan + "");
                    et_txt.setText("");
                    String scanTxt = et_txt.getHint().toString().trim();
                    et_txt.setFocusable(false);
                    et_txt.setFocusableInTouchMode(false);
                    et_txt.clearFocus();
                    et_txt.setEnabled(false);
                    if (mPositivePopDialogListener != null) {
                        mPositivePopDialogListener.onlinePayClick(TYPE_PAY, scanTxt);
                    }
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
                et_txt.setFocusable(false);
                et_txt.setFocusableInTouchMode(false);
                et_txt.setEnabled(false);
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
            StatService.trackCustomKVEvent(BWApplication.getInstance(), "pay_dialog_online", prop);
            if (mActivity != null) {
                if (!mActivity.isFinishing()) {

                    if (tv_summary != null)
                        tv_summary.setText("请扫描二维码");
                    if (tv_money != null)
                        tv_money.setText("应收：" + pay);
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
        int id = v.getId();
        if (id == R.id.tv_finish) {
            if (mPositivePopDialogListener != null) {
                mPositivePopDialogListener.onlinePayClick(TYPE_CHECK, "");
            }
//            dismiss();
//            dialog = null;
        }
    }

    DialogType type;

    public PayOnlineDialog setDialogType(DialogType type) {
        this.type = type;
        return this;
    }

    public PayOnlineDialog setContent(String content) {
        this.content = content;
        return this;
    }

    public PayOnlineDialog setLeftStr(String left) {
        this.left = left;
        return this;
    }

    public PayOnlineDialog setRightStr(String right) {
        this.right = right;
        return this;
    }


    public PayOnlineDialog setOnlinePayOncClick(PopDialogListener listener) {
        mPositivePopDialogListener = listener;
        return this;
    }


    public void addContentView(View child) {
        mContentView.addView(child);
    }

    public interface PopDialogListener {
        void onlinePayClick(int type,
                            String txt);
    }

}
