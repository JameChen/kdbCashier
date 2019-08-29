package com.yiku.kdb_flat.model.http.api;

public class HttpUtils {
    private static final String TAG = "HttpUtils";
    public static final boolean IS_LOCAL = false;//true=内网/false=外网
    /*=============================*/
    public static final String VERSION_V3 = "v3/";
    public static final String VERSION_TEST = "test/";
    public static final String VERSION = VERSION_V3;
    //友盟开启调试模式打包发版时候可以设置false
    public static final boolean UmengDebug = false;
    //环信控制值true为打开false关闭
    public static final boolean ECC_OPEN = true;

    /*=============================*/
    public static final String BASE_URL = IS_LOCAL ? "http://local.api2.nahuo.com/" : "http://api2.nahuo.com/";
    public static final String SERVERURL_V4 = BASE_URL + (IS_LOCAL ? VERSION : "V4/");
    public static final String URL = BASE_URL + VERSION;
    public static final String SERVERURL = URL;
    public static final String SERVER_404 = "未找到服务器地址。\n请稍后再试";
    public static final String SERVER_UNKONW_ERROR = "访问失败。\n请稍后再试";
    private static final String SERVER_CONNECT_ERROR = "访问服务器失败。\n请稍后再试";
    public static final String SERVER_500 = "啊哦，服务器出现问题了，正在维护中";
    public static final String SERVER_403 = "服务器拒绝您的访问。\n请稍后再试";
    public static final String SERVER_405 = "服务器拒绝您的访问。\n请稍后再试";
    public static final String SERVER_504 = "服务器拒绝您的访问。\n请稍后再试";
    private static final String SERVER_ERROR = "啊哦，访问服务器失败了呢。\n请稍后再试";
    private static final String SERVER_TIME_OUT = "读取数据失败，请检查您的网络是否正常";
    private static final String SERVER_NO_NETWORK = "您未连接网络，请接入网络后再试";
    public static final String CONNECT_TIME_OUT = "网络连接超时";
    public static final String SERVER_NOT_FOUND_SERVER = "您的网络访问不到服务器，请切换另一个网络试试";
    public static final String SERVER_NOT_NET = "请检查网络或稍候重试";
    private static final int REQUEST_TIMEOUT = 60 * 1000;                  // 设置请求超时30秒钟
    private static final int SO_TIMEOUT = 60 * 1000;                  // 设置等待数据超时时
    private static final String User_Agent = "User-Agent";

    public static final String JudeApiUrl(String url) {
        String ss = "";
        if (url.contains("user/user")) {
            url = SERVERURL_V4 + url;
        }
        return ss;
    }

}
