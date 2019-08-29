package com.yiku.kdb_flat.model.bean;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class ItemRemarkModel implements Serializable {

    private static final long serialVersionUID = -3718423961923385888L;
    
    @Expose
    private String Content;
	@Expose
	private String RecordTime;
	@Expose
	private String ID;
	@Expose
	private String UserName;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public String getRecordTime() {
		return RecordTime;
	}

	public void setRecordTime(String RecordTime) {
		RecordTime = RecordTime;
	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}
}
