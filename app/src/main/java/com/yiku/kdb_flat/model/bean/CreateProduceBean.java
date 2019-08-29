package com.yiku.kdb_flat.model.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jame on 2018/11/23.
 */

public class CreateProduceBean implements Serializable{

    private static final long serialVersionUID = -4622438584606105521L;
    /**
     * ProductCount : 2
     * PayableAmount : 106.00
     * ProductAmount : 106.00
     * DiscountAmount : 0.00
     * VipDiscount : 8.8
     */

    @Expose
    @SerializedName("ProductCount")
    private int ProductCount;
    @Expose
    @SerializedName("OnSaleSummary")
    private String OnSaleSummary="";
    @SerializedName("Products")
    private List<ProductsBean> Products;
    @SerializedName("TotalPoint")
    private int TotalPoint;

    public int getTotalPoint() {
        return TotalPoint;
    }

    public void setTotalPoint(int totalPoint) {
        TotalPoint = totalPoint;
    }

    /**
     * PointOpt : false
     * Point : 0
     * PointSummary : 活动中：100积分可���扣1元，最多只能抵20%
     */

    @SerializedName("PointOpt")
    private boolean PointOpt;
    @SerializedName("Point")
    private int Point;
    @SerializedName("PointSummary")
    private String PointSummary="";

    public String getOnSaleSummary() {
        return OnSaleSummary;
    }

    public void setOnSaleSummary(String onSaleSummary) {
        OnSaleSummary = onSaleSummary;
    }

    @Expose
    @SerializedName("PayableAmount")
    private String PayableAmount="0.00";
    @Expose
    @SerializedName("ProductAmount")
    private String ProductAmount="0.00";
    @Expose
    @SerializedName("DiscountAmount")
    private String DiscountAmount="0.00";
    @Expose
    @SerializedName("VipDiscount")
    private String VipDiscount="0.00";
    @Expose
    @SerializedName("Discount")
    private String Discount="0.00";
    /**
     * FreeAmount : 0.00
     * FreeAmountOpt : false
     */

    @SerializedName("FreeAmount")
    private String FreeAmount="0";
    @SerializedName("FreeAmountOpt")
    private boolean FreeAmountOpt;

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public int getProductCount() {
        return ProductCount;
    }

    public void setProductCount(int ProductCount) {
        this.ProductCount = ProductCount;
    }

    public String getPayableAmount() {
        return PayableAmount;
    }

    public void setPayableAmount(String PayableAmount) {
        this.PayableAmount = PayableAmount;
    }

    public String getProductAmount() {
        return ProductAmount;
    }

    public void setProductAmount(String ProductAmount) {
        this.ProductAmount = ProductAmount;
    }

    public String getDiscountAmount() {
        return DiscountAmount;
    }

    public void setDiscountAmount(String DiscountAmount) {
        this.DiscountAmount = DiscountAmount;
    }

    public String getVipDiscount() {
        return VipDiscount;
    }

    public void setVipDiscount(String VipDiscount) {
        this.VipDiscount = VipDiscount;
    }

    public String getFreeAmount() {
        return FreeAmount;
    }

    public void setFreeAmount(String FreeAmount) {
        this.FreeAmount = FreeAmount;
    }

    public boolean isFreeAmountOpt() {
        return FreeAmountOpt;
    }

    public void setFreeAmountOpt(boolean FreeAmountOpt) {
        this.FreeAmountOpt = FreeAmountOpt;
    }

    public List<ProductsBean> getProducts() {
        return Products;
    }

    public void setProducts(List<ProductsBean> Products) {
        this.Products = Products;
    }

    public boolean isPointOpt() {
        return PointOpt;
    }

    public void setPointOpt(boolean PointOpt) {
        this.PointOpt = PointOpt;
    }

    public int getPoint() {
        return Point;
    }

    public void setPoint(int Point) {
        this.Point = Point;
    }

    public String getPointSummary() {
        return PointSummary;
    }

    public void setPointSummary(String PointSummary) {
        this.PointSummary = PointSummary;
    }

    public static class ProductsBean implements Serializable{
        private static final long serialVersionUID = -7524998374716532612L;
        /**
         * ShoppingID : 30464
         * ItemID : 1117856
         * Color : 碎花
         * Size : L
         * Qty : 1
         * DiscountPrice : 333.0
         * Price : 333.0
         * OrgRetailPrice : 333.0
         */

        @SerializedName("ShoppingID")
        private int ShoppingID;
        @SerializedName("ItemID")
        private int ItemID;
        @SerializedName("Color")
        private String Color;
        @SerializedName("Size")
        private String Size;
        @SerializedName("Qty")
        private int Qty;
        @SerializedName("DiscountPrice")
        private double DiscountPrice;
        @SerializedName("Price")
        private double Price;
        @SerializedName("OrgRetailPrice")
        private double OrgRetailPrice;

        public int getShoppingID() {
            return ShoppingID;
        }

        public void setShoppingID(int ShoppingID) {
            this.ShoppingID = ShoppingID;
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

        public int getQty() {
            return Qty;
        }

        public void setQty(int Qty) {
            this.Qty = Qty;
        }

        public double getDiscountPrice() {
            return DiscountPrice;
        }

        public void setDiscountPrice(double DiscountPrice) {
            this.DiscountPrice = DiscountPrice;
        }

        public double getPrice() {
            return Price;
        }

        public void setPrice(double Price) {
            this.Price = Price;
        }

        public double getOrgRetailPrice() {
            return OrgRetailPrice;
        }

        public void setOrgRetailPrice(double OrgRetailPrice) {
            this.OrgRetailPrice = OrgRetailPrice;
        }
    }
}
