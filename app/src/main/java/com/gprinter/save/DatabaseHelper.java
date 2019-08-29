/*    */ package com.gprinter.save;
/*    */ 
/*    */ import android.content.ContentValues;
/*    */ import android.content.Context;
/*    */ import android.database.sqlite.SQLiteDatabase;
/*    */ import android.database.sqlite.SQLiteDatabase.CursorFactory;
/*    */ import android.database.sqlite.SQLiteOpenHelper;
/*    */ 
/*    */ public class DatabaseHelper extends SQLiteOpenHelper
/*    */ {
/*    */   private static final int VERSION = 4;
/*    */   private static final String TABLE_PORT_PARAM = "CREATE TABLE IF NOT EXISTS portparam(id INTEGER,open INTEGER,porttype INTEGER, btaddr VARCHAR, usbname VARCHAR, ip VARCHAR, port INTEGER)";
/*    */   private static final String TABLE_PRINTER_NAME = "create table if not exists printername(id INTEGER, name VARCHAR(20) )";
/*    */   
/*    */   public DatabaseHelper(Context context, String name, CursorFactory factory, int version)
/*    */   {
/* 17 */     super(context, name, factory, version);
/*    */   }
/*    */   
/*    */   public DatabaseHelper(Context context, String name, int version)
/*    */   {
/* 22 */     super(context, name, null, version);
/*    */   }
/*    */   
/*    */   public DatabaseHelper(Context context, String name)
/*    */   {
/* 27 */     super(context, name, null, 4);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void onCreate(SQLiteDatabase db)
/*    */   {
/* 34 */     System.out.println("create a database");
/* 35 */     db.execSQL("CREATE TABLE IF NOT EXISTS portparam(id INTEGER,open INTEGER,porttype INTEGER, btaddr VARCHAR, usbname VARCHAR, ip VARCHAR, port INTEGER)");
/* 36 */     db.execSQL("create table if not exists printername(id INTEGER, name VARCHAR(20) )");
/* 37 */     for (int i = 0; i < 20; i++) {
/* 38 */       ContentValues contentValues = new ContentValues();
/* 39 */       contentValues.put("id", Integer.valueOf(i));
/* 40 */       contentValues.put("name", "");
/* 41 */       db.insert("printername", null, contentValues);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
/*    */   {
/* 48 */     System.out.println("upgrade a database");
/* 49 */     switch (oldVersion) {
/*    */     case 1: 
/* 51 */       db.execSQL("CREATE TABLE IF NOT EXISTS portparam(id INTEGER,open INTEGER,porttype INTEGER, btaddr VARCHAR, usbname VARCHAR, ip VARCHAR, port INTEGER)");
/*    */     case 3: 
/* 53 */       db.execSQL("create table if not exists printername(id INTEGER, name VARCHAR(20) )");
/* 54 */       for (int i = 0; i < 20; i++) {
/* 55 */         ContentValues contentValues = new ContentValues();
/* 56 */         contentValues.put("id", Integer.valueOf(i));
/* 57 */         contentValues.put("name", "");
/* 58 */         db.insert("printername", null, contentValues);
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\jame\Desktop\gprintersdkv22.jar!\com\gprinter\save\DatabaseHelper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */