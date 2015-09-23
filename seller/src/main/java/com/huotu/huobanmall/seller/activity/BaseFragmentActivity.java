package com.huotu.huobanmall.seller.activity;

import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.avast.android.dialogs.fragment.ProgressDialogFragment;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.utils.DialogUtils;
import cn.jpush.android.api.JPushInterface;

/**
 * A placeholder fragment containing a simple view.
 */
public class BaseFragmentActivity extends FragmentActivity implements View.OnClickListener , Response.ErrorListener {
    ProgressDialogFragment _progressDialog=null;

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
    }

    @Override
    protected void onPause() {
        super.onPause();

        JPushInterface.onPause(BaseFragmentActivity.this);
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

    protected void showProgressDialog( String title , String message ){
        if( _progressDialog !=null ) {
            _progressDialog.dismiss();
            _progressDialog=null;
        }
        ProgressDialogFragment.ProgressDialogBuilder builder = ProgressDialogFragment.createBuilder(this, getSupportFragmentManager())
                .setTitle(title)
                .setMessage( message )
                .setCancelable(false)
                .setCancelableOnTouchOutside(false);
        _progressDialog = (ProgressDialogFragment) builder.show();
    }

    protected  void closeProgressDialog(){
        if(_progressDialog!=null){
            _progressDialog.dismiss();
            _progressDialog=null;
        }
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        BaseFragmentActivity.this.closeProgressDialog();
        String message="";
        if( null != volleyError.networkResponse){
            message=new String( volleyError.networkResponse.data);
        }else{
            message = volleyError.getMessage();
        }
        if( message.length()<1){
            message = "网络请求失败，请检查网络状态";
        }
        DialogUtils.showDialog(BaseFragmentActivity.this, BaseFragmentActivity.this.getSupportFragmentManager(), "错误信息", message, "关闭");
    }
}
