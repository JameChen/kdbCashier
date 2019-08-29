package com.yiku.kdb_flat.model.bean;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class PurchaseModel implements Serializable {

    private static final long serialVersionUID = -2492773240079820019L;

        @Expose
        private String FloorName;
        @Expose
        private String StallsName;
        @Expose
        private String MarketName;
        @Expose
        private String Code;
        @Expose
        private double TotalMoney;
        @Expose
        private double KdMoney;
        @Expose
        private String StatusName;
        @Expose
        private int OrderID;

//采购单列表需要
    @Expose
    private String Time;
    @Expose
    private String Date;
    @Expose
    private int ID;
    @Expose
    private String BillPic;


    public String getFloorName() {
            return FloorName;
        }

        public void setFloorName(String floorName) {
            FloorName = floorName;
        }

        public String getStallsName() {
            return StallsName;
        }

        public void setStallsName(String stallsName) {
            StallsName = stallsName;
        }

        public String getMarketName() {
            return MarketName;
        }

        public void setMarketName(String marketName) {
            MarketName = marketName;
        }

        public String getCode() {
            return Code;
        }

        public void setCode(String code) {
            Code = code;
        }

        public double getTotalMoney() {
            return TotalMoney;
        }

        public void setTotalMoney(double totalMoney) {
            TotalMoney = totalMoney;
        }

        public double getKdMoney() {
            return KdMoney;
        }

        public void setKdMoney(double kdMoney) {
            KdMoney = kdMoney;
        }

        public String getStatusName() {
            return StatusName;
        }

        public void setStatusName(String statusName) {
            StatusName = statusName;
        }

        public int getOrderID() {
            return OrderID;
        }

        public void setOrderID(int orderID) {
            OrderID = orderID;
        }

    public void setTime(String Time) {
        this.Time = Time;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setBillPic(String BillPic) {
        this.BillPic = BillPic;
    }

    public String getTime() {
        return Time;
    }

    public String getDate() {
        return Date;
    }

    public int getID() {
        return ID;
    }

    public String getBillPic() {
        return BillPic;
    }
}
