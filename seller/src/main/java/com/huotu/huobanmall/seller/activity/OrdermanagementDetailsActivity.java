package com.huotu.huobanmall.seller.activity;

import android.content.Intent;
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
import com.huotu.huobanmall.seller.bean.UserScoreModel;
import com.huotu.huobanmall.seller.common.Constant;
import com.huotu.huobanmall.seller.utils.ActivityUtils;
import com.huotu.huobanmall.seller.utils.DialogUtils;
import com.huotu.huobanmall.seller.utils.GsonRequest;
import com.huotu.huobanmall.seller.utils.HttpParaUtils;
import com.huotu.huobanmall.seller.utils.ObtainParamsMap;
import com.huotu.huobanmall.seller.utils.VolleyRequestManager;

import java.util.ArrayList;
import java.util.Date;
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
    @Bind(R.id.buyer)
    TextView buyer;
    @Bind(R.id.receiver)
    TextView receiver;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.moblic)
    TextView moblic;
    @Bind(R.id.orderNo)
    TextView orderNo;
    @Bind(R.id.orderdetail_amount)
    TextView tvAmount;
    @Bind(R.id.orderdetail_pay)
    TextView tvPaid;
    @Bind(R.id.logistics)
    TextView logistics;
    @Bind(R.id.order_item_goodsList)
    ListView order_item_goodsList;
    @Bind(R.id.orderscrollview)
    ScrollView  orderscrollview;
    @Bind(R.id.order_detail_scoreList)
    ExpandableListView _orderScoreList;

    OrderDetailModel _data=null;

    LogisticsGoodsAdapter _orderGoodsAdapter;
    List<OrderListProductModel> _productList=null;

    ScoreExpandableAdapter _scoreAdapter=null;

    String _orderNo="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordermanagement_details);
        initView();

        getData();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
    protected void initView() {
        ButterKnife.bind(this);
        header_operate.setVisibility(View.GONE);
        header_back.setOnClickListener(this);
        header_title.setText("订单管理详情");
        moblic.setOnClickListener(this);
        logistics.setOnClickListener(this);

        if( getIntent()!=null && getIntent().hasExtra( Constant.Extra_OrderNo )){
            _orderNo = getIntent().getStringExtra( Constant.Extra_OrderNo );
        }

        //_orderNo = "15986ae3-c92b-4d0a-b95e-f9978bbf51e9";

        _data = new OrderDetailModel();

        _orderGoodsAdapter= new LogisticsGoodsAdapter(this, _data.getList());
        order_item_goodsList.setAdapter(_orderGoodsAdapter);
        orderscrollview.smoothScrollTo(0, 0);
    }

    protected void getData(){
        if( false == canConnect()){
            OrdermanagementDetailsActivity.this.closeProgressDialog();
            return;
        }


        Map<String,String> paras = new HashMap<>();

        String url = Constant.ORDERDETAIL_INTERFACE;
        paras.put(Constant.Extra_OrderNo, _orderNo);
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

        this.showProgressDialog("", "正在获取数据，请稍等...");

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
            if( mjOrderDetailModel.getResultData().getData() ==null ){
                DialogUtils.showDialog(OrdermanagementDetailsActivity.this,OrdermanagementDetailsActivity.this.getSupportFragmentManager(),"错误信息","返回数据不正确","关闭");
                return;
            }

            //TODO
            _data = mjOrderDetailModel.getResultData().getData();
            _orderGoodsAdapter = new LogisticsGoodsAdapter(OrdermanagementDetailsActivity.this , _data.getList());
            buyer.setText( _data.getBuyer() );
            receiver.setText( _data.getReceiver() );
            address.setText(_data.getAddress());
            moblic.setText( "联系方式：" + _data.getContact() );
            orderNo.setText( _data.getOrderNo() );
            tvAmount.setText( "共"+_data.getAmount() +"件商品 实付:￥" );
            tvPaid.setText( String.valueOf( _data.getPaid() ) );

            if( _data.getScoreList() ==null ) {
                List<OrderScoreModel> scores =new ArrayList<>();
                _scoreAdapter=new ScoreExpandableAdapter( OrdermanagementDetailsActivity.this, scores);
                _orderScoreList.setAdapter(_scoreAdapter);
            }else{
                List<OrderScoreModel> scores = new ArrayList<>();
                for(int k=0;k<_data.getScoreList().size();k++){
                    UserScoreModel model = _data.getScoreList().get(k);
                    OrderScoreModel item = new OrderScoreModel();
                    item.setUserType(model.getUserType());
                    scores.add(item);

                    List<OrderScoreModel> list =new ArrayList<>();
                    OrderScoreModel child=new OrderScoreModel();
                    child.setStatus( model.getPresent() );
                    child.setGetTime(model.getGetTime());
                    child.setScore(model.getScore());
                    child.setZzTime(model.getRegularization());
                    child.setUserName( model.getUserName());
                    list.add(child);

                    item.setList(list);
                }

                _scoreAdapter = new ScoreExpandableAdapter(OrdermanagementDetailsActivity.this, scores);
                _orderScoreList.setAdapter(_scoreAdapter);
            }
        }
    };


    public void onClick(View v) {
        switch (v.getId()){
            case R.id.header_back: {
                finish();
            }
            break;
            case R.id.logistics: {
                Intent intent = new Intent();
                intent.putExtra(Constant.Extra_OrderNo , _orderNo );
                intent.setClass(OrdermanagementDetailsActivity.this, LogisticsActivity.class);
                ActivityUtils.getInstance().showActivity(OrdermanagementDetailsActivity.this, intent );
            }
            break;
        }
    }
}


