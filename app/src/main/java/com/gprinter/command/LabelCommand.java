/*     */ package com.gprinter.command;
/*     */ 
/*     */ import android.graphics.Bitmap;
/*     */ import android.util.Log;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LabelCommand
/*     */ {
/*     */   private static final String DEBUG_TAG = "LabelCommand";
/*  19 */   Vector<Byte> Command = null;
/*     */   
/*     */   public static enum FOOT {
/*  22 */     F2(0),  F5(1);
/*     */     
/*     */     private final int value;
/*     */     
/*  26 */     private FOOT(int value) { this.value = value; }
/*     */     
/*     */     public int getValue()
/*     */     {
/*  30 */       return this.value;
/*     */     }
/*     */   }
/*     */   
/*     */   public static enum SPEED {
/*  35 */     SPEED1DIV5(1.5F),  SPEED2(2.0F),  SPEED3(3.0F),  SPEED4(4.0F);
/*     */     
/*     */     private final float value;
/*     */     
/*  39 */     private SPEED(float value) { this.value = value; }
/*     */     
/*     */     public float getValue()
/*     */     {
/*  43 */       return this.value;
/*     */     }
/*     */   }
/*     */   
/*     */   public static enum READABEL {
/*  48 */     DISABLE(0),  EANBEL(1);
/*     */     
/*     */     private final int value;
/*     */     
/*  52 */     private READABEL(int value) { this.value = value; }
/*     */     
/*     */     public int getValue()
/*     */     {
/*  56 */       return this.value;
/*     */     }
/*     */   }
/*     */   
/*     */   public static enum BITMAP_MODE {
/*  61 */     OVERWRITE(0),  OR(1),  XOR(2);
/*     */     
/*     */     private final int value;
/*     */     
/*  65 */     private BITMAP_MODE(int value) { this.value = value; }
/*     */     
/*     */     public int getValue()
/*     */     {
/*  69 */       return this.value;
/*     */     }
/*     */   }
/*     */   
/*     */   public static enum DENSITY {
/*  74 */     DNESITY0(0),  DNESITY1(1),  DNESITY2(2),  DNESITY3(3),  DNESITY4(4),  DNESITY5(5),  DNESITY6(6),  DNESITY7(
/*  75 */       7),  DNESITY8(8),  DNESITY9(
/*  76 */       9),  DNESITY10(10),  DNESITY11(11),  DNESITY12(12),  DNESITY13(13),  DNESITY14(14),  DNESITY15(15);
/*     */     
/*     */     private final int value;
/*     */     
/*  80 */     private DENSITY(int value) { this.value = value; }
/*     */     
/*     */     public int getValue()
/*     */     {
/*  84 */       return this.value;
/*     */     }
/*     */   }
/*     */   
/*     */   public static enum DIRECTION {
/*  89 */     FORWARD(0),  BACKWARD(1);
/*     */     
/*     */     private final int value;
/*     */     
/*  93 */     private DIRECTION(int value) { this.value = value; }
/*     */     
/*     */     public int getValue()
/*     */     {
/*  97 */       return this.value;
/*     */     }
/*     */   }
/*     */   
/*     */   public static enum MIRROR {
/* 102 */     NORMAL(0),  MIRROR(1);
/*     */     
/*     */     private final int value;
/*     */     
/* 106 */     private MIRROR(int value) { this.value = value; }
/*     */     
/*     */     public int getValue()
/*     */     {
/* 110 */       return this.value;
/*     */     }
/*     */   }
/*     */   
/*     */   public static enum CODEPAGE {
/* 115 */     PC437(437),  PC850(850),  PC852(852),  PC860(860),  PC863(863),  PC865(865),  WPC1250(1250),  WPC1252(1252),  WPC1253(
/* 116 */       1253),  WPC1254(1254);
/*     */     
/*     */     private final int value;
/*     */     
/* 120 */     private CODEPAGE(int value) { this.value = value; }
/*     */     
/*     */     public int getValue()
/*     */     {
/* 124 */       return this.value;
/*     */     }
/*     */   }
/*     */   
/*     */   public static enum FONTMUL {
/* 129 */     MUL_1(1),  MUL_2(2),  MUL_3(3),  MUL_4(4),  MUL_5(5),  MUL_6(6),  MUL_7(7),  MUL_8(8),  MUL_9(9),  MUL_10(10);
/*     */     
/*     */     private final int value;
/*     */     
/* 133 */     private FONTMUL(int value) { this.value = value; }
/*     */     
/*     */     public int getValue()
/*     */     {
/* 137 */       return this.value;
/*     */     }
/*     */   }
/*     */   
/*     */   public static enum FONTTYPE {
/* 142 */     FONT_1("1"),  FONT_2("2"),  FONT_3("3"),  FONT_4("4"),  FONT_5("5"),  FONT_6("6"),  FONT_7("7"),  FONT_8("8"),  FONT_9(
/* 143 */       "9"),  FONT_10("10"),  SIMPLIFIED_CHINESE("TSS24.BF2"),  TRADITIONAL_CHINESE("TST24.BF2"),  KOREAN("K");
/*     */     
/*     */     private final String value;
/*     */     
/* 147 */     private FONTTYPE(String value) { this.value = value; }
/*     */     
/*     */     public String getValue()
/*     */     {
/* 151 */       return this.value;
/*     */     }
/*     */   }
/*     */   
/*     */   public static enum ROTATION {
/* 156 */     ROTATION_0(0),  ROTATION_90(90),  ROTATION_180(180),  ROTATION_270(270);
/*     */     
/*     */     private final int value;
/*     */     
/* 160 */     private ROTATION(int value) { this.value = value; }
/*     */     
/*     */     public int getValue()
/*     */     {
/* 164 */       return this.value;
/*     */     }
/*     */   }
/*     */   
/*     */   public static enum EEC {
/* 169 */     LEVEL_L("L"),  LEVEL_M("M"),  LEVEL_Q("Q"),  LEVEL_H("H");
/*     */     
/*     */     private final String value;
/*     */     
/* 173 */     private EEC(String value) { this.value = value; }
/*     */     
/*     */     public String getValue()
/*     */     {
/* 177 */       return this.value;
/*     */     }
/*     */   }
/*     */   
/*     */   public static enum BARCODETYPE {
/* 182 */     CODE128("128"),  CODE128M("128M"),  EAN128("EAN128"),  ITF25("25"),  ITF25C("25C"),  CODE39("39"),  CODE39C(
/* 183 */       "39C"),  CODE39S("39S"),  CODE93("93"),  EAN13("EAN13"),  EAN13_2("EAN13+2"),  EAN13_5("EAN13+5"),  EAN8(
/* 184 */       "EAN8"),  EAN8_2("EAN8+2"),  EAN8_5("EAN8+5"),  CODABAR("CODA"),  POST("POST"),  UPCA(
/* 185 */       "UPCA"),  UPCA_2("UPCA+2"),  UPCA_5("UPCA+5"),  UPCE("UPCE13"),  UPCE_2("UPCE13+2"),  UPCE_5(
/* 186 */       "UPCE13+5"),  CPOST("CPOST"),  MSI("MSI"),  MSIC(
/* 187 */       "MSIC"),  PLESSEY("PLESSEY"),  ITF14("ITF14"),  EAN14("EAN14");
/*     */     
/*     */     private final String value;
/*     */     
/* 191 */     private BARCODETYPE(String value) { this.value = value; }
/*     */     
/*     */     public String getValue()
/*     */     {
/* 195 */       return this.value;
/*     */     }
/*     */   }
/*     */   
/*     */   public static enum RESPONSE_MODE {
/* 200 */     ON("ON"),  OFF("OFF"),  BATCH("BATCH");
/*     */     
/*     */     private final String value;
/*     */     
/* 204 */     private RESPONSE_MODE(String value) { this.value = value; }
/*     */     
/*     */     public String getValue()
/*     */     {
/* 208 */       return this.value;
/*     */     }
/*     */   }
/*     */   
/*     */   public LabelCommand() {
/* 213 */     this.Command = new Vector();
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
/*     */   public LabelCommand(int width, int height, int gap)
/*     */   {
/* 227 */     this.Command = new Vector(4096, 1024);
/* 228 */     addSize(width, height);
/* 229 */     addGap(gap);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void clrCommand()
/*     */   {
/* 238 */     this.Command.clear();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void addStrToCommand(String str)
/*     */   {
/* 249 */     byte[] bs = null;
/* 250 */     if (!str.equals("")) {
/*     */       try {
/* 252 */         bs = str.getBytes("GB2312");
/*     */       }
/*     */       catch (UnsupportedEncodingException e) {
/* 255 */         e.printStackTrace();
/*     */       }
/* 257 */       for (int i = 0; i < bs.length; i++) {
/* 258 */         this.Command.add(Byte.valueOf(bs[i]));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addGap(int gap)
/*     */   {
/* 271 */     String str = "GAP " + gap + " mm," + 0 + " mm" + "\r\n";
/* 272 */     addStrToCommand(str);
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
/*     */   public void addSize(int width, int height)
/*     */   {
/* 285 */     String str = "SIZE " + width + " mm," + height + " mm" + "\r\n";
/* 286 */     addStrToCommand(str);
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
/*     */   public void addCashdrwer(FOOT m, int t1, int t2)
/*     */   {
/* 301 */     String str = "CASHDRAWER " + m.getValue() + "," + t1 + "," + t2 + "\r\n";
/* 302 */     addStrToCommand(str);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addOffset(int offset)
/*     */   {
/* 313 */     String str = "OFFSET " + offset + " mm" + "\r\n";
/* 314 */     addStrToCommand(str);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addSpeed(SPEED speed)
/*     */   {
/* 325 */     String str = "SPEED " + speed.getValue() + "\r\n";
/* 326 */     addStrToCommand(str);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addDensity(DENSITY density)
/*     */   {
/* 337 */     String str = "DENSITY " + density.getValue() + "\r\n";
/* 338 */     addStrToCommand(str);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addDirection(DIRECTION direction, MIRROR mirror)
/*     */   {
/* 349 */     String str = "DIRECTION " + direction.getValue() + ',' + mirror.getValue() + "\r\n";
/* 350 */     addStrToCommand(str);
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
/*     */   public void addReference(int x, int y)
/*     */   {
/* 363 */     String str = "REFERENCE " + x + "," + y + "\r\n";
/* 364 */     addStrToCommand(str);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addShif(int shift)
/*     */   {
/* 375 */     String str = "SHIFT " + shift + "\r\n";
/* 376 */     addStrToCommand(str);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addCls()
/*     */   {
/* 385 */     String str = "CLS\r\n";
/* 386 */     addStrToCommand(str);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addFeed(int dot)
/*     */   {
/* 397 */     String str = "FEED " + dot + "\r\n";
/* 398 */     addStrToCommand(str);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addBackFeed(int dot)
/*     */   {
/* 409 */     String str = "BACKFEED " + dot + "\r\n";
/* 410 */     addStrToCommand(str);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addFormFeed()
/*     */   {
/* 419 */     String str = "FORMFEED\r\n";
/* 420 */     addStrToCommand(str);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addHome()
/*     */   {
/* 429 */     String str = "HOME\r\n";
/* 430 */     addStrToCommand(str);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addPrint(int m, int n)
/*     */   {
/* 441 */     String str = "PRINT " + m + "," + n + "\r\n";
/* 442 */     addStrToCommand(str);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addPrint(int m)
/*     */   {
/* 453 */     String str = "PRINT " + m + "\r\n";
/* 454 */     addStrToCommand(str);
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
/*     */   public void addCodePage(CODEPAGE page)
/*     */   {
/* 467 */     String str = "CODEPAGE " + page.getValue() + "\r\n";
/* 468 */     addStrToCommand(str);
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
/*     */   public void addSound(int level, int interval)
/*     */   {
/* 481 */     String str = "SOUND " + level + "," + interval + "\r\n";
/* 482 */     addStrToCommand(str);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addLimitFeed(int n)
/*     */   {
/* 493 */     String str = "LIMITFEED " + n + "\r\n";
/* 494 */     addStrToCommand(str);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addSelfTest()
/*     */   {
/* 503 */     String str = "SELFTEST\r\n";
/* 504 */     addStrToCommand(str);
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
/*     */   public void addBar(int x, int y, int width, int height)
/*     */   {
/* 522 */     String str = "BAR " + x + "," + y + "," + width + "," + height + "\r\n";
/* 523 */     addStrToCommand(str);
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
/*     */   public void addText(int x, int y, FONTTYPE font, ROTATION rotation, FONTMUL Xscal, FONTMUL Yscal, String text)
/*     */   {
/* 546 */     String str = "TEXT " + x + "," + y + "," + "\"" + font.getValue() + "\"" + "," + rotation.getValue() + "," + 
/* 547 */       Xscal.getValue() + "," + Yscal.getValue() + "," + "\"" + text + "\"" + "\r\n";
/* 548 */     addStrToCommand(str);
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
/*     */   public void add1DBarcode(int x, int y, BARCODETYPE type, int height, READABEL readable, ROTATION rotation, String content)
/*     */   {
/* 573 */     int narrow = 2;int width = 2;
/* 574 */     String str = "BARCODE " + x + "," + y + "," + "\"" + type.getValue() + "\"" + "," + height + "," + 
/* 575 */       readable.getValue() + "," + rotation.getValue() + "," + narrow + "," + width + "," + "\"" + content + 
/* 576 */       "\"" + "\r\n";
/* 577 */     addStrToCommand(str);
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
/*     */   public void add1DBarcode(int x, int y, BARCODETYPE type, int height, READABEL readable, ROTATION rotation, int narrow, int width, String content)
/*     */   {
/* 603 */     String str = "BARCODE " + x + "," + y + "," + "\"" + type.getValue() + "\"" + "," + height + "," + readable.getValue() + 
/* 604 */       "," + rotation.getValue() + "," + narrow + "," + width + "," + "\"" + content + "\"" + "\r\n";
/* 605 */     addStrToCommand(str);
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
/*     */   public void addBox(int x, int y, int xend, int yend, int thickness)
/*     */   {
/* 623 */     String str = "BOX " + x + "," + y + "," + xend + "," + yend + "," + thickness + "\r\n";
/* 624 */     addStrToCommand(str);
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
/*     */   public void addBitmap(int x, int y, BITMAP_MODE mode, int nWidth, Bitmap b)
/*     */   {
/* 641 */     if (b != null) {
/* 642 */       int width = (nWidth + 7) / 8 * 8;
/* 643 */       int height = b.getHeight() * width / b.getWidth();
/* 644 */       Log.d("BMP", "bmp.getWidth() " + b.getWidth());
/* 645 */       Bitmap grayBitmap = GpUtils.toGrayscale(b);
/* 646 */       Bitmap rszBitmap = GpUtils.resizeImage(grayBitmap, width, height);
/* 647 */       byte[] src = GpUtils.bitmapToBWPix(rszBitmap);
/* 648 */       height = src.length / width;
/* 649 */       width /= 8;
/* 650 */       String str = "BITMAP " + x + "," + y + "," + width + "," + height + "," + mode.getValue() + ",";
/* 651 */       addStrToCommand(str);
/* 652 */       byte[] codecontent = GpUtils.pixToLabelCmd(src);
/* 653 */       for (int k = 0; k < codecontent.length; k++) {
/* 654 */         this.Command.add(Byte.valueOf(codecontent[k]));
/*     */       }
/* 656 */       Log.d("LabelCommand", "codecontent" + codecontent);
/*     */     }
/*     */   }
/*     */   
/*     */   public void addBitmapByMethod(int x, int y, BITMAP_MODE mode, int nWidth, Bitmap b) {
/* 661 */     if (b != null) {
/* 662 */       int width = (nWidth + 7) / 8 * 8;
/* 663 */       int height = b.getHeight() * width / b.getWidth();
/* 664 */       Log.d("BMP", "bmp.getWidth() " + b.getWidth());
/* 665 */       Bitmap rszBitmap = GpUtils.resizeImage(b, width, height);
/* 666 */       Bitmap grayBitmap = GpUtils.filter(rszBitmap, width, height);
/* 667 */       byte[] src = GpUtils.bitmapToBWPix(grayBitmap);
/* 668 */       height = src.length / width;
/* 669 */       width /= 8;
/* 670 */       String str = "BITMAP " + x + "," + y + "," + width + "," + height + "," + mode.getValue() + ",";
/* 671 */       addStrToCommand(str);
/* 672 */       byte[] codecontent = GpUtils.pixToLabelCmd(src);
/* 673 */       for (int k = 0; k < codecontent.length; k++) {
/* 674 */         this.Command.add(Byte.valueOf(codecontent[k]));
/*     */       }
/* 676 */       Log.d("LabelCommand", "codecontent" + codecontent);
/*     */     }
/*     */   }
/*     */   
/*     */   public void addBitmap(int x, int y, int nWidth, Bitmap bmp) {
/* 681 */     if (bmp != null) {
/* 682 */       int width = (nWidth + 7) / 8 * 8;
/* 683 */       int height = bmp.getHeight() * width / bmp.getWidth();
/* 684 */       Log.d("BMP", "bmp.getWidth() " + bmp.getWidth());
/* 685 */       Bitmap rszBitmap = GpUtils.resizeImage(bmp, width, height);
/* 686 */       byte[] bytes = GpUtils.printTscDraw(x, y, BITMAP_MODE.OVERWRITE, rszBitmap);
/* 687 */       for (int i = 0; i < bytes.length; i++) {
/* 688 */         this.Command.add(Byte.valueOf(bytes[i]));
/*     */       }
/* 690 */       addStrToCommand("\r\n");
/*     */     }
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
/*     */   public void addErase(int x, int y, int xwidth, int yheight)
/*     */   {
/* 707 */     String str = "ERASE " + x + "," + y + "," + xwidth + "," + yheight + "\r\n";
/* 708 */     addStrToCommand(str);
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
/*     */   public void addReverse(int x, int y, int xwidth, int yheight)
/*     */   {
/* 724 */     String str = "REVERSE " + x + "," + y + "," + xwidth + "," + yheight + "\r\n";
/* 725 */     addStrToCommand(str);
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
/*     */   public void addQRCode(int x, int y, EEC level, int cellwidth, ROTATION rotation, String data)
/*     */   {
/* 741 */     String str = "QRCODE " + x + "," + y + "," + level.getValue() + "," + cellwidth + "," + 'A' + "," + 
/* 742 */       rotation.getValue() + "," + "\"" + data + "\"" + "\r\n";
/* 743 */     addStrToCommand(str);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Vector<Byte> getCommand()
/*     */   {
/* 753 */     return this.Command;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addQueryPrinterType()
/*     */   {
/* 762 */     String str = new String();
/* 763 */     str = "~!T\r\n";
/* 764 */     addStrToCommand(str);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addQueryPrinterStatus()
/*     */   {
/* 773 */     this.Command.add(Byte.valueOf((byte)27));
/* 774 */     this.Command.add(Byte.valueOf((byte)33));
/* 775 */     this.Command.add(Byte.valueOf((byte)63));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addResetPrinter()
/*     */   {
/* 784 */     this.Command.add(Byte.valueOf((byte)27));
/* 785 */     this.Command.add(Byte.valueOf((byte)33));
/* 786 */     this.Command.add(Byte.valueOf((byte)82));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addQueryPrinterLife()
/*     */   {
/* 795 */     String str = "~!@\r\n";
/* 796 */     addStrToCommand(str);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addQueryPrinterMemory()
/*     */   {
/* 805 */     String str = "~!A\r\n";
/* 806 */     addStrToCommand(str);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addQueryPrinterFile()
/*     */   {
/* 815 */     String str = "~!F\r\n";
/* 816 */     addStrToCommand(str);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addQueryPrinterCodePage()
/*     */   {
/* 825 */     String str = "~!I\r\n";
/* 826 */     addStrToCommand(str);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addPeel(EscCommand.ENABLE enable)
/*     */   {
/* 837 */     if (enable.getValue() == 0) {
/* 838 */       String str = "SET PEEL " + enable.getValue() + "\r\n";
/* 839 */       addStrToCommand(str);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addTear(EscCommand.ENABLE enable)
/*     */   {
/* 851 */     String str = "SET TEAR " + enable.getValue() + "\r\n";
/* 852 */     addStrToCommand(str);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addCutter(EscCommand.ENABLE enable)
/*     */   {
/* 863 */     String str = "SET CUTTER " + enable.getValue() + "\r\n";
/* 864 */     addStrToCommand(str);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void addCutterBatch()
/*     */   {
/* 871 */     String str = "SET CUTTER BATCH\r\n";
/* 872 */     addStrToCommand(str);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addCutterPieces(short number)
/*     */   {
/* 882 */     String str = "SET CUTTER " + number + "\r\n";
/* 883 */     addStrToCommand(str);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addReprint(EscCommand.ENABLE enable)
/*     */   {
/* 894 */     String str = "SET REPRINT " + enable.getValue() + "\r\n";
/* 895 */     addStrToCommand(str);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addPrintKey(EscCommand.ENABLE enable)
/*     */   {
/* 906 */     String str = "SET PRINTKEY " + enable.getValue() + "\r\n";
/* 907 */     addStrToCommand(str);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addPrintKey(int m)
/*     */   {
/* 918 */     String str = "SET PRINTKEY " + m + "\r\n";
/* 919 */     addStrToCommand(str);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addPartialCutter(EscCommand.ENABLE enable)
/*     */   {
/* 930 */     String str = "SET PARTIAL_CUTTER " + enable.getValue() + "\r\n";
/*     */     
/* 932 */     addStrToCommand(str);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addQueryPrinterStatus(RESPONSE_MODE mode)
/*     */   {
/* 944 */     String str = "SET RESPONSE " + mode.getValue() + "\r\n";
/* 945 */     addStrToCommand(str);
/*     */   }
/*     */   
/*     */   public void addUserCommand(String command) {
/* 949 */     addStrToCommand(command);
/*     */   }
/*     */ }


/* Location:              C:\Users\jame\Desktop\gprintersdkv22.jar!\com\gprinter\command\LabelCommand.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */