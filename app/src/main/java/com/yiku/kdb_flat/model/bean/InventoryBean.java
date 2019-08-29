package com.yiku.kdb_flat.model.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jame on 2018/11/29.
 */

public class InventoryBean implements Serializable {
    private static final long serialVersionUID = 8979589348960971756L;
    @Expose
    @SerializedName("List")
    private java.util.List<ListBean> List;

    public List<ListBean> getList() {
        return List;
    }

    public void setList(List<ListBean> List) {
        this.List = List;
    }

    public static class ListBean implements Serializable{
        private static final long serialVersionUID = 6933537591251144937L;
        /**
         * CheckQty : 3
         * ItemCount : 1
         * StockQty : 7
         * StockTime : 2018-11-29
         */
        @Expose
        @SerializedName("CheckQty")
        private int CheckQty;
        @Expose
        @SerializedName("ItemCount")
        private int ItemCount;
        @Expose
        @SerializedName("StockQty")
        private int StockQty;
        @Expose
        @SerializedName("StockTime")
        private String StockTime="";

        public int getCheckQty() {
            return CheckQty;
        }

        public void setCheckQty(int CheckQty) {
            this.CheckQty = CheckQty;
        }

        public int getItemCount() {
            return ItemCount;
        }

        public void setItemCount(int ItemCount) {
            this.ItemCount = ItemCount;
        }

        public int getStockQty() {
            return StockQty;
        }

        public void setStockQty(int StockQty) {
            this.StockQty = StockQty;
        }

        public String getStockTime() {
            return StockTime;
        }

        public void setStockTime(String StockTime) {
            this.StockTime = StockTime;
        }
    }
}
