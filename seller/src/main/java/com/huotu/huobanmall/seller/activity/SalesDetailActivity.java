package com.huotu.huobanmall.seller.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.adapter.SalesDetailAdapter;
import com.huotu.huobanmall.seller.bean.MJSaleListModel;
import com.huotu.huobanmall.seller.bean.OperateTypeEnum;
import com.huotu.huobanmall.seller.bean.SalesListModel;
import com.huotu.huobanmall.seller.common.Constant;
import com.huotu.huobanmall.seller.fragment.SaleGoodsFragment;
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
    SalesDetailAdapter _salesDetailAdapter;
    List<SalesListModel> _saledetailList = null;
    OperateTypeEnum _operateType = OperateTypeEnum.REFRESH;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_detail);
        ButterKnife.bind(this);
        _header_back.setOnClickListener(this);
        _saledetailList = new ArrayList<>();
//        SalesListModel saleslist= new SalesListModel();
//        saleslist.setTime(new Date(System.currentTimeMillis()));
//        saleslist.setMoney(Float.valueOf("1333"));
//        saleslist.setOrderNo(String.valueOf(111111));
//        _saledetailList.add(saleslist);
//        saleslist=new SalesListModel();
//        saleslist.setTime(new Date(System.currentTimeMillis()));
//        saleslist.setMoney(Float.valueOf("1333"));
//        saleslist.setOrderNo(String.valueOf(111111));
//        _saledetailList.add(saleslist);
//        saleslist=new SalesListModel();
//        saleslist.setTime(new Date(System.currentTimeMillis()));
//        saleslist.setMoney(Float.valueOf("1333"));
//        saleslist.setOrderNo(String.valueOf(111111));
//        _saledetailList.add(saleslist);
//        saleslist=new SalesListModel();
//        saleslist.setTime(new Date(System.currentTimeMillis()));
//        saleslist.setMoney(Float.valueOf("1333"));
//        saleslist.setOrderNo(String.valueOf(111111));
//        _saledetailList.add(saleslist);
        _salesDetailAdapter= new SalesDetailAdapter(this, _saledetailList );
        _salesDetail_ListView.getRefreshableView().setAdapter(_salesDetailAdapter);
        _salesDetail_ListView.setMode(PullToRefreshBase.Mode.BOTH);
        View emptyView= new View(this);
        emptyView.setBackgroundResource(R.mipmap.tpzw);
        _salesDetail_ListView.setEmptyView(emptyView);
        _salesDetail_ListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                _operateType = OperateTypeEnum.REFRESH;
                getData( _operateType);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                _operateType = OperateTypeEnum.LOADMORE;
                getData(_operateType);
            }
        });

        firstSaleGoodData();
    }

    protected void getData( OperateTypeEnum type ){
        String url = Constant.SALESLIST_INTERFACE;
        HttpParaUtils httpParaUtils = new HttpParaUtils();
        if( type == OperateTypeEnum.REFRESH ) {
            url = httpParaUtils.getHttpGetUrl(url, null);
        }else {
            Map<String, String> paras = new HashMap<>();
            if( _saledetailList !=null && _saledetailList.size() >0 ) {
                Date lastDate = _saledetailList.get(_saledetailList.size() - 1).getTime();
                paras.put("lastDate", String.valueOf(lastDate.getTime()));
            }
            url= httpParaUtils.getHttpGetUrl(url , paras);
        }

        GsonRequest<MJSaleListModel> request = new GsonRequest<>(
                Request.Method.GET,
                url ,
                MJSaleListModel.class,
                null,
                listener,
                this
        );

        VolleyRequestManager.getRequestQueue().add( request);
    }

    Response.Listener<MJSaleListModel> listener =new Response.Listener<MJSaleListModel>() {
        @Override
        public void onResponse(MJSaleListModel mjSaleListModel) {
            SalesDetailActivity.this.closeProgressDialog();
            _salesDetail_ListView.onRefreshComplete();

            if( mjSaleListModel==null){
                DialogUtils.showDialog(SalesDetailActivity.this, SalesDetailActivity.this.getSupportFragmentManager(), "错误信息", "获取数据失败", "关闭");
                return;
            }
            if( mjSaleListModel.getSystemResultCode()!=1){
                DialogUtils.showDialog(SalesDetailActivity.this, SalesDetailActivity.this.getSupportFragmentManager()
                        ,"错误信息"
                        , mjSaleListModel.getSystemResultDescription()
                        ,"关闭");
                return;
            }else if( mjSaleListModel.getResultCode()== Constant.TOKEN_OVERDUE){
                ActivityUtils.getInstance().skipActivity(SalesDetailActivity.this, LoginActivity.class);
                return;
            }
            else if( mjSaleListModel.getResultCode() != 1){
                DialogUtils.showDialog(SalesDetailActivity.this, SalesDetailActivity.this.getSupportFragmentManager()
                        ,"错误信息"
                        , mjSaleListModel.getResultDescription()
                        ,"关闭");
                return;
            }

            if(_operateType == OperateTypeEnum.REFRESH){
                _saledetailList.clear();
                if( mjSaleListModel.getResultData() !=null && mjSaleListModel.getResultData().getList() !=null && mjSaleListModel.getResultData().getList().size()>0) {
                    _saledetailList.addAll(mjSaleListModel.getResultData().getList());
                }
            }else{
                if( mjSaleListModel.getResultData() !=null && mjSaleListModel.getResultData().getList() !=null && mjSaleListModel.getResultData().getList().size()>0) {
                    _saledetailList.addAll(mjSaleListModel.getResultData().getList());
                }
            }
            _salesDetailAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        _salesDetail_ListView.onRefreshComplete();
        super.onErrorResponse(volleyError);
    }

    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void firstSaleGoodData() {
        this.showProgressDialog("","正在获取数据，请稍等...");
        _operateType= OperateTypeEnum.REFRESH;
        getData(_operateType);
    }

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}