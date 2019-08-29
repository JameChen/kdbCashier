/*     */ package com.gprinter.service;
/*     */ 
/*     */ import android.app.Service;
/*     */ import android.content.Intent;
/*     */ import android.content.IntentFilter;
/*     */ import android.content.SharedPreferences;
/*     */ import android.os.IBinder;
/*     */ import android.os.PowerManager;
/*     */ import android.os.PowerManager.WakeLock;
/*     */ import android.preference.PreferenceManager;
/*     */ import android.util.Log;
/*     */ import com.gprinter.model.PrinterStatus;
/*     */ import com.gprinter.printer.DeviceInfoManager;
/*     */ import com.gprinter.printer.PrinterManager;
/*     */ import com.gprinter.util.LogInfo;
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
/*     */ public class AllService
/*     */   extends Service
/*     */ {
/*     */   private PrinterManager mPrinterManager;
/*     */   private DeviceInfoManager mDeviceInfoManager;
/*  35 */   private PrinterStatusBroadcastReceiver printerStatusBroadcastReceiver = null;
/*     */   
/*     */   private UpDeviceStatusThread mUpDeviceStatusThread;
/*     */   private SendDeviceInfoThread mSendDeviceInfoThread;
/*  39 */   private PrinterStatus mPrinterStatus = new PrinterStatus();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String TAG = "--ALLService--";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onCreate()
/*     */   {
/*  60 */     super.onCreate();
/*  61 */     Log.d("--ALLService--", "onCreate()");
/*     */     
/*  63 */     acquireWakeLock();
/*     */     
/*  65 */     LogInfo.setContext(this);
/*     */     
/*  67 */     startPrinterConnect();
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
/*  81 */     this.mDeviceInfoManager = DeviceInfoManager.getDeviceInfoManager(this);
/*     */     
/*  83 */     this.mUpDeviceStatusThread = new UpDeviceStatusThread(this, this.mPrinterStatus);
/*  84 */     this.mUpDeviceStatusThread.start();
/*     */     
/*  86 */     SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(this);
/*  87 */     boolean ischeck = sharedPreference.getBoolean("key_ischecked", true);
/*  88 */     SendDeviceInfoThread.isChecked(ischeck);
/*     */     
/*  90 */     this.mSendDeviceInfoThread = new SendDeviceInfoThread(this);
/*  91 */     this.mSendDeviceInfoThread.start();
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
/*     */   public void startPrinterConnect()
/*     */   {
/* 109 */     if (this.printerStatusBroadcastReceiver != null) {
/* 110 */       unregisterReceiver(this.printerStatusBroadcastReceiver);
/* 111 */       this.printerStatusBroadcastReceiver = null;
/*     */     }
/* 113 */     this.printerStatusBroadcastReceiver = new PrinterStatusBroadcastReceiver();
/* 114 */     IntentFilter filter = new IntentFilter();
/* 115 */     filter.addAction("action.connect.status");
/* 116 */     registerReceiver(this.printerStatusBroadcastReceiver, filter);
/*     */     
/* 118 */     this.mPrinterManager = PrinterManager.getPrinterManager(this);
/*     */     
/* 120 */     this.mPrinterManager.start();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public IBinder onBind(Intent intent)
/*     */   {
/* 127 */     return null;
/*     */   }
/*     */   
/*     */   public int onStartCommand(Intent intent, int flags, int startId)
/*     */   {
/* 132 */     LogInfo.out("-Service onStartCommand-");
/*     */     
/* 134 */     return 1;
/*     */   }
/*     */   
/*     */ 
/*     */   public void onDestroy()
/*     */   {
/* 140 */     this.mUpDeviceStatusThread.setStop(true);
/* 141 */     this.mSendDeviceInfoThread.setStop(true);
/*     */     
/*     */ 
/*     */ 
/* 145 */     if (this.mPrinterManager != null) {
/* 146 */       this.mPrinterManager.stop();
/*     */     }
/* 148 */     if (this.printerStatusBroadcastReceiver != null) {
/* 149 */       unregisterReceiver(this.printerStatusBroadcastReceiver);
/* 150 */       this.printerStatusBroadcastReceiver = null;
/*     */     }
/*     */     
/* 153 */     if (this.mUpDeviceStatusThread.mBroadcastReceiver != null) {
/* 154 */       unregisterReceiver(this.mUpDeviceStatusThread.mBroadcastReceiver);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 160 */     Intent localIntent = new Intent();
/* 161 */     localIntent.setClass(this, AllService.class);
/* 162 */     startService(localIntent);
/* 163 */     releaseWakeLock();
/* 164 */     super.onDestroy();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PrinterManager getmPrinterManager()
/*     */   {
/* 175 */     return this.mPrinterManager;
/*     */   }
/*     */   
/*     */   public void setmPrinterManager(PrinterManager mPrinterManager) {
/* 179 */     this.mPrinterManager = mPrinterManager;
/*     */   }
/*     */   
/*     */   public DeviceInfoManager getDeviceInfoManager() {
/* 183 */     return this.mDeviceInfoManager;
/*     */   }
/*     */   
/*     */   public void setDeviceInfoManager(DeviceInfoManager deviceInfoManager) {
/* 187 */     this.mDeviceInfoManager = deviceInfoManager;
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
/* 213 */   WakeLock wakeLock = null;
/*     */   
/*     */   private void acquireWakeLock()
/*     */   {
/* 217 */     if (this.wakeLock == null) {
/* 218 */       PowerManager pm = (PowerManager)getSystemService("power");
/* 219 */       this.wakeLock = pm.newWakeLock(536870913, 
/* 220 */         "PostLocationService");
/* 221 */       if (this.wakeLock != null) {
/* 222 */         this.wakeLock.acquire();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void releaseWakeLock()
/*     */   {
/* 229 */     if (this.wakeLock != null) {
/* 230 */       this.wakeLock.release();
/* 231 */       this.wakeLock = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\jame\Desktop\gprintersdkv22.jar!\com\gprinter\service\AllService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */