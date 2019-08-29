package com.yiku.kdb_flat.model.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jame on 2018/9/29.
 */

public class MemberBean extends AbstractExpandableItem<MemberBean.MemberListBean> implements Serializable, MultiItemEntity {
    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    private static final long serialVersionUID = -2466993289178694202L;
    /**
     * Code :
     * Result : true
     * Message :
     * Data : [{"GroupID":1,"GroupName":"管理员","IsAddMember":true,"IsDelGroup":false,"IsEditGroup":false,"MemberList":[{"MemberID":1,"MemberUserID":129766,"MemberName":"lilifeiyang","IsDelMember":false,"IsEditMember":false}]},{"GroupID":2,"GroupName":"收银员","IsAddMember":true,"IsDelGroup":false,"IsEditGroup":false,"MemberList":[{"MemberID":2,"MemberUserID":339925,"MemberName":"好韵来","IsDelMember":false,"IsEditMember":false}]}]
     */
    /**
     * GroupID : 1
     * GroupName : 管理员
     * IsAddMember : true
     * IsDelGroup : false
     * IsEditGroup : false
     * MemberList : [{"MemberID":1,"MemberUserID":129766,"MemberName":"lilifeiyang","IsDelMember":false,"IsEditMember":false}]
     */


    @Expose
    @SerializedName("GroupID")
    private int GroupID;
    @Expose
    @SerializedName("GroupName")
    private String GroupName = "";
    @Expose
    @SerializedName("IsAddMember")
    private boolean IsAddMember;
    @Expose
    @SerializedName("IsDelGroup")
    private boolean IsDelGroup;
    @Expose
    @SerializedName("IsEditGroup")
    private boolean IsEditGroup;
    @Expose
    @SerializedName("MemberList")
    private List<MemberListBean> MemberList;

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

    public boolean isIsAddMember() {
        return IsAddMember;
    }

    public void setIsAddMember(boolean IsAddMember) {
        this.IsAddMember = IsAddMember;
    }

    public boolean isIsDelGroup() {
        return IsDelGroup;
    }

    public void setIsDelGroup(boolean IsDelGroup) {
        this.IsDelGroup = IsDelGroup;
    }

    public boolean isIsEditGroup() {
        return IsEditGroup;
    }

    public void setIsEditGroup(boolean IsEditGroup) {
        this.IsEditGroup = IsEditGroup;
    }

    public List<MemberListBean> getMemberList() {
        return MemberList;
    }

    public void setMemberList(List<MemberListBean> MemberList) {
        this.MemberList = MemberList;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return TYPE_LEVEL_0;
    }

    @Override
    public List<MemberListBean> getSubItems() {
        return MemberList;
    }

    public static class MemberListBean implements Serializable, MultiItemEntity {
        private static final long serialVersionUID = -4442051462136222936L;
        /**
         * MemberID : 1
         * MemberUserID : 129766
         * MemberName : lilifeiyang
         * IsDelMember : false
         * IsEditMember : false
         */
        private int GroupID;

        public int getGroupID() {
            return GroupID;
        }

        public void setGroupID(int groupID) {
            GroupID = groupID;
        }

        private String GroupName = "";

        public String getGroupName() {
            return GroupName;
        }

        public void setGroupName(String groupName) {
            GroupName = groupName;
        }

        @Expose
        @SerializedName("Alias")
        private String Alias="";

        public String getAlias() {
            return Alias;
        }

        public void setAlias(String alias) {
            Alias = alias;
        }

        @Expose
        @SerializedName("MemberID")
        private int MemberID;
        @Expose
        @SerializedName("MemberUserID")
        private int MemberUserID;
        @Expose
        @SerializedName("MemberName")
        private String MemberName = "";
        @Expose
        @SerializedName("IsDelMember")
        private boolean IsDelMember;
        @Expose
        @SerializedName("IsEditMember")
        private boolean IsEditMember;

        public int getMemberID() {
            return MemberID;
        }

        public void setMemberID(int MemberID) {
            this.MemberID = MemberID;
        }

        public int getMemberUserID() {
            return MemberUserID;
        }

        public void setMemberUserID(int MemberUserID) {
            this.MemberUserID = MemberUserID;
        }

        public String getMemberName() {
            return MemberName;
        }

        public void setMemberName(String MemberName) {
            this.MemberName = MemberName;
        }

        public boolean isIsDelMember() {
            return IsDelMember;
        }

        public void setIsDelMember(boolean IsDelMember) {
            this.IsDelMember = IsDelMember;
        }

        public boolean isIsEditMember() {
            return IsEditMember;
        }

        public void setIsEditMember(boolean IsEditMember) {
            this.IsEditMember = IsEditMember;
        }

        @Override
        public int getItemType() {
            return TYPE_LEVEL_1;
        }
    }
}
