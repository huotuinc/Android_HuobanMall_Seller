package com.huotu.huobanmall.seller.receiver;

import java.io.Serializable;
import java.net.ProtocolException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.JsonSyntaxException;
import com.huotu.huobanmall.seller.activity.BaseFragmentActivity;
import com.huotu.huobanmall.seller.activity.ConsumeStatisticsActivity;
import com.huotu.huobanmall.seller.activity.OrderActivity;
import com.huotu.huobanmall.seller.activity.PushMsgHandlerActivity;
import com.huotu.huobanmall.seller.bean.BaseModel;
import com.huotu.huobanmall.seller.bean.JPushBean;
import com.huotu.huobanmall.seller.common.Constant;
import com.huotu.huobanmall.seller.common.SellerApplication;
import com.huotu.huobanmall.seller.utils.GsonRequest;
import com.huotu.huobanmall.seller.utils.HttpParaUtils;
import com.huotu.huobanmall.seller.utils.JSONUtil;
import com.huotu.huobanmall.seller.utils.KJLoger;
import com.huotu.huobanmall.seller.utils.PreferenceHelper;
import com.huotu.huobanmall.seller.utils.SystemTools;
import com.huotu.huobanmall.seller.utils.SystemUtils;
import com.huotu.huobanmall.seller.utils.VolleyRequestManager;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则： 1) 默认用户会打开主界面 2) 接收不到自定义消息
 */
public class MJJPushReceiver extends BroadcastReceiver
{
    private static final String TAG = "JPush";

    public SellerApplication application;

    private String imei = null;

    private String regId = null;

    @Override
    public void onReceive(final Context context, Intent intent)
    {
        Bundle bundle = intent.getExtras();
        Log.d( TAG, "onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())){
            // 注册后获取极光全局ID
            regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            //imei = SystemUtils.getPhoneIMEI(context);

            registerJPush_Alias( context );

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction()))
        {
            // 处理接受的自定义消息
            int notifactionId = bundle
                    .getInt(JPushInterface.EXTRA_NOTIFICATION_ID);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
                .getAction()))
        {

            String regId = JPushInterface.getRegistrationID(context);
            // 接收到了自定义的通知
            // 通知ID
            String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
//            // json封装成bean
//            JBean bean = new JBean();
//            JSONUtil<JBean> jsonUtil = new JSONUtil<JBean>();
//            bean = jsonUtil.toBean(extra, bean);
//            bean.setTitle(title);
//            if( null !=bean && bean.getType().equals("2")){
//                //在文件中设置请求流量标记
//                MyBroadcastReceiver.sendBroadcast(context , MyBroadcastReceiver.ACTION_REQUESTFLOW);
//                MyApplication.writeBoolean( context , Constant.LOGIN_USER_INFO , bean.getUsername() , true);
//            }


        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
                .getAction()))
        {
            // 接受点击通知事件
            String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
            // json封装成bean
            JPushBean bean = new JPushBean();
            JSONUtil<JPushBean> jsonUtil = new JSONUtil<JPushBean>();
            bean = jsonUtil.toBean(extra, bean);
            bean.setTitle(title);

            // 根据type判断
            switch (Integer.parseInt(bean.getType()))
            {
            case 1:
            {
                // 赠送流量
                //sendFlowMessage(context, bean);
            }
                break;
            case 2:
            case 3:
            {
                sellerMessage(context);
            }
            break;
            case 6:
            {
                // 通知
                notificationMessage(context, bean);
            }
                break;
            default:
                break;
            }
            // 添加统计
            JPushInterface.reportNotificationOpened(context,
                    bundle.getString(JPushInterface.EXTRA_MSG_ID));

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent
                .getAction()))
        {
            // 在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity，
            // 打开一个网页等..
            // 接受富文本框
            int notifactionId = bundle
                    .getInt(JPushInterface.EXTRA_NOTIFICATION_ID);

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent
                .getAction()))
        {
            String regId = JPushInterface.getRegistrationID(context);

            boolean connected = intent.getBooleanExtra(
                    JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            // 处理网络变更事件

            //sellerMessage( context );

        } else
        {

        }
    }


    /**
     * 注册 JPUSH 的别名
     */
    protected void registerJPush_Alias( Context  context ){
        String url = Constant.UPDATEDEVICETOKEN_INTERFACE;
        HttpParaUtils httpParaUtils = new HttpParaUtils();
        String imei = SystemUtils.getPhoneIMEI( context  );
        Map<String,String> paras = new HashMap<>();
        paras.put("deviceToken", imei);
        url = httpParaUtils.getHttpGetUrl(url, paras);
        GsonRequest<BaseModel> request = new GsonRequest<BaseModel>(
                Request.Method.GET,
                url,
                BaseModel.class,
                null,
                listener,
                errorListener
        );

        VolleyRequestManager.getRequestQueue().add(request);
    }

    protected Response.Listener<BaseModel> listener = new Response.Listener<BaseModel>() {
        @Override
        public void onResponse(BaseModel baseModel) {
            final String imei = SystemUtils.getPhoneIMEI(SellerApplication.getInstance());
            if( baseModel != null && baseModel.getSystemResultCode() ==1 && baseModel.getResultCode() ==1 ){
                JPushInterface.setAlias(SellerApplication.getInstance(), imei, new TagAliasCallback() {
                    @Override
                    public void gotResult(int i, String s, Set<String> set) {
                        if( i == 0 ){
                            Log.i(TAG , "设置别名成功！");
                            PreferenceHelper.writeString( SellerApplication.getInstance() , Constant.PUSH_INFO,  Constant.PUSH_INFO_ALIAS,      imei);
                        }else if( i== 6002){
                            Log.i(TAG,"设置别名超时!");
                        }else{
                            Log.i(TAG, "返回码："+ i);
                        }
                    }
                });
            }
        }
    };

    protected Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            String message="";
            if( null != volleyError.networkResponse){
                message=new String( volleyError.networkResponse.data);
            }else{
                message = volleyError.getMessage();
            }
            if( message.length()<1){
                message = "网络请求失败，请检查网络状态";
            }
        }
    };
    /**
     * 
     * @创建人：jinxiangdong
     * @修改时间：2015年7月2日 下午2:40:59
     * @方法描述：
     * @方法名：notificationMessage
     * @参数：@param context
     * @参数：@param bean
     * @返回：void
     * @exception
     * @since
     */
    protected void notificationMessage(Context context, JPushBean bean)
    {
        if (bean == null)
            return;
        
        String message = bean.getTitle();
        final Dialog dlg = new AlertDialog.Builder(context)
        .setTitle("消息")
        .setMessage(message)
        .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        })
        .create();
        
        dlg.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        
        dlg.show();
    }


    protected void sellerMessage( Context context ){
        Intent intent = new Intent(context, PushMsgHandlerActivity.class);
        intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("type", Constant.MESSAGE_TYPE_SYSTEMMESSAGE);
        context.startActivity(intent);
    }

    /**
     *    打印所有的 intent extra 数据
    */
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            }else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            }
            else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }
}
