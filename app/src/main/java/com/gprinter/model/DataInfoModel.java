/*    */ package com.gprinter.model;
/*    */ 
/*    */ import java.util.Date;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DataInfoModel
/*    */ {
/*    */   private int id;
/*    */   private Date dateTime;
/*    */   private double processCpuRate;
/*    */   private int appMem;
/*    */   private int systemAvailableMem;
/*    */   private int memRate;
/*    */   private int status;
/*    */   
/*    */   public int getId()
/*    */   {
/* 44 */     return this.id;
/*    */   }
/*    */   
/*    */   public void setId(int id) {
/* 48 */     this.id = id;
/*    */   }
/*    */   
/*    */   public Date getDateTime() {
/* 52 */     return this.dateTime;
/*    */   }
/*    */   
/*    */   public void setDateTime(Date dateTime) {
/* 56 */     this.dateTime = dateTime;
/*    */   }
/*    */   
/*    */   public double getProcessCpuRate() {
/* 60 */     return this.processCpuRate;
/*    */   }
/*    */   
/*    */   public void setProcessCpuRate(double processCpuRate) {
/* 64 */     this.processCpuRate = processCpuRate;
/*    */   }
/*    */   
/*    */   public int getAppMem() {
/* 68 */     return this.appMem;
/*    */   }
/*    */   
/*    */   public void setAppMem(int appMem) {
/* 72 */     this.appMem = appMem;
/*    */   }
/*    */   
/*    */   public int getSystemAvailableMem() {
/* 76 */     return this.systemAvailableMem;
/*    */   }
/*    */   
/*    */   public void setSystemAvailableMem(int systemAvailableMem) {
/* 80 */     this.systemAvailableMem = systemAvailableMem;
/*    */   }
/*    */   
/*    */   public int getMemRate() {
/* 84 */     return this.memRate;
/*    */   }
/*    */   
/*    */   public void setMemRate(int memRate) {
/* 88 */     this.memRate = memRate;
/*    */   }
/*    */   
/*    */   public int getStatus() {
/* 92 */     return this.status;
/*    */   }
/*    */   
/*    */   public void setStatus(int status) {
/* 96 */     this.status = status;
/*    */   }
/*    */ }


/* Location:              C:\Users\jame\Desktop\gprintersdkv22.jar!\com\gprinter\model\DataInfoModel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */