package com.yiku.kdb_flat.model.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jame on 2018/11/27.
 */

public class PackageDetailBean implements Serializable {

    private static final long serialVersionUID = 8411682558260791989L;
    /**
     * Items : [{"Name":"lilifeiyang内网测试 20180919第2","Sku":null,"Cover":"upyun:nahuo-img-server://3636/item/153732505780-3489.jpg","SourceID":1117833,"Products":[{"Color":"橙橙","Size":"2030","ShipQty":4,"MaxQty":3},{"Color":"橙橙","Size":"5","ShipQty":4,"MaxQty":4},{"Color":"飞翔","Size":"2030","ShipQty":4,"MaxQty":9},{"Color":"飞翔","Size":"5","ShipQty":4,"MaxQty":10}]},{"Name":"lilifeiyang内网测试 20180919第6","Sku":null,"Cover":"upyun:nahuo-img-server://3636/item/153734424056-6414.jpg","SourceID":1117841,"Products":[{"Color":"闪光金","Size":"2030","ShipQty":4,"MaxQty":9},{"Color":"闪光金","Size":"5","ShipQty":4,"MaxQty":9},{"Color":"闪光金","Size":"利率","ShipQty":4,"MaxQty":4},{"Color":"颜色04","Size":"5","ShipQty":4,"MaxQty":4}]},{"Name":"lilifeiyang 内网测试 20180920第一 原价","Sku":null,"Cover":"upyun:nahuo-img-server://3636/item/153742690549-59.jpg","SourceID":1117851,"Products":[{"Color":"咖色","Size":"36","ShipQty":5,"MaxQty":5},{"Color":"咖色","Size":"42","ShipQty":5,"MaxQty":5}]},{"Name":"lilifeiyang 内网测试 201809201第二 原价不多说了","Sku":null,"Cover":"upyun:nahuo-img-server://3636/item/153742690649-7936.jpg","SourceID":1117853,"Products":[{"Color":"吃吃吃","Size":"36","ShipQty":5,"MaxQty":5},{"Color":"吃吃吃","Size":"42","ShipQty":5,"MaxQty":5},{"Color":"咖色","Size":"36","ShipQty":5,"MaxQty":5},{"Color":"咖色","Size":"42","ShipQty":5,"MaxQty":5}]}]
     * TotalShipQty : 62
     */
    @Expose
    @SerializedName("TotalShipQty")
    private int TotalShipQty;
    @Expose
    @SerializedName("Items")
    private List<ItemsBean> Items;

    public int getTotalShipQty() {
        return TotalShipQty;
    }

    public void setTotalShipQty(int TotalShipQty) {
        this.TotalShipQty = TotalShipQty;
    }

    public List<ItemsBean> getItems() {
        return Items;
    }

    public void setItems(List<ItemsBean> Items) {
        this.Items = Items;
    }

    public static class ItemsBean implements Serializable {
        private static final long serialVersionUID = 2873759483536474096L;
        /**
         * Name : lilifeiyang内网测试 20180919第2
         * Sku : null
         * Cover : upyun:nahuo-img-server://3636/item/153732505780-3489.jpg
         * SourceID : 1117833
         * Products : [{"Color":"橙橙","Size":"2030","ShipQty":4,"MaxQty":3},{"Color":"橙橙","Size":"5","ShipQty":4,"MaxQty":4},{"Color":"飞翔","Size":"2030","ShipQty":4,"MaxQty":9},{"Color":"飞翔","Size":"5","ShipQty":4,"MaxQty":10}]
         */
        @Expose
        @SerializedName("Name")
        private String Name="";
        @Expose
        @SerializedName("Sku")
        private String Sku="";
        @Expose
        @SerializedName("Cover")
        private String Cover="";
        @Expose
        @SerializedName("SourceID")
        private int SourceID;
        @Expose
        @SerializedName("Products")
        private List<ProductsBean> Products;

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getSku() {
            return Sku;
        }

        public void setSku(String Sku) {
            this.Sku = Sku;
        }

        public String getCover() {
            return Cover;
        }

        public void setCover(String Cover) {
            this.Cover = Cover;
        }

        public int getSourceID() {
            return SourceID;
        }

        public void setSourceID(int SourceID) {
            this.SourceID = SourceID;
        }

        public List<ProductsBean> getProducts() {
            return Products;
        }

        public void setProducts(List<ProductsBean> Products) {
            this.Products = Products;
        }

        public static class ProductsBean  implements Serializable{
            private static final long serialVersionUID = -5878977346667478105L;
            /**
             * Color : 橙橙
             * Size : 2030
             * ShipQty : 4
             * MaxQty : 3
             */
            private int ID;

            public int getSourceID() {
                return ID;
            }

            public void setSourceID(int sourceID) {
                ID = sourceID;
            }

            @Expose
            @SerializedName("Color")
            private String Color="";
            @Expose
            @SerializedName("Size")
            private String Size="";
            @Expose
            @SerializedName("ShipQty")
            private int ShipQty;
            private int ArrivalQty;

            public String getColor_Size() {
                return getSourceID()+"/"+ getColor()+"/"+getSize();
            }

            public int getArrivalQty() {
                return ArrivalQty;
            }

            public void setArrivalQty(int arrivalQty) {
                ArrivalQty = arrivalQty;
            }

            @Expose
            @SerializedName("MaxQty")
            private int MaxQty;

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

            public int getShipQty() {
                return ShipQty;
            }

            public void setShipQty(int ShipQty) {
                this.ShipQty = ShipQty;
            }

            public int getMaxQty() {
                return MaxQty;
            }

            public void setMaxQty(int MaxQty) {
                this.MaxQty = MaxQty;
            }
        }
    }
}
