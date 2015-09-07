package com.huotu.huobanmall.seller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.baidu.location.LocationClientOption;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.bean.MJInitData;
import com.huotu.huobanmall.seller.common.Constant;
import com.huotu.huobanmall.seller.common.SellerApplication;
import com.huotu.huobanmall.seller.utils.ActivityUtils;
import com.huotu.huobanmall.seller.utils.GsonRequest;
import com.huotu.huobanmall.seller.utils.HttpParaUtils;
import com.huotu.huobanmall.seller.utils.VolleyRequestManager;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends BaseFragmentActivity {
    @Bind(R.id.splash_login)
    Button splashlogin;
    @Bind(R.id.splash_update)
    Button splashUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();

        initLocation();

        callInit();
    }

    protected void initView(){
        ButterKnife.bind(this);

        splashlogin.setOnClickListener(this);
        splashUpdate.setOnClickListener(this);


    }

    protected void callInit() {
        String url = Constant.INIT_INTERFACE;
        HttpParaUtils httpParaUtils = new HttpParaUtils();
        url = httpParaUtils.getHttpGetUrl(url, null);
        GsonRequest<MJInitData> initRequest = new GsonRequest<>(
                Request.Method.GET
                , url
                , MJInitData.class
                , null
                , initListener
                , errorListener);

        VolleyRequestManager.getRequestQueue().add(initRequest);
    }

    @Override
    public void onClick(View v) {
        if( v.getId() == R.id.splash_login){
            ActivityUtils.getInstance().showActivity(this, LoginActivity.class);
            this.finish();
        }else if(v.getId()==R.id.splash_update){
            Intent intent=new Intent(this, WebViewActivity.class);
            intent.putExtra(Constants.Extra_Url,"http://www.baidu.com");
            ActivityUtils.getInstance().showActivity(this, intent );
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系，
        int span=5000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(false);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.disableCache(true);

        SellerApplication.getInstance().getBaiduLocationClient().setLocOption(option);

        SellerApplication.getInstance().getBaiduLocationClient().start();
    }

    Response.Listener<MJInitData> initListener =new Response.Listener<MJInitData>() {
        @Override
        public void onResponse(MJInitData data ) {



        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            SimpleDialogFragment.createBuilder( SplashActivity.this , SplashActivity.this.getSupportFragmentManager())
                    .setTitle("错误信息")
                    .setMessage( volleyError.getMessage())
                    .setPositiveButtonText("关闭")
                    .show();
        }
    };
}
