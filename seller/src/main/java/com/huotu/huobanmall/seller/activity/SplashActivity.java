package com.huotu.huobanmall.seller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.avast.android.dialogs.iface.ISimpleDialogListener;
import com.baidu.location.LocationClientOption;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.bean.HTInitBean;
import com.huotu.huobanmall.seller.bean.MerchantModel;
import com.huotu.huobanmall.seller.bean.UpdateModel;
import com.huotu.huobanmall.seller.bean.VersionUpdateTypeEnum;
import com.huotu.huobanmall.seller.common.Constant;
import com.huotu.huobanmall.seller.common.SellerApplication;
import com.huotu.huobanmall.seller.utils.ActivityUtils;
import com.huotu.huobanmall.seller.utils.DialogUtils;
import com.huotu.huobanmall.seller.utils.GsonRequest;
import com.huotu.huobanmall.seller.utils.HttpParaUtils;
import com.huotu.huobanmall.seller.utils.StringUtils;
import com.huotu.huobanmall.seller.utils.ToastUtils;
import com.huotu.huobanmall.seller.utils.VolleyRequestManager;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 启动界面
 */
public class SplashActivity extends BaseFragmentActivity implements ISimpleDialogListener {
    @Bind(R.id.loadL)
    RelativeLayout loadLayout;
    //public SellerApplication application;
    //升级APP 请求代码
    public static final int REQUESTCODE_UPDATE= 6001;

    //public static final int REQUESTCODE_CLOSE=6008;
    HTInitBean _data=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //application = (SellerApplication) SplashActivity.this.getApplication();
        setContentView(R.layout.activity_splash);
        initView();
        handlerView();
    }

    protected void initView() {
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

    protected void callInit ( ) {
        String url = Constant.INIT_INTERFACE;
        HttpParaUtils utils = new HttpParaUtils();
        url = utils.getHttpGetUrl(url, null);
        GsonRequest<HTInitBean> initRequest = new GsonRequest<>(
                Request.Method.GET,
                url,
                HTInitBean.class,
                null,
                htInitBeanListener,
                this
        );
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
            if( htInitBean == null ){
                //DialogUtils.showDialog(SplashActivity.this,SplashActivity.this.getSupportFragmentManager(),"错误信息","请求数据失败","关闭");
                ToastUtils.showLong("请求数据失败");
                SplashActivity.this.finish();
                return;
            }
            if( htInitBean.getSystemResultCode() != 1 ){
                //DialogUtils.showDialog(SplashActivity.this,SplashActivity.this.getSupportFragmentManager(),"错误信息",htInitBean.getSystemResultDescription(),"关闭");
                ToastUtils.showLong(htInitBean.getSystemResultDescription());
                SplashActivity.this.finish();
                return;
            }
            if( htInitBean.getResultCode() == Constant.TOKEN_OVERDUE ||
                htInitBean.getResultCode() == Constant.ERROR_USER_PASSWORD ){
                //调转到登录界面
                ActivityUtils.getInstance().skipActivity ( SplashActivity.this, LoginActivity.class);
                return;
            }

            if( htInitBean.getResultCode() != 1){
                //DialogUtils.showDialog(SplashActivity.this,SplashActivity.this.getSupportFragmentManager(),"错误信息",htInitBean.getResultDescription(),"关闭");
                ToastUtils.showLong( htInitBean.getResultDescription());
                SplashActivity.this.finish();
                return;
            }

            if( htInitBean.getResultData() ==null  ){
                ToastUtils.showLong( "启动失败，返回的数据有问题。" );
                SplashActivity.this.finish();
                return;
            }

            _data = htInitBean;

            SellerApplication.getInstance().writeGlobalInfo(htInitBean.getResultData().getGlobal());

            updateApp( htInitBean.getResultData().getUpdate() );

            //更新Token信息
            MerchantModel user = htInitBean.getResultData().getUser();
            if(null != user)
            {
                String token = user.getToken ();
                //记录商户信息
                SellerApplication.getInstance().writeMerchantInfo(user);
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


    @Override
    public void onErrorResponse(VolleyError volleyError) {
        //super.onErrorResponse(volleyError);
        ToastUtils.showLong("访问失败，请重试");
        SplashActivity.this.finish();
    }

    @Override
    public void onNegativeButtonClicked(int i) {
        if( i == REQUESTCODE_UPDATE ){
            ToastUtils.showLong("用户取消升级APP");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( requestCode == Constant.REQUEST_CODE_CLIENT_DOWNLOAD
            && resultCode == Constant.RESULT_CODE_CLIENT_DOWNLOAD_FAILED){
            Bundle bd = data.getExtras();
            if (bd != null) {
                boolean isForce = bd.getBoolean("isForce");
                if (isForce) {
                    finish();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onNeutralButtonClicked(int i) {

    }

    @Override
    public void onPositiveButtonClicked(int i) {
        if( i== REQUESTCODE_UPDATE){
            updateAppNow( _data.getResultData().getUpdate() );
        }
    }

    protected void updateAppNow( UpdateModel model ){
        boolean isForce=false;
        AppUpdateActivity.UpdateType type= AppUpdateActivity.UpdateType.FullUpate;
        String md5= model.getUpdateMD5(); //"sadfsafsafafd121";
        String url= model.getUpdateUrl(); //"http://cdn4.ops.baidu.com/new-repackonline/baidunuomi/AndroidPhone/5.12.0.1/1/1009769b/20150810142355/baidunuomi_AndroidPhone_5-12-0-1_1009769b.apk"; //"http://newresources.fanmore.cn/fanmore/fanmore3.0.apk";
        String tips= model.getUpdateTips(); //"本文版权归作者和博客园共有，欢迎转载，但未经作者同意必须保留此段声明，且在文章页面明显位置给出原文连接，否则保留追究法律责任的权利.";

        Intent intent = new Intent( SplashActivity.this, AppUpdateActivity.class);
        intent.putExtra("isForce", isForce);
        intent.putExtra("type", type);
        intent.putExtra("md5", md5);
        intent.putExtra("url", url);
        intent.putExtra("tips", tips);
        startActivityForResult(intent, Constant.REQUEST_CODE_CLIENT_DOWNLOAD);
    }

    /**
     *
     * @param updateInfo
     */
    protected void updateApp( UpdateModel updateInfo ){
        if( updateInfo == null || updateInfo.getUpdateType()==null )return;
        if( updateInfo.getUpdateType().getValue() == VersionUpdateTypeEnum.NO.getIndex() ) return;

        if( updateInfo.getUpdateType().getValue() == VersionUpdateTypeEnum.FORCE_WHOLE.getIndex() ){
            //强制整包更新
            updateAppNow( updateInfo );
        }else if( updateInfo.getUpdateType().getValue() == VersionUpdateTypeEnum.WHOLE.getIndex() ){
            //整包更新
            SimpleDialogFragment.createBuilder( SplashActivity.this , SplashActivity.this.getSupportFragmentManager() )
                    .setTitle("询问")
                    .setMessage( updateInfo.getUpdateTips() )
                    .setPositiveButtonText("升级")
                    .setNegativeButtonText("下次再说")
                    .setRequestCode( REQUESTCODE_UPDATE)
                    .show();
        }

    }
}
