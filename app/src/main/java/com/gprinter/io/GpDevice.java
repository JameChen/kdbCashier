/*     */ package com.gprinter.io;
/*     */ 
/*     */

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.gprinter.command.GpCom;

import java.net.InetAddress;
import java.util.LinkedList;
import java.util.Queue;
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
/*     */ public class GpDevice
/*     */ {
/*     */   private static final String DEBUG_TAG = "GpDevice";
/*     */   public static final String CONNECT_ERROR = "connect error";
/*  28 */   private BluetoothAdapter mBluetoothAdapter = null;
/*  29 */   private GpPort mPort = null;
/*     */   
/*     */   public static final int STATE_NONE = 0;
/*     */   
/*     */   public static final int STATE_LISTEN = 1;
/*     */   
/*     */   public static final int STATE_CONNECTING = 2;
/*     */   
/*     */   public static final int STATE_CONNECTED = 3;
/*     */   
/*     */   public static final int STATE_INVALID_PRINTER = 4;
/*     */   
/*     */   public static final int STATE_VALID_PRINTER = 5;
/*     */   public static final int MESSAGE_STATE_CHANGE = 1;
/*     */   public static final int MESSAGE_READ = 2;
/*     */   public static final int MESSAGE_WRITE = 3;
/*     */   public static final int MESSAGE_DEVICE_NAME = 4;
/*     */   public static final int MESSAGE_TOAST = 5;
/*     */   public static final int MESSAGE_OFFLINE_STATUS = 6;
/*     */   public static final String DEVICE_NAME = "device_name";
/*     */   public static final String TOAST = "toast";
/*     */   public static final String PRINTER_ID = "printer.id";
/*     */   public static final String DEVICE_STATUS = "device_status";
/*     */   public static final String DEVICE_READ = "device.read";
/*     */   public static final String DEVICE_READ_CNT = "device.readcnt";
/*  54 */   private PortParameters mPortParam = null;
/*  55 */   private int mCommandType = 0;
/*  56 */   public static Queue<Integer> mReceiveQueue = new LinkedList();
/*  57 */   private boolean mReceiveDataEnable = false;
/*     */   
/*     */   public GpDevice() {
/*  60 */     this.mBluetoothAdapter = null;
/*  61 */     this.mPort = null;
/*  62 */     this.mPortParam = new PortParameters();
/*  63 */     this.mCommandType = 0;
/*  64 */     this.mReceiveDataEnable = false;
/*     */   }
/*     */   
/*     */   public void setCommandType(int command) {
/*  68 */     this.mCommandType = command;
/*     */   }
/*     */   
/*     */   public int getCommandType() {
/*  72 */     return this.mCommandType;
/*     */   }
/*     */   
/*     */   public PortParameters getPortParameters() {
/*  76 */     return this.mPortParam;
/*     */   }
/*     */   
/*     */   public void setReceiveDataEnable(boolean b) {
/*  80 */     this.mReceiveDataEnable = b;
/*     */   }
/*     */   
/*     */   public boolean getReceiveDataEnable() {
/*  84 */     return this.mReceiveDataEnable;
/*     */   }
/*     */   
/*     */   public int getConnectState() {
/*  88 */     int state = 0;
/*  89 */     if (this.mPort != null) {
/*  90 */       Log.d("GpDevice", "getConnectState ");
/*  91 */       state = this.mPort.getState();
/*     */     }
/*  93 */     return state;
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
/*     */   public GpCom.ERROR_CODE openEthernetPort(int id, String ip, int port, Handler mHandler)
/*     */   {
/* 106 */     GpCom.ERROR_CODE retval = GpCom.ERROR_CODE.SUCCESS;
/* 107 */     this.mPortParam.setPortType(3);
/* 108 */     this.mPortParam.setIpAddr(ip);
/* 109 */     this.mPortParam.setPortNumber(port);
/*     */     
/* 111 */     if (mHandler == null) {
/* 112 */       Log.e("GpDevice", "Parameters is invalid");
/* 113 */       retval = GpCom.ERROR_CODE.INVALID_DEVICE_PARAMETERS;
/*     */     }
/* 115 */     else if (port <= 0) {
/* 116 */       Log.e("GpDevice", "PortNumber is invalid");
/* 117 */       retval = GpCom.ERROR_CODE.INVALID_PORT_NUMBER;
/*     */     } else {
/* 119 */       if (ip.length() != 0) {
/*     */         try {
/* 121 */           InetAddress.getByName(ip);
/* 122 */           if (this.mPort != null) {
/* 123 */             if (this.mPort.getState() != 3) {
/* 124 */               Log.e("GpDevice", "UsbPort is open already, try to closing port");
/* 125 */               this.mPort.stop();
/* 126 */               this.mPort = null;
/*     */             } else {
/* 128 */               return GpCom.ERROR_CODE.DEVICE_ALREADY_OPEN;
/*     */             }
/*     */           }
/* 131 */           this.mPort = new EthernetPort(id, ip, port, mHandler);
/* 132 */           this.mPort.connect();
/*     */         } catch (Exception e) {
/* 134 */           Log.e("GpDevice", "IpAddress is invalid");
/* 135 */           retval = GpCom.ERROR_CODE.INVALID_IP_ADDRESS;
/*     */         }
/*     */       }
/* 138 */       Log.e("GpDevice", "IpAddress is invalid");
/* 139 */       retval = GpCom.ERROR_CODE.INVALID_IP_ADDRESS;
/*     */     }
/*     */     
/*     */ 
/* 143 */     return retval;
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
/*     */   public GpCom.ERROR_CODE openBluetoothPort(int id, String addr, Handler mHandler)
/*     */   {
/* 156 */     GpCom.ERROR_CODE retval = GpCom.ERROR_CODE.SUCCESS;
/* 157 */     this.mPortParam.setPortType(4);
/* 158 */     this.mPortParam.setBluetoothAddr(addr);
/*     */     
/* 160 */     if (mHandler == null) {
/* 161 */       Log.e("GpDevice", "Parameters is invalid");
/* 162 */       retval = GpCom.ERROR_CODE.INVALID_DEVICE_PARAMETERS;
/*     */     }
/*     */     else {
/* 165 */       this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
/*     */       
/* 167 */       if (this.mBluetoothAdapter == null) {
/* 168 */         retval = GpCom.ERROR_CODE.BLUETOOTH_IS_NOT_SUPPORT;
/* 169 */         Log.e("GpDevice", "Bluetooth is not support");
/*     */       }
/* 171 */       else if (!this.mBluetoothAdapter.isEnabled()) {
/* 172 */         retval = GpCom.ERROR_CODE.OPEN_BLUETOOTH;
/* 173 */         Log.e("GpDevice", "Bluetooth is not open");
/*     */       }
/* 175 */       else if (BluetoothAdapter.checkBluetoothAddress(addr)) {
/* 176 */         BluetoothDevice device = this.mBluetoothAdapter.getRemoteDevice(addr);
/* 177 */         if (this.mPort != null) {
/* 178 */           if (this.mPort.getState() != 3) {
/* 179 */             Log.e("GpDevice", "Bluetooth is open already, try to closing port");
/* 180 */             this.mPort.stop();
/* 181 */             this.mPort = null;
/*     */           } else {
/* 183 */             return GpCom.ERROR_CODE.DEVICE_ALREADY_OPEN;
/*     */           }
/*     */         }
/* 186 */         this.mPort = new BluetoothPort(id, device, mHandler);
/* 187 */         this.mPort.connect();
/*     */       } else {
/* 189 */         Log.e("GpDevice", "Bluetooth address is invalid");
/* 190 */         retval = GpCom.ERROR_CODE.INVALID_BLUETOOTH_ADDRESS;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 195 */     return retval;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public GpCom.ERROR_CODE openUSBPort(Context context, int id, String deviceName, Handler handler)
/*     */   {
/* 206 */     GpCom.ERROR_CODE retval = GpCom.ERROR_CODE.SUCCESS;
/* 207 */     this.mPortParam.setPortType(2);
/* 208 */     this.mPortParam.setUsbDeviceName(deviceName);
/* 209 */     if ((handler == null) || (context == null)) {
/* 210 */       retval = GpCom.ERROR_CODE.INVALID_DEVICE_PARAMETERS;
/* 211 */       Log.e("GpDevice", "Parameters is invalid");
/*     */     } else {
/* 213 */       if (this.mPort != null) {
/* 214 */         if (this.mPort.getState() != 3) {
/* 215 */           Log.e("GpDevice", "UsbPort is open already, try to closing port");
/* 216 */           this.mPort.stop();
/* 217 */           this.mPort = null;
/*     */         } else {
/* 219 */           return GpCom.ERROR_CODE.DEVICE_ALREADY_OPEN;
/*     */         }
/*     */       }
/* 222 */       Log.e("GpDevice", "openUSBPort id " + id);
/* 223 */       this.mPort = new UsbPort(context, id, deviceName, handler);
/* 224 */       this.mPort.connect();
/*     */     }
/* 226 */     return retval;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void closePort()
/*     */   {
/* 235 */     if (this.mPort != null) {
/* 236 */       this.mPort.stop();
/* 237 */       this.mPort = null;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public GpCom.ERROR_CODE sendDataImmediately(Vector<Byte> Command)
/*     */   {
/* 249 */     Vector<Byte> data = new Vector(Command.size());
/* 250 */     GpCom.ERROR_CODE retval; if (this.mPort != null) {
/* 251 */       if (this.mPort.getState() == 3) {
/* 252 */         for (int k = 0; k < Command.size(); k++) {
/* 253 */           if (data.size() >= 1024) {
/* 254 */            retval = this.mPort.writeDataImmediately(data);
/* 255 */             data.clear();
/* 256 */             if (retval != GpCom.ERROR_CODE.SUCCESS) {
/* 257 */               return retval;
/*     */             }
/*     */           }
/* 260 */           data.add((Byte)Command.get(k));
/*     */         }
/* 262 */         retval = this.mPort.writeDataImmediately(data);
/* 263 */         Log.i("GpDevice", "retval = " + retval);
/*     */       } else {
/* 265 */         retval = GpCom.ERROR_CODE.PORT_IS_DISCONNECT;
/* 266 */         Log.e("GpDevice", "Port is disconnect");
/*     */       }
/*     */     } else {
/* 269 */       retval = GpCom.ERROR_CODE.PORT_IS_NOT_OPEN;
/* 270 */       Log.e("GpDevice", "Port is not open");
/*     */     }
/* 272 */     return retval;
/*     */   }
/*     */ }


/* Location:              C:\Users\jame\Desktop\gprintersdkv22.jar!\com\gprinter\io\GpDevice.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */