/*      */
package com.gprinter.command;
/*      */ 
/*      */

import android.annotation.SuppressLint;
/*      */ import android.graphics.Bitmap;
/*      */ import android.util.Log;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import java.util.Vector;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;

/*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class EscCommand
/*      */ {
    /*      */   private static final String DEBUG_TAG = "EscCommand";
    /*   25 */ Vector<Byte> Command = null;

    /*      */
/*      */   public static enum STATUS {
        /*   28 */     PRINTER_STATUS(1), PRINTER_OFFLINE(2), PRINTER_ERROR(3), PRINTER_PAPER(4);
        /*      */
/*      */     private final int value;

        /*      */
/*   32 */
        private STATUS(int value) {
            this.value = value;
        }

        /*      */
/*      */
        public byte getValue()
/*      */ {
/*   36 */
            return (byte) this.value;
/*      */
        }
/*      */
    }

    /*      */
/*      */   public static enum ENABLE {
        /*   41 */     OFF(0), ON(1);
        /*      */
/*      */     private final int value;

        /*      */
/*   45 */
        private ENABLE(int value) {
            this.value = value;
        }

        /*      */
/*      */
        public byte getValue()
/*      */ {
/*   49 */
            return (byte) this.value;
/*      */
        }
/*      */
    }

    /*      */
/*      */   public static enum UNDERLINE_MODE {
        /*   54 */     OFF(0), UNDERLINE_1DOT(1), UNDERLINE_2DOT(2);
        /*      */
/*      */     private final int value;

        /*      */
/*   58 */
        private UNDERLINE_MODE(int value) {
            this.value = value;
        }

        /*      */
/*      */
        public byte getValue()
/*      */ {
/*   62 */
            return (byte) this.value;
/*      */
        }
/*      */
    }

    /*      */
/*      */   public static enum FONT {
        /*   67 */     FONTA(0), FONTB(1);
        /*      */
/*      */     private final int value;

        /*      */
/*   71 */
        private FONT(int value) {
            this.value = value;
        }

        /*      */
/*      */
        public byte getValue()
/*      */ {
/*   75 */
            return (byte) this.value;
/*      */
        }
/*      */
    }

    /*      */
/*      */   public static enum CHARACTER_SET {
        /*   80 */     USA(0), FRANCE(1), GERMANY(2), UK(3), DENMARK_I(4), SWEDEN(5), ITALY(6), SPAIN_I(7), JAPAN(8), NORWAY(
/*   81 */       9), DENMARK_II(10), SPAIN_II(11), LATIN_AMERCIA(12), KOREAN(13), SLOVENIA(14), CHINA(15);
        /*      */
/*      */     private final int value;

        /*      */
/*   85 */
        private CHARACTER_SET(int value) {
            this.value = value;
        }

        /*      */
/*      */
        public byte getValue()
/*      */ {
/*   89 */
            return (byte) this.value;
/*      */
        }
/*      */
    }

    /*      */
/*      */   public static enum JUSTIFICATION {
        /*   94 */     LEFT(0), CENTER(1), RIGHT(2);
        /*      */
/*      */     private final int value;

        /*      */
/*   98 */
        private JUSTIFICATION(int value) {
            this.value = value;
        }

        /*      */
/*      */
        public byte getValue()
/*      */ {
/*  102 */
            return (byte) this.value;
/*      */
        }
/*      */
    }

    /*      */
/*      */   public static enum CODEPAGE {
        /*  107 */     PC437(0), KATAKANA(1), PC850(2), PC860(3), PC863(4), PC865(5), WEST_EUROPE(6), GREEK(7), HEBREW(8), EAST_EUROPE(
/*  108 */       9), IRAN(10), WPC1252(16), PC866(17), PC852(18), PC858(19), IRANII(20), LATVIAN(21), ARABIC(22), PT151(
/*  109 */       23), PC747(24), WPC1257(25), VIETNAM(27), PC864(28), PC1001(29), UYGUR(30), THAI(255);
        /*      */
/*      */     private final int value;

        /*      */
/*  113 */
        private CODEPAGE(int value) {
            this.value = value;
        }

        /*      */
/*      */
        public byte getValue()
/*      */ {
/*  117 */
            return (byte) this.value;
/*      */
        }
/*      */
    }

    /*      */
/*      */   public static enum WIDTH_ZOOM {
        /*  122 */     MUL_1(0), MUL_2(16), MUL_3(32), MUL_4(48), MUL_5(64), MUL_6(80), MUL_7(96), MUL_8(112);
        /*      */
/*      */     private final int value;

        /*      */
/*  126 */
        private WIDTH_ZOOM(int value) {
            this.value = value;
        }

        /*      */
/*      */
        public byte getValue()
/*      */ {
/*  130 */
            return (byte) this.value;
/*      */
        }
/*      */
    }

    /*      */
/*      */   public static enum HEIGHT_ZOOM {
        /*  135 */     MUL_1(0), MUL_2(1), MUL_3(2), MUL_4(3), MUL_5(4), MUL_6(5), MUL_7(6), MUL_8(7);
        /*      */
/*      */     private final int value;

        /*      */
/*  139 */
        private HEIGHT_ZOOM(int value) {
            this.value = value;
        }

        /*      */
/*      */
        public byte getValue()
/*      */ {
/*  143 */
            return (byte) this.value;
/*      */
        }
/*      */
    }

    /*      */
/*      */   public static enum HRI_POSITION {
        /*  148 */     NO_PRINT(0), ABOVE(1), BELOW(2), ABOVE_AND_BELOW(3);
        /*      */
/*      */     private final int value;

        /*      */
/*  152 */
        private HRI_POSITION(int value) {
            this.value = value;
        }

        /*      */
/*      */
        public byte getValue()
/*      */ {
/*  156 */
            return (byte) this.value;
/*      */
        }
/*      */
    }

    /*      */
/*      */
    public EscCommand() {
/*  161 */
        this.Command = new Vector(4096, 1024);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    private void addArrayToCommand(byte[] array)
/*      */ {
/*  172 */
        for (int i = 0; i < array.length; i++) {
/*  173 */
            this.Command.add(Byte.valueOf(array[i]));
/*      */
        }
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    private void addStrToCommand(String str)
/*      */ {
/*  185 */
        byte[] bs = null;
/*  186 */
        if (!str.equals("")) {
/*      */
            try {
/*  188 */
                bs = str.getBytes("GB2312");
/*      */
            }
/*      */ catch (UnsupportedEncodingException e) {
/*  191 */
                e.printStackTrace();
/*      */
            }
/*  193 */
            for (int i = 0; i < bs.length; i++) {
/*  194 */
                this.Command.add(Byte.valueOf(bs[i]));
/*      */
            }
/*      */
        }
/*      */
    }

    /*      */
/*      */
    private void addStrToCommand(String str, String charset) {
/*  200 */
        byte[] bs = null;
/*  201 */
        if (!str.equals("")) {
/*      */
            try {
/*  203 */
                bs = str.getBytes("GB2312");
/*      */
            }
/*      */ catch (UnsupportedEncodingException e) {
/*  206 */
                e.printStackTrace();
/*      */
            }
/*  208 */
            for (int i = 0; i < bs.length; i++) {
/*  209 */
                this.Command.add(Byte.valueOf(bs[i]));
/*      */
            }
/*      */
        }
/*      */
    }

    /*      */
/*      */
    private void addStrToCommandUTF8Encoding(String str, int length) {
/*  215 */
        byte[] bs = null;
/*  216 */
        if (!str.equals("")) {
/*      */
            try {
/*  218 */
                bs = str.getBytes("UTF-8");
/*      */
            }
/*      */ catch (UnsupportedEncodingException e) {
/*  221 */
                e.printStackTrace();
/*      */
            }
/*  223 */
            Log.d("EscCommand", "bs.length" + bs.length);
/*  224 */
            if (length > bs.length)
/*  225 */ length = bs.length;
/*  226 */
            Log.d("EscCommand", "length" + length);
/*  227 */
            for (int i = 0; i < length; i++) {
/*  228 */
                this.Command.add(Byte.valueOf(bs[i]));
/*      */
            }
/*      */
        }
/*      */
    }

    /*      */
/*      */
    private void addStrToCommand(String str, int length) {
/*  234 */
        byte[] bs = null;
/*  235 */
        if (!str.equals("")) {
/*      */
            try {
/*  237 */
                bs = str.getBytes("GB2312");
/*      */
            }
/*      */ catch (UnsupportedEncodingException e) {
/*  240 */
                e.printStackTrace();
/*      */
            }
/*  242 */
            Log.d("EscCommand", "bs.length" + bs.length);
/*  243 */
            if (length > bs.length)
/*  244 */ length = bs.length;
/*  245 */
            Log.d("EscCommand", "length" + length);
/*  246 */
            for (int i = 0; i < length; i++) {
/*  247 */
                this.Command.add(Byte.valueOf(bs[i]));
/*      */
            }
/*      */
        }
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */
    public void addHorTab()
/*      */ {
/*  256 */
        byte[] command = {9};
/*  257 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addText(String text)
/*      */ {
/*  266 */
        addStrToCommand(text);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addText(String text, String charsetName)
/*      */ {
/*  275 */
        addStrToCommand(text, charsetName);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addArabicText(String text)
/*      */ {
/*  284 */
        text = GpUtils.reverseLetterAndNumber(text);
/*  285 */
        text = GpUtils.splitArabic(text);
/*  286 */
        String[] fooInput = text.split("\\n");
/*  287 */
        String[] arrayOfString1;
        int j = (arrayOfString1 = fooInput).length;
        for (int i = 0; i < j; i++) {
            String in = arrayOfString1[i];
/*      */       
/*  289 */
            byte[] output = GpUtils.string2Cp864(in);
/*  290 */
            for (int ii = 0; ii < output.length; ii++) {
/*  291 */
                if (output[ii] == -16) {
/*  292 */
                    addArrayToCommand(new byte[]{27, 116, 29, -124, 27, 116, 22});
/*  293 */
                } else if (output[ii] == Byte.MAX_VALUE) {
/*  294 */
                    this.Command.add(Byte.valueOf((byte) -41));
/*      */
                } else {
/*  296 */
                    this.Command.add(Byte.valueOf(output[ii]));
/*      */
                }
/*      */
            }
/*      */
        }
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */
    public void addPrintAndLineFeed()
/*      */ {
/*  306 */
        byte[] command = {10};
/*  307 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void RealtimeStatusTransmission(STATUS status)
/*      */ {
/*  318 */
        byte[] command = {16, 4, status.getValue()};
/*  319 */     //command[2] = status.getValue();
/*  320 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addGeneratePluseAtRealtime(LabelCommand.FOOT foot, byte t)
/*      */ {
        //command[3] = ((byte)foot.getValue());
/*  334 */
        if (t > 8)
/*  335 */ t = 8;
/*  336 */     //command[4] = t;
/*  332 */
        byte[] command = {16, 20, 1, ((byte) foot.getValue()), t};
/*  333 */
/*  337 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addSound(byte n, byte t)
/*      */ {
/*  349 */
/*  350 */
        if (n < 0) {
/*  351 */
            n = 1;
/*  352 */
        } else if (n > 9) {
/*  353 */
            n = 9;
/*      */
        }
/*  355 */
        if (t < 0) {
/*  356 */
            t = 1;
/*  357 */
        } else if (t > 9) {
/*  358 */
            t = 9;
/*      */
        }
/*      */
///*  361 */     command[2] = n;
///*  362 */     command[3] = t;
        byte[] command = {27, 66, n, t};
/*  363 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addSetRightSideCharacterSpacing(byte n)
/*      */ {
/*  373 */
        byte[] command = {27, 32, n};
/*  374 */     //command[2] = n;
/*  375 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public Vector<Byte> getCommand()
/*      */ {
/*  384 */
        return this.Command;
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addSelectPrintModes(FONT font, ENABLE emphasized, ENABLE doubleheight, ENABLE doublewidth, ENABLE underline)
/*      */ {
/*  403 */
        byte temp = 0;
/*  404 */
        if (font == FONT.FONTB) {
/*  405 */
            temp = 1;
/*      */
        }
/*  407 */
        if (emphasized == ENABLE.ON) {
/*  408 */
            temp = (byte) (temp | 0x8);
/*      */
        }
/*  410 */
        if (doubleheight == ENABLE.ON) {
/*  411 */
            temp = (byte) (temp | 0x10);
/*      */
        }
/*  413 */
        if (doublewidth == ENABLE.ON) {
/*  414 */
            temp = (byte) (temp | 0x20);
/*      */
        }
/*  416 */
        if (underline == ENABLE.ON) {
/*  417 */
            temp = (byte) (temp | 0x80);
/*      */
        }
/*  419 */
        byte[] command = {27, 33, temp};
/*  420 */    // command[2] = temp;
/*  421 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addSetAbsolutePrintPosition(short n)
/*      */ {
        byte nl = (byte) (n % 256);
/*  433 */
        byte nh = (byte) (n / 256);
/*  431 */
        byte[] command = {27, 36, nl, nh};
/*  432 */
///*  434 */     command[2] = nl;
///*  435 */     command[3] = nh;
/*  436 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addSelectOrCancelUserDefineCharacter(ENABLE enable)
/*      */ {
/*  446 */
        byte i = 0;
/*  447 */
        if (enable == ENABLE.ON) {
/*  448 */     //  command[2] = 1;
            i = 1;
/*      */
        } else
/*  450 */      // command[2] = 0;
            i = 0;
        byte[] command = {27, 37, i};
/*  451 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addTurnUnderlineModeOnOrOff(UNDERLINE_MODE underline)
/*      */ {
/*  461 */
        byte[] command = {27, 45, underline.getValue()};
/*  462 */    // command[2] = underline.getValue();
/*  463 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */
    public void addSelectDefualtLineSpacing()
/*      */ {
/*  470 */
        byte[] command = {27, 50};
/*  471 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addSetLineSpacing(byte n)
/*      */ {
/*  481 */
        byte[] command = {27, 51, n};
/*  482 */    // command[2] = n;
/*  483 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addCancelUserDefinedCharacters(byte n)
/*      */ {
/*  493 */    // byte[] command = { 27, 63 };
        byte t;
/*  494 */
        if ((n >= 32) && (n <= 126)) {
/*  495 */       //command[2] = n;
            t = n;
/*      */
        } else
/*  497 */       //command[2] = 32;
            t = 32;
        byte[] command = {27, 63, t};
/*  498 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */
    public void addInitializePrinter()
/*      */ {
/*  505 */
        byte[] command = {27, 64};
/*  506 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addTurnEmphasizedModeOnOrOff(ENABLE enabel)
/*      */ {
/*  516 */
        byte[] command = {27, 69, enabel.getValue()};
/*  517 */    // command[2] = enabel.getValue();
/*  518 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addTurnDoubleStrikeOnOrOff(ENABLE enabel)
/*      */ {
/*  528 */
        byte[] command = {27, 71, enabel.getValue()};
/*  529 */     //command[2] = enabel.getValue();
/*  530 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addPrintAndFeedPaper(byte n)
/*      */ {
/*  540 */
        byte[] command = {27, 74, n};
/*  541 */     //command[2] = n;
/*  542 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addSelectCharacterFont(FONT font)
/*      */ {
/*  552 */
        byte[] command = {27, 77, font.getValue()};
/*  553 */     //command[2] = font.getValue();
/*  554 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addSelectInternationalCharacterSet(CHARACTER_SET set)
/*      */ {
/*  564 */
        byte[] command = {27, 82, set.getValue()};
/*  565 */     //command[2] = set.getValue();
/*  566 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addTurn90ClockWiseRotatin(ENABLE enabel)
/*      */ {
/*  576 */
        byte[] command = {27, 86, enabel.getValue()};
/*  577 */     //command[2] = enabel.getValue();
/*  578 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addSetRelativePrintPositon(short n)
/*      */ {
/*  588 */     //byte[] command = { 27, 92 };
/*  589 */
        byte nl = (byte) (n % 256);
/*  590 */
        byte nh = (byte) (n / 256);
///*  591 */     command[2] = nl;
///*  592 */     command[3] = nh;
        byte[] command = {27, nl, nh};
/*  593 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addSelectJustification(JUSTIFICATION just)
/*      */ {
/*  603 */
        byte[] command = {27, 97, just.getValue()};
/*  605 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addPrintAndFeedLines(byte n)
/*      */ {
/*  615 */
        byte[] command = {27, 100, n};
/*  616 */     //command[2] = n;
/*  617 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addGeneratePlus(LabelCommand.FOOT foot, byte t1, byte t2)
/*      */ {
/*  631 */     //byte[] command = { 27, 112 };
///*  632 */     command[2] = ((byte)foot.getValue());
///*  633 */     command[3] = t1;
///*  634 */     command[4] = t2;
        byte[] command = {27, 112, ((byte) foot.getValue()), t1, t2};
/*  635 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addSelectCodePage(CODEPAGE page)
/*      */ {
/*  645 */
        byte[] command = {27, 116, page.getValue()};
/*  646 */     //command[2] = page.getValue();
/*  647 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addTurnUpsideDownModeOnOrOff(ENABLE enable)
/*      */ {
/*  657 */
        byte[] command = {27, 123, enable.getValue()};
/*  658 */     //command[2] = enable.getValue();
/*  659 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addSetCharcterSize(WIDTH_ZOOM width, HEIGHT_ZOOM height)
/*      */ {
/*  671 */
/*  672 */
        byte temp = 0;
/*  673 */
        temp = (byte) (temp | width.getValue());
/*  674 */
        temp = (byte) (temp | height.getValue());
/*  675 */    // command[2] = temp;
        byte[] command = {29, 33, temp};
/*  676 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addTurnReverseModeOnOrOff(ENABLE enable)
/*      */ {
/*  686 */
        byte[] command = {29, 66, enable.getValue()};
/*  687 */    // command[2] = enable.getValue();
/*  688 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addSelectPrintingPositionForHRICharacters(HRI_POSITION position)
/*      */ {
/*  698 */
        byte[] command = {29, 72, position.getValue()};
/*  699 */    // command[2] = position.getValue();
/*  700 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addSetLeftMargin(short n)
/*      */ {
/*  710 */     //byte[] command = { 29, 76 };
/*  711 */
        byte nl = (byte) (n % 256);
/*  712 */
        byte nh = (byte) (n / 256);
///*  713 */     command[2] = nl;
///*  714 */     command[3] = nh;
        byte[] command = {29, 76, nl, nh};
/*  715 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addSetHorAndVerMotionUnits(byte x, byte y)
/*      */ {
/*  727 */
        byte[] command = {29, 80, x, y};
///*  728 */     command[2] = x;
///*  729 */     command[3] = y;
/*  730 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addCutAndFeedPaper(byte length)
/*      */ {
/*  740 */
        byte[] command = {29, 86, 66, length};
/*  741 */     //command[3] = length;
/*  742 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */
    public void addCutPaper()
/*      */ {
/*  749 */
        byte[] command = {29, 86, 1};
/*  750 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addSetPrintingAreaWidth(short width)
/*      */ {
/*  760 */
        byte nl = (byte) (width % 256);
/*  761 */
        byte nh = (byte) (width / 256);
/*  762 */
        byte[] command = {29, 87, nl, nh};
///*  763 */     command[2] = nl;
///*  764 */     command[3] = nh;
/*  765 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addSetAutoSatusBack(ENABLE enable)
/*      */ {
/*  775 */    // byte[] command = { 29, 97 };
        byte t;
/*  776 */
        if (enable == ENABLE.OFF) {
/*  777 */       //command[2] = 0;
            t = 0;
/*      */
        } else
/*  779 */      // command[2] = -1;
            t = -1;
        byte[] command = {29, 97,t};
/*  780 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addSetFontForHRICharacter(FONT font)
/*      */ {
/*  790 */
        byte[] command = {29, 102,font.getValue()};
/*  791 */
        //command[2] = font.getValue();
/*  792 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addSetBarcodeHeight(byte height)
/*      */ {
/*  802 */
        byte[] command = {29, 104,height};
/*  803 */
       // command[2] = height;
/*  804 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addSetBarcodeWidth(byte width)
/*      */ {
/*  814 */
       // byte[] command = {29, 119};
/*  815 */
        if (width > 6)
/*  816 */ width = 6;
/*  817 */
        if (width < 2)
/*  818 */ width = 1;
/*  819 */
        //command[2] = width;
        byte[] command = {29, 119,width};
/*  820 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addSetKanjiFontMode(ENABLE DoubleWidth, ENABLE DoubleHeight, ENABLE Underline)
/*      */ {
/*  834 */
      //  byte[] command = {28, 33};
/*  835 */
        byte temp = 0;
/*  836 */
        if (DoubleWidth == ENABLE.ON)
/*  837 */ temp = (byte) (temp | 0x4);
/*  838 */
        if (DoubleHeight == ENABLE.ON)
/*  839 */ temp = (byte) (temp | 0x8);
/*  840 */
        if (Underline == ENABLE.ON)
/*  841 */ temp = (byte) (temp | 0x80);
/*  842 */
        //command[2] = temp;
        byte[] command = {28, 33,temp};
/*  843 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */
    public void addSelectKanjiMode()
/*      */ {
/*  850 */
        byte[] command = {28, 38};
/*  851 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addSetKanjiUnderLine(UNDERLINE_MODE underline)
/*      */ {
/*  860 */
        byte[] command = {28, 45,underline.getValue()};
/*  861 */
       // command[3] = underline.getValue();
/*  862 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */
    public void addCancelKanjiMode()
/*      */ {
/*  869 */
        byte[] command = {28, 46};
/*  870 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addSetKanjiLefttandRightSpace(byte left, byte right)
/*      */ {
/*  882 */
        byte[] command = {28, 83,left,right};
/*  883 */
//        command[2] = left;
///*  884 */
//        command[3] = right;
/*  885 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addSetQuadrupleModeForKanji(ENABLE enable)
/*      */ {
/*  894 */
        byte[] command = {28, 87, enable.getValue()};
/*  895 */
        //command[2] = enable.getValue();
/*  896 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addRastBitImage(Bitmap bitmap, int nWidth, int nMode)
/*      */ {
/*  907 */
        if (bitmap != null) {
/*  908 */
            int width = (nWidth + 7) / 8 * 8;
/*  909 */
            int height = bitmap.getHeight() * width / bitmap.getWidth();
/*  910 */
            Bitmap grayBitmap = GpUtils.toGrayscale(bitmap);
/*  911 */
            Bitmap rszBitmap = GpUtils.resizeImage(grayBitmap, width, height);
/*  912 */
            byte[] src = GpUtils.bitmapToBWPix(rszBitmap);
/*  913 */
            byte[] command = new byte[8];
/*  914 */
            height = src.length / width;
/*  915 */
            command[0] = 29;
/*  916 */
            command[1] = 118;
/*  917 */
            command[2] = 48;
/*  918 */
            command[3] = ((byte) (nMode & 0x1));
/*  919 */
            command[4] = ((byte) (width / 8 % 256));
/*  920 */
            command[5] = ((byte) (width / 8 / 256));
/*  921 */
            command[6] = ((byte) (height % 256));
/*  922 */
            command[7] = ((byte) (height / 256));
/*  923 */
            addArrayToCommand(command);
/*  924 */
            byte[] codecontent = GpUtils.pixToEscRastBitImageCmd(src);
/*  925 */
            for (int k = 0; k < codecontent.length; k++) {
/*  926 */
                this.Command.add(Byte.valueOf(codecontent[k]));
/*      */
            }
/*      */
        } else {
/*  929 */
            Log.d("BMP", "bmp.  null ");
/*      */
        }
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addRastBitImageWithMethod(Bitmap bitmap, int nWidth, int nMode, int method)
/*      */ {
/*  947 */
        if (bitmap != null) {
/*  948 */
            int width = (nWidth + 7) / 8 * 8;
/*  949 */
            int height = bitmap.getHeight() * width / bitmap.getWidth();
/*  950 */
            Bitmap resizeImage = GpUtils.resizeImage(bitmap, width, height);
/*  951 */
            Bitmap rszBitmap = GpUtils.filter(resizeImage, resizeImage.getWidth(), resizeImage.getHeight());
/*      */       
/*  953 */
            byte[] src = GpUtils.bitmapToBWPix(rszBitmap);
/*  954 */
            byte[] command = new byte[8];
/*  955 */
            height = src.length / width;
/*  956 */
            command[0] = 29;
/*  957 */
            command[1] = 118;
/*  958 */
            command[2] = 48;
/*  959 */
            command[3] = ((byte) (nMode & 0x1));
/*  960 */
            command[4] = ((byte) (width / 8 % 256));
/*  961 */
            command[5] = ((byte) (width / 8 / 256));
/*  962 */
            command[6] = ((byte) (height % 256));
/*  963 */
            command[7] = ((byte) (height / 256));
/*  964 */
            addArrayToCommand(command);
/*  965 */
            byte[] codecontent = GpUtils.pixToEscRastBitImageCmd(src);
/*  966 */
            for (int k = 0; k < codecontent.length; k++) {
/*  967 */
                this.Command.add(Byte.valueOf(codecontent[k]));
/*      */
            }
/*      */
        } else {
/*  970 */
            Log.d("BMP", "bmp.  null ");
/*      */
        }
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addDownloadNvBitImage(Bitmap[] bitmap)
/*      */ {
/*  981 */
        if (bitmap != null) {
/*  982 */
            Log.d("BMP", "bitmap.length " + bitmap.length);
/*  983 */
            int n = bitmap.length;
/*  984 */
            if (n > 0) {
/*  985 */
                byte[] command = new byte[3];
/*  986 */
                command[0] = 28;
/*  987 */
                command[1] = 113;
/*  988 */
                command[2] = ((byte) n);
/*  989 */
                addArrayToCommand(command);
/*  990 */
                for (int i = 0; i < n; i++) {
/*  991 */
                    int height = (bitmap[i].getHeight() + 7) / 8 * 8;
/*  992 */
                    int width = bitmap[i].getWidth() * height / bitmap[i].getHeight();
/*  993 */
                    Bitmap grayBitmap = GpUtils.toGrayscale(bitmap[i]);
/*  994 */
                    Bitmap rszBitmap = GpUtils.resizeImage(grayBitmap, width, height);
/*  995 */
                    byte[] src = GpUtils.bitmapToBWPix(rszBitmap);
/*  996 */
                    height = src.length / width;
/*  997 */
                    Log.d("BMP", "bmp  Width " + width);
/*  998 */
                    Log.d("BMP", "bmp  height " + height);
/*  999 */
                    byte[] codecontent = GpUtils.pixToEscNvBitImageCmd(src, width, height);
/* 1000 */
                    for (int k = 0; k < codecontent.length; k++) {
/* 1001 */
                        this.Command.add(Byte.valueOf(codecontent[k]));
/*      */
                    }
/*      */
                }
/*      */
            }
/*      */
        } else {
/* 1006 */
            Log.d("BMP", "bmp.  null ");
/* 1007 */
            return;
/*      */
        }
/*      */
    }

    /*      */
/*      */
    public void addPrintNvBitmap(byte n, byte mode) {
/* 1012 */
        byte[] command = {28, 112,n,mode};
/* 1013 */
//        command[2] = n;
///* 1014 */
//        command[3] = mode;
/* 1015 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addUPCA(String content)
/*      */ {
/* 1025 */
        byte[] command = new byte[4];
/* 1026 */
        command[0] = 29;
/* 1027 */
        command[1] = 107;
/* 1028 */
        command[2] = 65;
/* 1029 */
        command[3] = 11;
/* 1030 */
        if (content.length() < command[3])
/* 1031 */ return;
/* 1032 */
        addArrayToCommand(command);
/* 1033 */
        addStrToCommand(content, 11);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addUPCE(String content)
/*      */ {
/* 1043 */
        byte[] command = new byte[4];
/* 1044 */
        command[0] = 29;
/* 1045 */
        command[1] = 107;
/* 1046 */
        command[2] = 66;
/* 1047 */
        command[3] = 11;
/* 1048 */
        if (content.length() < command[3])
/* 1049 */ return;
/* 1050 */
        addArrayToCommand(command);
/* 1051 */
        addStrToCommand(content, command[3]);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addEAN13(String content)
/*      */ {
/* 1061 */
        byte[] command = new byte[4];
/* 1062 */
        command[0] = 29;
/* 1063 */
        command[1] = 107;
/* 1064 */
        command[2] = 67;
/* 1065 */
        command[3] = 12;
/* 1066 */
        if (content.length() < command[3])
/* 1067 */ return;
/* 1068 */
        addArrayToCommand(command);
/* 1069 */
        Log.d("EscCommand", "content.length" + content.length());
/* 1070 */
        addStrToCommand(content, command[3]);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addEAN8(String content)
/*      */ {
/* 1080 */
        byte[] command = new byte[4];
/* 1081 */
        command[0] = 29;
/* 1082 */
        command[1] = 107;
/* 1083 */
        command[2] = 68;
/* 1084 */
        command[3] = 7;
/* 1085 */
        if (content.length() < command[3])
/* 1086 */ return;
/* 1087 */
        addArrayToCommand(command);
/* 1088 */
        addStrToCommand(content, command[3]);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    @SuppressLint({"DefaultLocale"})
/*      */ public void addCODE39(String content)
/*      */ {
/* 1099 */
        byte[] command = new byte[4];
/* 1100 */
        command[0] = 29;
/* 1101 */
        command[1] = 107;
/* 1102 */
        command[2] = 69;
/* 1103 */
        command[3] = ((byte) content.length());
/* 1104 */
        content = content.toUpperCase();
/* 1105 */
        addArrayToCommand(command);
/* 1106 */
        addStrToCommand(content, command[3]);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addITF(String content)
/*      */ {
/* 1116 */
        byte[] command = new byte[4];
/* 1117 */
        command[0] = 29;
/* 1118 */
        command[1] = 107;
/* 1119 */
        command[2] = 70;
/* 1120 */
        command[3] = ((byte) content.length());
/* 1121 */
        addArrayToCommand(command);
/* 1122 */
        addStrToCommand(content, command[3]);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addCODABAR(String content)
/*      */ {
/* 1132 */
        byte[] command = new byte[4];
/* 1133 */
        command[0] = 29;
/* 1134 */
        command[1] = 107;
/* 1135 */
        command[2] = 71;
/* 1136 */
        command[3] = ((byte) content.length());
/* 1137 */
        addArrayToCommand(command);
/* 1138 */
        addStrToCommand(content, command[3]);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addCODE93(String content)
/*      */ {
/* 1148 */
        byte[] command = new byte[4];
/* 1149 */
        command[0] = 29;
/* 1150 */
        command[1] = 107;
/* 1151 */
        command[2] = 72;
/* 1152 */
        command[3] = ((byte) content.length());
/* 1153 */
        addArrayToCommand(command);
/* 1154 */
        addStrToCommand(content, command[3]);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addCODE128(String content)
/*      */ {
/* 1164 */
        byte[] command = new byte[4];
/* 1165 */
        command[0] = 29;
/* 1166 */
        command[1] = 107;
/* 1167 */
        command[2] = 73;
/* 1168 */
        command[3] = ((byte) content.length());
/* 1169 */
        addArrayToCommand(command);
/* 1170 */
        addStrToCommand(content, command[3]);
/*      */
    }

    /*      */
/*      */
    public String genCodeC(String content) {
/* 1174 */
        List<Byte> bytes = new ArrayList(20);
/* 1175 */
        int len = content.length();
/* 1176 */
        bytes.add(Byte.valueOf((byte) 123));
/* 1177 */
        bytes.add(Byte.valueOf((byte) 67));
/* 1178 */
        for (int i = 0; i < len; i += 2) {
/* 1179 */
            int ken = (content.charAt(i) - '0') * 10;
/* 1180 */
            int bits = content.charAt(i + 1) - '0';
/* 1181 */
            int current = ken + bits;
/* 1182 */
            bytes.add(Byte.valueOf((byte) current));
/*      */
        }
/* 1184 */
        byte[] bb = new byte[bytes.size()];
/* 1185 */
        for (int i = 0; i < bb.length; i++) {
/* 1186 */
            bb[i] = ((Byte) bytes.get(i)).byteValue();
/*      */
        }
/*      */     
/* 1189 */
        return new String(bb, 0, bb.length);
/*      */
    }

    /*      */
/*      */
    public String genCodeB(String content) {
/* 1193 */
        return String.format("{B%s", new Object[]{content});
/*      */
    }

    /*      */
/*      */
    public String genCode128(String content) {
/* 1197 */
        String regex = "([^0-9])";
/* 1198 */
        String[] str = content.split(regex);
/*      */     
/* 1200 */
        Pattern pattern = Pattern.compile(regex);
/* 1201 */
        Matcher matcher = pattern.matcher(content);
/*      */     
/* 1203 */
        String splitString = null;
/* 1204 */
        int strlen = str.length;
/*      */     
/* 1206 */
        if ((strlen > 0) &&
/* 1207 */       (matcher.find())) {
/* 1208 */
            splitString = matcher.group(0);
/*      */
        }
/*      */     
/*      */ 
/* 1212 */
        StringBuilder sb = new StringBuilder();
/* 1213 */
        for (int i = 0; i < strlen; i++) {
/* 1214 */
            String first = str[i];
/* 1215 */
            int len = first.length();
/* 1216 */
            int result = len % 2;
/* 1217 */
            if (result == 0) {
/* 1218 */
                String codeC = genCodeC(first);
/* 1219 */
                sb.append(codeC);
/*      */
            } else {
/* 1221 */
                sb.append(genCodeB(String.valueOf(first.charAt(0))));
/* 1222 */
                sb.append(genCodeC(first.substring(1, first.length())));
/*      */
            }
/* 1224 */
            if (splitString != null) {
/* 1225 */
                sb.append(genCodeB(splitString));
/* 1226 */
                splitString = null;
/*      */
            }
/*      */
        }
/*      */     
/* 1230 */
        return sb.toString();
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addSelectSizeOfModuleForQRCode(byte n)
/*      */ {
/* 1240 */
        byte[] command = {29, 40, 107, 3, 0, 49, 67, 3};
/* 1241 */
        command[7] = n;
/* 1242 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addSelectErrorCorrectionLevelForQRCode(byte n)
/*      */ {
/* 1251 */
        byte[] command = {29, 40, 107, 3, 0, 49, 69,n};
/* 1252 */
       // command[7] = n;
/* 1253 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addStoreQRCodeData(String content)
/*      */ {
/* 1262 */
        byte[] command = {29, 40, 107, 0, 0, 49, 80, 48};
/* 1263 */
        command[3] = ((byte) ((content.getBytes().length + 3) % 256));
/* 1264 */
        command[4] = ((byte) ((content.getBytes().length + 3) / 256));
/* 1265 */
        addArrayToCommand(command);
/*      */     
/* 1267 */
        byte[] bs = null;
/* 1268 */
        if (!content.equals("")) {
/*      */
            try {
/* 1270 */
                bs = content.getBytes("utf-8");
/*      */
            }
/*      */ catch (UnsupportedEncodingException e) {
/* 1273 */
                e.printStackTrace();
/*      */
            }
/* 1275 */
            for (int i = 0; i < bs.length; i++) {
/* 1276 */
                this.Command.add(Byte.valueOf(bs[i]));
/*      */
            }
/*      */
        }
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addPrintQRCode()
/*      */ {
/* 1287 */
        byte[] command = {29, 40, 107, 3, 0, 49, 81, 48};
/* 1288 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */
    public void addQueryPrinterStatus()
/*      */ {
/* 1298 */
        byte[] command = {29, 114, 1};
/* 1299 */
        addArrayToCommand(command);
/*      */
    }

    /*      */
/*      */
    public void addUserCommand(byte[] command) {
/* 1303 */
        addArrayToCommand(command);
/*      */
    }
/*      */
}


/* Location:              C:\Users\jame\Desktop\gprintersdkv22.jar!\com\gprinter\command\EscCommand.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */