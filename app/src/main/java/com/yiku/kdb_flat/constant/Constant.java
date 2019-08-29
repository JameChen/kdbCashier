package com.yiku.kdb_flat.constant;

import com.yiku.kdb_flat.BWApplication;

import java.io.File;

/**
 * Created by jame on 2018/12/3.
 */

public class Constant {
    public static final String PATH_DATA = BWApplication.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";
    public static final String PATH_CACHE = PATH_DATA + "/NetCache";
    public static final int LOGIN_REGISTER_FROM_ANDROID = 8;
    public class SHOP_TYPEID{
        public static final int TypeID_Defaut = 0;
        public static final int TypeID_Exchang_Goods = 1;
    }

}
