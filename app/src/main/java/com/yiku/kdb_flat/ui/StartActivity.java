package com.yiku.kdb_flat.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.nahuo.library.utils.TimeUtils;
import com.yiku.kdb_flat.R;
import com.yiku.kdb_flat.model.bean.PublicData;
import com.yiku.kdb_flat.ui.base.BaseAppCompatActivity;
import com.yiku.kdb_flat.utils.SpManager;

import java.util.Date;

public class StartActivity extends BaseAppCompatActivity {

    private static final String TAG = "StartActivity";
    private StartActivity vThis = this;

    private static final int STATE_GUIDE = 1; // 首次使用
    private static final int STATE_LOGIN = 2; // 进入登录界面
    private static final int STATE_MAIN = 3; // 进入主界面

    private ImageView ivBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        setContentView(R.layout.activity_start);
        ivBackground = (ImageView) findViewById(android.R.id.background);
        try {
            ivBackground.setImageResource(R.drawable.first_load_bg);
        } catch (OutOfMemoryError error) {
        }

        Thread loadThread = new Thread(myRunnable);
        loadThread.start();
    }

    private Runnable myRunnable = new Runnable() {

        @Override
        public void run() {
            try {

                // 休眠500毫秒，避免程序出现白屏
            Thread.sleep(1000);
            forward();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case STATE_GUIDE: // 进入引导界面
                    toGuide();
                    break;
                case STATE_LOGIN: // 进入登录界面
                    toLogin();
                    break;
                case STATE_MAIN: // 自动登录进入主界面
                    autoLogin();
                    break;
            }


        }
    };

    private void forward() {
        int state = 0;
        // 读取位置文件，判断程序是否首次使用
        boolean isFirst = SpManager.isFirstUseApp(vThis);
        if (isFirst) {
            Log.i(TAG, "进入引导界面");
            state = 1;
        } else {
            // 读取当前登录用户配置文件，存在Cookie信息时不在执行登录操作直接进入主界面，不存在则进入登录界面
            String cookie = SpManager.getCookie(vThis);
            if (!TextUtils.isEmpty(cookie.trim())) {
                PublicData.setCookie(vThis, cookie.trim());

                PublicData.mUserInfo.setUserID(SpManager.getUserId(vThis));
                PublicData.mUserInfo.setUserName(SpManager.getUserName(vThis));
                PublicData.mUserInfo.setSignature(SpManager.getSignature(vThis));

                Log.i(TAG, "Cookie值：" + PublicData.getCookie(vThis));
                state = 3;
            } else {
                Log.i(TAG, "进入登录界面");
                state = 2;
            }
        }

        switch (state) {
            case STATE_GUIDE: // 首次使用，进入引导界面
                myHandler.obtainMessage(STATE_GUIDE).sendToTarget();
                break;
            case STATE_LOGIN: // 首次登录、用户已注销、自动登录失败，进入登录界面
                myHandler.obtainMessage(STATE_LOGIN).sendToTarget();
                break;
            case STATE_MAIN: // 自动登录进入主界面
                myHandler.obtainMessage(STATE_MAIN).sendToTarget();
                break;
        }
    }

    /**
     * 进入引导界面
     */
    private void toGuide() {
        // 首次使用，记录首次使用时间等信息
        SpManager.setIsFirstUseApp(vThis, false);
        SpManager.setFirstUseTime(vThis, TimeUtils.dateToTimeStamp(new Date(), "yyyy-MM-dd HH:mm:ss"));
        // 进入引导界面
//        toOtherActivity(GuideActivity.class);
        toLogin();
    }

    /**
     * 进入登录界面
     */
    private void toLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();// 需要finish这个页面，否则无法退出应用
    }


    /**
     * 自动登录，登录成功进入主界面
     */
    private void autoLogin() {

        toOtherActivity(MainActivity.class);

    }

    private void toOtherActivity(Class<?> clazz) {
        Intent intent = new Intent(vThis, clazz);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
       // StatService.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
      //  StatService.onResume(this);
    }
}
