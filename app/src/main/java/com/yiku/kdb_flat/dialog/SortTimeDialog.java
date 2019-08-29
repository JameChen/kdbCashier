package com.yiku.kdb_flat.dialog;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.TextView;

import com.nahuo.library.helper.FunctionHelper;
import com.yiku.kdb_flat.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jame on 2017/7/7.
 */

public class SortTimeDialog extends Dialog implements View.OnClickListener {
    static SortTimeDialog dialog = null;
    private Activity mActivity;
    View mRootView;
    TextView btn_ok, btn_cancle, tv_title;
    int h, w;
    PopDialogListener mPopDialogListener;
    List<Integer> propertyIDS;
    RecyclerView listView;
    List<String> data = new ArrayList<>();
    String type;
    int sort_type;
    int hasSelId;
    private String beginTime="",endTime="";
    private TextView tvBeginTime,tvEndTime;

    public SortTimeDialog setHasSelId(int hasSelId) {
        this.hasSelId = hasSelId;
        return this;
    }

    public SortTimeDialog(@NonNull Activity context) {
        super(context, R.style.popDialog);
        this.mActivity = context;
    }

    int choose_index = 0;

    public static SortTimeDialog getInstance(Activity activity) {
        if (dialog == null) {
            dialog = new SortTimeDialog(activity);
        }
        return dialog;
    }


    public SortTimeDialog setPropertyIDS(List<Integer> propertyIDS) {
        this.propertyIDS = propertyIDS;
        return this;
    }


    public SortTimeDialog setType(String type, int sort_type) {
        this.type = type;
        this.sort_type = sort_type;
        return this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    private void initViews() {
        h = mActivity.getResources().getDisplayMetrics().heightPixels;
        w = mActivity.getResources().getDisplayMetrics().widthPixels;
        mRootView = LayoutInflater.from(mActivity).inflate(R.layout.time_dialog, null);
        btn_ok = (TextView) mRootView.findViewById(R.id.tv_ok);
        btn_cancle = (TextView) mRootView.findViewById(R.id.tv_cancel);
        tv_title = (TextView) mRootView.findViewById(R.id.tv_title);
        tvBeginTime = (TextView)mRootView.findViewById(R.id.trade_log_search_begintime);
        tvEndTime = (TextView)mRootView.findViewById(R.id.trade_log_search_endtime);
        tvBeginTime.setOnClickListener(this);
        tvEndTime.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
        btn_cancle.setOnClickListener(this);
        setContentView(mRootView);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager m = mActivity.getWindowManager();
        Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes(); //获取对话框当前的参数值
        p.width = d.getWidth(); //宽度设置为屏幕
        p.height = d.getHeight() * 2 / 3;
        dialog.getWindow().setAttributes(p); //设置生效
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        Date begin = FunctionHelper.GetDateTime(-30);
        Date end = FunctionHelper.GetDateTime(0);
        beginTime = df1.format(begin);
        endTime = df1.format(end);
        tvBeginTime.setText("开始时间:"+beginTime);
        tvEndTime.setText("结束时间:"+endTime);


    }


    public void showDialog() {
//        if (!TextUtils.isEmpty(content)) {
//            if (tv_title != null)
//                tv_title.setText(content);
//        }
        this.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        dialog = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                dismiss();
                dialog = null;
                break;
            case R.id.tv_ok:
                if (mPopDialogListener != null) {
                        mPopDialogListener.onGetSortTimeDialogButtonClick(beginTime,endTime);
                }
                dismiss();
                dialog = null;
                break;
            case R.id.trade_log_search_begintime: {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(mActivity, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String time1 = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth ;

                        if (checkSearchTime(time1,endTime))
                        {
                            beginTime = time1;
                            tvBeginTime.setText("开始时间:" + beginTime);
                        }
                    }
                }, calendar.get(Calendar.YEAR), calendar
                        .get(Calendar.MONTH), calendar
                        .get(Calendar.DAY_OF_MONTH));
                dialog.show();
                break;
            }
            case R.id.trade_log_search_endtime: {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(mActivity, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String time2 = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

                        if (checkSearchTime(beginTime,time2))
                        {
                            endTime = time2;
                            tvEndTime.setText("结束时间:" + endTime);
                        }
                    }
                }, calendar.get(Calendar.YEAR), calendar
                        .get(Calendar.MONTH), calendar
                        .get(Calendar.DAY_OF_MONTH));
                dialog.show();
                break;
            }
        }
    }
    private boolean checkSearchTime(String time1,String time2)
    {
        try {
//            Date begin = FunctionHelper.StringToDate(time1,"yyyy-MM-dd");
//            Date end = FunctionHelper.StringToDate(time2,"yyyy-MM-dd");
//            long second = begin.getTime() - end.getTime();
//            long day = Math.abs(second/1000/60/60/24);
//            if (day>365*2)
//            {
//                ViewHub.showLongToast(mActivity,"搜索时间区间不能超过两年");
//                return false;
//            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public SortTimeDialog setPositive(PopDialogListener listener) {
        mPopDialogListener = listener;
        return this;
    }

    public interface PopDialogListener {
        void onGetSortTimeDialogButtonClick(String beginTime, String endTime);
    }
}
