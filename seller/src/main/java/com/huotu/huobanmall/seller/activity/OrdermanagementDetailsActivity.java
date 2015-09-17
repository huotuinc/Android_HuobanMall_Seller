package com.huotu.huobanmall.seller.activity;



import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.adapter.LogisticsGoodsAdapter;
import com.huotu.huobanmall.seller.bean.OrderListProductModel;
import com.huotu.huobanmall.seller.utils.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OrdermanagementDetailsActivity extends BaseFragmentActivity implements View.OnClickListener {

    @Bind(R.id.header_back)
    TextView header_back;
    @Bind(R.id.header_title)
    TextView header_title;
    @Bind(R.id.header_operate)
    TextView header_operate;
    @Bind(R.id.moblic)
    TextView moblic;
    @Bind(R.id.logistics)
    TextView logistics;
    @Bind(R.id.order_item_goodsList)
    ListView order_item_goodsList;
    @Bind(R.id.orderscrollview)
    ScrollView  orderscrollview;
    List<OrderListProductModel> _list=null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordermanagement_details);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
    protected void initView() {
        ButterKnife.bind(this);
        header_back.setOnClickListener(this);
        header_title.setText("订单管理详情");
        moblic.setOnClickListener(this);
        logistics.setOnClickListener(this);
        _list=new ArrayList<>();
        OrderListProductModel orderListProductModel=new OrderListProductModel();
        orderListProductModel.setTitle("如何让熊孩子爱上刷牙？飞利浦新款智能牙刷加入游戏应用");
        orderListProductModel.setSpec("40*40");
        orderListProductModel.setMoney(1333f);
        orderListProductModel.setAmount(2333);
        _list.add(orderListProductModel);
        orderListProductModel=new OrderListProductModel();
        orderListProductModel.setTitle("如何让熊孩子爱上刷牙？飞利浦新款智能牙刷加入游戏应用");
        orderListProductModel.setSpec("40*40");
        orderListProductModel.setMoney(1333f);
        orderListProductModel.setAmount(2333);
        _list.add(orderListProductModel);
        orderListProductModel=new OrderListProductModel();
        orderListProductModel.setTitle("如何让熊孩子爱上刷牙？飞利浦新款智能牙刷加入游戏应用");
        orderListProductModel.setSpec("40*40");
        orderListProductModel.setMoney(1333f);
        orderListProductModel.setAmount(2333);
        _list.add(orderListProductModel);
        orderListProductModel=new OrderListProductModel();
        orderListProductModel.setTitle("如何让熊孩子爱上刷牙？飞利浦新款智能牙刷加入游戏应用");
        orderListProductModel.setSpec("40*40");
        orderListProductModel.setMoney(1333f);
        orderListProductModel.setAmount(2333);
        _list.add(orderListProductModel);
        orderListProductModel=new OrderListProductModel();
        orderListProductModel.setTitle("如何让熊孩子爱上刷牙？飞利浦新款智能牙刷加入游戏应用");
        orderListProductModel.setSpec("40*40");
        orderListProductModel.setMoney(1333f);
        orderListProductModel.setAmount(2333);
        _list.add(orderListProductModel);
        orderListProductModel=new OrderListProductModel();
        orderListProductModel.setTitle("如何让熊孩子爱上刷牙？飞利浦新款智能牙刷加入游戏应用");
        orderListProductModel.setSpec("40*40");
        orderListProductModel.setMoney(1333f);
        orderListProductModel.setAmount(2333);
        _list.add(orderListProductModel);
        orderGoodsAdapter=new  LogisticsGoodsAdapter(this,_list);
        order_item_goodsList.setAdapter(orderGoodsAdapter);
        orderscrollview.smoothScrollTo(0,0);


    }

    public LogisticsGoodsAdapter orderGoodsAdapter;



    public void onClick(View v) {
        switch (v.getId()){
            case R.id.header_back: {
                finish();
            }
            break;
            case R.id.logistics: {
                ActivityUtils.getInstance().showActivity(OrdermanagementDetailsActivity.this, LogisticsActivity.class);
            }
            break;
        }

    }
}


