/*     */ package com.gprinter.service;
/*     */ 
/*     */ import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.gprinter.model.DataInfoModel;
import com.gprinter.model.PrinterStatus;
import com.gprinter.printer.DeviceInfoManager;
import com.gprinter.protocol.DeviceStatus;
import com.gprinter.util.DBUtil;
import com.gprinter.util.LogInfo;

import java.io.IOException;
import java.util.Properties;

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
/*     */ public class UpDeviceStatusThread
/*     */   extends Thread
/*     */ {
/*  28 */   private int time = 1700000;
/*     */   
/*     */   private boolean isStop;
/*     */   
/*     */   private boolean error;
/*     */   
/*     */   private AllService mAllService;
/*     */   private PrinterStatus mPrinterStatus;
/*     */   private DeviceInfoManager mDeviceInfoManager;
/*     */   private DBUtil db;
/*     */   private int lastDeviceStatus;
/*     */   public static final int QUERY_PRINTER_STATUS = 255;
/*  40 */   public BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver()
/*     */   {
/*     */     public void onReceive(Context context, Intent intent) {
/*  43 */       String action = intent.getAction();
/*  44 */       if (action.equals("action.device.real.status")) {
/*  45 */         int deviceStatus = intent.getIntExtra("action.printer.real.status", 16);
/*  46 */         int requestCode = intent.getIntExtra("printer.request_code", -1);
/*  47 */         if (requestCode == 255) {
/*  48 */           Intent statusIntent = new Intent("com.pointercn.smartprinter.status.RECEIVER");
/*     */           
/*  50 */           deviceStatus = UpDeviceStatusThread.this.mAllService.getmPrinterManager().getDeviceStatus(deviceStatus);
/*  51 */           if (UpDeviceStatusThread.this.db != null)
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  61 */             if (deviceStatus == 1)
/*     */             {
/*  63 */               UpDeviceStatusThread.this.saveDateInfo(deviceStatus);
/*  64 */               UpDeviceStatusThread.this.setError(false);
/*     */ 
/*     */             }
/*  67 */             else if (!UpDeviceStatusThread.this.error) {
/*  68 */               UpDeviceStatusThread.this.saveDateInfo(deviceStatus);
/*  69 */               UpDeviceStatusThread.this.setError(true);
/*     */             }
/*     */           }
/*     */           
/*  73 */           UpDeviceStatusThread.this.sendStatus(statusIntent, deviceStatus);
/*  74 */           if ((deviceStatus >= 0) && (
/*  75 */             (deviceStatus == 4) || (deviceStatus == 5))) {
/*  76 */             LogInfo.out(" --> 4 无法检测到打印机 或者 5 未知错误 ");
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public UpDeviceStatusThread(AllService allService, PrinterStatus printerStatus)
/*     */   {
/*  88 */     this.mAllService = allService;
/*  89 */     this.mAllService.registerReceiver(this.mBroadcastReceiver, new IntentFilter("action.device.real.status"));
/*  90 */     this.mPrinterStatus = printerStatus;
/*  91 */     this.mDeviceInfoManager = allService.getDeviceInfoManager();
/*  92 */     this.db = DBUtil.getDB(allService);
/*  93 */     setDaemon(true);
/*  94 */     setName("SmartPrinter-UpDeviceStatusThread");
/*  95 */     Properties properties = new Properties();
/*     */     try {
/*  97 */       properties.load(allService.getAssets().open("interval.properties"));
/*  98 */       this.time = Integer.parseInt(properties.getProperty("up"));
/*     */     } catch (IOException e) {
/* 100 */       e.printStackTrace();
/*     */     }
/* 102 */     LogInfo.out("up time ->" + this.time);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void run()
/*     */   {
/* 109 */     Intent statusIntent = new Intent("com.pointercn.smartprinter.status.RECEIVER");
/* 110 */     int status = -1;
/*     */     
/* 112 */     while (!this.isStop)
/*     */     {
/*     */       try {
/* 115 */         Thread.sleep(this.time);
/*     */       } catch (InterruptedException e) {
/* 117 */         e.printStackTrace();
/*     */       }
/*     */       
/* 120 */       if (this.mAllService != null)
/*     */       {
/*     */ 
/*     */ 
/* 124 */         if ((this.mAllService.getmPrinterManager() == null) || (!this.mAllService.getmPrinterManager().isStop()))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 131 */           if (this.mAllService.getmPrinterManager() != null)
/*     */           {
/* 133 */             status = this.mAllService.getmPrinterManager().getPrinterConnectStatus();
/* 134 */             if (status == 3) {
/* 135 */               this.mAllService.getmPrinterManager().getPrinterStatus();
/*     */               try
/*     */               {
/* 138 */                 Thread.sleep(10000L);
/*     */               } catch (InterruptedException e) {
/* 140 */                 e.printStackTrace();
/*     */               }
/* 142 */             } else if (status == 0) {
/* 143 */               sendStatus(statusIntent, DeviceStatus.NO_PRINTER.toInt());
/* 144 */               if (!this.error) {
/* 145 */                 saveDateInfo(DeviceStatus.NO_PRINTER.toInt());
/* 146 */                 setError(true);
/*     */               }
/*     */               
/*     */ 
/*     */ 
/*     */               try
/*     */               {
/* 153 */                 Thread.sleep(5000L);
/*     */               } catch (InterruptedException e) {
/* 155 */                 e.printStackTrace();
/*     */               }
/*     */             }
/*     */           } }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void sendStatus(Intent intent, int printStatus) {
/* 164 */     intent.putExtra("status", printStatus);
/* 165 */     this.mAllService.sendBroadcast(intent);
/*     */   }
/*     */   
/*     */   private void saveDateInfo(int printStatus) {
/* 169 */     if (this.db != null) {
/*     */       try {
/* 171 */         DataInfoModel dm = new DataInfoModel();
/* 172 */         if (this.mDeviceInfoManager != null)
/*     */         {
/* 174 */           dm = this.mDeviceInfoManager.getDataInfo();
/*     */         } else {
/* 176 */           LogInfo.out("mDeviceInfoManager无实例");
/*     */         }
/* 178 */         dm.setStatus(printStatus);
/* 179 */         //this.db.save(dm);
/*     */ 
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 184 */         e.printStackTrace();
/* 185 */         LogInfo.out("Db异常");
/* 186 */         LogInfo.out(e.getCause().getMessage());
/* 187 */         //File f = new File(GpPrintService.DB_DIR + "/" + "smartprint.db");
///* 188 */         LogInfo.out(f.toString());
///* 189 */         if (f.exists()) return;
            }
/* 190 */       sendStatus(new Intent("com.gprinter.status.RECEIVER"), 404);
/*     */     }
/*     */     else
/*     */     {
/* 194 */       LogInfo.out("无db实例");
/*     */     }
/*     */   }
/*     */   
/*     */   public void setError(boolean error)
/*     */   {
/* 200 */     this.error = error;
/*     */   }
/*     */   
/*     */   public void setTime(int time) {
/* 204 */     this.time = time;
/*     */   }
/*     */   
/*     */   public void setStop(boolean isStop) {
/* 208 */     this.isStop = isStop;
/*     */   }
/*     */ }


/* Location:              C:\Users\jame\Desktop\gprintersdkv22.jar!\com\gprinter\service\UpDeviceStatusThread.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */