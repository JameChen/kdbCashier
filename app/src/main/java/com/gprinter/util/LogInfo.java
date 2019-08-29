/*    */ package com.gprinter.util;
/*    */ 
/*    */ import android.content.Context;
import android.util.Log;

import com.gprinter.model.LogType;

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
/*    */ public class LogInfo
/*    */ {
/*    */   private static final String tag = "smartprinter";
/* 19 */   private static Context mContext = null;
/*    */   
/*    */   public static void setContext(Context context) {
/* 22 */     mContext = context;
/* 23 */     out("set LogInfo mContext!");
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static void err(LogType logType, String mes)
/*    */   {
/* 32 */     if (("smartprinter" != null) && (mes != null)) {
/* 33 */       Log.e("smartprinter", mes);
/* 34 */       if (mContext != null)
/*    */       {
///* 36 */         DbUtils db = DBUtil.getDB(mContext);
///* 37 */         if (db != null) {
///* 38 */           LogModel logModel = new LogModel();
///* 39 */           logModel.setLogTime(new Date());
///* 40 */           logModel.setLogType(logType.toInt());
///* 41 */           logModel.setLogMsg(mes);
///*    */           try {
///* 43 */             db.save(logModel);
///*    */           } catch (DbException e) {
///* 45 */             e.printStackTrace();
///* 46 */             if (e.getCause() != null) {
///* 47 */               Log.e("smartprinter", e.getCause().getMessage());
///*    */             } else {
///* 49 */               Log.e("smartprinter", e.getMessage());
///*    */             }
///*    */           }
/* 52 */          // logModel = null;
/*    */        // }
/* 54 */        // db = null;
/*    */       } else {
/* 56 */         out("LogInfo mContext is null!");
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public static void out(String mes)
/*    */   {
/* 66 */     if ("smartprinter" != null) {}
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static void debug(String mes)
/*    */   {
/* 76 */     if ("smartprinter" != null) {}
/*    */   }
/*    */ }
