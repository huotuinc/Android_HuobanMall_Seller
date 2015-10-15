package com.huotu.huobanmall.seller.activity;


import android.os.Bundle;
import android.os.Handler;
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

import org.apache.commons.logging.Log;

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
    Handler _handler=new Handler();

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

        _data=new LogisticsDetailModel();
        _data.setTrack(new ArrayList<LogisticsDataModel>());
        _data.setList(new ArrayList<OrderListProductModel>());
        _logisticAdapter =new LogisticsAdapter(this, _data.getTrack() );
        _logistics_list.setAdapter(_logisticAdapter);
        _headerBack.setOnClickListener(this);

        _goodsAdapter = new LogisticsGoodsAdapter(this, _data.getList());
        _logistics_goods.setAdapter(_goodsAdapter);

        _scrollView.getRefreshableView().smoothScrollTo(0, 0);

        firstGetData();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    protected void firstGetData(){
        _handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if( LogisticsActivity.this.isFinishing() ) return;
                _scrollView.setRefreshing(true);
            }
        },1000);
    }


    protected void getData(){
        if( false == canConnect()){
            LogisticsActivity.this.closeProgressDialog();
            _scrollView.onRefreshComplete();
            return;
        }

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

        VolleyRequestManager.AddRequest( request );
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        if(LogisticsActivity.this.isFinishing()) return;

        _scrollView.onRefreshComplete();
        super.onErrorResponse(volleyError);
    }

    Response.Listener<MJLogisticsDetailModel> requestListener = new Response.Listener<MJLogisticsDetailModel>() {
        @Override
        public void onResponse(MJLogisticsDetailModel mjLogisticsDetailModel ) {
            if( LogisticsActivity.this.isFinishing() ) return;

            LogisticsActivity.this.closeProgressDialog();
            _scrollView.onRefreshComplete();

            if( !validateData(mjLogisticsDetailModel) ){
                return;
            }
//            if(null == mjLogisticsDetailModel){
//                DialogUtils.showDialog(LogisticsActivity.this, LogisticsActivity.this.getSupportFragmentManager()
//                        , "错误信息", "请求失败", "关闭");
//                return;
//            }
//            if(mjLogisticsDetailModel.getSystemResultCode()!=1){
//                DialogUtils.showDialog(LogisticsActivity.this, LogisticsActivity.this.getSupportFragmentManager()
//                        ,"错误信息",mjLogisticsDetailModel.getSystemResultDescription(),"关闭");
//                return;
//            }else if( mjLogisticsDetailModel.getResultCode()== Constant.TOKEN_OVERDUE){
//                ActivityUtils.getInstance().skipActivity(LogisticsActivity.this,LoginActivity.class);
//                return;
//            }else if( mjLogisticsDetailModel.getResultCode() != 1){
//                DialogUtils.showDialog(LogisticsActivity.this, LogisticsActivity.this.getSupportFragmentManager()
//                        ,"错误信息",mjLogisticsDetailModel.getResultDescription(),"关闭");
//                return;
//            }

            if( null == mjLogisticsDetailModel.getResultData() ){
                DialogUtils.showDialog(LogisticsActivity.this , LogisticsActivity.this.getSupportFragmentManager(),
                        "错误信息", "返回数据不完整","关闭");
                return;
            }

            _data = mjLogisticsDetailModel.getResultData().getData();
            setData();
            _scrollView.getRefreshableView().smoothScrollTo(0, 0);
        }
    };

    protected void setData(){
        if(_data==null) return;

        BitmapLoader.create().displayUrl( this , _logistics_logo , _data.getPictureURL() , R.mipmap.wl,R.mipmap.wl);
        _logistics_orderNo.setText(_data.getNo());
        _logistics_status.setText(_data.getStatusName());
        _logistics_source.setText(_data.getSource());

        _goodsAdapter=new LogisticsGoodsAdapter(this, _data.getList());
        _logistics_goods.setAdapter(_goodsAdapter);

        _logisticAdapter= new LogisticsAdapter(this, _data.getTrack());
        _logistics_list.setAdapter(_logisticAdapter);
    }
}
