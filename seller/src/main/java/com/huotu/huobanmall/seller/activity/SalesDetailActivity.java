package com.huotu.huobanmall.seller.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.adapter.SalesDetailAdapter;
import com.huotu.huobanmall.seller.adapter.TopsalesAdapter;
import com.huotu.huobanmall.seller.bean.MJSaleListModel;
import com.huotu.huobanmall.seller.bean.MJTopSalesModel;
import com.huotu.huobanmall.seller.bean.OperateTypeEnum;
import com.huotu.huobanmall.seller.bean.SalesListModel;
import com.huotu.huobanmall.seller.bean.TopSalesModel;
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
 * 销售明细 界面
 */
public class SalesDetailActivity extends BaseFragmentActivity implements CompoundButton.OnCheckedChangeListener, AdapterView.OnItemClickListener,View.OnClickListener {
    @Bind(R.id.header_bar)
    RelativeLayout header_bar;
    @Bind(R.id.search_bar)
    RelativeLayout search_bar;
    @Bind(R.id.search_cancel)
    Button search_cancel;
    @Bind(R.id.search_text)
    com.huotu.android.library.libedittext.EditText search_text;
    @Bind(R.id.header_back)
    Button _header_back;
    @Bind(R.id.header_operate)
    TextView header_operate;
    @Bind(R.id.salesdetail_listview)
    PullToRefreshListView _salesDetail_ListView;
    @Bind(R.id.detail_btn)
    RadioButton detail_btn;
    @Bind(R.id.statistic_btn)
    RadioButton statistic_btn;
    @Bind(R.id.salesdetail_title)
    RadioGroup salesdetail_title;

    SalesDetailAdapter _salesDetailAdapter;

    TopsalesAdapter _topSalesAdapter;

    List<SalesListModel> _saledetailList = null;

    List<TopSalesModel> _topSalesList=null;

    OperateTypeEnum _operateType = OperateTypeEnum.REFRESH;

    Handler _handler=new Handler();
    View emptyView=null;
    boolean isSetEmptyView = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_detail);
        ButterKnife.bind(this);
        _header_back.setOnClickListener(this);
        header_operate.setOnClickListener(this);
        search_cancel.setOnClickListener(this);
        search_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    if (TextUtils.isEmpty(search_text.getText())) {
                        search_text.requestFocus();
                        search_text.setError("不能为空");
                    } else {
                        detail_btn.setChecked(true);
                        _operateType = OperateTypeEnum.REFRESH;
                        SalesDetailActivity.this.showProgressDialog("", "正在获取数据，请稍等...");
                        getData_MX(_operateType);
                    }
                    return true;
                }
                return false;
            }
        });

        salesdetail_title.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.detail_btn) {
                    header_operate.setVisibility(View.VISIBLE);
                    _salesDetail_ListView.setMode(PullToRefreshBase.Mode.BOTH);
                    _operateType = OperateTypeEnum.REFRESH;
                    getData_MX(_operateType);
                } else if (checkedId == R.id.statistic_btn) {
                    header_operate.setVisibility(View.GONE);
                    _salesDetail_ListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                    _operateType = OperateTypeEnum.REFRESH;
                    getData_TJ();
                }
            }
        });

        _topSalesList =new ArrayList<>();
        _topSalesAdapter =new TopsalesAdapter(this , _topSalesList);

        _saledetailList = new ArrayList<>();
        _salesDetailAdapter= new SalesDetailAdapter(this, _saledetailList );
        _salesDetail_ListView.getRefreshableView().setAdapter(_salesDetailAdapter);
        _salesDetail_ListView.setMode(PullToRefreshBase.Mode.BOTH);

        emptyView= new View(this);
        emptyView.setBackgroundResource(R.mipmap.tpzw);

        //_salesDetail_ListView.setEmptyView(emptyView);

        _salesDetail_ListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                if (detail_btn.isChecked()) {
                    _operateType = OperateTypeEnum.REFRESH;
                    getData_MX(_operateType);
                }else {
                    _operateType = OperateTypeEnum.REFRESH;
                    getData_TJ();
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                _operateType = OperateTypeEnum.LOADMORE;
                if (detail_btn.isChecked()) {
                    getData_MX(_operateType);
                }
            }
        });

        firstSaleGoodData();
    }

    protected void getData_MX( OperateTypeEnum type  ){
        String url = Constant.SALESLIST_INTERFACE;
        Map<String,String> paras = new HashMap<>();
        HttpParaUtils httpParaUtils = new HttpParaUtils();
        if( type == OperateTypeEnum.REFRESH ) {
            //url = httpParaUtils.getHttpGetUrl(url, null);
        }else {

            if( _saledetailList !=null && _saledetailList.size() >0 ) {
                Date lastDate = _saledetailList.get(_saledetailList.size() - 1).getTime();
                paras.put("lastDate", String.valueOf(lastDate.getTime()));
            }
        }

        String key = search_text.getText().toString().trim();
        if( key!=null && key.length()>0){
            paras.put("key",key);
        }

        url= httpParaUtils.getHttpGetUrl(url , paras);
        GsonRequest<MJSaleListModel> request = new GsonRequest<>(
                Request.Method.GET,
                url ,
                MJSaleListModel.class,
                null,
                listener_MX,
                this
        );

        VolleyRequestManager.AddRequest(request);
    }

    protected void getData_TJ(){
        String url = Constant.TOPSALES_INTERFACE;
        HttpParaUtils httpParaUtils = new HttpParaUtils();
        url = httpParaUtils.getHttpGetUrl(url, null);

        GsonRequest<MJTopSalesModel> request = new GsonRequest<MJTopSalesModel>(
                Request.Method.GET,
                url ,
                MJTopSalesModel.class,
                null,
                listener_TJ,
                this
        );

        VolleyRequestManager.AddRequest(request);
    }

    Response.Listener<MJTopSalesModel> listener_TJ =new Response.Listener<MJTopSalesModel>() {
        @Override
        public void onResponse(MJTopSalesModel mjTopSalesModel ) {
            SalesDetailActivity.this.closeProgressDialog();
            _salesDetail_ListView.onRefreshComplete();

            if(isSetEmptyView==false){
                _salesDetail_ListView.setEmptyView(emptyView);
                isSetEmptyView=true;
            }

            if(!validateData(mjTopSalesModel)){return;}
//            if( mjTopSalesModel==null){
//                DialogUtils.showDialog(SalesDetailActivity.this, SalesDetailActivity.this.getSupportFragmentManager(), "错误信息", "获取数据失败", "关闭");
//                return;
//            }
//            if( mjTopSalesModel.getSystemResultCode()!=1){
//                SimpleDialogFragment.createBuilder(SalesDetailActivity.this, SalesDetailActivity.this.getSupportFragmentManager())
//                        .setTitle("错误信息")
//                        .setMessage( mjTopSalesModel.getSystemResultDescription() )
//                        .setNegativeButtonText("关闭")
//                        .show();
//                return;
//            }else if( mjTopSalesModel.getResultCode()== Constant.TOKEN_OVERDUE){
//                ActivityUtils.getInstance().showActivity(SalesDetailActivity.this, LoginActivity.class);
//                return;
//            }
//            else if( mjTopSalesModel.getResultCode() != 1){
//                SimpleDialogFragment.createBuilder( SalesDetailActivity.this , SalesDetailActivity.this.getSupportFragmentManager())
//                        .setTitle("错误信息")
//                        .setMessage( mjTopSalesModel.getResultDescription() )
//                        .setNegativeButtonText("关闭")
//                        .show();
//                return;
//            }

            _topSalesList.clear();
            if( mjTopSalesModel.getResultData() !=null && mjTopSalesModel.getResultData().getList() !=null
                    && mjTopSalesModel.getResultData().getList().size()>0){
                _topSalesList.addAll(mjTopSalesModel.getResultData().getList());
            }
            _salesDetail_ListView.setAdapter( _topSalesAdapter );
        }
    };


    Response.Listener<MJSaleListModel> listener_MX =new Response.Listener<MJSaleListModel>() {
        @Override
        public void onResponse(MJSaleListModel mjSaleListModel) {
           if( SalesDetailActivity.this.isFinishing() ) return;
            SalesDetailActivity.this.closeProgressDialog();
            _salesDetail_ListView.onRefreshComplete();

            if(!isSetEmptyView){
                _salesDetail_ListView.setEmptyView(emptyView);
                isSetEmptyView=true;
            }

            if(!validateData(mjSaleListModel)){
                return;
            }

            if(_operateType == OperateTypeEnum.REFRESH){
                _saledetailList.clear();
                if( mjSaleListModel.getResultData() !=null && mjSaleListModel.getResultData().getList() !=null && mjSaleListModel.getResultData().getList().size()>0) {
                    _saledetailList.addAll(mjSaleListModel.getResultData().getList());
                }
                _salesDetail_ListView.setAdapter( _salesDetailAdapter );
            }else{
                if( mjSaleListModel.getResultData() !=null && mjSaleListModel.getResultData().getList() !=null && mjSaleListModel.getResultData().getList().size()>0) {
                    _saledetailList.addAll(mjSaleListModel.getResultData().getList());
                }
                _salesDetailAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        if( SalesDetailActivity.this.isFinishing() )return;
        _salesDetail_ListView.onRefreshComplete();
        super.onErrorResponse(volleyError);
    }

    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void firstSaleGoodData() {
        _handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if( SalesDetailActivity.this.isFinishing() ) return;

                _operateType= OperateTypeEnum.REFRESH;
                _salesDetail_ListView.setRefreshing(true);
            }
        },800);
    }

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
                break;
            }
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}