package com.yiku.kdb_flat.model.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jame on 2018/12/27.
 */

public class RefundBean implements Serializable {
    private static final long serialVersionUID = 3491029253897649402L;

    /**
     * OrderQty : 1
     * Reasons : [{"ID":0,"Name":"质量问题"},{"ID":1,"Name":"其他原因"}]
     * ReasonPayType : [{"ID":1,"Name":"现金"},{"ID":2,"Name":"微信"},{"ID":3,"Name":"支付宝"},{"ID":4,"Name":"Pos机"}]
     * ItemList : [{"Title":"lilifeiyang 内网测试20180925第1","Code":"20180925","DiscountPrice":"0.02","ItemID":1117856,"Qty":1,"Color":"碎花","Size":"L","Cover":"upyun:nahuo-img-server://3636/item/153785408296-1235.jpg","CanRefundQty":1}]
     * OptUsers : [{"ID":339925,"Name":"好韵来"},{"ID":531785,"Name":"ciciE"},{"ID":531825,"Name":"zghyluu"},{"ID":531826,"Name":"zghyl哼哼唧唧"},{"ID":531827,"Name":"zghyl工业园"},{"ID":531828,"Name":"zghyl该不会"},{"ID":531829,"Name":"zghyl不抱抱"},{"ID":531830,"Name":"zghyl白百何"},{"ID":531831,"Name":"zghyl该喝喝"},{"ID":531832,"Name":"zghylv个"},{"ID":531833,"Name":"zghyl哈哈哈"},{"ID":531834,"Name":"zghyl哈哈健健康康"},{"ID":531835,"Name":"zghyl哈哈"},{"ID":531837,"Name":"zghyl3312"},{"ID":531838,"Name":"zghyl3351302"},{"ID":531845,"Name":"zghylaa"}]
     */

    @SerializedName("OrderQty")
    private int OrderQty;
    @SerializedName("Reasons")
    private List<SelectBean> Reasons;
    @SerializedName("ReasonPayType")
    private List<SelectBean> ReasonPayType;
    @SerializedName("ItemList")
    private List<ItemListBean> ItemList;
    @SerializedName("OptUsers")
    private List<SelectBean> OptUsers;

    public int getOrderQty() {
        return OrderQty;
    }

    public void setOrderQty(int OrderQty) {
        this.OrderQty = OrderQty;
    }

    public List<SelectBean> getReasons() {
        return Reasons;
    }

    public void setReasons(List<SelectBean> Reasons) {
        this.Reasons = Reasons;
    }

    public List<SelectBean> getReasonPayType() {
        return ReasonPayType;
    }

    public void setReasonPayType(List<SelectBean> ReasonPayType) {
        this.ReasonPayType = ReasonPayType;
    }

    public List<ItemListBean> getItemList() {
        return ItemList;
    }

    public void setItemList(List<ItemListBean> ItemList) {
        this.ItemList = ItemList;
    }

    public List<SelectBean> getOptUsers() {
        return OptUsers;
    }

    public void setOptUsers(List<SelectBean> OptUsers) {
        this.OptUsers = OptUsers;
    }




    public static class ItemListBean implements Serializable{
        private static final long serialVersionUID = 3058456688286321971L;
        /**
         * Title : lilifeiyang 内网测试20180925第1
         * Code : 20180925
         * DiscountPrice : 0.02
         * ItemID : 1117856
         * Qty : 1
         * Color : 碎花
         * Size : L
         * Cover : upyun:nahuo-img-server://3636/item/153785408296-1235.jpg
         * CanRefundQty : 1
         */
        private int TID;

        public int getTID() {
            return TID;
        }

        public void setTID(int TID) {
            this.TID = TID;
        }

        @SerializedName("Title")
        private String Title="";
        @SerializedName("Code")
        private String Code="";
        @SerializedName("DiscountPrice")
        private String DiscountPrice="0.00";
        @SerializedName("ItemID")
        private int ItemID;
        @SerializedName("Qty")
        private int Qty;
        @SerializedName("Color")
        private String Color="";
        @SerializedName("Size")
        private String Size="";
        @SerializedName("Cover")
        private String Cover="";
        @SerializedName("CanRefundQty")
        private int CanRefundQty;

        public int getRefundQty() {
            return RefundQty;
        }

        public void setRefundQty(int refundQty) {
            RefundQty = refundQty;
        }

        private int RefundQty;
        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getCode() {
            return Code;
        }

        public void setCode(String Code) {
            this.Code = Code;
        }

        public String getDiscountPrice() {
            return DiscountPrice;
        }

        public void setDiscountPrice(String DiscountPrice) {
            this.DiscountPrice = DiscountPrice;
        }

        public int getItemID() {
            return ItemID;
        }

        public void setItemID(int ItemID) {
            this.ItemID = ItemID;
        }

        public int getQty() {
            return Qty;
        }

        public void setQty(int Qty) {
            this.Qty = Qty;
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

        public String getCover() {
            return Cover;
        }

        public void setCover(String Cover) {
            this.Cover = Cover;
        }

        public int getCanRefundQty() {
            return CanRefundQty;
        }

        public void setCanRefundQty(int CanRefundQty) {
            this.CanRefundQty = CanRefundQty;
        }
    }


}
