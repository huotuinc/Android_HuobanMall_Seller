package com.huotu.huobanmall.seller.activity;

import android.os.Bundle;

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
import com.huotu.huobanmall.seller.adapter.TopsalesAdapter;
import com.huotu.huobanmall.seller.bean.MJTopSalesModel;
import com.huotu.huobanmall.seller.bean.TopSalesModel;
import com.huotu.huobanmall.seller.common.Constant;
import com.huotu.huobanmall.seller.utils.ActivityUtils;
import com.huotu.huobanmall.seller.utils.DialogUtils;
import com.huotu.huobanmall.seller.utils.GsonRequest;
import com.huotu.huobanmall.seller.utils.HttpParaUtils;
import com.huotu.huobanmall.seller.utils.VolleyRequestManager;


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/9/18.
 */
public class TopSalesActivity extends BaseFragmentActivity implements View.OnClickListener {
    @Bind(R.id.header_back)
    Button _headerBack;
    @Bind(R.id.topsales_listview)
    PullToRefreshListView topsales_listview;
    TopsalesAdapter topsalesAdapter;
    List<TopSalesModel> topsalesList = null;
    @Bind(R.id.header_title)
    TextView header_title;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topsales);
        ButterKnife.bind(this);

        initView();

    }
    protected void initView(){
        ButterKnife.bind(this);
        _headerBack.setOnClickListener(this);
        header_title.setText("商品销量前十");

        topsalesList = new ArrayList<>();
        topsalesAdapter = new TopsalesAdapter(this, topsalesList);
        topsales_listview.getRefreshableView().setAdapter(topsalesAdapter);


        View entmyview= new View(this);
        entmyview.setBackgroundResource(R.mipmap.tpzw);
        topsales_listview.setEmptyView(entmyview);
        topsales_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
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
        this.showProgressDialog("", "正在获取数据，请稍等...");
         getData();
    }
    protected void getData(){
        String url = Constant.TOPSALES_INTERFACE;
        HttpParaUtils httpParaUtils = new HttpParaUtils();
        url = httpParaUtils.getHttpGetUrl(url, null);

        GsonRequest<MJTopSalesModel> request = new GsonRequest<MJTopSalesModel>(
                Request.Method.GET,
                url ,
                MJTopSalesModel.class,
                null,
                listener,
                this
        );



        VolleyRequestManager.getRequestQueue().add(request);
    }
    Response.Listener<MJTopSalesModel> listener =new Response.Listener<MJTopSalesModel>() {
        @Override
        public void onResponse(MJTopSalesModel mjTopSalesModel ) {
            TopSalesActivity.this.closeProgressDialog();
            topsales_listview.onRefreshComplete();

            if( mjTopSalesModel==null){
                DialogUtils.showDialog(TopSalesActivity.this, TopSalesActivity.this.getSupportFragmentManager(), "错误信息", "获取数据失败", "关闭");
                return;
            }
            if( mjTopSalesModel.getSystemResultCode()!=1){
                SimpleDialogFragment.createBuilder(TopSalesActivity.this, TopSalesActivity.this.getSupportFragmentManager())
                        .setTitle("错误信息")
                        .setMessage( mjTopSalesModel.getSystemResultDescription() )
                        .setNegativeButtonText("关闭")
                        .show();
                return;
            }else if( mjTopSalesModel.getResultCode()== Constant.TOKEN_OVERDUE){
                ActivityUtils.getInstance().showActivity(TopSalesActivity.this, LoginActivity.class);
                return;
            }
            else if( mjTopSalesModel.getResultCode() != 1){
                SimpleDialogFragment.createBuilder( TopSalesActivity.this , TopSalesActivity.this.getSupportFragmentManager())
                        .setTitle("错误信息")
                        .setMessage( mjTopSalesModel.getResultDescription() )
                        .setNegativeButtonText("关闭")
                        .show();
                return;
            }

            if( mjTopSalesModel.getResultData() ==null || mjTopSalesModel.getResultData().getList()==null||mjTopSalesModel.getResultData().getList().size()<1){
                return;
            }


            topsalesList.addAll(mjTopSalesModel.getResultData().getList());

            topsalesAdapter.notifyDataSetChanged();
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
