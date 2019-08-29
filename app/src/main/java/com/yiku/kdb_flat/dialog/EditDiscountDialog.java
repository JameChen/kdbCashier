package com.yiku.kdb_flat.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yiku.kdb_flat.R;
import com.yiku.kdb_flat.custom_controls.ViewHub;


public class EditDiscountDialog extends Dialog implements View.OnClickListener {

    private Activity mActivity;
    private View mRootView;
    private LinearLayout mContentViewBg;
    private TextView mTvTitle;
    TextView mTvMessage;
    private Button mBtnCancel, mBtnOK;
    private PopDialogListener mPositivePopDialogListener;
    private PopDialogListener mNegativePopDialogListener;
    private LinearLayout mContentView;
    private boolean isShowDismiss = true;
    static EditDiscountDialog dialog;
    public static final int BUTTON_POSITIVIE = 1;
    public static final int BUTTON_NEGATIVE = 0;
    String content = "", left = "", right;
    private EditText et_txt;
    private String useName = "";
    public final static int TYPE_DISCOUNT = 1;
    public final static int TYPE_CARD_NO = 2;
    public final static int TYPE_REDUE = 3;
    public final static int TYPE_INTEGRAL = 4;
    private int Type;

    public EditDiscountDialog setType(int type) {
        Type = type;
        return this;
    }

    public EditDiscountDialog setUseName(String useName) {
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

    public static EditDiscountDialog getInstance(Activity activity) {
        if (dialog == null) {
            dialog = new EditDiscountDialog(activity);
        }
        return dialog;
    }

    public EditDiscountDialog(Activity activity) {
        super(activity, R.style.popDialog);
        this.mActivity = activity;
        initViews();
    }

    public View getmRootView() {
        return mRootView;
    }


    private void initViews() {
        mRootView = mActivity.getLayoutInflater().inflate(R.layout.light_popwindow_dialog_discount_edit, null);
        mContentViewBg = (LinearLayout) mRootView.findViewById(R.id.contentView);
        mTvTitle = (TextView) mContentViewBg.findViewById(R.id.tv_title);
        mContentView = (LinearLayout) mRootView.findViewById(R.id.ll_content);
        mTvMessage = (TextView) mContentViewBg.findViewById(R.id.tv_message);
        mBtnCancel = (Button) mContentViewBg.findViewById(R.id.btn_cancle);
        mBtnOK = (Button) mContentViewBg.findViewById(R.id.btn_ok);
        et_txt = (EditText) mContentView.findViewById(R.id.et_txt);
        mBtnCancel.setOnClickListener(this);
        mBtnOK.setOnClickListener(this);
        if (mBtnOK != null)
            mBtnOK.setText("确定");
        if (mBtnCancel != null)
            mBtnCancel.setText("取消");

        setCanceledOnTouchOutside(false);
        OnKeyListener keylistener = new OnKeyListener() {
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        this.setContentView(mRootView);
        setOnKeyListener(keylistener);
        setCancelable(true);
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

        switch (Type) {
            case TYPE_DISCOUNT:
                if (mTvMessage != null)
                    mTvMessage.setText("修改折扣");
                if (et_txt != null) {
                    et_txt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    et_txt.setHint("请填写折扣");
                }
                break;
            case EditDiscountDialog.TYPE_CARD_NO:
                //会员号
                if (mTvMessage != null)
                    mTvMessage.setText("填写或扫描会员号");
                if (et_txt != null) {
                    et_txt.setInputType(InputType.TYPE_CLASS_NUMBER);
                    et_txt.setHint("请填写或扫描会员号");
                }
                break;
            case EditDiscountDialog.TYPE_REDUE:
                //减免
                if (mTvMessage != null)
                    mTvMessage.setText("修改金额");
                if (et_txt != null) {
                    et_txt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    et_txt.setHint("请填写金额");
                }
                break;
            case EditDiscountDialog.TYPE_INTEGRAL:
                if (mTvMessage != null)
                    mTvMessage.setText("修改积分");
                if (et_txt != null) {
                    et_txt.setInputType(InputType.TYPE_CLASS_NUMBER );
                    et_txt.setHint("请填写积分");
                }
                break;
        }
        if (dialog != null)
            dialog.show();
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
        if (id == R.id.btn_ok) {
            String userName = "";
            if (et_txt != null)
                userName = et_txt.getText().toString().trim();
            if (TextUtils.isEmpty(userName)) {
                ViewHub.showLongToast(mActivity, "请输入值");
                return;
            }
            try {
                if (Type == EditDiscountDialog.TYPE_DISCOUNT || Type == EditDiscountDialog.TYPE_REDUE) {
                    if (TextUtils.isEmpty(userName)) {
                        useName = "0";
                    }
                    double d = Double.parseDouble(userName);
                    userName = d + "";
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                userName = "0";
            } finally {
                if (mPositivePopDialogListener != null) {
                    mPositivePopDialogListener.onPopDialogButtonClick(BUTTON_POSITIVIE, Type, userName);
                }
                dialog = null;
                dismiss();
            }

        } else if (id == R.id.btn_cancle) {
            if (mPositivePopDialogListener != null) {
                mPositivePopDialogListener.onPopDialogButtonClick(BUTTON_NEGATIVE, Type, "");
            }
            dialog = null;
            dismiss();
        }
    }

    DialogType type;

    public EditDiscountDialog setDialogType(DialogType type) {
        this.type = type;
        return this;
    }

    public EditDiscountDialog setContent(String content) {
        this.content = content;
        return this;
    }

    public EditDiscountDialog setLeftStr(String left) {
        this.left = left;
        return this;
    }

    public EditDiscountDialog setRightStr(String right) {
        this.right = right;
        return this;
    }

    public EditDiscountDialog setIcon(int resId) {
        mTvTitle.setCompoundDrawablesWithIntrinsicBounds(resId, 0, 0, 0);
        return this;
    }

    public EditDiscountDialog setIcon(Drawable icon) {
        mTvTitle.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
        return this;
    }

    public EditDiscountDialog setMessage(CharSequence message) {
        mTvMessage.setText(message);
        return this;
    }

    public EditDiscountDialog setMessage(int resId) {
        mTvMessage.setText(resId);
        return this;
    }

    public EditDiscountDialog setNegative(CharSequence text, PopDialogListener listener) {
        mBtnCancel.setText(text);
        mNegativePopDialogListener = listener;
        return this;
    }

    public EditDiscountDialog setNegative(int resId, PopDialogListener listener) {
        mBtnCancel.setText(resId);
        mNegativePopDialogListener = listener;
        return this;
    }

    public EditDiscountDialog setPositive(PopDialogListener listener) {
        mPositivePopDialogListener = listener;
        return this;
    }


    public void addContentView(View child) {
        mContentView.addView(child);
    }

    public interface PopDialogListener {
        void onPopDialogButtonClick(int ok_cancel, int type,
                                    String txt);
    }

}
