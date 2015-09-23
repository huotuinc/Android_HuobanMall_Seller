package com.huotu.huobanmall.seller.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.adapter.ConsumeDetailAdapter;
import com.huotu.huobanmall.seller.adapter.ConsumeStatisticsAdapter;
import com.huotu.huobanmall.seller.adapter.RebateStatisticsAdapter;
import com.huotu.huobanmall.seller.adapter.UserScoreAdapter;
import com.huotu.huobanmall.seller.bean.ConsumeListModel;
import com.huotu.huobanmall.seller.bean.MJConsumeListModel;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Bind(R.id.salesdetail_title)
    RadioGroup salesdetail_title;

    ConsumeStatisticsAdapter _consumeStatisticsAdapter;
    List<TopConsumeModel> _consumeStatisticsList = null;

    List<ConsumeListModel> _consumeDetailList =null;
    ConsumeDetailAdapter _consumeDetailAdapter=null;

    OperateTypeEnum _operateType = OperateTypeEnum.REFRESH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_detail);
        ButterKnife.bind(this);

        header_back.setOnClickListener(this);

        salesdetail_title.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.detail_btn) {
                    _consumStatistics_listview.setMode(PullToRefreshBase.Mode.BOTH);
                    _operateType = OperateTypeEnum.REFRESH;
                    getData_MX(_operateType, "");
                } else if (checkedId == R.id.statistic_btn) {
                    _consumStatistics_listview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                    _operateType = OperateTypeEnum.REFRESH;
                    getData_TJ();
                }
            }
        });

        View entmyview= new View(this);
        entmyview.setBackgroundResource(R.mipmap.tpzw);
        _consumStatistics_listview.setEmptyView(entmyview);
        _consumStatistics_listview.setMode(PullToRefreshBase.Mode.BOTH);

        _consumeStatisticsList = new ArrayList<>();
        _consumeStatisticsAdapter =new ConsumeStatisticsAdapter(this , _consumeStatisticsList);

        _consumeDetailList = new ArrayList<>();
        _consumeDetailAdapter = new ConsumeDetailAdapter(this, _consumeDetailList);
        _consumStatistics_listview.setAdapter(_consumeDetailAdapter);

        _consumStatistics_listview.setOnRefreshListener(
                new PullToRefreshBase.OnRefreshListener2<ListView>() {
                    @Override
                    public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                        if (detail_btn.isChecked()) {
                            _operateType = OperateTypeEnum.REFRESH;
                            getData_MX(OperateTypeEnum.REFRESH, "");
                        } else {
                            _operateType = OperateTypeEnum.REFRESH;
                            getData_TJ();
                        }
                    }

                    @Override
                    public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                        if (detail_btn.isChecked()) {
                            _operateType = OperateTypeEnum.LOADMORE;
                            getData_MX(OperateTypeEnum.LOADMORE, "");
                        }
                    }
                }
        );

        firstGetData();
    }

    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void firstGetData() {
        this.showProgressDialog("","正在获取数据，请稍等...");
        _operateType = OperateTypeEnum.REFRESH;
        getData_MX(_operateType, "");
    }

    protected void getData_MX( OperateTypeEnum operateType ,String key){
        String url = Constant.USERCONSUMELIST_INTERFACE;
        Map<String,String> paras = new HashMap<>();
        if( operateType== OperateTypeEnum.LOADMORE ){
            if( _consumeDetailList !=null && _consumeDetailList.size() > 0 ) {
                String lastDate = String.valueOf( _consumeDetailList.get( _consumeDetailList.size()-1 ).getTime().getTime() );
                paras.put("lastDate",lastDate);
            }
        }

        if( key!=null && key.length()>0){
            paras.put("key",key);
        }


        HttpParaUtils httpParaUtils = new HttpParaUtils();
        url = httpParaUtils.getHttpGetUrl(url, paras );

        GsonRequest<MJConsumeListModel> request = new GsonRequest<MJConsumeListModel>(
                Request.Method.GET,
                url ,
                MJConsumeListModel.class,
                null,
                listener_MX,
                this
        );

        VolleyRequestManager.getRequestQueue().add(request);
    }

    protected void getData_TJ(){
        String url = Constant.TOPCONSUME_INTERFACE;
        Map<String,String> paras = new HashMap<>();

        HttpParaUtils httpParaUtils = new HttpParaUtils();
        url = httpParaUtils.getHttpGetUrl(url, paras );

        GsonRequest<MJTopConsumeModel> request = new GsonRequest<MJTopConsumeModel>(
                Request.Method.GET,
                url ,
                MJTopConsumeModel.class,
                null,
                listener_TJ,
                this
        );

        VolleyRequestManager.getRequestQueue().add( request);
    }

    Response.Listener<MJTopConsumeModel> listener_TJ =new Response.Listener<MJTopConsumeModel>() {
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

            _consumeStatisticsList.clear();
            if( mjTopConsumeModel.getResultData() !=null && mjTopConsumeModel.getResultData().getList() !=null
                    && mjTopConsumeModel.getResultData().getList().size()>0){
                _consumeStatisticsList.addAll(mjTopConsumeModel.getResultData().getList());
            }
            _consumStatistics_listview.setAdapter( _consumeStatisticsAdapter );
        }
    };



    Response.Listener<MJConsumeListModel> listener_MX =new Response.Listener<MJConsumeListModel>() {
        @Override
        public void onResponse(MJConsumeListModel mjConsumeListModel ) {
            ConsumeStatisticsActivity.this.closeProgressDialog();
            _consumStatistics_listview.onRefreshComplete();

            if( mjConsumeListModel==null){
                DialogUtils.showDialog(ConsumeStatisticsActivity.this, ConsumeStatisticsActivity.this.getSupportFragmentManager(), "错误信息", "获取数据失败", "关闭");
                return;
            }
            if( mjConsumeListModel.getSystemResultCode()!=1){
                SimpleDialogFragment.createBuilder(ConsumeStatisticsActivity.this, ConsumeStatisticsActivity.this.getSupportFragmentManager())
                        .setTitle("错误信息")
                        .setMessage( mjConsumeListModel.getSystemResultDescription() )
                        .setNegativeButtonText("关闭")
                        .show();
                return;
            }else if( mjConsumeListModel.getResultCode()== Constant.TOKEN_OVERDUE){
                ActivityUtils.getInstance().showActivity(ConsumeStatisticsActivity.this, LoginActivity.class);
                return;
            }
            else if( mjConsumeListModel.getResultCode() != 1){
                SimpleDialogFragment.createBuilder( ConsumeStatisticsActivity.this , ConsumeStatisticsActivity.this.getSupportFragmentManager())
                        .setTitle("错误信息")
                        .setMessage( mjConsumeListModel.getResultDescription() )
                        .setNegativeButtonText("关闭")
                        .show();
                return;
            }

            if( _operateType == OperateTypeEnum.REFRESH ) {
                _consumeDetailList.clear();
                if( mjConsumeListModel.getResultData() !=null
                        && mjConsumeListModel.getResultData().getList() !=null
                        && mjConsumeListModel.getResultData().getList().size()> 0 ) {
                    _consumeDetailList.addAll(mjConsumeListModel.getResultData().getList());
                }
                _consumStatistics_listview.setAdapter( _consumeDetailAdapter );
            }else{
                if( mjConsumeListModel.getResultData() !=null
                        && mjConsumeListModel.getResultData().getList() !=null
                        && mjConsumeListModel.getResultData().getList().size()> 0 ) {
                    _consumeDetailList.addAll(mjConsumeListModel.getResultData().getList());
                }
                _consumeDetailAdapter.notifyDataSetChanged();
            }
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
