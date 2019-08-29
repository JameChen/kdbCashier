package com.yiku.kdb_flat.model.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jame on 2018/11/29.
 */

public class InventoryDetailBean implements Serializable {
    private static final long serialVersionUID = 6878088219443171038L;

    /**
     * CreateTime : 2018-11-29 10:29:24
     * OptUserName : 好韵来
     * CheckLostQty : 0
     * CheckLostAmount : 0.0
     * CheckMakeQty : 4
     * CheckMakeAmount : 0.0
     * RemarkList : [{"Remark":"sss","OptUserName":"好韵来","CreateTime":"2018/11/29 10:29:24"}]
     * Items : [{"ItemID":1117939,"Name":"lilifeiyang内网测试20181015第一","Cover":"upyun:nahuo-img-server://129766/item/1539568261018.jpg","Sku":"1117939【】","TotalStockQty":3,"RetailPrice":18.81,"Products":[{"Color":"如图","Size":"E","StockQty":7,"CheckQty":3,"QtyBalance":4}]}]
     */
    @Expose
    @SerializedName("CreateTime")
    private String CreateTime="";
    @Expose
    @SerializedName("OptUserName")
    private String OptUserName="";
    @Expose
    @SerializedName("CheckLostQty")
    private int CheckLostQty;
    @Expose
    @SerializedName("CheckLostAmount")
    private double CheckLostAmount;
    @Expose
    @SerializedName("CheckMakeQty")
    private int CheckMakeQty;
    @Expose
    @SerializedName("CheckMakeAmount")
    private double CheckMakeAmount;
    @Expose
    @SerializedName("RemarkList")
    private List<RemarkListBean> RemarkList;
    @Expose
    @SerializedName("Items")
    private List<ItemsBean> Items;

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getOptUserName() {
        return OptUserName;
    }

    public void setOptUserName(String OptUserName) {
        this.OptUserName = OptUserName;
    }

    public int getCheckLostQty() {
        return CheckLostQty;
    }

    public void setCheckLostQty(int CheckLostQty) {
        this.CheckLostQty = CheckLostQty;
    }

    public double getCheckLostAmount() {
        return CheckLostAmount;
    }

    public void setCheckLostAmount(double CheckLostAmount) {
        this.CheckLostAmount = CheckLostAmount;
    }

    public int getCheckMakeQty() {
        return CheckMakeQty;
    }

    public void setCheckMakeQty(int CheckMakeQty) {
        this.CheckMakeQty = CheckMakeQty;
    }

    public double getCheckMakeAmount() {
        return CheckMakeAmount;
    }

    public void setCheckMakeAmount(double CheckMakeAmount) {
        this.CheckMakeAmount = CheckMakeAmount;
    }

    public List<RemarkListBean> getRemarkList() {
        return RemarkList;
    }

    public void setRemarkList(List<RemarkListBean> RemarkList) {
        this.RemarkList = RemarkList;
    }

    public List<ItemsBean> getItems() {
        return Items;
    }

    public void setItems(List<ItemsBean> Items) {
        this.Items = Items;
    }

    public static class RemarkListBean implements Serializable {
        private static final long serialVersionUID = -1075990718973633355L;
        /**
         * Remark : sss
         * OptUserName : 好韵来
         * CreateTime : 2018/11/29 10:29:24
         */
        @Expose
        @SerializedName("Remark")
        private String Remark="";
        @Expose
        @SerializedName("OptUserName")
        private String OptUserName="";
        @Expose
        @SerializedName("CreateTime")
        private String CreateTime="";

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String Remark) {
            this.Remark = Remark;
        }

        public String getOptUserName() {
            return OptUserName;
        }

        public void setOptUserName(String OptUserName) {
            this.OptUserName = OptUserName;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }
    }

    public static class ItemsBean implements Serializable{
        private static final long serialVersionUID = -1466404946405453709L;
        /**
         * ItemID : 1117939
         * Name : lilifeiyang内网测试20181015第一
         * Cover : upyun:nahuo-img-server://129766/item/1539568261018.jpg
         * Sku : 1117939【】
         * TotalStockQty : 3
         * RetailPrice : 18.81
         * Products : [{"Color":"如图","Size":"E","StockQty":7,"CheckQty":3,"QtyBalance":4}]
         */
        @Expose
        @SerializedName("ItemID")
        private int ItemID;
        @Expose
        @SerializedName("Name")
        private String Name="";
        @Expose
        @SerializedName("Cover")
        private String Cover="";
        @Expose
        @SerializedName("Sku")
        private String Sku="";
        @Expose
        @SerializedName("TotalStockQty")
        private int TotalStockQty;
        @Expose
        @SerializedName("RetailPrice")
        private double RetailPrice;
        @Expose
        @SerializedName("Products")
        private List<ProductsBean> Products;

        public int getItemID() {
            return ItemID;
        }

        public void setItemID(int ItemID) {
            this.ItemID = ItemID;
        }

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

        public String getSku() {
            return Sku;
        }

        public void setSku(String Sku) {
            this.Sku = Sku;
        }

        public int getTotalStockQty() {
            return TotalStockQty;
        }

        public void setTotalStockQty(int TotalStockQty) {
            this.TotalStockQty = TotalStockQty;
        }

        public double getRetailPrice() {
            return RetailPrice;
        }

        public void setRetailPrice(double RetailPrice) {
            this.RetailPrice = RetailPrice;
        }

        public List<ProductsBean> getProducts() {
            return Products;
        }

        public void setProducts(List<ProductsBean> Products) {
            this.Products = Products;
        }

        public static class ProductsBean implements Serializable {
            private static final long serialVersionUID = -1273514878815162257L;
            /**
             * Color : 如图
             * Size : E
             * StockQty : 7
             * CheckQty : 3
             * QtyBalance : 4
             */
            @Expose
            @SerializedName("Color")
            private String Color="";
            @Expose
            @SerializedName("Size")
            private String Size="";
            @Expose
            @SerializedName("StockQty")
            private int StockQty;
            @Expose
            @SerializedName("CheckQty")
            private int CheckQty;
            @Expose
            @SerializedName("QtyBalance")
            private int QtyBalance;

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

            public int getStockQty() {
                return StockQty;
            }

            public void setStockQty(int StockQty) {
                this.StockQty = StockQty;
            }

            public int getCheckQty() {
                return CheckQty;
            }

            public void setCheckQty(int CheckQty) {
                this.CheckQty = CheckQty;
            }

            public int getQtyBalance() {
                return QtyBalance;
            }

            public void setQtyBalance(int QtyBalance) {
                this.QtyBalance = QtyBalance;
            }
        }
    }
}
