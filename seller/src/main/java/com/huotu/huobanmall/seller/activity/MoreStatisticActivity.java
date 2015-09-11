package com.huotu.huobanmall.seller.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.bean.BaseModel;
import com.huotu.huobanmall.seller.bean.MJOtherStatisticModel;
import com.huotu.huobanmall.seller.bean.OtherStatisticModel;
import com.huotu.huobanmall.seller.common.Constant;
import com.huotu.huobanmall.seller.utils.ActivityUtils;
import com.huotu.huobanmall.seller.utils.GsonRequest;
import com.huotu.huobanmall.seller.utils.HttpParaUtils;
import com.huotu.huobanmall.seller.utils.VolleyRequestManager;

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

        getData();
    }

    protected void getData(){
        String url = Constant.OTHERSTATISTIC_INTERFACE;
        HttpParaUtils httpParaUtils =new HttpParaUtils();
        url = httpParaUtils.getHttpGetUrl(url ,null);

        GsonRequest<MJOtherStatisticModel> otherRequest = new GsonRequest<MJOtherStatisticModel>(
                Request.Method.GET,
                url,
                MJOtherStatisticModel.class,
                null,
                otherListener,
                errorListener
        );

        this.showProgressDialog("","正在获取数据，请稍等...");

        VolleyRequestManager.getRequestQueue().add(otherRequest);
    }

    Response.Listener<MJOtherStatisticModel> otherListener=new Response.Listener<MJOtherStatisticModel>() {
        @Override
        public void onResponse(MJOtherStatisticModel  mjOtherStatisticModel ) {
            MoreStatisticActivity.this.closeProgressDialog();
            
        }
    };


    Response.ErrorListener errorListener =new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            MoreStatisticActivity.this.closeProgressDialog();

        }
    };

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
