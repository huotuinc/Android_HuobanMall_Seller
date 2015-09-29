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

import com.huotu.huobanmall.seller.bean.RoleEnum;
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
    //@Bind(R.id.datastatistic_indicator)
    //CirclePageIndicator _circlePageIndicator;
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

        showTabsByRoles();

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

    /**
     * 通过权限点显示Tab
     */
    protected void showTabsByRoles(){
        boolean order_Role = hasRole(RoleEnum.订单统计);
        boolean xse_Role = hasRole(RoleEnum.销售额统计);
        boolean member_Role = hasRole(RoleEnum.会员统计);

        rdb_a.setVisibility( order_Role ? View.VISIBLE : View.GONE );
        rdb_b.setVisibility( xse_Role ? View.VISIBLE : View.GONE );
        rdb_c.setVisibility( member_Role ? View.VISIBLE : View.GONE);

        if( order_Role && xse_Role && member_Role ){
            rdb_a.setBackgroundResource( R.drawable.radiobutton_left );
            rdb_b.setBackgroundResource( R.drawable.radiobutton_middle );
            rdb_c.setBackgroundResource( R.drawable.radiobutton_right);
        }else if( order_Role && xse_Role ){
            rdb_a.setBackgroundResource( R.drawable.radiobutton_left );
            rdb_b.setBackgroundResource( R.drawable.radiobutton_right );
        }else if( order_Role && member_Role){
            rdb_a.setBackgroundResource( R.drawable.radiobutton_left );
            rdb_c.setBackgroundResource( R.drawable.radiobutton_right );
        }else if( xse_Role && member_Role ) {
            rdb_b.setBackgroundResource( R.drawable.radiobutton_left );
            rdb_c.setBackgroundResource( R.drawable.radiobutton_right);
        }else if( order_Role ){
            rdb_a.setBackgroundResource( R.drawable.radiobutton_round );
        }else if( xse_Role ){
            rdb_b.setBackgroundResource(R.drawable.radiobutton_round);
        }else if( member_Role ){
            rdb_c.setBackgroundResource(R.drawable.radiobutton_round);
        }
    }

    protected void initFragments(){
        boolean order_Role = hasRole(RoleEnum.订单统计);
        boolean xse_Role = hasRole(RoleEnum.销售额统计);
        boolean member_Role = hasRole(RoleEnum.会员统计);

        if( _fragments ==null ) {
            _fragments = new ArrayList<>();
        }

        int index = 0;
        if( order_Role && _orderFragment==null) {
            _orderFragment = OrderFragment.newInstance();
            _fragments.add( _orderFragment );
            rdb_a.setTag(index);
            index++;
        }
        if( xse_Role && _salesFragments==null) {
            _salesFragments = SalesFragment.newInstance();
            _fragments.add( _salesFragments );
            rdb_b.setTag(index);
            index++;
        }
        if( member_Role &&  _membersFragments==null) {
            _membersFragments = MembersFragment.newInstance();
            _fragments.add(_membersFragments);
            rdb_c.setTag( index );
            index++;
        }

        _fragmentManager = this.getSupportFragmentManager();
        _dataStatisticFragmentAdapter = new DataStatisticFragmentAdapter(_fragments,_fragmentManager);

        _viewPager.setAdapter(_dataStatisticFragmentAdapter);

        _viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                rdb_a.setChecked(false);
                rdb_b.setChecked(false);
                rdb_c.setChecked(false);
                int index = -1;
                if (rdb_a.getVisibility() == View.VISIBLE) {
                    index = (int) rdb_a.getTag();
                    if (index == position) {
                        rdb_a.setChecked(true);
                    }
                }
                if (rdb_b.getVisibility() == View.VISIBLE) {
                    index = (int) rdb_b.getTag();
                    if (index == position) {
                        rdb_b.setChecked(true);
                    }
                }
                if (rdb_c.getVisibility() == View.VISIBLE) {
                    index = (int) rdb_c.getTag();
                    if (index == position) {
                        rdb_c.setChecked(true);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        _viewPager.setCurrentItem( _currentTabType-1 );

        //_circlePageIndicator.setViewPager(_viewPager);

//        _circlePageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                rdb_a.setChecked(false);
//                rdb_b.setChecked(false);
//                rdb_c.setChecked(false);
//                    int index = -1;
//                    if( rdb_a.getVisibility() == View.VISIBLE ) {
//                        index = (int) rdb_a.getTag();
//                        if (index == position) {
//                            rdb_a.setChecked(true);
//                        }
//                    }
//                if( rdb_b.getVisibility() == View.VISIBLE) {
//                    index = (int) rdb_b.getTag();
//                    if (index == position) {
//                        rdb_b.setChecked(true);
//                    }
//                }
//                if( rdb_c.getVisibility() == View.VISIBLE ) {
//                    index = (int) rdb_c.getTag();
//                    if (index == position) {
//                        rdb_c.setChecked(true);
//                    }
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//        //设置当前Tab
//        _circlePageIndicator.setCurrentItem( _currentTabType -1 );
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if( checkedId == R.id.tab_rb_a){
            if( rdb_a.getVisibility() == View.GONE ) return;
            int index = (int) rdb_a.getTag();
            //_circlePageIndicator.setCurrentItem( index );
            _viewPager.setCurrentItem(index);
        }else if( checkedId == R.id.tab_rb_b){
            if( rdb_b.getVisibility() == View.GONE ) return;
            int index = (int) rdb_b.getTag();
            //_circlePageIndicator.setCurrentItem(  index );
            _viewPager.setCurrentItem(index);
        }else if( checkedId == R.id.tab_rb_c){
            if( rdb_c.getVisibility() == View.GONE ) return;
            int index = (int)rdb_c.getTag();
            //_circlePageIndicator.setCurrentItem( index );
            _viewPager.setCurrentItem(index);
        }
    }
}
