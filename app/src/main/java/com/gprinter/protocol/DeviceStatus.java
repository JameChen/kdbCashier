/*    */ package com.gprinter.protocol;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum DeviceStatus
/*    */ {
/* 12 */   NORMAL(
/*    */   
/*    */ 
/* 15 */     1), 
/* 16 */   LACK_PAGER(
/*    */   
/*    */ 
/* 19 */     2), 
/* 20 */   ERROR(
/*    */   
/*    */ 
/* 23 */     3), 
/* 24 */   NO_PRINTER(
/*    */   
/*    */ 
/* 27 */     4), 
/* 28 */   COVER_OPEN(
/*    */   
/*    */ 
/* 31 */     5);
/*    */   
/*    */   private int status;
/*    */   
/*    */   private DeviceStatus(int _status) {
/* 36 */     this.status = _status;
/*    */   }
/*    */   
/*    */   public static DeviceStatus getDeviceStatus(int _status) {
/* 40 */     switch (_status) {
/*    */     case 1: 
/* 42 */       return NORMAL;
/*    */     case 2: 
/* 44 */       return LACK_PAGER;
/*    */     case 3: 
/* 46 */       return ERROR;
/*    */     case 4: 
/* 48 */       return NO_PRINTER;
/*    */     case 5: 
/* 50 */       return COVER_OPEN;
/*    */     }
/* 52 */     return ERROR;
/*    */   }
/*    */   
/*    */   public int toInt()
/*    */   {
/* 57 */     return this.status;
/*    */   }
/*    */ }


/* Location:              C:\Users\jame\Desktop\gprintersdkv22.jar!\com\gprinter\protocol\DeviceStatus.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */