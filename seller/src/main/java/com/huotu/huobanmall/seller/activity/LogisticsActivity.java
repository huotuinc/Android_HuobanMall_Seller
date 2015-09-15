package com.huotu.huobanmall.seller.activity;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.adapter.LogisticsAdapter;
import com.huotu.huobanmall.seller.bean.LogisticsDetailModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 物流查看 界面
 */
public class LogisticsActivity extends BaseFragmentActivity {
    @Bind(R.id.logistics_list)
    RecyclerView _logistics_list;

    List<LogisticsDetailModel> _list=null;
    LogisticsAdapter _adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    protected void initView(){
        ButterKnife.bind(this);

        _list = new ArrayList<>();
        for( int i=0;i<10;i++) {
            LogisticsDetailModel item = new LogisticsDetailModel();
            if( i%2==0){
                item.set_context("时光飞逝，安迪·鲁宾（Andy Rubin）依旧对三星放弃收购安卓耿耿于怀");
            }else {
                item.set_context("打发撒旦发送思阿斯顿飞阿时光飞逝，安迪·鲁宾（Andy Rubin）依旧对三星放弃收购安卓耿耿于怀，而如今安卓依旧在市面上同苹果系统分庭抗礼。记得那是2004年的第一场雪，当时的国内智能手机市场主要占有者是诺基亚，改变世界的iPhone还没有到来，第一台安卓手机也是三年后才发布的。当时的智能手机远不像现在这般统一。各种杂牌手机盛行，而且每种系统都有对应的手机，互相之间是不兼容的，这也就为后来的故事埋下了伏笔。是打发撒的阿是打发受asdfasdf到发岁达发送");
            }
            _list.add(item);
        }

        _adapter=new LogisticsAdapter(this, _list);
        _logistics_list.setLayoutManager(new LinearLayoutManager(this));
        _logistics_list.setAdapter(_adapter);
    }
    
}
