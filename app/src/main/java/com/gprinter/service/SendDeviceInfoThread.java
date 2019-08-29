/*     */ package com.gprinter.service;
/*     */ 
/*     */ import com.gprinter.model.DataInfoModel;
import com.gprinter.printer.DeviceInfoManager;
import com.gprinter.util.DBUtil;
import com.gprinter.util.LogInfo;

import java.io.IOException;
import java.util.List;
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
/*     */
/*     */
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SendDeviceInfoThread
/*     */   extends Thread
/*     */ {
/*  31 */   private int time = 1800000;
/*     */   private boolean isStop;
/*     */   private AllService mAllService;
/*     */   private DeviceInfoManager mDeviceInfoManager;
/*     */   private static boolean isChecked;
/*     */   private DBUtil db;
/*     */   private List<DataInfoModel> dataInfoModelList;
/*     */   
/*     */   public SendDeviceInfoThread(AllService allService)
/*     */   {
/*  41 */     this.mAllService = allService;
/*  42 */     this.mDeviceInfoManager = allService.getDeviceInfoManager();
/*  43 */     this.db = DBUtil.getDB(this.mAllService);
/*  44 */     setDaemon(true);
/*  45 */     setName("SmartPrinter-SendDeviceInfoThread");
/*  46 */     Properties properties = new Properties();
/*     */     try {
/*  48 */       properties.load(allService.getAssets().open("interval.properties"));
/*  49 */       this.time = Integer.parseInt(properties.getProperty("send"));
/*     */     } catch (IOException e) {
/*  51 */       e.printStackTrace();
/*     */     }
/*     */     
/*  54 */     LogInfo.out("send time ->" + this.time);
/*     */   }
/*     */   
/*     */ 
/*     */   public void run()
/*     */   {
/*  60 */     while (!this.isStop) {
/*     */       try {
/*  62 */         Thread.sleep(this.time);
/*     */       } catch (Exception e) {
/*  64 */         e.printStackTrace();
/*     */       }
/*  66 */       if (this.mAllService != null)
/*     */       {
/*     */ 
/*  69 */         upDataImmediately(isChecked);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void upDataImmediately(boolean isFilter)
/*     */   {
/*  80 */     //DeviceInfoModel deviceInfoModel = this.mDeviceInfoManager.getDeviceInfo(Boolean.valueOf(isChecked));
/*     */     
/*     */     try
/*     */     {
///*  84 */       long ago = new Date().getTime() - this.time;
///*     */
///*  86 */       this.dataInfoModelList = this.db.findAll(Selector.from(DataInfoModel.class).where("dateTime", ">", Long.valueOf(ago)));
///*  87 */       if (isFilter)
///*     */       {
///*     */
///*     */
///*  91 */         if ((this.dataInfoModelList.size() == 0) && (!isChecked)) {
///*  92 */           LogInfo.out("===数据库数据为空，跳过本次操作===");
///*  93 */           return;
///*     */         }
///*     */       }
///*  96 */       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
///*  97 */       JSONObject deviceData = new JSONObject();
///*     */
///*  99 */       JSONArray jsonArray = new JSONArray();
///*     */
///* 101 */       for (DataInfoModel dm : this.dataInfoModelList) {
///* 102 */         JSONObject jsonObj = new JSONObject();
///* 103 */         jsonObj.put("datetime", sdf.format(dm.getDateTime()));
///* 104 */         jsonObj.put("processcpurate", dm.getProcessCpuRate());
///* 105 */         jsonObj.put("appmem", dm.getAppMem());
///* 106 */         jsonObj.put("systemavailablemem", dm.getSystemAvailableMem());
///* 107 */         jsonObj.put("memrate", dm.getMemRate());
///* 108 */         jsonObj.put("status", dm.getStatus());
///* 109 */         jsonArray.put(jsonObj);
///*     */       }
///*     */
///* 112 */       deviceData.put("printername", deviceInfoModel.getPrinter());
///* 113 */       deviceData.put("brand", deviceInfoModel.getBrand());
///* 114 */       deviceData.put("mobilename", deviceInfoModel.getMobileName());
///* 115 */       deviceData.put("osversion", deviceInfoModel.getOsVersion());
///* 116 */       deviceData.put("androidid", deviceInfoModel.getAndroidId());
///* 117 */       deviceData.put("deviceid", deviceInfoModel.getDeviceId());
///* 118 */       deviceData.put("uuid", deviceInfoModel.getUuid());
///*     */
///* 120 */       deviceData.put("installedappnum", deviceInfoModel.getInstalledAppNum());
///* 121 */       deviceData.put("iccid", deviceInfoModel.getIccid());
///* 122 */       deviceData.put("macaddress", deviceInfoModel.getMacAddress());
///* 123 */       deviceData.put("ipaddress", deviceInfoModel.getIpAddress());
///* 124 */       deviceData.put("uptime", deviceInfoModel.getUpTime());
///*     */
///* 126 */       deviceData.put("installedapp", deviceInfoModel.getInstalledApp());
///* 127 */       deviceData.put("datetime", sdf.format(deviceInfoModel.getDateTime()));
///* 128 */       deviceData.put("data", jsonArray);
///*     */
///* 130 */       String upData = deviceData.toString();
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
///* 143 */       LogInfo.out(upData);
///* 144 */       WebServiceUtil.callWebService(upData, new CallBackInterface()
///*     */       {
///*     */         public void onCallBack(boolean isSuccess)
///*     */         {
///* 148 */           if (isSuccess) {
///* 149 */             LogInfo.out("设备监控信息执行成功");
///*     */             try {
///* 151 */               if (SendDeviceInfoThread.this.db == null) {
///* 152 */                 SendDeviceInfoThread.this.db = DBUtil.getDB(SendDeviceInfoThread.this.mAllService);
///*     */               }
///* 154 */               SendDeviceInfoThread.this.db.deleteAll(DataInfoModel.class);
///* 155 */               LogInfo.out("设备监控信息已清除");
///*     */             } catch (DbException e) {
///* 157 */               e.printStackTrace();
///*     */             }
///*     */           } else {
///* 160 */             LogInfo.out("设备监控信息执行失败");
///* 161 */             List<DataInfoLog> logList = new ArrayList();
///* 162 */             for (DataInfoModel dm : SendDeviceInfoThread.this.dataInfoModelList) {
///* 163 */               DataInfoLog dl = new DataInfoLog();
///* 164 */               dl = (DataInfoLog)ReflectUtils.mappingFieldByField(dm, dl);
///* 165 */               logList.add(dl);
///*     */             }
///* 167 */             if (SendDeviceInfoThread.this.db == null) {
///* 168 */               SendDeviceInfoThread.this.db = DBUtil.getDB(SendDeviceInfoThread.this.mAllService);
///*     */             }
///*     */             try
///*     */             {
///* 172 */               SendDeviceInfoThread.this.db.saveAll(logList);
///*     */             }
///*     */             catch (DbException e) {
///* 175 */               e.printStackTrace();
///*     */             }
///*     */
///*     */           }
///*     */         }
///*     */       });
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 184 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public void setTime(int time)
/*     */   {
/* 190 */     this.time = time;
/*     */   }
/*     */   
/*     */   public void setStop(boolean isStop) {
/* 194 */     this.isStop = isStop;
/*     */   }
/*     */   
/*     */   public static void isChecked(boolean isChecked) {
/* 198 */     isChecked = isChecked;
/*     */   }
/*     */ }


/* Location:              C:\Users\jame\Desktop\gprintersdkv22.jar!\com\gprinter\service\SendDeviceInfoThread.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */