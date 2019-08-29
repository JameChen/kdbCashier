package com.yiku.kdb_flat.model.bean;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

public class SubmitOrderModel implements Serializable {

    private static final long serialVersionUID = -3718423961923385896L;

    @Expose
    public String OrderCode;
    @Expose
    public String CreateTime;
    @Expose
    public List<OrderProductModel> Products;
    @Expose
    public double ProductAmount;
    @Expose
    public int ProductCount;
    @Expose
    public double Discount;
    @Expose
    public OrderShopModel ShopInfo;
    @Expose
    public double PayableAmount;

    public class OrderShopModel implements Serializable {

        private static final long serialVersionUID = -2492773240079820019L;

        @Expose
        private String Name;

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }
    }
    public class OrderProductModel implements Serializable {

        private static final long serialVersionUID = -2492773240079820019L;

        @Expose
        private String Name;
        @Expose
        private String Cover;
        @Expose
        private String Color;
        @Expose
        private String Size;
        @Expose
        private int Qty;
        @Expose
        private double Price;

        public double getPrice() {
            return Price;
        }

        public void setPrice(double price) {
            Price = price;
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
    }

    public String getOrderCode() {
        return OrderCode;
    }

    public void setOrderCode(String orderCode) {
        OrderCode = orderCode;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public List<OrderProductModel> getProducts() {
        return Products;
    }

    public void setProducts(List<OrderProductModel> products) {
        Products = products;
    }

    public double getProductAmount() {
        return ProductAmount;
    }

    public void setProductAmount(double productAmount) {
        ProductAmount = productAmount;
    }

    public int getProductCount() {
        return ProductCount;
    }

    public void setProductCount(int productCount) {
        ProductCount = productCount;
    }

    public double getDiscount() {
        return Discount;
    }

    public void setDiscount(double discount) {
        Discount = discount;
    }

    public OrderShopModel getShopInfo() {
        return ShopInfo;
    }

    public void setShopInfo(OrderShopModel shopInfo) {
        ShopInfo = shopInfo;
    }

    public double getPayableAmount() {
        return PayableAmount;
    }

    public void setPayableAmount(double payableAmount) {
        PayableAmount = payableAmount;
    }
}
