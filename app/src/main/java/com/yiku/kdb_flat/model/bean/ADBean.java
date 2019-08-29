package com.yiku.kdb_flat.model.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jame on 2018/12/8.
 */

public class ADBean implements Serializable {
    List<String> Ads;
    @SerializedName("Data")
    private List<String> Data;

    public List<String> getAds() {
        return Ads;
    }

    public void setAds(List<String> ads) {
        Ads = ads;
    }

    public List<String> getData() {
        return Data;
    }

    public void setData(List<String> Data) {
        this.Data = Data;
    }
}
