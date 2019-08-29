package com.yiku.kdb_flat.model.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jame on 2018/9/18.
 */

public class PayBean {

    /**
     * Statu : 待支付
     */
    @Expose
    @SerializedName("Statu")
    private String Statu = "";

    public String getStatu() {
        return Statu;
    }

    public void setStatu(String Statu) {
        this.Statu = Statu;
    }
}
