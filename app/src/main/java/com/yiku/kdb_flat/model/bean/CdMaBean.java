package com.yiku.kdb_flat.model.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jame on 2018/11/29.
 */

public class CdMaBean implements Serializable {
    private static final long serialVersionUID = 748915644956340239L;

    /**
     * Items : [{"SourceID":1117853,"InStockQty":20,"OrderQty":0,"Cover":"upyun:nahuo-img-server://3636/item/153742690649-7936.jpg","ReName":"lilifeiyang 内网测试 201809201第二 原价不多说了","Sku":"1117853【】","FirstInStockTime":"2018-11-28","QtyBalance":20,"StockOutRate":0},{"SourceID":1117851,"InStockQty":10,"OrderQty":0,"Cover":"upyun:nahuo-img-server://3636/item/153742690549-59.jpg","ReName":"lilifeiyang 内网测试 20180920第一 原价","Sku":"1117851【】","FirstInStockTime":"2018-11-28","QtyBalance":10,"StockOutRate":0},{"SourceID":1117841,"InStockQty":26,"OrderQty":0,"Cover":"upyun:nahuo-img-server://3636/item/153734424056-6414.jpg","ReName":"lilifeiyang内网测试 20180919第6","Sku":"1117841【】","FirstInStockTime":"2018-11-28","QtyBalance":26,"StockOutRate":0},{"SourceID":1117841,"InStockQty":26,"OrderQty":0,"Cover":"upyun:nahuo-img-server://3636/item/153734424056-6414.jpg","ReName":"lilifeiyang内网测试 20180919第6","Sku":"1117841【】","FirstInStockTime":"2018-11-28","QtyBalance":26,"StockOutRate":0},{"SourceID":1117833,"InStockQty":27,"OrderQty":0,"Cover":"upyun:nahuo-img-server://3636/item/153732505780-3489.jpg","ReName":"lilifeiyang内网测试 20180919第2","Sku":"1117833【】","FirstInStockTime":"2018-11-27","QtyBalance":27,"StockOutRate":0},{"SourceID":1117833,"InStockQty":27,"OrderQty":0,"Cover":"upyun:nahuo-img-server://3636/item/153732505780-3489.jpg","ReName":"lilifeiyang内网测试 20180919第2","Sku":"1117833【】","FirstInStockTime":"2018-11-27","QtyBalance":27,"StockOutRate":0},{"SourceID":1117825,"InStockQty":10,"OrderQty":0,"Cover":"upyun:nahuo-img-server://3636/item/153725032648-4180.jpg","ReName":"Lilifeiyang 内网测试 20180918 第3","Sku":"1117825【】","FirstInStockTime":"2018-11-29","QtyBalance":10,"StockOutRate":0},{"SourceID":1117825,"InStockQty":10,"OrderQty":0,"Cover":"upyun:nahuo-img-server://3636/item/153725032648-4180.jpg","ReName":"Lilifeiyang 内网测试 20180918 第7","Sku":"1117825【】","FirstInStockTime":"2018-11-29","QtyBalance":10,"StockOutRate":0},{"SourceID":1117815,"InStockQty":10,"OrderQty":0,"Cover":"upyun:nahuo-img-server://3636/item/153560974312-4495.jpg","ReName":"Lilifeiyang 内网测试 20180918 第7","Sku":"1117815【】","FirstInStockTime":"2018-11-29","QtyBalance":10,"StockOutRate":0},{"SourceID":1107094,"InStockQty":10,"OrderQty":0,"Cover":"upyun:nahuo-img-server://3636/item/153267276165-3958.jpg","ReName":"lilifeiyang内网测试 20180727第二","Sku":"1107094【】","FirstInStockTime":"2018-11-27","QtyBalance":10,"StockOutRate":0},{"SourceID":1076904,"InStockQty":20,"OrderQty":0,"Cover":"upyun:nahuo-img-server://3636/item/152567269078-6408.jpg","ReName":"lilifeiyang 内网测试 20180507第1款","Sku":"1076904【】","FirstInStockTime":"2018-11-29","QtyBalance":20,"StockOutRate":0},{"SourceID":1076845,"InStockQty":42,"OrderQty":0,"Cover":"upyun:nahuo-img-server://129766/item/1524472701756.jpg","ReName":"lilifeiyang内网测试0423第一","Sku":"1076845【】","FirstInStockTime":"2018-11-28","QtyBalance":42,"StockOutRate":0},{"SourceID":1066374,"InStockQty":5,"OrderQty":0,"Cover":"upyun:nahuo-img-server://3636/item/dd0c106f-b90b-428c-80a7-d60044373ee8-65923","ReName":"Lilifeiyang 20180201内网测试第6","Sku":"1066374【】","FirstInStockTime":"2018-11-29","QtyBalance":5,"StockOutRate":0}]
     * TotalInstockQty : 180
     * TotalSaleQty : 0
     * StockOutRate : 0.0
     */
    @Expose
    @SerializedName("TotalInstockQty")
    private int TotalInstockQty;
    @Expose
    @SerializedName("TotalSaleQty")
    private int TotalSaleQty;
    @Expose
    @SerializedName("StockOutRate")
    private double StockOutRate;
    @Expose
    @SerializedName("Items")
    private List<ItemsBean> Items;

    public int getTotalInstockQty() {
        return TotalInstockQty;
    }

    public void setTotalInstockQty(int TotalInstockQty) {
        this.TotalInstockQty = TotalInstockQty;
    }

    public int getTotalSaleQty() {
        return TotalSaleQty;
    }

    public void setTotalSaleQty(int TotalSaleQty) {
        this.TotalSaleQty = TotalSaleQty;
    }

    public double getStockOutRate() {
        return StockOutRate;
    }

    public void setStockOutRate(double StockOutRate) {
        this.StockOutRate = StockOutRate;
    }

    public List<ItemsBean> getItems() {
        return Items;
    }

    public void setItems(List<ItemsBean> Items) {
        this.Items = Items;
    }

    public static class ItemsBean implements Serializable {
        private static final long serialVersionUID = 5292863933488651254L;
        /**
         * SourceID : 1117853
         * InStockQty : 20
         * OrderQty : 0
         * Cover : upyun:nahuo-img-server://3636/item/153742690649-7936.jpg
         * ReName : lilifeiyang 内网测试 201809201第二 原价不多说了
         * Sku : 1117853【】
         * FirstInStockTime : 2018-11-28
         * QtyBalance : 20
         * StockOutRate : 0.0
         */
        @Expose
        @SerializedName("SourceID")
        private int SourceID;
        @Expose
        @SerializedName("InStockQty")
        private int InStockQty;
        @Expose
        @SerializedName("OrderQty")
        private int OrderQty;
        @Expose
        @SerializedName("Cover")
        private String Cover="";
        @SerializedName("ReName")
        @Expose
        private String ReName="";
        @SerializedName("Sku")
        @Expose
        private String Sku="";
        @Expose
        @SerializedName("FirstInStockTime")
        private String FirstInStockTime="";
        @Expose
        @SerializedName("QtyBalance")
        private int QtyBalance;
        @Expose
        @SerializedName("StockOutRate")
        private double StockOutRate;

        public int getSourceID() {
            return SourceID;
        }

        public void setSourceID(int SourceID) {
            this.SourceID = SourceID;
        }

        public int getInStockQty() {
            return InStockQty;
        }

        public void setInStockQty(int InStockQty) {
            this.InStockQty = InStockQty;
        }

        public int getOrderQty() {
            return OrderQty;
        }

        public void setOrderQty(int OrderQty) {
            this.OrderQty = OrderQty;
        }

        public String getCover() {
            return Cover;
        }

        public void setCover(String Cover) {
            this.Cover = Cover;
        }

        public String getReName() {
            return ReName;
        }

        public void setReName(String ReName) {
            this.ReName = ReName;
        }

        public String getSku() {
            return Sku;
        }

        public void setSku(String Sku) {
            this.Sku = Sku;
        }

        public String getFirstInStockTime() {
            return FirstInStockTime;
        }

        public void setFirstInStockTime(String FirstInStockTime) {
            this.FirstInStockTime = FirstInStockTime;
        }

        public int getQtyBalance() {
            return QtyBalance;
        }

        public void setQtyBalance(int QtyBalance) {
            this.QtyBalance = QtyBalance;
        }

        public double getStockOutRate() {
            return StockOutRate;
        }

        public void setStockOutRate(double StockOutRate) {
            this.StockOutRate = StockOutRate;
        }
    }
}
