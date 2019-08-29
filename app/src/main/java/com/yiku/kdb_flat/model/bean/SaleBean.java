package com.yiku.kdb_flat.model.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jame on 2018/9/13.
 */

public class SaleBean implements Serializable {
    private static final long serialVersionUID = 3169588200961925883L;
    @Expose
    private String Summary = "";
    @Expose
    @SerializedName("SellerUsers")
    private List<SelectBean> SellerUsers;

    public OrderModel getOrders() {
        return Orders;
    }

    public void setOrders(OrderModel orders) {
        Orders = orders;
    }

    @SerializedName("Orders")
    @Expose
    private OrderModel Orders;

    public String getSummary() {
        return Summary;
    }

    public void setSummary(String summary) {
        Summary = summary;
    }

    public List<SelectBean> getSellerUsers() {
        return SellerUsers;
    }

    public void setSellerUsers(List<SelectBean> SellerUsers) {
        this.SellerUsers = SellerUsers;
    }

}
