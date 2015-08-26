package com.huotu.huobanmall.seller.common;

import android.app.Application;

import com.baidu.location.LocationClient;
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
        VolleyRequestManager.init(this);
    }

    public LocationClient getBaiduLocationClient(){
        return _baiduLocation.mLocationClient;
    }
}
