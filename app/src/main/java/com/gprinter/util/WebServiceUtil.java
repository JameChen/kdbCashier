/*     */ package com.gprinter.util;
/*     */ 
/*     */ import com.gprinter.interfaces.CallBackInterface;
import com.gprinter.model.DataInfoModel;
import com.gprinter.model.DeviceInfoModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WebServiceUtil
/*     */ {
/*     */   private static final String targetNameSpace = "http://tempuri.org/";
/*     */   private static final String WSDL = "http://61.143.38.128:8080/Service.asmx";
/*     */   private static final String uploadData = "UploadData";
/*     */   
/*     */   public static void callWebService(String json, final CallBackInterface callBack)
/*     */   {
/*  78 */     new Thread(new Runnable()
/*     */     {
/*     */       public void run()
/*     */       {
/*  35 */         SoapObject soapObject = new SoapObject("http://tempuri.org/", "UploadData");
/*     */         
/*     */ 
/*  38 */         soapObject.addProperty("json", this);
/*     */         
/*     */ 
/*  41 */         SoapSerializationEnvelope envelop = new SoapSerializationEnvelope(100);
/*     */         
/*  43 */         envelop.dotNet = true;
/*     */         
/*  45 */         envelop.setOutputSoapObject(soapObject);
/*     */         
/*  47 */         HttpTransportSE httpSE = new HttpTransportSE("http://61.143.38.128:8080/Service.asmx");
/*     */         try
/*     */         {
/*  50 */           httpSE.call("http://tempuri.org/UploadData", envelop);
/*     */           
/*     */ 
/*  53 */           Object resultObj = envelop.getResponse();
/*     */           
/*  55 */           if (resultObj == null) {
/*  56 */             LogInfo.out("WebService返回结果为空");
/*     */           } else {
/*  58 */             LogInfo.out("WebService返回结果： " + resultObj.toString());
/*  59 */             if (callBack != null)
/*     */             {
/*  61 */               callBack.onCallBack(true);
/*     */             }
/*     */           }
/*     */         }
/*     */         catch (IOException e)
/*     */         {
/*  67 */           e.printStackTrace();
/*  68 */           if (callBack != null)
/*     */           {
/*  70 */             callBack.onCallBack(false);
/*     */           }
/*     */         }
/*     */         catch (XmlPullParserException e) {
/*  74 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */     })
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  78 */       .start();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static JSONObject createJSONObject(DeviceInfoModel deviceInfoModel, List<DataInfoModel> dataInfoModelList, List<Integer> statusList)
/*     */   {
/*  89 */     JSONObject jsonObject = new JSONObject();
/*     */     
/*     */     try
/*     */     {
/*  93 */       jsonObject.put("mobilename", deviceInfoModel.getMobileName());
/*  94 */       jsonObject.put("androidid", deviceInfoModel.getAndroidId());
/*  95 */       jsonObject.put("osversion", deviceInfoModel.getOsVersion());
/*  96 */       jsonObject.put("deviceid", deviceInfoModel.getDeviceId());
/*  97 */       jsonObject.put("iccid", deviceInfoModel.getIccid());
/*  98 */       jsonObject.put("macaddress", deviceInfoModel.getMacAddress());
/*  99 */       jsonObject.put("ipaddress", deviceInfoModel.getIpAddress());
/* 100 */       jsonObject.put("uptime", deviceInfoModel.getUpTime());
/* 101 */       jsonObject.put("allappnum", deviceInfoModel.getAllAppNum());
/* 102 */       jsonObject.put("installedapp", deviceInfoModel.getInstalledApp());
/* 103 */       jsonObject.put("uuid", deviceInfoModel.getUuid());
/* 104 */       jsonObject.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
/* 105 */         deviceInfoModel.getDateTime()));
/*     */       
/*     */ 
/* 108 */       JSONObject dataEle1 = new JSONObject();
/* 109 */       DataInfoModel dataModel1 = (DataInfoModel)dataInfoModelList.get(0);
/* 110 */       dataEle1.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
/* 111 */         dataModel1.getDateTime()));
/* 112 */       dataEle1.put("processcpurate", dataModel1.getProcessCpuRate());
/* 113 */       dataEle1.put("appmem", dataModel1.getAppMem());
/* 114 */       dataEle1.put("systemavailablemem", dataModel1.getSystemAvailableMem());
/* 115 */       dataEle1.put("memrate", dataModel1.getMemRate());
/* 116 */       dataEle1.put("status", statusList.get(0));
/*     */       
/*     */ 
/* 119 */       JSONObject dataEle2 = new JSONObject();
/* 120 */       DataInfoModel dataModel2 = (DataInfoModel)dataInfoModelList.get(1);
/* 121 */       dataEle2.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
/* 122 */         dataModel2.getDateTime()));
/* 123 */       dataEle2.put("processcpurate", dataModel2.getProcessCpuRate());
/* 124 */       dataEle2.put("appmem", dataModel2.getAppMem());
/* 125 */       dataEle2.put("systemavailablemem", dataModel2.getSystemAvailableMem());
/* 126 */       dataEle2.put("memrate", dataModel2.getMemRate());
/* 127 */       dataEle2.put("status", statusList.get(1));
/*     */       
/*     */ 
/* 130 */       JSONObject dataEle3 = new JSONObject();
/* 131 */       DataInfoModel dataModel3 = (DataInfoModel)dataInfoModelList.get(2);
/* 132 */       dataEle3.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
/* 133 */         dataModel3.getDateTime()));
/* 134 */       dataEle3.put("processcpurate", dataModel3.getProcessCpuRate());
/* 135 */       dataEle3.put("appmem", dataModel3.getAppMem());
/* 136 */       dataEle3.put("systemavailablemem", dataModel3.getSystemAvailableMem());
/* 137 */       dataEle3.put("memrate", dataModel3.getMemRate());
/* 138 */       dataEle3.put("status", statusList.get(2));
/*     */       
/* 140 */       JSONArray jsonArray = new JSONArray();
/* 141 */       jsonArray.put(0, dataEle1);
/* 142 */       jsonArray.put(1, dataEle2);
/* 143 */       jsonArray.put(2, dataEle3);
/*     */       
/* 145 */       jsonObject.put("data", jsonArray);
/*     */     }
/*     */     catch (JSONException localJSONException) {}
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 152 */     return jsonObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static JSONObject createJSONObject(DeviceInfoModel deviceInfoModel, List<DataInfoModel> dataInfoModelList)
/*     */   {
/* 161 */     JSONObject jsonObject = new JSONObject();
/*     */     
/*     */     try
/*     */     {
/* 165 */       jsonObject.put("mobilename", deviceInfoModel.getMobileName());
/* 166 */       jsonObject.put("androidid", deviceInfoModel.getAndroidId());
/* 167 */       jsonObject.put("osversion", deviceInfoModel.getOsVersion());
/* 168 */       jsonObject.put("deviceid", deviceInfoModel.getDeviceId());
/* 169 */       jsonObject.put("iccid", deviceInfoModel.getIccid());
/* 170 */       jsonObject.put("macaddress", deviceInfoModel.getMacAddress());
/* 171 */       jsonObject.put("ipaddress", deviceInfoModel.getIpAddress());
/* 172 */       jsonObject.put("uptime", deviceInfoModel.getUpTime());
/* 173 */       jsonObject.put("allappnum", deviceInfoModel.getAllAppNum());
/* 174 */       jsonObject.put("installedapp", deviceInfoModel.getInstalledApp());
/* 175 */       jsonObject.put("uuid", deviceInfoModel.getUuid());
/* 176 */       jsonObject.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
/* 177 */         deviceInfoModel.getDateTime()));
/*     */       
/*     */ 
/* 180 */       JSONObject dataEle1 = new JSONObject();
/* 181 */       DataInfoModel dataModel1 = (DataInfoModel)dataInfoModelList.get(0);
/* 182 */       dataEle1.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
/* 183 */         dataModel1.getDateTime()));
/* 184 */       dataEle1.put("processcpurate", dataModel1.getProcessCpuRate());
/* 185 */       dataEle1.put("appmem", dataModel1.getAppMem());
/* 186 */       dataEle1.put("systemavailablemem", dataModel1.getSystemAvailableMem());
/* 187 */       dataEle1.put("memrate", dataModel1.getMemRate());
/*     */       
/*     */ 
/*     */ 
/* 191 */       JSONObject dataEle2 = new JSONObject();
/* 192 */       DataInfoModel dataModel2 = (DataInfoModel)dataInfoModelList.get(1);
/* 193 */       dataEle2.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
/* 194 */         dataModel2.getDateTime()));
/* 195 */       dataEle2.put("processcpurate", dataModel2.getProcessCpuRate());
/* 196 */       dataEle2.put("appmem", dataModel2.getAppMem());
/* 197 */       dataEle2.put("systemavailablemem", dataModel2.getSystemAvailableMem());
/* 198 */       dataEle2.put("memrate", dataModel2.getMemRate());
/*     */       
/*     */ 
/* 201 */       JSONObject dataEle3 = new JSONObject();
/* 202 */       DataInfoModel dataModel3 = (DataInfoModel)dataInfoModelList.get(2);
/* 203 */       dataEle3.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
/* 204 */         dataModel3.getDateTime()));
/* 205 */       dataEle3.put("processcpurate", dataModel3.getProcessCpuRate());
/* 206 */       dataEle3.put("appmem", dataModel3.getAppMem());
/* 207 */       dataEle3.put("systemavailablemem", dataModel3.getSystemAvailableMem());
/* 208 */       dataEle3.put("memrate", dataModel3.getMemRate());
/*     */       
/* 210 */       JSONArray jsonArray = new JSONArray();
/* 211 */       jsonArray.put(0, dataEle1);
/* 212 */       jsonArray.put(1, dataEle2);
/* 213 */       jsonArray.put(2, dataEle3);
/*     */       
/* 215 */       jsonObject.put("data", jsonArray);
/*     */     }
/*     */     catch (JSONException localJSONException) {}
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 222 */     return jsonObject;
/*     */   }
/*     */ }
