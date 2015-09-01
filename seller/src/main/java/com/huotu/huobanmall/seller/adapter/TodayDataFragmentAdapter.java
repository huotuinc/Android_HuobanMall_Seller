package com.huotu.huobanmall.seller.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.huotu.huobanmall.seller.fragment.BaseFragment;

import java.util.List;

/**
 * Created by Administrator on 2015/9/1.
 */
public class TodayDataFragmentAdapter extends FragmentPagerAdapter {
    List<BaseFragment> _fragments;
    public TodayDataFragmentAdapter( List<BaseFragment> fragments ,FragmentManager fragmentManager){
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
}
