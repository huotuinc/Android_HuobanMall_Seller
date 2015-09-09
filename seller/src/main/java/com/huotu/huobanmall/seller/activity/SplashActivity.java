package com.huotu.huobanmall.seller.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.baidu.location.LocationClientOption;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.bean.HTInitBean;
import com.huotu.huobanmall.seller.bean.MJInitData;
import com.huotu.huobanmall.seller.bean.MerchantModel;
import com.huotu.huobanmall.seller.common.Constant;
import com.huotu.huobanmall.seller.common.SellerApplication;
import com.huotu.huobanmall.seller.utils.ActivityUtils;
import com.huotu.huobanmall.seller.utils.GsonRequest;
import com.huotu.huobanmall.seller.utils.HttpParaUtils;
import com.huotu.huobanmall.seller.utils.JSONUtil;
import com.huotu.huobanmall.seller.utils.KJLoger;
import com.huotu.huobanmall.seller.utils.ObtainParamsMap;
import com.huotu.huobanmall.seller.utils.StringUtils;
import com.huotu.huobanmall.seller.utils.VolleyRequestManager;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends BaseFragmentActivity {

    @Bind(R.id.loadL)
    RelativeLayout loadLayout;
    public SellerApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (SellerApplication) SplashActivity.this.getApplication();
        setContentView(R.layout.activity_splash);


        initView();
        handlerView();
    }

    protected void initView() {
        loadLayout = (RelativeLayout) this.findViewById(R.id.loadL);

        ButterKnife.bind(this);

    }



    private void handlerView() {
        AlphaAnimation anima = new AlphaAnimation(0.0f, 1.0f);
        anima.setDuration(Constant.ANIMATION_COUNT);// 设置动画显示时间
        loadLayout.setAnimation(anima);
        anima.setAnimationListener(
                new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {

                        //百度定位
                        initLocation();


                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                        //调用初始化接口
                        callInit();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {


                    }
                });
    }


    protected
    void callInit ( ) {


        String url = Constant.INIT_INTERFACE;
        Map<String, String> map = new HashMap<>();
        HttpParaUtils utils = new HttpParaUtils();
        url = utils.getHttpGetUrl(url, null);



        GsonRequest<HTInitBean> initRequest = new GsonRequest<HTInitBean>(Request.Method.GET,
                url,
                HTInitBean.class,
                null,
                htInitBeanListener,
                errorListener
        );

        initRequest.setRetryPolicy( new DefaultRetryPolicy(20*1000,1,1.0f));

        VolleyRequestManager.getRequestQueue().add(initRequest);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系，
        int span = 5000;
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

    Response.Listener<HTInitBean> htInitBeanListener = new Response.Listener<HTInitBean>() {
        @Override
        public void onResponse(HTInitBean htInitBean) {
            //更新Token信息
            MerchantModel user = htInitBean.getResultData ().getUser ();
            if(null != user)
            {
                String token = user.getToken ();
                //记录商户信息
                application.writeMerchantInfo ( user );
                if( !StringUtils.isEmpty ( token ))
                {
                    //直接登录
                    ActivityUtils.getInstance().skipActivity ( SplashActivity.this, MainActivity.class);
                }
                else
                {
                    //跳转到登录界面
                    ActivityUtils.getInstance().skipActivity ( SplashActivity.this, LoginActivity.class );
                }
            }
            else
            {
                //跳转到登录界面
                ActivityUtils.getInstance().skipActivity(SplashActivity.this, LoginActivity.class);
            }
        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            //提示初始化失败,服务器问题
            SimpleDialogFragment.createBuilder( SplashActivity.this , SplashActivity.this.getSupportFragmentManager())
                    .setTitle("错误信息")
                    .setTitle(volleyError.getMessage())
                    .setNegativeButtonText("关闭")
                    .show();
        }
    };
}
