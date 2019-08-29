package com.yiku.kdb_flat.model.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jame on 2018/11/30.
 */

public class AchieveBean  implements Serializable{
    private static final long serialVersionUID = 7053562413547422878L;

    /**
     * SellerList : [{"Top":1,"SellerUserID":0,"SellerUserName":"","OrderCount":145,"ProductQty":367,"JointRate":2,"perCustomerTransaction":45.4,"SalesAmount":6582.5},{"Top":2,"SellerUserID":-1,"SellerUserName":"酷有","OrderCount":1,"ProductQty":1,"JointRate":1,"perCustomerTransaction":22.14,"SalesAmount":22.14}]
     * SalesTotalQty : 368
     * PerCustTran : 45.24
     * TotalAmount : 6604.64
     */
    @Expose
    @SerializedName("SalesTotalQty")
    private int SalesTotalQty;
    @Expose
    @SerializedName("PerCustTran")
    private double PerCustTran;
    @Expose
    @SerializedName("TotalAmount")
    private double TotalAmount;
    @Expose
    @SerializedName("SellerList")
    private List<SellerListBean> SellerList;

    public int getSalesTotalQty() {
        return SalesTotalQty;
    }

    public void setSalesTotalQty(int SalesTotalQty) {
        this.SalesTotalQty = SalesTotalQty;
    }

    public double getPerCustTran() {
        return PerCustTran;
    }

    public void setPerCustTran(double PerCustTran) {
        this.PerCustTran = PerCustTran;
    }

    public double getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(double TotalAmount) {
        this.TotalAmount = TotalAmount;
    }

    public List<SellerListBean> getSellerList() {
        return SellerList;
    }

    public void setSellerList(List<SellerListBean> SellerList) {
        this.SellerList = SellerList;
    }

    public static class SellerListBean implements Serializable {
        private static final long serialVersionUID = -5780501393196146171L;
        /**
         * Top : 1
         * SellerUserID : 0
         * SellerUserName :
         * OrderCount : 145
         * ProductQty : 367
         * JointRate : 2.0
         * perCustomerTransaction : 45.4
         * SalesAmount : 6582.5
         */
        @Expose
        @SerializedName("Top")
        private int Top;
        @Expose
        @SerializedName("SellerUserID")
        private int SellerUserID;
        @Expose
        @SerializedName("SellerUserName")
        private String SellerUserName="";
        @Expose
        @SerializedName("OrderCount")
        private int OrderCount;
        @Expose
        @SerializedName("ProductQty")
        private int ProductQty;
        @Expose
        @SerializedName("JointRate")
        private double JointRate;
        @Expose
        @SerializedName("perCustomerTransaction")
        private double perCustomerTransaction;
        @Expose
        @SerializedName("SalesAmount")
        private double SalesAmount;

        public int getTop() {
            return Top;
        }

        public void setTop(int Top) {
            this.Top = Top;
        }

        public int getSellerUserID() {
            return SellerUserID;
        }

        public void setSellerUserID(int SellerUserID) {
            this.SellerUserID = SellerUserID;
        }

        public String getSellerUserName() {
            return SellerUserName;
        }

        public void setSellerUserName(String SellerUserName) {
            this.SellerUserName = SellerUserName;
        }

        public int getOrderCount() {
            return OrderCount;
        }

        public void setOrderCount(int OrderCount) {
            this.OrderCount = OrderCount;
        }

        public int getProductQty() {
            return ProductQty;
        }

        public void setProductQty(int ProductQty) {
            this.ProductQty = ProductQty;
        }

        public double getJointRate() {
            return JointRate;
        }

        public void setJointRate(double JointRate) {
            this.JointRate = JointRate;
        }

        public double getPerCustomerTransaction() {
            return perCustomerTransaction;
        }

        public void setPerCustomerTransaction(double perCustomerTransaction) {
            this.perCustomerTransaction = perCustomerTransaction;
        }

        public double getSalesAmount() {
            return SalesAmount;
        }

        public void setSalesAmount(double SalesAmount) {
            this.SalesAmount = SalesAmount;
        }
    }
}
