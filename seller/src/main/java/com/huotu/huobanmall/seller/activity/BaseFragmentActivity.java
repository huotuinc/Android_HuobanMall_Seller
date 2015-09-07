package com.huotu.huobanmall.seller.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.utils.ActivityUtils;

/**
 * A placeholder fragment containing a simple view.
 */
public class BaseFragmentActivity extends FragmentActivity implements View.OnClickListener {
    public BaseFragmentActivity() {
    }

    @Override
    public void onClick(View v) {
        if( v.getId()== R.id.header_back){
            this.finish();
        }
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
}
