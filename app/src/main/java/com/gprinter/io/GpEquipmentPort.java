/*     */ package com.gprinter.io;
/*     */ 
/*     */ import java.io.IOException;
/*     */ 
/*     */ public abstract class GpEquipmentPort extends SerialPort {
/*     */   private SerialPort mSerialPort;
/*     */   private ReadThread mReadThread;
/*     */   private java.io.InputStream mInputStream;
/*     */   private android.content.Context mContext;
/*     */   private String mPath;
/*     */   
/*     */   public static abstract interface OnDataReceived { public abstract void onPortOpen(boolean paramBoolean);
/*     */     
/*     */     public abstract void onBacklightStatus(boolean paramBoolean);
/*     */     
/*     */     public abstract void onCursorPosition(int paramInt1, int paramInt2);
/*     */     
/*     */     public abstract void onDisplayRowAndColumn(int paramInt1, int paramInt2);
/*     */     
/*     */     public abstract void onBacklightTimeout(int paramInt);
/*     */     
/*     */     public abstract void onUpdateSuccess();
/*     */     
/*     */     public abstract void onUpdateFail(String paramString);
/*     */   }
/*     */   
/*  27 */   private android.os.Handler mHandler = new android.os.Handler(android.os.Looper.getMainLooper());
/*     */   
/*  29 */   private boolean mRequestVersion = false;
/*  30 */   private boolean mPrepareUpdate = false;
/*  31 */   private boolean mUpdating = false;
/*     */   
/*     */ 
/*  34 */   private int currentPackage = 0;
/*  35 */   private int mPackOffsetL = 0;
/*  36 */   private int mPackOffsetH = 0;
/*  37 */   private int mSignlePackageSize = 64;
/*     */   private byte[] data;
/*     */   
/*     */   protected GpEquipmentPort(android.content.Context context)
/*     */   {
/*  42 */     super(context);
/*  43 */     this.mContext = context;
/*     */   }
/*     */   
/*     */   protected SerialPort getSerialPort(java.io.File file, int baudrate, int flag)
/*     */     throws SecurityException, IOException, java.security.InvalidParameterException
/*     */   {
/*  49 */     if (this.mSerialPort == null)
/*     */     {
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
/*  63 */       this.mSerialPort = new SerialPort(this.mContext);
/*  64 */       this.mSerialPort.openSerialPort(file, baudrate, flag);
/*  65 */       this.mInputStream = this.mSerialPort.getInputStream();
/*     */       
/*  67 */       this.mReadThread = new ReadThread();
/*  68 */       this.mReadThread.start();
/*  69 */       new Thread(new Runnable()
/*     */       {
/*     */         public void run() {
/*     */           try {
/*  73 */             Thread.sleep(200L);
/*     */           } catch (InterruptedException e) {
/*  75 */             e.printStackTrace();
/*     */           }
/*  77 */           GpEquipmentPort.this.is();
/*     */         }
/*     */       })
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  79 */         .start();
/*     */     }
/*     */     
/*  82 */     return this.mSerialPort;
/*     */   }
/*     */   
/*     */   private void resetFlag() {
/*  86 */     this.mUpdating = false;
/*  87 */     this.mRequestVersion = false;
/*  88 */     this.mPrepareUpdate = false;
/*     */   }
/*     */   
/*     */   public void closeSerialPort() {
/*  92 */     resetFlag();
/*     */     
/*  94 */     if (this.mSerialPort != null) {
/*  95 */       this.mSerialPort.close();
/*  96 */       this.mSerialPort = null;
/*  97 */       if (this.mReadThread != null) {
/*  98 */         this.mReadThread.interrupt();
/*  99 */         this.mReadThread = null;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void setPath(String path) {
/* 105 */     this.mPath = path;
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   private int getFileSize()
/*     */     throws IOException
/*     */   {
/*     */     // Byte code:
/*     */     //   0: iconst_0
/*     */     //   1: istore_1
/*     */     //   2: aconst_null
/*     */     //   3: astore_2
/*     */     //   4: iconst_m1
/*     */     //   5: istore_3
/*     */     //   6: aload_0
/*     */     //   7: getfield 132	com/gprinter/io/GpEquipmentPort:mPath	Ljava/lang/String;
/*     */     //   10: invokestatic 137	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
/*     */     //   13: ifeq +13 -> 26
/*     */     //   16: new 77	java/io/IOException
/*     */     //   19: dup
/*     */     //   20: ldc -113
/*     */     //   22: invokespecial 145	java/io/IOException:<init>	(Ljava/lang/String;)V
/*     */     //   25: athrow
/*     */     //   26: new 147	java/io/FileInputStream
/*     */     //   29: dup
/*     */     //   30: aload_0
/*     */     //   31: getfield 132	com/gprinter/io/GpEquipmentPort:mPath	Ljava/lang/String;
/*     */     //   34: invokespecial 149	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
/*     */     //   37: astore_2
/*     */     //   38: aload_2
/*     */     //   39: invokevirtual 150	java/io/FileInputStream:getChannel	()Ljava/nio/channels/FileChannel;
/*     */     //   42: astore 4
/*     */     //   44: goto +6 -> 50
/*     */     //   47: iinc 1 1
/*     */     //   50: aload_2
/*     */     //   51: invokevirtual 154	java/io/FileInputStream:read	()I
/*     */     //   54: iconst_m1
/*     */     //   55: if_icmpne -8 -> 47
/*     */     //   58: aload_0
/*     */     //   59: iload_1
/*     */     //   60: newarray <illegal type>
/*     */     //   62: putfield 157	com/gprinter/io/GpEquipmentPort:data	[B
/*     */     //   65: aload 4
/*     */     //   67: lconst_0
/*     */     //   68: invokevirtual 159	java/nio/channels/FileChannel:position	(J)Ljava/nio/channels/FileChannel;
/*     */     //   71: pop
/*     */     //   72: aload_2
/*     */     //   73: aload_0
/*     */     //   74: getfield 157	com/gprinter/io/GpEquipmentPort:data	[B
/*     */     //   77: invokevirtual 165	java/io/FileInputStream:read	([B)I
/*     */     //   80: istore_3
/*     */     //   81: ldc -88
/*     */     //   83: new 170	java/lang/StringBuilder
/*     */     //   86: dup
/*     */     //   87: iload_3
/*     */     //   88: invokestatic 172	java/lang/String:valueOf	(I)Ljava/lang/String;
/*     */     //   91: invokespecial 178	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
/*     */     //   94: invokevirtual 179	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*     */     //   97: invokestatic 183	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
/*     */     //   100: pop
/*     */     //   101: goto +54 -> 155
/*     */     //   104: astore 4
/*     */     //   106: aload 4
/*     */     //   108: invokevirtual 189	java/io/IOException:printStackTrace	()V
/*     */     //   111: aload_2
/*     */     //   112: ifnull +61 -> 173
/*     */     //   115: aload_2
/*     */     //   116: invokevirtual 192	java/io/FileInputStream:close	()V
/*     */     //   119: goto +54 -> 173
/*     */     //   122: astore 6
/*     */     //   124: aload 6
/*     */     //   126: invokevirtual 189	java/io/IOException:printStackTrace	()V
/*     */     //   129: goto +44 -> 173
/*     */     //   132: astore 5
/*     */     //   134: aload_2
/*     */     //   135: ifnull +17 -> 152
/*     */     //   138: aload_2
/*     */     //   139: invokevirtual 192	java/io/FileInputStream:close	()V
/*     */     //   142: goto +10 -> 152
/*     */     //   145: astore 6
/*     */     //   147: aload 6
/*     */     //   149: invokevirtual 189	java/io/IOException:printStackTrace	()V
/*     */     //   152: aload 5
/*     */     //   154: athrow
/*     */     //   155: aload_2
/*     */     //   156: ifnull +17 -> 173
/*     */     //   159: aload_2
/*     */     //   160: invokevirtual 192	java/io/FileInputStream:close	()V
/*     */     //   163: goto +10 -> 173
/*     */     //   166: astore 6
/*     */     //   168: aload 6
/*     */     //   170: invokevirtual 189	java/io/IOException:printStackTrace	()V
/*     */     //   173: iload_3
/*     */     //   174: iconst_m1
/*     */     //   175: if_icmpne +13 -> 188
/*     */     //   178: new 77	java/io/IOException
/*     */     //   181: dup
/*     */     //   182: ldc -63
/*     */     //   184: invokespecial 145	java/io/IOException:<init>	(Ljava/lang/String;)V
/*     */     //   187: athrow
/*     */     //   188: iload_1
/*     */     //   189: ireturn
/*     */     // Line number table:
/*     */     //   Java source line #109	-> byte code offset #0
/*     */     //   Java source line #110	-> byte code offset #2
/*     */     //   Java source line #111	-> byte code offset #4
/*     */     //   Java source line #112	-> byte code offset #6
/*     */     //   Java source line #113	-> byte code offset #16
/*     */     //   Java source line #116	-> byte code offset #26
/*     */     //   Java source line #117	-> byte code offset #38
/*     */     //   Java source line #118	-> byte code offset #44
/*     */     //   Java source line #119	-> byte code offset #47
/*     */     //   Java source line #118	-> byte code offset #50
/*     */     //   Java source line #121	-> byte code offset #58
/*     */     //   Java source line #123	-> byte code offset #65
/*     */     //   Java source line #124	-> byte code offset #72
/*     */     //   Java source line #125	-> byte code offset #81
/*     */     //   Java source line #126	-> byte code offset #101
/*     */     //   Java source line #127	-> byte code offset #106
/*     */     //   Java source line #129	-> byte code offset #111
/*     */     //   Java source line #131	-> byte code offset #115
/*     */     //   Java source line #132	-> byte code offset #119
/*     */     //   Java source line #133	-> byte code offset #124
/*     */     //   Java source line #128	-> byte code offset #132
/*     */     //   Java source line #129	-> byte code offset #134
/*     */     //   Java source line #131	-> byte code offset #138
/*     */     //   Java source line #132	-> byte code offset #142
/*     */     //   Java source line #133	-> byte code offset #147
/*     */     //   Java source line #136	-> byte code offset #152
/*     */     //   Java source line #129	-> byte code offset #155
/*     */     //   Java source line #131	-> byte code offset #159
/*     */     //   Java source line #132	-> byte code offset #163
/*     */     //   Java source line #133	-> byte code offset #168
/*     */     //   Java source line #137	-> byte code offset #173
/*     */     //   Java source line #138	-> byte code offset #178
/*     */     //   Java source line #140	-> byte code offset #188
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	190	0	this	GpEquipmentPort
/*     */     //   1	188	1	fileSize	int
/*     */     //   3	157	2	inputStream	java.io.FileInputStream
/*     */     //   5	169	3	len	int
/*     */     //   42	24	4	fc	java.nio.channels.FileChannel
/*     */     //   104	3	4	e	IOException
/*     */     //   132	21	5	localObject	Object
/*     */     //   122	3	6	e	IOException
/*     */     //   145	3	6	e	IOException
/*     */     //   166	3	6	e	IOException
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   26	101	104	java/io/IOException
/*     */     //   115	119	122	java/io/IOException
/*     */     //   26	111	132	finally
/*     */     //   138	142	145	java/io/IOException
/*     */     //   159	163	166	java/io/IOException
    return 0;
/*     */   }
/*     */   
/*     */   private void prepareUpdate()
/*     */     throws IOException
/*     */   {
/* 144 */     int fileSize = getFileSize();
/* 145 */     boolean addPack = fileSize % 64 == 0;
/* 146 */     int packSize = addPack ? fileSize / 64 : fileSize / 64 + 1;
/* 147 */     int packL = packSize % 256;
/* 148 */     int packH = packSize / 256;
/* 149 */     int sizeL = 64;
/* 150 */     int sizeH = 0;
/* 151 */     requestUpdate(packL, packH, sizeL, sizeH);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public byte[] getUpdateData()
/*     */   {
/* 158 */     int currentPackageIndex = this.mSignlePackageSize * (this.mPackOffsetL + this.mPackOffsetH * 256);
/* 159 */     int calcPackageSize = this.data.length - currentPackageIndex;
/* 160 */     calcPackageSize = calcPackageSize > 64 ? this.mSignlePackageSize : calcPackageSize;
/* 161 */     byte[] d1 = new byte[calcPackageSize];
/* 162 */     System.arraycopy(this.data, currentPackageIndex, d1, 0, calcPackageSize);
/*     */     
/* 164 */     return d1;
/*     */   }
/*     */   
/* 167 */   int index = 0;
/*     */   
/*     */   private class ReadThread extends Thread {
/*     */     private ReadThread() {}
/*     */     
/*     */     public void run() {
/* 173 */       android.util.Log.d("=======", "Read start");
/* 174 */       byte[] version = new byte[36];
/* 175 */       int versionCnt = 0;
/* 176 */       while (!isInterrupted())
/*     */       {
/*     */         try
/*     */         {
/* 180 */           byte[] buffer = new byte[64];
/* 181 */           if (GpEquipmentPort.this.mInputStream == null)
/* 182 */             return;
/* 183 */           int size = GpEquipmentPort.this.mInputStream.read(buffer);
/*     */           
/* 185 */           if (GpEquipmentPort.this.mOnDataReceived != null) {
/* 186 */             if (GpEquipmentPort.this.mRequestVersion) {
/* 187 */               for (int i = 0; i < size; i++) {
/* 188 */                 if (versionCnt >= 36) break;
/* 189 */                 version[(versionCnt++)] = buffer[i];
/*     */               }
/*     */               
/*     */ 
/*     */ 
/*     */ 
/* 195 */               if (versionCnt == 36)
/*     */               {
/* 197 */                 GpEquipmentPort.this.mRequestVersion = false;
/* 198 */                 GpEquipmentPort.this.mPrepareUpdate = true;
/* 199 */                 versionCnt = 0;
/*     */                 
/* 201 */                 if (checkVersion(new String(version, 0, version.length), new java.io.File(GpEquipmentPort.this.mPath).getName())) {
/* 202 */                   android.util.Log.d("====", "prepareUpdate");
/* 203 */                   GpEquipmentPort.this.prepareUpdate();
/*     */                 } else {
/* 205 */                   GpEquipmentPort.this.resetFlag();
/* 206 */                   GpEquipmentPort.this.callbackDisplayUpdateFail("版本错误");
/*     */                 }
/*     */                 
/*     */               }
/*     */               
/*     */             }
/* 212 */             else if (size == 2) {
/* 213 */               switch (buffer[0]) {
/*     */               case 2: 
/* 215 */                 GpEquipmentPort.this.callbackBacklightStatus(buffer[1]);
/*     */               }
/*     */             }
/* 218 */             else if (size == 3) {
/* 219 */               switch (buffer[0]) {
/*     */               case 1: 
/* 221 */                 GpEquipmentPort.this.callbackBacklightTimeout(buffer[1] & 0xFF, buffer[2] & 0xFF);
/* 222 */                 break;
/*     */               case 3: 
/* 224 */                 GpEquipmentPort.this.callbackCursorPosition(buffer[1], buffer[2]);
/* 225 */                 break;
/*     */               
/*     */               case 4: 
/* 228 */                 GpEquipmentPort.this.callbackDisplayRowAndColumn(buffer[1], buffer[2] & 0xFF);
/*     */               }
/*     */             }
/* 231 */             else if (size == 4)
/*     */             {
/* 233 */               android.util.Log.d("OK", "-----_OK_------");
/* 234 */               String ok = new String(buffer, 0, size);
/*     */               
/*     */ 
/* 237 */               if (GpEquipmentPort.this.mPrepareUpdate) {
/* 238 */                 if ("_OK_".equals(ok)) {
/* 239 */                   GpEquipmentPort.this.mPrepareUpdate = false;
/* 240 */                   GpEquipmentPort.this.mUpdating = true;
/* 241 */                   android.util.Log.d("---ok----", "可以开始下载程序了");
/* 242 */                   update();
/*     */ 
/*     */                 }
/*     */                 
/*     */ 
/*     */               }
/* 248 */               else if (GpEquipmentPort.this.mUpdating) {
/* 249 */                 android.util.Log.d("====updateing====", GpEquipmentPort.this.index + " -> OK");
/* 250 */                 update();
/*     */ 
/*     */ 
/*     */               }
/* 254 */               else if ("_OK_".equals(ok)) {
/* 255 */                 GpEquipmentPort.this.mRequestVersion = true;
/* 256 */                 GpEquipmentPort.this.requestVersionInfo();
/*     */               }
/*     */             }
/* 259 */             else if (size == 5) {
/* 260 */               if ((buffer[0] == 95) && (buffer[1] == 66) && 
/* 261 */                 (buffer[2] == 85) && (buffer[3] == 83) && 
/* 262 */                 (buffer[4] == 89) && (buffer[5] == 95)) {
/* 263 */                 GpEquipmentPort.this.mPrepareUpdate = false;
/* 264 */                 android.util.Log.d("===BUSY===", "下位机忙");
/* 265 */                 GpEquipmentPort.this.resetFlag();
/* 266 */                 GpEquipmentPort.this.callbackDisplayUpdateFail("下位机忙");
/*     */               }
/* 268 */             } else if (size == 7) {
/* 269 */               if ("_ERROR_".equals(new String(buffer, 0, size))) {
/* 270 */                 if (GpEquipmentPort.this.mPrepareUpdate) {
/* 271 */                   android.util.Log.d("===ERROR===", "固件错误");
/* 272 */                   GpEquipmentPort.this.mPrepareUpdate = false;
/*     */                 }
/* 274 */                 GpEquipmentPort.this.resetFlag();
/* 275 */                 GpEquipmentPort.this.callbackDisplayUpdateFail("固件错误");
/*     */               }
/*     */               else {
/* 278 */                 boolean isPortOpen = GpEquipmentPort.this.check(buffer, size);
/* 279 */                 GpEquipmentPort.this.callbackIsPortOpen(isPortOpen);
/*     */               }
/* 281 */             } else if (size == 8) {
/* 282 */               boolean isFinish = new String(buffer, 0, size).equals("_FINISH_");
/* 283 */               if (isFinish) {
/* 284 */                 GpEquipmentPort.this.callbackDisplayUpdateSuccess();
/*     */               } else {
/* 286 */                 GpEquipmentPort.this.updateCheck(buffer, size);
/*     */               }
/*     */             }
/*     */           }
/*     */         } catch (IOException e) {
/* 291 */           e.printStackTrace();
/* 292 */           GpEquipmentPort.this.closeSerialPort();
/* 293 */           android.util.Log.d("GpEquipment", "端口关闭");
/* 294 */           return;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */     private boolean checkVersion(String originVersion, String updateVersion) {
/* 300 */       String[] origin = originVersion.split(",");
/* 301 */       String[] update = updateVersion.split(",");
/* 302 */       if (!origin[0].equals(update[0])) { return false;
/*     */       }
/* 304 */       String originHardwareVer = origin[1].substring(0, 5);
/* 305 */       float originSoftwareVer = Float.parseFloat(origin[1].substring(5));
/* 306 */       String updateHardwareVer = update[1].substring(0, 5);
/* 307 */       float updateSoftwareVer = Float.parseFloat(update[1].substring(5));
/*     */       
/* 309 */       if (!originHardwareVer.equals(updateHardwareVer)) return false;
/* 310 */       if (originSoftwareVer < updateSoftwareVer) { return true;
/*     */       }
/* 312 */       String originDateStr = origin[2].replace("_", "/").replace("E", "");
/* 313 */       String updateDateStr = origin[2].replace("_", "/").replace("E", "");
/* 314 */       java.util.Date originDate = new java.util.Date(originDateStr);
/* 315 */       java.util.Date updateDate = new java.util.Date(updateDateStr);
/* 316 */       return originDate.getTime() < updateDate.getTime();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     private void update()
/*     */     {
/* 324 */       byte[] d = GpEquipmentPort.this.getUpdateData();
/*     */       
/* 326 */       int length = d.length;
/* 327 */       int sizeL = length % 256;
/* 328 */       int sizeH = length / 256;
/*     */       
/*     */ 
/* 331 */       GpEquipmentPort.this.download(GpEquipmentPort.this.mPackOffsetL, GpEquipmentPort.this.mPackOffsetH, sizeL, sizeH, d, d.length);
/*     */       
/* 333 */       GpEquipmentPort.this.mPackOffsetL += 1;
/* 334 */       if (GpEquipmentPort.this.mPackOffsetL > 255) {
/* 335 */         GpEquipmentPort.this.mPackOffsetL = 0;
/* 336 */         GpEquipmentPort.this.mPackOffsetH += 1;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void callbackIsPortOpen(final boolean isOpen)
/*     */   {
/* 347 */     this.mHandler.post(new Runnable()
/*     */     {
/*     */       public void run() {
/* 350 */         GpEquipmentPort.this.mOnDataReceived.onPortOpen(isOpen);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private OnDataReceived mOnDataReceived;
/*     */   
/*     */ 
/*     */ 
/*     */   private void callbackBacklightTimeout(final int t1, final int t2)
/*     */   {
/* 365 */     this.mHandler.post(new Runnable()
/*     */     {
/*     */       public void run() {
/* 368 */         int timeout = t1 + t2 * 256;
/* 369 */         GpEquipmentPort.this.mOnDataReceived.onBacklightTimeout(timeout);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void callbackBacklightStatus(final byte buffer)
/*     */   {
/* 381 */     this.mHandler.post(new Runnable()
/*     */     {
/*     */       public void run() {
/*     */         boolean isOn;
/* 385 */         if (buffer == -86) {
/* 386 */           isOn = true; } else {
/* 387 */           if (buffer == -35)
/* 388 */             isOn = false; else
/*     */             return;
/*     */         }
/* 392 */         GpEquipmentPort.this.mOnDataReceived.onBacklightStatus(isOn);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void callbackCursorPosition(final int x, final int y)
/*     */   {
/* 404 */     this.mHandler.post(new Runnable()
/*     */     {
/*     */       public void run() {
/* 407 */         GpEquipmentPort.this.mOnDataReceived.onCursorPosition(x, y);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void callbackDisplayRowAndColumn(final int x, final int y)
/*     */   {
/* 419 */     this.mHandler.post(new Runnable()
/*     */     {
/*     */       public void run() {
/* 422 */         GpEquipmentPort.this.mOnDataReceived.onDisplayRowAndColumn(x, y);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   private void callbackDisplayUpdateSuccess()
/*     */   {
/* 429 */     this.mHandler.post(new Runnable()
/*     */     {
/*     */       public void run() {
/* 432 */         GpEquipmentPort.this.mOnDataReceived.onUpdateSuccess();
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   private void callbackDisplayUpdateFail(final String error)
/*     */   {
/* 439 */     this.mHandler.post(new Runnable()
/*     */     {
/*     */       public void run() {
/* 442 */         GpEquipmentPort.this.mOnDataReceived.onUpdateFail(error);
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */   protected void setDataReceived(OnDataReceived onDataReceived)
/*     */   {
/* 450 */     this.mOnDataReceived = onDataReceived;
/*     */   }
/*     */ }


/* Location:              C:\Users\jame\Desktop\gprintersdkv22.jar!\com\gprinter\io\GpEquipmentPort.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */