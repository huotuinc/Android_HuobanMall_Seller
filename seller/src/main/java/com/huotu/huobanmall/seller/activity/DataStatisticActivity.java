package com.huotu.huobanmall.seller.activity;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.adapter.DataStatisticFragmentAdapter;
import com.huotu.huobanmall.seller.fragment.BaseFragment;
import com.huotu.huobanmall.seller.fragment.MembersFragment;
import com.huotu.huobanmall.seller.fragment.OrderFragment;
import com.huotu.huobanmall.seller.fragment.SalesFragment;
import com.huotu.huobanmall.seller.activity.BaseFragmentActivity;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DataStatisticActivity extends BaseFragmentActivity {
    OrderFragment _orderFragment;
    SalesFragment _salesFragments;
    MembersFragment _membersFragments;
    List<BaseFragment> _fragments;
    FragmentManager _fragmentManager;
    @Bind(R.id.datastatistic_pager)
    ViewPager _viewPager;
    @Bind(R.id.datastatistic_indicator)
    CirclePageIndicator _circlePageIndicator;

    DataStatisticFragmentAdapter _dataStatisticFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_statistic);
        ButterKnife.bind(this);
        initFragments();
    }

    protected void initFragments(){
        _orderFragment = OrderFragment.newInstance();
        _salesFragments = SalesFragment.newInstance();
        _membersFragments = MembersFragment.newInstance();
        _fragments = new ArrayList<>();
        _fragments.add(_orderFragment);
        _fragments.add(_salesFragments);
        _fragments.add(_membersFragments);
        _fragmentManager = this.getSupportFragmentManager();
        _dataStatisticFragmentAdapter = new DataStatisticFragmentAdapter(_fragments,_fragmentManager);

        _viewPager.setAdapter(_dataStatisticFragmentAdapter);
        _circlePageIndicator.setViewPager(_viewPager);
    }

    protected void setCurrentFragment(){

    }
}
