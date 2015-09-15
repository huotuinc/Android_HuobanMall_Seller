package com.huotu.huobanmall.seller.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.adapter.SalesDetailAdapter;
import com.huotu.huobanmall.seller.bean.OperateTypeEnum;
import com.huotu.huobanmall.seller.bean.SalesListModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SalesDetailActivity extends BaseFragmentActivity implements CompoundButton.OnCheckedChangeListener, AdapterView.OnItemClickListener,View.OnClickListener {
    @Bind(R.id.header_title)
    TextView header_title;
    @Bind(R.id.header_back)
    Button header_back;
    @Bind(R.id.salesdetail_listview)
    PullToRefreshListView _salesDetail_listview;
    SalesDetailAdapter salesDetailAdapter;
    List<SalesListModel> _saledetailList = null;
    OperateTypeEnum _operateType = OperateTypeEnum.REFRESH;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_detail);
        ButterKnife.bind(this);
        header_title.setText("销售明细");
        header_back.setOnClickListener(this);
        _saledetailList = new ArrayList<>();
        SalesListModel saleslist= new SalesListModel();
        saleslist.setTime(new Date(System.currentTimeMillis()));
        saleslist.setMoney(Float.valueOf("1333"));
        saleslist.setOrderNo(String.valueOf(111111));
        _saledetailList.add(saleslist);
        saleslist=new SalesListModel();
        saleslist.setTime(new Date(System.currentTimeMillis()));
        saleslist.setMoney(Float.valueOf("1333"));
        saleslist.setOrderNo(String.valueOf(111111));
        _saledetailList.add(saleslist);
        saleslist=new SalesListModel();
        saleslist.setTime(new Date(System.currentTimeMillis()));
        saleslist.setMoney(Float.valueOf("1333"));
        saleslist.setOrderNo(String.valueOf(111111));
        _saledetailList.add(saleslist);
        saleslist=new SalesListModel();
        saleslist.setTime(new Date(System.currentTimeMillis()));
        saleslist.setMoney(Float.valueOf("1333"));
        saleslist.setOrderNo(String.valueOf(111111));
        _saledetailList.add(saleslist);
        salesDetailAdapter= new SalesDetailAdapter(this, _saledetailList );
        _salesDetail_listview.getRefreshableView().setAdapter(salesDetailAdapter);
        _salesDetail_listview.setMode(PullToRefreshBase.Mode.BOTH);

        _salesDetail_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}