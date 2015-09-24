package com.huotu.huobanmall.seller.activity;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.adapter.DataStatisticFragmentAdapter;

import com.huotu.huobanmall.seller.common.Constant;
import com.huotu.huobanmall.seller.fragment.BaseFragment;
import com.huotu.huobanmall.seller.fragment.MembersFragment;
import com.huotu.huobanmall.seller.fragment.OrderFragment;
import com.huotu.huobanmall.seller.fragment.SalesFragment;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 订单，分销商，会员 的主页面
 */
public class DataStatisticActivity extends BaseFragmentActivity implements RadioGroup.OnCheckedChangeListener {
    OrderFragment _orderFragment=null;
    SalesFragment _salesFragments=null;
    MembersFragment _membersFragments=null;
    List<BaseFragment> _fragments;
    FragmentManager _fragmentManager;
    //当前Tab页类型
    Integer _currentTabType = Constant.TAB_ORDER;
    @Bind(R.id.datastatistic_pager)
    ViewPager _viewPager;
    @Bind(R.id.datastatistic_indicator)
    CirclePageIndicator _circlePageIndicator;
    @Bind(R.id.tab_rb_a)
    RadioButton rdb_a;
    @Bind(R.id.tab_rb_b)
    RadioButton rdb_b;
    @Bind(R.id.tab_rb_c)
    RadioButton rdb_c;
    @Bind(R.id.statistic_title)
    RadioGroup statistic_title;
    @Bind(R.id.header_back)
    Button btnBack;

    DataStatisticFragmentAdapter _dataStatisticFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_statistic);
        ButterKnife.bind(this);
        initView();
        initFragments();
    }

    protected void initView(){
        if( null != getIntent() && null != getIntent().getExtras() ){
            if( getIntent().getExtras().containsKey("tabType")){
                _currentTabType = getIntent().getExtras().getInt("tabType",Constant.TAB_ORDER);
            }
        }

        btnBack.setOnClickListener(this);
        statistic_title.setOnCheckedChangeListener(this);
    }

    protected void initFragments(){
        if(_orderFragment==null) {
            _orderFragment = OrderFragment.newInstance();
        }
        if( _salesFragments==null) {
            _salesFragments = SalesFragment.newInstance();
        }
        if( _membersFragments==null) {
            _membersFragments = MembersFragment.newInstance();
        }
        if( _fragments ==null ) {
            _fragments = new ArrayList<>();
        }
        _fragments.add(_orderFragment);
        _fragments.add(_salesFragments);
        _fragments.add(_membersFragments);
        _fragmentManager = this.getSupportFragmentManager();
        _dataStatisticFragmentAdapter = new DataStatisticFragmentAdapter(_fragments,_fragmentManager);

        _viewPager.setAdapter(_dataStatisticFragmentAdapter);
        _circlePageIndicator.setViewPager(_viewPager);


        _circlePageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if( position ==0 ){
                    rdb_a.setChecked(true);
                    rdb_b.setChecked(false);
                    rdb_c.setChecked(false);
                }else if( position==1){
                    rdb_a.setChecked(false);
                    rdb_b.setChecked(true);
                    rdb_c.setChecked(false);
                }else if( position==2){
                    rdb_a.setChecked(false);
                    rdb_b.setChecked(false);
                    rdb_c.setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //设置当前Tab
        _circlePageIndicator.setCurrentItem( _currentTabType -1 );
    }

    protected void setCurrentFragment(){

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if( checkedId == R.id.tab_rb_a){
            _circlePageIndicator.setCurrentItem( 0 );
        }else if( checkedId == R.id.tab_rb_b){
            _circlePageIndicator.setCurrentItem(1);
        }else if( checkedId == R.id.tab_rb_c){
            _circlePageIndicator.setCurrentItem(2);
        }
    }
}
