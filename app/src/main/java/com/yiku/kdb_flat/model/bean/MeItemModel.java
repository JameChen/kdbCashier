package com.yiku.kdb_flat.model.bean;

/**
 * Created by 诚 on 2015/9/21.
 */
public class MeItemModel {
    public int getCount_unread() {
        return count_unread;
    }

    public void setCount_unread(int count_unread) {
        this.count_unread = count_unread;
    }

    int count_unread=0;


    public int getSourceId() {
        return sourceId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int type;

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    private int sourceId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
}
