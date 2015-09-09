package com.huotu.huobanmall.seller.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.adapter.DataStatisticFragmentAdapter;
import com.huotu.huobanmall.seller.adapter.GoodsFragmentAdapter;
import com.huotu.huobanmall.seller.fragment.BaseFragment;
import com.huotu.huobanmall.seller.fragment.OffShelfFragment;
import com.huotu.huobanmall.seller.fragment.SaleGoodsFragment;
import com.huotu.huobanmall.seller.utils.ActivityUtils;
import com.viewpagerindicator.TabPageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 商品管理
 */
public class GoodsActivity extends BaseFragmentActivity {
    @Bind(R.id.header_title)
    TextView _header_title;
    @Bind(R.id.header_back)
    Button  _header_back;
    @Bind(R.id.goods_pager)
    ViewPager _goodsViewPager;
    @Bind(R.id.goods_indicator)
    TabPageIndicator _goodIndicator;
    @Bind(R.id.header_operate)
    Button _btnOperate;

    SaleGoodsFragment _salesGoodsFragment;
    OffShelfFragment _offShelfFragments;
    List<BaseFragment> _fragments;
    GoodsFragmentAdapter _goodsFragmentAdapter;
    FragmentManager _fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        ButterKnife.bind(this);
        _header_back.setOnClickListener(this);
        _btnOperate.setOnClickListener(this);
        _btnOperate.setVisibility(View.VISIBLE);
        _btnOperate.setText(getString(R.string.header_edit));
        _header_title.setText("产品列表");
        _salesGoodsFragment=SaleGoodsFragment.newInstance();
        _offShelfFragments=OffShelfFragment.newInstance();
        _fragments = new ArrayList<>();
        _fragments.add(_salesGoodsFragment);
        _fragments.add(_offShelfFragments);
        _fragmentManager = this.getSupportFragmentManager();

        _goodsFragmentAdapter = new GoodsFragmentAdapter(_fragments,_fragmentManager);

        _goodsViewPager.setAdapter(_goodsFragmentAdapter);
        _goodIndicator.setViewPager(_goodsViewPager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if( v.getId() == R.id.header_operate){
            ActivityUtils.getInstance().showActivity(this, GoodsEditActivity.class);
        }
        if( v.getId() == R.id.header_back){
            finish();
        }
    }
}
