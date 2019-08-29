package com.yiku.kdb_flat.model.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jame on 2018/9/13.
 */

public class SaleDetailBean implements Serializable {
    private static final long serialVersionUID = 6267976614602283166L;
    @Expose
    @SerializedName("SellerUserID")
    private int SellerUserID;
    /**
     * CreateUserName : 好韵来
     * CreateUserUserID : 339925
     */
    @Expose
    @SerializedName("CreateUserName")
    private String CreateUserName = "";
    @Expose
    @SerializedName("CreateUserUserID")
    private int CreateUserUserID;
    /**
     * SpDiscountFl : false
     * InnerBuyFl : false
     */
    @SerializedName("Point")
    private int Point;

    public int getPoint() {
        return Point;
    }

    public void setPoint(int point) {
        Point = point;
    }

    @SerializedName("SpDiscountFl")
    private boolean SpDiscountFl;
    @SerializedName("InnerBuyFl")
    private boolean InnerBuyFl;
    @SerializedName("RefundList")
    private List<RefundListBean> RefundList;
    @SerializedName("ChangeList")
    private List<RefundListBean> ChangeList;
    @SerializedName("OrgOrderList")
    private List<RefundListBean> OrgOrderList;

    public List<RefundListBean> getOrgOrderList() {
        return OrgOrderList;
    }

    public void setOrgOrderList(List<RefundListBean> orgOrderList) {
        OrgOrderList = orgOrderList;
    }

    public List<RefundListBean> getChangeList() {
        return ChangeList;
    }

    public void setChangeList(List<RefundListBean> changeList) {
        ChangeList = changeList;
    }

    public int getSellerUserID() {
        return SellerUserID;
    }

    public void setSellerUserID(int sellerUserID) {
        SellerUserID = sellerUserID;
    }

    public String getSellerUserUserName() {
        return SellerUserUserName;
    }

    public void setSellerUserUserName(String sellerUserUserName) {
        SellerUserUserName = sellerUserUserName;
    }

    @Expose
    @SerializedName("SellerUserName")
    private String SellerUserUserName = "";
    /**
     * ShopInfo : {"Name":"天天拼货"}
     * CreateTime : 2018-09-12 15:54:19
     * OrderCode : 18091215541940318369
     * ProductCount : 2
     * ProductAmount : 8.00
     * Discount : 3.20
     * PayableAmount : 4.80
     * Statu : 已完成
     * PayInfo : {"Type":"未支付","Code":""}
     * Products : [{"Name":"zzb 0519 内网测试 第4款","Cover":"upyun:nahuo-img-server://33306/item/1495160487.jpg","Color":"15","Size":"123","Qty":2,"Price":"4.00"}]
     */
    @Expose
    @SerializedName("Buttons")
    private List<OrderButton> Buttons;
    @Expose
    @SerializedName("Charge")
    private String Charge = "0.00";

    public String getCharge() {
        return Charge;
    }

    public void setCharge(String charge) {
        Charge = charge;
    }

    public List<OrderButton> getButtons() {
        return Buttons;
    }

    public void setButtons(List<OrderButton> buttons) {
        Buttons = buttons;
    }

    @Expose
    @SerializedName("ShopInfo")
    private ShopInfoBean ShopInfo;
    @Expose
    @SerializedName("CreateTime")
    private String CreateTime = "";
    @Expose
    @SerializedName("OrderCode")
    private String OrderCode = "";
    @Expose
    @SerializedName("ProductCount")
    private int ProductCount;
    @Expose
    @SerializedName("ProductAmount")
    private String ProductAmount = "0.00";
    @Expose
    @SerializedName("Discount")
    private String Discount = "0.00";
    @Expose
    @SerializedName("GetDiscountPercent")
    private String GetDiscountPercent = "0.00";
    @Expose
    @SerializedName("Mobile")
    private String Mobile = "";

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getGetDiscountPercent() {
        return GetDiscountPercent;
    }

    public void setGetDiscountPercent(String getDiscountPercent) {
        GetDiscountPercent = getDiscountPercent;
    }

    @Expose
    @SerializedName("PayableAmount")
    private String PayableAmount = "0.00";
    @Expose
    @SerializedName("Statu")
    private String Statu = "";
    @Expose
    @SerializedName("PayInfo")
    private PayInfoBean PayInfo;
    @Expose
    @SerializedName("Products")
    private List<ProductsBean> Products;
    @Expose
    @SerializedName("CollectedAmount")
    private String CollectedAmount = "0.00";

    public String getCollectedAmount() {
        return CollectedAmount;
    }

    public void setCollectedAmount(String collectedAmount) {
        CollectedAmount = collectedAmount;
    }

    public ShopInfoBean getShopInfo() {
        return ShopInfo;
    }

    public void setShopInfo(ShopInfoBean ShopInfo) {
        this.ShopInfo = ShopInfo;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getOrderCode() {
        return OrderCode;
    }

    public void setOrderCode(String OrderCode) {
        this.OrderCode = OrderCode;
    }

    public int getProductCount() {
        return ProductCount;
    }

    public void setProductCount(int ProductCount) {
        this.ProductCount = ProductCount;
    }

    public String getProductAmount() {
        return ProductAmount;
    }

    public void setProductAmount(String ProductAmount) {
        this.ProductAmount = ProductAmount;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String Discount) {
        this.Discount = Discount;
    }

    public String getPayableAmount() {
        return PayableAmount;
    }

    public void setPayableAmount(String PayableAmount) {
        this.PayableAmount = PayableAmount;
    }

    public String getStatu() {
        return Statu;
    }

    public void setStatu(String Statu) {
        this.Statu = Statu;
    }

    public PayInfoBean getPayInfo() {
        return PayInfo;
    }

    public void setPayInfo(PayInfoBean PayInfo) {
        this.PayInfo = PayInfo;
    }

    public List<ProductsBean> getProducts() {
        return Products;
    }

    public void setProducts(List<ProductsBean> Products) {
        this.Products = Products;
    }

    public String getCreateUserName() {
        return CreateUserName;
    }

    public void setCreateUserName(String CreateUserName) {
        this.CreateUserName = CreateUserName;
    }

    public int getCreateUserUserID() {
        return CreateUserUserID;
    }

    public void setCreateUserUserID(int CreateUserUserID) {
        this.CreateUserUserID = CreateUserUserID;
    }

    public boolean isSpDiscountFl() {
        return SpDiscountFl;
    }

    public void setSpDiscountFl(boolean SpDiscountFl) {
        this.SpDiscountFl = SpDiscountFl;
    }

    public boolean isInnerBuyFl() {
        return InnerBuyFl;
    }

    public void setInnerBuyFl(boolean InnerBuyFl) {
        this.InnerBuyFl = InnerBuyFl;
    }

    public List<RefundListBean> getRefundList() {
        return RefundList;
    }

    public void setRefundList(List<RefundListBean> RefundList) {
        this.RefundList = RefundList;
    }

    public static class ShopInfoBean implements Serializable {
        private static final long serialVersionUID = 6267976614502283166L;
        /**
         * Name : 天天拼货
         */

        @SerializedName("Name")
        private String Name;

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }
    }

    public static class PayInfoBean implements Serializable {
        private static final long serialVersionUID = 6267976814502283166L;
        /**
         * Type : 未支付
         * Code :
         */
        @Expose
        @SerializedName("Type")
        private String Type = "";
        @Expose
        @SerializedName("Code")
        private String Code = "";

        public String getType() {
            return Type;
        }

        public void setType(String Type) {
            this.Type = Type;
        }

        public String getCode() {
            return Code;
        }

        public void setCode(String Code) {
            this.Code = Code;
        }
    }

    public static class ProductsBean implements Serializable {
        private static final long serialVersionUID = 6257976614502283166L;
        /**
         * Name : zzb 0519 内网测试 第4款
         * Cover : upyun:nahuo-img-server://33306/item/1495160487.jpg
         * Color : 15
         * Size : 123
         * Qty : 2
         * Price : 4.00
         */
        @SerializedName("Code")
        @Expose
        private String Sku = "";
        /**
         * OrgPrice : 0.00
         * IsOnSale : false
         */
        @SerializedName("PriceTag")
        private String PriceTag="";

        public String getPriceTag() {
            return PriceTag;
        }

        public void setPriceTag(String priceTag) {
            PriceTag = priceTag;
        }

        @SerializedName("RefundSummary")
        private String RefundSummary = "";
        @SerializedName("IsPointChange")
        private boolean IsPointChange;//  是否积分兑换
        @SerializedName("IsChanged")
        private boolean IsChanged;

        public boolean isChanged() {
            return IsChanged;
        }

        public void setChanged(boolean changed) {
            IsChanged = changed;
        }

        public boolean isPointChange() {
            return IsPointChange;
        }

        public void setPointChange(boolean pointChange) {
            IsPointChange = pointChange;
        }

        public int getPoint() {
            return Point;
        }

        public void setPoint(int point) {
            Point = point;
        }

        private int Point;             //多少积分

        public String getRefundSummary() {
            return RefundSummary;
        }

        public void setRefundSummary(String refundSummary) {
            RefundSummary = refundSummary;
        }

        @SerializedName("OrgPrice")
        private String OrgPrice = "0.00";
        @SerializedName("IsOnSale")
        private boolean IsOnSale;
        @SerializedName("DiscountPrice")
        private String DiscountPrice = "0.00";
        @SerializedName("GetDiscountPercentStr")
        private String GetDiscountPercentStr = "0.00";
        @SerializedName("IsDiscount")
        private boolean IsDiscount;


        public String getGetDiscountPercentStr() {
            return GetDiscountPercentStr;
        }

        public void setGetDiscountPercentStr(String getDiscountPercentStr) {
            GetDiscountPercentStr = getDiscountPercentStr;
        }

        public boolean isDiscount() {
            return IsDiscount;
        }

        public void setDiscount(boolean discount) {
            IsDiscount = discount;
        }

        public String getDiscountPrice() {
            return DiscountPrice;
        }

        public void setDiscountPrice(String discountPrice) {
            DiscountPrice = discountPrice;
        }

        public String getSku() {
            return Sku;
        }

        public void setSku(String sku) {
            Sku = sku;
        }

        @Expose
        @SerializedName("Name")
        private String Name = "";
        @SerializedName("Cover")
        @Expose
        private String Cover = "";
        @Expose
        @SerializedName("Color")
        private String Color = "";
        @Expose
        @SerializedName("Size")
        private String Size = "";
        @Expose
        @SerializedName("Qty")
        private int Qty;
        @Expose
        @SerializedName("Price")
        private String Price = "0.00";

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getCover() {
            return Cover;
        }

        public void setCover(String Cover) {
            this.Cover = Cover;
        }

        public String getColor() {
            return Color;
        }

        public void setColor(String Color) {
            this.Color = Color;
        }

        public String getSize() {
            return Size;
        }

        public void setSize(String Size) {
            this.Size = Size;
        }

        public int getQty() {
            return Qty;
        }

        public void setQty(int Qty) {
            this.Qty = Qty;
        }

        public String getPrice() {
            return Price;
        }

        public void setPrice(String Price) {
            this.Price = Price;
        }

        public String getOrgPrice() {
            return OrgPrice;
        }

        public void setOrgPrice(String OrgPrice) {
            this.OrgPrice = OrgPrice;
        }

        public boolean isIsOnSale() {
            return IsOnSale;
        }

        public void setIsOnSale(boolean IsOnSale) {
            this.IsOnSale = IsOnSale;
        }
    }

    public static class RefundListBean implements Serializable {
        private static final long serialVersionUID = -659741006075298967L;
        /**
         * Code : 171812282095313
         * OptUserName : 好韵来
         * RefundPayType : 现金
         * Reason : 质量问题
         * CreateTime : 2018-12-28 10:59:39
         * TotalQty : 1
         * Amount : 0.08
         * Items : [{"ItemID":1117858,"Color":"玫红色","Size":"L","Price":"0.08","Qty":1,"Cover":"upyun:nahuo-img-server://3636/item/153785408296-1235.jpg","Code":"20180925","Title":"lilifeiyang 1内网测试20180925第3"}]
         */

        @SerializedName("Code")
        private String Code = "";
        @SerializedName("OptUserName")
        private String OptUserName = "";
        @SerializedName("RefundPayType")
        private String RefundPayType = "";
        @SerializedName("Reason")
        private String Reason = "";
        @SerializedName("CreateTime")
        private String CreateTimeX = "";
        @SerializedName("TotalQty")
        private int TotalQty;
        @SerializedName("Amount")
        private String Amount = "";
        @SerializedName("Items")
        private List<ItemsBean> Items;

        public String getCode() {
            return Code;
        }

        public void setCode(String Code) {
            this.Code = Code;
        }

        public String getOptUserName() {
            return OptUserName;
        }

        public void setOptUserName(String OptUserName) {
            this.OptUserName = OptUserName;
        }

        public String getRefundPayType() {
            return RefundPayType;
        }

        public void setRefundPayType(String RefundPayType) {
            this.RefundPayType = RefundPayType;
        }

        public String getReason() {
            return Reason;
        }

        public void setReason(String Reason) {
            this.Reason = Reason;
        }

        public String getCreateTimeX() {
            return CreateTimeX;
        }

        public void setCreateTimeX(String CreateTimeX) {
            this.CreateTimeX = CreateTimeX;
        }

        public int getTotalQty() {
            return TotalQty;
        }

        public void setTotalQty(int TotalQty) {
            this.TotalQty = TotalQty;
        }

        public String getAmount() {
            return Amount;
        }

        public void setAmount(String Amount) {
            this.Amount = Amount;
        }

        public List<ItemsBean> getItems() {
            return Items;
        }

        public void setItems(List<ItemsBean> Items) {
            this.Items = Items;
        }

        public static class ItemsBean implements Serializable {
            private static final long serialVersionUID = -7010240033666835521L;
            /**
             * ItemID : 1117858
             * Color : 玫红色
             * Size : L
             * Price : 0.08
             * Qty : 1
             * Cover : upyun:nahuo-img-server://3636/item/153785408296-1235.jpg
             * Code : 20180925
             * Title : lilifeiyang 1内网测试20180925第3
             */

            @SerializedName("ItemID")
            private int ItemID;
            @SerializedName("Color")
            private String Color = "";
            @SerializedName("Size")
            private String Size = "";
            @SerializedName("Price")
            private String Price = "";
            @SerializedName("Qty")
            private int Qty;
            @SerializedName("Cover")
            private String Cover = "";
            @SerializedName("Code")
            private String Code = "";
            @SerializedName("Title")
            private String Title = "";
            @SerializedName("OrgOrderCode")
            private String OrgOrderCode = "";

            public String getOrgOrderCode() {
                return OrgOrderCode;
            }

            public void setOrgOrderCode(String orgOrderCode) {
                OrgOrderCode = orgOrderCode;
            }

            public int getItemID() {
                return ItemID;
            }

            public void setItemID(int ItemID) {
                this.ItemID = ItemID;
            }

            public String getColor() {
                return Color;
            }

            public void setColor(String Color) {
                this.Color = Color;
            }

            public String getSize() {
                return Size;
            }

            public void setSize(String Size) {
                this.Size = Size;
            }

            public String getPrice() {
                return Price;
            }

            public void setPrice(String Price) {
                this.Price = Price;
            }

            public int getQty() {
                return Qty;
            }

            public void setQty(int Qty) {
                this.Qty = Qty;
            }

            public String getCover() {
                return Cover;
            }

            public void setCover(String Cover) {
                this.Cover = Cover;
            }

            public String getCode() {
                return Code;
            }

            public void setCode(String Code) {
                this.Code = Code;
            }

            public String getTitle() {
                return Title;
            }

            public void setTitle(String Title) {
                this.Title = Title;
            }
        }
    }
}
