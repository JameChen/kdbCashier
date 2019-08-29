package com.yiku.kdb_flat.model.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ShopItemListModel implements Serializable {

    private static final long serialVersionUID = 7427104386583994172L;
    public boolean is_check = false;

    public boolean is_check() {
        return is_check;
    }

    public void setIs_check(boolean is_check) {
        this.is_check = is_check;
    }
    @SerializedName("Code")
    private String Code="";

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    @Expose
    public boolean IsHide=false;

    public boolean isHide() {
        return IsHide;
    }

    public void setHide(boolean hide) {
        IsHide = hide;
    }

    @Expose
    public String Name;
    @Expose
    public String Cover;
    @Expose
    public int BookQty;
    @Expose
    public int ShipQty;
    @SerializedName("OrgPrice")
    @Expose
    public double Price;
    @SerializedName("SourceID")
    @Expose
    public int AgentItemId;
    @Expose
    public int ItemID;
    @Expose
    public int ID;
    @Expose
    public double RetailPrice;
    @Expose
    public int QsId;
    @Expose
    public int Qty;
//    @Expose
//    public double OriPrice;
//    @Expose
//    public double AgentPrice;
    @Expose
    public String QsName;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCover() {
        return Cover;
    }

    public void setCover(String cover) {
        Cover = cover;
    }

    public int getBookQty() {
        return BookQty;
    }

    public void setBookQty(int bookQty) {
        BookQty = bookQty;
    }

    public int getShipQty() {
        return ShipQty;
    }

    public void setShipQty(int shipQty) {
        ShipQty = shipQty;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public int getAgentItemId() {
        return AgentItemId;
    }

    public void setAgentItemId(int agentItemId) {
        AgentItemId = agentItemId;
    }

    public double getRetailPrice() {
        return RetailPrice;
    }

    public void setRetailPrice(double retailPrice) {
        RetailPrice = retailPrice;
    }

    public int getQsId() {
        return QsId;
    }

    public void setQsId(int qsId) {
        QsId = qsId;
    }

    public String getQsName() {
        return QsName;
    }

    public void setQsName(String qsName) {
        QsName = qsName;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    public double getOriPrice() {
        return Price;
    }

    public void setOriPrice(double oriPrice) {
        Price = oriPrice;
    }

    public double getAgentPrice() {
        return Price;
    }

    public void setAgentPrice(double agentPrice) {
        Price = agentPrice;
    }

    @Expose
    public int dhQty;
    @Expose
    private String Color;// 颜色
    @Expose
    private String Size;// 尺码

    public int getDhQty() {
        return dhQty;
    }

    public void setDhQty(int dhQty) {
        if (dhQty < 0) {
            dhQty = 0;
        }
        if (dhQty > getShipQty()) {
            dhQty = getShipQty();
        }
        this.dhQty = dhQty;
    }

    public String getColor() {
        return Color == null ? "" : Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getSize() {
        return Size == null ? "" : Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public int getItemId() {
        return ItemID;
    }

    public void setItemId(int itemId) {
        ItemID = itemId;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
