package com.huotu.huobanmall.seller.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import android.view.inputmethod.EditorInfo;
import android.widget.Button;

import android.widget.ListView;
import android.widget.RadioButton;

import android.widget.RelativeLayout;

import android.widget.RadioGroup;



import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.adapter.RebateStatisticsAdapter;

import com.huotu.huobanmall.seller.adapter.UserScoreAdapter;
import com.huotu.huobanmall.seller.bean.MJTopScoreModel;
import com.huotu.huobanmall.seller.bean.MJUserScoreModel;
import com.huotu.huobanmall.seller.bean.OperateTypeEnum;
import com.huotu.huobanmall.seller.bean.TopScoreModel;
import com.huotu.huobanmall.seller.bean.UserScoreModel;
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
 * 返利积分 界面
 */
public class RebateStatisticsActivity extends BaseFragmentActivity {
    @Bind(R.id.header_bar)
    RelativeLayout header_bar;
    @Bind(R.id.search_bar)
    RelativeLayout search_bar;
    @Bind(R.id.search_cancel)
    Button search_cancel;
    @Bind(R.id.search_text)
    com.huotu.android.library.libedittext.EditText search_text;
    @Bind(R.id.salesdetail_title)
    RadioGroup goods_title;
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
    RebateStatisticsAdapter _topScoreAdapter;
    UserScoreAdapter _userScoreAdapter;
    List<TopScoreModel> _topScoreList = null;
    List<UserScoreModel> _userScoreList =null;
    OperateTypeEnum _operateType = OperateTypeEnum.REFRESH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_detail);
        ButterKnife.bind(this);

        PullToRefreshListView.Mode m = _rebateStatistics_listview.getMode();

        goods_title.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.detail_btn) {
                    _rebateStatistics_listview.setMode(PullToRefreshBase.Mode.BOTH );
                    _operateType = OperateTypeEnum.REFRESH;
                    getData_MX(_operateType, "");
                } else if (checkedId == R.id.statistic_btn) {
                    _rebateStatistics_listview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                    _operateType = OperateTypeEnum.REFRESH;
                    getData_TJ();
                }
            }
        });

        header_back.setOnClickListener(this);
        header_operate.setOnClickListener(this);
        search_cancel.setOnClickListener(this);
        search_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH  ||(event!=null&&event.getKeyCode()== KeyEvent.KEYCODE_ENTER)){
                    if (TextUtils.isEmpty(search_text.getText())){
                        search_text.requestFocus();
                        search_text.setError("不能为空");
                    }else {
                        //do reseach
                        detail_btn.setChecked(true);
                        String key = search_text.getText().toString().trim();
                        _operateType = OperateTypeEnum.REFRESH;
                        getData_MX( _operateType , key );
                    }
                    return true;
                }
                return false;
            }
        });

        View emptyView= new View(this);
        emptyView.setBackgroundResource(R.mipmap.tpzw);
        _rebateStatistics_listview.setEmptyView(emptyView);
        _rebateStatistics_listview.setMode(PullToRefreshBase.Mode.BOTH);

        _userScoreList = new ArrayList<>();
        _userScoreAdapter = new UserScoreAdapter(this,_userScoreList);
        _rebateStatistics_listview.setAdapter(_userScoreAdapter);

        _topScoreList = new ArrayList<>();
        _topScoreAdapter = new RebateStatisticsAdapter(this, _topScoreList );

        _rebateStatistics_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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
        });

        firstGetData();
    }

    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void firstGetData() {
        this.showProgressDialog("", "正在获取数据，请稍等...");
        _operateType = OperateTypeEnum.REFRESH;
        getData_MX( OperateTypeEnum.REFRESH, "");
    }

    protected void getData_MX( OperateTypeEnum operateType ,String key ){
        String url = Constant.USERSCORELIST_INTERFACE;
        Map<String,String> paras = new HashMap<>();
        if( operateType== OperateTypeEnum.LOADMORE ){
            if( _userScoreList !=null && _userScoreList.size() > 0 ) {
                String lastId = String.valueOf( _userScoreList.get( _userScoreList.size()-1 ).getPid() );
                paras.put("lastId",lastId);
            }
        }

        if( key!=null && key.length()>0){
            paras.put("key",key);
        }

        HttpParaUtils httpParaUtils = new HttpParaUtils();
        url = httpParaUtils.getHttpGetUrl(url, paras);

        GsonRequest<MJUserScoreModel> request = new GsonRequest<MJUserScoreModel>(
                Request.Method.GET,
                url ,
                MJUserScoreModel.class,
                null,
                listener_MX,
                this
        );

        VolleyRequestManager.getRequestQueue().add(request);
    }

    protected void getData_TJ(){
        String url = Constant.TOPSCORE_INTERFACE;
        HttpParaUtils httpParaUtils = new HttpParaUtils();
        url = httpParaUtils.getHttpGetUrl(url, null);

        GsonRequest<MJTopScoreModel> request = new GsonRequest<MJTopScoreModel>(
                Request.Method.GET,
                url ,
                MJTopScoreModel.class,
                null,
                listener_TJ,
                this
        );

        VolleyRequestManager.getRequestQueue().add( request);
    }

    Response.Listener<MJUserScoreModel> listener_MX =new Response.Listener<MJUserScoreModel>() {
        @Override
        public void onResponse(MJUserScoreModel mjUserScoreModel ) {
            RebateStatisticsActivity.this.closeProgressDialog();
            _rebateStatistics_listview.onRefreshComplete();

            if( mjUserScoreModel==null){
                DialogUtils.showDialog(RebateStatisticsActivity.this, RebateStatisticsActivity.this.getSupportFragmentManager(), "错误信息", "获取数据失败", "关闭");
                return;
            }
            if( mjUserScoreModel.getSystemResultCode()!=1){
                SimpleDialogFragment.createBuilder(RebateStatisticsActivity.this, RebateStatisticsActivity.this.getSupportFragmentManager())
                        .setTitle("错误信息")
                        .setMessage( mjUserScoreModel.getSystemResultDescription() )
                        .setNegativeButtonText("关闭")
                        .show();
                return;
            }else if( mjUserScoreModel.getResultCode()== Constant.TOKEN_OVERDUE){
                ActivityUtils.getInstance().skipActivity(RebateStatisticsActivity.this, LoginActivity.class);
                return;
            }
            else if( mjUserScoreModel.getResultCode() != 1){
                SimpleDialogFragment.createBuilder( RebateStatisticsActivity.this , RebateStatisticsActivity.this.getSupportFragmentManager())
                        .setTitle("错误信息")
                        .setMessage( mjUserScoreModel.getResultDescription() )
                        .setNegativeButtonText("关闭")
                        .show();
                return;
            }

            if( _operateType == OperateTypeEnum.REFRESH ) {

                _userScoreList.clear();

                if (mjUserScoreModel.getResultData() != null && mjUserScoreModel.getResultData().getList() != null
                        && mjUserScoreModel.getResultData().getList().size() > 0) {
                    _userScoreList.addAll(mjUserScoreModel.getResultData().getList());
                }
                _rebateStatistics_listview.setAdapter( _userScoreAdapter );
            }else if( _operateType == OperateTypeEnum.LOADMORE){
                if (mjUserScoreModel.getResultData() != null && mjUserScoreModel.getResultData().getList() != null
                        && mjUserScoreModel.getResultData().getList().size() > 0) {
                    _userScoreList.addAll(mjUserScoreModel.getResultData().getList());
                }
                _userScoreAdapter.notifyDataSetChanged();
            }
        }
    };

    Response.Listener<MJTopScoreModel> listener_TJ =new Response.Listener<MJTopScoreModel>() {
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

            _topScoreList.clear();
            if( mjTopScoreModel.getResultData() !=null && mjTopScoreModel.getResultData().getList() !=null
                    && mjTopScoreModel.getResultData().getList().size()>0){
                _topScoreList.addAll(mjTopScoreModel.getResultData().getList());
            }
            _rebateStatistics_listview.setAdapter( _topScoreAdapter );
        }
    };

    protected void openSearchBar(){
        search_bar.setVisibility(View.VISIBLE);
        header_bar.setVisibility(View.GONE);
    }
    protected void closeSearchBar(){
        search_text.setText("");
        search_bar.setVisibility(View.GONE);
        header_bar.setVisibility(View.VISIBLE);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_back: {
                finish();
            }
            break;
            case R.id.header_operate:{
                openSearchBar();
                break;
            }
            case R.id.search_cancel:{
                closeSearchBar();
                break;
            }
            default:
                break;
        }

    }



}
