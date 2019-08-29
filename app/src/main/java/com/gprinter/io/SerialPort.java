/*     */ package com.gprinter.io;
/*     */ 
/*     */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SerialPort
/*     */ {
/*     */   private static final String TAG = "SPort";
/*     */   protected FileDescriptor mFd;
/*     */   private FileInputStream mFileInputStream;
/*     */   private Context mContext;
/*     */   
/*     */   protected SerialPort(Context context)
/*     */   {
/*  45 */     this.mContext = context;
/*     */   }
/*     */   
/*     */   protected void openSerialPort(File device, int baudrate, int flags) throws IOException
/*     */   {
/*  50 */     if ((!device.canRead()) || (!device.canWrite()))
/*     */     {
/*     */       try
/*     */       {
/*  54 */         Process su = Runtime.getRuntime().exec("/system/xbin/su");
/*  55 */         String cmd = "chmod 666 " + device.getAbsolutePath() + "\n" + 
/*  56 */           "exit\n";
/*  57 */         su.getOutputStream().write(cmd.getBytes());
/*  58 */         if ((su.waitFor() != 0) || (!device.canRead()) || 
/*  59 */           (!device.canWrite()))
/*     */         {
/*  61 */           AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
/*  62 */           builder.setMessage("没有权限");
/*  63 */           builder.setPositiveButton("关闭", new OnClickListener()
/*     */           {
/*     */             public void onClick(DialogInterface dialog, int which) {
/*  66 */               ((Activity)SerialPort.this.mContext).finish();
/*     */             }
/*  68 */           });
/*  69 */           builder.show();
/*     */         }
/*     */       }
/*     */       catch (InterruptedException e) {
/*  73 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */     
/*  77 */     this.mFd = open(device.getAbsolutePath(), baudrate, flags);
/*  78 */     if (this.mFd == null) {
/*  79 */       Log.e("SPort", "native open returns null");
/*  80 */       throw new IOException();
/*     */     }
/*     */     
/*  83 */     this.mFileInputStream = new FileInputStream(this.mFd);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected InputStream getInputStream()
/*     */   {
/*  92 */     return this.mFileInputStream;
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
/*     */   static
/*     */   {
/* 118 */     System.loadLibrary("gpequipment");
/*     */   }
/*     */   
/*     */   private static native FileDescriptor open(String paramString, int paramInt1, int paramInt2);
/*     */   
/*     */   protected native void close();
/*     */   
/*     */   protected native void is();
/*     */   
/*     */   protected native boolean check(byte[] paramArrayOfByte, int paramInt);
/*     */   
/*     */   protected native void update();
/*     */   
/*     */   protected native void updateCheck(byte[] paramArrayOfByte, int paramInt);
/*     */   
/*     */   protected native void requestVersionInfo();
/*     */   
/*     */   protected native void requestUpdate(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */   
/*     */   protected native void download(int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfByte, int paramInt5);
/*     */ }


/* Location:              C:\Users\jame\Desktop\gprintersdkv22.jar!\com\gprinter\io\SerialPort.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */