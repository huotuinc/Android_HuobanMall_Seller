package com.huotu.huobanmall.seller.activity;

import android.os.Bundle;
import android.view.View;

import android.widget.Button;

import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.adapter.RebateStatisticsAdapter;

import com.huotu.huobanmall.seller.bean.OperateTypeEnum;
import com.huotu.huobanmall.seller.bean.SalesListModel;

import java.util.ArrayList;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/9/15.
 */
public class RebateStatisticsActivity extends BaseFragmentActivity {


    @Bind(R.id.header_title)
    TextView header_title;
    @Bind(R.id.header_back)
    Button header_back;
    @Bind(R.id.header_operate)
    TextView header_operate;
    @Bind(R.id.salesdetail_listview)
    PullToRefreshListView _rebateStatistics_listview;
    RebateStatisticsAdapter rebateStatisticsAdapter;
    List<SalesListModel> _srebateStatisticsList = null;
    OperateTypeEnum _operateType = OperateTypeEnum.REFRESH;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_detail);
        ButterKnife.bind(this);
        header_title.setText("返利统计");
        header_back.setOnClickListener(this);
        _srebateStatisticsList = new ArrayList<>();
        SalesListModel saleslist = new SalesListModel();
        saleslist.setMoblie("(" + "18767152078" + ")");
        _srebateStatisticsList.add(saleslist);
        saleslist = new SalesListModel();
        saleslist.setMoblie("(" + "18767152078" + ")");
        _srebateStatisticsList.add(saleslist);
        saleslist = new SalesListModel();
        saleslist.setMoblie("(" + "18767152078" + ")");
        _srebateStatisticsList.add(saleslist);
        saleslist = new SalesListModel();
        saleslist.setMoblie("(" + "18767152078" + ")");
        _srebateStatisticsList.add(saleslist);
        rebateStatisticsAdapter = new RebateStatisticsAdapter(this, _srebateStatisticsList);
        _rebateStatistics_listview.getRefreshableView().setAdapter(rebateStatisticsAdapter);
        _rebateStatistics_listview.setMode(PullToRefreshBase.Mode.BOTH);

        _rebateStatistics_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                _operateType = OperateTypeEnum.REFRESH;

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                _operateType = OperateTypeEnum.LOADMORE;

            }
        });

        firstSaleGoodData();
    }

    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void firstSaleGoodData() {

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
