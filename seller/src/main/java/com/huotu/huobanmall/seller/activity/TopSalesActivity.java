package com.huotu.huobanmall.seller.activity;

import android.app.Activity;
import android.os.Bundle;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.adapter.TopGoodsAdapter;
import com.huotu.huobanmall.seller.adapter.TopsalesAdapter;
import com.huotu.huobanmall.seller.bean.MJTopGoodsModel;
import com.huotu.huobanmall.seller.bean.TopGoodsModel;
import com.huotu.huobanmall.seller.common.Constant;
import com.huotu.huobanmall.seller.utils.ActivityUtils;
import com.huotu.huobanmall.seller.utils.DialogUtils;
import com.huotu.huobanmall.seller.utils.GsonRequest;
import com.huotu.huobanmall.seller.utils.HttpParaUtils;
import com.huotu.huobanmall.seller.utils.ToastUtils;
import com.huotu.huobanmall.seller.utils.VolleyRequestManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/9/18.
 * 商品销售前10排名
 */
public class TopSalesActivity extends BaseFragmentActivity implements View.OnClickListener {
    @Bind(R.id.header_back)
    Button _headerBack;
    @Bind(R.id.topsales_listview)
    PullToRefreshListView topGoods_listview;
    TopGoodsAdapter topGoodsAdapter;
    List<TopGoodsModel> topGoodsList = null;
    @Bind(R.id.header_title)
    TextView header_title;
    Handler handler=new Handler();
    View emptyView=null;
    boolean isSetEmptyView = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topsales);
        initView();
    }
    protected void initView(){
        ButterKnife.bind(this);
        _headerBack.setOnClickListener(this);
        header_title.setText("商品销量前十");

        emptyView= new View(this);
        emptyView.setBackgroundResource(R.mipmap.tpzw);

        topGoodsList = new ArrayList<>();
        topGoodsAdapter = new TopGoodsAdapter(this, topGoodsList);
        topGoods_listview.getRefreshableView().setAdapter(topGoodsAdapter);

        //topGoods_listview.setEmptyView(emptyView);

        topGoods_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
               getData();
            }
        });

        firstSaleGoodData();
    }

    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void firstSaleGoodData() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if( TopSalesActivity.this.isFinishing() ) return;
                topGoods_listview.setRefreshing(true);
            }
        },1000);
    }

    protected void getData(){
        String url = Constant.TOPGOODS_INTERFACE;
        HttpParaUtils httpParaUtils = new HttpParaUtils();
        url = httpParaUtils.getHttpGetUrl(url, null);

        GsonRequest<MJTopGoodsModel> request = new GsonRequest<MJTopGoodsModel>(
                Request.Method.GET,
                url ,
                MJTopGoodsModel.class,
                null,
                new MyListener(this),
                new MyErrorListener(this)
        );

        VolleyRequestManager.AddRequest(request);
    }

    //Response.Listener<MJTopGoodsModel> listener =new Response.Listener<MJTopGoodsModel>() {
    static class MyListener implements Response.Listener<MJTopGoodsModel>{
        WeakReference<TopSalesActivity> ref;
        public MyListener(TopSalesActivity act){
            ref = new WeakReference<TopSalesActivity>(act);
        }

        @Override
        public void onResponse(MJTopGoodsModel mjTopGoodsModel) {
            if( ref.get()==null )return;

            if (ref.get().isFinishing()) return;

            ref.get().closeProgressDialog();
            ref.get().topGoods_listview.onRefreshComplete();

            if ( ref.get(). isSetEmptyView == false) {
                ref.get().topGoods_listview.setEmptyView(ref.get().emptyView);
                ref.get().isSetEmptyView=true;
            }

            if (!ref.get().validateData(mjTopGoodsModel)) {
                return;
            }

            ref.get().topGoodsList.clear();

            if (mjTopGoodsModel.getResultData() != null
                    && mjTopGoodsModel.getResultData().getList() != null
                    && mjTopGoodsModel.getResultData().getList().size() > 0) {
                ref.get().topGoodsList.addAll(mjTopGoodsModel.getResultData().getList());
            }
            ref.get().topGoodsAdapter.notifyDataSetChanged();
        }
    };

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

    static class MyErrorListener implements Response.ErrorListener{
        WeakReference<TopSalesActivity> ref;
        public MyErrorListener(TopSalesActivity act){
            ref = new WeakReference<TopSalesActivity>(act);
        }
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            if( ref.get() ==null ) return;

            if( ref.get().isFinishing() ) return;

            ref.get().topGoods_listview.onRefreshComplete();
            ref.get().closeProgressDialog();
            String message="";
            if( volleyError instanceof TimeoutError){
                message = "网络连接超时";
            }else if( volleyError instanceof NetworkError || volleyError instanceof NoConnectionError) {
                message ="网络请求异常，请检查网络状态";
            }else if( volleyError instanceof ParseError){
                message = "数据解析失败，请检测数据的正确性";
            }else if( volleyError instanceof ServerError || volleyError instanceof AuthFailureError){
                if( null != volleyError.networkResponse){
                    message=new String( volleyError.networkResponse.data);
                }else{
                    message = volleyError.getMessage();
                }
            }

            if( message.length()<1){
                message = "网络请求失败，请检查网络状态";
            }

            ToastUtils.showLong(message, Toast.LENGTH_LONG);
        }
    }
}
