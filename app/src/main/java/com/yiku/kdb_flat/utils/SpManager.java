package com.yiku.kdb_flat.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.yiku.kdb_flat.constant.Const;
import com.yiku.kdb_flat.model.bean.UserModel;


/**
 * Description: 对SharedPreference进行统一管理 2014-7-4下午3:46:34
 */
public class SpManager {
    public static final String APP_USRER_AGENT = "APP_USRER_AGENT";
    private static final String Seller_Users_ID = "Seller_Users_ID";
    private static final String Seller_Users_NAME = "Seller_Users_NAME";
    private static final String LOGIN_ACCOUNTS = "LOGIN_ACCOUNTS";
    private static final String LOGIN_ACCOUNT = "LOGIN_ACCOUNT";
    private static final String VISIBLE_RANAGE_IDS = "VISIBLE_RANAGE_IDS";
    private static final String VISIBLE_RANGE_NAMES = "VISIBLE_RANGE_NAMES";
    private static final String IS_FIRST_USE_APP = "IS_FIRST_USE_APP";
    private static final String HAS_CONTACT_MSG = "HAS_CONTACT_MSG";
    private static final String FIRST_USE_TIME = "FIRST_USE_TIME";
    // ======Shop
    private static final String SHOP_ID = "SHOP_ID";
    private static final String SHOP_NAME = "SHOP_NAME";
    private static final String SHOP_SIGNATURE = "SHOP_SIGNATURE";
    private static final String SHOP_MOBILE = "SHOP_MOBILE";
    private static final String SHOP_BANNER = "BANNER";
    private static final String SHOP_RECRUITDESC = "RECRUITDESC";
    private static final String SHOP_DOMAIN = "SHOP_DOMAIN";
    private static final String SHOP_LOGO = "SHOP_LOGO";
    private static final String SHOP_BACKGROUND = "SHOP_BACKGROUND";
    // =======User
    private static final String USER_NAME = "USER_NAME";
    private static final String USER_ID = "USER_ID";
    private static final String COOKIE = "COOKIE";
    // =======Version
    private static final String VERSION_3_TIP = "VERSION_3_TIP";
    /**
     * 用户绑定的手机号码
     */
    private static final String USER_BIND_PHONE_NUMS = "USER_BIND_PHONE_NUM";
    private static final String UPLOADED_ITEMS = "UPLOADED_ITEMS";
    private static final String SIGNATURE = "SIGNATURE";
    private static final String JINHUOPRICE = "JINHUOPRICE";
    private static final String LINGSHOUPRICE = "LINGSHOUPRICE";
    private static final String EDIT_TITLE = "EDIT_TITLE";
    private static final String EDIT_PRICE= "EDIT_PRICE";
    private static final String ISADD_GROUP= "ISADD_GROUP";
    private static final String LOGIN_STATE_IS_SUCESS = "LOGIN_STATE_IS_SUCESS";
    public static void setIs_Login(Context context, boolean never_show) {
        setBoolean(context, LOGIN_STATE_IS_SUCESS, never_show);
    }

    public static boolean getIs_Login(Context context) {
        return getBoolean(context, LOGIN_STATE_IS_SUCESS, false);
    }
    public static void setUserAgent(Context context, String value) {
        setString(context, APP_USRER_AGENT, value);
    }

    public static String getUserAgent(Context context) {
        return getString(context, APP_USRER_AGENT);
    }
    /**
     * @description 设置用户信息到sp
     * @created 2014-9-17 上午11:59:24
     * @author ZZB
     */
    public static void setUserInfo(Context context, UserModel user) {
        setSignature(context, user.getSignature());
        // setAllitemcount(context, user.getAllItemCount());
        // setAllagentcount(context, user.getAllAgentCount());
        // setAllvendorcount(context, user.getAllVendorCount());
        setUserId(context, user.getUserID());
        setUserName(context, user.getUserName());
    }

    /**
     * @description 删除用户信息
     * @created 2014-8-27 上午10:02:14
     * @author ZZB
     */
    public static void clearUserInfo(Context context) {
        remove(context, COOKIE, USER_NAME, USER_ID);
    }

    /**
     * @description 删除店铺信息
     * @created 2014-8-27 上午10:01:48
     * @author ZZB
     */
    public static void clearShopInfo(Context context) {
        remove(context, SHOP_ID, SHOP_NAME, SHOP_DOMAIN, SHOP_LOGO, SHOP_BACKGROUND,SHOP_SIGNATURE,SHOP_MOBILE);
    }


    public static String getSignature(Context context) {
        return getString(context, SIGNATURE, "");
    }

    public static void setSignature(Context context, String value) {
        setString(context, SIGNATURE, value);
    }
    public static void setPurchase(Context context, boolean value) {
        setBoolean(context, JINHUOPRICE+getUserId(context), value);
    }
    public static boolean getPurchase(Context context) {
        return getBoolean(context, JINHUOPRICE+getUserId(context), true);
    }
    public static void setRetail(Context context, boolean value) {
        setBoolean(context, LINGSHOUPRICE+getUserId(context), value);
    }
    public static boolean getRetail(Context context) {
        return getBoolean(context, LINGSHOUPRICE+getUserId(context), true);
    }
    public static void setEditTitle(Context context, boolean value) {
        setBoolean(context, EDIT_TITLE+getUserId(context), value);
    }
    public static boolean getEditTitle(Context context) {
        return getBoolean(context, EDIT_TITLE+getUserId(context), true);
    }
    public static void setEditPrice(Context context, boolean value) {
        setBoolean(context, EDIT_PRICE+getUserId(context), value);
    }
    public static boolean getEditPrice(Context context) {
        return getBoolean(context, EDIT_PRICE+getUserId(context), true);
    }
    public static void setIsadd_Group(Context context, boolean value) {
        setBoolean(context, ISADD_GROUP+getUserId(context), value);
    }
    public static boolean getIsadd_Group(Context context) {
        return getBoolean(context, ISADD_GROUP+getUserId(context), true);
    }

    /**
     * Description:获取已经上传过的ITEM信息 2014-7-24 下午8:38:46
     *
     * @author ZZB
     */
    public static String getUploadItems(Context context) {
        return getString(context, UPLOADED_ITEMS, "");
    }

    /**
     * Description:设置已经上传过的item 2014-7-24 下午8:40:55
     *
     * @author ZZB
     */
    public static void setUploadedItems(Context context, String item) {
        setString(context, UPLOADED_ITEMS, item);
    }


    /**
     * Description:设置店铺logo 2014-7-18下午2:58:49
     *
     * @author ZZB
     */
    public static void setShopLogo(Context context, String logo) {
        setString(context, SHOP_LOGO, logo);
    }

    /**
     * Description:获取店铺logo 2014-7-18下午2:58:40
     *
     * @author ZZB
     */
    public static String getShopLogo(Context context) {
        return Const.getShopLogo(SpManager.getUserId(context));
        // return getString(context, SHOP_LOGO, "");
    }

    ;

    /**
     * Description:设置用户id 2014-7-18下午3:04:05
     *
     * @author ZZB
     */
    public static void setUserId(Context context, int userId) {
        setInt(context, USER_ID, userId);
    }

    /**
     * Description:获取用户id 2014-7-18下午3:03:57
     *
     * @author ZZB
     */
    public static int getUserId(Context context) {
        return getInt(context, USER_ID, 0);
    }
    /**
     * Description:设置用户id 2014-7-18下午3:04:05
     *
     * @author ZZB
     */
    public static void setSellerUsersId(Context context, int userId) {
        setInt(context, Seller_Users_ID+SpManager.getUserId(context), userId);
    }

    /**
     * Description:获取用户id 2014-7-18下午3:03:57
     *
     * @author ZZB
     */
    public static int getSellerUsersId(Context context) {
        return getInt(context, Seller_Users_ID+SpManager.getUserId(context), 0);
    }
    /**
     * Description:设置用户id 2014-7-18下午3:04:05
     *
     * @author ZZB
     */
    public static void setSellerName(Context context, String userId) {
        setString(context, Seller_Users_NAME+SpManager.getUserId(context), userId);
    }

    /**
     * Description:获取用户id 2014-7-18下午3:03:57
     *
     * @author ZZB
     */
    public static String getSellerName(Context context) {
        return getString(context, Seller_Users_NAME+SpManager.getUserId(context));
    }

    /**
     * Description:设置用户名 2014-7-18下午2:58:30
     *
     * @author ZZB
     */
    public static void setUserName(Context context, String userName) {
        setString(context, USER_NAME, userName);
    }

    /**
     * Description:获取用户名 2014-7-18下午2:58:23
     *
     * @author ZZB
     */
    public static String getUserName(Context context) {
        return getString(context, USER_NAME, "");
    }

    /**
     * Description:设置店铺域名 2014-7-18下午2:58:15
     *
     * @author ZZB
     */
    public static void setShopDomain(Context context, String domain) {
        setString(context, SHOP_DOMAIN, domain);
    }

    /**
     * Description:获取店铺域名 2014-7-18下午2:58:07
     *
     * @author ZZB
     */
    public static String getShopDomain(Context context) {
        return getString(context, SHOP_DOMAIN, "");
    }

    /**
     * Description:设置店铺名 2014-7-18下午2:58:00
     *
     * @author ZZB
     */
    public static void setShopName(Context context, String shopName) {
        setString(context, SHOP_NAME, shopName);
    }

    /**
     * Description:获取店铺名 2014-7-18下午2:57:52
     *
     * @author ZZB
     */
    public static String getShopName(Context context) {
        return getString(context, SHOP_NAME, "");
    }

    /**
     * Description:设置店铺签名 2014-7-18下午2:57:52
     *
     * @author ZZB
     */
    public static void setShopSignature(Context context, String shopSignature) {
        setString(context, SHOP_SIGNATURE, shopSignature);
    }

    /**
     * Description:获取店铺签名 2014-7-18下午2:57:52
     *
     * @author ZZB
     */
    public static String getShopSignature(Context context) {
        return getString(context, SHOP_SIGNATURE, "");
    }
    /**
     * Description:设置店铺号码 2014-7-18下午2:57:52
     *
     * @author ZZB
     */
    public static void setShopMobile(Context context, String shopMobile) {
        setString(context, SHOP_MOBILE, shopMobile);
    }
    /**
     * Description:获取店铺号码 2014-7-18下午2:57:52
     *
     * @author ZZB
     */
    public static String getShopMobile(Context context) {
        return getString(context, SHOP_MOBILE, "");
    }
    /**
     * Description:设置店招
     */
    public static void setShopBanner(Context context, String banner) {
        setString(context, SHOP_BANNER, banner);
    }

    /**
     * Description:获取店招
     */
    public static String getShopBanner(Context context) {
        return getString(context, SHOP_BANNER, "");
    }

    /**
     * Description:设置招募文案
     */
    public static void setShopRecruitDesc(Context context, String recruitDesc) {
        setString(context, SHOP_RECRUITDESC, recruitDesc);
    }

    /**
     * Description:获取招募文案
     */
    public static String getShopRecruitDesc(Context context) {
        return getString(context, SHOP_RECRUITDESC, "");
    }

    /**
     * Description:设置店铺ID 2014-7-18下午2:46:45
     *
     * @author ZZB
     */
    public static void setShopId(Context context, long shopId) {
        setLong(context, SHOP_ID, shopId);
    }

    /**
     * Description:获取店铺ID 2014-7-18下午2:47:33
     *
     * @author ZZB
     */
    public static long getShopId(Context context) {
        return getLong(context, SHOP_ID, 0);
    }

    /**
     * Description:设置最后一次登录的用户账号 2014-7-4下午3:48:00
     */
    public static void setLoginAccount(Context context, String loginAccount) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(LOGIN_ACCOUNT, loginAccount).commit();

        addLoginAccounts(context, loginAccount);
    }

    /**
     * Description:获取最后一次登录的用户账号 2014-7-4下午3:49:44
     */
    public static String getLoginAccount(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(LOGIN_ACCOUNT, "");
    }

    /**
     * 加入已登录过的账号
     *
     * @param context
     * @param loginAccount
     */
    public static void addLoginAccounts(Context context, String loginAccount) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String account = getLoginAccounts(context);
        if (!StringUtils.contains(account, loginAccount, ",")) {// 不存在账号，添加
            sp.edit().putString(LOGIN_ACCOUNTS, account + loginAccount + ",").commit();
        }
    }

    /**
     * 获取登录过的所有账号，注：账号间以“,”隔开
     *
     * @param context
     * @return
     */
    public static String getLoginAccounts(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(LOGIN_ACCOUNTS, "");
    }

    /**
     * 删除某个关键字
     *
     * @param context
     * @param text
     * @return
     */
    public static String deleteLoginAccounts(Context context, String text) {
        String newChar = SpManager.getLoginAccounts(context.getApplicationContext()).replace(text + ",", "");
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(LOGIN_ACCOUNTS, newChar).commit();
        return newChar;
    }


    /**
     * Description:获取上传新商品时的可视范围的id 2014-7-7上午9:32:40
     */
    public static String getUploadNewItemVisibleRanageIds(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(VISIBLE_RANAGE_IDS, Const.SystemGroupId.ALL_PPL + "");// 默认公开
    }

    /**
     * Description:设置上传商品时的可视范围的id 2014-7-7上午9:43:07
     */
    public static void setUploadNewItemVisibleRanageIds(Context context, String groupIds) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(VISIBLE_RANAGE_IDS, groupIds).commit();
    }

    /**
     * Description:设置上传商品时可视范围的名称 2014-7-13 下午10:48:34
     */
    public static void setUploadNewItemVisibleRangeNames(Context context, String visibleRangeNames) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(VISIBLE_RANGE_NAMES, visibleRangeNames).commit();
    }

    /**
     * Description:获取上传商品时可视范围的名称 2014-7-13 下午10:51:12
     */
    public static String getUploadNewItemVisibleRangeNames(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(VISIBLE_RANGE_NAMES, "公开");// 默认公开
    }

    /**
     * Description:设置cookie 2014-7-18下午2:29:31
     *
     * @author ZZB
     */
    public static void setCookie(Context context, String cookie) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(COOKIE, cookie).commit();
    }

    /**
     * Description:拿cookie 2014-7-18下午2:28:34
     *
     * @author ZZB
     */
    public static String getCookie(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(COOKIE, "");
    }

    /**
     * Description:设置是否是第一次使用app 2014-7-18下午2:34:21
     *
     * @author ZZB
     */
    public static void setIsFirstUseApp(Context context, boolean isFirstUse) {
        setBoolean(context, IS_FIRST_USE_APP, isFirstUse);
    }

    /**
     * Description:是否是第一次使用app, 默认是第一 2014-7-18下午2:36:03
     *
     * @author ZZB
     */
    public static boolean isFirstUseApp(Context context) {
        return getBoolean(context, IS_FIRST_USE_APP, true);
    }

    /**
     * Description:设置是否提示联系方式
     *
     * @author pj
     */
    public static void setContactMsgTip(Context context, boolean isTip) {
        setBoolean(context, HAS_CONTACT_MSG, isTip);
    }


    /**
     * Description:设置首次使用app的时间 2014-7-18下午2:40:10
     *
     * @author ZZB
     */
    public static void setFirstUseTime(Context context, String time) {
        setString(context, FIRST_USE_TIME, time);
    }


    /**
     * Description:获取第一次使用app的时间 2014-7-18下午2:40:58
     *
     * @author ZZB
     */
    public static String getFirstUseTime(Context context) {
        return getString(context, FIRST_USE_TIME, "");
    }

    public static String getString(Context context, String key) {
        return getString(context, key, "");
    }

    public static String getString(Context context, String key, String defValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(key, defValue);
    }

    public static void setString(Context context, String key, String value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(key, value).commit();
    }

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(key, defValue);
    }

    public static void setBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(key, value).commit();
    }

    public static int getInt(Context context, String key, int defValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getInt(key, defValue);
    }

    public static void setInt(Context context, String key, int value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putInt(key, value).commit();
    }

    private static long getLong(Context context, String key, long defValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getLong(key, defValue);
    }

    private static void setLong(Context context, String key, long value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putLong(key, value).commit();
    }

    private static float getFloat(Context context, String key, float defValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getFloat(key, defValue);
    }

    private static void setFloat(Context context, String key, float value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putFloat(key, value).commit();
    }

    private static void remove(Context context, String... keys) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        Editor edit = sp.edit();
        for (String key : keys) {
            edit.remove(key);
        }
        edit.commit();
    }
}
