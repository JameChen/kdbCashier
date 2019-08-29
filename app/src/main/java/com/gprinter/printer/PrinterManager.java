/*     */ package com.gprinter.printer;
/*     */ 
/*     */

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.IBinder;
import android.os.RemoteException;

import com.gprinter.aidl.GpService;
import com.gprinter.command.EscCommand;
import com.gprinter.command.EscCommand.ENABLE;
import com.gprinter.command.EscCommand.JUSTIFICATION;
import com.gprinter.command.GpCom;
import com.gprinter.command.GpCom.ERROR_CODE;
import com.gprinter.command.GpUtils;
import com.gprinter.command.LabelCommand;
import com.gprinter.command.LabelCommand.FONTMUL;
import com.gprinter.command.LabelCommand.FONTTYPE;
import com.gprinter.command.LabelCommand.MIRROR;
import com.gprinter.command.LabelCommand.ROTATION;
import com.gprinter.io.PortParameters;
import com.gprinter.model.LogType;
import com.gprinter.protocol.DeviceStatus;
import com.gprinter.save.SharedPreferencesUtil;
import com.gprinter.service.GpPrintService;
import com.gprinter.service.PrinterStatusBroadcastReceiver;
import com.gprinter.util.LogInfo;

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
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ 
/*     */ public class PrinterManager
/*     */ {
/*     */   private static final String ACTION_CONNECT_STATUS = "action.connect.status";
/*  39 */   private static PrinterManager printerManager = null;
/*     */   private Context context;
/*  41 */   private GpService mGpService = null;
/*     */   private int PrinterId;
/*     */   private PortParameters mPortParam;
/*  44 */   private String usbDeviceName = null;
/*     */   
/*  46 */   private int PrinterCommandType = -1;
/*     */   
/*     */   private Intent intentPrinterService;
/*     */   
/*     */   private Intent intentConnectionPrinter;
/*     */   
/*     */   private PrinterStatusBroadcastReceiver printerStatusBroadcastReceiver;
/*  53 */   private boolean isStop = false;
/*     */   private ServiceConnection conn;
/*     */   
/*     */   private PrinterManager(Context context)
/*     */   {
/*  58 */     this.context = context;
/*  59 */     this.PrinterId = GpPrintService.PrinterId;
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
/*  71 */     this.printerStatusBroadcastReceiver = new PrinterStatusBroadcastReceiver();
/*     */     
/*  73 */     this.conn = new ServiceConnection()
/*     */     {
/*     */       public void onServiceDisconnected(ComponentName name) {
/*  76 */         LogInfo.out("打印机-已断开");
/*  77 */         PrinterManager.this.mGpService = null;
/*  78 */         PrinterManager.this.stop();
/*     */       }
/*     */       
/*     */       public void onServiceConnected(ComponentName name, IBinder service)
/*     */       {
/*  83 */         LogInfo.out("打印机-已连接");
/*  84 */         PrinterManager.this.mGpService = GpService.Stub.asInterface(service);
/*     */         
/*     */ 
/*  87 */         PrinterManager.this.getPrinterCommandType();
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean checkUsbDevicePidVid(UsbDevice dev)
/*     */   {
/*  99 */     int pid = dev.getProductId();
/* 100 */     int vid = dev.getVendorId();
/* 101 */     boolean rel = false;
/* 102 */     if (((vid == 34918) && (pid == 256)) || ((vid == 1137) && (pid == 85)) || ((vid == 6790) && (pid == 30084)) || 
/* 103 */       ((vid == 26728) && (pid == 256)) || ((vid == 26728) && (pid == 512)) || ((vid == 26728) && (pid == 768)) || 
/* 104 */       ((vid == 26728) && (pid == 1024)) || ((vid == 26728) && (pid == 1280)) || ((vid == 26728) && (pid == 1536))) {
/* 105 */       rel = true;
/*     */     }
/* 107 */     return rel;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void getUsbDeviceList()
/*     */   {
/* 114 */     UsbManager manager = (UsbManager)this.context.getSystemService("usb");
/*     */     
/* 116 */     HashMap<String, UsbDevice> devices = manager.getDeviceList();
/* 117 */     Iterator<UsbDevice> deviceIterator = devices.values().iterator();
/* 118 */     int count = devices.size();
/* 119 */     LogInfo.out("usb device count " + count);
/* 120 */     if (count > 0) {
/* 121 */       while (deviceIterator.hasNext()) {
/* 122 */         UsbDevice device = (UsbDevice)deviceIterator.next();
/* 123 */         String devicename = device.getDeviceName();
/* 124 */         LogInfo.out("devicename:" + devicename);
/* 125 */         if (checkUsbDevicePidVid(device))
/*     */         {
/* 127 */           this.usbDeviceName = devicename;
/* 128 */           LogInfo.out("use devicename:" + devicename);
/* 129 */           break;
/*     */         }
/*     */       }
/*     */     } else {
/* 133 */       LogInfo.out("no usb Devices ");
/*     */     }
/*     */   }
/*     */   
/*     */   public static PrinterManager getPrinterManager(Context context) {
/* 138 */     if (printerManager == null) {
/* 139 */       printerManager = new PrinterManager(context);
/*     */     }
/* 141 */     return printerManager;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void start()
/*     */   {
/* 148 */     this.isStop = false;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 157 */     startPrinterService();
/* 158 */     registerBroadcast();
/* 159 */     connectionPrinter();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void stop()
/*     */   {
/* 166 */     this.isStop = true;
/* 167 */     closePort();
/*     */     try {
/* 169 */       this.context.unbindService(this.conn);
/*     */     } catch (IllegalArgumentException e) {
/* 171 */       e.printStackTrace();
/* 172 */       if (e.getCause() != null) {
/* 173 */         LogInfo.err(LogType.APP_ERR, e.getCause().getMessage());
/*     */       } else {
/* 175 */         LogInfo.err(LogType.APP_ERR, e.getMessage());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void startPrinterService() {
/* 181 */     if (this.intentPrinterService == null) {
/* 182 */       this.intentPrinterService = new Intent(this.context, GpPrintService.class);
/*     */     } else {
/* 184 */       this.context.stopService(this.intentPrinterService);
/*     */     }
/* 186 */     this.context.startService(this.intentPrinterService);
/*     */     try {
/* 188 */       Thread.sleep(2000L);
/*     */     } catch (InterruptedException e) {
/* 190 */       e.printStackTrace();
/* 191 */       if (e.getCause() != null) {
/* 192 */         LogInfo.err(LogType.APP_ERR, e.getCause().getMessage());
/*     */       } else {
/* 194 */         LogInfo.err(LogType.APP_ERR, e.getMessage());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void connectionPrinter() {
/* 200 */     if (this.intentConnectionPrinter == null) {
/* 201 */       this.intentConnectionPrinter = new Intent(this.context, GpPrintService.class);
/*     */     }
/* 203 */     this.context.bindService(this.intentConnectionPrinter, this.conn, 1);
/*     */   }
/*     */   
/*     */   private void registerBroadcast() {
/* 207 */     IntentFilter filter = new IntentFilter();
/* 208 */     filter.addAction("action.connect.status");
/* 209 */     this.context.registerReceiver(this.printerStatusBroadcastReceiver, filter);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getCommand(String printMsg, int controller)
/*     */   {
/* 220 */     int commandType = getPrinterCommandType();
/* 221 */     String r = null;
/* 222 */     if (commandType == 0) {
/* 223 */       EscCommand esc = new EscCommand();
/* 224 */       esc.addPrintAndLineFeed();
/* 225 */       esc.addSelectJustification(JUSTIFICATION.LEFT);
/* 226 */       esc.addSelectPrintModes(EscCommand.FONT.FONTA, ENABLE.OFF, ENABLE.OFF, ENABLE.OFF, ENABLE.OFF);
/* 227 */       esc.addText(printMsg);
/* 228 */       esc.addPrintAndFeedLines((byte)8);
/* 229 */       if (controller == 1) {
/* 230 */         esc.addCutPaper();
/*     */       }
/*     */       
/* 233 */       r = getString(esc.getCommand());
/* 234 */     } else if (commandType == 1)
/*     */     {
/* 236 */       String labelWidth = SharedPreferencesUtil.ReadSharedPerference(this.context, 
/* 237 */         "labelWidth");
/* 238 */       String labelHeight = SharedPreferencesUtil.ReadSharedPerference(this.context, 
/* 239 */         "labelHeight");
/* 240 */       String labelGap = SharedPreferencesUtil.ReadSharedPerference(this.context, "labelGap");
/*     */       
/* 242 */       LabelCommand tsc = new LabelCommand();
/* 243 */       tsc.addSize(Integer.valueOf(labelWidth).intValue(), Integer.valueOf(labelHeight).intValue());
/* 244 */       tsc.addGap(Integer.valueOf(labelGap).intValue());
/* 245 */       tsc.addDirection(LabelCommand.DIRECTION.BACKWARD, MIRROR.NORMAL);
/* 246 */       tsc.addReference(0, 0);
/* 247 */       if (controller == 1) {
/* 248 */         tsc.addTear(ENABLE.ON);
/*     */       }
/* 250 */       tsc.addCls();
/*     */       
/* 252 */       tsc.addText(20, 20, FONTTYPE.SIMPLIFIED_CHINESE, ROTATION.ROTATION_0, FONTMUL.MUL_1, FONTMUL.MUL_1,
/* 253 */         printMsg);
/* 254 */       tsc.addPrint(1, 1);
/* 255 */       r = getString(tsc.getCommand());
/*     */     }
/* 257 */     LogInfo.out("print command:" + r);
/* 258 */     return r;
/*     */   }
/*     */   
/*     */   private String getString(Vector<Byte> datas) {
/* 262 */     Byte[] Bytes = (Byte[])datas.toArray(new Byte[datas.size()]);
/* 263 */     byte[] bytes = com.gprinter.io.utils.GpUtils.ByteTo_byte(Bytes);
/* 264 */     return android.util.Base64.encodeToString(bytes, 0);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int sendLabelCommand(String b64)
/*     */   {
/* 303 */     int rl = -1;
/* 304 */     if ((this.mGpService != null) && (this.PrinterCommandType == 1)) {
/*     */       try {
/* 306 */         return this.mGpService.sendLabelCommand(this.PrinterId, b64);
/*     */       }
/*     */       catch (RemoteException e) {
/* 309 */         e.printStackTrace();
/* 310 */         if (e.getCause() != null) {
/* 311 */           LogInfo.err(LogType.CONNECT_PRINTER_ERR, e.getCause().getMessage());
/*     */         } else {
/* 313 */           LogInfo.err(LogType.CONNECT_PRINTER_ERR, e.getMessage());
/*     */         }
/* 315 */         return rl;
/*     */       }
/*     */     }
/* 318 */     return rl;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int sendEscCommand(String b64)
/*     */   {
/* 329 */     int rl = -1;
/* 330 */     if ((this.mGpService != null) && (this.PrinterCommandType == 0)) {
/*     */       try {
/* 332 */         return this.mGpService.sendEscCommand(this.PrinterId, b64);
/*     */       }
/*     */       catch (RemoteException e) {
/* 335 */         e.printStackTrace();
/* 336 */         if (e.getCause() != null) {
/* 337 */           LogInfo.err(LogType.CONNECT_PRINTER_ERR, e.getCause().getMessage());
/*     */         } else {
/* 339 */           LogInfo.err(LogType.CONNECT_PRINTER_ERR, e.getMessage());
/*     */         }
/* 341 */         return rl;
/*     */       }
/*     */     }
/* 344 */     return rl;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int printTestPage()
/*     */   {
/* 352 */     if (this.mGpService == null) {
/* 353 */       return -1;
/*     */     }
/* 355 */     int status = getPrinterConnectStatus();
/* 356 */     if (status == 3)
/*     */       try {
/* 358 */         return this.mGpService.printeTestPage(this.PrinterId);
/*     */       } catch (RemoteException e) {
/* 360 */         e.printStackTrace();
/* 361 */         if (e.getCause() != null) {
/* 362 */           LogInfo.err(LogType.CONNECT_PRINTER_ERR, e.getCause().getMessage());
/*     */         } else {
/* 364 */           LogInfo.err(LogType.CONNECT_PRINTER_ERR, e.getMessage());
/*     */         }
/* 366 */         return -1;
/*     */       }
/* 368 */     if (status == 0) {
/* 369 */       LogInfo.out("打印机连接断开");
/*     */     }
/* 371 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getPrinterCommandType()
/*     */   {
/* 381 */     int type = -1;
/* 382 */     if (this.mGpService == null) {
/* 383 */       return type;
/*     */     }
/*     */     try {
/* 386 */       this.PrinterCommandType = this.mGpService.getPrinterCommandType(this.PrinterId);
/* 387 */       type = this.PrinterCommandType;
/* 388 */       if (type == 0) {
/* 389 */         LogInfo.out("打印机使用 ESC 命令");
/*     */       } else {
/* 391 */         LogInfo.out("打印机使用 TSC 命令");
/*     */       }
/* 393 */       return type;
/*     */     } catch (RemoteException e) {
/* 395 */       e.printStackTrace();
/* 396 */       if (e.getCause() != null) {
/* 397 */         LogInfo.err(LogType.CONNECT_PRINTER_ERR, e.getCause().getMessage());
/*     */       } else
/* 399 */         LogInfo.err(LogType.CONNECT_PRINTER_ERR, e.getMessage());
/*     */     }
/* 401 */     return type;
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
/*     */   public synchronized void getPrinterStatus()
/*     */   {
/*     */     try
/*     */     {
/* 416 */       this.mGpService.queryPrinterStatus(this.PrinterId, 1000, 255);
/*     */     } catch (RemoteException e) {
/* 418 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public int getDeviceStatus(int status)
/*     */   {
/* 424 */     int deviceStatus = DeviceStatus.NO_PRINTER.toInt();
/* 425 */     if (this.mGpService == null) {
/* 426 */       return deviceStatus;
/*     */     }
/*     */     
/* 429 */     LogInfo.out("状态值： " + status);
/* 430 */     String str = "未知";
/* 431 */     if (status == 0) {
/* 432 */       str = "正常";
/* 433 */       deviceStatus = DeviceStatus.NORMAL.toInt();
/* 434 */     } else if ((byte)(status & 0x1) > 0) {
/* 435 */       str = "脱机";
/* 436 */       deviceStatus = DeviceStatus.NO_PRINTER.toInt();
/* 437 */     } else if ((byte)(status & 0x2) > 0) {
/* 438 */       str = "缺纸";
/* 439 */       deviceStatus = DeviceStatus.LACK_PAGER.toInt();
/* 440 */     } else if ((byte)(status & 0x4) > 0) {
/* 441 */       str = "开盖";
/* 442 */       deviceStatus = DeviceStatus.COVER_OPEN.toInt();
/* 443 */     } else if ((byte)(status & 0x8) > 0) {
/* 444 */       str = "过热或出错";
/* 445 */       deviceStatus = DeviceStatus.ERROR.toInt();
/*     */     }
/*     */     
/* 448 */     return deviceStatus;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void openPort()
/*     */   {
/* 455 */     if (this.mGpService == null) {
/* 456 */       return;
/*     */     }
/*     */     try {
/* 459 */       int rel = this.mGpService.openPort(this.PrinterId, this.mPortParam.getPortType(), this.mPortParam.getUsbDeviceName(), 
/* 460 */         this.mPortParam.getPortNumber());
/* 461 */       ERROR_CODE r = ERROR_CODE.values()[rel];
/* 462 */       if (r != ERROR_CODE.SUCCESS) {
/* 463 */         if (r == ERROR_CODE.DEVICE_ALREADY_OPEN) {
/* 464 */           LogInfo.out("端口已经打开");
/*     */         } else {
/* 466 */           LogInfo.out(GpCom.getErrorText(r));
/*     */         }
/*     */       } else {
/* 469 */         LogInfo.out("正常打开");
/*     */       }
/*     */     } catch (RemoteException e) {
/* 472 */       e.printStackTrace();
/* 473 */       if (e.getCause() != null) {
/* 474 */         LogInfo.err(LogType.CONNECT_PRINTER_ERR, e.getCause().getMessage());
/*     */       } else {
/* 476 */         LogInfo.err(LogType.CONNECT_PRINTER_ERR, e.getMessage());
/*     */       }
/*     */     }
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
/*     */   public int getPrinterConnectStatus()
/*     */   {
/* 492 */     int status = -1;
/* 493 */     if (this.mGpService == null) {
/* 494 */       return status;
/*     */     }
/*     */     try {
/* 497 */       status = this.mGpService.getPrinterConnectStatus(this.PrinterId);
/* 498 */       if (status != 0)
/*     */       {
/* 500 */         if (status != 1)
/*     */         {
/* 502 */           if (status == 2) {}
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 507 */       return status;
/*     */     } catch (RemoteException e) {
/* 509 */       e.printStackTrace();
/* 510 */       if (e.getCause() != null) {
/* 511 */         LogInfo.err(LogType.CONNECT_PRINTER_ERR, e.getCause().getMessage());
/*     */       } else
/* 513 */         LogInfo.err(LogType.CONNECT_PRINTER_ERR, e.getMessage());
/*     */     }
/* 515 */     return status;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void closePort()
/*     */   {
/* 523 */     if (this.mGpService != null) {
/*     */       try {
/* 525 */         this.mGpService.closePort(this.PrinterId);
/*     */       } catch (RemoteException e) {
/* 527 */         e.printStackTrace();
/* 528 */         if (e.getCause() != null) {
/* 529 */           LogInfo.err(LogType.CONNECT_PRINTER_ERR, e.getCause().getMessage());
/*     */         } else {
/* 531 */           LogInfo.err(LogType.CONNECT_PRINTER_ERR, e.getMessage());
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public GpService getGpService() {
/* 538 */     return this.mGpService;
/*     */   }
/*     */   
/*     */   public boolean isStop() {
/* 542 */     return this.isStop;
/*     */   }
/*     */ }


/* Location:              C:\Users\jame\Desktop\gprintersdkv22.jar!\com\gprinter\printer\PrinterManager.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */