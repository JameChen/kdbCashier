package com.yiku.kdb_flat.utils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.yiku.kdb_flat.constant.Const;
import com.yiku.kdb_flat.eventbus.BusEvent;
import com.yiku.kdb_flat.eventbus.EventBusId;
import com.yiku.kdb_flat.model.bean.PublicData;
import com.yiku.kdb_flat.ui.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;

import static com.yiku.kdb_flat.model.http.api.HttpUtils.ECC_OPEN;

/**
 * @description 用户信息
 * @created 2014-9-3 上午9:39:36
 * @author ZZB
 */
public class UserInfoProvider {

    private static final String TAG = UserInfoProvider.class.getSimpleName();
    private static final String PREF_PREFIX_USER_INFO = "PREF_PREFIX_USER_INFO";
    private static final String PREF_SAFE_QUESTION_ID = "PREF_SAFE_QUESTION_ID";
    private static final String PREF_BIND_BANK_ID = "PREF_BIND_BANK_ID";
    private static final String PREF_BIND_PHONE_ID = "PREF_BIND_PHONE_ID";
    private static final String PREF_PAY_PSW_ID = "PREF_PAY_PSW_ID";
    private static final String PREF_YFT_ID = "PREF_YFT_ID";
    private static final String PREF_BIND_PHONE = "PREF_BIND_PHONE";
    private static final String PREF_IDENTITY_AUTH_STATE = "PREF_IDENTITY_AUTH_STATE";
    /**
     * 退出应用
     */
    public static void exitApp(Context context){
        EventBus.getDefault().post(BusEvent.getEvent(EventBusId.ON_APP_EXIT));
//        if (ECC_OPEN) {
//            EMClient.getInstance().logout(false, new EMCallBack() {
//
//                @Override
//                public void onSuccess() {
//                    Log.d(TAG, "logout: onSuccess");
//                }
//
//                @Override
//                public void onProgress(int progress, String status) {
//                }
//
//                @Override
//                public void onError(int code, String error) {
//                    Log.d(TAG, "logout: onSuccess");
//                }
//            });
//        }
        UserInfoProvider.clearAllUserInfo(context);
        // 进入登录界面
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    /**
     * 退出应用
     */
    public static void exitLogin(Context context){
       // EventBus.getDefault().post(BusEvent.getEvent(EventBusId.ON_APP_EXIT));
        if (ECC_OPEN) {
//            EMClient.getInstance().logout(false, new EMCallBack() {
//
//                @Override
//                public void onSuccess() {
//                    Log.d(TAG, "logout: onSuccess");
//                }
//
//                @Override
//                public void onProgress(int progress, String status) {
//                }
//
//                @Override
//                public void onError(int code, String error) {
//                    Log.d(TAG, "logout: onSuccess");
//                }
//            });
        }
        UserInfoProvider.clearAllUserInfo(context);
        // 进入登录界面
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    /**
     * @description 是否显示或者开通过衣付通
     * @created 2014-9-9 上午10:41:19
     * @author ZZB
     */
    public static boolean isYFTShowedOrOpened(Context context) {
        int userId = SpManager.getUserId(context);
        // 是否开通过衣付通
        boolean opened = UserInfoProvider.hasOpenedYFT(context, userId);
        return opened;
    }
    /**
     * @description 删除所有的用户信息
     * @created 2014-12-19 下午5:17:11
     * @author ZZB
     */
    public static void clearAllUserInfo(Context context){

        // 清空cookie
        PublicData.setCookie(context, "");
        // 清空cookie
//        PublicData.mShopInfo = new ShopInfoModel();
        // 修改配置文件
        SpManager.clearUserInfo(context);
        Log.i(TAG, "Cookie已经清空");
        SpManager.clearShopInfo(context);
        Log.i(TAG, "shopInfo已经清空");
        
    }
    /**
     * @description 设置身份验证状态, 全部 = -1, 未提交 = 0, 审核中 = 1, 已审核 = 2,驳回 = 3, 重审 = 4,
     * @created 2014-9-12 下午2:00:54
     * @author ZZB
     */
    private static void setIdentityAuthStateId(Context context, String state) {
        SpManager.setString(context, getSpKey(context, PREF_IDENTITY_AUTH_STATE), state);
    }
    /**
     * @description 身份验证状态, 全部 = -1, 未提交 = 0, 审核中 = 1, 已审核 = 2,驳回 = 3, 重审 = 4,
     * @created 2014-9-12 下午2:00:54
     * @author ZZB
     */
    private static String getIdentityAuthState(Context context){
        return SpManager.getString(context, getSpKey(context, PREF_IDENTITY_AUTH_STATE));
    }
    /**
     * @description 是否进行身份验证
     * @created 2014-9-12 下午2:00:03
     * @author ZZB
     */
    public static boolean hasIdentityAuth(Context context, int userId) {
        return Const.IDAuthState.AUTH_PASSED.equals(getIdentityAuthState(context));
    }

    /**
     * @description 获取身份验证状态
     * @created 2014-9-12 下午12:04:39
     * @author ZZB
     * @return 全部 = -1, 未提交 = 0, 审核中 = 1, 已审核 = 2,驳回 = 3, 重审 = 4,
     */
    public static String getIdentityAuthState(Context context, int userId) {
        return SpManager.getString(context, getSpKey(context, PREF_IDENTITY_AUTH_STATE));
//        // "'uid':state,'uid':state
//        String states = SpManager.getIdentityAuthStates(context).trim();
//        return StringUtils.getValue(states, userId);

    }

    /**
     * @description 设置身份验证状态 //全部 = -1, 未提交 = 0, 审核中 = 1, 已审核 = 2,驳回 = 3, 重审 = 4,
     * @created 2014-9-12 下午12:03:54
     * @author ZZB
     */
    public static void setIdentityAuthState(Context context, int userId, String state){
     // "'uid':state,'uid':state
        if("未审核".equals(state)){
            state = Const.IDAuthState.CHECKING;
        }
        SpManager.setString(context, getSpKey(context, PREF_IDENTITY_AUTH_STATE), state);
//        if(state.equals(Const.IDAuthState.AUTH_PASSED)){
//            setHasIdentityAuth(context, userId);
//        }
//        String states = SpManager.getIdentityAuthStates(context).trim();
//        states = StringUtils.insertOrUpdateKV(states, userId, state);
//        SpManager.setIdentityAuthStates(context, states);
    }

    /**
     * @description 是否开通衣付通
     * @created 2014-9-4 上午9:53:42
     * @author ZZB
     */
    public static boolean hasOpenedYFT(Context context, int userId) {
        return getYFTOpenId(context) == 1;
    }

    /**
     * @description 设置开通衣付通
     * @created 2014-9-4 上午10:08:12
     * @author ZZB
     */
    public static void setHasOpenedYFT(Context context, int userId) {
        setYFTOpenId(context, 1);
    }

    /**
     * @description 设置绑定的手机
     * @created 2014-9-4 上午9:50:31
     * @author ZZB
     */
    public static void setBindPhone(Context context, int userId, String phone) {
        SpManager.setString(context, getSpKey(context, PREF_BIND_PHONE), phone);
    }

    /**
     * @description 根据用户id获取绑定手机
     * @created 2014-9-3 下午3:43:56
     * @author ZZB
     */
    public static String getBindPhone(Context context, int userId) {
        String phoneNum = SpManager.getString(context, getSpKey(context, PREF_BIND_PHONE));
        return phoneNum;

    }

    /**
     * @description 设置是否设置支付密码
     * @created 2014-9-3 上午10:58:25
     * @author ZZB
     */
    public static void setHasSetPayPsw(Context context, int userId) {
        setPayPswStatusId(context, 1);
    }

    /**
     * @description 是否设置支付密码
     * @created 2014-9-3 上午10:58:11
     * @author ZZB
     */
    public static boolean hasSetPayPsw(Context context, int userId) {
        return getPayPswStatusId(context) == 1;
    }

    /**
     * @description 是否绑定手机
     * @created 2014-9-3 上午10:39:42
     * @author ZZB
     */
    public static boolean hasBindPhone(Context context, int userId) {
        return getBindPhoneStatusId(context) == 1;
    }

    /**
     * @description 检查是否设置安全问题
     * @created 2014-9-3 上午9:46:03
     * @author ZZB
     */
    public static boolean hasSetSafeQuestion(Context context, int userId) {
        int statusId = getSafeQuestionStatusId(context);
        return statusId == 1;
    }

    /**
     * @description 标记已设置安全问题的用户到本地
     * @created 2014-9-3 上午9:45:41
     * @author ZZB
     */
    public static void setHasSafeQuestion(Context context, int userId) {
        setSafeQuestionStatusId(context, 1);
    }
    /**
     * 衣付通是否已经开通，1为已经开通
     *@author ZZB
     *created at 2015/7/27 15:46
     */
    public static void setYFTOpenId(Context context, int id){
        SpManager.setInt(context, getSpKey(context, PREF_YFT_ID), id);
    }
    /**
     * 衣付通是否已经开通，1为已经开通
     *@author ZZB
     *created at 2015/7/27 15:46
     */
    public static int getYFTOpenId(Context context){
        return SpManager.getInt(context, getSpKey(context, PREF_YFT_ID), -1);
    }
    /**
     * 是否设置支付密码id, 1为已设置
     *@author ZZB
     *created at 2015/7/27 15:41
     */
    public static void setPayPswStatusId(Context context, int id){
        SpManager.setInt(context, getSpKey(context, PREF_PAY_PSW_ID), id);
    }
    /**
     * 是否设置支付密码id, 1为已设置
     *@author ZZB
     *created at 2015/7/27 15:41
     */
    public static int getPayPswStatusId(Context context){
        return SpManager.getInt(context, getSpKey(context, PREF_PAY_PSW_ID), -1);
    }
    /**
     * 是否绑定手机状态id, 1为已经绑定
     *@author ZZB
     *created at 2015/7/27 15:30
     */
    public static void setBindPhoneStatusId(Context context, int id){
        SpManager.setInt(context, getSpKey(context, PREF_BIND_PHONE_ID), id);
    }
    /**
     * 是否绑定手机状态id, 1为已经绑定
     *@author ZZB
     *created at 2015/7/27 15:31
     */
    public static int getBindPhoneStatusId(Context context){
        return SpManager.getInt(context, getSpKey(context, PREF_BIND_PHONE_ID), -1);
    }
    /**
     * 设置银行是否已经绑定的状态id，如果是1，则为设绑定银行
     *@author ZZB
     *created at 2015/7/27 15:23
     */
    public static void setBindBankStatusId(Context context, int id){
        SpManager.setInt(context, getSpKey(context, PREF_BIND_BANK_ID), id);
    }
    /**
     * 绑定银行状态id
     *@author ZZB
     *created at 2015/7/27 15:24
     */
    public static int getBindBankStatusId(Context context){
        return SpManager.getInt(context, getSpKey(context, PREF_BIND_BANK_ID), -1);
    }
    /**
     * 设置安全问题是否已经设置的状态id，如果是1，则为设置过安全问题
     *@author ZZB
     *created at 2015/7/27 14:48
     */
    public static void setSafeQuestionStatusId(Context context, int id){
        SpManager.setInt(context, getSpKey(context, PREF_SAFE_QUESTION_ID), id);
    }
    /**
     * 获取安全问题设置状态id
     *@author ZZB
     *created at 2015/7/27 15:00
     */
    private static int getSafeQuestionStatusId(Context context){
        return SpManager.getInt(context, getSpKey(context, PREF_SAFE_QUESTION_ID), -1);
    }
    private static String getSpKey(Context context, String key){
        return PREF_PREFIX_USER_INFO + "_" + SpManager.getUserId(context) + "_" + key;
    }
    /**
     * @description 检查是否绑定银行
     * @created 2014-9-3 上午9:59:50
     * @author ZZB
     */
    public static boolean hasBindBank(Context context, int userId) {
        int statusId = getBindBankStatusId(context);
        return statusId == 1;
    }

//    /**
//     * @description 标记已绑定银行到本地
//     * @created 2014-9-3 上午10:01:13
//     * @author ZZB
//     */
//    public static void setHasBindBank(Context context, int userId) {
//        setBindBankStatusId(context, 1);
//    }
    
    /**
     * @description 把json转成对象
     * @created 2014-9-19 下午4:59:16
     * @author ZZB
     */
    private static Object jsonToObj(Context context, Class<?> cls, String json){
        int userId = SpManager.getUserId(context);
        if(TextUtils.isEmpty(json)){
            return null;
        }else{
            try {
                JSONArray jArr = new JSONArray(json);
                if(jArr.toString().contains("\"" + userId + "\"")){
                    for(int i=0; i<jArr.length(); i++){
                        JSONObject obj = jArr.getJSONObject(i);
                        int uid = obj.getInt("uid");
                        if(uid == userId){
                            Gson gson = new Gson();
                            return gson.fromJson(obj.getString("data"), cls);
                        }
                    }
                }else{
                    return null;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            
        }
        return null;
    }
    /**
     * @description 把对象转成json[{uid:,data:{obj}},{},{}]
     * @created 2014-9-19 下午4:53:23
     * @author ZZB
     */
    private static String storeObjAsJson(Context context, Object obj, String json) {
        Gson gson = new Gson();
        String infoJson = gson.toJson(obj);
        int userId = SpManager.getUserId(context);
        try {
            JSONArray jArr = TextUtils.isEmpty(json) ? new JSONArray() : new JSONArray(json);
            if(!jArr.toString().contains("\"" + userId + "\"")){//不存在，直接插入
                JSONObject jObj = new JSONObject();
                jObj.put("uid", userId + "");
                jObj.put("data", infoJson);
                jArr.put(jObj);
                return jArr.toString();
            }else{//存在，替换
                JSONArray newArr = new JSONArray();
                for(int i=0; i<jArr.length(); i++){
                    JSONObject jObj = jArr.getJSONObject(i);
                    int uid = jObj.getInt("uid");
                    if(uid == userId){
                        jObj.put("data", infoJson);
                    }
                    newArr.put(jObj);
                }
                return newArr.toString();
            }
            
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
}
