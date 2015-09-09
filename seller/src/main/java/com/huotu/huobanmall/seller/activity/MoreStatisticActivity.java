package com.huotu.huobanmall.seller.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.utils.ActivityUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MoreStatisticActivity extends BaseFragmentActivity {
    @Bind(R.id.header_back)
    Button btnBack;
    @Bind(R.id.morestatistic_title2)
    RelativeLayout morestatistic_title2;
    @Bind(R.id.morestatistic_menu_fxs)
    Button moresta_fxs;
    @Bind(R.id.morestatistic_menu_sp)
    Button moresta_sp;
    @Bind(R.id.morestatistic_menu_sxetj)
    Button moresta_xsetj;
    @Bind(R.id.morestatistic_menu_hytj)
    Button moresta_hytj;
    @Bind(R.id.morestatistic_menu_xsmx)
    Button moresta_xsmx;
    @Bind(R.id.morestatistic_menu_fltj)
    Button moresta_fljftj;
    @Bind(R.id.morestatistic_menu_xftj)
    Button moresta_xftj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_statistic);
        ButterKnife.bind(this);

        btnBack.setOnClickListener(this);
        morestatistic_title2.setOnClickListener(this);
        moresta_fljftj.setOnClickListener(this);
        moresta_fxs.setOnClickListener(this);
        moresta_hytj.setOnClickListener(this);
        moresta_sp.setOnClickListener(this);
        moresta_xftj.setOnClickListener(this);
        moresta_xsetj.setOnClickListener(this);
        moresta_xsmx.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId()==R.id.morestatistic_title2){
            ActivityUtils.getInstance().showActivity(this, DataStatisticActivity.class);
        }else if( v.getId() == R.id.morestatistic_menu_xftj){

        }else if( v.getId() == R.id.morestatistic_menu_xsmx){

        }else if( v.getId() == R.id.morestatistic_menu_hytj){
            ActivityUtils.getInstance().showActivity(this, DataStatisticActivity.class);
        }else if( v.getId() == R.id.morestatistic_menu_fltj){

        }else if( v.getId() == R.id.morestatistic_menu_sxetj){

        }else if( v.getId() == R.id.morestatistic_menu_fxs){

        }else if( v.getId() == R.id.morestatistic_menu_sp){

        }
    }
}
