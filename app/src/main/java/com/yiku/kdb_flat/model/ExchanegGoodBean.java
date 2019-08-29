package com.yiku.kdb_flat.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jame on 2018/12/29.
 */

public class ExchanegGoodBean implements Serializable {
    private static final long serialVersionUID = -8976661360863680059L;
    @SerializedName("ItemList")
    private List<ItemListBean> ItemList;

    public List<ItemListBean> getItemList() {
        return ItemList;
    }

    public void setItemList(List<ItemListBean> ItemList) {
        this.ItemList = ItemList;
    }

    public static class ItemListBean implements Serializable {
        private static final long serialVersionUID = 512246111845543292L;
        /**
         * OrderProductID : 20619
         * ItemID : 1117856
         * OrderCode : bw18122814165183131045
         * CreateTimes : 2018-12-28 14:16:51
         * Mobile :
         * Cover : upyun:nahuo-img-server://3636/item/153785408296-1235.jpg
         * Color : 碎花
         * Size : L
         * Qty : 1
         * DiscountPrice : 0.02
         */
        private String Uid="";

        public String getUid() {
            return Uid;
        }

        public void setUid(String uid) {
            Uid = uid;
        }

        @SerializedName("OrderProductID")
        private int OrderProductID;
        @SerializedName("ItemID")
        private int ItemID;
        @SerializedName("OrderCode")
        private String OrderCode="";
        @SerializedName("CreateTimes")
        private String CreateTimes="";
        @SerializedName("Mobile")
        private String Mobile="";
        @SerializedName("DisplayMobile")
        private String DisplayMobile="";

        public String getDisplayMobile() {
            return DisplayMobile;
        }

        public void setDisplayMobile(String displayMobile) {
            DisplayMobile = displayMobile;
        }

        @SerializedName("Cover")
        private String Cover="";
        @SerializedName("Color")
        private String Color="";
        @SerializedName("Size")
        private String Size="";
        @SerializedName("Code")
        private String Code="";

        public String getCode() {
            return Code;
        }

        public void setCode(String code) {
            Code = code;
        }

        @SerializedName("Qty")
        private int Qty;
        private int exchangQty;

        public int getExchangQty() {
            return exchangQty;
        }

        public void setExchangQty(int exchangQty) {
            this.exchangQty = exchangQty;
        }

        @SerializedName("DiscountPrice")
        private double DiscountPrice;

        public int getOrderProductID() {
            return OrderProductID;
        }

        public void setOrderProductID(int OrderProductID) {
            this.OrderProductID = OrderProductID;
        }

        public int getItemID() {
            return ItemID;
        }

        public void setItemID(int ItemID) {
            this.ItemID = ItemID;
        }

        public String getOrderCode() {
            return OrderCode;
        }

        public void setOrderCode(String OrderCode) {
            this.OrderCode = OrderCode;
        }

        public String getCreateTimes() {
            return CreateTimes;
        }

        public void setCreateTimes(String CreateTimes) {
            this.CreateTimes = CreateTimes;
        }

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String Mobile) {
            this.Mobile = Mobile;
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

        public double getDiscountPrice() {
            return DiscountPrice;
        }

        public void setDiscountPrice(double DiscountPrice) {
            this.DiscountPrice = DiscountPrice;
        }
    }
}
