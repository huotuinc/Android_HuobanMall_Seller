package com.huotu.huobanmall.seller.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.adapter.LogisticsGoodsAdapter;
import com.huotu.huobanmall.seller.adapter.ScoreExpandableAdapter;
import com.huotu.huobanmall.seller.bean.MJOrderDetailModel;
import com.huotu.huobanmall.seller.bean.OrderDetailModel;
import com.huotu.huobanmall.seller.bean.OrderListProductModel;
import com.huotu.huobanmall.seller.bean.OrderScoreModel;
import com.huotu.huobanmall.seller.common.Constant;
import com.huotu.huobanmall.seller.utils.ActivityUtils;
import com.huotu.huobanmall.seller.utils.DialogUtils;
import com.huotu.huobanmall.seller.utils.GsonRequest;
import com.huotu.huobanmall.seller.utils.HttpParaUtils;
import com.huotu.huobanmall.seller.utils.VolleyRequestManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 订单详情页面
 */
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
    @Bind(R.id.order_detail_scoreList)
    ExpandableListView _orderScoreList;

    OrderDetailModel _data=null;

    LogisticsGoodsAdapter orderGoodsAdapter;
    List<OrderListProductModel> _productList=null;

    ScoreExpandableAdapter _scoreAdapter=null;

    String _orderNo="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordermanagement_details);
        initView();

        demoData();
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

        if( getIntent()!=null && getIntent().hasExtra( Constant.Extra_OrderNo )){
            _orderNo = getIntent().getStringExtra( Constant.Extra_OrderNo );
        }

        _data = new OrderDetailModel();
        _productList=new ArrayList<>();
        OrderListProductModel orderListProductModel=new OrderListProductModel();
        orderListProductModel.setTitle("如何让熊孩子爱上刷牙？飞利浦新款智能牙刷加入游戏应用");
        orderListProductModel.setSpec("40*40");
        orderListProductModel.setMoney(1333f);
        orderListProductModel.setAmount(2333);
        _productList.add(orderListProductModel);
        orderListProductModel=new OrderListProductModel();
        orderListProductModel.setTitle("如何让熊孩子爱上刷牙？飞利浦新款智能牙刷加入游戏应用");
        orderListProductModel.setSpec("40*40");
        orderListProductModel.setMoney(1333f);
        orderListProductModel.setAmount(2333);
        _productList.add(orderListProductModel);
        orderListProductModel=new OrderListProductModel();
        orderListProductModel.setTitle("如何让熊孩子爱上刷牙？飞利浦新款智能牙刷加入游戏应用");
        orderListProductModel.setSpec("40*40");
        orderListProductModel.setMoney(1333f);
        orderListProductModel.setAmount(2333);
        _productList.add(orderListProductModel);
        orderListProductModel=new OrderListProductModel();
        orderListProductModel.setTitle("如何让熊孩子爱上刷牙？飞利浦新款智能牙刷加入游戏应用");
        orderListProductModel.setSpec("40*40");
        orderListProductModel.setMoney(1333f);
        orderListProductModel.setAmount(2333);
        _productList.add(orderListProductModel);
        orderListProductModel=new OrderListProductModel();
        orderListProductModel.setTitle("如何让熊孩子爱上刷牙？飞利浦新款智能牙刷加入游戏应用");
        orderListProductModel.setSpec("40*40");
        orderListProductModel.setMoney(1333f);
        orderListProductModel.setAmount(2333);
        _productList.add(orderListProductModel);
        orderListProductModel=new OrderListProductModel();
        orderListProductModel.setTitle("如何让熊孩子爱上刷牙？飞利浦新款智能牙刷加入游戏应用");
        orderListProductModel.setSpec("40*40");
        orderListProductModel.setMoney(1333f);
        orderListProductModel.setAmount(2333);
        _productList.add(orderListProductModel);

        _data.setList(_productList);

        orderGoodsAdapter=new  LogisticsGoodsAdapter(this,_productList);
        order_item_goodsList.setAdapter(orderGoodsAdapter);
        orderscrollview.smoothScrollTo(0,0);

    }

    protected void demoData(){
        List< OrderScoreModel> scoreList =new ArrayList<>();
        for( int i=0;i<4;i++){
            OrderScoreModel item =new OrderScoreModel();
            item.setOrderNo("阿斯顿飞阿斯顿飞");
            item.setType(1);
            List<OrderScoreModel> list= new ArrayList<>();
            if( i != 0 ) {
                for (int k = 0; k < 5; k++) {
                    OrderScoreModel child = new OrderScoreModel();
                    child.setType(2);
                    child.setOrderNo("1111111111" + i);
                    child.setGetTime(System.currentTimeMillis());
                    child.setScore(i);
                    child.setStatus("斯蒂芬打的费的发大阿是打发岁的发放");
                    child.setZzTime(System.currentTimeMillis());
                    list.add(child);
                }
            }
            item.setList(list);
            scoreList.add(item);
        }

        _scoreAdapter=new ScoreExpandableAdapter(this, scoreList);
        _orderScoreList.setAdapter(_scoreAdapter);
        if( _scoreAdapter.getGroupCount()>0) {
            _orderScoreList.expandGroup(0);
        }
    }

    protected void getData(){
        Map<String,String> paras = new HashMap<>();

        String url = Constant.ORDERDETAIL_INTERFACE;
        paras.put( Constant.Extra_OrderNo , _orderNo );
        HttpParaUtils utils = new HttpParaUtils();
        String urlString = utils.getHttpGetUrl(url, paras);

        GsonRequest<MJOrderDetailModel > request =
                new GsonRequest<>(
                        Request.Method.GET,
                        urlString,
                        MJOrderDetailModel.class,
                        null,
                        orderDetailListener,
                        this);

        this.showProgressDialog("","正在获取数据，请稍等...");

        VolleyRequestManager.getRequestQueue().add(request);
    }

    private Response.Listener< MJOrderDetailModel > orderDetailListener = new Response.Listener<MJOrderDetailModel>() {
        @Override
        public void onResponse( MJOrderDetailModel mjOrderDetailModel ) {
            OrdermanagementDetailsActivity.this.closeProgressDialog();
            if (mjOrderDetailModel == null) {
                DialogUtils.showDialog(OrdermanagementDetailsActivity.this
                        , OrdermanagementDetailsActivity.this.getSupportFragmentManager()
                        , "错误信息"
                        , "获取数据失败"
                        , "关闭");
                return;
            }
            if (mjOrderDetailModel.getSystemResultCode() != 1) {
                DialogUtils.showDialog(OrdermanagementDetailsActivity.this
                        , OrdermanagementDetailsActivity.this.getSupportFragmentManager()
                        , "错误信息"
                        , mjOrderDetailModel.getSystemResultDescription()
                        , "关闭");
                return;
            } else if (mjOrderDetailModel.getResultCode() == Constant.TOKEN_OVERDUE) {
                ActivityUtils.getInstance().showActivity(OrdermanagementDetailsActivity.this, LoginActivity.class);
                return;
            } else if (mjOrderDetailModel.getResultCode() != 1) {
                DialogUtils.showDialog(OrdermanagementDetailsActivity.this
                        , OrdermanagementDetailsActivity.this.getSupportFragmentManager()
                        , "错误信息"
                        , mjOrderDetailModel.getResultDescription()
                        , "关闭");
                return;
            }

            //TODO

        }
    };


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


