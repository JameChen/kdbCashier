package com.yiku.kdb_flat.model.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by jame on 2018/12/8.
 */

public class MemBean implements Serializable{

    /**
     * MemberID : 3
     * Phone : 13128637009
     * CardNo :
     * Name :
     * Level : 普通会员
     * Score : 0
     */

    @SerializedName("MemberID")
    private int MemberID;
    @SerializedName("Phone")
    private String Phone="";
    @SerializedName("CardNo")
    private String CardNo="";
    @SerializedName("Name")
    private String Name="";
    @SerializedName("Level")
    private String Level="";
    @SerializedName("Score")
    private int Score;

    public int getMemberID() {
        return MemberID;
    }

    public void setMemberID(int MemberID) {
        this.MemberID = MemberID;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getCardNo() {
        return CardNo;
    }

    public void setCardNo(String CardNo) {
        this.CardNo = CardNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String Level) {
        this.Level = Level;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int Score) {
        this.Score = Score;
    }
}
