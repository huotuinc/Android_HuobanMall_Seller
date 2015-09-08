package com.huotu.huobanmall.seller.utils;

/**
 * 字符串操作
 */
public
class StringUtils {

    public static boolean isEmpty(String str)
    {
        if(null == str || "".equals ( str.trim () ))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
