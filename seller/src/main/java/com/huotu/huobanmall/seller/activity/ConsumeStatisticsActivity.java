package com.huotu.huobanmall.seller.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.adapter.ConsumeStatisticsAdapter;
import com.huotu.huobanmall.seller.bean.MJTopConsumeModel;
import com.huotu.huobanmall.seller.bean.MJTopScoreModel;
import com.huotu.huobanmall.seller.bean.OperateTypeEnum;
import com.huotu.huobanmall.seller.bean.SalesListModel;
import com.huotu.huobanmall.seller.bean.TopConsumeModel;
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
 * Created by Administrator on 2015/9/15.
 * 消费统计 界面
 */
public class ConsumeStatisticsActivity extends BaseFragmentActivity implements View.OnClickListener {
    @Bind(R.id.detail_btn)
    RadioButton detail_btn;
    @Bind(R.id.statistic_btn)
    RadioButton statistic_btn;
    @Bind(R.id.header_back)
    Button header_back;
    @Bind(R.id.header_operate)
    TextView header_operate;
    @Bind(R.id.salesdetail_listview)
    PullToRefreshListView _consumStatistics_listview;

    ConsumeStatisticsAdapter _consumeStatisticsAdapter;
    List<TopConsumeModel> _consumeStatisticsList = null;
    OperateTypeEnum _operateType = OperateTypeEnum.REFRESH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_detail);
        ButterKnife.bind(this);

        header_back.setOnClickListener(this);
        _consumeStatisticsList = new ArrayList<>();
//        SalesListModel saleslist = new SalesListModel();
//
//        saleslist.setMoney(Float.valueOf("1333"));
//
//        _consumeStatisticsList.add(saleslist);
//        saleslist = new SalesListModel();
//
//        saleslist.setMoney(Float.valueOf("1333"));
//
//        _consumeStatisticsList.add(saleslist);
//        saleslist = new SalesListModel();
//
//        saleslist.setMoney(Float.valueOf("1333"));
//
//        _consumeStatisticsList.add(saleslist);
//        saleslist = new SalesListModel();
//
//        saleslist.setMoney(Float.valueOf("1333"));
//
//        _consumeStatisticsList.add(saleslist);
        _consumeStatisticsAdapter = new ConsumeStatisticsAdapter(this, _consumeStatisticsList);
        _consumStatistics_listview.getRefreshableView().setAdapter(_consumeStatisticsAdapter);
        //_consumStatistics_listview.setMode(PullToRefreshBase.Mode.BOTH);

        _consumStatistics_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                getData();
            }
        });

        firstGetData();
    }

    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void firstGetData() {
        this.showProgressDialog("","正在获取数据，请稍等...");
        getData();
    }

    protected void getData( ){
        String url = Constant.TOPCONSUME_INTERFACE;
        HttpParaUtils httpParaUtils = new HttpParaUtils();
        url = httpParaUtils.getHttpGetUrl(url, null);

        GsonRequest<MJTopConsumeModel> request = new GsonRequest<MJTopConsumeModel>(
                Request.Method.GET,
                url ,
                MJTopConsumeModel.class,
                null,
                listener,
                this
        );

        VolleyRequestManager.getRequestQueue().add( request);
    }

    Response.Listener<MJTopConsumeModel> listener =new Response.Listener<MJTopConsumeModel>() {
        @Override
        public void onResponse(MJTopConsumeModel mjTopConsumeModel ) {
            ConsumeStatisticsActivity.this.closeProgressDialog();
            _consumStatistics_listview.onRefreshComplete();

            if( mjTopConsumeModel==null){
                DialogUtils.showDialog(ConsumeStatisticsActivity.this, ConsumeStatisticsActivity.this.getSupportFragmentManager(), "错误信息", "获取数据失败", "关闭");
                return;
            }
            if( mjTopConsumeModel.getSystemResultCode()!=1){
                SimpleDialogFragment.createBuilder(ConsumeStatisticsActivity.this, ConsumeStatisticsActivity.this.getSupportFragmentManager())
                        .setTitle("错误信息")
                        .setMessage( mjTopConsumeModel.getSystemResultDescription() )
                        .setNegativeButtonText("关闭")
                        .show();
                return;
            }else if( mjTopConsumeModel.getResultCode()== Constant.TOKEN_OVERDUE){
                ActivityUtils.getInstance().showActivity(ConsumeStatisticsActivity.this, LoginActivity.class);
                return;
            }
            else if( mjTopConsumeModel.getResultCode() != 1){
                SimpleDialogFragment.createBuilder( ConsumeStatisticsActivity.this , ConsumeStatisticsActivity.this.getSupportFragmentManager())
                        .setTitle("错误信息")
                        .setMessage( mjTopConsumeModel.getResultDescription() )
                        .setNegativeButtonText("关闭")
                        .show();
                return;
            }

            if( mjTopConsumeModel.getResultData() ==null || mjTopConsumeModel.getResultData().getList()==null||mjTopConsumeModel.getResultData().getList().size()<1){
                return;
            }


            _consumeStatisticsList.addAll(mjTopConsumeModel.getResultData().getList());

            _consumeStatisticsAdapter.notifyDataSetChanged();
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
