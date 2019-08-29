package com.yiku.kdb_flat.model.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ScanQrcodeModel implements Serializable {

    private static final long serialVersionUID = -2492773240079820019L;
    @Expose
    private String Cover;
    private int DhQty=1;

    public String getColor_Size() {
        return Color_Size;
    }

    private  String Color_Size ;

    public void setColor_Size(String color_Size) {
        Color_Size = color_Size;
    }

    public int getDhQty() {
        return DhQty;
    }

    public void setDhQty(int dhQty) {
        DhQty = dhQty;
    }

    public String getCover() {
        return Cover;
    }

    public void setCover(String cover) {
        Cover = cover;
    }

    @Expose
    private double OrgPrice;
    @Expose
    private double AgentPrice;
    @Expose
    private String Name;
    @Expose
    private String Color;
    @Expose
    private String Size;
    @SerializedName("SourceID")
    @Expose
    private int ItemID;
    @Expose
    private int MaxQty;

    public double getOrgPrice() {
        return OrgPrice;
    }

    public void setOrgPrice(double orgPrice) {
        OrgPrice = orgPrice;
    }

    public double getAgentPrice() {
        return AgentPrice;
    }

    public void setAgentPrice(double agentPrice) {
        AgentPrice = agentPrice;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int itemID) {
        ItemID = itemID;
    }

    public int getMaxQty() {
        return MaxQty;
    }

    public void setMaxQty(int maxQty) {
        MaxQty = maxQty;
    }
}
