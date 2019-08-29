package com.yiku.kdb_flat.model.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jame on 2018/11/30.
 */

public class MBean implements Serializable {
    private static final long serialVersionUID = 7662622593956551870L;
    @Expose
    @SerializedName("ShopNo")
    private String ShopNo = "";

    public String getShopNo() {
        return ShopNo;
    }

    public void setShopNo(String shopNo) {
        ShopNo = shopNo;
    }

    @Expose
    @SerializedName("List")
    private List<MemberBean> list;

    public List<MemberBean> getList() {
        return list;
    }

    public void setList(List<MemberBean> list) {
        this.list = list;
    }
}
