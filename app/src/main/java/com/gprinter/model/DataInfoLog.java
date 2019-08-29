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
/*    */ public class DataInfoLog
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
/* 47 */   public void setId(int id) { this.id = id; }
/*    */   
/*    */   public Date getDateTime() {
/* 50 */     return this.dateTime;
/*    */   }
/*    */   
/* 53 */   public void setDateTime(Date dateTime) { this.dateTime = dateTime; }
/*    */   
/*    */   public double getProcessCpuRate() {
/* 56 */     return this.processCpuRate;
/*    */   }
/*    */   
/* 59 */   public void setProcessCpuRate(double processCpuRate) { this.processCpuRate = processCpuRate; }
/*    */   
/*    */   public int getAppMem() {
/* 62 */     return this.appMem;
/*    */   }
/*    */   
/* 65 */   public void setAppMem(int appMem) { this.appMem = appMem; }
/*    */   
/*    */   public int getSystemAvailableMem() {
/* 68 */     return this.systemAvailableMem;
/*    */   }
/*    */   
/* 71 */   public void setSystemAvailableMem(int systemAvailableMem) { this.systemAvailableMem = systemAvailableMem; }
/*    */   
/*    */   public int getMemRate() {
/* 74 */     return this.memRate;
/*    */   }
/*    */   
/* 77 */   public void setMemRate(int memRate) { this.memRate = memRate; }
/*    */   
/*    */   public int getStatus() {
/* 80 */     return this.status;
/*    */   }
/*    */   
/* 83 */   public void setStatus(int status) { this.status = status; }
/*    */ }


/* Location:              C:\Users\jame\Desktop\gprintersdkv22.jar!\com\gprinter\model\DataInfoLog.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */