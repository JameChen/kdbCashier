/*    */ package com.gprinter.command;
/*    */ 
/*    */ public class GpCom { public static final int STATE_NO_ERR = 0;
/*    */   public static final int STATE_OFFLINE = 1;
/*    */   public static final int STATE_PAPER_ERR = 2;
/*    */   public static final int STATE_COVER_OPEN = 4;
/*    */   public static final int STATE_ERR_OCCURS = 8;
/*    */   public static final int STATE_TIMES_OUT = 16;
/*    */   public static final String ACTION_CONNECT_STATUS = "action.connect.status";
/*    */   public static final String EXTRA_PRINTER_REAL_STATUS = "action.printer.real.status";
/*    */   public static final String ACTION_DEVICE_REAL_STATUS = "action.device.real.status";
/*    */   
/* 13 */   public static enum ERROR_CODE { SUCCESS,  FAILED,  TIMEOUT,  INVALID_DEVICE_PARAMETERS,  DEVICE_ALREADY_OPEN,  INVALID_PORT_NUMBER,  INVALID_IP_ADDRESS,  INVALID_CALLBACK_OBJECT,  BLUETOOTH_IS_NOT_SUPPORT,  OPEN_BLUETOOTH,  PORT_IS_NOT_OPEN,  INVALID_BLUETOOTH_ADDRESS,  PORT_IS_DISCONNECT; }
/*    */   
/*    */   public static final String ACTION_RECEIPT_RESPONSE = "action.device.receipt.response";
/*    */   public static final String ACTION_LABEL_RESPONSE = "action.device.label.response";
/*    */   public static final String EXTRA_PRINTER_ID = "printer.id";
/*    */   public static final String EXTRA_PRINTER_REQUEST_CODE = "printer.request_code";
/*    */   public static final String EXTRA_PRINTER_LABEL_RESPONSE = "printer.label.response";
/*    */   public static final String EXTRA_PRINTER_LABEL_RESPONSE_CNT = "printer.label.response.cnt";
/*    */   public static final int ESC_COMMAND = 0;
/*    */   public static final int LABEL_COMMAND = 1;
/*    */   public static final String KEY_ISCHECKED = "key_ischecked";
/*    */   public static String getErrorText(ERROR_CODE errorcode) { String s;
///*    */     String s;
///*    */     String s;
///*    */     String s;
///*    */     String s;
///*    */     String s;
///*    */     String s;
///*    */     String s;
///*    */     String s;
///*    */     String s;
///*    */     String s;
///*    */     String s;
///*    */     String s;
///* 37 */     String s;
    switch (errorcode) {
/*    */     case BLUETOOTH_IS_NOT_SUPPORT: 
/* 39 */       s = "success";
/* 40 */       break;
/*    */     case FAILED: 
/* 42 */       s = "timeout";
/* 43 */       break;
/*    */     case INVALID_BLUETOOTH_ADDRESS: 
/* 45 */       s = "Invalid device paramters";
/* 46 */       break;
/*    */     case INVALID_CALLBACK_OBJECT: 
/* 48 */       s = "Device already open";
/* 49 */       break;
/*    */     case INVALID_DEVICE_PARAMETERS: 
/* 51 */       s = "Invalid port number";
/* 52 */       break;
/*    */     case INVALID_IP_ADDRESS: 
/* 54 */       s = "Invalid ip address";
/* 55 */       break;
/*    */     case OPEN_BLUETOOTH: 
/* 57 */       s = "Bluetooth is not support by the device";
/* 58 */       break;
/*    */     case PORT_IS_DISCONNECT: 
/* 60 */       s = "Please open bluetooth";
/* 61 */       break;
/*    */     case PORT_IS_NOT_OPEN: 
/* 63 */       s = "Port is not open";
/* 64 */       break;
/*    */     case SUCCESS: 
/* 66 */       s = "Invalid bluetooth address";
/* 67 */       break;
/*    */     case TIMEOUT: 
/* 69 */       s = "Port is disconnect";
/* 70 */       break;
/*    */     case INVALID_PORT_NUMBER: 
/* 72 */       s = "Invalid callback object";
/* 73 */       break;
/*    */     case DEVICE_ALREADY_OPEN: 
/* 75 */       s = "Failed";
/* 76 */       break;
/*    */     default: 
/* 78 */       s = "Unknown error code";
/*    */     }
/*    */     
/* 81 */     return s;
/*    */   }
/*    */ }


/* Location:              C:\Users\jame\Desktop\gprintersdkv22.jar!\com\gprinter\command\GpCom.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */