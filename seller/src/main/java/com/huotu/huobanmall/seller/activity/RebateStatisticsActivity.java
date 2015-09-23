package com.huotu.huobanmall.seller.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huotu.android.library.libedittext.EditText;
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

    @Bind(R.id.detail_btn)
    RadioButton detail_btn;
    @Bind(R.id.statistic_btn)
    RadioButton statistic_btn;
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
        header_back.setOnClickListener(this);
        _rebateStatisticsList = new ArrayList<>();
        _rebateStatisticsAdapter = new RebateStatisticsAdapter(this, _rebateStatisticsList);
        _rebateStatistics_listview.getRefreshableView().setAdapter(_rebateStatisticsAdapter);
        View emptyView= new View(this);
        emptyView.setBackgroundResource(R.mipmap.tpzw);
        _rebateStatistics_listview.setEmptyView(emptyView);
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

            _rebateStatisticsList.clear();

            if( mjTopScoreModel.getResultData() !=null && mjTopScoreModel.getResultData().getList() !=null
                    && mjTopScoreModel.getResultData().getList().size()>0){
                _rebateStatisticsList.addAll(mjTopScoreModel.getResultData().getList());
            }

            _rebateStatisticsAdapter.notifyDataSetChanged();
        }
    };

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_back: {
                finish();

            }
            break;
            case R.id.header_operate: {
               search();

            }
            break;

        }

    }
protected void search(){
    AlertDialog.Builder dialog = new AlertDialog.Builder(RebateStatisticsActivity.this);

    LinearLayout llContent = new LinearLayout(this);
    LinearLayout.LayoutParams params =new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT);
    llContent.setOrientation(LinearLayout.HORIZONTAL);
    params.setMargins(5, 5, 5, 5);
    llContent.setPadding(8,10,8,0);
    llContent.setLayoutParams(params);
    //发送提示信息
    final EditText etMessage=new EditText(this);
    etMessage.setHint("请输入搜索内容");
    etMessage.setLayoutParams(params);
    etMessage.setSingleLine(true);

    llContent.addView(etMessage);

    dialog.setView(llContent);

    dialog.setTitle("搜索框");

    dialog.setPositiveButton("搜索", new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
//            // TODO Auto-generated method stub
//            // 送流量接口
//            String mobile="";
//            if( bundle.containsKey("originMobile")) {
//                mobile = bundle.getString("originMobile");
//            }
//            String message=etMessage.getText().toString().trim();//"朕赏你点流量,还不谢恩";
//            int flow = Integer.parseInt(flowText.getText().toString());
//            String flowStr= String.valueOf(flow);
//            new MakeProvideAsyncTask(SendFlowActivity.this , mHandler , mobile , flowStr , message ).execute();
        }
    });
    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            // TODO Auto-generated method stub

        }
    });

    dialog.show();

}



}
