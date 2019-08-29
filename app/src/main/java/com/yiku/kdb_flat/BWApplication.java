package com.yiku.kdb_flat;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.display.DisplayManager;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.stat.StatConfig;
import com.tencent.stat.StatService;
import com.yiku.kdb_flat.double_screen.DifferentDislay;
import com.yiku.kdb_flat.utils.SpManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class BWApplication extends Application {
    public static String currentUserNick = "";
    //  private Map<String, ChatUserModel> mcontactList, mconversionList;
    public static String PayOrderId = "";
    public static String RECHARGECODE = "";
    public static double PayMoney = 0;
    public static String PayMode = "";
    public static String PINHUPTITLE = "";
    public static boolean addisEdit = false;
    private static BWApplication instance;
    public static List<Activity> pList = new ArrayList<>();
    public static List<Activity> shopCartList = new ArrayList<>();
    public static List<Activity> startList = new ArrayList<>();
    public static String KDB_FLAT_LOGGER = "KDB_FLAT_LOGGER";

    public static BWApplication getInstance() {
        return instance;
    }

    public static int PrinterId = 0;
    private List<WeakReference<Activity>> activities = new LinkedList<WeakReference<Activity>>();

    public static void addStartActivity(Activity activity) {
        if (!startList.contains(activity)) {
            startList.add(activity);
        }
    }

    public static void removeStart() {
        try {
            for (Activity activity : startList) {
                activity.finish();
            }
            startList.clear();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void addActivity(Activity activity) {
        if (!pList.contains(activity)) {
            pList.add(activity);
        }
    }

    public static void addToShorpCartActivity(Activity activity) {
        if (!shopCartList.contains(activity)) {
            shopCartList.add(activity);
        }
    }

    public static void reBackFirst() {
        try {
            for (Activity activity : pList) {
                activity.finish();
            }
            pList.clear();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void goBackShopCart() {
        try {
            for (Activity activity : shopCartList) {
                activity.finish();
            }
            shopCartList.clear();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static String UserAgent = "";

    public void setUserAgent() {
        WebView webView = new WebView(instance);
        WebSettings settings = webView.getSettings();
        UserAgent = settings.getUserAgentString();
        SpManager.setUserAgent(instance, UserAgent);
    }

    public String getUserAgent() {
        if (TextUtils.isEmpty(UserAgent)) {
            UserAgent = SpManager.getUserAgent(instance);
        }
        return UserAgent;
    }

    static {
        //设置全局的Header构建器
//        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
//            @Override
//            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
//                layout.setPrimaryColorsId(R.color.my_colorPrimary, android.R.color.white);//全局设置主题颜色
//                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
//            }
//        });
//        //设置全局的Footer构建器
//        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
//            @Override
//            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
//                //指定为经典Footer，默认是 BallPulseFooter
//                return new ClassicsFooter(context).setDrawableSize(20);
//            }
//        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        // [可选]设置是否打开debug输出，上线时请关闭，Logcat标签为"MtaSDK"
        StatConfig.setDebugEnable(true);
        // 基础统计API
        StatService.registerActivityLifecycleCallbacks(this);
        CrashReport.initCrashReport(getApplicationContext(), "d62e9f4924", true);
        instance = this;
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(2)         // (Optional) How many method line to show. Default 2
                .methodOffset(0)        // (Optional) Hides internal method calls up to offset. Default 5
//                .logStrategy(new LogStrategy() {
//                    @Override
//                    public void log(int priority, String tag, String message) {
//
//                    }
//                }) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag(KDB_FLAT_LOGGER)   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }

        });
        setUserAgent();
        DisplayManager manager = (DisplayManager) getSystemService(Context.DISPLAY_SERVICE);
        Display[] displays = manager.getDisplays();
        // displays[0] 主屏
        // displays[1] 副屏
        try {
            if (displays.length > 1) {
                DifferentDislay differentDislay = new DifferentDislay(BWApplication.getInstance(), displays[1]);
                differentDislay.getWindow().setType(
                        WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                differentDislay.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this
                .getSystemService(ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> l = am.getRunningAppProcesses();
        Iterator<RunningAppProcessInfo> i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            RunningAppProcessInfo info = i.next();
            try {
                if (info.pid == pID) {
                    pm.getApplicationLabel(pm.getApplicationInfo(
                            info.processName, PackageManager.GET_META_DATA));
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("BWApplication", e.toString());
            }
        }
        return processName;
    }


    /**
     * 注册activity
     *
     * @param activity
     */
    public void registerActivity(Activity activity) {
        activities.add(new WeakReference<Activity>(activity));
    }

}
