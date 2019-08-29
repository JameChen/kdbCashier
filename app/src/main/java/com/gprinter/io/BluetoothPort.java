/*     */ package com.gprinter.io;
/*     */ 
/*     */

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.gprinter.command.GpCom;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
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
/*     */ public class BluetoothPort
/*     */   extends GpPort
/*     */ {
/*     */   private static final String DEBUG_TAG = "BluetoothService";
/*  27 */   private static final UUID SERIAL_PORT_SERVICE_CLASS_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
/*     */   
/*  29 */   private BluetoothAdapter mAdapter = null;
/*  30 */   private ConnectThread mConnectThread = null;
/*  31 */   private ConnectedThread mConnectedThread = null;
/*  32 */   BluetoothDevice mDevice = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BluetoothPort(int id, BluetoothDevice device, Handler handler)
/*     */   {
/*  43 */     this.mAdapter = BluetoothAdapter.getDefaultAdapter();
/*  44 */     this.mState = 0;
/*  45 */     this.mHandler = handler;
/*  46 */     this.mDevice = device;
/*  47 */     this.mPrinterId = id;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void connect()
/*     */   {
/*  57 */     Log.d("BluetoothService", "connect to: " + this.mDevice);
/*     */     
/*  59 */     if (this.mConnectThread != null) {
/*  60 */       this.mConnectThread.cancel();
/*  61 */       this.mConnectThread = null;
/*     */     }
/*     */     
/*  64 */     if (this.mConnectedThread != null) {
/*  65 */       this.mConnectedThread.cancel();
/*  66 */       this.mConnectedThread = null;
/*     */     }
/*     */     
/*  69 */     this.mConnectThread = new ConnectThread(this.mDevice);
/*  70 */     this.mConnectThread.start();
/*  71 */     setState(2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void connected(BluetoothSocket socket, BluetoothDevice device)
/*     */   {
/*  83 */     Log.d("BluetoothService", "connected");
/*     */     
/*  85 */     if (this.mConnectThread != null) {
/*  86 */       this.mConnectThread.cancel();
/*  87 */       this.mConnectThread = null;
/*     */     }
/*     */     
/*  90 */     if (this.mConnectedThread != null) {
/*  91 */       this.mConnectedThread.cancel();
/*  92 */       this.mConnectedThread = null;
/*     */     }
/*     */     
/*  95 */     this.mConnectedThread = new ConnectedThread(socket);
/*  96 */     this.mConnectedThread.start();
/*     */     
/*  98 */     Message msg = this.mHandler.obtainMessage(4);
/*  99 */     Bundle bundle = new Bundle();
/* 100 */     bundle.putInt("printer.id", this.mPrinterId);
/* 101 */     bundle.putString("device_name", device.getName());
/* 102 */     msg.setData(bundle);
/* 103 */     this.mHandler.sendMessage(msg);
/* 104 */     setState(3);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public synchronized void stop()
/*     */   {
/* 111 */     Log.d("BluetoothService", "stop");
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
/*     */     //ConnectedThread r;
/* 133 */     retval = r.writeDataImmediately(data);
/* 134 */     return retval;
/*     */   }
/*     */   
/*     */ 
/*     */   private class ConnectThread
/*     */     extends Thread
/*     */   {
/*     */     private BluetoothSocket mmSocket;
/*     */     
/*     */     private final BluetoothDevice mmDevice;
/*     */     
/*     */     public ConnectThread(BluetoothDevice device)
/*     */     {
/* 147 */       this.mmDevice = device;
/* 148 */       BluetoothSocket tmp = null;
/*     */       
/*     */       try
/*     */       {
/* 152 */         tmp = device.createRfcommSocketToServiceRecord(BluetoothPort.SERIAL_PORT_SERVICE_CLASS_UUID);
/*     */       } catch (IOException e) {
/* 154 */         Log.e("BluetoothService", "create() failed", e);
/*     */       }
/* 156 */       this.mmSocket = tmp;
/*     */     }
/*     */     
/*     */     public void run()
/*     */     {
/* 161 */       Log.i("BluetoothService", "BEGIN mConnectThread");
/* 162 */       setName("ConnectThread");
/*     */       
/* 164 */       BluetoothPort.this.mAdapter.cancelDiscovery();
/*     */       
/*     */ 
/*     */       try
/*     */       {
/* 169 */         this.mmSocket.connect();
/*     */       } catch (IOException e) {
/* 171 */         BluetoothPort.this.connectionFailed();
/*     */         try
/*     */         {
/* 174 */           if (this.mmSocket != null) {
/* 175 */             this.mmSocket.close();
/*     */           }
/*     */         } catch (IOException e2) {
/* 178 */           Log.e("BluetoothService", "unable to close() socket during connection failure", e2);
/*     */         }
/*     */         
/* 181 */         BluetoothPort.this.stop();
/* 182 */         return;
/*     */       }
/*     */       
/* 185 */       synchronized (BluetoothPort.this) {
/* 186 */         BluetoothPort.this.mConnectThread = null;
/*     */       }
/*     */       
/* 189 */       BluetoothPort.this.connected(this.mmSocket, this.mmDevice);
/*     */     }
/*     */     
/*     */     public void cancel() {
/*     */       try {
/* 194 */         if (this.mmSocket != null) {
/* 195 */           this.mmSocket.close();
/*     */         }
/* 197 */         this.mmSocket = null;
/*     */       } catch (IOException e) {
/* 199 */         Log.e("BluetoothService", "close() of connect socket failed", e);
/* 200 */         BluetoothPort.this.closePortFailed();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private class ConnectedThread
/*     */     extends Thread
/*     */   {
/*     */     private final BluetoothSocket mmSocket;
/*     */     private final InputStream mmInStream;
/*     */     private final OutputStream mmOutStream;
/*     */     
/*     */     public ConnectedThread(BluetoothSocket socket)
/*     */     {
/* 215 */       Log.d("BluetoothService", "create ConnectedThread");
/* 216 */       this.mmSocket = socket;
/* 217 */       InputStream tmpIn = null;
/* 218 */       OutputStream tmpOut = null;
/*     */       try
/*     */       {
/* 221 */         tmpIn = socket.getInputStream();
/* 222 */         tmpOut = socket.getOutputStream();
/*     */       } catch (IOException e) {
/* 224 */         Log.e("BluetoothService", "temp sockets not created", e);
/*     */       }
/* 226 */       this.mmInStream = tmpIn;
/* 227 */       this.mmOutStream = tmpOut;
/*     */     }
/*     */     
/*     */     public void run()
/*     */     {
/* 232 */       Log.i("BluetoothService", "BEGIN mConnectedThread");
/*     */       
/* 234 */       while (!BluetoothPort.this.mClosePort) {
/*     */         try {
/* 236 */           byte[] buffer = new byte[1024];//64
/*     */           
/* 238 */           int bytes = this.mmInStream.read(buffer);
/* 239 */           Message msg = new Message();
/* 240 */           msg.what = 2;
/* 241 */           Bundle bundle = new Bundle();
/* 242 */           bundle.putInt("printer.id", BluetoothPort.this.mPrinterId);
/* 243 */           bundle.putInt("device.readcnt", bytes);
/* 244 */           bundle.putByteArray("device.read", buffer);
/* 245 */           msg.setData(bundle);
/* 246 */           BluetoothPort.this.mHandler.sendMessage(msg);
/* 247 */           Log.d("BluetoothService", bytes + " 的长度");
/*     */         } catch (IOException e) {
/* 249 */           cancel();
/* 250 */           BluetoothPort.this.connectionLost();
/* 251 */           e.printStackTrace();
/* 252 */           Log.e("BluetoothService", "disconnected", e);
/*     */         }
/*     */       }
/* 255 */       Log.d("BluetoothService", "Closing Bluetooth work");
/*     */     }
/*     */     
/*     */     public void cancel() {
/*     */       try {
/* 260 */         BluetoothPort.this.mClosePort = true;
/* 261 */         this.mmOutStream.flush();
/* 262 */         if (this.mmSocket != null) {
/* 263 */           this.mmSocket.close();
/*     */         }
/*     */       } catch (IOException e) {
/* 266 */         BluetoothPort.this.closePortFailed();
/*     */       }
/*     */     }
/*     */     
/*     */     public GpCom.ERROR_CODE writeDataImmediately(Vector<Byte> data) {
/* 271 */       GpCom.ERROR_CODE retval = GpCom.ERROR_CODE.SUCCESS;
/* 272 */       if ((this.mmSocket != null) && (this.mmOutStream != null)) {
/* 273 */         if ((data != null) && (data.size() > 0)) {
/* 274 */           byte[] sendData = new byte[data.size()];
/* 275 */           if (data.size() > 0) {
/* 276 */             for (int i = 0; i < data.size(); i++) {
/* 277 */               sendData[i] = ((Byte)data.get(i)).byteValue();
/*     */             }
/*     */             try {
/* 280 */               this.mmOutStream.write(sendData);
/* 281 */               this.mmOutStream.flush();
/*     */             } catch (Exception e) {
/* 283 */               Log.d("BluetoothService", "Exception occured while sending data immediately: " + e.getMessage());
/* 284 */               retval = GpCom.ERROR_CODE.FAILED;
/*     */             }
/*     */           }
/*     */         }
/*     */       } else {
/* 289 */         retval = GpCom.ERROR_CODE.PORT_IS_NOT_OPEN;
/*     */       }
/* 291 */       return retval;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\jame\Desktop\gprintersdkv22.jar!\com\gprinter\io\BluetoothPort.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */