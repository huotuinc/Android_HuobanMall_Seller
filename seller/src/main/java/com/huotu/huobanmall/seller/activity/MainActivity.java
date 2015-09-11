package com.huotu.huobanmall.seller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.avast.android.dialogs.fragment.ProgressDialogFragment;
import com.huotu.huobanmall.seller.Interface.IIndexFragmentInteractionListener;
import com.huotu.huobanmall.seller.adapter.TodayDataFragmentAdapter;
import com.huotu.huobanmall.seller.bean.BaseModel;
import com.huotu.huobanmall.seller.bean.MJNewTodayModel;
import com.huotu.huobanmall.seller.common.Constant;
import com.huotu.huobanmall.seller.fragment.BaseFragment;
import com.huotu.huobanmall.seller.fragment.TodayDistributorsFragment;
import com.huotu.huobanmall.seller.fragment.TodayMemberFragment;
import com.huotu.huobanmall.seller.fragment.TodayOrderFragment;
import com.huotu.huobanmall.seller.utils.ActivityUtils;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.utils.DialogUtils;
import com.huotu.huobanmall.seller.utils.GsonRequest;
import com.huotu.huobanmall.seller.utils.HttpParaUtils;
import com.huotu.huobanmall.seller.utils.StringUtils;
import com.huotu.huobanmall.seller.utils.VolleyRequestManager;
import com.viewpagerindicator.CirclePageIndicator;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Manifest;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseFragmentActivity implements IIndexFragmentInteractionListener{

    @Bind(R.id.main_todyMoney)
    TextView main_TodayMoney;
    @Bind(R.id.main_totalMoney)
    TextView main_TotalMoney;
    @Bind(R.id.main_menu_cpgl)
    Button main_menu_cpgl;
    @Bind(R.id.main_menu_ddgl)
    Button main_menu_ddgl;
    @Bind(R.id.main_menu_gdtj)
    Button main_menu_gdtj;
    @Bind(R.id.main_menu_szgl)
    Button main_menu_szgl;

    TodayDistributorsFragment _todayDistributorsFragments;
    TodayMemberFragment _todayMemberFragments;
    TodayOrderFragment _todayOrderFragments;
    List<BaseFragment> _fragments;
    @Bind(R.id.main_pager)
    ViewPager _viewPager;
    @Bind(R.id.main_indicator)
    CirclePageIndicator _indicator;
    TodayDataFragmentAdapter _fragmentAdapter;

    MJNewTodayModel _data=null;

    //ProgressDialogFragment _progressDlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //String formatText = "￥%.2f";
        //CountUpTimerView countUpView =new CountUpTimerView( main_todyMoney , formatText , 1000.33f,5000.45f, 3000,100);
        //countUpView.start();

        initTodayData();

        main_menu_cpgl.setOnClickListener(this);
        main_menu_ddgl.setOnClickListener(this);
        main_menu_gdtj.setOnClickListener(this);
        main_menu_szgl.setOnClickListener(this);

        getData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    protected void getData(){
        String url = Constant.NEWTODAY_INTERFACE;
        HttpParaUtils httpParaUtils =new HttpParaUtils();
        url =  httpParaUtils.getHttpGetUrl(url,null);
        GsonRequest<MJNewTodayModel> newTodayRequest=new GsonRequest<MJNewTodayModel>(
                Request.Method.GET,
                url,
                MJNewTodayModel.class,
                null,
                newTodayModelListener,
                errorListener
        );

        this.showProgressDialog("","正在获取数据，请稍等...");

        VolleyRequestManager.getRequestQueue().add(newTodayRequest);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if( v.getId() == R.id.main_menu_cpgl){
            ActivityUtils.getInstance().showActivity(this, GoodsActivity.class);
        }else if( v.getId() == R.id.main_menu_ddgl){
            Intent intent = new Intent( this , WebViewActivity.class);
            intent.putExtra(Constant.Extra_Url,"http://www.baidu.com");
            ActivityUtils.getInstance().showActivity(this, WebViewActivity.class);
        }else if( v.getId() == R.id.main_menu_gdtj){
            ActivityUtils.getInstance().showActivity(this, MoreStatisticActivity.class);
        }else if( v.getId() ==R.id.main_menu_szgl){
            ActivityUtils.getInstance().showActivity(this, SettingActivity.class);
        }
    }

    protected void initTodayData(){
        _todayDistributorsFragments = new TodayDistributorsFragment();
        _todayMemberFragments = new TodayMemberFragment();
        _todayOrderFragments = new TodayOrderFragment();
        _fragments=new ArrayList<>();
        _fragments.add(_todayOrderFragments);
        _fragments.add(_todayMemberFragments);
        _fragments.add(_todayDistributorsFragments);
        _fragmentAdapter=new TodayDataFragmentAdapter(_fragments, getSupportFragmentManager());
        _viewPager.setAdapter(_fragmentAdapter);
        _indicator.setViewPager(_viewPager);

    }

    @Override
    public void switchFragment(int position) {
        _indicator.setCurrentItem(position);
    }

    Response.Listener<MJNewTodayModel> newTodayModelListener = new Response.Listener<MJNewTodayModel>() {
        @Override
        public void onResponse(MJNewTodayModel mjNewTodayModel) {
            MainActivity.this.closeProgressDialog();

            if(null == mjNewTodayModel){
                DialogUtils.showDialog(MainActivity.this, MainActivity.this.getSupportFragmentManager()
                ,"错误信息","请求失败","关闭");
                return;
            }
            if(mjNewTodayModel.getSystemResultCode()!=1){
                DialogUtils.showDialog(MainActivity.this, MainActivity.this.getSupportFragmentManager()
                        ,"错误信息",mjNewTodayModel.getSystemResultDescription(),"关闭");
                return;
            }else if( mjNewTodayModel.getResultCode()== Constant.TOKEN_OVERDUE){
                ActivityUtils.getInstance().skipActivity(MainActivity.this,LoginActivity.class);
                return;
            }else if( mjNewTodayModel.getResultCode() != 1){
                DialogUtils.showDialog(MainActivity.this, MainActivity.this.getSupportFragmentManager()
                        ,"错误信息",mjNewTodayModel.getResultDescription(),"关闭");
                return;
            }


            main_TodayMoney.setText(String.valueOf(mjNewTodayModel.getResultData().getTodaySales()));
            main_TotalMoney.setText(String.valueOf(mjNewTodayModel.getResultData().getTotalSales()));

            _data=mjNewTodayModel;

//            Bundle bd = new Bundle();
//            bd.putFloat("todayOrderAmount", mjNewTodayModel.getResultData().getTodayOrderAmount());
//            bd.putFloat("todayMemberAmount",mjNewTodayModel.getResultData().getTodayMemberAmount());
//            bd.putFloat("todayPartnerAmount",mjNewTodayModel.getResultData().getTodayPartnerAmount());
//            bd.putIntegerArrayList("orderHour", (ArrayList)mjNewTodayModel.getResultData().getOrderHour());
//            bd.putIntegerArrayList("OrderAmount",(ArrayList)mjNewTodayModel.getResultData().getOrderAmount());
            _todayOrderFragments.setData(mjNewTodayModel);
        }
    };

    Response.ErrorListener errorListener=new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            MainActivity.this.closeProgressDialog();
            if( volleyError.networkResponse!=null) {
                String message = new String(volleyError.networkResponse.data);
                DialogUtils.showDialog(MainActivity.this,MainActivity.this.getSupportFragmentManager(),"错误信息",message,"关闭");
            }
        }
    };
}
