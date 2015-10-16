package com.huotu.huobanmall.seller.activity;

import android.os.Bundle;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
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
import com.huotu.huobanmall.seller.utils.VolleyRequestManager;

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
                listener,
                this
        );

        VolleyRequestManager.AddRequest(request);
    }
    Response.Listener<MJTopGoodsModel> listener =new Response.Listener<MJTopGoodsModel>() {
        @Override
        public void onResponse(MJTopGoodsModel mjTopGoodsModel) {
            if (TopSalesActivity.this.isFinishing()) return;

            TopSalesActivity.this.closeProgressDialog();
            topGoods_listview.onRefreshComplete();

            if (isSetEmptyView == false) {
                topGoods_listview.setEmptyView(emptyView);
                isSetEmptyView=true;
            }

            if (!validateData(mjTopGoodsModel)) {
                return;
            }

            topGoodsList.clear();

            if (mjTopGoodsModel.getResultData() != null
                    && mjTopGoodsModel.getResultData().getList() != null
                    && mjTopGoodsModel.getResultData().getList().size() > 0) {
                topGoodsList.addAll(mjTopGoodsModel.getResultData().getList());
            }
            topGoodsAdapter.notifyDataSetChanged();
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
}
