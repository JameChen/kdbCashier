package com.yiku.kdb_flat.model.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jame on 2018/9/18.
 */

public class CodeBean {

    /**
     * QrPayCode : https://pay.weixin.qq.com/wiki/doc/api/img/chapter6_5_2.png
     */
    @Expose
    @SerializedName("Action")
    private String Action = "";

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }
    @Expose
    @SerializedName("PayType")
    private String payType = "";

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    @Expose
    @SerializedName("QrPayCode")
    private String QrPayCode = "";

    public String getQrPayCode() {
        return QrPayCode;
    }

    public void setQrPayCode(String QrPayCode) {
        this.QrPayCode = QrPayCode;
    }
}
