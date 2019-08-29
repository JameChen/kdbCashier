/*    */ package com.gprinter.save;
/*    */ 
/*    */ import android.content.Context;
/*    */ import android.content.SharedPreferences;
/*    */ import android.content.SharedPreferences.Editor;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SharedPreferencesUtil
/*    */ {
/*    */   private static final String XMLNAME = "SmartPrinter";
/*    */   private static final String XMLNAME2 = "Smartprinter-DeviceInfoPres";
/*    */   public static final String INIT_KEY = "init";
/*    */   public static final String LABEL_WIDTH_KEY = "labelWidth";
/*    */   public static final String LABEL_HEIGHT_KEY = "labelHeight";
/*    */   public static final String LABEL_GAP_KEY = "labelGap";
/*    */   public static final String KEYS_KEY = "keys";
/*    */   
/*    */   public static String ReadSharedPerference(Context context, String key)
/*    */   {
/* 36 */     SharedPreferences preferences = context.getSharedPreferences("SmartPrinter", 
/* 37 */       0);
/*    */     
/* 39 */     String value = preferences.getString(key, null);
/*    */     
/* 41 */     return value;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static void SharedPerferencesCreat(Context context, String key, String value)
/*    */   {
/* 58 */     SharedPreferences preferences = context.getSharedPreferences("SmartPrinter", 
/* 59 */       0);
/* 60 */     Editor edit = preferences.edit();
/* 61 */     edit.putString(key, value);
/* 62 */     edit.commit();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static Boolean ReadDeviceInfoSharedPerference(Context context, String key)
/*    */   {
/* 74 */     SharedPreferences preferences = context.getSharedPreferences("Smartprinter-DeviceInfoPres", 
/* 75 */       0);
/* 76 */     Boolean value = Boolean.valueOf(preferences.getBoolean(key, false));
/* 77 */     return value;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static void DeviceInfoSharedPerferencesCreat(Context context, String key, Boolean value)
/*    */   {
/* 89 */     SharedPreferences preferences = context.getSharedPreferences("Smartprinter-DeviceInfoPres", 
/* 90 */       0);
/* 91 */     Editor edit = preferences.edit();
/* 92 */     edit.putBoolean(key, value.booleanValue());
/* 93 */     edit.commit();
/*    */   }
/*    */ }


/* Location:              C:\Users\jame\Desktop\gprintersdkv22.jar!\com\gprinter\save\SharedPreferencesUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */