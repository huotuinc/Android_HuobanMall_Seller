package com.huotu.huobanmall.seller.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
 * 商品管理 界面
 */
public class GoodsActivity extends BaseFragmentActivity {
    @Bind(R.id.header_back)
    Button  _header_back;
    @Bind(R.id.goods_pager)
    ViewPager _goodsViewPager;
    @Bind(R.id.header_operate)
    TextView _btnOperate;
    @Bind(R.id.goods_title)
    RadioGroup _rdGroup;
    @Bind(R.id.saleing_btn)
    RadioButton _rdbSales;
    @Bind(R.id.offshelf_btn)
    RadioButton _rdbOffShelf;

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

        _goodsViewPager.setOffscreenPageLimit(2);

        _rdGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.saleing_btn) {
                    _goodsViewPager.setCurrentItem(0);
                } else if (checkedId == R.id.offshelf_btn) {
                    _goodsViewPager.setCurrentItem(1);
                }
            }
        });

        _salesGoodsFragment=SaleGoodsFragment.newInstance();
        _offShelfFragments=OffShelfFragment.newInstance();
        _fragments = new ArrayList<>();
        _fragments.add(_salesGoodsFragment);
        _fragments.add(_offShelfFragments);
        _fragmentManager = this.getSupportFragmentManager();
        _goodsFragmentAdapter = new GoodsFragmentAdapter(_fragments,_fragmentManager);
        _goodsViewPager.setAdapter(_goodsFragmentAdapter);
        _goodsViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    _rdbSales.setChecked(true);
                    _rdbOffShelf.setChecked(false);
                }else if( position==1){
                    _rdbSales.setChecked(false);
                    _rdbOffShelf.setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        if( v.getId() == R.id.header_operate){
            ActivityUtils.getInstance().skipActivity(this, GoodsEditActivity.class);
        }else if( v.getId() == R.id.header_back){
            finish();
        }
    }
}
