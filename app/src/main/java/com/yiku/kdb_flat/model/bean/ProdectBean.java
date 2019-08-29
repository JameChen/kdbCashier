package com.yiku.kdb_flat.model.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jame on 2018/11/22.
 */

public class ProdectBean implements Serializable{
    private static final long serialVersionUID = 998034422518668028L;
    @Expose
    @SerializedName("Products")
    List<ShopCartModel> Products;
    @Expose
    @SerializedName("SellerUsers")
    private List<SellerUsersBean> SellerUsers;

    public List<ShopCartModel> getProducts() {
        return Products;
    }

    public void setProducts(ArrayList<ShopCartModel> products) {
        Products = products;
    }

    public List<SellerUsersBean> getSellerUsers() {
        return SellerUsers;
    }

    public void setSellerUsers(List<SellerUsersBean> SellerUsers) {
        this.SellerUsers = SellerUsers;
    }

    public static class SellerUsersBean implements Serializable {
        private static final long serialVersionUID = 9009432548765167266L;
        /**
         * ID : 339925.0
         * Name : 好韵来
         */
        public boolean isCheck=false;
        @Expose
        @SerializedName("ID")
        private int ID;
        @Expose
        @SerializedName("Name")
        private String Name="";

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }
    }
}
