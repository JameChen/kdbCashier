/*     */ package com.gprinter.io;
/*     */ 
/*     */

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.gprinter.command.GpCom;

import java.util.Vector;

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class GpPort
/*     */ {
/*     */   private static final String DEBUG_TAG = "GpPort";
/*     */   protected boolean mClosePort;
/*     */   protected int mState;
/*  18 */   protected Handler mHandler = null;
/*     */   
/*     */   protected int mmBytesAvailable;
/*     */   
/*     */   protected int mPrinterId;
/*     */   
/*     */ 
/*     */   abstract void connect();
/*     */   
/*     */ 
/*     */   abstract void stop();
/*     */   
/*     */ 
/*     */   abstract GpCom.ERROR_CODE writeDataImmediately(Vector<Byte> paramVector);
/*     */   
/*     */   protected synchronized void setState(int state)
/*     */   {
/*  35 */     if (this.mState != state) {
/*  36 */       Log.d("GpPort", "setState() " + this.mState + " -> " + state);
/*  37 */       Log.d("GpPort", "PrinterId() " + this.mPrinterId + " -> " + this.mPrinterId);
/*  38 */       this.mState = state;
/*  39 */       Message msg = this.mHandler.obtainMessage(1);
/*  40 */       Bundle bundle = new Bundle();
/*  41 */       bundle.putInt("printer.id", this.mPrinterId);
/*  42 */       bundle.putInt("device_status", state);
/*  43 */       msg.setData(bundle);
/*  44 */       this.mHandler.sendMessage(msg);
/*     */     } else {
/*  46 */       Log.d("GpPort", "STATE NOT CHANGE");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected int getState()
/*     */   {
/*  54 */     return this.mState;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void connectionFailed()
/*     */   {
/*  61 */     setState(0);
/*     */   }
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
/*     */   protected void closePortFailed()
/*     */   {
/*  75 */     setState(0);
/*  76 */     Log.d("GpPort", "closePortFailed ");
/*     */   }
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
/*     */   protected void connectionLost()
/*     */   {
/*  90 */     setState(0);
/*  91 */     Log.d("GpPort", "connectionLost ");
/*     */   }
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
/*     */   protected void invalidPrinter()
/*     */   {
/* 105 */     setState(0);
/*     */     
/* 107 */     Message msg = this.mHandler.obtainMessage(5);
/* 108 */     Bundle bundle = new Bundle();
/* 109 */     bundle.putInt("printer.id", this.mPrinterId);
/* 110 */     bundle.putString("toast", "Please use Gprinter");
/* 111 */     msg.setData(bundle);
/* 112 */     this.mHandler.sendMessage(msg);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void connectionToPrinterFailed()
/*     */   {
/* 119 */     setState(0);
/* 120 */     Log.d("GpPort", "Close port failed ");
/*     */   }
/*     */ }


/* Location:              C:\Users\jame\Desktop\gprintersdkv22.jar!\com\gprinter\io\GpPort.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */