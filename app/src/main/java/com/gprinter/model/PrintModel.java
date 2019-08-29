/*     */ package com.gprinter.model;
/*     */ 
/*     */ import java.util.Date;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PrintModel
/*     */ {
/*     */   private int id;
/*     */   private Date printTime;
/*     */   private int deviceStatus;
/*  33 */   private int printResult = -1;
/*     */   
/*     */ 
/*     */ 
/*  37 */   private String printResultMsg = "未打印";
/*     */   
/*     */ 
/*     */ 
/*     */   private int eventNum;
/*     */   
/*     */ 
/*     */ 
/*     */   private int partNum;
/*     */   
/*     */ 
/*     */ 
/*     */   private int partIndex;
/*     */   
/*     */ 
/*     */ 
/*     */   private long orderId;
/*     */   
/*     */ 
/*     */   private int controller;
/*     */   
/*     */ 
/*     */   private String printMsg;
/*     */   
/*     */ 
/*     */ 
/*     */   public int getId()
/*     */   {
/*  65 */     return this.id;
/*     */   }
/*     */   
/*  68 */   public void setId(int id) { this.id = id; }
/*     */   
/*     */   public Date getPrintTime() {
/*  71 */     return this.printTime;
/*     */   }
/*     */   
/*  74 */   public void setPrintTime(Date printTime) { this.printTime = printTime; }
/*     */   
/*     */   public int getDeviceStatus() {
/*  77 */     return this.deviceStatus;
/*     */   }
/*     */   
/*  80 */   public void setDeviceStatus(int deviceStatus) { this.deviceStatus = deviceStatus; }
/*     */   
/*     */   public int getPrintResult() {
/*  83 */     return this.printResult;
/*     */   }
/*     */   
/*  86 */   public void setPrintResult(int printResult) { this.printResult = printResult; }
/*     */   
/*     */   public long getOrderId() {
/*  89 */     return this.orderId;
/*     */   }
/*     */   
/*  92 */   public void setOrderId(long orderId) { this.orderId = orderId; }
/*     */   
/*     */   public int getController() {
/*  95 */     return this.controller;
/*     */   }
/*     */   
/*  98 */   public void setController(int controller) { this.controller = controller; }
/*     */   
/*     */   public String getPrintMsg() {
/* 101 */     return this.printMsg;
/*     */   }
/*     */   
/* 104 */   public void setPrintMsg(String printMsg) { this.printMsg = printMsg; }
/*     */   
/*     */   public String getPrintResultMsg() {
/* 107 */     return this.printResultMsg;
/*     */   }
/*     */   
/* 110 */   public void setPrintResultMsg(String printResultMsg) { this.printResultMsg = printResultMsg; }
/*     */   
/*     */   public int getEventNum() {
/* 113 */     return this.eventNum;
/*     */   }
/*     */   
/* 116 */   public void setEventNum(int eventNum) { this.eventNum = eventNum; }
/*     */   
/*     */   public int getPartNum() {
/* 119 */     return this.partNum;
/*     */   }
/*     */   
/* 122 */   public void setPartNum(int partNum) { this.partNum = partNum; }
/*     */   
/*     */   public int getPartIndex() {
/* 125 */     return this.partIndex;
/*     */   }
/*     */   
/* 128 */   public void setPartIndex(int partIndex) { this.partIndex = partIndex; }
/*     */ }


/* Location:              C:\Users\jame\Desktop\gprintersdkv22.jar!\com\gprinter\model\PrintModel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */