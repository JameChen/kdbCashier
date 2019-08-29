package com.yiku.kdb_flat.utils;

import java.util.List;

public class ListUtils {

    /**
     * @description 判断list是不是空
     * @created 2015-4-13 下午5:51:24
     * @author ZZB
     */
    public static boolean isEmpty(List list){
        if (list==null){
            return true;
        }else {
            if (list.size()==0){
                return  true;
            }else {
                return  false;
            }
        }
    }
    
    /**
     * @description 获取list的长度
     * @created 2015-4-13 下午5:53:18
     * @author ZZB
     */
    public static int getSize(List list){
        return isEmpty(list) ? 0 : list.size();
    }
}
