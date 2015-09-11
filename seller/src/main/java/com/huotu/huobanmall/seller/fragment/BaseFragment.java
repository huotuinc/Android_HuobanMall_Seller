package com.huotu.huobanmall.seller.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avast.android.dialogs.fragment.ProgressDialogFragment;
import com.huotu.huobanmall.seller.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {

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

}
