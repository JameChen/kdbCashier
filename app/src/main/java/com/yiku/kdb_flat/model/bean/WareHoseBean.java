package com.yiku.kdb_flat.model.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jame on 2018/12/21.
 */

public class WareHoseBean implements Serializable {
    private static final long serialVersionUID = 7527104386583994172L;
    /**
     * TotalQty : 1035
     * Money : 113128.71
     */

    @SerializedName("TotalQty")
    private int TotalQty;
    @SerializedName("Money")
    private double Money;
    @SerializedName("List")
    private List<ShopItemListModel> list;

    public List<ShopItemListModel> getList() {
        return list;
    }

    public void setList(List<ShopItemListModel> list) {
        this.list = list;
    }

    public int getTotalQty() {
        return TotalQty;
    }

    public void setTotalQty(int TotalQty) {
        this.TotalQty = TotalQty;
    }

    public double getMoney() {
        return Money;
    }

    public void setMoney(double Money) {
        this.Money = Money;
    }
}
