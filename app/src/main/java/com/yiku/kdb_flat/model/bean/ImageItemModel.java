package com.yiku.kdb_flat.model.bean;

/**
 * Created by Administrator on 2017/5/17 0017.
 */
public class ImageItemModel {
    private int resId;
    private String text;

    public ImageItemModel(int resId, String text) {
        this.resId = resId;
        this.text = text;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
