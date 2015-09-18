package com.huotu.huobanmall.seller.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.avast.android.dialogs.fragment.ProgressDialogFragment;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.activity.BaseFragmentActivity;
import com.huotu.huobanmall.seller.utils.DialogUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment implements Response.ErrorListener{

    ProgressDialogFragment _progressDialog=null;


    public BaseFragment() {
        // Required empty public constructor
    }

    protected void showProgressDialog( String title , String message ){
        if( _progressDialog !=null ) {
            _progressDialog.dismiss();
            _progressDialog=null;
        }

        ProgressDialogFragment.ProgressDialogBuilder builder = ProgressDialogFragment.createBuilder(this.getActivity() , this.getFragmentManager())
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
        BaseFragment.this.closeProgressDialog();
        String message="";
        if( null != volleyError.networkResponse){
            message=new String( volleyError.networkResponse.data);
        }else{
            message = volleyError.getMessage();
        }
        if( message.length()<1){
            message = "网络请求失败，请检查网络状态";
        }
        DialogUtils.showDialog(BaseFragment.this.getActivity(), BaseFragment.this.getFragmentManager() , "错误信息", message, "关闭");
    }
}
