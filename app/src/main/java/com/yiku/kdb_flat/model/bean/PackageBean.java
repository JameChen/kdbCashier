package com.yiku.kdb_flat.model.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jame on 2018/11/27.
 */

public class PackageBean implements Serializable{

    private static final long serialVersionUID = -3098048236858381851L;
    @Expose
    @SerializedName("PackageList")
    private List<PackageListBean> PackageList;

    public List<PackageListBean> getPackageList() {
        return PackageList;
    }

    public void setPackageList(List<PackageListBean> PackageList) {
        this.PackageList = PackageList;
    }

    public static class PackageListBean implements Serializable{
        private static final long serialVersionUID = 3115038360961908514L;
        /**
         * PackageID : 190578
         * ExpressCode : 3387270791287
         * CreateTime : 2018-11-27 09:32:09
         * ShipQty : 62
         * ExpressName : 申通
         * Sender : 广州发货
         */
        @Expose
        @SerializedName("PackageID")
        private int PackageID;
        @Expose
        @SerializedName("ExpressCode")
        private String ExpressCode="";
        @Expose
        @SerializedName("CreateTime")
        private String CreateTime="";
        @Expose
        @SerializedName("ShipQty")
        private int ShipQty;
        @Expose
        @SerializedName("ExpressName")
        private String ExpressName="";
        @Expose
        @SerializedName("Sender")
        private String Sender="";

        public int getPackageID() {
            return PackageID;
        }

        public void setPackageID(int PackageID) {
            this.PackageID = PackageID;
        }

        public String getExpressCode() {
            return ExpressCode;
        }

        public void setExpressCode(String ExpressCode) {
            this.ExpressCode = ExpressCode;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public int getShipQty() {
            return ShipQty;
        }

        public void setShipQty(int ShipQty) {
            this.ShipQty = ShipQty;
        }

        public String getExpressName() {
            return ExpressName;
        }

        public void setExpressName(String ExpressName) {
            this.ExpressName = ExpressName;
        }

        public String getSender() {
            return Sender;
        }

        public void setSender(String Sender) {
            this.Sender = Sender;
        }
    }
}
