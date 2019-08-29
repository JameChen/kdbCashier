package com.yiku.kdb_flat.model.http.api;

import com.yiku.kdb_flat.model.ExchanegGoodBean;
import com.yiku.kdb_flat.model.bean.CodeBean;
import com.yiku.kdb_flat.model.bean.CreateProduceBean;
import com.yiku.kdb_flat.model.bean.LoginBean;
import com.yiku.kdb_flat.model.bean.MemBean;
import com.yiku.kdb_flat.model.bean.MenuBean;
import com.yiku.kdb_flat.model.bean.PayBean;
import com.yiku.kdb_flat.model.bean.ProdectBean;
import com.yiku.kdb_flat.model.bean.RefundBean;
import com.yiku.kdb_flat.model.bean.SaleBean;
import com.yiku.kdb_flat.model.bean.SaleDetailBean;
import com.yiku.kdb_flat.model.bean.ShopInfoModel;
import com.yiku.kdb_flat.model.bean.StoageDetailBean;
import com.yiku.kdb_flat.model.bean.UserModel;
import com.yiku.kdb_flat.model.bean.WareHoseBean;
import com.yiku.kdb_flat.model.http.response.KDBResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

import static com.yiku.kdb_flat.model.http.api.HttpUtils.SERVERURL_V4;

/**
 * Created by jame on 2018/4/27.
 */

public interface KDBApi {
    String HOST = HttpUtils.BASE_URL + HttpUtils.VERSION;
    String HTTP_V4 = SERVERURL_V4;

    /**
     * 账号登录
     */
    @FormUrlEncoded
    @POST(HTTP_V4 + "user/user/login")
    Flowable<KDBResponse<LoginBean>> getLoginData(@Field("account") String account,
                                                  @Field("password") String password,
                                                  @Field("isEncode") boolean isEncode,
                                                  @Field("loginFrom") int loginFrom,
                                                  @Field("IMEI") String IMEI,
                                                  @Field("PhoneName") String PhoneName,
                                                  @Field("OS") String OS,
                                                  @Field("Network") String Network
            , @Field("deviceID") String deviceID
            , @Field("channelCode") String channelCode
    );

    /**
     * 获取用户信息，包括用户名，邮箱，积分等等
     */
    @GET(HTTP_V4 + "user/user/GetMyUserInfo")
    Flowable<KDBResponse<UserModel>> getMyUserInfo(
    );

    /**
     * 获取店鋪
     */
    @GET("pinhuokdb/shop/GetShopInfo")
    Flowable<KDBResponse<ShopInfoModel>> getShopInfo(
    );

    /**
     * PP首页菜单
     */
    @GET("pinhuokdb/home/GetMenusV2")
    Flowable<KDBResponse<MenuBean>> getMenusV2(
    );

    /**
     * PP首页菜单
     */
    @GET("pinhuokdb/ShoppingCart/GetProdectList")
    Flowable<KDBResponse<ProdectBean>> GetProdectList(
    );

    /**
     * 获取销售列表数据
     */
    @GET("pinhuokdb/Order/GetOrderList")
    Flowable<KDBResponse<SaleBean>> GetOrderList(
            @QueryMap Map<String, Object> params
    );
    /**
     * 获取库存列表数据
     */
    @GET("pinhuokdb/Storage/GetInStoageList")
    Flowable<KDBResponse<WareHoseBean>> GetInStoageList(
            @QueryMap Map<String, Object> params
    );
    /**
     * 获取库存列表数据
     */
    @GET("pinhuokdb/Storage/GetInStoageDetail")
    Flowable<KDBResponse<StoageDetailBean>> GetInStoageDetail(
            @Query("SourceID")int SourceID
    );

    /**
     * 获取销售明细数据
     */

    @GET("pinhuokdb/Order/GetOrderDetail")
    Flowable<KDBResponse<SaleDetailBean>> GetOrderDetail(
            @Query("OrderCode") String OrderCode
    );

    /**
     * * 订单取消
     */

    @GET("pinhuokdb/Order/CancelOrder")
    Flowable<KDBResponse<Object>> CancelOrder(
            @Query("code") String OrderCode
    );

    /**
     * * 扫描
     */
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    @FormUrlEncoded
    @POST("pinhuokdb/shoppingcart/AddFromCode")
    Flowable<KDBResponse<Object>> AddFromCode(
            @Field("scanCode") String scanCode
    );

    /**
     * 修改
     */
    @POST("pinhuokdb/ShoppingCart/Add/")
    Flowable<KDBResponse<Object>> upadteShoppingCart(
            @Body RequestBody requestBody);

    /**
     * 删除
     */
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    @FormUrlEncoded
    @POST("pinhuokdb/ShoppingCart/delete/")
    Flowable<KDBResponse<Object>> deleteShoppingCart(
            @Field("ids") String ids
    );

    /**
     * 创建
     */
    @FormUrlEncoded
    @POST("pinhuokdb/Order/CreateOrderPreview")
    Flowable<KDBResponse<CreateProduceBean>> CreateOrderPreview(
            @Field("FreeAmount") String FreeAmount,
            @Field("BuyerUserID") int BuyerUserID,
            @Field("ShoppingIDS") String ShoppingIDS,
            @Field("Discount") String Discount,
            @Field("PayableAmount") String PayableAmount
    ,@Field("InnerBuyFl") boolean InnerBuyFl,@Field("SpDiscountFl") boolean SpDiscountFl
    ,@Field("Point") int Point
    ,@Field("ChangeShoppingIDS[]")List<Integer> ChangeShoppingIDS);

    /**
     * 提交
     */

    @FormUrlEncoded
    @POST("pinhuokdb/Order/SaveOrder")
    Flowable<KDBResponse<SaleDetailBean>> SaveOrder(
            @Field("FreeAmount") String FreeAmount,
            @Field("BuyerUserID") int BuyerUserID,
            @Field("shoppingIDS") String ShoppingIDS,
            @Field("discount") String Discount,
            @Field("PayableAmount") String PayableAmount,
            @Field("SellerUserID") String SellerUserID,
            @Field("OrderCode") String OrderCode
            ,@Field("InnerBuyFl") boolean InnerBuyFl,@Field("SpDiscountFl") boolean SpDiscountFl
            ,@Field("Point")int Point
            ,@Field("ChangeShoppingIDS[]")List<Integer> ChangeShoppingIDS
    );


    /**
     * 支付
     */
    @POST("pinhuopay/trade/CodePay4ShopTrade")
    Flowable<KDBResponse<CodeBean>> goCodePay4ShopTrade(
            @QueryMap Map<String, Object> map
    );
//    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
//    @FormUrlEncoded
//    @POST("pinhuopay/trade/CodePay4ShopTrade")
//    Flowable<KDBResponse<CodeBean>> goCodePay4ShopTrade(
//            @Field("payCode")String payCode,
//            @Field("orderCode")String orderCode,
//            @Field("payableAmount")String payableAmount,
//            @Field("onceStr")String onceStr,
//            @Field("payType")String payType,
//            @Field("sign")String sign
//    );

    /**
     * 查询
     */
    @GET("pinhuokdb/order/CheckOrderPayStatu")
    Flowable<KDBResponse<PayBean>> CheckOrderPayStatu(
            @Query("OrderCode") String OrderCode
    );

    /**
     * 会员
     */
    @GET("pinhuokdb/user/getmemberInfo")
    Flowable<KDBResponse<MemBean>> GetMemBerInfo(
           @Query("number") String number
    );
    /**
     *
     *gg
     */
    @GET("pinhuokdb/AD/GetADList")
    Flowable<KDBResponse<List<String>>> GetADList(
    );
    /**
     * * 订单取消
     */

    @GET("pinhuokdb/refund/Get")
    Flowable<KDBResponse<RefundBean>> getRefundOrder(
            @Query("ordercode") String ordercode
    );
    /**
     * 保存退款
     */
    @POST("pinhuokdb/refund/save/")
    Flowable<KDBResponse<Object>> refundSave(
            @Body RequestBody requestBody);
    /**
     * * 换货
     */

    @GET("pinhuokdb/order/GetReturnOrder")
    Flowable<KDBResponse<ExchanegGoodBean>> GetReturnOrder(
            @QueryMap Map<String,Object> map);

}
