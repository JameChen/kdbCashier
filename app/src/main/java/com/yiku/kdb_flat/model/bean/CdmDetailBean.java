package com.yiku.kdb_flat.model.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jame on 2018/11/30.
 */

public class CdmDetailBean implements Serializable {
    private static final long serialVersionUID = -789739202464606754L;

    /**
     * Name : lilifeiyang 内网测试 201809201第二 原价不多说了
     * SourceID : 1117853
     * Sku : 1117853【】
     * FirstInstockTime : 2018-11-28 10:41:26
     * Products : [{"Color":"吃吃吃","Size":"36","InStockQty":10,"SaleQty":5,"StockQty":5,"StockOutRate":50},{"Color":"黑暗","Size":"36","InStockQty":5,"SaleQty":5,"StockQty":0,"StockOutRate":100},{"Color":"咖色","Size":"36","InStockQty":15,"SaleQty":6,"StockQty":9,"StockOutRate":40},{"Color":"吃吃吃","Size":"42","InStockQty":10,"SaleQty":3,"StockQty":7,"StockOutRate":30},{"Color":"黑暗","Size":"42","InStockQty":5,"SaleQty":2,"StockQty":3,"StockOutRate":40},{"Color":"咖色","Size":"42","InStockQty":15,"SaleQty":2,"StockQty":13,"StockOutRate":13.333333333333334}]
     */
    @Expose
    @SerializedName("Name")
    private String Name = "";
    @Expose
    @SerializedName("SourceID")
    private int SourceID;
    @Expose
    @SerializedName("Sku")
    private String Sku = "";
    @Expose
    @SerializedName("FirstInstockTime")
    private String FirstInstockTime = "";
    @Expose
    @SerializedName("Products")
    private List<ProductsBean> Products;
    @Expose
    @SerializedName("Cover")
    private String Cover="";

    public String getCover() {
        return Cover;
    }

    public void setCover(String cover) {
        Cover = cover;
    }

    /**
     * TotalStockQty : 37
     * TotalStockOutRate : 62.16216216216216
     */
    @Expose
    @SerializedName("TotalStockQty")
    private int TotalStockQty;
    @Expose
    @SerializedName("TotalStockOutRate")
    private double TotalStockOutRate;

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getSourceID() {
        return SourceID;
    }

    public void setSourceID(int SourceID) {
        this.SourceID = SourceID;
    }

    public String getSku() {
        return Sku;
    }

    public void setSku(String Sku) {
        this.Sku = Sku;
    }

    public String getFirstInstockTime() {
        return FirstInstockTime;
    }

    public void setFirstInstockTime(String FirstInstockTime) {
        this.FirstInstockTime = FirstInstockTime;
    }

    public List<ProductsBean> getProducts() {
        return Products;
    }

    public void setProducts(List<ProductsBean> Products) {
        this.Products = Products;
    }

    public int getTotalStockQty() {
        return TotalStockQty;
    }

    public void setTotalStockQty(int TotalStockQty) {
        this.TotalStockQty = TotalStockQty;
    }

    public double getTotalStockOutRate() {
        return TotalStockOutRate;
    }

    public void setTotalStockOutRate(double TotalStockOutRate) {
        this.TotalStockOutRate = TotalStockOutRate;
    }

    public static class ProductsBean implements Serializable {
        private static final long serialVersionUID = -5616245585714868746L;
        /**
         * Color : 吃吃吃
         * Size : 36
         * InStockQty : 10
         * SaleQty : 5
         * StockQty : 5
         * StockOutRate : 50.0
         */
        @Expose
        @SerializedName("Color")
        private String Color = "";
        @Expose
        @SerializedName("Size")
        private String Size = "";
        @Expose
        @SerializedName("InStockQty")
        private int InStockQty;
        @Expose
        @SerializedName("SaleQty")
        private int SaleQty;
        @Expose
        @SerializedName("StockQty")
        private int StockQty;
        @Expose
        @SerializedName("StockOutRate")
        private double StockOutRate;

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

        public int getInStockQty() {
            return InStockQty;
        }

        public void setInStockQty(int InStockQty) {
            this.InStockQty = InStockQty;
        }

        public int getSaleQty() {
            return SaleQty;
        }

        public void setSaleQty(int SaleQty) {
            this.SaleQty = SaleQty;
        }

        public int getStockQty() {
            return StockQty;
        }

        public void setStockQty(int StockQty) {
            this.StockQty = StockQty;
        }

        public double getStockOutRate() {
            return StockOutRate;
        }

        public void setStockOutRate(double StockOutRate) {
            this.StockOutRate = StockOutRate;
        }
    }
}
