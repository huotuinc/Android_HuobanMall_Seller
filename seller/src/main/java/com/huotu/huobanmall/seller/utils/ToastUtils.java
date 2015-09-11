package com.huotu.huobanmall.seller.utils;

import android.content.Context;
import android.widget.Toast;

import com.huotu.huobanmall.seller.widget.AlarmDailog;


public class ToastUtils
{
    private static AlarmDailog alarmDialog;

    public static void showShortToast(Context context, String showMsg)
    {
        if (null != alarmDialog)
        {
            alarmDialog = null;
        }
        alarmDialog = new AlarmDailog(context, showMsg);
        alarmDialog.setDuration(Toast.LENGTH_SHORT);
        alarmDialog.show();

    }

    public static void showLongToast(Context context, String showMsg)
    {
        if (null != alarmDialog)
        {
            alarmDialog = null;
        }
        alarmDialog = new AlarmDailog(context, showMsg);
        alarmDialog.show();
    }

    public static void showLongToast(Context context, String showMsg , boolean singleLine)
    {
        if (null != alarmDialog)
        {
            alarmDialog = null;
        }
        alarmDialog = new AlarmDailog(context, showMsg,singleLine);
        alarmDialog.show();
    }
}
