///*     */ package com.gprinter.io;
///*     */
///*     */
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.os.Build;
//import android.widget.Toast;
//
//import com.gprinter.command.GpUtils;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//
///*     */
///*     */
///*     */
///*     */
///*     */
///*     */
///*     */
///*     */
///*     */
///*     */
///*     */
///*     */
///*     */
///*     */ public class CustomerDisplay
///*     */   extends GpEquipmentPort
///*     */ {
///*  20 */   private Jni mJni = Jni.getInstance();
///*     */   private Context mContext;
///*     */   private static CustomerDisplay mCustomerDisplay;
///*     */
///*     */   private CustomerDisplay(Context context) {
///*  25 */     super(context);
///*  26 */     this.mContext = context;
///*     */   }
///*     */
///*     */
///*     */
///*     */
///*     */
///*     */   public static CustomerDisplay getInstance(Context context)
///*     */   {
///*  35 */     if (mCustomerDisplay == null) {
///*  36 */       mCustomerDisplay = new CustomerDisplay(context);
///*     */     }
///*  38 */     return mCustomerDisplay;
///*     */   }
///*     */
///*     */
///*     */   public void openPort()
///*     */     throws IOException
///*     */   {
///*     */     String path;
///*  47 */     if (Build.VERSION.SDK_INT >= 21) {
///*  48 */       path = "/dev/ttyS2";
///*     */     } else {
///*  50 */       path = "/dev/ttyS3";
///*     */     }
///*  52 */     File file = new File(path);
///*  53 */     if (!file.exists()) {
///*  54 */       throw new IOException("Not found serial port");
///*     */     }
///*  56 */     getSerialPort(file, 115200, 0);
///*     */   }
///*     */
///*     */
///*     */
///*     */
///*     */
///*     */
///*     */   public boolean isPortOpen()
///*     */   {
///*  66 */     return this.mJni.isPortOpen();
///*     */   }
///*     */
///*     */
///*     */
///*     */
///*     */
///*     */   public void setReceivedListener(OnDataReceived onDataReceived)
///*     */   {
///*  75 */     setDataReceived(onDataReceived);
///*     */   }
///*     */
///*     */
///*     */
///*     */   public void clear()
///*     */   {
///*  82 */     this.mJni.clear();
///*     */   }
///*     */
///*     */
///*     */
///*     */   public void reset()
///*     */   {
///*  89 */     this.mJni.reset();
///*  90 */     closeSerialPort();
///*     */   }
///*     */
///*     */
///*     */
///*     */
///*     */
///*     */
///*     */   public void setBacklight(boolean isOn)
///*     */   {
///* 100 */     this.mJni.setBacklight(isOn);
///*     */   }
///*     */
///*     */   public void setCursorPosition(int x, int y) {
///* 104 */     this.mJni.setCursorPosition(x, y);
///*     */   }
///*     */
///*     */
///*     */
///*     */
///*     */
///*     */
///*     */   public void setBacklightTimeout(int timeout)
///*     */   {
///* 114 */     this.mJni.setBacklightTimeout(timeout);
///*     */   }
///*     */
///*     */
///*     */
///*     */
///*     */
///*     */   public void setTextCurrentCursor(String text)
///*     */   {
///*     */     try
///*     */     {
///* 125 */       byte[] bytes = text.getBytes("GB2312");
///* 126 */       if ((bytes.length > 255) && (bytes.length <= 0)) {
///* 127 */         throw new IllegalArgumentException("the content must be greater than 0 and less than 255");
///*     */       }
///* 129 */       this.mJni.setInputInCurrentCursor(bytes, bytes.length);
///*     */     } catch (UnsupportedEncodingException e) {
///* 131 */       e.printStackTrace();
///*     */     }
///*     */   }
///*     */
///*     */
///*     */
///*     */
///*     */
///*     */   public void setTextBebindCursor(String text)
///*     */   {
///*     */     try
///*     */     {
///* 143 */       byte[] bytes = text.getBytes("GB2312");
///* 144 */       if ((bytes.length > 255) && (bytes.length <= 0)) {
///* 145 */         throw new IllegalArgumentException("the content must be greater than 0 and less than 255");
///*     */       }
///* 147 */       this.mJni.setInputBebindCursor(bytes, bytes.length);
///*     */     } catch (UnsupportedEncodingException e) {
///* 149 */       e.printStackTrace();
///*     */     }
///*     */   }
///*     */
///*     */
///*     */
///*     */   public void getBacklight()
///*     */   {
///* 157 */     this.mJni.getBacklight();
///*     */   }
///*     */
///*     */
///*     */
///*     */   public void getBacklightTimeout()
///*     */   {
///* 164 */     this.mJni.getBacklightTimeout();
///*     */   }
///*     */
///*     */
///*     */
///*     */   public void getCursorPosition()
///*     */   {
///* 171 */     this.mJni.getCursorPosition();
///*     */   }
///*     */
///*     */
///*     */
///*     */   public void getDisplayRowAndColumn()
///*     */   {
///* 178 */     this.mJni.getDisplayRowAndColumn();
///*     */   }
///*     */
///*     */
///*     */
///*     */
///*     */
///*     */   public void swichMode(byte mode)
///*     */   {
///* 187 */     this.mJni.swichMode(mode);
///*     */   }
///*     */
///*     */
///*     */
///*     */
///*     */
///*     */
///*     */
///*     */
///*     */   public void displayBitmap(Bitmap bitmap, int nWidth)
///*     */   {
///* 199 */     if (bitmap != null) {
///* 200 */       int width = (nWidth + 7) / 8 * 8;
///* 201 */       int height = bitmap.getHeight() * width / bitmap.getWidth();
///* 202 */       Bitmap grayBitmap = GpUtils.toGrayscale(bitmap);
///* 203 */       Bitmap rszBitmap = GpUtils.resizeImage(grayBitmap, width, height);
///* 204 */       byte[] src = GpUtils.bitmapToBWPix(rszBitmap);
///* 205 */       byte[] command = new byte[4];
///* 206 */       height = src.length / width;
///* 207 */       command[0] = ((byte)(width / 8 % 256));
///* 208 */       command[1] = ((byte)(width / 8 / 256));
///* 209 */       command[2] = ((byte)(height % 256));
///* 210 */       command[3] = ((byte)(height / 256));
///*     */
///* 212 */       byte[] codecontent = GpUtils.pixToEscRastBitImageCmd(src);
///* 213 */       byte[] bytes = new byte[command.length + codecontent.length];
///* 214 */       System.arraycopy(command, 0, bytes, 0, command.length);
///* 215 */       System.arraycopy(codecontent, 0, bytes, command.length, codecontent.length);
///* 216 */       this.mJni.displayBitmap(bytes, bytes.length);
///*     */     }
///*     */   }
///*     */
///*     */
///*     */
///*     */
///*     */
///*     */
///*     */   public void setContrast(byte contrast)
///*     */   {
///* 227 */     if ((contrast < 0) || (contrast > 21)) {
///* 228 */       Toast.makeText(this.mContext, "contrast param error", 0).show();
///* 229 */       return;
///*     */     }
///* 231 */     this.mJni.setContrast(contrast);
///*     */   }
///*     */
///*     */
///*     */
///*     */
///*     */
///*     */
///*     */   public void setBrightness(byte brightness)
///*     */   {
///* 241 */     if ((brightness < 0) || (brightness > 5)) {
///* 242 */       Toast.makeText(this.mContext, "brightness param error", 0).show();
///* 243 */       return;
///*     */     }
///* 245 */     this.mJni.setBrightness(brightness);
///*     */   }
///*     */
///*     */
///*     */
///*     */
///*     */
///*     */
///*     */   public void setCursorVisible(boolean visible)
///*     */   {
///* 255 */     this.mJni.setCursorVisible(visible);
///*     */   }
///*     */ }
//
//
///* Location:              C:\Users\jame\Desktop\gprintersdkv22.jar!\com\gprinter\io\CustomerDisplay.class
// * Java compiler version: 7 (51.0)
// * JD-Core Version:       0.7.1
// */