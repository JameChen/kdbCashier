package com.yiku.kdb_flat.model.bean;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class ItemStockInfoModel implements Serializable {

    private static final long serialVersionUID = -3718423961923385888L;

	private static enum StockInfoStatusType {
		正常,排单,断货
	}

    @Expose
    private int StockDays;
	@Expose
	private int StockStatusID;
	@Expose
	private String StockDate;

	public int getStockDays() {
		return StockDays;
	}

	public void setStockDays(int stockDays) {
		StockDays = stockDays;
	}

	public int getStockStatusID() {
		return StockStatusID;
	}
	public String getStockStatusStr() {
		if (StockStatusID == 0) {
			return "正常";
		} else if (StockStatusID == 1) {
			return "排单";
		} else if (StockStatusID == 2) {
			return "断货";
		} else {
			return "正常";
		}
	}

	public void setStockStatusID(int stockStatusID) {
		StockStatusID = stockStatusID;
	}

	public void setStockStatusWithName(String stockStatusStr) {
		if (stockStatusStr.equals("正常"))
		{
			setStockStatusID(0);
		}
		else if (stockStatusStr.equals("排单"))
		{
			setStockStatusID(1);
		}
		else if (stockStatusStr.equals("断货"))
		{
			setStockStatusID(2);
		}
	}

	public String getStackDate() {
		return StockDate;
	}

	public String getStackDateFrendy() {
		if (StockDate.length()>0)
		{
			return "排单到 "+StockDate.substring(5,StockDate.length());
		}
		return StockDate;
	}

	public void setStackDate(String stackDate) {
		StockDate = stackDate;
	}
}
