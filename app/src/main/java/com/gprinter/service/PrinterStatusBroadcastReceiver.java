/*    */ package com.gprinter.service;
/*    */ 
/*    */ import android.content.BroadcastReceiver;
/*    */ import android.content.Context;
/*    */ import android.content.Intent;
/*    */ import com.gprinter.util.LogInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PrinterStatusBroadcastReceiver
/*    */   extends BroadcastReceiver
/*    */ {
/*    */   public void onReceive(Context context, Intent intent)
/*    */   {
/* 20 */     if ("action.connect.status".equals(intent.getAction())) {
/* 21 */       int type = intent.getIntExtra("connect.status", 0);
/* 22 */       int id = intent.getIntExtra("printer.id", 0);
/*    */       
/* 24 */       LogInfo.out("PRINTER_ID:" + id);
/* 25 */       LogInfo.out("CONNECT_STATUS:" + type);
/*    */       
/* 27 */       if (type == 0)
/*    */       {
/* 29 */         LogInfo.out("打印机-连接断开");
/* 30 */       } else if (type == 1)
/*    */       {
/* 32 */         LogInfo.out("打印机-监听状态");
/* 33 */       } else if (type == 2)
/*    */       {
/* 35 */         LogInfo.out("打印机-正在连接");
/* 36 */       } else if (type == 3)
/*    */       {
/* 38 */         LogInfo.out("打印机-已连接");
/* 39 */       } else if (type == 4)
/*    */       {
/* 41 */         LogInfo.out("打印机-无效的打印机");
/* 42 */       } else if (type == 5)
/*    */       {
/* 44 */         LogInfo.out("打印机-有效的打印机");
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\jame\Desktop\gprintersdkv22.jar!\com\gprinter\service\PrinterStatusBroadcastReceiver.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */