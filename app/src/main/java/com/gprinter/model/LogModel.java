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
/*    */ public class LogModel
/*    */ {
/*    */   private int id;
/*    */   private Date logTime;
/* 31 */   private int logType = 0;
/*    */   
/*    */ 
/*    */   private String logMsg;
/*    */   
/*    */ 
/*    */   public int getId()
/*    */   {
/* 39 */     return this.id;
/*    */   }
/*    */   
/*    */   public void setId(int id) {
/* 43 */     this.id = id;
/*    */   }
/*    */   
/*    */   public Date getLogTime() {
/* 47 */     return this.logTime;
/*    */   }
/*    */   
/*    */   public void setLogTime(Date logTime) {
/* 51 */     this.logTime = logTime;
/*    */   }
/*    */   
/*    */   public int getLogType() {
/* 55 */     return this.logType;
/*    */   }
/*    */   
/*    */   public void setLogType(int logType) {
/* 59 */     this.logType = logType;
/*    */   }
/*    */   
/*    */   public String getLogMsg() {
/* 63 */     return this.logMsg;
/*    */   }
/*    */   
/*    */   public void setLogMsg(String logMsg) {
/* 67 */     this.logMsg = logMsg;
/*    */   }
/*    */ }


/* Location:              C:\Users\jame\Desktop\gprintersdkv22.jar!\com\gprinter\model\LogModel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */