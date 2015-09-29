package com.huotu.huobanmall.seller.common;

import android.app.Application;

import com.baidu.location.LocationClient;
import com.huotu.huobanmall.seller.bean.GlobalModel;
import com.huotu.huobanmall.seller.bean.MerchantModel;
import com.huotu.huobanmall.seller.utils.PreferenceHelper;
import com.huotu.huobanmall.seller.utils.VolleyRequestManager;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2015/8/24.
 */
public class SellerApplication extends Application{
    private static SellerApplication app;

    private BaiduLocation _baiduLocation;

    public SellerApplication() {
        app = this;
    }

    public static synchronized SellerApplication getInstance() {
        if (app == null) {
            app = new SellerApplication();
        }
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _baiduLocation =new BaiduLocation();

        initJPush();

        initVolley();
    }

    protected void initJPush(){
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush


    }

    protected void registerJPush_Alias(){

    }

    protected void initVolley(){
        VolleyRequestManager.init(this);
    }

    public LocationClient getBaiduLocationClient(){
        return _baiduLocation.mLocationClient;
    }

    public void writeUserToken(String token)
    {
        PreferenceHelper.writeString ( getApplicationContext (), Constant.LOGIN_USER_INFO, Constant
                                               .PRE_USER_TOKEN, token );
    }

    public String readUserToken()
    {
        return PreferenceHelper.readString (
                getApplicationContext ( ), Constant.LOGIN_USER_INFO, Constant
                        .PRE_USER_TOKEN
                                    );
    }

    public void writeGlobalInfo(GlobalModel globalModel){
        if( globalModel==null)return;
        //关于我们url
        PreferenceHelper.writeString( getApplicationContext(), Constant.LOGIN_GLOBAL_INFO , Constant.LOGIN_GLOBAL_ABOUTURL , globalModel.getAboutURL() );
        //
        PreferenceHelper.writeString(this,Constant.LOGIN_GLOBAL_INFO,Constant.LOGIN_GLOBAL_SERVERURL,globalModel.getServerUrl());

        PreferenceHelper.writeString(this,Constant.LOGIN_GLOBAL_INFO,Constant.LOGIN_GLOBAL_HELPURL,globalModel.getHelpURL());
    }

    public void writeMerchantInfo(MerchantModel user)
    {
        //店铺权限
        PreferenceHelper.writeString(getApplicationContext(), Constant.LOGIN_USER_INFO, Constant.LOGIN_AUTH_AUTHORITY, user.getAuthority());
        //店铺描述
        PreferenceHelper.writeString(
                getApplicationContext(), Constant.LOGIN_USER_INFO,
                Constant.LOGIN_AUTH_DISCRIPTION, user.getDiscription()
        );
        //支付订单通知
        PreferenceHelper.writeInt(
                getApplicationContext(), Constant.LOGIN_USER_INFO, Constant
                        .LOGIN_AUTH_ENABLE_BILL_NOTICE, user.getEnableBillNotice
                        ()
        );
        //新增小伙伴通知
        PreferenceHelper.writeInt(getApplicationContext(), Constant.LOGIN_USER_INFO, Constant
                .LOGIN_AUTH_ENABLE_PARTNER_NOTICE, user
                .getEnableBillNotice());
        //店铺logo
        PreferenceHelper.writeString ( getApplicationContext (), Constant.LOGIN_USER_INFO, Constant.LOGIN_AUTH_LOGO, user.getLogo() );
        //用户Token
        PreferenceHelper.writeString ( getApplicationContext (), Constant.LOGIN_USER_INFO, Constant
                                               .PRE_USER_TOKEN, user.getToken() );
        //用户手机
        PreferenceHelper.writeString ( getApplicationContext (), Constant.LOGIN_USER_INFO, Constant.LOGIN_AUTH_MOBILE, user.getMobile() );
        //用户名
        PreferenceHelper.writeString(getApplicationContext(), Constant.LOGIN_USER_INFO, Constant.LOGIN_AUTH_NAME, user.getName());
        //昵称
        PreferenceHelper.writeString(getApplicationContext(), Constant.LOGIN_USER_INFO, Constant.LOGIN_AUTH_NICKNAME, user.getNickName());
        //夜间免打扰
        PreferenceHelper.writeInt(getApplicationContext(), Constant.LOGIN_USER_INFO, Constant.LOGIN_AUTH_NO_DISTURBD, user.isNoDisturbed());
        //欢迎提示
        PreferenceHelper.writeString ( getApplicationContext (), Constant.LOGIN_USER_INFO, Constant.LOGIN_AUTH_WELCOME_TIP, user.getWelcomeTip() );
        //操作者
        PreferenceHelper.writeString ( getApplicationContext (), Constant.LOGIN_USER_INFO, Constant.LOGIN_AUTH_OPERATOR, user.getOperatored () );
        //店铺名称
        PreferenceHelper.writeString(getApplicationContext(), Constant.LOGIN_USER_INFO, Constant.LOGIN_AUTH_SHOPNAME, user.getTitle());
        //店铺url
        PreferenceHelper.writeString( getApplicationContext(), Constant.LOGIN_USER_INFO,Constant.LOGIN_AUTH_SHOPURL , user.getIndexUrl() );
    }

    public void cleanMerchantInfo()
    {
        PreferenceHelper.clean ( getApplicationContext (), Constant.LOGIN_USER_INFO);
    }
}
