package com.huotu.huobanmall.seller.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.adapter.LogisticsAdapter;
import com.huotu.huobanmall.seller.adapter.LogisticsGoodsAdapter;
import com.huotu.huobanmall.seller.bean.LogisticsDataModel;
import com.huotu.huobanmall.seller.bean.LogisticsDetailModel;
import com.huotu.huobanmall.seller.bean.MJLogisticsDetailModel;
import com.huotu.huobanmall.seller.bean.OrderListProductModel;
import com.huotu.huobanmall.seller.common.Constant;
import com.huotu.huobanmall.seller.utils.ActivityUtils;
import com.huotu.huobanmall.seller.utils.BitmapLoader;
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
    @Bind(R.id.logistics_logo)
    NetworkImageView _logistics_logo;
    @Bind(R.id.logistics_status)
    TextView _logistics_status;
    @Bind(R.id.logistics_source)
    TextView _logistics_source;
    @Bind(R.id.logistics_orderNo)
    TextView _logistics_orderNo;
    @Bind(R.id.logistics_goodsList)
    ListView _logistics_goods;
    @Bind(R.id.logistics_list)
    ListView _logistics_list;
    @Bind(R.id.header_back)
    Button _headerBack;
    @Bind(R.id.logistics_pullScrollView)
    PullToRefreshScrollView _scrollView;
    LogisticsGoodsAdapter _goodsAdapter;
    LogisticsDetailModel _data=null;
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

        if( getIntent()!=null && getIntent().hasExtra(Constant.Extra_OrderNo)){
            _orderNo = getIntent().getStringExtra( Constant.Extra_OrderNo);
        }

        //
        _orderNo="111ec76e-fe08-45a8-a13c-782e290c5ba1";

        _data=new LogisticsDetailModel();
        List<LogisticsDataModel> logisticsList = new ArrayList<>();
        for( int i=0;i<10;i++) {
            LogisticsDataModel item = new LogisticsDataModel();
            item.setAreacode("322105");
            item.setAreaname("浙江东阳");
            item.setCode("10021");
            item.setCompany("顺丰物流");
            item.setTimes("2015-09-22 13:00:00");
            if( i%2==0){
                item.setContext("时光飞逝，安迪·鲁宾（Andy Rubin）依旧对三星放弃收购安卓耿耿于怀");
            }else {
                item.setContext("阿时光飞逝，安迪·鲁宾（Andy Rubin）依旧对三星放弃收购安卓耿耿于怀，而如今安卓依旧在市面上同苹果系统分庭抗礼。记得那是2004年的第一场雪，当时的国内智能手机市场主要占有者是诺基亚，改变世界的iPhone还没有到来，第一台安卓手机也是三年后才发布的。当时的智能手机远不像现在这般统一。各种杂牌手机盛行，而且每种系统都有对应的手机，互相之间是不兼容的，这也就为后来的故事埋下了伏笔。");
            }
            logisticsList.add(item);
        }
        _data.setTrack(logisticsList);

        _logisticAdapter =new LogisticsAdapter(this, logisticsList );
        _logistics_list.setAdapter(_logisticAdapter);
        _headerBack.setOnClickListener(this);

        List<OrderListProductModel> goodsList = new ArrayList<>();

        for(int i=0;i<8;i++){
            OrderListProductModel item = new OrderListProductModel();
            item.setAmount(2);
            item.setMoney(67.44f);
            item.setPictureUrl("");
            item.setSpec("阿斯顿飞阿斯顿飞阿斯顿飞撒旦法");
            item.setTitle("丹杰仕休闲帆布鞋男2015秋季男鞋男士透气");
            goodsList.add(item);
        }
        _data.setList(goodsList);

        _goodsAdapter = new LogisticsGoodsAdapter(this, goodsList);
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
                this
        );
        VolleyRequestManager.getRequestQueue().add(request);
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        _scrollView.onRefreshComplete();
        super.onErrorResponse(volleyError);
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

            _data = mjLogisticsDetailModel.getResultData().getData();

            setData();

        }
    };

    protected void setData(){
        if(_data==null) return;

        BitmapLoader.create().displayUrl( this , _logistics_logo , _data.getPictureURL());
        _logistics_orderNo.setText(_data.getNo());
        _logistics_status.setText(_data.getStatusName());
        _logistics_source.setText(_data.getSource());

        _goodsAdapter=new LogisticsGoodsAdapter(this, _data.getList());
        _logistics_goods.setAdapter(_goodsAdapter);

        _logisticAdapter= new LogisticsAdapter(this, _data.getTrack());
        _logistics_list.setAdapter(_logisticAdapter);
    }
}
