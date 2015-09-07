package com.huotu.huobanmall.seller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.huotu.huobanmall.seller.adapter.TodayDataFragmentAdapter;
import com.huotu.huobanmall.seller.common.Constant;
import com.huotu.huobanmall.seller.fragment.BaseFragment;
import com.huotu.huobanmall.seller.fragment.TodayDistributorsFragment;
import com.huotu.huobanmall.seller.fragment.TodayMemberFragment;
import com.huotu.huobanmall.seller.fragment.TodayOrderFragment;
import com.huotu.huobanmall.seller.utils.ActivityUtils;
import com.huotu.huobanmall.seller.R;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseFragmentActivity {

    //@Bind(R.id.main_gridView)
    //GridView main_gridView;
    @Bind(R.id.main_todyMoney)
    TextView main_TodayMoney;
    @Bind(R.id.main_menu_cpgl)
    Button main_menu_cpgl;
    @Bind(R.id.main_menu_ddgl)
    Button main_menu_ddgl;
    @Bind(R.id.main_menu_gdtj)
    Button main_menu_gdtj;
    @Bind(R.id.main_menu_szgl)
    Button main_menu_szgl;

    //MenuAdapter mAdapter;
    //List<MenuModel> mMenus;

    TodayDistributorsFragment _todayDistributorsFragments;
    TodayMemberFragment _todayMemberFragments;
    TodayOrderFragment _todayOrderFragments;
    List<BaseFragment> _fragments;
    @Bind(R.id.main_pager)
    ViewPager _viewPager;
    @Bind(R.id.main_indicator)
    CirclePageIndicator _indicator;
    TodayDataFragmentAdapter _fragmentAdapter;

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

//        mMenus=new ArrayList<>();
//        final String[] menus = getResources().getStringArray(R.array.main_menu_name);
//        TypedArray iconsTA = getResources().obtainTypedArray(R.array.main_menu_icon);
//
//        for( int i=0;i<menus.length;i++) {
//            MenuModel item = new MenuModel();
//            item.setName(menus[i]);
//            item.setIcon( iconsTA.getResourceId( i,0 ));
//            mMenus.add(item);
//        }
//        mAdapter = new MenuAdapter(mMenus,this);
//        main_gridView.setAdapter(mAdapter);
//        main_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                MenuModel item = mMenus.get(position);
//                if( item.getIcon()==R.mipmap.cpgl ) {
//                    ActivityUtils.getInstance().showActivity(MainActivity.this, GoodsEditActivity.class);
//                }else if( item.getIcon() == R.mipmap.ddgl){
//                    //ActivityUtils.getInstance().showActivity(this , GoodsEditActivity.class);
//                }else if( item.getIcon()==R.mipmap.zytj){
//                    ActivityUtils.getInstance().showActivity(MainActivity.this,DataStatisticActivity.class);
//                }else if( item.getIcon()==R.mipmap.setting){
//
//                }
//            }
//        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
        }

        super.onClick(v);
        if( v.getId() == R.id.main_menu_cpgl){
            ActivityUtils.getInstance().showActivity(this, GoodsActivity.class);
        }else if( v.getId() == R.id.main_menu_ddgl){
            Intent intent = new Intent( this , WebViewActivity.class);
            intent.putExtra(Constant.Extra_Url,"http://www.baidu.com");
            ActivityUtils.getInstance().showActivity(this, WebViewActivity.class);
        }else if( v.getId() == R.id.main_menu_gdtj){
            ActivityUtils.getInstance().showActivity(this, DataStatisticActivity.class);
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
}
