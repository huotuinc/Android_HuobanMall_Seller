package com.huotu.huobanmall.seller.activity;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.adapter.TopsalesAdapter;
import com.huotu.huobanmall.seller.bean.TopSalesModel;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/9/18.
 */
public class TopSalesActivity extends BaseFragmentActivity implements View.OnClickListener {
    @Bind(R.id.header_back)
    Button _headerBack;
    @Bind(R.id.topsales_listview)
    PullToRefreshListView topsales_listview;
    TopsalesAdapter topsalesAdapter;
    List<TopSalesModel> topsalesList = null;
    @Bind(R.id.header_title)
    TextView header_title;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topsales);
        ButterKnife.bind(this);

        initView();

    }
    protected void initView(){
        ButterKnife.bind(this);
        _headerBack.setOnClickListener(this);
        header_title.setText("商品销量前十");

        topsalesList = new ArrayList<>();
        TopSalesModel toplist = new TopSalesModel();
        toplist.setAmount(78);
        toplist.setName("撒旦撒旦撒大苏打撒旦撒大苏打撒旦撒旦撒旦");
        topsalesList.add(toplist);
        toplist = new TopSalesModel();
        toplist.setAmount(78);
        toplist.setName("撒旦撒旦撒大苏打撒旦撒大苏打撒旦撒旦撒旦");
        topsalesList.add(toplist);
        toplist = new TopSalesModel();
        toplist.setAmount(78);
        toplist.setName("撒旦撒旦撒大苏打撒旦撒大苏打撒旦撒旦撒旦");
        topsalesList.add(toplist);
        toplist = new TopSalesModel();
        toplist.setAmount(78);
        toplist.setName("撒旦撒旦撒大苏打撒旦撒大苏打撒旦撒旦撒旦");
        topsalesList.add(toplist);

        topsalesAdapter = new TopsalesAdapter(this, topsalesList);
        topsales_listview.getRefreshableView().setAdapter(topsalesAdapter);

        topsales_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                //getData();
            }
        });

        firstSaleGoodData();
    }



    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void firstSaleGoodData() {
        //this.showProgressDialog("", "正在获取数据，请稍等...");
        // getData();
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_back: {
                finish();

            }
            break;
            default:
                break;
        }

    }

}
