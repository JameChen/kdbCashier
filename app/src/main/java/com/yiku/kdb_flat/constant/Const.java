package com.yiku.kdb_flat.constant;

import android.content.Context;
import android.support.v4.BuildConfig;

import com.nahuo.library.helper.FunctionHelper;

import java.util.HashMap;
import java.util.Map;

public class Const {

    public static final String TAG_TEST = "test";
    public static final boolean DEBUG = BuildConfig.DEBUG;            // BuildConfig.DEBUG;
    public static final String PIC_SEPERATOR = "85231";
    public static final int UPLOAD_MAX_COUNTER = 3;
    private static Map<String, String> startParam = new HashMap<String, String>();
    private static String startChildHost = "";
    private static String startNormalHost = "//main";
    public static boolean IS_NETWORK_AVAILABLE = true;                         // 网络是否可用默认是true
    private static int qqFaceWidth = 0;
    public static int doubleClickTime = 1000;


    public static final String UPYUN_BUCKET_ITEM = "nahuo-img-server";

    public static final String UPYUN_API_KEY_ITEM = "ueRRYuadbyhmJnM/shyyNrj8Wm4=";


    public static final String UPYUN_BUCKET = "banwo-img-server";


    public static final String UPYUN_API_KEY = "3bCOkeK030b7wFIgF7nnqB/a6/s=";


    public static final long UPYUN_EXPIRATION = System.currentTimeMillis()
            / 1000 + 1000 * 5 * 10;


    /**
     * @author ZZB
     * @description 帖子类型
     * @created 2015-2-9 下午5:52:40
     */
    public static enum PostType {
        ACTIVITY, TOPIC
    }

    /**
     * 商品复制类型
     *
     * @author ZZB
     *         created at 2015/8/5 14:19
     */
    public static class ShopItemCopyType {
        //自己创建的商品
        public static final int SELF_UPLOAD = 1;
        //自己转发的商品 = 2,
        public static final int SELF_SHARE = 2;
        //自己复制的商品 = 3,
        public static final int SELF_COPY = 3;
        //        未转发商品 = 4,
        public static final int NOT_SHARE = 4;
        //        已转发的商品=5,
        public static final int ALREADY_SHARED = 5;
        //        已复制的商品=6
        public static final int ALREADY_COPIED = 6;

    }

    /**
     * @description 商品地址
     * @created 2015-5-6 下午5:19:41
     * @author ZZB
     */
    public static String getItemUrl(int itemId) {
        return "http://item.weipushop.com/wap/wpitem/" + itemId;
    }

    public static int getQQFaceWidth(Context context) {
        if (qqFaceWidth == 0) {
            qqFaceWidth = FunctionHelper.dip2px(context.getResources(), 17);
        }
        return qqFaceWidth;
    }

    /**
     * 应用logo地址
     */
  //  public static final String APP_LOGO_URL = "http://banwo-files.b0.upaiyun.com/img/APP-logo_57.png";

    /**
     * @author ZZB
     * @description 订单状态
     * @created 2015-4-28 下午5:20:31
     */
    public static class OrderStatus {
        /**
         * 所有
         */
        public static final int ALL = -1;
        /**
         * 待确认
         */
        public static final int WAIT_CONFIRM = 1;
        /**
         * 待支付
         */
        public static final int WAIT_PAY = 2;
        /**
         * 待发货
         */
        public static final int WAIT_SHIP = 3;
        /**
         * 已发货
         */
        public static final int SHIPED = 4;
        /**
         * 已完成
         */
        public static final int DONE = 6;
        /**
         * 退款中
         */
        public static final int REFUNDING = -2;
    }

    public enum SortIndex {
        DealCountDesc,
        CreateTimeDesc,
        CollectCountDesc,
        MustDealDesc,
    }

    /**
     * @author ZZB
     * @description 订单按钮值
     * @created 2015-4-28 下午4:46:04
     */
    public static class OrderAction {
        public static final String AGENT_REFUND = "代理退款单";
        public static final String SUPPLIERS_CHANGE_PRICE = "供货商改价";
        public static final String SELLER_CHANGE_PRICE = "卖家改价";
        public static final String SELLER_CANCEL = "卖家取消";
        public static final String BUYER_CANCEL = "买家取消";
        public static final String BUYER_PAY = "买家支付";
        public static final String SUPPLIER_SHIP = "供货商发货";
        public static final String BUYER_CONFIRM_RECEIPT = "买家确认收货";
        //        public static final String BUYER_LOGISTICS        = "买家物流";
        public static final String MEMO = "备注";
        public static final String LEAVE_MSG = "留言";
        public static final String BUYER_RETURN = "买家申请退款";
        //        public static final String BUYER_GET_GOOD         = "买家确认收货";
        public static final String BUYER_EXPRESS = "买家物流";
        public static final String BUYER_RETURN_BILL = "买家退款单";
        public static final String BUYER_FOUND_BILL = "买家维权单";
        //        public static final String SUPPLIERS_SEND_GOOD    = "供货商发货";
        public static final String SELLER_EXPRESS = "卖家物流";
        public static final String SELLER_RETURN_BILL = "卖家退款单";
        public static final String SELLER_FOUND_BILL = "卖家维权单";
        public static final String SELLER_EXPRESS_BILL = "卖家发货单";
        public static final String SUPPLIERS_RETUNR_BILL = "供货商退款单";
        public static final String SUPPLIERS_FOUND_BILL = "供货商维权单";
    }

    /**
     * @author ZZB
     * @description 订单类型
     * @created 2015-4-14 上午11:19:49
     */
    public static class OrderType {
        /**
         * 所有订单
         */
        public static final int ALL = -1;
        /**
         * 拿货单
         */
        public static final int BUY = 1;
        /**
         * 售货单
         */
        public static final int SELL = 2;
        /**
         * 代理单
         */
        public static final int AGENT = 3;
        /**
         * 发货单
         */
        public static final int SHIP = 4;
    }

    public static void clearStartParam() {
        setStartModel("");
        setStartParam(new HashMap<String, String>());
    }

    public static String getStartNormalHost() {
        return startNormalHost;
    }

    public static void setStartModel(String model) {
        startChildHost = model;
    }

    public static void setStartParam(Map<String, String> param) {
        startParam = param;
    }

    public static String getStartModel() {
        return startChildHost;
    }

    public static Map<String, String> getStartParam() {
        return startParam;
    }

    public static String getShopLogo(int uid) {
        return "http://api2.nahuo.com/v3/shop/logo/uid/" + uid;
    }

    public static String getShopLogo(String uid) {
        return "http://api2.nahuo.com/v3/shop/logo/uid/" + uid;
    }

    /**
     * @author ZZB
     * @description 商品上传状态
     * @created 2015-4-7 下午5:22:57
     */
    public static class UploadStatus {
        /**
         * 上传中
         */
        public static final String UPLOADING = "上传中";
        /**
         * 等待上传
         */
        public static final String UPLOAD_WAIT = "等待";
        /**
         * 上传失败
         */
        public static final String UPLOAD_FAILED = "点击重传";
        /**
         * 无网络
         */
        public static final String NO_NETWORK = "无网络";
    }

    /**
     * @author ZZB
     * @description 诚保状态id
     * @created 2015-3-5 上午10:39:34
     */
    public static class CreditJoinStatuId {
        /**
         * 未加入
         */
        public static final int NOT_APPLY = -1;
        /**
         * 审核未通过
         */
        public static final int CHECK_NOT_PASSED = 2;
        /**
         * 审核通过，已加入
         */
        public static final int CHECK_PASSED = 3;
        /**
         * 已退出
         */
        public static final int QUIT = 7;

    }

    /**
     * @author ZZB
     * @description 诚保服务id
     * @created 2015-3-4 下午4:46:26
     */
    public static class CreditItemId {
        /**
         * 诚信保证金计划
         */
        public static final int CREDIT_MONEY = 1;
        /**
         * 24小时无理由退换货
         */
        public static final int _24HR_RETURN = 2;
        /**
         * 一件拿样
         */
        public static final int ONE_SAMPLE = 3;
        /**
         * 5件起混批
         */
        public static final int _5_MIXED = 4;
        /**
         * 7天寄售
         */
        public static final int _7_DAYS_DELIEVERY = 5;
        /**
         * 微货源
         */
        public static final int MICRO_SOURCES = 6;
    }

    /**
     * @author ZZB
     * @description 从哪里登录
     * @created 2015-1-6 上午11:14:17
     */
    public static enum LoginFrom {
        QQ, WECHAT;
    }

    /**
     * @author ZZB
     * @description 申请代理的状态
     * @created 2014-11-18 上午10:33:24
     */
    public static final class ApplyAgentStatu {
        /**
         * 未申请
         */
        public static final int NOT_APPLY = 0;
        /**
         * 申请中
         */
        public static final int APPLYING = 1;
        /**
         * 拒绝
         */
        public static final int REJECT = 2;
        /**
         * 接受
         */
        public static final int ACCEPT = 3;
    }

    /**
     * @author ZZB
     * @description 运费类型
     * @created 2014-9-24 上午11:26:36
     */
    public static final class PostFeeType {
        /**
         * 供货商运费之和
         */
        public static final int VENDOR_TOTAL = 1;
        /**
         * 统一运费
         */
        public static final int UNIFICATION = 2;
        /**
         * 卖家运费
         */
        public static final int VENDOR_SINGLE = 3;
    }

    /**
     * @author ZZB
     * @description 银行状态
     * @created 2014-9-19 下午5:18:36
     */
    public static final class BankState {
        /**
         * 全部
         */
        public static final String ALL = "全部";
        /**
         * 未提交
         */
        public static final String NOT_COMMIT = "未提交";
        /**
         * 未审核
         */
        public static final String CHECKING = "审核中";
        /**
         * 已审核
         */
        public static final String AUTH_PASSED = "已审核";
        /**
         * 驳回
         */
        public static final String REJECT = "驳回";
        /**
         * 重审
         */
        public static final String RECHECK = "重审";
    }

    /**
     * @author ZZB
     * @description 身份验证状态
     * @created 2014-9-15 下午2:06:04
     */
    public static final class IDAuthState {
        // 全部 = -1, 未提交 = 0, 审核中 = 1, 已审核 = 2,驳回 = 3, 重审 = 4,
        /**
         * 全部
         */
        public static final String ALL = "全部";
        /**
         * 未提交
         */
        public static final String NOT_COMMIT = "未提交";
        /**
         * 未审核
         */
        public static final String CHECKING = " 审核中";
        /**
         * 已审核
         */
        public static final String AUTH_PASSED = "已审核";
        /**
         * 驳回
         */
        public static final String REJECT = "驳回";
        /**
         * 重审
         */
        public static final String RECHECK = "重审";
    }

    /**
     * @author ZZB
     * @description 腾讯开放平台相关
     * @created 2014-12-18 上午10:56:44
     */
    public static final class TecentOpen {
        public static final String APP_ID = "1104862442";
    }

    /**
     * @author ZZB
     * @description 微信开放平台相关
     * @time 2014-8-4 上午10:40:44
     */
    public static final class WeChatOpen {

        public static final String APP_ID = "wx0888563abd3a8781";
        public static final String PARTNER_ID = "85419541";
        /* 登录相关的 */
        public static final String APP_ID_1 = DEBUG ? "wx3d1fe1d0ca7fda52" : "wxf85afd6c7c1cdc7f";
        public static final String APP_SECRET_1 = DEBUG ? "d0a912bf239e8e5481cdbc1eed94a6cc"
                : "6bf8aaeda97a61b09d9e626cf5337941";

    }

   // public static final String WECHAT_MINIAPP_IDS = "gh_dbb20c98c942";//微信小程序id(测试gh_dbb20c98c942)
    public static final String WECHAT_MINIAPP_IDS = "gh_c99676c8186e";

    /**
     * Description:系统分组id 2014-7-15下午4:32:10
     */
    public static final class SystemGroupId {
        public static final int MIN_ID = -4;
        public static final int ALL_AGENT = -1;
        public static final int BLACK_LIST = -2;
        public static final int NEW_APPLY = -3;
        public static final int ALL_PPL = -5;

    }

    /**
     * Description:我的代理分组界面更新 2014-7-16下午5:46:16
     */
    public static final class AgentGroup {
        /**
         * 需要减少人数的分组id
         */
        public static final String DECREASE_GROUP_IDS = "DECREASE_GROUP_IDS";
        /**
         * 需要增加人数的分组id
         */
        public static final String INCREASE_GROUP_IDS = "INCREASE_GROUP_IDS";
        /**
         * 用户老的分组id
         */
        public static final String OLD_GROUP_IDS = "OLD_GROUP_IDS";
        public static final String GROUP_TYPE = "GROUP_TYPE";
        /**
         * 从一个分组移到另外一个分组的用户id，需要在旧分组中移除它
         */
        public static final String MOVED_USER_ID = "MOVED_USER_ID";
        public static final String USER_ID = "USER_ID";

    }

    /**
     * @author ZZB
     * @description 密码类型
     * @time 2014-7-31 下午2:08:16
     */
    public static enum PasswordType {
        /**
         * 支付
         */
        PAYMENT,
        /**
         * 登录
         */
        LOGIN, RESET_PAYMENT
    }

    /**
     * @author ZZB
     * @description 密码相关的，用intent传数据时的key
     * @time 2014-7-31 下午2:18:56
     */
    public static class PasswordExtra {
        public static final String EXTRA_PSW_TYPE = "EXTRA_PSW_TYPE";
    }

    /**
     * @description 图片picasso压缩质量
     * @author PJ
     */
    public static int IMAGE_QUANTITY = 90;

    /**
     * @description 图片上传最大大小
     * @author PJ
     */
    public static int UPLOAD_ITEM_MAX_SIZE = 160;

    /**
     * @description 图片下载缩略图配置
     * @author PJ
     */
    public static int DOWNLOAD_ITEM_SIZE = 24;

    /**
     * @description 列表位置头像的缩略图配置
     * @author PJ
     */
    public static int LIST_COVER_SIZE = 10;

    /**
     * @description 搜索列表商品小图的缩略图配置
     * @author PJ
     */
    public static int LIST_ITEM_SIZE = 3;
    /**
     * @description 列表位置HEADER头像的缩略图配置
     * @author PJ
     */
    public static int LIST_HEADER_COVER_SIZE = 4;
    /**
     * @description 列表位置HEADER背景的缩略图配置
     * @author PJ
     */
    public static int HEADER_BG_SIZE = 16;
}
