package com.huotu.huobanmall.seller.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.huotu.huobanmall.seller.utils.ActivityUtils;
import com.huotu.huobanmall.seller.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseFragmentActivity {

    @Bind(R.id.btn_datastatistic)
    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        btnStart.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        if( v.getId() == R.id.btn_datastatistic){
            ActivityUtils.getInstance().showActivity(this,DataStatisticActivity.class);
        }
        super.onClick(v);
    }
}
