package com.yiku.kdb_flat.model.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderButton implements Serializable {
    public static String ACTION_PAYORDER="PayOrder";
    public static String ACTION_CANCELORDER="CancelOrder";
    public static String ACTION_REFUNDORDER="RefundOrder";
    private static final long serialVersionUID = -3634477538870340066L;
    public static int type_small = 1;
    public static int type_big = 2;
    private int btn_type = type_small;

    public int getBtn_type() {
        return btn_type;
    }

    public void setBtn_type(int btn_type) {
        this.btn_type = btn_type;
    }

    @Expose
    @SerializedName("Name")
    private String title = "";
    @Expose
    @SerializedName("Ico")
    private String Ico="";

    public String getIco() {
        return Ico;
    }

    public void setIco(String ico) {
        Ico = ico;
    }

    @Expose
    @SerializedName("Action")
    private String action = "";
    @Expose
    @SerializedName("IsPoint")
    private boolean isPoint;
    @Expose
    @SerializedName("IsEnable")
    private boolean isEnable;
    @Expose
    @SerializedName("type")
    private String type = "";
    @Expose
    @SerializedName("data")
    private int data;

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }

    public boolean isPoint() {
        return isPoint;
    }

    public void setPoint(boolean isPoint) {
        this.isPoint = isPoint;
    }

}
