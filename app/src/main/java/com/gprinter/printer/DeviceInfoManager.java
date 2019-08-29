/*     */ package com.gprinter.printer;
/*     */ 
/*     */

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Debug;
import android.os.Process;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.gprinter.model.DataInfoModel;
import com.gprinter.model.DeviceInfoModel;
import com.gprinter.save.PortParamDataBase;
import com.gprinter.service.GpPrintService;
import com.yiku.kdb_flat.BWApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
/*     */ public class DeviceInfoManager
/*     */ {
/*     */   private static Context context= BWApplication.getInstance();
/*     */   private static DeviceInfoManager deviceInfoManager;
/*     */   private static TelephonyManager telephonyManager;
/*     */   private static ActivityManager activityManager;
/*     */   private static LocationManager locationManager;
/*     */   private static final String UNKNOWN = "n";
/*     */   private static final String KEY_DEVICE_ID = "device_id";
/*     */   
/*     */   public static DeviceInfoManager getDeviceInfoManager(Context context)
/*     */   {
/*  54 */     if (deviceInfoManager == null) {
/*  55 */       context = context;
/*  56 */       telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
/*  57 */       activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
/*  58 */       locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
/*  59 */       deviceInfoManager = new DeviceInfoManager();
/*     */     }
/*  61 */     return deviceInfoManager;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DeviceInfoModel getDeviceInfo(Boolean isChecked)
/*     */   {
/*  72 */     DeviceInfoModel deviceInfoModel = new DeviceInfoModel();
/*     */     
/*  74 */     String mobileName = Build.MODEL;
/*  75 */     String mobileBrand = Build.BRAND;
/*  76 */     String ANDROID_ID = Settings.Secure.getString(context.getContentResolver(), "android_id");
/*  77 */     String osVersion = VERSION.RELEASE;
/*  78 */     String deviceID = getDeviceId(telephonyManager.getDeviceId());
/*  79 */     String printerName = new PortParamDataBase(context).readPrinterName(GpPrintService.PrinterId);
/*     */     
/*  81 */     String uuid = UUID.randomUUID().toString();
/*     */     
/*  83 */     deviceInfoModel.setBrand(mobileBrand);
/*  84 */     deviceInfoModel.setMobileName(mobileName);
/*  85 */     deviceInfoModel.setAndroidId(ANDROID_ID);
/*  86 */     deviceInfoModel.setOsVersion(osVersion);
/*  87 */     deviceInfoModel.setDeviceId(deviceID);
/*  88 */     deviceInfoModel.setUuid(uuid);
/*  89 */     deviceInfoModel.setPrinter(printerName);
/*     */     
/*     */ 
/*     */ 
/*  93 */     if (isChecked.booleanValue())
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 101 */       String ICCID = telephonyManager.getSimSerialNumber();
/*     */       
/* 103 */       String macAddress = getMacAddress();
/* 104 */       String ipAddress = getIpAddress();
/*     */       
/* 106 */       String upTime = getTimes();
/* 107 */       int allAppNum = getNumberOfApp();
/* 108 */       int installedAppNum = getNumberOfAppWithoutSystemApp();
/* 109 */       String installedApp = getNameOfInstalledApp();
/* 110 */       Date dateTime = new Date();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 119 */       deviceInfoModel.setIccid(ICCID);
/* 120 */       deviceInfoModel.setMacAddress(macAddress);
/* 121 */       deviceInfoModel.setIpAddress(ipAddress);
/* 122 */       deviceInfoModel.setUpTime(upTime);
/*     */       
/* 124 */       deviceInfoModel.setInstalledAppNum(installedAppNum);
/* 125 */       deviceInfoModel.setInstalledApp(installedApp);
/* 126 */       deviceInfoModel.setDateTime(dateTime);
/*     */     }
/*     */     else
/*     */     {
/* 130 */       Date dateTime = new Date();
/* 131 */       String deviceId = telephonyManager.getDeviceId();
/* 132 */       deviceInfoModel.setDeviceId(deviceId);
/* 133 */       deviceInfoModel.setDateTime(dateTime);
/*     */     }
/*     */     
/* 136 */     return deviceInfoModel;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DataInfoModel getDataInfo()
/*     */   {
/* 147 */     DataInfoModel dataInfoModel = new DataInfoModel();
/*     */     
/*     */ 
/*     */ 
/* 151 */     Date dateTime = new Date();
/*     */     
/* 153 */     int systemAvaialbeMem = getSystemAvaialbeMemorySize();
/* 154 */     int memRate = getMemRate();
/* 155 */     int appMem = getAppMem();
/* 156 */     double processCpuRate = 1;
/*     */     //double processCpuRate = getProcessCpuRate();
/* 158 */     dataInfoModel.setDateTime(dateTime);
/* 159 */     dataInfoModel.setSystemAvailableMem(systemAvaialbeMem);
/* 160 */     dataInfoModel.setMemRate(memRate);
/* 161 */     dataInfoModel.setAppMem(appMem);
/* 162 */     dataInfoModel.setProcessCpuRate(processCpuRate);
/*     */     
/* 164 */     return dataInfoModel;
/*     */   }
/*     */   
/*     */ 
/*     */   private int getNumberOfApp()
/*     */   {
/* 170 */     PackageManager pm = context.getPackageManager();
/*     */     
/* 172 */     List<ApplicationInfo> applicationInfos = pm.getInstalledApplications(8192);
/* 173 */     return applicationInfos.size();
/*     */   }
/*     */   
/*     */   private int getNumberOfAppWithoutSystemApp()
/*     */   {
/* 178 */     int numberOfApp = 0;
/*     */     
/* 180 */     PackageManager pm = context.getPackageManager();
/*     */     
/* 182 */     List<ApplicationInfo> applicationInfos = pm.getInstalledApplications(8192);
/* 183 */     for (ApplicationInfo appInfo : applicationInfos)
/*     */     {
/* 185 */       if ((appInfo.flags & 0x1) == 0) {
/* 186 */         numberOfApp++;
/*     */       }
/*     */     }
/* 189 */     return numberOfApp;
/*     */   }
/*     */   
/*     */ 
/*     */   private String getNameOfInstalledApp()
/*     */   {
/* 195 */     StringBuilder builder = new StringBuilder();
/*     */     
/* 197 */     PackageManager pm = context.getPackageManager();
/*     */     
/* 199 */     List<ApplicationInfo> applicationInfos = pm.getInstalledApplications(8192);
/* 200 */     for (ApplicationInfo appInfo : applicationInfos)
/*     */     {
/* 202 */       if ((appInfo.flags & 0x1) == 0) {
/* 203 */         String str = appInfo.loadLabel(pm).toString();
/* 204 */         builder.append(str + ",");
/*     */       }
/*     */     }
/* 207 */     if (builder.length() > 1) {
/* 208 */       builder.deleteCharAt(builder.length() - 1);
/*     */     }
/* 210 */     return builder.toString();
/*     */   }
/*     */   
/*     */   private String getBtMacAddress()
/*     */   {
/* 215 */     String macAddress = null;
/* 216 */     macAddress = BluetoothAdapter.getDefaultAdapter().getAddress();
/* 217 */     return macAddress;
/*     */   }
/*     */   
/*     */ 
/*     */   private String getMacAddress()
/*     */   {
/* 223 */     WifiManager wifiManager = (WifiManager)context.getSystemService("wifi");
/* 224 */     WifiInfo wifiInfo = wifiManager.getConnectionInfo();
/* 225 */     String macAddress; if (wifiInfo.getMacAddress() != null) {
/* 226 */       macAddress = wifiInfo.getMacAddress();
/*     */     } else {
/* 228 */       macAddress = "Fail";
/*     */     }
/* 230 */     return macAddress;
/*     */   }
/*     */   
/*     */ 
/*     */   private String getIpAddress()
/*     */   {
/* 236 */     WifiManager wifiManager = (WifiManager)context.getSystemService("wifi");
/* 237 */     if (!wifiManager.isWifiEnabled()) {
/* 238 */       return "未开启wifi";
/*     */     }
/* 240 */     WifiInfo wifiInfo = wifiManager.getConnectionInfo();
/* 241 */     int ipAddress = wifiInfo.getIpAddress();
/* 242 */     String ip = intToIp(ipAddress);
/* 243 */     return ip;
/*     */   }
/*     */   
/*     */   private String intToIp(int i)
/*     */   {
/* 248 */     return (i & 0xFF) + "." + (i >> 8 & 0xFF) + "." + (i >> 16 & 0xFF) + "." + (i >> 24 & 0xFF);
/*     */   }
/*     */   
/*     */   private String getTimes()
/*     */   {
/* 253 */     long ut = SystemClock.elapsedRealtime() / 1000L;
/* 254 */     if (ut == 0L) {
/* 255 */       ut = 1L;
/*     */     }
/* 257 */     int m = (int)(ut / 60L % 60L);
/* 258 */     int h = (int)(ut / 3600L);
/* 259 */     return h + "h" + m + "m";
/*     */   }
/*     */   
/*     */ 
/*     */   private int getAppMem()
/*     */   {
/* 265 */     int uid = Process.myUid();
/* 266 */     int memSize = 0;
/*     */     
/* 268 */     for (RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses())
/*     */     {
/* 270 */       if (appProcess.uid == uid) {
/* 271 */         int[] pid = { appProcess.pid };
/* 272 */         Debug.MemoryInfo[] memoryInfo = activityManager.getProcessMemoryInfo(pid);
/* 273 */         memSize = memoryInfo[0].dalvikPrivateDirty + memSize;
/*     */       }
/*     */     }
/*     */     
/* 277 */     return memSize;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private int getSystemAvaialbeMemorySize()
/*     */   {
/* 284 */     ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
/*     */     
/*     */ 
/* 287 */     activityManager.getMemoryInfo(memoryInfo);
/* 288 */     long memSize = memoryInfo.availMem;
/*     */     
/*     */ 
/*     */ 
/* 292 */     int i = (int)memSize / 1048576;
/*     */     
/* 294 */     return i;
/*     */   }
/*     */   
/*     */   private int getMemRate()
/*     */   {
/*     */     try
/*     */     {
/* 301 */       File file = new File("/proc/meminfo");
/* 302 */       FileInputStream fis = new FileInputStream(file);
/* 303 */       BufferedReader br = new BufferedReader(new InputStreamReader(fis));
/* 304 */       String totalRam = br.readLine();
/* 305 */       br.close();
/* 306 */       StringBuffer sb = new StringBuffer();
/* 307 */       char[] cs = totalRam.toCharArray();
/* 308 */       char[] arrayOfChar1; int j = (arrayOfChar1 = cs).length; for (int i = 0; i < j; i++) { char c = arrayOfChar1[i];
/* 309 */         if ((c >= '0') && (c <= '9')) {
/* 310 */           sb.append(c);
/*     */         }
/*     */       }
/* 313 */       int totalMem = (int)(Long.parseLong(sb.toString()) * 1024L) / 1048576;
/* 314 */       int availableMem = getSystemAvaialbeMemorySize();
/* 315 */       return 100 * (totalMem - availableMem) / totalMem;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 319 */       e.printStackTrace(); }
/* 320 */     return 0;
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
/*     */   private double getProcessCpuRate()
/*     */   {
/* 350 */     float totalCpuTime1 = (float)getTotalCpuTime();
/* 351 */     float processCpuTime1 = (float)getAppCpuTime();
/*     */     try {
/* 353 */       Thread.sleep(360L);
/*     */     }
/*     */     catch (Exception localException) {}
/*     */     
/*     */ 
/* 358 */     float totalCpuTime2 = (float)getTotalCpuTime();
/* 359 */     float processCpuTime2 = (float)getAppCpuTime();
/*     */     
/* 361 */     float cpuRate = 100.0F * (processCpuTime2 - processCpuTime1) / (totalCpuTime2 - totalCpuTime1);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 367 */     BigDecimal b = new BigDecimal(cpuRate);
/* 368 */     double f = b.setScale(2, 4).doubleValue();
/*     */     
/* 370 */     return f;
/*     */   }
/*     */   
/*     */   private long getTotalCpuTime()
/*     */   {
/* 375 */     String[] cpuInfos = null;
/*     */     try {
/* 377 */       BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("/proc/stat")), 1000);
/* 378 */       String load = reader.readLine();
/* 379 */       reader.close();
/* 380 */       cpuInfos = load.split(" ");
/*     */     } catch (IOException ex) {
/* 382 */       ex.printStackTrace();
/*     */     }
/* 384 */     long totalCpu = Long.parseLong(cpuInfos[2]) + Long.parseLong(cpuInfos[3]) + Long.parseLong(cpuInfos[4]) + 
/* 385 */       Long.parseLong(cpuInfos[6]) + Long.parseLong(cpuInfos[5]) + Long.parseLong(cpuInfos[7]) + 
/* 386 */       Long.parseLong(cpuInfos[8]);
/* 387 */     return totalCpu;
/*     */   }
/*     */   
/*     */   private long getAppCpuTime()
/*     */   {
/* 392 */     String[] cpuInfos = null;
/*     */     try {
/* 394 */       int pid = Process.myPid();
/* 395 */       BufferedReader reader = new BufferedReader(
/* 396 */         new InputStreamReader(new FileInputStream("/proc/" + pid + "/stat")), 1000);
/* 397 */       String load = reader.readLine();
/* 398 */       reader.close();
/* 399 */       cpuInfos = load.split(" ");
/*     */     } catch (IOException ex) {
/* 401 */       ex.printStackTrace();
/*     */     }
/* 403 */     long appCpuTime = Long.parseLong(cpuInfos[13]) + Long.parseLong(cpuInfos[14]) + Long.parseLong(cpuInfos[15]) + 
/* 404 */       Long.parseLong(cpuInfos[16]);
/* 405 */     return appCpuTime;
/*     */   }
/*     */   
/*     */   private String getDeviceId(String deviceId) {
/* 409 */     if (deviceId == null) {
/* 410 */       String macAddr = getMacAddress().replaceAll(":", "");
/* 411 */       if (macAddr.equals("Fail")) {
/* 412 */         macAddr = getBtMacAddress();
/* 413 */         if (TextUtils.isEmpty(macAddr))
/*     */         {
/* 415 */           SharedPreferences sharedPreference = context.getSharedPreferences("device_id", 
/* 416 */             0);
/* 417 */           deviceId = sharedPreference.getString("device_id", deviceId);
/* 418 */           if (TextUtils.isEmpty(deviceId))
/*     */           {
/* 420 */             double randomNum = Math.random() * 1.0E15D + 1.0D;
/* 421 */             deviceId = String.valueOf(randomNum);
/* 422 */             sharedPreference.edit().putString("device_id", deviceId).commit();
/*     */           }
/*     */           
/* 425 */           return deviceId;
/*     */         }
/*     */       }
/* 428 */       String androidId = Settings.Secure.getString(context.getContentResolver(), "android_id");
/* 429 */       deviceId = macAddr + androidId.substring(androidId.length() - 3, androidId.length());
/*     */     }
/* 431 */     return deviceId;
/*     */   }
/*     */   
/*     */   private String getLocation() {
/* 435 */     Location location = locationManager.getLastKnownLocation("gps");
/* 436 */     double longtitude = location.getLongitude();
/* 437 */     double latitude = location.getLatitude();
/* 438 */     return longtitude + "," + latitude;
/*     */   }
/*     */ }


/* Location:              C:\Users\jame\Desktop\gprintersdkv22.jar!\com\gprinter\printer\DeviceInfoManager.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */