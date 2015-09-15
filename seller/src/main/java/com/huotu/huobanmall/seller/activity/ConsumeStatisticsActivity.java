package com.huotu.huobanmall.seller.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.adapter.ConsumeStatisticsAdapter;
import com.huotu.huobanmall.seller.bean.OperateTypeEnum;
import com.huotu.huobanmall.seller.bean.SalesListModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/9/15.
 */
public class ConsumeStatisticsActivity extends BaseFragmentActivity implements View.OnClickListener {
    @Bind(R.id.header_title)
    TextView header_title;
    @Bind(R.id.header_back)
    Button header_back;
    @Bind(R.id.header_operate)
    TextView header_operate;
    @Bind(R.id.salesdetail_listview)
    PullToRefreshListView _consumStatistics_listview;
    ConsumeStatisticsAdapter consumeStatisticsAdapter;
    List<SalesListModel> _consumeStatisticsList = null;
    OperateTypeEnum _operateType = OperateTypeEnum.REFRESH;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_detail);
        ButterKnife.bind(this);
        header_title.setText("消费统计");
        header_back.setOnClickListener(this);
        _consumeStatisticsList = new ArrayList<>();
        SalesListModel saleslist = new SalesListModel();

        saleslist.setMoney(Float.valueOf("1333"));

        _consumeStatisticsList.add(saleslist);
        saleslist = new SalesListModel();

        saleslist.setMoney(Float.valueOf("1333"));

        _consumeStatisticsList.add(saleslist);
        saleslist = new SalesListModel();

        saleslist.setMoney(Float.valueOf("1333"));

        _consumeStatisticsList.add(saleslist);
        saleslist = new SalesListModel();

        saleslist.setMoney(Float.valueOf("1333"));

        _consumeStatisticsList.add(saleslist);
        consumeStatisticsAdapter = new ConsumeStatisticsAdapter(this, _consumeStatisticsList);
        _consumStatistics_listview.getRefreshableView().setAdapter(consumeStatisticsAdapter);
        _consumStatistics_listview.setMode(PullToRefreshBase.Mode.BOTH);

        _consumStatistics_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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
