package com.huotu.huobanmall.seller.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.nfc.Tag;
import android.os.StrictMode;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by Administrator on 2015/8/25.
 */
public class SystemUtils {
    private static String TAG= SystemUtils.class.getName();
    /**
     * 获得手机IMEI码
     */
    public static String getPhoneIMEI( Context context ){
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }
    /**
     * 获取当前应用程序的版本号
     */
    public static String getAppVersion(Context context)
    {
        String version = "0";
        try
        {
            version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e)
        {
            Log.e( TAG , e.getMessage());
        }
        return version;
    }
}
