package com.yiku.kdb_flat.model.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jame on 2018/9/30.
 */

public class AuthBean implements Serializable {
    private static final long serialVersionUID = 1517439508979989668L;
    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    /**
     * GroupID : 0
     * GroupName :
     * AuthTypeList : [{"TypeID":1,"TypeName":"全站设置","IsSelect":false,"AuthorityList":[{"AuthorityID":1,"AuthorityName":"显示进货价","IsAuthorised":false},{"AuthorityID":2,"AuthorityName":"显示零售价","IsAuthorised":false}]},{"TypeID":2,"TypeName":"入库","IsSelect":false,"AuthorityList":[{"AuthorityID":3,"AuthorityName":"入库操作","IsAuthorised":false}]},{"TypeID":3,"TypeName":"销售","IsSelect":false,"AuthorityList":[{"AuthorityID":4,"AuthorityName":"查看销售记录","IsAuthorised":false},{"AuthorityID":5,"AuthorityName":"开单","IsAuthorised":false}]},{"TypeID":4,"TypeName":"库存","IsSelect":false,"AuthorityList":[{"AuthorityID":6,"AuthorityName":"编辑标题","IsAuthorised":false},{"AuthorityID":7,"AuthorityName":"编辑价格","IsAuthorised":false}]}]
     */
    @Expose
    @SerializedName("GroupID")
    private int GroupID;
    @Expose
    @SerializedName("GroupName")
    private String GroupName="";
    @Expose
    @SerializedName("AuthTypeList")
    private List<AuthTypeListBean> AuthTypeList;

    public int getGroupID() {
        return GroupID;
    }

    public void setGroupID(int GroupID) {
        this.GroupID = GroupID;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String GroupName) {
        this.GroupName = GroupName;
    }

    public List<AuthTypeListBean> getAuthTypeList() {
        return AuthTypeList;
    }

    public void setAuthTypeList(List<AuthTypeListBean> AuthTypeList) {
        this.AuthTypeList = AuthTypeList;
    }

    public static class AuthTypeListBean extends AbstractExpandableItem<AuthTypeListBean.AuthorityListBean> implements  Serializable,MultiItemEntity {
        private static final long serialVersionUID = 4697759020092607776L;
        /**
         * TypeID : 1
         * TypeName : 全站设置
         * IsSelect : false
         * AuthorityList : [{"AuthorityID":1,"AuthorityName":"显示进货价","IsAuthorised":false},{"AuthorityID":2,"AuthorityName":"显示零售价","IsAuthorised":false}]
         */
        @Expose
        @SerializedName("IsButton")
        private boolean isShow=true;

        public boolean isShow() {
            return isShow;
        }

        public void setShow(boolean show) {
            isShow = show;
        }

        @Expose
        @SerializedName("TypeID")
        private int TypeID;
        @Expose
        @SerializedName("TypeName")
        private String TypeName="";
        @Expose
        @SerializedName("IsSelect")
        private boolean IsSelect;
        @Expose
        @SerializedName("AuthorityList")
        private List<AuthorityListBean> AuthorityList;

        public int getTypeID() {
            return TypeID;
        }

        public void setTypeID(int TypeID) {
            this.TypeID = TypeID;
        }

        public String getTypeName() {
            return TypeName;
        }

        public void setTypeName(String TypeName) {
            this.TypeName = TypeName;
        }

        public boolean isIsSelect() {
            return IsSelect;
        }

        public void setIsSelect(boolean IsSelect) {
            this.IsSelect = IsSelect;
        }

        public List<AuthorityListBean> getAuthorityList() {
            return AuthorityList;
        }

        public void setAuthorityList(List<AuthorityListBean> AuthorityList) {
            this.AuthorityList = AuthorityList;
        }

        @Override
        public int getLevel() {
            return 0;
        }

        @Override
        public int getItemType() {
            return TYPE_LEVEL_0;
        }

        public static class AuthorityListBean implements MultiItemEntity,Serializable {
            private static final long serialVersionUID = -3769437503662993423L;
            /**
             * AuthorityID : 1
             * AuthorityName : 显示进货价
             * IsAuthorised : false
             */
            @Expose
            @SerializedName("AuthorityID")
            private int AuthorityID;
            @Expose
            @SerializedName("AuthorityName")
            private String AuthorityName="";
            @Expose
            @SerializedName("IsAuthorised")
            private boolean IsAuthorised;
            private int parentId;

            public int getParentId() {
                return parentId;
            }

            public void setParentId(int parentId) {
                this.parentId = parentId;
            }

            public int getAuthorityID() {
                return AuthorityID;
            }

            public void setAuthorityID(int AuthorityID) {
                this.AuthorityID = AuthorityID;
            }

            public String getAuthorityName() {
                return AuthorityName;
            }

            public void setAuthorityName(String AuthorityName) {
                this.AuthorityName = AuthorityName;
            }

            public boolean isIsAuthorised() {
                return IsAuthorised;
            }

            public void setIsAuthorised(boolean IsAuthorised) {
                this.IsAuthorised = IsAuthorised;
            }

            @Override
            public int getItemType() {
                return TYPE_LEVEL_1;
            }
        }
    }
}
