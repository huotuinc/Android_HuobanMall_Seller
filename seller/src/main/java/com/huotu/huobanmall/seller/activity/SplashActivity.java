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

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;

/**
 * 启动界面
 */
public class SplashActivity extends BaseFragmentActivity implements ISimpleDialogListener {
    @Bind(R.id.loadL)
    RelativeLayout loadLayout;
    //升级APP 请求代码
    public static final int REQUESTCODE_UPDATE= 6001;
    HTInitBean _data=null;
    //消息的类型
    private int messageType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initJPush();

        initView();
        handlerView();
    }

    protected void initJPush(){
            if(  JPushInterface.isPushStopped(SplashActivity.this))
            {
                JPushInterface.resumePush(SplashActivity.this);
            }
    }



    protected void initView() {
        ButterKnife.bind(this);
        if(getIntent().hasExtra("type")){
            messageType = getIntent().getIntExtra("type", 0);
        }
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
        if( false== canConnect() ){
            this.finish();
            return;
        }

        String url = Constant.INIT_INTERFACE;
        HttpParaUtils utils = new HttpParaUtils();
        url = utils.getHttpGetUrl(url, null);
        GsonRequest<HTInitBean> initRequest = new GsonRequest<>(
                Request.Method.GET,
                url,
                HTInitBean.class,
                null,
                new MyListener(this),
                new MJErrorListener(this)
        );
        VolleyRequestManager.AddRequest(initRequest);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);

        VolleyRequestManager.cancelAllRequest();
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

    //Response.Listener<HTInitBean> htInitBeanListener = new Response.Listener<HTInitBean>() {

    static class MyListener implements Response.Listener<HTInitBean>{
        WeakReference<SplashActivity> ref;
        public MyListener(SplashActivity act){
            ref = new WeakReference<SplashActivity>(act);
        }

        @Override
        public void onResponse(HTInitBean htInitBean) {
            if( ref.get()==null) return;

            if( ref.get().isFinishing() ) return;

            ref.get()._data = htInitBean;
            if( htInitBean == null ){
                ToastUtils.showLong("请求数据失败");
                ref.get().finish();
                return;
            }
            if( htInitBean.getSystemResultCode() != 1 ){
                ToastUtils.showLong(htInitBean.getSystemResultDescription());
                ref.get().finish();
                return;
            }

            //判断是否需要升级
            if( htInitBean.getResultData() !=null ){
                boolean isNeedUpdate =  ref.get().judgeUpdate(htInitBean.getResultData().getUpdate());
                if( isNeedUpdate ) {
                    ref.get().updateApp(htInitBean.getResultData().getUpdate());
                    return;
                }
            }

            ref.get().gotoMain();

        }
    }

    protected void gotoMain(){
        if( _data.getResultCode() == Constant.TOKEN_OVERDUE ||
            _data.getResultCode() == Constant.ERROR_USER_PASSWORD ){
            //调转到登录界面
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            intent.putExtra("type", messageType);
            ActivityUtils.getInstance().skipActivity ( SplashActivity.this, intent);
            return;
        }

        if( _data.getResultCode() != 1){
            ToastUtils.showLong( _data.getResultDescription());
            SplashActivity.this.finish();
            return;
        }

        if( _data.getResultData() ==null  ){
            ToastUtils.showLong( "启动失败，返回的数据有问题。" );
            SplashActivity.this.finish();
            return;
        }

        SellerApplication.getInstance().writeGlobalInfo(_data.getResultData().getGlobal());

        //更新Token信息
        MerchantModel user = _data.getResultData().getUser();
        if(null != user){
            String token = user.getToken ();
            //记录商户信息
            SellerApplication.getInstance().writeMerchantInfo(user);
            if( !StringUtils.isEmpty ( token )){
                // 跳转到首页
                Intent intent = new Intent();
                intent.putExtra("type", messageType);
                intent.setClass( SplashActivity.this , MainActivity.class );
                ActivityUtils.getInstance().skipActivity ( SplashActivity.this, intent);
            }
            else{
                //跳转到登录界面
                ActivityUtils.getInstance().skipActivity ( SplashActivity.this, LoginActivity.class );
            }
        }
        else{
            //跳转到登录界面
            ActivityUtils.getInstance().skipActivity(SplashActivity.this, LoginActivity.class);
        }
    }

    /**
     * 判断 App 是否要升级
     * @param updateInfo
     * @return
     */
    protected boolean judgeUpdate( UpdateModel updateInfo ){
        if( updateInfo == null || updateInfo.getUpdateType()==null ) return false;
        if( updateInfo.getUpdateType().getValue() == VersionUpdateTypeEnum.NO.getIndex() ) return false;

        if( updateInfo.getUpdateType().getValue() == VersionUpdateTypeEnum.FORCE_WHOLE.getIndex() ){
            //强制整包更新
            //updateAppNow( updateInfo );
            return true;
        }else if( updateInfo.getUpdateType().getValue() == VersionUpdateTypeEnum.WHOLE.getIndex() ){
            //整包更新
            return true;
        }
        return false;
    }


    @Override
    public void onErrorResponse(VolleyError volleyError) {
        if( SplashActivity.this.isFinishing() ) return;

        ToastUtils.showLong("访问失败，请重试");
        SplashActivity.this.finish();
    }

//    Response.ErrorListener errorListener =new Response.ErrorListener() {
//        @Override
//        public void onErrorResponse(VolleyError volleyError) {
//            if( SplashActivity.this.isFinishing() ) return;
//            ToastUtils.showLong("访问失败，请重试");
//            SplashActivity.this.finish();
//        }
//    };

    @Override
    public void onNegativeButtonClicked(int i) {
        if( i == REQUESTCODE_UPDATE ){
            ToastUtils.showLong("用户取消升级APP");
            gotoMain();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( requestCode == Constant.REQUEST_CODE_CLIENT_DOWNLOAD
            && resultCode == Constant.RESULT_CODE_CLIENT_DOWNLOAD_FAILED){

            finish();
        }
        if( requestCode == Constant.REQUEST_CODE_CLIENT_DOWNLOAD && resultCode == 0 ){
            finish();
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
        String md5= model.getUpdateMD5();
        String url= model.getUpdateUrl(); //
        String tips= model.getUpdateTips(); //

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
