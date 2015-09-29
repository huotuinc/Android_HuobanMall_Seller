package com.huotu.huobanmall.seller.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.huotu.huobanmall.seller.common.SellerApplication;
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

    private static Toast  mToast=null;
    public static void showLong(  String msg){
        if( mToast ==null){
            mToast = Toast.makeText( SellerApplication.getInstance() , msg , Toast.LENGTH_LONG );
            //mToast.setMargin(10,10);
            mToast.setGravity(Gravity.CENTER , 0,0);
        }
        else{
            mToast.setText(msg);
        }
        mToast.show();
    }


    public static void showShort( String msg){
        if( mToast ==null){
            mToast = Toast.makeText( SellerApplication.getInstance() , msg , Toast.LENGTH_SHORT );
        }
        else{
            mToast.setText(msg);
        }
        mToast.show();
    }

    public  static void cancel(){
        if( mToast !=null){
            mToast.cancel();
        }
    }
}
