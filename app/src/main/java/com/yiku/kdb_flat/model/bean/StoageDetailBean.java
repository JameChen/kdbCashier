package com.yiku.kdb_flat.model.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jame on 2018/11/28.
 */

public class StoageDetailBean implements Serializable {
    private static final long serialVersionUID = 8090536453998819613L;

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    /**
     * SourceID : 1117939
     * Name : lilifeiyang内网测试20181015第一
     * Sku : 1117939【】
     * StockQty : 7
     * Cover : upyun:nahuo-img-server://129766/item/1539568261018.jpg
     * LastStockTime :
     */
    @SerializedName("Code")

    @Expose
    private String Code = "";
    @Expose
    @SerializedName("ColorSize")
    private List<ProductModel> ColorSize;

    public List<ProductModel> getColorSize() {
        return ColorSize;
    }

    public void setColorSize(ArrayList<ProductModel> colorSize) {
        ColorSize = colorSize;
    }

    @Expose
    @SerializedName("SourceID")
    private int SourceID;
    @Expose
    @SerializedName("Name")
    private String Name="";
    @Expose
    @SerializedName("Sku")
    private String Sku="";
    @Expose
    @SerializedName("StockQty")
    private int StockQty;
    @Expose
    @SerializedName("Cover")
    private String Cover="";
    @Expose
    @SerializedName("LastStockTime")
    private String LastStockTime="";

    public int getSourceID() {
        return SourceID;
    }

    public void setSourceID(int SourceID) {
        this.SourceID = SourceID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getSku() {
        return Sku;
    }

    public void setSku(String Sku) {
        this.Sku = Sku;
    }

    public int getStockQty() {
        return StockQty;
    }

    public void setStockQty(int StockQty) {
        this.StockQty = StockQty;
    }

    public String getCover() {
        return Cover;
    }

    public void setCover(String Cover) {
        this.Cover = Cover;
    }

    public String getLastStockTime() {
        return LastStockTime;
    }

    public void setLastStockTime(String LastStockTime) {
        this.LastStockTime = LastStockTime;
    }
}
