/*    */
package com.gprinter.util;
/*    */ 
/*    */

import android.content.Context;


/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */

/*    */
/*    */ public class DBUtil
/*    */ {
    /*    */
    public static DBUtil getDB(Context context)
/*    */ {
/* 17 */
       // if (GpPrintService.db == null) {
/* 18 */
//            DaoConfig config = new DaoConfig(context);
///* 19 */
//            config.setDbName("smartprint.db");
///* 20 */
//            config.setDbVersion(1);
///* 21 */
//            config.setDbDir(GpPrintService.DB_DIR);
///* 22 */
//            GpPrintService.db = DbUtils.create(config);
///*    */
         //  try {
///* 24 */
//                if (GpPrintService.db != null) {
///* 25 */
//                    GpPrintService.db.createTableIfNotExist(LogModel.class);
///* 26 */
//                    GpPrintService.db.createTableIfNotExist(PrintModel.class);
///*    */
///* 28 */
//                    GpPrintService.db.createTableIfNotExist(com.gprinter.model.DeviceInfoModel.class);
///* 29 */
//                    GpPrintService.db.createTableIfNotExist(DataInfoModel.class);
///* 30 */
//                    GpPrintService.db.createTableIfNotExist(DataInfoLog.class);
///*    */
//                }
///*    */
//            } catch (Exception e) {
///* 33 */
//                e.printStackTrace();
///*    */
//            }
/*    */
       // }
/* 36 */
        return null;
/*    */
    }
/*    */
}
