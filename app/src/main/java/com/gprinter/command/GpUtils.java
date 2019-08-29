/*     */ package com.gprinter.command;
/*     */ 
/*     */ import android.graphics.Bitmap;
/*     */ import android.graphics.Bitmap.CompressFormat;
/*     */ import android.graphics.Bitmap.Config;
/*     */ import android.graphics.Canvas;
/*     */ import android.graphics.ColorMatrix;
/*     */ import android.graphics.ColorMatrixColorFilter;
/*     */ import android.graphics.Matrix;
/*     */ import android.graphics.Paint;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Vector;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class GpUtils
/*     */ {
/*  27 */   private static Pattern pattern = Pattern.compile("([a-zA-Z0-9!@#$^&*\\(\\)~\\{\\}:\",\\.<>/]+)");
/*     */   //0128
/*  29 */   private static int[] p0 = { 128 };
/*  30 */   private static int[] p1 = { 064 };
/*  31 */   private static int[] p2 = { 032 };
/*  32 */   private static int[] p3 = { 016 };
    //                              08
/*  33 */   private static int[] p4 = { 8 };
/*  34 */   private static int[] p5 = { 04 };
/*  35 */   private static int[] p6 = { 02 };
/*     */   
/*  37 */   private static int[][] Floyd16x16 = { { 0, 128, 32, 160, 8, 136, 40, 168, 2, 130, 34, 162, 10, 138, 42, 170 }, 
/*  38 */     { 192, 64, 224, 96, 200, 72, 232, 104, 194, 66, 226, 98, 202, 74, 234, 106 }, 
/*  39 */     { 48, 176, 16, 144, 56, 184, 24, 152, 50, 178, 18, 146, 58, 186, 26, 154 }, 
/*  40 */     { 240, 112, 208, 80, 248, 120, 216, 88, 242, 114, 210, 82, 250, 122, 218, 90 }, 
/*  41 */     { 12, 140, 44, 172, 4, 132, 36, 164, 14, 142, 46, 174, 6, 134, 38, 166 }, 
/*  42 */     { 204, 76, 236, 108, 196, 68, 228, 100, 206, 78, 238, 110, 198, 70, 230, 102 }, 
/*  43 */     { 60, 188, 28, 156, 52, 180, 20, 148, 62, 190, 30, 158, 54, 182, 22, 150 }, 
/*  44 */     { 252, 124, 220, 92, 244, 116, 212, 84, 254, 126, 222, 94, 246, 118, 214, 86 }, 
/*  45 */     { 3, 131, 35, 163, 11, 139, 43, 171, 1, 129, 33, 161, 9, 137, 41, 169 }, 
/*  46 */     { 195, 67, 227, 99, 203, 75, 235, 107, 193, 65, 225, 97, 201, 73, 233, 105 }, 
/*  47 */     { 51, 179, 19, 147, 59, 187, 27, 155, 49, 177, 17, 145, 57, 185, 25, 153 }, 
/*  48 */     { 243, 115, 211, 83, 251, 123, 219, 91, 241, 113, 209, 81, 249, 121, 217, 89 }, 
/*  49 */     { 15, 143, 47, 175, 7, 135, 39, 167, 13, 141, 45, 173, 5, 133, 37, 165 }, 
/*  50 */     { 207, 79, 239, 111, 199, 71, 231, 103, 205, 77, 237, 109, 197, 69, 229, 101 }, 
/*  51 */     { 63, 191, 31, 159, 55, 183, 23, 151, 61, 189, 29, 157, 53, 181, 21, 149 }, 
/*  52 */     { 254, 127, 223, 95, 247, 119, 215, 87, 253, 125, 221, 93, 245, 117, 213, 85 } };
/*     */   
/*  54 */   private static int[][] Floyd8x8 = { { 0, 32, 8, 40, 2, 34, 10, 42 }, { 48, 16, 56, 24, 50, 18, 58, 26 }, 
/*  55 */     { 12, 44, 4, 36, 14, 46, 6, 38 }, { 60, 28, 52, 20, 62, 30, 54, 22 }, { 3, 35, 11, 43, 1, 33, 9, 41 }, 
/*  56 */     { 51, 19, 59, 27, 49, 17, 57, 25 }, { 15, 47, 7, 39, 13, 45, 5, 37 }, { 63, 31, 55, 23, 61, 29, 53, 21 } };
/*     */   
/*     */   public static final int PAPER_58_WIDTH = 32;
/*     */   public static final int PAPER_80_WIDTH = 48;
/*  60 */   private static int sPaperWidth = 48;
/*     */   
/*  62 */   private static Integer[] theSet0 = { Integer.valueOf(1569), Integer.valueOf(1570), Integer.valueOf(1571), Integer.valueOf(1572), Integer.valueOf(1573), Integer.valueOf(1574), Integer.valueOf(1575), Integer.valueOf(1576), Integer.valueOf(1577), Integer.valueOf(1578), 
/*  63 */     Integer.valueOf(1579), Integer.valueOf(1580), Integer.valueOf(1581), Integer.valueOf(1582), Integer.valueOf(1583), Integer.valueOf(1584), Integer.valueOf(1585), Integer.valueOf(1586), Integer.valueOf(1587), Integer.valueOf(1588), Integer.valueOf(1589), Integer.valueOf(1590), Integer.valueOf(1591), 
/*  64 */     Integer.valueOf(1592), Integer.valueOf(1593), Integer.valueOf(1594), Integer.valueOf(1601), Integer.valueOf(1602), Integer.valueOf(1603), Integer.valueOf(1604), Integer.valueOf(1605), Integer.valueOf(1606), Integer.valueOf(1607), Integer.valueOf(1608), Integer.valueOf(1609), Integer.valueOf(1610), 
/*  65 */     Integer.valueOf(17442), Integer.valueOf(17443), Integer.valueOf(17445), Integer.valueOf(17447) };
/*     */   
/*     */ 
/*     */ 
/*  69 */   private static Integer[][] FormatTable = { { Integer.valueOf(65152), Integer.valueOf(65152), Integer.valueOf(65152), Integer.valueOf(65152) }, 
/*  70 */     { Integer.valueOf(65153), Integer.valueOf(65154), Integer.valueOf(65153), Integer.valueOf(65154) }, 
/*  71 */     { Integer.valueOf(65155), Integer.valueOf(65156), Integer.valueOf(65155), Integer.valueOf(65156) }, 
/*  72 */     { Integer.valueOf(65157), Integer.valueOf(65157), Integer.valueOf(65157), Integer.valueOf(65157) }, 
/*  73 */     { Integer.valueOf(65149), Integer.valueOf(65149), Integer.valueOf(65149), Integer.valueOf(65149) }, 
/*  74 */     { Integer.valueOf(65163), Integer.valueOf(65163), Integer.valueOf(65163), Integer.valueOf(65163) }, 
/*  75 */     { Integer.valueOf(65165), Integer.valueOf(65166), Integer.valueOf(65165), Integer.valueOf(65166) }, 
/*  76 */     { Integer.valueOf(65167), Integer.valueOf(65167), Integer.valueOf(65169), Integer.valueOf(65169) }, 
/*  77 */     { Integer.valueOf(65171), Integer.valueOf(65171), Integer.valueOf(65171), Integer.valueOf(65171) }, 
/*  78 */     { Integer.valueOf(65173), Integer.valueOf(65173), Integer.valueOf(65175), Integer.valueOf(65175) }, 
/*  79 */     { Integer.valueOf(65177), Integer.valueOf(65177), Integer.valueOf(65179), Integer.valueOf(65179) }, 
/*  80 */     { Integer.valueOf(65181), Integer.valueOf(65181), Integer.valueOf(65183), Integer.valueOf(65183) }, 
/*  81 */     { Integer.valueOf(65185), Integer.valueOf(65185), Integer.valueOf(65187), Integer.valueOf(65187) }, 
/*  82 */     { Integer.valueOf(65189), Integer.valueOf(65189), Integer.valueOf(65191), Integer.valueOf(65191) }, 
/*  83 */     { Integer.valueOf(65193), Integer.valueOf(65193), Integer.valueOf(65193), Integer.valueOf(65193) }, 
/*  84 */     { Integer.valueOf(65195), Integer.valueOf(65195), Integer.valueOf(65195), Integer.valueOf(65195) }, 
/*  85 */     { Integer.valueOf(65197), Integer.valueOf(65197), Integer.valueOf(65197), Integer.valueOf(65197) }, 
/*  86 */     { Integer.valueOf(65199), Integer.valueOf(65199), Integer.valueOf(65199), Integer.valueOf(65199) }, 
/*  87 */     { Integer.valueOf(65201), Integer.valueOf(65201), Integer.valueOf(65203), Integer.valueOf(65203) }, 
/*  88 */     { Integer.valueOf(65205), Integer.valueOf(65205), Integer.valueOf(65207), Integer.valueOf(65207) }, 
/*  89 */     { Integer.valueOf(65209), Integer.valueOf(65209), Integer.valueOf(65211), Integer.valueOf(65211) }, 
/*  90 */     { Integer.valueOf(65213), Integer.valueOf(65213), Integer.valueOf(65215), Integer.valueOf(65215) }, 
/*  91 */     { Integer.valueOf(65217), Integer.valueOf(65217), Integer.valueOf(65217), Integer.valueOf(65217) }, 
/*  92 */     { Integer.valueOf(65221), Integer.valueOf(65221), Integer.valueOf(65221), Integer.valueOf(65221) }, 
/*  93 */     { Integer.valueOf(65225), Integer.valueOf(65226), Integer.valueOf(65227), Integer.valueOf(65228) }, 
/*  94 */     { Integer.valueOf(65229), Integer.valueOf(65230), Integer.valueOf(65231), Integer.valueOf(65232) }, 
/*  95 */     { Integer.valueOf(65233), Integer.valueOf(65233), Integer.valueOf(65235), Integer.valueOf(65235) }, 
/*  96 */     { Integer.valueOf(65237), Integer.valueOf(65237), Integer.valueOf(65239), Integer.valueOf(65239) }, 
/*  97 */     { Integer.valueOf(65241), Integer.valueOf(65241), Integer.valueOf(65243), Integer.valueOf(65243) }, 
/*  98 */     { Integer.valueOf(65245), Integer.valueOf(65245), Integer.valueOf(65247), Integer.valueOf(65247) }, 
/*  99 */     { Integer.valueOf(65249), Integer.valueOf(65249), Integer.valueOf(65251), Integer.valueOf(65251) }, 
/* 100 */     { Integer.valueOf(65253), Integer.valueOf(65253), Integer.valueOf(65255), Integer.valueOf(65255) }, 
/* 101 */     { Integer.valueOf(65257), Integer.valueOf(65257), Integer.valueOf(65259), Integer.valueOf(65259) }, 
/* 102 */     { Integer.valueOf(65261), Integer.valueOf(65261), Integer.valueOf(65261), Integer.valueOf(65261) }, 
/* 103 */     { Integer.valueOf(65263), Integer.valueOf(65264), Integer.valueOf(65263), Integer.valueOf(65264) }, 
/* 104 */     { Integer.valueOf(65265), Integer.valueOf(65266), Integer.valueOf(65267), Integer.valueOf(65267) }, 
/* 105 */     { Integer.valueOf(65269), Integer.valueOf(65270), Integer.valueOf(65269), 
/* 106 */     Integer.valueOf(65270) }, 
/* 107 */     { Integer.valueOf(65271), Integer.valueOf(65272), Integer.valueOf(65271), 
/* 108 */     Integer.valueOf(65272) }, 
/* 109 */     { Integer.valueOf(65273), Integer.valueOf(65274), Integer.valueOf(65273), 
/* 110 */     Integer.valueOf(65274) }, 
/* 111 */     { Integer.valueOf(65275), Integer.valueOf(65276), Integer.valueOf(65275), 
/* 112 */     Integer.valueOf(65276) } };
/*     */   
/*     */ 
/*     */ 
/* 116 */   static Integer[] theSet1 = { Integer.valueOf(1574), Integer.valueOf(1576), Integer.valueOf(1578), Integer.valueOf(1579), Integer.valueOf(1580), Integer.valueOf(1581), Integer.valueOf(1582), Integer.valueOf(1587), Integer.valueOf(1588), Integer.valueOf(1589), Integer.valueOf(1590), Integer.valueOf(1591), 
/* 117 */     Integer.valueOf(1592), Integer.valueOf(1593), Integer.valueOf(1594), Integer.valueOf(1600), Integer.valueOf(1601), Integer.valueOf(1602), Integer.valueOf(1603), Integer.valueOf(1604), Integer.valueOf(1605), Integer.valueOf(1606), Integer.valueOf(1607), Integer.valueOf(1610) };
/*     */   
/* 119 */   static Integer[] theSet2 = { Integer.valueOf(1570), Integer.valueOf(1571), Integer.valueOf(1572), Integer.valueOf(1573), Integer.valueOf(1574), Integer.valueOf(1575), Integer.valueOf(1576), Integer.valueOf(1577), Integer.valueOf(1578), Integer.valueOf(1579), Integer.valueOf(1580), Integer.valueOf(1581), 
/* 120 */     Integer.valueOf(1582), Integer.valueOf(1583), Integer.valueOf(1584), Integer.valueOf(1585), Integer.valueOf(1586), Integer.valueOf(1587), Integer.valueOf(1588), Integer.valueOf(1589), Integer.valueOf(1590), Integer.valueOf(1591), Integer.valueOf(1592), Integer.valueOf(1593), Integer.valueOf(1594), Integer.valueOf(1600), Integer.valueOf(1601), 
/* 121 */     Integer.valueOf(1602), Integer.valueOf(1603), Integer.valueOf(1604), Integer.valueOf(1605), Integer.valueOf(1606), Integer.valueOf(1607), Integer.valueOf(1608), Integer.valueOf(1609), Integer.valueOf(1610) };
/*     */   public static final int ALGORITHM_DITHER_16x16 = 16;
/*     */   public static final int ALGORITHM_DITHER_8x8 = 8;
/*     */   public static final int ALGORITHM_TEXTMODE = 2;
/*     */   public static final int ALGORITHM_GRAYTEXTMODE = 1;
/*     */   
/*     */   public static Bitmap resizeImage(Bitmap bitmap, int w, int h)
/*     */   {
/* 129 */     Bitmap BitmapOrg = bitmap;
/*     */     
/* 131 */     int width = BitmapOrg.getWidth();
/* 132 */     int height = BitmapOrg.getHeight();
/* 133 */     int newWidth = w;
/* 134 */     int newHeight = h;
/*     */     
/* 136 */     float scaleWidth = newWidth / width;
/* 137 */     float scaleHeight = newHeight / height;
/* 138 */     Matrix matrix = new Matrix();
/*     */     
/* 140 */     matrix.postScale(scaleWidth, scaleHeight);
/*     */     
/* 142 */     Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width, height, matrix, true);
/*     */     
/* 144 */     return resizedBitmap;
/*     */   }
/*     */   
/*     */   public static void saveMyBitmap(Bitmap mBitmap) {
/* 148 */     File f = new File(android.os.Environment.getExternalStorageDirectory().getPath(), "Btatotest.jpeg");
/*     */     try {
/* 150 */       f.createNewFile();
/*     */     }
/*     */     catch (IOException localIOException) {}
/* 153 */     FileOutputStream fOut = null;
/*     */     try {
/* 155 */       fOut = new FileOutputStream(f);
/* 156 */       mBitmap.compress(CompressFormat.PNG, 100, fOut);
/* 157 */       fOut.flush();
/* 158 */       fOut.close();
/*     */     }
/*     */     catch (FileNotFoundException localFileNotFoundException) {}catch (IOException localIOException1) {}
/*     */   }
/*     */   
/*     */   public static Bitmap toGrayscale(Bitmap bmpOriginal)
/*     */   {
/* 165 */     int height = bmpOriginal.getHeight();
/* 166 */     int width = bmpOriginal.getWidth();
/* 167 */     Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Config.RGB_565);
/* 168 */     Canvas c = new Canvas(bmpGrayscale);
/* 169 */     Paint paint = new Paint();
/* 170 */     ColorMatrix cm = new ColorMatrix();
/* 171 */     cm.setSaturation(0.0F);
/* 172 */     ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
/* 173 */     paint.setColorFilter(f);
/* 174 */     c.drawBitmap(bmpOriginal, 0.0F, 0.0F, paint);
/* 175 */     return bmpGrayscale;
/*     */   }
/*     */   
/*     */   static byte[] pixToEscRastBitImageCmd(byte[] src, int nWidth, int nMode) {
/* 179 */     int nHeight = src.length / nWidth;
/* 180 */     byte[] data = new byte[8 + src.length / 8];
/* 181 */     data[0] = 29;
/* 182 */     data[1] = 118;
/* 183 */     data[2] = 48;
/* 184 */     data[3] = ((byte)(nMode & 0x1));
/* 185 */     data[4] = ((byte)(nWidth / 8 % 256));
/* 186 */     data[5] = ((byte)(nWidth / 8 / 256));
/* 187 */     data[6] = ((byte)(nHeight % 256));
/* 188 */     data[7] = ((byte)(nHeight / 256));
/* 189 */     int i = 8; for (int k = 0; i < data.length; i++) {
/* 190 */       data[i] = 
/* 191 */         ((byte)(p0[src[k]] + p1[src[(k + 1)]] + p2[src[(k + 2)]] + p3[src[(k + 3)]] + p4[src[(k + 4)]] + p5[src[(k + 5)]] + p6[src[(k + 6)]] + src[(k + 7)]));
/* 192 */       k += 8;
/*     */     }
/* 194 */     return data;
/*     */   }
/*     */   
/*     */   public static byte[] pixToEscRastBitImageCmd(byte[] src) {
/* 198 */     byte[] data = new byte[src.length / 8];
/* 199 */     int i = 0; for (int k = 0; i < data.length; i++) {
/* 200 */       data[i] = 
/* 201 */         ((byte)(p0[src[k]] + p1[src[(k + 1)]] + p2[src[(k + 2)]] + p3[src[(k + 3)]] + p4[src[(k + 4)]] + p5[src[(k + 5)]] + p6[src[(k + 6)]] + src[(k + 7)]));
/* 202 */       k += 8;
/*     */     }
/* 204 */     return data;
/*     */   }
/*     */   
/*     */   static byte[] pixToEscNvBitImageCmd(byte[] src, int width, int height) {
/* 208 */     byte[] data = new byte[src.length / 8 + 4];
/* 209 */     data[0] = ((byte)(width / 8 % 256));
/* 210 */     data[1] = ((byte)(width / 8 / 256));
/* 211 */     data[2] = ((byte)(height / 8 % 256));
/* 212 */     data[3] = ((byte)(height / 8 / 256));
/* 213 */     int k = 0;
/* 214 */     for (int i = 0; i < width; i++) {
/* 215 */       k = 0;
/* 216 */       for (int j = 0; j < height / 8; j++) {
/* 217 */         data[(4 + j + i * height / 8)] = 
/*     */         
/* 219 */           ((byte)(p0[src[(i + k)]] + p1[src[(i + k + 1 * width)]] + p2[src[(i + k + 2 * width)]] + p3[src[(i + k + 3 * width)]] + p4[src[(i + k + 4 * width)]] + p5[src[(i + k + 5 * width)]] + p6[src[(i + k + 6 * width)]] + src[(i + k + 7 * width)]));
/* 220 */         k += 8 * width;
/*     */       }
/*     */     }
/* 223 */     return data;
/*     */   }
/*     */   
/*     */   public static byte[] pixToLabelCmd(byte[] src) {
/* 227 */     byte[] data = new byte[src.length / 8];
/*     */     
/* 229 */     int k = 0; for (int j = 0; k < data.length; k++) {
/* 230 */       byte temp = (byte)(p0[src[j]] + p1[src[(j + 1)]] + p2[src[(j + 2)]] + p3[src[(j + 3)]] + p4[src[(j + 4)]] + 
/* 231 */         p5[src[(j + 5)]] + p6[src[(j + 6)]] + src[(j + 7)]);
/* 232 */       data[k] = ((byte)(temp ^ 0xFFFFFFFF));
/* 233 */       j += 8;
/*     */     }
/* 235 */     return data;
/*     */   }
/*     */   
/*     */   public static byte[] pixToTscCmd(int x, int y, int mode, byte[] src, int nWidth) {
/* 239 */     int height = src.length / nWidth;
/* 240 */     int width = nWidth / 8;
/* 241 */     String str = "BITMAP " + x + "," + y + "," + width + "," + height + "," + mode + ",";
/* 242 */     byte[] bitmap = null;
/*     */     try {
/* 244 */       bitmap = str.getBytes("GB2312");
/*     */     }
/*     */     catch (UnsupportedEncodingException e) {
/* 247 */       e.printStackTrace();
/*     */     }
/* 249 */     byte[] arrayOfByte = new byte[src.length / 8];
/*     */     
/* 251 */     int k = 0; for (int j = 0; k < arrayOfByte.length; k++) {
/* 252 */       byte temp = (byte)(p0[src[j]] + p1[src[(j + 1)]] + p2[src[(j + 2)]] + p3[src[(j + 3)]] + p4[src[(j + 4)]] + 
/* 253 */         p5[src[(j + 5)]] + p6[src[(j + 6)]] + src[(j + 7)]);
/* 254 */       arrayOfByte[k] = ((byte)(temp ^ 0xFFFFFFFF));
/* 255 */       j += 8;
/*     */     }
/* 257 */     byte[] data = new byte[bitmap.length + arrayOfByte.length];
/* 258 */     System.arraycopy(bitmap, 0, data, 0, bitmap.length);
/* 259 */     System.arraycopy(arrayOfByte, 0, data, bitmap.length, arrayOfByte.length);
/* 260 */     return data;
/*     */   }
/*     */   
/*     */   private static void format_K_dither16x16(int[] orgpixels, int xsize, int ysize, byte[] despixels) {
/* 264 */     int k = 0;
/* 265 */     for (int y = 0; y < ysize; y++) {
/* 266 */       for (int x = 0; x < xsize; x++) {
/* 267 */         if ((orgpixels[k] & 0xFF) > Floyd16x16[(x & 0xF)][(y & 0xF)]) {
/* 268 */           despixels[k] = 0;
/*     */         } else {
/* 270 */           despixels[k] = 1;
/*     */         }
/* 272 */         k++;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static byte[] bitmapToBWPix(Bitmap mBitmap) {
/* 278 */     int[] pixels = new int[mBitmap.getWidth() * mBitmap.getHeight()];
/* 279 */     byte[] data = new byte[mBitmap.getWidth() * mBitmap.getHeight()];
/* 280 */     Bitmap grayBitmap = toGrayscale(mBitmap);
/* 281 */     grayBitmap.getPixels(pixels, 0, mBitmap.getWidth(), 0, 0, mBitmap.getWidth(), mBitmap.getHeight());
/*     */     
/* 283 */     format_K_dither16x16(pixels, grayBitmap.getWidth(), grayBitmap.getHeight(), data);
/*     */     
/* 285 */     return data;
/*     */   }
/*     */   
/*     */   private static void format_K_dither16x16_int(int[] orgpixels, int xsize, int ysize, int[] despixels) {
/* 289 */     int k = 0;
/* 290 */     for (int y = 0; y < ysize; y++) {
/* 291 */       for (int x = 0; x < xsize; x++) {
/* 292 */         if ((orgpixels[k] & 0xFF) > Floyd16x16[(x & 0xF)][(y & 0xF)]) {
/* 293 */           despixels[k] = -1;
/*     */         } else {
/* 295 */           despixels[k] = -16777216;
/*     */         }
/* 297 */         k++;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static void format_K_dither8x8_int(int[] orgpixels, int xsize, int ysize, int[] despixels) {
/* 303 */     int k = 0;
/* 304 */     for (int y = 0; y < ysize; y++) {
/* 305 */       for (int x = 0; x < xsize; x++) {
/* 306 */         if ((orgpixels[k] & 0xFF) >> 2 > Floyd8x8[(x & 0x7)][(y & 0x7)]) {
/* 307 */           despixels[k] = -1;
/*     */         } else {
/* 309 */           despixels[k] = -16777216;
/*     */         }
/* 311 */         k++;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static int[] bitmapToBWPix_int(Bitmap mBitmap, int algorithm) {
/* 317 */     int[] pixels = new int[0];
/* 318 */     switch (algorithm) {
/*     */     case 8: 
/* 320 */       Bitmap grayBitmap = toGrayscale(mBitmap);
/* 321 */       pixels = new int[grayBitmap.getWidth() * grayBitmap.getHeight()];
/* 322 */       grayBitmap.getPixels(pixels, 0, grayBitmap.getWidth(), 0, 0, grayBitmap.getWidth(), grayBitmap.getHeight());
/* 323 */       format_K_dither8x8_int(pixels, grayBitmap.getWidth(), grayBitmap.getHeight(), pixels);
/* 324 */       break;
/*     */     case 2: 
/*     */       break;
/*     */     case 16: 
/*     */     default: 
/* 329 */       Bitmap grayBitmap2 = toGrayscale(mBitmap);
/* 330 */       pixels = new int[grayBitmap2.getWidth() * grayBitmap2.getHeight()];
/* 331 */       grayBitmap2.getPixels(pixels, 0, grayBitmap2.getWidth(), 0, 0, grayBitmap2.getWidth(), grayBitmap2.getHeight());
/* 332 */       format_K_dither16x16_int(pixels, grayBitmap2.getWidth(), grayBitmap2.getHeight(), pixels);
/*     */     }
/*     */     
/* 335 */     return pixels;
/*     */   }
/*     */   
/*     */   public static Bitmap toBinaryImage(Bitmap mBitmap, int nWidth, int algorithm) {
/* 339 */     int width = (nWidth + 7) / 8 * 8;
/* 340 */     int height = mBitmap.getHeight() * width / mBitmap.getWidth();
/* 341 */     Bitmap rszBitmap = resizeImage(mBitmap, width, height);
/*     */     
/* 343 */     int[] pixels = bitmapToBWPix_int(rszBitmap, algorithm);
/* 344 */     rszBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
/*     */     
/* 346 */     return rszBitmap;
/*     */   }
/*     */   
/* 349 */   public static final int[][] COLOR_PALETTE = { new int[3], { 255, 255, 255 } };
/*     */   
/*     */   private static int getCloseColor(int tr, int tg, int tb) {
/* 352 */     int minDistanceSquared = 195076;
/* 353 */     int bestIndex = 0;
/* 354 */     for (int i = 0; i < COLOR_PALETTE.length; i++) {
/* 355 */       int rdiff = tr - COLOR_PALETTE[i][0];
/* 356 */       int gdiff = tg - COLOR_PALETTE[i][1];
/* 357 */       int bdiff = tb - COLOR_PALETTE[i][2];
/* 358 */       int distanceSquared = rdiff * rdiff + gdiff * gdiff + bdiff * bdiff;
/* 359 */       if (distanceSquared < minDistanceSquared) {
/* 360 */         minDistanceSquared = distanceSquared;
/* 361 */         bestIndex = i;
/*     */       }
/*     */     }
/*     */     
/* 365 */     return bestIndex;
/*     */   }
/*     */   
/*     */   private static void setPixel(int[] input, int width, int height, int col, int row, int[] p) {
/* 369 */     if ((col < 0) || (col >= width))
/* 370 */       col = 0;
/* 371 */     if ((row < 0) || (row >= height))
/* 372 */       row = 0;
/* 373 */     int index = row * width + col;
/* 374 */     input[index] = (0xFF000000 | clamp(p[0]) << 16 | clamp(p[1]) << 8 | clamp(p[2]));
/*     */   }
/*     */   
/*     */   private static int[] getPixel(int[] input, int width, int height, int col, int row, float error, int[] ergb) {
/* 378 */     if ((col < 0) || (col >= width))
/* 379 */       col = 0;
/* 380 */     if ((row < 0) || (row >= height))
/* 381 */       row = 0;
/* 382 */     int index = row * width + col;
/* 383 */     int tr = input[index] >> 16 & 0xFF;
/* 384 */     int tg = input[index] >> 8 & 0xFF;
/* 385 */     int tb = input[index] & 0xFF;
/* 386 */     tr = (int)(tr + error * ergb[0]);
/* 387 */     tg = (int)(tg + error * ergb[1]);
/* 388 */     tb = (int)(tb + error * ergb[2]);
/* 389 */     return new int[] { tr, tg, tb };
/*     */   }
/*     */   
/*     */   public static int clamp(int value) {
/* 393 */     return value < 0 ? 0 : value > 255 ? 255 : value;
/*     */   }
/*     */   
/*     */   public static Bitmap filter(Bitmap nbm, int width, int height) {
/* 397 */     int[] inPixels = new int[width * height];
/* 398 */     nbm.getPixels(inPixels, 0, width, 0, 0, width, height);
/* 399 */     int[] outPixels = new int[inPixels.length];
/* 400 */     int index = 0;
/* 401 */     for (int row = 0; row < height; row++) {
/* 402 */       for (int col = 0; col < width; col++) {
/* 403 */         index = row * width + col;
/* 404 */         int r1 = inPixels[index] >> 16 & 0xFF;
/* 405 */         int g1 = inPixels[index] >> 8 & 0xFF;
/* 406 */         int b1 = inPixels[index] & 0xFF;
/* 407 */         int cIndex = getCloseColor(r1, g1, b1);
/* 408 */         outPixels[index] = 
/* 409 */           (0xFF000000 | COLOR_PALETTE[cIndex][0] << 16 | COLOR_PALETTE[cIndex][1] << 8 | COLOR_PALETTE[cIndex][2]);
/*     */         
/* 411 */         int[] ergb = new int[3];
/* 412 */         ergb[0] = (r1 - COLOR_PALETTE[cIndex][0]);
/* 413 */         ergb[1] = (g1 - COLOR_PALETTE[cIndex][1]);
/* 414 */         ergb[2] = (b1 - COLOR_PALETTE[cIndex][2]);
/*     */         
/*     */ 
/* 417 */         if (method == 1) {
/* 418 */           float e1 = 0.4375F;
/* 419 */           float e2 = 0.3125F;
/* 420 */           float e3 = 0.1875F;
/* 421 */           float e4 = 0.0625F;
/* 422 */           int[] rgb1 = getPixel(inPixels, width, height, col + 1, row, e1, ergb);
/* 423 */           int[] rgb2 = getPixel(inPixels, width, height, col, row + 1, e2, ergb);
/* 424 */           int[] rgb3 = getPixel(inPixels, width, height, col - 1, row + 1, e3, ergb);
/* 425 */           int[] rgb4 = getPixel(inPixels, width, height, col + 1, row + 1, e4, ergb);
/* 426 */           setPixel(inPixels, width, height, col + 1, row, rgb1);
/* 427 */           setPixel(inPixels, width, height, col, row + 1, rgb2);
/* 428 */           setPixel(inPixels, width, height, col - 1, row + 1, rgb3);
/* 429 */           setPixel(inPixels, width, height, col + 1, row + 1, rgb4);
/* 430 */         } else if (method == 2) {
/* 431 */           float e1 = 0.125F;
/* 432 */           int[] rgb1 = getPixel(inPixels, width, height, col + 1, row, e1, ergb);
/* 433 */           int[] rgb2 = getPixel(inPixels, width, height, col + 2, row, e1, ergb);
/* 434 */           int[] rgb3 = getPixel(inPixels, width, height, col - 1, row + 1, e1, ergb);
/* 435 */           int[] rgb4 = getPixel(inPixels, width, height, col, row + 1, e1, ergb);
/* 436 */           int[] rgb5 = getPixel(inPixels, width, height, col + 1, row + 1, e1, ergb);
/* 437 */           int[] rgb6 = getPixel(inPixels, width, height, col, row + 2, e1, ergb);
/* 438 */           setPixel(inPixels, width, height, col + 1, row, rgb1);
/* 439 */           setPixel(inPixels, width, height, col + 2, row, rgb2);
/* 440 */           setPixel(inPixels, width, height, col - 1, row + 1, rgb3);
/* 441 */           setPixel(inPixels, width, height, col, row + 1, rgb4);
/* 442 */           setPixel(inPixels, width, height, col + 1, row + 1, rgb5);
/* 443 */           setPixel(inPixels, width, height, col, row + 2, rgb6);
/*     */         } else {
/* 445 */           throw new IllegalArgumentException("Not Supported Dither Mothed!!");
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 450 */     Bitmap bitmap = Bitmap.createBitmap(outPixels, 0, width, width, height, Config.RGB_565);
/*     */     
/* 452 */     return bitmap;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public static byte[] printTscDraw(int x, int y, LabelCommand.BITMAP_MODE mode, Bitmap bitmap)
/*     */   {
/* 545 */     int width = bitmap.getWidth();
/* 546 */     int height = bitmap.getHeight();
/* 547 */     byte[] bitbuf = new byte[width / 8];
/* 548 */     String str = "BITMAP " + x + "," + y + "," + width / 8 + "," + height + "," + mode.getValue() + ",";
/* 549 */     byte[] strPrint = null;
/*     */     try {
/* 551 */       strPrint = str.getBytes("GB2312");
/*     */     } catch (UnsupportedEncodingException e) {
/* 553 */       e.printStackTrace();
/*     */     }
/*     */     
/* 556 */     byte[] imgbuf = new byte[width / 8 * height + strPrint.length + 8];
/*     */     
/* 558 */     for (int d = 0; d < strPrint.length; d++) {
/* 559 */       imgbuf[d] = strPrint[d];
/*     */     }
/*     */     
/* 562 */     int s = strPrint.length - 1;
/* 563 */     for (int i = 0; i < height; i++) {
/* 564 */       for (int k = 0; k < width / 8; k++) {
/* 565 */         int c0 = bitmap.getPixel(k * 8, i);
/* 567 */         int p0; if (c0 == -1) {
/* 568 */           p0 = 1;
/*     */         } else {
/* 570 */           p0 = 0;
/*     */         }
/* 572 */         int c1 = bitmap.getPixel(k * 8 + 1, i);
/* 574 */         int p1; if (c1 == -1) {
/* 575 */           p1 = 1;
/*     */         } else {
/* 577 */           p1 = 0;
/*     */         }
/* 579 */         int c2 = bitmap.getPixel(k * 8 + 2, i);
/* 581 */         int p2; if (c2 == -1) {
/* 582 */           p2 = 1;
/*     */         } else {
/* 584 */           p2 = 0;
/*     */         }
/* 586 */         int c3 = bitmap.getPixel(k * 8 + 3, i);
/* 588 */         int p3; if (c3 == -1) {
/* 589 */           p3 = 1;
/*     */         } else {
/* 591 */           p3 = 0;
/*     */         }
/* 593 */         int c4 = bitmap.getPixel(k * 8 + 4, i);
/* 595 */         int p4; if (c4 == -1) {
/* 596 */           p4 = 1;
/*     */         } else {
/* 598 */           p4 = 0;
/*     */         }
/* 600 */         int c5 = bitmap.getPixel(k * 8 + 5, i);
/* 602 */         int p5; if (c5 == -1) {
/* 603 */           p5 = 1;
/*     */         } else {
/* 605 */           p5 = 0;
/*     */         }
/* 607 */         int c6 = bitmap.getPixel(k * 8 + 6, i);
/* 609 */         int p6; if (c6 == -1) {
/* 610 */           p6 = 1;
/*     */         } else {
/* 612 */           p6 = 0;
/*     */         }
/* 614 */         int c7 = bitmap.getPixel(k * 8 + 7, i);
/* 616 */         int p7; if (c7 == -1) {
/* 617 */           p7 = 1;
/*     */         } else {
/* 619 */           p7 = 0;
/*     */         }
/* 621 */         int value = p0 * 128 + p1 * 64 + p2 * 32 + p3 * 16 + p4 * 8 + p5 * 4 + p6 * 2 + p7;
/* 622 */         bitbuf[k] = ((byte)value);
/*     */       }
/*     */       
/* 625 */       for (int t = 0; t < width / 8; t++) {
/* 626 */         s++;
/* 627 */         imgbuf[s] = bitbuf[t];
/*     */       }
/*     */     }
/*     */     
/* 631 */     return imgbuf;
/*     */   }
/*     */   
/*     */   static String splitArabic(String input) {
/* 635 */     StringBuilder sb = new StringBuilder(256);
/* 636 */     String[] arabics = input.split("\\n");
/*     */     
/* 638 */     if ((arabics.length == 1) && (arabics[0].length() > sPaperWidth)) {
/* 639 */       int insertWrapNumber = arabics[0].length() / sPaperWidth;
/* 640 */       int i = 1; for (int j = 0; i <= insertWrapNumber; i++) {
/* 641 */         sb.append(arabics[0].substring(j, sPaperWidth * i));
/* 642 */         j += sPaperWidth;
/*     */       }
/* 644 */       if (sb.length() >= 0) {
/* 645 */         sb.append('\n');
/*     */       }
/* 647 */       int lastArabic = arabics[0].length() % sPaperWidth;
/* 648 */       sb.append(arabics[0].substring(arabics[0].length() - lastArabic, arabics[0].length()));
/* 649 */       return splitArabic(sb.toString());
/*     */     }
/*     */     
/* 652 */     for (int i = 0; i < arabics.length; i++) {
/* 653 */       int childStringLength = arabics[i].length();
/* 654 */       if (childStringLength > sPaperWidth) {
/* 655 */         sb.append(splitArabic(arabics[i]));
/*     */       } else {
/* 657 */         sb.append(addSpaceAfterArabicString(arabics[i], sPaperWidth - childStringLength));
/*     */       }
/*     */     }
/* 660 */     return sb.toString();
/*     */   }
/*     */   
/*     */   static String addSpaceAfterArabicString(String arabic, int number) {
/* 664 */     StringBuilder sb = new StringBuilder(65);
/* 665 */     sb.append(arabic);
/* 666 */     for (int i = 0; i < number; i++) {
/* 667 */       sb.append(' ');
/*     */     }
/* 669 */     sb.append('\n');
/* 670 */     return sb.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static String reverseLetterAndNumber(String input)
/*     */   {
/* 681 */     StringBuilder sb = new StringBuilder(input);
/* 682 */     Matcher matcher = pattern.matcher(input);
/* 683 */     while (matcher.find()) {
/* 684 */       String matcherString = matcher.group();
/* 685 */       int matcherStart = matcher.start();
/* 686 */       int matcherEnd = matcher.end();
/* 687 */       sb.replace(matcherStart, matcherEnd, new StringBuilder(matcherString).reverse().toString());
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 692 */     return sb.toString();
/*     */   }
/*     */   
/*     */   static byte[] string2Cp864(String arabicString) {
/* 696 */     Integer[] originUnicode = new Integer[arabicString.length()];
/* 697 */     Integer[] outputUnicode = new Integer[arabicString.length()];
/*     */     
/*     */ 
/* 700 */     Integer[] outputChars = new Integer[originUnicode.length];
/*     */     
/* 702 */     copy(arabicString.toCharArray(), originUnicode, arabicString.length());
/*     */     
/* 704 */     List<Integer> list = new ArrayList(Arrays.asList(originUnicode));
/* 705 */     list = Hyphen(list);
/* 706 */     list = Deformation(list);
/* 707 */     java.util.Collections.reverse(list);
/* 708 */     list.toArray(outputUnicode);
/*     */     
/* 710 */     char[] chs = integer2Character(outputUnicode);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 717 */     byte[] cp864bytes = new byte[0];
/*     */     try {
/* 719 */       cp864bytes = new String(chs).getBytes("cp864");
/*     */     } catch (UnsupportedEncodingException e) {
/* 721 */       e.printStackTrace();
/*     */     }
/* 723 */     return cp864bytes;
/*     */   }
/*     */   
/*     */   static char[] integer2Character(Integer[] integers)
/*     */   {
/* 728 */     char[] chs = new char[integers.length];
/* 729 */     for (int i = 0; i < integers.length; i++) {
/* 730 */       if (integers[i] != null) {
/* 731 */         chs[i] = ((char)integers[i].intValue());
/*     */       } else
/* 733 */         chs[i] = ' ';
/*     */     }
/* 735 */     return chs;
/*     */   }
/*     */   
/*     */   static void copy(char[] array, Integer[] originUnicode, int length)
/*     */   {
/* 740 */     for (int i = 0; i < length; i++) {
/* 741 */       originUnicode[i] = Integer.valueOf(array[i]);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static List<Integer> Hyphen(List<Integer> list)
/*     */   {
/* 749 */     for (int i = 0; i < list.size(); i++) {
/* 750 */       if (((Integer)list.get(i)).intValue() == 1604)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 756 */         switch (((Integer)list.get(i + 1)).intValue()) {
/*     */         case 1570: 
/* 758 */           list.set(i, Integer.valueOf(17442));
/* 759 */           list.remove(i + 1);
/* 760 */           break;
/*     */         case 1571: 
/* 762 */           list.set(i, Integer.valueOf(17443));
/* 763 */           list.remove(i + 1);
/* 764 */           break;
/*     */         case 1573: 
/* 766 */           list.set(i, Integer.valueOf(17445));
/* 767 */           list.remove(i + 1);
/* 768 */           break;
/*     */         case 1575: 
/* 770 */           list.set(i, Integer.valueOf(17447));
/* 771 */           list.remove(i + 1);
/*     */         }
/*     */         
/*     */       }
/*     */     }
/* 776 */     return list;
/*     */   }
/*     */   
/*     */   static List<Integer> Deformation(List<Integer> inputlist) {
/* 780 */     int flag = 0;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 785 */     List<Integer> outputlist = new ArrayList();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 790 */     Map<Integer, Integer[]> formHashTable = new HashMap(40);
/*     */     
/* 792 */     for (int i = 0; i < 40; i++) {
/* 793 */       formHashTable.put(theSet0[i], FormatTable[i]);
/*     */     }
/*     */     
/* 796 */     for (int i = 0; i < inputlist.size(); i++) {
/* 797 */       if (compare((Integer)inputlist.get(i), 0))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/* 802 */         if (i == 0) {
/* 803 */           boolean inSet1 = false;
/* 804 */           boolean inSet2 = compare((Integer)inputlist.get(i + 1), 2);
/* 805 */           flag = Flag(inSet1, inSet2);
/* 806 */         } else if (i == inputlist.size() - 1) {
/* 807 */           boolean inSet1 = compare((Integer)inputlist.get(i - 1), 1);
/* 808 */           boolean inSet2 = false;
/* 809 */           flag = Flag(inSet1, inSet2);
/*     */         } else {
/* 811 */           boolean inSet1 = compare((Integer)inputlist.get(i - 1), 1);
/* 812 */           boolean inSet2 = compare((Integer)inputlist.get(i + 1), 2);
/* 813 */           flag = Flag(inSet1, inSet2);
/*     */         }
/*     */         
/* 816 */         Integer[] a = 
/* 817 */           (Integer[])formHashTable.get(inputlist.get(i));
/* 818 */         outputlist.add(a[flag]);
/*     */       } else {
/* 820 */         outputlist.add((Integer)inputlist.get(i));
/*     */       }
/*     */     }
/*     */     
/* 824 */     return outputlist;
/*     */   }
/*     */   
/*     */   static boolean compare(Integer input, int i)
/*     */   {
/* 829 */     List<Integer[]> list = new ArrayList(3);
/* 830 */     list.add(theSet0);
/* 831 */     list.add(theSet1);
/* 832 */     list.add(theSet2);
/* 833 */     return findInArray((Integer[])list.get(i), input.intValue());
/*     */   }
/*     */   
/*     */   static boolean findInArray(Integer[] integer, int input) {
/* 837 */     for (int j = 0; j < integer.length; j++) {
/* 838 */       if (integer[j].intValue() == input) {
/* 839 */         return true;
/*     */       }
/*     */     }
/* 842 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static int Flag(boolean set1, boolean set2)
/*     */   {
/* 849 */     if ((set1) && (set2))
/* 850 */       return 3;
/* 851 */     if ((!set1) && (set2))
/* 852 */       return 2;
/* 853 */     if ((set1) && (!set2)) {
/* 854 */       return 1;
/*     */     }
/* 856 */     return 0;
/*     */   }
/*     */   
/*     */   public static void setPaperWidth(int paperWidth) {
/* 860 */     sPaperWidth = paperWidth;
/*     */   }
/*     */   
/*     */   public static byte[] ByteTo_byte(Vector<Byte> vector) {
/* 864 */     int len = vector.size();
/* 865 */     byte[] data = new byte[len];
/* 866 */     for (int i = 0; i < len; i++) {
/* 867 */       data[i] = ((Byte)vector.get(i)).byteValue();
/*     */     }
/* 869 */     return data;
/*     */   }
/*     */   
/* 872 */   private static int method = 1;
/*     */   public static final int FLOYD_STEINBERG_DITHER = 1;
/*     */   public static final int ATKINSON_DITHER = 2;
/*     */   
/*     */   public int getMethod() {
/* 877 */     return method;
/*     */   }
/*     */   
/*     */   public static void setMethod(int method) {
/* 881 */     method = method;
/*     */   }
/*     */ }


/* Location:              C:\Users\jame\Desktop\gprintersdkv22.jar!\com\gprinter\command\GpUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */