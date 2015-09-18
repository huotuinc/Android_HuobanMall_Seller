package com.huotu.huobanmall.seller.activity;

import android.os.Bundle;
import android.view.View;

import android.widget.Button;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.adapter.RebateStatisticsAdapter;

import com.huotu.huobanmall.seller.bean.MJSaleListModel;
import com.huotu.huobanmall.seller.bean.MJTopScoreModel;
import com.huotu.huobanmall.seller.bean.OperateTypeEnum;
import com.huotu.huobanmall.seller.bean.SalesListModel;
import com.huotu.huobanmall.seller.bean.TopScoreModel;
import com.huotu.huobanmall.seller.common.Constant;
import com.huotu.huobanmall.seller.utils.ActivityUtils;
import com.huotu.huobanmall.seller.utils.DialogUtils;
import com.huotu.huobanmall.seller.utils.GsonRequest;
import com.huotu.huobanmall.seller.utils.HttpParaUtils;
import com.huotu.huobanmall.seller.utils.VolleyRequestManager;

import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/9/15.
 * 返利积分 界面
 */
public class RebateStatisticsActivity extends BaseFragmentActivity {

    @Bind(R.id.header_title)
    TextView header_title;
    @Bind(R.id.header_back)
    Button header_back;
    @Bind(R.id.header_operate)
    TextView header_operate;
    @Bind(R.id.salesdetail_listview)
    PullToRefreshListView _rebateStatistics_listview;
    RebateStatisticsAdapter _rebateStatisticsAdapter;
    List<TopScoreModel> _rebateStatisticsList = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_detail);
        ButterKnife.bind(this);

        header_title.setText("返利统计");
        header_back.setOnClickListener(this);
        _rebateStatisticsList = new ArrayList<>();
        _rebateStatisticsAdapter = new RebateStatisticsAdapter(this, _rebateStatisticsList);
        _rebateStatistics_listview.getRefreshableView().setAdapter(_rebateStatisticsAdapter);

        _rebateStatistics_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
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
        this.showProgressDialog("","正在获取数据，请稍等...");
        getData();
    }

    protected void getData(){
        String url = Constant.TOPSCORE_INTERFACE;
        HttpParaUtils httpParaUtils = new HttpParaUtils();
        url = httpParaUtils.getHttpGetUrl(url, null);

        GsonRequest<MJTopScoreModel> request = new GsonRequest<MJTopScoreModel>(
                Request.Method.GET,
                url ,
                MJTopScoreModel.class,
                null,
                listener,
                this
        );



        VolleyRequestManager.getRequestQueue().add( request);
    }

    Response.Listener<MJTopScoreModel> listener =new Response.Listener<MJTopScoreModel>() {
        @Override
        public void onResponse(MJTopScoreModel mjTopScoreModel ) {
             RebateStatisticsActivity.this.closeProgressDialog();
            _rebateStatistics_listview.onRefreshComplete();

            if( mjTopScoreModel==null){
                DialogUtils.showDialog(RebateStatisticsActivity.this, RebateStatisticsActivity.this.getSupportFragmentManager(), "错误信息", "获取数据失败", "关闭");
                return;
            }
            if( mjTopScoreModel.getSystemResultCode()!=1){
                SimpleDialogFragment.createBuilder(RebateStatisticsActivity.this, RebateStatisticsActivity.this.getSupportFragmentManager())
                        .setTitle("错误信息")
                        .setMessage( mjTopScoreModel.getSystemResultDescription() )
                        .setNegativeButtonText("关闭")
                        .show();
                return;
            }else if( mjTopScoreModel.getResultCode()== Constant.TOKEN_OVERDUE){
                ActivityUtils.getInstance().showActivity(RebateStatisticsActivity.this, LoginActivity.class);
                return;
            }
            else if( mjTopScoreModel.getResultCode() != 1){
                SimpleDialogFragment.createBuilder( RebateStatisticsActivity.this , RebateStatisticsActivity.this.getSupportFragmentManager())
                        .setTitle("错误信息")
                        .setMessage( mjTopScoreModel.getResultDescription() )
                        .setNegativeButtonText("关闭")
                        .show();
                return;
            }

            if( mjTopScoreModel.getResultData() ==null || mjTopScoreModel.getResultData().getList()==null||mjTopScoreModel.getResultData().getList().size()<1){
                return;
            }

            _rebateStatisticsList.addAll(mjTopScoreModel.getResultData().getList());

            _rebateStatisticsAdapter.notifyDataSetChanged();
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
