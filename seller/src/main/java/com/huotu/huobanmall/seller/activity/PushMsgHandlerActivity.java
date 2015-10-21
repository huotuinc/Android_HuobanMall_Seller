package com.huotu.huobanmall.seller.activity;

import android.content.Intent;
import android.os.Bundle;

import com.huotu.huobanmall.seller.common.Constant;
import com.huotu.huobanmall.seller.utils.Util;

public class PushMsgHandlerActivity extends BaseFragmentActivity
{
    @Override
    protected void onCreate(Bundle arg0)
    {
        super.onCreate(arg0);        
        
        operationAlarm();       
        
        finish();
    }
    
    private boolean operationAlarm() {
        if(null != getIntent().getExtras()){
            final boolean isLoaded = Util.isActivityLoaded(this);
            
             System.out.println(">>>>isRunning:" + isLoaded);
             
            int type = 0;
            if( getIntent().hasExtra("type")){
                type = getIntent().getIntExtra( "type", 0);
            }
           
            if( type == Constant.MESSAGE_TYPE_SYSTEMMESSAGE){
                //如果是系统消息，则跳转到消息界面
                Intent intentmsg =null;
                if(isLoaded){
                     intentmsg = new Intent(this, MessageActivity.class);
                 }else{
                     intentmsg = new Intent(this, SplashActivity.class);
                     intentmsg.putExtra("type",  type);
                 }
                startActivity(intentmsg);
                return true;
            }
        }
        return false;
    }

}
