/*     */ package com.gprinter.io;
/*     */ 
/*     */

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.gprinter.command.GpCom;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
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
/*     */ public class EthernetPort
/*     */   extends GpPort
/*     */ {
/*     */   private static final String DEBUG_TAG = "EthernetService";
/*     */   private String mIp;
/*     */   private int mPortNumber;
/*  28 */   private ConnectThread mConnectThread = null;
/*  29 */   private ConnectedThread mConnectedThread = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private ReachableThread mReachableThread;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public EthernetPort(int id, String Ip, int Port, Handler handler)
/*     */   {
/*  41 */     Log.e("EthernetService", "recreate Socket");
/*  42 */     this.mState = 0;
/*  43 */     this.mHandler = handler;
/*  44 */     this.mPortNumber = Port;
/*  45 */     this.mIp = Ip;
/*  46 */     this.mPrinterId = id;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void connect()
/*     */   {
/*  57 */     Log.d("EthernetService", "connect to Ip :" + this.mIp + " Port: " + this.mPortNumber);
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
/*  69 */     this.mConnectThread = new ConnectThread(this.mIp, this.mPortNumber);
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
/*     */   public synchronized void connected(Socket socket, String ip)
/*     */   {
/*  83 */     Log.d("EthernetService", "connected");
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
/*  94 */     if (this.mReachableThread != null) {
/*  95 */       this.mReachableThread.cancel();
/*  96 */       this.mReachableThread = null;
/*     */     }
/*     */     
/*  99 */     this.mConnectedThread = new ConnectedThread(socket);
/* 100 */     this.mConnectedThread.start();
/* 101 */     this.mReachableThread = new ReachableThread(socket);
/*     */     
/* 103 */     Message msg = this.mHandler.obtainMessage(4);
/* 104 */     Bundle bundle = new Bundle();
/* 105 */     bundle.putString("device_name", ip);
/* 106 */     msg.setData(bundle);
/* 107 */     this.mHandler.sendMessage(msg);
/* 108 */     setState(3);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public synchronized void stop()
/*     */   {
/* 115 */     Log.d("EthernetService", "stop");
/* 116 */     setState(0);
/* 117 */     if (this.mConnectThread != null) {
/* 118 */       this.mConnectThread.cancel();
/* 119 */       this.mConnectThread = null;
/*     */     }
/* 121 */     if (this.mConnectedThread != null) {
/* 122 */       this.mConnectedThread.cancel();
/* 123 */       this.mConnectedThread = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public GpCom.ERROR_CODE writeDataImmediately(Vector<Byte> data) {
/* 128 */     GpCom.ERROR_CODE retval = GpCom.ERROR_CODE.SUCCESS;
/*     */     
/*     */     ConnectedThread r;
/*     */     
/* 132 */     synchronized (this) {
/* 133 */       if (this.mState != 3)
/* 134 */         return GpCom.ERROR_CODE.PORT_IS_NOT_OPEN;
/* 135 */       r = this.mConnectedThread; }
/* 137 */     retval = r.writeDataImmediately(data);
/* 138 */     return retval;
/*     */   }
/*     */   
/*     */ 
/*     */   private class ConnectThread
/*     */     extends Thread
/*     */   {
/*     */     private Socket mmSocket;
/*     */     
/*     */     private String mmIp;
/*     */     InetAddress mmIpAddress;
/*     */     SocketAddress mmRemoteAddr;
/*     */     
/*     */     public ConnectThread(String Ip, int Port)
/*     */     {
/* 153 */       this.mmSocket = new Socket();
/*     */       try {
/* 155 */         this.mmIpAddress = Inet4Address.getByName(Ip);
/* 156 */         this.mmRemoteAddr = new InetSocketAddress(this.mmIpAddress, Port);
/* 157 */         this.mmIp = Ip;
/*     */       }
/*     */       catch (UnknownHostException e) {
/* 160 */         Log.e("EthernetService", "IpAddress is invalid", e);
/* 161 */         EthernetPort.this.connectionFailed();
/*     */       }
/*     */     }
/*     */     
/*     */     public void run()
/*     */     {
/* 167 */       Log.i("EthernetService", "BEGIN mConnectThread");
/* 168 */       setName("ConnectThread");
/*     */       
/*     */ 
/* 171 */       Log.e("EthernetService", this.mmRemoteAddr.toString());
/*     */       try {
/* 173 */         this.mmSocket.connect(this.mmRemoteAddr, 4000);
/*     */       } catch (IOException e) {
/* 175 */         EthernetPort.this.connectionFailed();
/* 176 */         Log.e("EthernetService", "connectThread failed");
/*     */         try {
/* 178 */           if (this.mmSocket != null) {
/* 179 */             this.mmSocket.close();
/*     */           }
/*     */         }
/*     */         catch (IOException e1) {
/* 183 */           Log.e("EthernetService", "unable to close() socket during connection failure", e1);
/*     */         }
/* 185 */         EthernetPort.this.stop();
/* 186 */         return;
/*     */       }
/*     */       
/* 189 */       synchronized (EthernetPort.this) {
/* 190 */         EthernetPort.this.mConnectThread = null;
/*     */       }
/*     */       
/* 193 */       EthernetPort.this.connected(this.mmSocket, this.mmIp);
/*     */     }
/*     */     
/*     */     public void cancel() {
/*     */       try {
/* 198 */         this.mmSocket.close();
/*     */       } catch (IOException e) {
/* 200 */         Log.e("EthernetService", "close() of connect socket failed", e);
/* 201 */         EthernetPort.this.closePortFailed();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private class ConnectedThread
/*     */     extends Thread
/*     */   {
/*     */     private final Socket mmSocket;
/*     */     private final InputStream mmInStream;
/*     */     private final OutputStream mmOutStream;
/*     */     
/*     */     public ConnectedThread(Socket socket)
/*     */     {
/* 216 */       Log.d("EthernetService", "create ConnectedThread");
/* 217 */       this.mmSocket = socket;
/* 218 */       InputStream tmpIn = null;
/* 219 */       OutputStream tmpOut = null;
/*     */       try
/*     */       {
/* 222 */         tmpIn = socket.getInputStream();
/* 223 */         tmpOut = socket.getOutputStream();
/*     */       } catch (IOException e) {
/* 225 */         Log.e("EthernetService", "temp sockets not created", e);
/*     */       }
/* 227 */       this.mmInStream = tmpIn;
/* 228 */       this.mmOutStream = tmpOut;
/*     */     }
/*     */     
/*     */     public void run()
/*     */     {
/* 233 */       Log.i("EthernetService", "BEGIN mConnectedThread");
/*     */       
/* 235 */       EthernetPort.this.mClosePort = false;
/* 236 */       EthernetPort.this.mReachableThread = new ReachableThread( this.mmSocket);
/* 237 */       EthernetPort.this.mReachableThread.start();
/*     */       
/* 239 */       while (!EthernetPort.this.mClosePort) {
/*     */         try
/*     */         {
/* 242 */           byte[] ReceiveData = new byte[100];
/* 243 */           int bytes = this.mmInStream.read(ReceiveData);
/* 244 */           Log.d("EthernetService", "bytes " + bytes);
/* 245 */           if (bytes > 0) {
/* 246 */             Message msg = EthernetPort.this.mHandler.obtainMessage(2);
/* 247 */             Bundle bundle = new Bundle();
/* 248 */             bundle.putInt("printer.id", EthernetPort.this.mPrinterId);
/* 249 */             bundle.putInt("device.readcnt", bytes);
/* 250 */             bundle.putByteArray("device.read", ReceiveData);
/* 251 */             msg.setData(bundle);
/* 252 */             EthernetPort.this.mHandler.sendMessage(msg);
/*     */           } else {
/* 254 */             Log.e("EthernetService", "disconnected");
/* 255 */             EthernetPort.this.connectionLost();
/* 256 */             EthernetPort.this.stop();
/*     */           }
/*     */         }
/*     */         catch (IOException e) {
/* 260 */           EthernetPort.this.connectionLost();
/* 261 */           Log.e("EthernetService", "disconnected", e);
/*     */         }
/*     */       }
/*     */       
/* 265 */       Log.d("EthernetService", "Closing ethernet work");
/* 266 */       EthernetPort.this.setState(0);
/*     */     }
/*     */     
/*     */     public void cancel() {
/*     */       try {
/* 271 */         EthernetPort.this.mClosePort = true;
/* 272 */         this.mmOutStream.flush();
/* 273 */         if (this.mmSocket != null) {
/* 274 */           this.mmSocket.close();
/*     */         }
/*     */       } catch (IOException e) {
/* 277 */         EthernetPort.this.closePortFailed();
/*     */       }
/*     */     }
/*     */     
/*     */     public GpCom.ERROR_CODE writeDataImmediately(Vector<Byte> data) {
/* 282 */       GpCom.ERROR_CODE retval = GpCom.ERROR_CODE.SUCCESS;
/* 283 */       if ((this.mmSocket != null) && (this.mmOutStream != null)) {
/* 284 */         if ((data != null) && (data.size() > 0)) {
/* 285 */           byte[] sendData = new byte[data.size()];
/* 286 */           if (data.size() > 0) {
/* 287 */             for (int i = 0; i < data.size(); i++) {
/* 288 */               sendData[i] = ((Byte)data.get(i)).byteValue();
/*     */             }
/*     */             try {
/* 291 */               this.mmOutStream.write(sendData);
/* 292 */               this.mmOutStream.flush();
/*     */             } catch (Exception e) {
/* 294 */               Log.d("EthernetService", "Exception occured while sending data immediately: " + e.getMessage());
/* 295 */               retval = GpCom.ERROR_CODE.FAILED;
/*     */             }
/*     */           }
/*     */         }
/*     */       } else {
/* 300 */         retval = GpCom.ERROR_CODE.PORT_IS_NOT_OPEN;
/*     */       }
/* 302 */       return retval;
/*     */     }
/*     */   }
/*     */   
/*     */   class ReachableThread extends Thread {
/*     */     private Socket mSocket;
/*     */     
/*     */     public ReachableThread(Socket socket) {
/* 310 */       this.mSocket = socket;
/*     */     }
/*     */     
/*     */     public void cancel() {
/*     */       try {
/* 315 */         if (this.mSocket != null) {
/* 316 */           this.mSocket.close();
/*     */         }
/*     */       } catch (IOException e) {
/* 319 */         Log.e("EthernetService", "close() of connect socket failed", e);
/* 320 */         EthernetPort.this.closePortFailed();
/*     */       }
/*     */     }
/*     */     
/*     */     public void run()
/*     */     {
/*     */       try {
/* 327 */         Log.e("EthernetService", "start Reachable");
/* 328 */         while (!EthernetPort.this.mClosePort) {
/* 329 */           EthernetPort.this.mClosePort = (!this.mSocket.getInetAddress().isReachable(5000));
/* 330 */           Thread.sleep(5000L);
/*     */         }
/*     */       } catch (IOException e) {
/* 333 */         EthernetPort.this.connectionLost();
/* 334 */         e.printStackTrace();
/*     */       } catch (InterruptedException e) {
/* 336 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\jame\Desktop\gprintersdkv22.jar!\com\gprinter\io\EthernetPort.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */