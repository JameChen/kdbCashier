/*    */ package com.gprinter.io;
/*    */ 
/*    */ public class PortParameters
/*    */ {
/*    */   public static final int SERIAL = 0;
/*    */   public static final int PARALLEL = 1;
/*    */   public static final int USB = 2;
/*    */   public static final int ETHERNET = 3;
/*    */   public static final int BLUETOOTH = 4;
/*    */   public static final int UNDEFINE = 5;
/* 11 */   private boolean mbPortOpen = false;
/* 12 */   private String mBluetoothAddr = null;
/* 13 */   private String mUsbDeviceName = null;
/* 14 */   private String mIpAddr = null;
/* 15 */   private int mPortType = 0;
/* 16 */   private int mPortNumber = 0;
/*    */   
/* 18 */   public PortParameters() { this.mBluetoothAddr = "";
/* 19 */     this.mUsbDeviceName = "";
/* 20 */     this.mIpAddr = "192.168.123.100";
/* 21 */     this.mPortNumber = 9100;
/* 22 */     this.mPortType = 5;
/*    */   }
/*    */   
/*    */   public void setBluetoothAddr(String adr) {
/* 26 */     this.mBluetoothAddr = adr;
/*    */   }
/*    */   
/* 29 */   public String getBluetoothAddr() { return this.mBluetoothAddr; }
/*    */   
/*    */   public void setUsbDeviceName(String name) {
/* 32 */     this.mUsbDeviceName = name;
/*    */   }
/*    */   
/* 35 */   public String getUsbDeviceName() { return this.mUsbDeviceName; }
/*    */   
/*    */   public void setIpAddr(String adr) {
/* 38 */     this.mIpAddr = adr;
/*    */   }
/*    */   
/* 41 */   public String getIpAddr() { return this.mIpAddr; }
/*    */   
/*    */   public void setPortType(int PortType) {
/* 44 */     this.mPortType = PortType;
/*    */   }
/*    */   
/* 47 */   public int getPortType() { return this.mPortType; }
/*    */   
/*    */   public void setPortNumber(int number) {
/* 50 */     this.mPortNumber = number;
/*    */   }
/*    */   
/* 53 */   public int getPortNumber() { return this.mPortNumber; }
/*    */   
/*    */   public void setPortOpenState(boolean state) {
/* 56 */     this.mbPortOpen = state;
/*    */   }
/*    */   
/* 59 */   public boolean getPortOpenState() { return this.mbPortOpen; }
/*    */ }


/* Location:              C:\Users\jame\Desktop\gprintersdkv22.jar!\com\gprinter\io\PortParameters.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */