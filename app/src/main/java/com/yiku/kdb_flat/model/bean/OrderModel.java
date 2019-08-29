package com.yiku.kdb_flat.model.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class OrderModel implements Serializable {

    private static final long serialVersionUID = -3718423961923385896L;
    /**
     * OrderList : [{"Code":"bw18112316484933325820","SellerUserName":"好韵来","OptUserName":"好韵来","ProductQty":2,"PayableAmount":79.18,"CreateTime":"2018-11-23 16:48:49","Statu":"待支付","Items":[{"Cover":"http://nahuo-img-server.b0.upaiyun.com//3636/item/153734427781-5115.jpg","Name":"lilifeiyang内网测试 20180919第7","Sku":"1117842【】","Qty":2}]},{"Code":"bw18112316422277403592","SellerUserName":"好韵来","OptUserName":"好韵来","ProductQty":3,"PayableAmount":389.28,"CreateTime":"2018-11-23 16:42:22","Statu":"待支付","Items":[{"Cover":"http://nahuo-img-server.b0.upaiyun.com//3636/item/153742690649-7936.jpg","Name":"lilifeiyang 内网测试 201809201第二 原价不多说了","Sku":"1117853【】","Qty":3}]},{"Code":"bw18112316412555212586","SellerUserName":"好韵来","OptUserName":"好韵来","ProductQty":3,"PayableAmount":64.56,"CreateTime":"2018-11-23 16:41:25","Statu":"待支付","Items":[{"Cover":"http://nahuo-img-server.b0.upaiyun.com//3636/item/154155889819-3349.jpg","Name":"Lilifeiyang 内网测试 20181107 多尺码","Sku":"1117946【】","Qty":3}]},{"Code":"bw18112316404251059912","SellerUserName":"好韵来","OptUserName":"好韵来","ProductQty":2,"PayableAmount":106,"CreateTime":"2018-11-23 16:40:42","Statu":"待支付","Items":[{"Cover":"http://nahuo-img-server.b0.upaiyun.com//3636/item/154209794585-4637.jpg","Name":"lilifeiyang内网测试20181113第一","Sku":"1117969【】","Qty":2}]},{"Code":"bw18112310512922818854","SellerUserName":"好韵来","OptUserName":"好韵来","ProductQty":3,"PayableAmount":12.05,"CreateTime":"2018-11-23 10:51:29","Statu":"待支付","Items":[{"Cover":"http://nahuo-img-server.b0.upaiyun.com//3636/item/153785408296-1235.jpg","Name":"lilifeiyang 内网测试20180925第5","Sku":"1117860【】","Qty":1},{"Cover":"http://nahuo-img-server.b0.upaiyun.com//3636/item/153725032648-4180.jpg","Name":"Lilifeiyang 内网测试 20180918 第7","Sku":"1117824【】","Qty":2}]},{"Code":"bw18112310444310026682","SellerUserName":"好韵来","OptUserName":"好韵来","ProductQty":1,"PayableAmount":22.14,"CreateTime":"2018-11-23 10:44:43","Statu":"待支付","Items":[{"Cover":"http://nahuo-img-server.b0.upaiyun.com//3636/item/153896999435-5415.jpg","Name":"lilifeiyang 内网测试20181008第一","Sku":"1117927【】","Qty":1}]},{"Code":"bw18112310435312096123","SellerUserName":"好韵来","OptUserName":"好韵来","ProductQty":1,"PayableAmount":21.52,"CreateTime":"2018-11-23 10:43:53","Statu":"待支付","Items":[{"Cover":"http://nahuo-img-server.b0.upaiyun.com//3636/item/154155889819-3349.jpg","Name":"Lilifeiyang 内网测试 20181107 多尺码","Sku":"1117946【】","Qty":1}]},{"Code":"bw18112216374900534774","SellerUserName":"system","OptUserName":"好韵来","ProductQty":5,"PayableAmount":99.47,"CreateTime":"2018-11-22 16:37:49","Statu":"待支付","Items":[{"Cover":"http://nahuo-img-server.b0.upaiyun.com//3636/item/154155889819-3349.jpg","Name":"Lilifeiyang 内网测试 20181107 多尺码","Sku":"1117946【】","Qty":2},{"Cover":"http://nahuo-img-server.b0.upaiyun.com//129766/item/1539568261018.jpg","Name":"lilifeiyang内网测试20181015第一","Sku":"1117939【】","Qty":3}]},{"Code":"bw18112214522025551657","SellerUserName":null,"OptUserName":"好韵来","ProductQty":2,"PayableAmount":106,"CreateTime":"2018-11-22 14:52:20","Statu":"已取消","Items":[{"Cover":"http://nahuo-img-server.b0.upaiyun.com//3636/item/154209794585-4637.jpg","Name":"lilifeiyang内网测试20181113第一","Sku":"1117969【】","Qty":2}]},{"Code":"bw18112114153346684606","SellerUserName":null,"OptUserName":"好韵来","ProductQty":1,"PayableAmount":19.48,"CreateTime":"2018-11-21 14:15:33","Statu":"已完成","Items":[{"Cover":"http://nahuo-img-server.b0.upaiyun.com//3636/item/153896999435-5415.jpg","Name":"lilifeiyang 内网测试20181008第一","Sku":"1117927【】","Qty":1}]},{"Code":"bw18101916345720457935","SellerUserName":null,"OptUserName":"好韵来","ProductQty":1,"PayableAmount":44.04,"CreateTime":"2018-10-19 16:34:57","Statu":"已取消","Items":[{"Cover":"http://nahuo-img-server.b0.upaiyun.com//3636/item/153742690549-59.jpg","Name":"lilifeiyang 内网测试 20180920第一 原价","Sku":"1117851【】","Qty":1}]},{"Code":"bw18101916343648534925","SellerUserName":null,"OptUserName":"好韵来","ProductQty":2,"PayableAmount":88.08,"CreateTime":"2018-10-19 16:34:36","Statu":"待支付","Items":[{"Cover":"http://nahuo-img-server.b0.upaiyun.com//3636/item/153742690549-59.jpg","Name":"lilifeiyang 内网测试 20180920第一 原价","Sku":"1117851【】","Qty":2}]},{"Code":"bw18101212045908847059","SellerUserName":null,"OptUserName":"好韵来","ProductQty":1,"PayableAmount":22.14,"CreateTime":"2018-10-12 12:04:59","Statu":"已完成","Items":[{"Cover":"http://nahuo-img-server.b0.upaiyun.com//3636/item/153896999435-5415.jpg","Name":"lilifeiyang 内网测试20181008第一11","Sku":"1117927【】","Qty":1}]},{"Code":"bw18101211451178551877","SellerUserName":null,"OptUserName":"好韵来","ProductQty":1,"PayableAmount":22.14,"CreateTime":"2018-10-12 11:45:11","Statu":"已完成","Items":[{"Cover":"http://nahuo-img-server.b0.upaiyun.com//3636/item/153896999435-5415.jpg","Name":"lilifeiyang 内网测试20181008第一11","Sku":"1117927【】","Qty":1}]},{"Code":"bw18093010145449363180","SellerUserName":null,"OptUserName":"好韵来","ProductQty":1,"PayableAmount":44.04,"CreateTime":"2018-09-30 10:14:54","Statu":"已完成","Items":[{"Cover":"http://nahuo-img-server.b0.upaiyun.com//3636/item/153742690549-59.jpg","Name":"lilifeiyang 内网测试 20180920第一 原价","Sku":"1117851【】","Qty":1}]},{"Code":"bw18093009545363064189","SellerUserName":null,"OptUserName":"好韵来","ProductQty":1,"PayableAmount":129.76,"CreateTime":"2018-09-30 09:54:53","Statu":"已完成","Items":[{"Cover":"http://nahuo-img-server.b0.upaiyun.com//3636/item/153742690649-7936.jpg","Name":"lilifeiyang 内网测试 20180920第二 原价不多说了","Sku":"1117853【】","Qty":1}]},{"Code":"bw18092918024042846270","SellerUserName":null,"OptUserName":"好韵来","ProductQty":1,"PayableAmount":64.88,"CreateTime":"2018-09-29 18:02:40","Statu":"待支付","Items":[{"Cover":"http://nahuo-img-server.b0.upaiyun.com//3636/item/153742690649-7936.jpg","Name":"lilifeiyang 内网测试 20180920第二 原价不多说了","Sku":"1117853【】","Qty":1}]},{"Code":"bw18092610543677406865","SellerUserName":null,"OptUserName":"好韵来","ProductQty":1,"PayableAmount":129.76,"CreateTime":"2018-09-26 10:54:36","Statu":"已完成","Items":[{"Cover":"http://nahuo-img-server.b0.upaiyun.com//3636/item/153742690649-7936.jpg","Name":"lilifeiyang 内网测试 20180920第二 原价不多说了","Sku":"1117853【】","Qty":1}]},{"Code":"bw18092610531195997634","SellerUserName":null,"OptUserName":"好韵来","ProductQty":1,"PayableAmount":129.76,"CreateTime":"2018-09-26 10:53:11","Statu":"已完成","Items":[{"Cover":"http://nahuo-img-server.b0.upaiyun.com//3636/item/153742690649-7936.jpg","Name":"lilifeiyang 内网测试 20180920第二 原价不多说了","Sku":"1117853【】","Qty":1}]},{"Code":"bw18092514255634122035","SellerUserName":null,"OptUserName":"好韵来","ProductQty":1,"PayableAmount":6,"CreateTime":"2018-09-25 14:25:56","Statu":"已完成","Items":[{"Cover":"http://nahuo-img-server.b0.upaiyun.com//3636/item/153725032648-4180.jpg","Name":"Lilifeiyang 内网测试 20180918 第7","Sku":"1117824【】","Qty":1}]}]
     * TotalProductQty : 364
     * TotalPayableAmount : 6516.08
     * GetSummary : 销售数量：364件，金额：6516.08
     */
    @Expose
    @SerializedName("TotalProductQty")
    private int TotalProductQty;
    @Expose
    @SerializedName("TotalPayableAmount")
    private double TotalPayableAmount;
    @Expose
    @SerializedName("GetSummary")
    private String GetSummary="";


    @Expose
    @SerializedName("OrderList")
    private List<OrderListBean> OrderList;

    public int getTotalProductQty() {
        return TotalProductQty;
    }

    public void setTotalProductQty(int TotalProductQty) {
        this.TotalProductQty = TotalProductQty;
    }

    public double getTotalPayableAmount() {
        return TotalPayableAmount;
    }

    public void setTotalPayableAmount(double TotalPayableAmount) {
        this.TotalPayableAmount = TotalPayableAmount;
    }

    public String getGetSummary() {
        return GetSummary;
    }

    public void setGetSummary(String GetSummary) {
        this.GetSummary = GetSummary;
    }

    public List<OrderListBean> getOrderList() {
        return OrderList;
    }

    public void setOrderList(List<OrderListBean> OrderList) {
        this.OrderList = OrderList;
    }

    public static class OrderListBean implements Serializable {
        private static final long serialVersionUID = -7442798443893642943L;
        /**
         * Code : bw18112316484933325820
         * SellerUserName : 好韵来
         * OptUserName : 好韵来
         * ProductQty : 2
         * PayableAmount : 79.18
         * CreateTime : 2018-11-23 16:48:49
         * Statu : 待支付
         * Items : [{"Cover":"http://nahuo-img-server.b0.upaiyun.com//3636/item/153734427781-5115.jpg","Name":"lilifeiyang内网测试 20180919第7","Sku":"1117842【】","Qty":2}]
         */
        @Expose
        @SerializedName("RCSummary")
        private String RCSummary="";

        public String getRCSummary() {
            return RCSummary;
        }

        public void setRCSummary(String RCSummary) {
            this.RCSummary = RCSummary;
        }
        @Expose
        @SerializedName("Mobile")
        private String mobile="";

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        @Expose
        @SerializedName("Code")
        private String Code="";
        @Expose
        @SerializedName("SellerUserName")
        private String SellerUserName="";
        @Expose
        @SerializedName("OptUserName")
        private String OptUserName="";
        @Expose
        @SerializedName("ProductQty")
        private int ProductQty;
        @Expose
        @SerializedName("PayableAmount")
        private double PayableAmount;
        @Expose
        @SerializedName("CreateTime")
        private String CreateTime="";
        @Expose
        @SerializedName("Statu")
        private String Statu="";
        @Expose
        @SerializedName("Items")
        private List<ItemsBean> Items;

        public String getCode() {
            return Code;
        }

        public void setCode(String Code) {
            this.Code = Code;
        }

        public String getSellerUserName() {
            return SellerUserName;
        }

        public void setSellerUserName(String SellerUserName) {
            this.SellerUserName = SellerUserName;
        }

        public String getOptUserName() {
            return OptUserName;
        }

        public void setOptUserName(String OptUserName) {
            this.OptUserName = OptUserName;
        }

        public int getProductQty() {
            return ProductQty;
        }

        public void setProductQty(int ProductQty) {
            this.ProductQty = ProductQty;
        }

        public double getPayableAmount() {
            return PayableAmount;
        }

        public void setPayableAmount(double PayableAmount) {
            this.PayableAmount = PayableAmount;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getStatu() {
            return Statu;
        }

        public void setStatu(String Statu) {
            this.Statu = Statu;
        }

        public List<ItemsBean> getItems() {
            return Items;
        }

        public void setItems(List<ItemsBean> Items) {
            this.Items = Items;
        }

        public static class ItemsBean implements Serializable {
            private static final long serialVersionUID = -529420039897051475L;
            /**
             * Cover : http://nahuo-img-server.b0.upaiyun.com//3636/item/153734427781-5115.jpg
             * Name : lilifeiyang内网测试 20180919第7
             * Sku : 1117842【】
             * Qty : 2
             */

            @Expose
            @SerializedName("Cover")
            private String Cover="";
            @Expose
            @SerializedName("Name")
            private String Name="";
            @Expose
            @SerializedName("Code")
            private String Sku="";
            @Expose
            @SerializedName("Qty")
            private int Qty;
            @Expose
            @SerializedName("RetailPrice")
            private String RetailPrice="0.00";
            @SerializedName("DiscountPrice")
            private String DiscountPrice="0.00";

            public String getDiscountPrice() {
                return DiscountPrice;
            }

            public void setDiscountPrice(String discountPrice) {
                DiscountPrice = discountPrice;
            }

            /**
             * OrgRetailPrice : 0.0
             * IsOnSale : false
             */

            @SerializedName("OrgRetailPrice")
            private double OrgRetailPrice;
            @SerializedName("IsOnSale")
            private boolean IsOnSale;

            public String getRetailPrice() {
                return RetailPrice;
            }

            public void setRetailPrice(String retailPrice) {
                RetailPrice = retailPrice;
            }

            public String getCover() {
                return Cover;
            }

            public void setCover(String Cover) {
                this.Cover = Cover;
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

            public int getQty() {
                return Qty;
            }

            public void setQty(int Qty) {
                this.Qty = Qty;
            }

            public double getOrgRetailPrice() {
                return OrgRetailPrice;
            }

            public void setOrgRetailPrice(double OrgRetailPrice) {
                this.OrgRetailPrice = OrgRetailPrice;
            }

            public boolean isIsOnSale() {
                return IsOnSale;
            }

            public void setIsOnSale(boolean IsOnSale) {
                this.IsOnSale = IsOnSale;
            }
        }
    }

//    @Expose
//    public String OrderCode;
//    @Expose
//    public String CreateTime;
//    @Expose
//    public List<OrderProductModel> Products;
//    @Expose
//    public double ProductAmount;
//    @Expose
//    public int ProductCount;
//    @Expose
//    public double Discount;
//    @Expose
//    public double PayableAmount;
//    @Expose
//    private String Statu="";
//
//    public String getStatu() {
//        return Statu;
//    }
//
//    public void setStatu(String statu) {
//        Statu = statu;
//    }
//
//    public class OrderProductModel implements Serializable {
//
//        private static final long serialVersionUID = -2492773240079820019L;
//
//        @Expose
//        private String Name;
//        @Expose
//        private String Cover;
//        @Expose
//        private String Color;
//        @Expose
//        private String Size;
//        @Expose
//        private int Qty;
//        @Expose
//        private double Price;
//
//        public double getPrice() {
//            return Price;
//        }
//
//        public void setPrice(double price) {
//            Price = price;
//        }
//
//        public String getName() {
//            return Name;
//        }
//
//        public void setName(String name) {
//            Name = name;
//        }
//
//        public String getCover() {
//            return Cover;
//        }
//
//        public void setCover(String cover) {
//            Cover = cover;
//        }
//
//        public String getColor() {
//            return Color;
//        }
//
//        public void setColor(String color) {
//            Color = color;
//        }
//
//        public String getSize() {
//            return Size;
//        }
//
//        public void setSize(String size) {
//            Size = size;
//        }
//
//        public int getQty() {
//            return Qty;
//        }
//
//        public void setQty(int qty) {
//            Qty = qty;
//        }
//    }
//
//    public double getPayableAmount() {
//        return PayableAmount;
//    }
//
//    public void setPayableAmount(double payableAmount) {
//        PayableAmount = payableAmount;
//    }
//
//    public String getOrderCode() {
//        return OrderCode;
//    }
//
//    public void setOrderCode(String orderCode) {
//        OrderCode = orderCode;
//    }
//
//    public String getCreateTime() {
//        return CreateTime;
//    }
//
//    public void setCreateTime(String createTime) {
//        CreateTime = createTime;
//    }
//
//    public List<OrderProductModel> getProducts() {
//        return Products;
//    }
//
//    public void setProducts(List<OrderProductModel> products) {
//        Products = products;
//    }
//
//    public double getProductAmount() {
//        return ProductAmount;
//    }
//
//    public void setProductAmount(double productAmount) {
//        ProductAmount = productAmount;
//    }
//
//    public int getProductCount() {
//        return ProductCount;
//    }
//
//    public void setProductCount(int productCount) {
//        ProductCount = productCount;
//    }
//
//    public double getDiscount() {
//        return Discount;
//    }
//
//    public void setDiscount(double discount) {
//        Discount = discount;
//    }
}
