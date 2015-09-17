package com.huotu.huobanmall.seller.activity;

import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.adapter.GoodsAdapter;
import com.huotu.huobanmall.seller.adapter.LogisticsAdapter;
import com.huotu.huobanmall.seller.adapter.LogisticsGoodsAdapter;
import com.huotu.huobanmall.seller.bean.GoodsModel;
import com.huotu.huobanmall.seller.bean.LogisticsDetailModel;
import com.huotu.huobanmall.seller.bean.MJLogisticsDetailModel;
import com.huotu.huobanmall.seller.bean.OrderListProductModel;
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
 * 物流查看 界面
 */
public class LogisticsActivity extends BaseFragmentActivity {
    @Bind(R.id.logistics_goodsList)
    //RecyclerView _logistics_goods;
    ListView _logistics_goods;
    @Bind(R.id.logistics_list)
    //RecyclerView _logistics_list;
    ListView _logistics_list;
    @Bind(R.id.header_back)
    Button _headerBack;
    @Bind(R.id.logistics_pullScrollView)
    PullToRefreshScrollView _scrollView;
    List<OrderListProductModel> _goodsList;
    LogisticsGoodsAdapter _goodsAdapter;
    List<LogisticsDetailModel> _logisticsList=null;
    LogisticsAdapter _logisticAdapter;
    String _orderNo="";

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

        _scrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> pullToRefreshBase) {
                getData();
            }
        });

        if( getIntent()!=null && getIntent().hasExtra("orderNo")){
            _orderNo = getIntent().getStringExtra("orderNo");
        }

        _logisticsList = new ArrayList<>();
        for( int i=0;i<10;i++) {
            LogisticsDetailModel item = new LogisticsDetailModel();
            if( i%2==0){
                item.setSource("时光飞逝，安迪·鲁宾（Andy Rubin）依旧对三星放弃收购安卓耿耿于怀");
            }else {
                item.setSource("打发撒旦发送思阿斯顿飞阿时光飞逝，安迪·鲁宾（Andy Rubin）依旧对三星放弃收购安卓耿耿于怀，而如今安卓依旧在市面上同苹果系统分庭抗礼。记得那是2004年的第一场雪，当时的国内智能手机市场主要占有者是诺基亚，改变世界的iPhone还没有到来，第一台安卓手机也是三年后才发布的。当时的智能手机远不像现在这般统一。各种杂牌手机盛行，而且每种系统都有对应的手机，互相之间是不兼容的，这也就为后来的故事埋下了伏笔。是打发撒的阿是打发受asdfasdf到发岁达发送");
            }
            _logisticsList.add(item);
        }

        _logisticAdapter =new LogisticsAdapter(this, _logisticsList );
        //_logistics_list.setLayoutManager(new LinearLayoutManager(this));
        _logistics_list.setAdapter(_logisticAdapter);
        _headerBack.setOnClickListener(this);

        _goodsList = new ArrayList<>();

        for(int i=0;i<8;i++){
            OrderListProductModel item = new OrderListProductModel();
            item.setAmount(2);
            item.setMoney(67.44f);
            item.setPictureUrl("");
            item.setSpec("阿斯顿飞阿斯顿飞阿斯顿飞撒旦法");
            item.setTitle("丹杰仕休闲帆布鞋男2015秋季男鞋男士透气");
            _goodsList.add(item);
        }

        _goodsAdapter = new LogisticsGoodsAdapter(this, _goodsList);
        //_logistics_goods.setLayoutManager(new LinearLayoutManager(this));
        _logistics_goods.setAdapter(_goodsAdapter);

        _scrollView.getRefreshableView().smoothScrollTo(0, 0);

        firstGetData();
    }



    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    protected void firstGetData(){
        this.showProgressDialog("","正在获取数据，请稍等...");
        getData();
    }


    protected void getData(){
        String url = Constant.LOGISTICSDETAIL_INTERFACE;
        HttpParaUtils httpParaUtils = new HttpParaUtils();
        Map<String,String> paras = new HashMap<>();
        paras.put("orderNo", _orderNo);
        url = httpParaUtils.getHttpGetUrl(url , paras);

        GsonRequest<MJLogisticsDetailModel> request = new GsonRequest<MJLogisticsDetailModel>(
                Request.Method.GET,
                url,
                MJLogisticsDetailModel.class,
                null,
                requestListener,
                errorListener
        );
        VolleyRequestManager.getRequestQueue().add(request);
    }

    Response.Listener<MJLogisticsDetailModel> requestListener = new Response.Listener<MJLogisticsDetailModel>() {
        @Override
        public void onResponse(MJLogisticsDetailModel mjLogisticsDetailModel ) {
            LogisticsActivity.this.closeProgressDialog();
            _scrollView.onRefreshComplete();

            if(null == mjLogisticsDetailModel){
                DialogUtils.showDialog(LogisticsActivity.this, LogisticsActivity.this.getSupportFragmentManager()
                        , "错误信息", "请求失败", "关闭");
                return;
            }
            if(mjLogisticsDetailModel.getSystemResultCode()!=1){
                DialogUtils.showDialog(LogisticsActivity.this, LogisticsActivity.this.getSupportFragmentManager()
                        ,"错误信息",mjLogisticsDetailModel.getSystemResultDescription(),"关闭");
                return;
            }else if( mjLogisticsDetailModel.getResultCode()== Constant.TOKEN_OVERDUE){
                ActivityUtils.getInstance().skipActivity(LogisticsActivity.this,LoginActivity.class);
                return;
            }else if( mjLogisticsDetailModel.getResultCode() != 1){
                DialogUtils.showDialog(LogisticsActivity.this, LogisticsActivity.this.getSupportFragmentManager()
                        ,"错误信息",mjLogisticsDetailModel.getResultDescription(),"关闭");
                return;
            }

            if( mjLogisticsDetailModel.getResultData()==null){
                DialogUtils.showDialog(LogisticsActivity.this,LogisticsActivity.this.getSupportFragmentManager(),
                        "错误信息", "服务器返回空数据","关闭");
                return;
            }

            _logisticsList.add( mjLogisticsDetailModel.getResultData().getData() );

            _logisticAdapter.notifyDataSetChanged();
        }
    };
}
