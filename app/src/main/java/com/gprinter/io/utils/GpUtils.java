/*     */ package com.gprinter.io.utils;
/*     */ 
/*     */ import android.graphics.Bitmap;
/*     */ import android.graphics.Canvas;
/*     */ import android.graphics.ColorMatrix;
/*     */ import android.graphics.ColorMatrixColorFilter;
/*     */ import android.graphics.Matrix;
/*     */ import android.graphics.Paint;
/*     */ import android.os.Environment;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ 
/*     */ public class GpUtils
/*     */ {
/*  18 */   private static int[] p0 = { 128 };
/*  19 */   private static int[] p1 = { 064 };
/*  20 */   private static int[] p2 = { 032 };
/*  21 */   private static int[] p3 = { 016 };
/*  22 */   private static int[] p4 = { 8 };
/*  23 */   private static int[] p5 = { 04 };
/*  24 */   private static int[] p6 = { 02 };
/*     */   
/*  26 */   private static int[][] Floyd16x16 = {
/*  27 */     { 0, 128, 32, 160, 8, 136, 40, 168, 2, 130, 34, 162, 10, 138, 42, 
/*  28 */     170 }, 
/*  29 */     { 192, 64, 224, 96, 200, 72, 232, 104, 194, 66, 226, 98, 202, 74, 
/*  30 */     234, 106 }, 
/*  31 */     { 48, 176, 16, 144, 56, 184, 24, 152, 50, 178, 18, 146, 58, 186, 
/*  32 */     26, 154 }, 
/*  33 */     { 240, 112, 208, 80, 248, 120, 216, 88, 242, 114, 210, 82, 250, 
/*  34 */     122, 218, 90 }, 
/*  35 */     { 12, 140, 44, 172, 4, 132, 36, 164, 14, 142, 46, 174, 6, 134, 38, 
/*  36 */     166 }, 
/*  37 */     { 204, 76, 236, 108, 196, 68, 228, 100, 206, 78, 238, 110, 198, 70, 
/*  38 */     230, 102 }, 
/*  39 */     { 60, 188, 28, 156, 52, 180, 20, 148, 62, 190, 30, 158, 54, 182, 
/*  40 */     22, 150 }, 
/*  41 */     { 252, 124, 220, 92, 244, 116, 212, 84, 254, 126, 222, 94, 246, 
/*  42 */     118, 214, 86 }, 
/*  43 */     { 3, 131, 35, 163, 11, 139, 43, 171, 1, 129, 33, 161, 9, 137, 41, 
/*  44 */     169 }, 
/*  45 */     { 195, 67, 227, 99, 203, 75, 235, 107, 193, 65, 225, 97, 201, 73, 
/*  46 */     233, 105 }, 
/*  47 */     { 51, 179, 19, 147, 59, 187, 27, 155, 49, 177, 17, 145, 57, 185, 
/*  48 */     25, 153 }, 
/*  49 */     { 243, 115, 211, 83, 251, 123, 219, 91, 241, 113, 209, 81, 249, 
/*  50 */     121, 217, 89 }, 
/*  51 */     { 15, 143, 47, 175, 7, 135, 39, 167, 13, 141, 45, 173, 5, 133, 37, 
/*  52 */     165 }, 
/*  53 */     { 207, 79, 239, 111, 199, 71, 231, 103, 205, 77, 237, 109, 197, 69, 
/*  54 */     229, 101 }, 
/*  55 */     { 63, 191, 31, 159, 55, 183, 23, 151, 61, 189, 29, 157, 53, 181, 
/*  56 */     21, 149 }, 
/*  57 */     { 254, 127, 223, 95, 247, 119, 215, 87, 253, 125, 221, 93, 245, 
/*  58 */     117, 213, 85 } };
/*     */   
/*  60 */   private static int[][] Floyd8x8 = { { 0, 32, 8, 40, 2, 34, 10, 42 }, 
/*  61 */     { 48, 16, 56, 24, 50, 18, 58, 26 }, 
/*  62 */     { 12, 44, 4, 36, 14, 46, 6, 38 }, 
/*  63 */     { 60, 28, 52, 20, 62, 30, 54, 22 }, 
/*  64 */     { 3, 35, 11, 43, 1, 33, 9, 41 }, 
/*  65 */     { 51, 19, 59, 27, 49, 17, 57, 25 }, 
/*  66 */     { 15, 47, 7, 39, 13, 45, 5, 37 }, 
/*  67 */     { 63, 31, 55, 23, 61, 29, 53, 21 } };
/*     */   public static final int ALGORITHM_DITHER_16x16 = 16;
/*     */   public static final int ALGORITHM_DITHER_8x8 = 8;
/*     */   public static final int ALGORITHM_TEXTMODE = 2;
/*     */   public static final int ALGORITHM_GRAYTEXTMODE = 1;
/*     */   
/*     */   public static Bitmap resizeImage(Bitmap bitmap, int w, int h)
/*     */   {
/*  75 */     Bitmap BitmapOrg = bitmap;
/*     */     
/*  77 */     int width = BitmapOrg.getWidth();
/*  78 */     int height = BitmapOrg.getHeight();
/*  79 */     int newWidth = w;
/*  80 */     int newHeight = h;
/*     */     
/*  82 */     float scaleWidth = newWidth / width;
/*  83 */     float scaleHeight = newHeight / height;
/*  84 */     Matrix matrix = new Matrix();
/*     */     
/*  86 */     matrix.postScale(scaleWidth, scaleHeight);
/*     */     
/*  88 */     Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width, 
/*  89 */       height, matrix, true);
/*     */     
/*  91 */     return resizedBitmap;
/*     */   }
/*     */   
/*     */   public static void saveMyBitmap(Bitmap mBitmap) {
/*  95 */     File f = new File(Environment.getExternalStorageDirectory().getPath(), 
/*  96 */       "Btatotest.jpeg");
/*     */     try {
/*  98 */       f.createNewFile();
/*     */     }
/*     */     catch (IOException localIOException) {}
/* 101 */     FileOutputStream fOut = null;
/*     */     try {
/* 103 */       fOut = new FileOutputStream(f);
/* 104 */       mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
/* 105 */       fOut.flush();
/* 106 */       fOut.close();
/*     */     }
/*     */     catch (FileNotFoundException localFileNotFoundException) {}catch (IOException localIOException1) {}
/*     */   }
/*     */   
/*     */   public static Bitmap toGrayscale(Bitmap bmpOriginal)
/*     */   {
/* 113 */     int height = bmpOriginal.getHeight();
/* 114 */     int width = bmpOriginal.getWidth();
/* 115 */     Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, 
/* 116 */       Bitmap.Config.RGB_565);
/* 117 */     Canvas c = new Canvas(bmpGrayscale);
/* 118 */     Paint paint = new Paint();
/* 119 */     ColorMatrix cm = new ColorMatrix();
/* 120 */     cm.setSaturation(0.0F);
/* 121 */     ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
/* 122 */     paint.setColorFilter(f);
/* 123 */     c.drawBitmap(bmpOriginal, 0.0F, 0.0F, paint);
/* 124 */     return bmpGrayscale;
/*     */   }
/*     */   
/*     */   static byte[] pixToEscRastBitImageCmd(byte[] src, int nWidth, int nMode) {
/* 128 */     int nHeight = src.length / nWidth;
/* 129 */     byte[] data = new byte[8 + src.length / 8];
/* 130 */     data[0] = 29;
/* 131 */     data[1] = 118;
/* 132 */     data[2] = 48;
/* 133 */     data[3] = ((byte)(nMode & 0x1));
/* 134 */     data[4] = ((byte)(nWidth / 8 % 256));
/* 135 */     data[5] = ((byte)(nWidth / 8 / 256));
/* 136 */     data[6] = ((byte)(nHeight % 256));
/* 137 */     data[7] = ((byte)(nHeight / 256));
/* 138 */     int i = 8; for (int k = 0; i < data.length; i++) {
/* 139 */       data[i] = 
/*     */       
/* 141 */         ((byte)(p0[src[k]] + p1[src[(k + 1)]] + p2[src[(k + 2)]] + p3[src[(k + 3)]] + p4[src[(k + 4)]] + p5[src[(k + 5)]] + p6[src[(k + 6)]] + src[(k + 7)]));
/* 142 */       k += 8;
/*     */     }
/* 144 */     return data;
/*     */   }
/*     */   
/*     */   public static byte[] pixToEscRastBitImageCmd(byte[] src) {
/* 148 */     byte[] data = new byte[src.length / 8];
/* 149 */     int i = 0; for (int k = 0; i < data.length; i++) {
/* 150 */       data[i] = 
/*     */       
/* 152 */         ((byte)(p0[src[k]] + p1[src[(k + 1)]] + p2[src[(k + 2)]] + p3[src[(k + 3)]] + p4[src[(k + 4)]] + p5[src[(k + 5)]] + p6[src[(k + 6)]] + src[(k + 7)]));
/* 153 */       k += 8;
/*     */     }
/* 155 */     return data;
/*     */   }
/*     */   
/*     */   static byte[] pixToEscNvBitImageCmd(byte[] src, int width, int height) {
/* 159 */     byte[] data = new byte[src.length / 8 + 4];
/* 160 */     data[0] = ((byte)(width / 8 % 256));
/* 161 */     data[1] = ((byte)(width / 8 / 256));
/* 162 */     data[2] = ((byte)(height / 8 % 256));
/* 163 */     data[3] = ((byte)(height / 8 / 256));
/* 164 */     int k = 0;
/* 165 */     for (int i = 0; i < width; i++) {
/* 166 */       k = 0;
/* 167 */       for (int j = 0; j < height / 8; j++) {
/* 168 */         data[(4 + j + i * height / 8)] = 
/*     */         
/*     */ 
/* 171 */           ((byte)(p0[src[(i + k)]] + p1[src[(i + k + 1 * width)]] + p2[src[(i + k + 2 * width)]] + p3[src[(i + k + 3 * width)]] + p4[src[(i + k + 4 * width)]] + p5[src[(i + k + 5 * width)]] + p6[src[(i + k + 6 * width)]] + src[(i + k + 7 * width)]));
/* 172 */         k += 8 * width;
/*     */       }
/*     */     }
/* 175 */     return data;
/*     */   }
/*     */   
/*     */   public static byte[] pixToTscCmd(byte[] src) {
/* 179 */     byte[] data = new byte[src.length / 8];
/*     */     
/* 181 */     int k = 0; for (int j = 0; k < data.length; k++) {
/* 182 */       byte temp = (byte)(p0[src[j]] + p1[src[(j + 1)]] + p2[src[(j + 2)]] + 
/* 183 */         p3[src[(j + 3)]] + p4[src[(j + 4)]] + p5[src[(j + 5)]] + 
/* 184 */         p6[src[(j + 6)]] + src[(j + 7)]);
/* 185 */       data[k] = ((byte)(temp ^ 0xFFFFFFFF));
/* 186 */       j += 8;
/*     */     }
/* 188 */     return data;
/*     */   }
/*     */   
/*     */   public static byte[] pixToTscCmd(int x, int y, int mode, byte[] src, int nWidth)
/*     */   {
/* 193 */     int height = src.length / nWidth;
/* 194 */     int width = nWidth / 8;
/* 195 */     String str = "BITMAP " + x + "," + y + "," + width + "," + height + "," + 
/* 196 */       mode + ",";
/* 197 */     byte[] bitmap = null;
/*     */     try {
/* 199 */       bitmap = str.getBytes("GB2312");
/*     */     }
/*     */     catch (UnsupportedEncodingException e) {
/* 202 */       e.printStackTrace();
/*     */     }
/* 204 */     byte[] arrayOfByte = new byte[src.length / 8];
/*     */     
/* 206 */     int k = 0; for (int j = 0; k < arrayOfByte.length; k++) {
/* 207 */       byte temp = (byte)(p0[src[j]] + p1[src[(j + 1)]] + p2[src[(j + 2)]] + 
/* 208 */         p3[src[(j + 3)]] + p4[src[(j + 4)]] + p5[src[(j + 5)]] + 
/* 209 */         p6[src[(j + 6)]] + src[(j + 7)]);
/* 210 */       arrayOfByte[k] = ((byte)(temp ^ 0xFFFFFFFF));
/* 211 */       j += 8;
/*     */     }
/* 213 */     byte[] data = new byte[bitmap.length + arrayOfByte.length];
/* 214 */     System.arraycopy(bitmap, 0, data, 0, bitmap.length);
/* 215 */     System.arraycopy(arrayOfByte, 0, data, bitmap.length, 
/* 216 */       arrayOfByte.length);
/* 217 */     return data;
/*     */   }
/*     */   
/*     */   private static void format_K_dither16x16(int[] orgpixels, int xsize, int ysize, byte[] despixels)
/*     */   {
/* 222 */     int k = 0;
/* 223 */     for (int y = 0; y < ysize; y++) {
/* 224 */       for (int x = 0; x < xsize; x++) {
/* 225 */         if ((orgpixels[k] & 0xFF) > Floyd16x16[(x & 0xF)][(y & 0xF)]) {
/* 226 */           despixels[k] = 0;
/*     */         } else {
/* 228 */           despixels[k] = 1;
/*     */         }
/* 230 */         k++;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static byte[] bitmapToBWPix(Bitmap mBitmap)
/*     */   {
/* 237 */     int[] pixels = new int[mBitmap.getWidth() * mBitmap.getHeight()];
/* 238 */     byte[] data = new byte[mBitmap.getWidth() * mBitmap.getHeight()];
/* 239 */     Bitmap grayBitmap = toGrayscale(mBitmap);
/* 240 */     grayBitmap.getPixels(pixels, 0, mBitmap.getWidth(), 0, 0, 
/* 241 */       mBitmap.getWidth(), mBitmap.getHeight());
/*     */     
/* 243 */     format_K_dither16x16(pixels, grayBitmap.getWidth(), 
/* 244 */       grayBitmap.getHeight(), data);
/*     */     
/* 246 */     return data;
/*     */   }
/*     */   
/*     */ 
/*     */   private static void format_K_dither16x16_int(int[] orgpixels, int xsize, int ysize, int[] despixels)
/*     */   {
/* 252 */     int k = 0;
/* 253 */     for (int y = 0; y < ysize; y++) {
/* 254 */       for (int x = 0; x < xsize; x++) {
/* 255 */         if ((orgpixels[k] & 0xFF) > Floyd16x16[(x & 0xF)][(y & 0xF)]) {
/* 256 */           despixels[k] = -1;
/*     */         } else {
/* 258 */           despixels[k] = -16777216;
/*     */         }
/* 260 */         k++;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static void format_K_dither8x8_int(int[] orgpixels, int xsize, int ysize, int[] despixels)
/*     */   {
/* 267 */     int k = 0;
/* 268 */     for (int y = 0; y < ysize; y++) {
/* 269 */       for (int x = 0; x < xsize; x++) {
/* 270 */         if ((orgpixels[k] & 0xFF) >> 2 > Floyd8x8[(x & 0x7)][(y & 0x7)]) {
/* 271 */           despixels[k] = -1;
/*     */         } else {
/* 273 */           despixels[k] = -16777216;
/*     */         }
/* 275 */         k++;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static int[] bitmapToBWPix_int(Bitmap mBitmap, int algorithm) {
/* 281 */     int[] pixels = new int[0];
/* 282 */     switch (algorithm) {
/*     */     case 8: 
/* 284 */       Bitmap grayBitmap = toGrayscale(mBitmap);
/* 285 */       pixels = new int[grayBitmap.getWidth() * grayBitmap.getHeight()];
/* 286 */       grayBitmap.getPixels(pixels, 0, grayBitmap.getWidth(), 0, 0, 
/* 287 */         grayBitmap.getWidth(), grayBitmap.getHeight());
/* 288 */       format_K_dither8x8_int(pixels, grayBitmap.getWidth(), 
/* 289 */         grayBitmap.getHeight(), pixels);
/* 290 */       break;
/*     */     case 2: 
/*     */       break;
/*     */     case 16: 
/*     */     default: 
/* 295 */        grayBitmap = toGrayscale(mBitmap);
/* 296 */       pixels = new int[grayBitmap.getWidth() * grayBitmap.getHeight()];
/* 297 */       grayBitmap.getPixels(pixels, 0, grayBitmap.getWidth(), 0, 0, 
/* 298 */         grayBitmap.getWidth(), grayBitmap.getHeight());
/* 299 */       format_K_dither16x16_int(pixels, grayBitmap.getWidth(), 
/* 300 */         grayBitmap.getHeight(), pixels);
/*     */     }
/*     */     
/* 303 */     return pixels;
/*     */   }
/*     */   
/*     */   public static Bitmap toBinaryImage(Bitmap mBitmap, int nWidth, int algorithm) {
/* 307 */     int width = (nWidth + 7) / 8 * 8;
/* 308 */     int height = mBitmap.getHeight() * width / mBitmap.getWidth();
/* 309 */     Bitmap rszBitmap = resizeImage(mBitmap, width, height);
/*     */     
/* 311 */     int[] pixels = bitmapToBWPix_int(rszBitmap, algorithm);
/* 312 */     rszBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
/*     */     
/* 314 */     return rszBitmap;
/*     */   }
/*     */   
/* 317 */   public static byte[] ByteTo_byte(Byte[] bytes) { byte[] data = new byte[bytes.length];
/* 318 */     int len = bytes.length;
/* 319 */     for (int i = 0; i < len; i++) {
/* 320 */       data[i] = bytes[i].byteValue();
/*     */     }
/* 322 */     return data;
/*     */   }
/*     */ }

