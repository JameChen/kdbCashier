//package com.yiku.kdb_flat.model.bean;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.util.Log;
//
//import com.gprinter.io.PortParameters;
//import com.gprinter.save.DatabaseHelper;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by jame on 2017/10/27.
// */
//
//public class PortParamDataBase1 {
//    Context context;
//    DatabaseHelper dbHelper = null;
//    private static final String DEBUG_TAG = "LabelDataBase";
//    private static final String PORT_PARAM_DATABASE = "GpLink_port_db1";
//    private static final String TABLE_PORT_PARAM = "portparam";
//    private static final String[] PORT_PARAM_QUERY = new String[]{"id", "open", "porttype", "btaddr", "usbname", "ip", "port"};
//    public PortParamDataBase1(Context context) {
//        this.context = context;
//        this.dbHelper = new DatabaseHelper(this.context, "GpLink_port_db1");
//    }
//
//    public void updataDatabase(int version) {
//        this.dbHelper = new DatabaseHelper(this.context, "GpLink_port_db1", version);
//    }
//
//    public long insertPortParam(int id, PortParameters param) {
//        ContentValues values = new ContentValues();
//        Log.i("LabelDataBase", "insertPortParam");
//        values.put("id", Integer.valueOf(id));
//        values.put("open", Boolean.valueOf(param.getPortOpenState()));
//        values.put("porttype", Integer.valueOf(param.getPortType()));
//        values.put("btaddr", param.getBluetoothAddr());
//        values.put("usbname", param.getUsbDeviceName());
//        values.put("ip", param.getIpAddr());
//        values.put("port", Integer.valueOf(param.getPortNumber()));
//        SQLiteDatabase sqliteDatabase = this.dbHelper.getWritableDatabase();
//        return sqliteDatabase.insert("portparam", (String)null, values);
//    }
//
//    public void modifyPortParam(String id, PortParameters param) {
//        Log.i("LabelDataBase", "modifyPortParam");
//        ContentValues values = new ContentValues();
//        values.put("open", Boolean.valueOf(param.getPortOpenState()));
//        SQLiteDatabase sqliteDatabase = this.dbHelper.getWritableDatabase();
//        sqliteDatabase.update("portparam", values, "id=?", new String[]{id});
//    }
//    public List<PortParameters> queryAllPortParamDataBase() {
//
//        SQLiteDatabase sqliteDatabase = this.dbHelper.getReadableDatabase();
//        Log.i("LabelDataBase", "queryPortParam");
//        Cursor cursor = sqliteDatabase.query("portparam", PORT_PARAM_QUERY,null, null, (String)null, (String)null, (String)null);
//        List<PortParameters> list=new ArrayList<PortParameters>();
//        while(cursor.moveToNext()) {
//            PortParameters p = new PortParameters();
//            p.setPortType(cursor.getInt(cursor.getColumnIndex("porttype")));
//            int i = cursor.getInt(cursor.getColumnIndex("open"));
//            if(i == 0) {
//                p.setPortOpenState(false);
//            } else {
//                p.setPortOpenState(true);
//            }
//
//            p.setBluetoothAddr(cursor.getString(cursor.getColumnIndex("btaddr")));
//            p.setUsbDeviceName(cursor.getString(cursor.getColumnIndex("usbname")));
//            p.setIpAddr(cursor.getString(cursor.getColumnIndex("ip")));
//            p.setPortNumber(cursor.getInt(cursor.getColumnIndex("port")));
//            Log.i("LabelDataBase", "id " + cursor.getInt(cursor.getColumnIndex("id")));
//            Log.i("LabelDataBase", "PortOpen " + p.getPortOpenState());
//            Log.i("LabelDataBase", "PortType " + p.getPortType());
//            Log.i("LabelDataBase", "BluetoothAddr " + p.getBluetoothAddr());
//            Log.i("LabelDataBase", "UsbName " + p.getUsbDeviceName());
//            Log.i("LabelDataBase", "Ip " + p.getIpAddr());
//            Log.i("LabelDataBase", "Port " + p.getPortNumber());
//            list.add(p);
//        }
//
//        return list;
//    }
//
//    public PortParameters queryPortParamDataBase(String id) {
//        PortParameters p = new PortParameters();
//        SQLiteDatabase sqliteDatabase = this.dbHelper.getReadableDatabase();
//        Log.i("LabelDataBase", "queryPortParam");
//        Cursor cursor = sqliteDatabase.query("portparam", PORT_PARAM_QUERY, "id=?", new String[]{id}, (String)null, (String)null, (String)null);
//
//        while(cursor.moveToNext()) {
//            p.setPortType(cursor.getInt(cursor.getColumnIndex("porttype")));
//            int i = cursor.getInt(cursor.getColumnIndex("open"));
//            if(i == 0) {
//                p.setPortOpenState(false);
//            } else {
//                p.setPortOpenState(true);
//            }
//
//            p.setBluetoothAddr(cursor.getString(cursor.getColumnIndex("btaddr")));
//            p.setUsbDeviceName(cursor.getString(cursor.getColumnIndex("usbname")));
//            p.setIpAddr(cursor.getString(cursor.getColumnIndex("ip")));
//            p.setPortNumber(cursor.getInt(cursor.getColumnIndex("port")));
//            Log.i("LabelDataBase", "id " + cursor.getInt(cursor.getColumnIndex("id")));
//            Log.i("LabelDataBase", "PortOpen " + p.getPortOpenState());
//            Log.i("LabelDataBase", "PortType " + p.getPortType());
//            Log.i("LabelDataBase", "BluetoothAddr " + p.getBluetoothAddr());
//            Log.i("LabelDataBase", "UsbName " + p.getUsbDeviceName());
//            Log.i("LabelDataBase", "Ip " + p.getIpAddr());
//            Log.i("LabelDataBase", "Port " + p.getPortNumber());
//        }
//
//        return p;
//    }
//
//    public void deleteDataBase(String id) {
//        SQLiteDatabase sqliteDatabase = this.dbHelper.getWritableDatabase();
//        int rel = sqliteDatabase.delete("portparam", "id=?", new String[]{id});
//        Log.i("LabelDataBase", "rel  " + rel);
//    }
//
//    public void insertPrinterName(int id, String name) {
//        SQLiteDatabase sqliteDatabase = this.dbHelper.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("id", Integer.valueOf(id));
//        contentValues.put("name", name);
//        long ret = (long)sqliteDatabase.update("printername", contentValues, "id=?", new String[]{String.valueOf(id)});
//        Log.d("----------------------", String.valueOf(ret));
//    }
//
//    public void deletePrinterName(String id) {
//        SQLiteDatabase sqliteDatabase = this.dbHelper.getWritableDatabase();
//        int rel = sqliteDatabase.delete("printername", "id=?", new String[]{id});
//        Log.i("LabelDataBase", "rel delete printer name " + rel);
//    }
//
//    public String readPrinterName(int id) {
//        SQLiteDatabase sqliteDatabase = this.dbHelper.getReadableDatabase();
//        Cursor cursor = sqliteDatabase.query("printername", new String[]{"id", "name"}, "id=?", new String[]{String.valueOf(id)}, (String)null, (String)null, (String)null);
//        String name = "";
//        if(cursor.moveToNext()) {
//            name = cursor.getString(1);
//        }
//
//        return name;
//    }
//
//    public void close() {
//        if(this.dbHelper != null) {
//            this.dbHelper.close();
//        }
//
//    }
//}
