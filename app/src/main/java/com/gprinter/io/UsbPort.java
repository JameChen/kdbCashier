/*     */ package com.gprinter.io;
/*     */ 
/*     */

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.gprinter.command.GpCom;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UsbPort
/*     */   extends GpPort
/*     */ {
/*     */   private static final String DEBUG_TAG = "UsbPortService";
/*     */   public static final String ACTION_USB_DEVICE_ATTACHED = "com.example.ACTION_USB_DEVICE_ATTACHED";
/*     */   private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
/*     */   private static final String USB_PRINTER_NAME = "Gprinter";
/*     */   private String mUsbDeviceName;
/*     */   private UsbManager mUsbManager;
/*  34 */   private ConnectThread mConnectThread = null;
/*  35 */   private ConnectedThread mConnectedThread = null;
/*  36 */   private Context mContext = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public UsbPort(Context context, int id, String name, Handler handler)
/*     */   {
/*  47 */     this.mPrinterId = id;
/*  48 */     this.mState = 0;
/*  49 */     this.mHandler = handler;
/*  50 */     this.mUsbDeviceName = name;
/*  51 */     this.mContext = context;
/*  52 */     this.mUsbManager = ((UsbManager)context.getSystemService("usb"));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void connect()
/*     */   {
/*  60 */     Log.d("UsbPortService", "connect to usb device ");
/*     */     
/*  62 */     if (this.mConnectThread != null) {
/*  63 */       this.mConnectThread.cancel();
/*  64 */       this.mConnectThread = null;
/*     */     }
/*     */     
/*  67 */     if (this.mConnectedThread != null) {
/*  68 */       this.mConnectedThread.cancel();
/*  69 */       this.mConnectedThread = null;
/*     */     }
/*     */     
/*  72 */     this.mConnectThread = new ConnectThread(this.mUsbDeviceName);
/*  73 */     this.mConnectThread.start();
/*  74 */     setState(2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public synchronized void connected(UsbDeviceConnection connection, UsbInterface intf)
/*     */   {
/*  81 */     Log.d("UsbPortService", "connected");
/*     */     
/*  83 */     if (this.mConnectThread != null) {
/*  84 */       this.mConnectThread.cancel();
/*  85 */       this.mConnectThread = null;
/*     */     }
/*     */     
/*  88 */     if (this.mConnectedThread != null) {
/*  89 */       this.mConnectedThread.cancel();
/*  90 */       this.mConnectedThread = null;
/*     */     }
/*     */     
/*  93 */     this.mConnectedThread = new ConnectedThread(connection, intf);
/*  94 */     this.mConnectedThread.start();
/*     */     
/*     */ 
/*  97 */     Message msg = this.mHandler.obtainMessage(4);
/*  98 */     Bundle bundle = new Bundle();
/*  99 */     bundle.putInt("printer.id", this.mPrinterId);
/* 100 */     bundle.putString("device_name", "Gprinter");
/* 101 */     msg.setData(bundle);
/* 102 */     this.mHandler.sendMessage(msg);
/* 103 */     setState(3);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void stop()
/*     */   {
/* 111 */     Log.d("UsbPortService", "stop");
/* 112 */     setState(0);
/* 113 */     if (this.mConnectThread != null) {
/* 114 */       this.mConnectThread.cancel();
/* 115 */       this.mConnectThread = null;
/*     */     }
/* 117 */     if (this.mConnectedThread != null) {
/* 118 */       this.mConnectedThread.cancel();
/* 119 */       this.mConnectedThread = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public GpCom.ERROR_CODE writeDataImmediately(Vector<Byte> data) {
/* 124 */     GpCom.ERROR_CODE retval = GpCom.ERROR_CODE.SUCCESS;
/*     */     
/*     */     ConnectedThread r;
/*     */     
/* 128 */     synchronized (this) {
/* 129 */       if (this.mState != 3)
/* 130 */         return GpCom.ERROR_CODE.PORT_IS_NOT_OPEN;
/* 131 */       r = this.mConnectedThread; }
/* 133 */     retval = r.writeDataImmediately(data);
/* 134 */     return retval;
/*     */   }
/*     */   
/*     */   boolean checkUsbDevicePidVid(UsbDevice dev) {
/* 138 */     int pid = dev.getProductId();
/* 139 */     int vid = dev.getVendorId();
/* 140 */     boolean rel = false;
/* 141 */     if (((vid == 34918) && (pid == 256)) || ((vid == 1137) && (pid == 85)) || ((vid == 6790) && (pid == 30084)) || 
/* 142 */       ((vid == 26728) && (pid == 256)) || ((vid == 26728) && (pid == 512)) || ((vid == 26728) && (pid == 256)) || 
/* 143 */       ((vid == 26728) && (pid == 768)) || ((vid == 26728) && (pid == 1024)) || ((vid == 26728) && (pid == 1280)) || 
/* 144 */       ((vid == 26728) && (pid == 1536)) || ((vid == 7358) && (pid == 2))) {
/* 145 */       rel = true;
/*     */     }
/* 147 */     return rel;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private class ConnectThread
/*     */     extends Thread
/*     */   {
/* 156 */     private UsbDevice mmUSBDevice = null;
/* 157 */     private String mmDeviceName = null;
/* 158 */     private UsbDeviceConnection mmConnection = null;
/*     */     private UsbInterface mmIntf;
/*     */     
/*     */     public ConnectThread(String devicename) {
/* 162 */       this.mmDeviceName = devicename;
/* 163 */       this.mmUSBDevice = null;
/* 164 */       this.mmConnection = null;
/* 165 */       this.mmIntf = null;
/*     */     }
/*     */     
/*     */     public void run()
/*     */     {
/* 170 */       Log.i("UsbPortService", "BEGIN mConnectThread");
/* 171 */       setName("ConnectThread");
/* 172 */       this.mmUSBDevice = null;
/* 173 */       HashMap<String, UsbDevice> usbDeviceList = UsbPort.this.mUsbManager.getDeviceList();
/* 174 */       if (!this.mmDeviceName.equals("")) {
/* 175 */         Log.d("UsbPortService", "UsbDeviceName not empty. Trying to open it...");
/* 176 */         this.mmUSBDevice = ((UsbDevice)usbDeviceList.get(this.mmDeviceName));
/*     */       } else {
/* 178 */         Log.d("UsbPortService", "PortName is empty. Trying to find Gp device...");
/* 179 */         Iterator<String> deviceIterator = usbDeviceList.keySet().iterator();
/* 180 */         while (deviceIterator.hasNext()) {
/* 181 */           String deviceName = (String)deviceIterator.next();
/* 182 */           UsbDevice device = (UsbDevice)usbDeviceList.get(deviceName);
/* 183 */           if (UsbPort.this.checkUsbDevicePidVid(device)) {
/* 184 */             this.mmUSBDevice = device;
/* 185 */             break;
/*     */           }
/*     */         }
/*     */       }
/* 189 */       if (this.mmUSBDevice != null) {
/* 190 */         if (!UsbPort.this.mUsbManager.hasPermission(this.mmUSBDevice)) {
/* 191 */           IntentFilter UsbPermissionIntentFilter = new IntentFilter("com.android.example.USB_PERMISSION");
/* 192 */           UsbPort.this.mContext.registerReceiver(UsbPort.this.mUsbPermissionReceiver, UsbPermissionIntentFilter);
/* 193 */           UsbDevice dev = this.mmUSBDevice;
/* 194 */           this.mmUSBDevice = null;
/* 195 */           PendingIntent pi = PendingIntent.getBroadcast(UsbPort.this.mContext, 0, new Intent("com.android.example.USB_PERMISSION"), 0);
/* 196 */           if (UsbPort.this.checkUsbDevicePidVid(dev)) {
/* 197 */             UsbPort.this.mUsbManager.requestPermission(dev, pi);
/*     */           }
/*     */         } else {
/* 200 */           int count = this.mmUSBDevice.getInterfaceCount();
/* 201 */           UsbInterface intf = null;
/* 202 */           for (int i = 0; i < count; i++) {
/* 203 */             intf = this.mmUSBDevice.getInterface(i);
/* 204 */             if (intf.getInterfaceClass() == 7) {
/*     */               break;
/*     */             }
/*     */           }
/* 208 */           if (intf != null) {
/* 209 */             this.mmIntf = intf;
/* 210 */             this.mmConnection = null;
/* 211 */             this.mmConnection = UsbPort.this.mUsbManager.openDevice(this.mmUSBDevice);
/*     */             
/* 213 */             if (this.mmConnection != null) {
/* 214 */               synchronized (UsbPort.this) {
/* 215 */                 UsbPort.this.mConnectThread = null;
/*     */               }
/*     */               
/* 218 */               UsbPort.this.connected(this.mmConnection, this.mmIntf);
/*     */             } else {
/* 220 */               UsbPort.this.connectionToPrinterFailed();
/* 221 */               UsbPort.this.stop();
/*     */             }
/*     */           } else {
/* 224 */             UsbPort.this.connectionFailed();
/* 225 */             UsbPort.this.stop();
/*     */           }
/*     */         }
/*     */       } else {
/* 229 */         Log.e("UsbPortService", "Cannot find usb device");
/* 230 */         UsbPort.this.stop();
/*     */       }
/*     */     }
/*     */     
/*     */     public void cancel()
/*     */     {
/* 236 */       if (this.mmConnection != null) {
/* 237 */         this.mmConnection.releaseInterface(this.mmIntf);
/* 238 */         this.mmConnection.close();
/*     */       }
/* 240 */       this.mmConnection = null;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private class ConnectedThread
/*     */     extends Thread
/*     */   {
/*     */     UsbDeviceConnection mmConnection;
/*     */     
/*     */     UsbInterface mmIntf;
/* 251 */     private UsbEndpoint mmEndIn = null;
/* 252 */     private UsbEndpoint mmEndOut = null;
/*     */     
/*     */     public ConnectedThread(UsbDeviceConnection Connection, UsbInterface Intf) {
/* 255 */       Log.d("UsbPortService", "create ConnectedThread");
/* 256 */       this.mmConnection = Connection;
/* 257 */       this.mmIntf = Intf;
/* 258 */       Log.i("UsbPortService", "BEGIN mConnectedThread");
/* 259 */       int i; if (this.mmConnection.claimInterface(this.mmIntf, true)) {
/* 260 */         for (i = 0; i < this.mmIntf.getEndpointCount(); i++) {
/* 261 */           UsbEndpoint ep = this.mmIntf.getEndpoint(i);
/* 262 */           if (ep.getType() == 2) {
/* 263 */             if (ep.getDirection() == 0) {
/* 264 */               this.mmEndOut = ep;
/*     */             } else {
/* 266 */               this.mmEndIn = ep;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */     public void run()
/*     */     {
/* 275 */       if ((this.mmEndOut != null) && (this.mmEndIn != null))
/*     */       {
/* 277 */         UsbPort.this.mClosePort = false;
/*     */         
/* 279 */         while (!UsbPort.this.mClosePort) {
/*     */           try {
/* 281 */             byte[] ReceiveData = new byte[100];
/* 282 */             int bytes = this.mmConnection.bulkTransfer(this.mmEndIn, ReceiveData, ReceiveData.length, 200);
/* 283 */             if (bytes > 0)
/*     */             {
/* 285 */               Message msg = UsbPort.this.mHandler.obtainMessage(2);
/* 286 */               Bundle bundle = new Bundle();
/* 287 */               bundle.putInt("printer.id", UsbPort.this.mPrinterId);
/* 288 */               bundle.putInt("device.readcnt", bytes);
/* 289 */               bundle.putByteArray("device.read", ReceiveData);
/* 290 */               msg.setData(bundle);
/* 291 */               UsbPort.this.mHandler.sendMessage(msg);
/*     */             }
/* 293 */             Thread.sleep(30L);
/*     */           } catch (InterruptedException e) {
/* 295 */             UsbPort.this.connectionLost();
/* 296 */             break;
/*     */           }
/*     */         }
/* 299 */         Log.d("UsbPortService", "Closing Usb work");
/*     */       } else {
/* 301 */         UsbPort.this.stop();
/* 302 */         UsbPort.this.connectionLost();
/*     */       }
/*     */     }
/*     */     
/*     */     public void cancel() {
/* 307 */       UsbPort.this.mClosePort = true;
/* 308 */       this.mmConnection.releaseInterface(this.mmIntf);
/* 309 */       this.mmConnection.close();
/* 310 */       this.mmConnection = null;
/*     */     }
/*     */     
/*     */     public GpCom.ERROR_CODE writeDataImmediately(Vector<Byte> data) {
/* 314 */       GpCom.ERROR_CODE retval = GpCom.ERROR_CODE.SUCCESS;
/* 315 */       if ((data != null) && (data.size() > 0)) {
/* 316 */         byte[] sendData = new byte[data.size()];
/* 317 */         for (int i = 0; i < data.size(); i++) {
/* 318 */           sendData[i] = ((Byte)data.get(i)).byteValue();
/*     */         }
/*     */         try {
/* 321 */           int result = this.mmConnection.bulkTransfer(this.mmEndOut, sendData, sendData.length, 500);
/* 322 */           if (result >= 0) {
/* 323 */             Log.d("UsbPortService", "send success");
/*     */           }
/*     */         } catch (Exception e) {
/* 326 */           Log.d("UsbPortService", "Exception occured while sending data immediately: " + e.getMessage());
/* 327 */           retval = GpCom.ERROR_CODE.FAILED;
/*     */         }
/*     */       }
/* 330 */       return retval;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/* 335 */   private final BroadcastReceiver mUsbPermissionReceiver = new BroadcastReceiver() {
/*     */     public void onReceive(Context context, Intent intent) {
/* 337 */       String action = intent.getAction();
/* 338 */       if ("com.android.example.USB_PERMISSION".equals(action)) {
/* 339 */         synchronized (this) {
/* 340 */           UsbDevice device = (UsbDevice)intent.getParcelableExtra("device");
/*     */           
/* 342 */           if (intent.getBooleanExtra("permission", false)) {
/* 343 */             if (device != null) {
/* 344 */               Log.d("UsbPortService", "permission ok for device " + device);
/* 345 */               UsbPort.this.connect();
/*     */             }
/*     */           } else {
/* 348 */             Log.d("UsbPortService", "permission denied for device " + device);
/* 349 */             UsbPort.this.stop();
/*     */           }
/*     */           
/* 352 */           UsbPort.this.mContext.unregisterReceiver(this);
/*     */         }
/*     */       }
/*     */     }
/*     */   };
/*     */ }


/* Location:              C:\Users\jame\Desktop\gprintersdkv22.jar!\com\gprinter\io\UsbPort.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */