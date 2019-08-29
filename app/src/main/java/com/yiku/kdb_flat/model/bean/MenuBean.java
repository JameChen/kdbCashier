package com.yiku.kdb_flat.model.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jame on 2017/5/17.
 */

public class MenuBean implements Serializable {

    private static final long serialVersionUID = 1349368837655917573L;
    /**
     * UserID : 339925
     * UserName : 好韵来
     * Cashier : {"Show":true,"Name":"收银台","ImgUrl":"http://img/cashier.jpg"}
     * Menus : [{"ID":"InStorage","Name":"入库","ImgUrl":"http://img/instorage.jpg"},{"ID":"Item","Name":"商品","ImgUrl":"http://img/item.jpg"},{"ID":"Stock","Name":"库存","ImgUrl":"http://img/Stock.jpg"}]
     * NavBar : [{"ID":"SaleBill","Name":"销售单","Remark":"¥ 18990"},{"ID":"ModifyPriceBill","Name":"调价单","Remark":"3款"},{"ID":"Achievement","Name":"业绩PK","Remark":""},{"ID":"Set","Name":"设置","Remark":""}]
     * PageElement : {"IsShowBuyingPrice":true,"IsShowRetailPrice":true,"IsEditableTitle":true,"IsEditablePrice":true,"IsAddGroup":true}
     */
    @Expose
    @SerializedName("UserID")
    private int UserID;
    @Expose
    @SerializedName("UserName")
    private String UserName="";
    @Expose
    @SerializedName("Cashier")
    private CashierBean Cashier;
    @Expose
    @SerializedName("PageElement")
    private PageElementBean PageElement;
    @Expose
    @SerializedName("Menus")
    private List<MenusBean> Menus;
    @Expose
    @SerializedName("NavBar")
    private List<NavBarBean> NavBar;

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public CashierBean getCashier() {
        return Cashier;
    }

    public void setCashier(CashierBean Cashier) {
        this.Cashier = Cashier;
    }

    public PageElementBean getPageElement() {
        return PageElement;
    }

    public void setPageElement(PageElementBean PageElement) {
        this.PageElement = PageElement;
    }

    public List<MenusBean> getMenus() {
        return Menus;
    }

    public void setMenus(List<MenusBean> Menus) {
        this.Menus = Menus;
    }

    public List<NavBarBean> getNavBar() {
        return NavBar;
    }

    public void setNavBar(List<NavBarBean> NavBar) {
        this.NavBar = NavBar;
    }

    public static class CashierBean implements Serializable{
        private static final long serialVersionUID = 1876688515339816004L;
        /**
         * Show : true
         * Name : 收银台
         * ImgUrl : http://img/cashier.jpg
         */
        @Expose
        @SerializedName("Show")
        private boolean Show;
        @Expose
        @SerializedName("Name")
        private String Name="";
        @Expose
        @SerializedName("ImgUrl")
        private String ImgUrl="";

        public boolean isShow() {
            return Show;
        }

        public void setShow(boolean Show) {
            this.Show = Show;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getImgUrl() {
            return ImgUrl;
        }

        public void setImgUrl(String ImgUrl) {
            this.ImgUrl = ImgUrl;
        }
    }

    public static class PageElementBean implements Serializable{
        private static final long serialVersionUID = -5266745259477762021L;
        /**
         * IsShowBuyingPrice : true
         * IsShowRetailPrice : true
         * IsEditableTitle : true
         * IsEditablePrice : true
         * IsAddGroup : true
         */
        @Expose
        @SerializedName("IsShowBuyingPrice")
        private boolean IsShowBuyingPrice;
        @Expose
        @SerializedName("IsShowRetailPrice")
        private boolean IsShowRetailPrice;
        @Expose
        @SerializedName("IsEditableTitle")
        private boolean IsEditableTitle;
        @Expose
        @SerializedName("IsEditablePrice")
        private boolean IsEditablePrice;
        @Expose
        @SerializedName("IsAddGroup")
        private boolean IsAddGroup;

        public boolean isIsShowBuyingPrice() {
            return IsShowBuyingPrice;
        }

        public void setIsShowBuyingPrice(boolean IsShowBuyingPrice) {
            this.IsShowBuyingPrice = IsShowBuyingPrice;
        }

        public boolean isIsShowRetailPrice() {
            return IsShowRetailPrice;
        }

        public void setIsShowRetailPrice(boolean IsShowRetailPrice) {
            this.IsShowRetailPrice = IsShowRetailPrice;
        }

        public boolean isIsEditableTitle() {
            return IsEditableTitle;
        }

        public void setIsEditableTitle(boolean IsEditableTitle) {
            this.IsEditableTitle = IsEditableTitle;
        }

        public boolean isIsEditablePrice() {
            return IsEditablePrice;
        }

        public void setIsEditablePrice(boolean IsEditablePrice) {
            this.IsEditablePrice = IsEditablePrice;
        }

        public boolean isIsAddGroup() {
            return IsAddGroup;
        }

        public void setIsAddGroup(boolean IsAddGroup) {
            this.IsAddGroup = IsAddGroup;
        }
    }

    public static class MenusBean implements Serializable{
        private static final long serialVersionUID = -4979923515576592095L;
        /**
         * ID : InStorage
         * Name : 入库
         * ImgUrl : http://img/instorage.jpg
         */
        @Expose
        @SerializedName("ID")
        private String ID="";
        @Expose
        @SerializedName("Name")
        private String Name="";
        @Expose
        @SerializedName("ImgUrl")
        private String ImgUrl="";

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getImgUrl() {
            return ImgUrl;
        }

        public void setImgUrl(String ImgUrl) {
            this.ImgUrl = ImgUrl;
        }
    }

    public static class NavBarBean implements Serializable {
        private static final long serialVersionUID = 4304239716988402959L;
        /**
         * ID : SaleBill
         * Name : 销售单
         * Remark : ¥ 18990
         */
        @Expose
        @SerializedName("ID")
        private String ID="";
        @Expose
        @SerializedName("Name")
        private String Name="";
        @Expose
        @SerializedName("Remark")
        private String Remark="";

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String Remark) {
            this.Remark = Remark;
        }
    }
}
