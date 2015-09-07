package com.huotu.huobanmall.seller.common;

import android.location.LocationListener;

import com.baidu.location.LocationClient;

/**
 * Created by Administrator on 2015/8/25.
 */
public class BaiduLocation {
    public LocationClient mLocationClient;
    public com.huotu.huobanmall.seller.common.SellerLocationListener mSellerLocationListener;

    public BaiduLocation(){
        mLocationClient = new LocationClient( com.huotu.huobanmall.seller.common.SellerApplication.getInstance());
        mSellerLocationListener =new com.huotu.huobanmall.seller.common.SellerLocationListener();
        mLocationClient.registerLocationListener( mSellerLocationListener);
    }
}
