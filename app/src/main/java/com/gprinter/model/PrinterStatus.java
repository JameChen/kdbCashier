/*    */ package com.gprinter.model;
/*    */ 
/*    */ public class PrinterStatus {
/*    */   private int status;
/*    */   
/*    */   public synchronized void setStatus(int status) {
/*  7 */     this.status = status;
/*    */   }
/*    */   
/*    */   public synchronized int getStatus() {
/* 11 */     return this.status;
/*    */   }
/*    */ }


/* Location:              C:\Users\jame\Desktop\gprintersdkv22.jar!\com\gprinter\model\PrinterStatus.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */