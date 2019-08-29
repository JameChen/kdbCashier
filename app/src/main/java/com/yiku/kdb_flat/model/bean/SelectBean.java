package com.yiku.kdb_flat.model.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by jame on 2018/11/28.
 */

public class SelectBean implements Serializable {
    private static final long serialVersionUID = 2047353315146209223L;
    /**
     * ID : 339925
     * Name : 好韵来
     */
    @Expose
    @SerializedName("ID")
    private int ID;
    @Expose
    @SerializedName("Name")
    private String Name = "";
    public  boolean isShowAbove;
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
    private String start_time="";

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    private String end_time="";
}
