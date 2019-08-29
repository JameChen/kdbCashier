package com.yiku.kdb_flat.model.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ShopCartModel implements Serializable {

    private static final long serialVersionUID = -2492773240079820019L;
    @SerializedName("Code")
    private String Code="";
    /**
     * OrgRetailPrice : 229.00
     * IsOnSale : false
     */

    @SerializedName("OrgRetailPrice")
    private String OrgRetailPrice="0.00";
    @SerializedName("IsOnSale")
    private boolean IsOnSale;
    /**
     * OrderProductsID : 0
     * Type : 正常
     * TypeID : 0
     */
    private boolean isCheck;
    /**
     * HasPointChange : false
     * PointPrice : 0.0
     * Point : 0
     */

    @SerializedName("HasPointChange")
    private boolean HasPointChange;
    @SerializedName("PointPrice")
    private double PointPrice;
    @SerializedName("Point")
    private int Point;
    @SerializedName("IsChanged")
    private boolean IsChanged;

    public boolean isChanged() {
        return IsChanged;
    }

    public void setChanged(boolean changed) {
        IsChanged = changed;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    @SerializedName("OrderProductsID")
    private int OrderProductsID;
    @SerializedName("Type")
    private String Type="";
    @SerializedName("TypeID")
    private int TypeID;

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    private boolean isSelect;
    @Expose
    private String Color="";
    @Expose
    private String Size="";
    @Expose
    private int Qty;
    @Expose
    private double Price=0;
    @Expose
    private int ItemID;
    private double transPrice=0;

    public double getTransPrice() {
        return transPrice;
    }

    public void setTransPrice(double transPrice) {
        this.transPrice = transPrice;
    }

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int itemID) {
        ItemID = itemID;
    }

    @Expose
    private int ID;
    @Expose
    private String Name="";
    @Expose
    private String Cover="";

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

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }



    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

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

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getOrgRetailPrice() {
        return OrgRetailPrice;
    }

    public void setOrgRetailPrice(String OrgRetailPrice) {
        this.OrgRetailPrice = OrgRetailPrice;
    }

    public boolean isIsOnSale() {
        return IsOnSale;
    }

    public void setIsOnSale(boolean IsOnSale) {
        this.IsOnSale = IsOnSale;
    }

    public int getOrderProductsID() {
        return OrderProductsID;
    }

    public void setOrderProductsID(int OrderProductsID) {
        this.OrderProductsID = OrderProductsID;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public int getTypeID() {
        return TypeID;
    }

    public void setTypeID(int TypeID) {
        this.TypeID = TypeID;
    }

    public boolean isHasPointChange() {
        return HasPointChange;
    }

    public void setHasPointChange(boolean HasPointChange) {
        this.HasPointChange = HasPointChange;
    }

    public double getPointPrice() {
        return PointPrice;
    }

    public void setPointPrice(double PointPrice) {
        this.PointPrice = PointPrice;
    }

    public int getPoint() {
        return Point;
    }

    public void setPoint(int Point) {
        this.Point = Point;
    }
}
