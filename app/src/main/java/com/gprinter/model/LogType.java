/*    */ package com.gprinter.model;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum LogType
/*    */ {
/* 10 */   NO_KNOW(
/*    */   
/*    */ 
/* 13 */     0), 
/* 14 */   NORMAL(
/*    */   
/*    */ 
/* 17 */     1), 
/* 18 */   CONNECT_SERVER_ERR(
/*    */   
/*    */ 
/* 21 */     2), 
/* 22 */   CONNECT_PRINTER_ERR(
/*    */   
/*    */ 
/* 25 */     3), 
/* 26 */   DATA_ERR(
/*    */   
/*    */ 
/* 29 */     4), 
/* 30 */   PRINT_ERR(
/*    */   
/*    */ 
/* 33 */     5), 
/* 34 */   APP_ERR(
/*    */   
/*    */ 
/* 37 */     6);
/*    */   
/*    */   private int type;
/*    */   
/*    */   private LogType(int _type) {
/* 42 */     this.type = _type;
/*    */   }
/*    */   
/*    */   public int toInt() {
/* 46 */     return this.type;
/*    */   }
/*    */ }


/* Location:              C:\Users\jame\Desktop\gprintersdkv22.jar!\com\gprinter\model\LogType.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */