package com.huotu.huobanmall.seller.common;

import android.app.Application;

import com.baidu.location.LocationClient;
import com.huotu.huobanmall.seller.bean.MerchantModel;
import com.huotu.huobanmall.seller.utils.PreferenceHelper;
import com.huotu.huobanmall.seller.utils.VolleyRequestManager;

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

        initVolley();
    }

    protected void initVolley(){
        VolleyRequestManager.init ( this );
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
        PreferenceHelper.writeString(getApplicationContext(), Constant.LOGIN_USER_INFO, Constant.LOGIN_AUTH_NICKNAME, user.getName());
        //夜间免打扰
        PreferenceHelper.writeInt(getApplicationContext(), Constant.LOGIN_USER_INFO, Constant.LOGIN_AUTH_NO_DISTURBD, user.isNoDisturbed());
        //欢迎提示
        PreferenceHelper.writeString ( getApplicationContext (), Constant.LOGIN_USER_INFO, Constant.LOGIN_AUTH_WELCOME_TIP, user.getWelcomeTip() );
        //操作者
        PreferenceHelper.writeString ( getApplicationContext (), Constant.LOGIN_USER_INFO, Constant.LOGIN_AUTH_OPERATOR, user.getOperator () );
        //店铺名称
        PreferenceHelper.writeString(getApplicationContext(), Constant.LOGIN_USER_INFO, Constant.LOGIN_AUTH_SHOPNAME, user.getTitle());
    }

    public void cleanMerchantInfo()
    {
        PreferenceHelper.clean ( getApplicationContext (), Constant.LOGIN_USER_INFO);
    }
}
