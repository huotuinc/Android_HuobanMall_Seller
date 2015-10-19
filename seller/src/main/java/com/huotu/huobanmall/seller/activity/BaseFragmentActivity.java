package com.huotu.huobanmall.seller.activity;

import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.avast.android.dialogs.fragment.ProgressDialogFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.bean.BaseModel;
import com.huotu.huobanmall.seller.bean.RoleEnum;
import com.huotu.huobanmall.seller.common.Constant;
import com.huotu.huobanmall.seller.utils.ActivityUtils;
import com.huotu.huobanmall.seller.utils.DialogUtils;
import com.huotu.huobanmall.seller.utils.PreferenceHelper;
import com.huotu.huobanmall.seller.utils.ToastUtils;
import com.huotu.huobanmall.seller.utils.Util;
import com.umeng.analytics.MobclickAgent;

import java.lang.ref.WeakReference;

import cn.jpush.android.api.JPushInterface;

/**
 * A placeholder fragment containing a simple view.
 */
public class BaseFragmentActivity extends FragmentActivity implements View.OnClickListener , Response.ErrorListener {
    ProgressDialogFragment _progressDialog=null;
    PullToRefreshBase _pullToRefreshBase =null;

    protected static final String NULL_NETWORK = "无网络或当前网络不可用!";

    public BaseFragmentActivity() {
    }

    @Override
    public void onClick(View v) {
        if( v.getId()== R.id.header_back){
            this.finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        JPushInterface.onResume(BaseFragmentActivity.this);
        MobclickAgent.onResume(BaseFragmentActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        JPushInterface.onPause(BaseFragmentActivity.this);
        MobclickAgent.onPause(BaseFragmentActivity.this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if( keyCode == KeyEvent.KEYCODE_BACK &&
            event.getAction() == KeyEvent.ACTION_DOWN){
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected boolean canConnect(){
        //网络访问前先检测网络是否可用
        if(!Util.isConnect(BaseFragmentActivity.this)){
            ToastUtils.showLongToast(this, NULL_NETWORK);
            return false;
        }
        return true;
    }

    protected boolean showProgressDialog( String title , String message ){
        if( BaseFragmentActivity.this.isFinishing() ) return true;
        //网络访问前先检测网络是否可用
        if(canConnect()==false){
            return false;
        }

        if( _progressDialog !=null ) {
            _progressDialog.dismiss();
            _progressDialog=null;
        }
        ProgressDialogFragment.ProgressDialogBuilder builder = ProgressDialogFragment.createBuilder(this, getSupportFragmentManager())
                .setTitle(title)
                .setMessage( message )
                //.setCancelable(false)
                .setCancelableOnTouchOutside(false);
        _progressDialog = (ProgressDialogFragment) builder.show();

        return true;
    }

    protected  void closeProgressDialog(){
        if( BaseFragmentActivity.this.isFinishing() )return;
        if(_progressDialog!=null){
            _progressDialog.dismiss();
            _progressDialog=null;
        }
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        if( BaseFragmentActivity.this.isFinishing() ) return;

        BaseFragmentActivity.this.closeProgressDialog();
        if( _pullToRefreshBase!=null ){
            _pullToRefreshBase.onRefreshComplete();
        }

        String message="";
        if( volleyError instanceof TimeoutError ){
            message = "网络连接超时";
        }else if( volleyError instanceof NetworkError || volleyError instanceof NoConnectionError ) {
            message ="网络请求异常，请检查网络状态";
        }else if( volleyError instanceof ParseError ){
            message = "数据解析失败，请检测数据的正确性";
        }else if( volleyError instanceof ServerError || volleyError instanceof AuthFailureError){
            if( null != volleyError.networkResponse){
                message=new String( volleyError.networkResponse.data);
            }else{
                message = volleyError.getMessage();
            }
        }

        if( message.length()<1){
            message = "网络请求失败，请检查网络状态";
        }
        DialogUtils.showDialog(BaseFragmentActivity.this, BaseFragmentActivity.this.getSupportFragmentManager(), "错误信息", message, "关闭");
    }

    //Response.ErrorListener errorListener =new Response.ErrorListener() {
    static class MJErrorListener implements Response.ErrorListener{
        WeakReference<BaseFragmentActivity> ref;
        public MJErrorListener(BaseFragmentActivity act){
            ref = new WeakReference<BaseFragmentActivity>(act);
        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            if( ref.get() ==null )return;

            if( ref.get().isFinishing() ) return;

            ref.get().closeProgressDialog();
            if( ref.get()._pullToRefreshBase!=null ){
                ref.get()._pullToRefreshBase.onRefreshComplete();
            }

            String message="";
            if( volleyError instanceof TimeoutError ){
                message = "网络连接超时";
            }else if( volleyError instanceof NetworkError || volleyError instanceof NoConnectionError ) {
                message ="网络请求异常，请检查网络状态";
            }else if( volleyError instanceof ParseError ){
                message = "数据解析失败，请检测数据的正确性";
            }else if( volleyError instanceof ServerError || volleyError instanceof AuthFailureError){
                if( null != volleyError.networkResponse){
                    message=new String( volleyError.networkResponse.data);
                }else{
                    message = volleyError.getMessage();
                }
            }

            if( message.length()<1){
                message = "网络请求失败，请检查网络状态";
            }

            ToastUtils.showLong( message , Toast.LENGTH_LONG);
            //DialogUtils.showDialog(BaseFragmentActivity.this, BaseFragmentActivity.this.getSupportFragmentManager(), "错误信息", message, "关闭");

        }
    }

 
    /**
    * 方法描述：
    * 方法名称：
    * 参数：
    * 返回值：
    * 创建时间: 2015/10/15
    * 作者: Administrator
    */
    protected boolean validateData(BaseModel data){
        if( BaseFragmentActivity.this.isFinishing() ) return false;

        if(null == data){
            DialogUtils.showDialog(BaseFragmentActivity.this, BaseFragmentActivity.this.getSupportFragmentManager() ,"错误信息","请求失败","关闭");
            return false;
        }else if(data.getSystemResultCode()!=1){
            DialogUtils.showDialog(BaseFragmentActivity.this, BaseFragmentActivity.this.getSupportFragmentManager() ,"错误信息",data.getSystemResultDescription(),"关闭");
            return false;
        }else if( data.getResultCode()== Constant.TOKEN_OVERDUE){
            ActivityUtils.getInstance().skipActivity(BaseFragmentActivity.this,LoginActivity.class);
            return false;
        }else if( data.getResultCode() != 1){
            DialogUtils.showDialog(BaseFragmentActivity.this, BaseFragmentActivity.this.getSupportFragmentManager() ,"错误信息",data.getResultDescription(),"关闭");
            return false;
        }
        return true;
    }

    /*
      判断是否有权限
    */
    /**
    * 方法描述：
    * 方法名称：
    * 参数：
    * 返回值：
    * 创建时间: 2015/10/15
    * 作者: 
    */
    protected boolean hasRole( RoleEnum role ){
        String roles = PreferenceHelper.readString( this , Constant.LOGIN_USER_INFO , Constant.LOGIN_AUTH_AUTHORITY , "");
        if( roles.contains("*") ) return  true;

        String roleStr = String.valueOf( role.getIndex() );
        String[] items = roles.split(",");
        if(items==null || items.length<1) return false;

        for( int i=0;i< items.length;i++){
            String temp = items[i].trim();
            if(  temp.equals( roleStr ) ){
                return true;
            }
        }

        return false;
    }

}
