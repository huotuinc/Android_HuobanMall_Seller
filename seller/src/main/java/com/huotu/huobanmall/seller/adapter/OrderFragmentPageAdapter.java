package com.huotu.huobanmall.seller.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.huotu.huobanmall.seller.fragment.BaseFragment;

import java.util.List;

/**
 * Created by Administrator on 2015/9/2.
 */
public class OrderFragmentPageAdapter extends FragmentPagerAdapter {
    public static final String[] titles=new String[]{"今天","7日","30天"};

    List<BaseFragment> _fragments;
    public OrderFragmentPageAdapter( List<BaseFragment> fragments ,FragmentManager fragmentManager){
        super( fragmentManager );
        _fragments=fragments;
    }
    @Override
    public int getCount() {
        return _fragments==null? 0 : _fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        if (_fragments ==null ) return null;
        return _fragments.get(position%_fragments.size());
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return  titles[position% titles.length];
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return super.isViewFromObject(view, object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public int getItemPosition(Object object) {
        //return super.getItemPosition(object);
        return PagerAdapter.POSITION_NONE;
    }
}
