package com.huotu.huobanmall.seller.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.adapter.BillDataAdapter;
import com.huotu.huobanmall.seller.adapter.OrderDataAdapter;
import com.huotu.huobanmall.seller.bean.GoodsModel;
import com.huotu.huobanmall.seller.bean.MJOrderListModel;
import com.huotu.huobanmall.seller.bean.OperateTypeEnum;
import com.huotu.huobanmall.seller.bean.OrderListModel;
import com.huotu.huobanmall.seller.bean.OrderListProductModel;
import com.huotu.huobanmall.seller.bean.OrderTestModel;
import com.huotu.huobanmall.seller.common.Constant;
import com.huotu.huobanmall.seller.utils.ActivityUtils;
import com.huotu.huobanmall.seller.utils.DialogUtils;
import com.huotu.huobanmall.seller.utils.GsonRequest;
import com.huotu.huobanmall.seller.utils.HttpParaUtils;
import com.huotu.huobanmall.seller.utils.StringUtils;
import com.huotu.huobanmall.seller.utils.ToastUtils;
import com.huotu.huobanmall.seller.utils.VolleyRequestManager;
import com.viewpagerindicator.TabPageIndicator;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 订单管理 界面 2
 */
public class OrderActivity extends BaseFragmentActivity implements View.OnClickListener , TextView.OnEditorActionListener {

    @Bind(R.id.header_bar)
    RelativeLayout _header_bar;
    @Bind(R.id.search_bar)
    RelativeLayout _search_bar;
    @Bind(R.id.search_cancel)
    Button _search_cancel;
    @Bind(R.id.search_text)
    com.huotu.android.library.libedittext.EditText _search_text;
    @Bind(R.id.header_back)
    Button _headerBack;
    @Bind(R.id.order_indicator)
    TabPageIndicator _indicator;
    @Bind(R.id.order_ViewPager)
    ViewPager _ViewPager;
    @Bind(R.id.header_operate)
    TextView _header_operate;
    @Bind(R.id.header_title)
    TextView _header_title;

    OrderPagerAdapter _pagerAdapter;
    Handler _handler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initView();
    }

    protected void initView(){
        ButterKnife.bind(this);
        _headerBack.setOnClickListener(this);
        _header_title.setText("订单管理");
        _pagerAdapter = new OrderPagerAdapter(this);
        _ViewPager.setAdapter(_pagerAdapter);
        _indicator.setViewPager(_ViewPager);

        _header_operate.setOnClickListener(this);
        _search_cancel.setOnClickListener(this);
        _search_text.setOnEditorActionListener(this);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            if (TextUtils.isEmpty( _search_text.getText())) {
                _search_text.requestFocus();
                _search_text.setError("不能为空");
            } else {
                int index = _ViewPager.getCurrentItem();
                OrderActivity.this.showProgressDialog("","正在获取数据，请稍等...");
                _pagerAdapter.search(index);
            }
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    protected void openSearchBar(){
        _search_bar.setVisibility(View.VISIBLE);
        _header_bar.setVisibility(View.GONE);
    }
    protected void closeSearchBar(){
        _search_text.setText("");
        _search_bar.setVisibility(View.GONE);
        _header_bar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_back: {
                finish();
                break;
            }
            case R.id.header_operate: {
                openSearchBar();
                break;
            }
            case R.id.search_cancel:{
                closeSearchBar();
                break;
            }
        }
    }


    public class OrderPagerAdapter extends PagerAdapter{
        private final String[] Titles = new String[] { "全部", "待付款","待收货","已完成"};
        List<PullToRefreshListView> _lv;
        List<List<OrderListModel>> _datas;
        List<List<OrderTestModel>> _viewDatas;//为了适配UI的数据
        List<OrderDataAdapter> _adapters=null;
        List<OperateTypeEnum> _operateTypes = null;
        Map<Integer , Boolean> _tabInit=null;//保存tab是否初始化数据
        Map<Integer , Response.Listener<MJOrderListModel>> _listeners=null;
        Context _context;
        OrderDataAdapter.ILogisticListener _seeLogisticListener = new OrderDataAdapter.ILogisticListener() {
            @Override
            public void onClick(View view, OrderTestModel model) {
                Intent intent = new Intent();
                intent.putExtra(Constant.Extra_OrderNo , model.getChildOrderNO());
                intent.setClass(_context, LogisticsActivity.class);
                ActivityUtils.getInstance().showActivity((Activity)_context, intent);
            }
        };
        OrderDataAdapter.ISeeOrderDetailListener _seeOrderDetailListener = new OrderDataAdapter.ISeeOrderDetailListener() {
            @Override
            public void onSeeOrderDetail(View view, OrderTestModel model) {
                Intent intent=new Intent();
                intent.putExtra(Constant.Extra_OrderNo, model.getChildOrderNO());
                intent.setClass(_context, OrdermanagementDetailsActivity.class);
                ActivityUtils.getInstance().showActivity((Activity) _context, intent);
            }
        };

        public OrderPagerAdapter(Context context ){
            _context=context;
            _lv = new ArrayList<>();
            _datas = new ArrayList<>();
            _viewDatas = new ArrayList<>();
            _adapters =new ArrayList<>();
            _operateTypes = new ArrayList<>();
            _tabInit = new HashMap<>();
            _listeners =new HashMap<>();
            _listeners.put(0,listener_0);
            _listeners.put(1,listener_1);
            _listeners.put(2,listener_2);
            _listeners.put(3,listener_3);
            for( int i =0;i<4;i++) {
                _tabInit.put(i,false);

                PullToRefreshListView lv=new PullToRefreshListView(_context);
                View emptyView = new View(_context);
                emptyView.setBackgroundResource(R.mipmap.tpzw);
                lv.setEmptyView(emptyView);

                lv.setMode(PullToRefreshBase.Mode.BOTH);
                lv.setTag(i);
                lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                    @Override
                    public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                        int tabIndex = (Integer) pullToRefreshBase.getTag();
                        _operateTypes.set(tabIndex, OperateTypeEnum.REFRESH);
                        getData(tabIndex, OperateTypeEnum.REFRESH, _listeners.get(tabIndex));
//                        if (tabIndex == 0) {
//                            _operateTypes.set(0, OperateTypeEnum.REFRESH);
//                            getData_0(OperateTypeEnum.REFRESH);
//                        } else if (tabIndex == 1) {
//                            _operateTypes.set(1, OperateTypeEnum.REFRESH);
//                            getData_1(OperateTypeEnum.REFRESH);
//                        } else if (tabIndex == 2) {
//                            _operateTypes.set(2, OperateTypeEnum.REFRESH);
//                            getData_2(OperateTypeEnum.REFRESH);
//                        } else if (tabIndex == 3) {
//                            _operateTypes.set(3, OperateTypeEnum.REFRESH);
//                            getData_3(OperateTypeEnum.REFRESH);
//                        }
                    }

                    @Override
                    public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                        int tabIndex = (Integer) pullToRefreshBase.getTag();
                        _operateTypes.set(tabIndex, OperateTypeEnum.LOADMORE);
                        getData(tabIndex, OperateTypeEnum.LOADMORE , _listeners.get(tabIndex));
//                        if (tabIndex == 0) {
//                            _operateTypes.set(0, OperateTypeEnum.LOADMORE);
//                            getData_0(OperateTypeEnum.LOADMORE);
//                        } else if (tabIndex == 1) {
//                            _operateTypes.set(1, OperateTypeEnum.LOADMORE);
//                            getData_1(OperateTypeEnum.LOADMORE);
//                        } else if (tabIndex == 2) {
//                            _operateTypes.set(2, OperateTypeEnum.LOADMORE);
//                            getData_2(OperateTypeEnum.LOADMORE);
//                        } else if (tabIndex == 3) {
//                            _operateTypes.set(3, OperateTypeEnum.LOADMORE);
//                            getData_3(OperateTypeEnum.LOADMORE);
//                        }
                    }
                });
                _lv.add(lv);

                List<OrderTestModel> data = new ArrayList<>();
                _viewDatas.add(data);
                OrderDataAdapter adapter = new OrderDataAdapter(_context , data);
                _adapters.add(adapter);
                adapter.setLogisticsListener(_seeLogisticListener);
                adapter.set_seeOrderDetailListener(_seeOrderDetailListener);
                lv.setAdapter(adapter);

                List<OrderListModel> orders= new ArrayList<>();
                _datas.add(orders);
                _operateTypes.add( OperateTypeEnum.REFRESH);
            }
        }

        protected void getData( int tabIndex ,
                                String lastDate ,
                                String keyword,
                                OperateTypeEnum operateType ,
                                Response.Listener<MJOrderListModel> listener ){
            if( false == canConnect() ){
                _handler.post(new Runnable() {
                    @Override
                    public void run() {
                        _lv.get(0).onRefreshComplete();
                        _lv.get(1).onRefreshComplete();
                        _lv.get(2).onRefreshComplete();
                        _lv.get(3).onRefreshComplete();
                        OrderActivity.this.closeProgressDialog();
                    }
                });
                return;
            }

            String url = Constant.ORDERLIST_INTERFACE;
            Map<String,String> paras = new HashMap<>();
            paras.put("status", String.valueOf( tabIndex));
            if( operateType == OperateTypeEnum.REFRESH ){
            }else {
                paras.put("lastDate", lastDate);
            }

            if( keyword !=null && keyword.length()>0){
                paras.put("keyword", keyword);
            }

            HttpParaUtils httpParaUtils =new HttpParaUtils();
            url = httpParaUtils.getHttpGetUrl( url , paras);

            GsonRequest<MJOrderListModel> request=new GsonRequest<MJOrderListModel>(
                    Request.Method.GET,
                    url,
                    MJOrderListModel.class,
                    null,
                    listener,
                    errorListener
            );

            VolleyRequestManager.AddRequest(request);
        }

        Response.Listener<MJOrderListModel> listener_0 =new Response.Listener<MJOrderListModel>() {
            @Override
            public void onResponse(MJOrderListModel mjOrderListModel) {
                _handler.post(new Runnable() {
                    @Override
                    public void run() {
                        _lv.get(0).onRefreshComplete();
                        OrderActivity.this.closeProgressDialog();
                    }
                });
                setData( 0 , _operateTypes.get(0) , mjOrderListModel );
            }
        };

        protected void setData( int index , OperateTypeEnum operateType , MJOrderListModel data ){
            if( data == null ){
                DialogUtils.showDialog( OrderActivity.this , OrderActivity.this.getSupportFragmentManager() , "错误信息","请求数据失败","关闭" );
                return;
            }
            if( data.getSystemResultCode()!=1){
                DialogUtils.showDialog(OrderActivity.this,OrderActivity.this.getSupportFragmentManager(),"错误信息", data.getSystemResultDescription(),"关闭");
                return;
            }
            if( data.getResultCode() == Constant.TOKEN_OVERDUE){
                ActivityUtils.getInstance().skipActivity(OrderActivity.this, LoginActivity.class);
                return;
            }
            if( data.getResultCode() !=1){
                DialogUtils.showDialog(OrderActivity.this, OrderActivity.this.getSupportFragmentManager(), "错误信息", data.getResultDescription(), "关闭");
                return;
            }

            if( data.getResultData() == null ){
                DialogUtils.showDialog(OrderActivity.this,OrderActivity.this.getSupportFragmentManager(),"错误信息","返回的数据不完整","关闭");
                return;
            }

            if( operateType == OperateTypeEnum.REFRESH ) {
                _viewDatas.get(index).clear();

                if( data.getResultData().getList() !=null && data.getResultData().getList().size()>0) {
                    _datas.get(index).addAll(data.getResultData().getList());
                    List<OrderTestModel> viewData = changeData(data.getResultData().getList());
                    _viewDatas.get(index).addAll(viewData);
                }else{
                    ToastUtils.showLong(Constant.No_Data_Text,Gravity.BOTTOM);
                }

                _adapters.get(index).notifyDataSetChanged();
            }else {
                if( data.getResultData().getList() !=null && data.getResultData().getList().size()>0) {
                    _datas.get(index).addAll(data.getResultData().getList());
                    List<OrderTestModel> viewData = changeData(data.getResultData().getList());
                    _viewDatas.get(index).addAll(viewData);
                }else{
                    ToastUtils.showLong(Constant.No_Data_Text , Gravity.BOTTOM );
                }

                _adapters.get(index).notifyDataSetChanged();
            }
        }

        Response.Listener<MJOrderListModel> listener_1 =new Response.Listener<MJOrderListModel>() {
            @Override
            public void onResponse(MJOrderListModel mjOrderListModel) {
                _handler.post(new Runnable() {
                    @Override
                    public void run() {
                        _lv.get(1).onRefreshComplete();
                        OrderActivity.this.closeProgressDialog();
                    }
                });

                setData(1, _operateTypes.get(1), mjOrderListModel);
            }
        };

        Response.Listener<MJOrderListModel> listener_2 =new Response.Listener<MJOrderListModel>() {
            @Override
            public void onResponse(MJOrderListModel mjOrderListModel) {
                _handler.post(new Runnable() {
                    @Override
                    public void run() {
                        _lv.get(2).onRefreshComplete();
                        OrderActivity.this.closeProgressDialog();
                    }
                });

                setData(2, _operateTypes.get(2) , mjOrderListModel );
            }
        };

        Response.Listener<MJOrderListModel> listener_3 =new Response.Listener<MJOrderListModel>() {
            @Override
            public void onResponse(MJOrderListModel mjOrderListModel) {
                _handler.post(new Runnable() {
                    @Override
                    public void run() {
                        _lv.get(3).onRefreshComplete();
                        OrderActivity.this.closeProgressDialog();
                    }
                });

                setData(3, _operateTypes.get(3), mjOrderListModel);
            }
        };

        public void search( int index ){
            _operateTypes.set(index, OperateTypeEnum.REFRESH);
            getData(index,OperateTypeEnum.REFRESH,_listeners.get(index));
//            if( index ==0 ) {
//                getData_0(OperateTypeEnum.REFRESH);
//            }else if(index==1){
//                getData_1(OperateTypeEnum.REFRESH);
//            }else if(index==2){
//                getData_2(OperateTypeEnum.REFRESH);
//            }else if(index ==3){
//                getData_3(OperateTypeEnum.REFRESH);
//            }
        }

        protected void getData( int index ,  OperateTypeEnum operateType, Response.Listener<MJOrderListModel> listener){
            String lastDate="";
            if( operateType == OperateTypeEnum.LOADMORE && _datas.get(index ) !=null && _datas.get(index).size()>0 ){
                lastDate = String.valueOf( _datas.get(index).get( _datas.get(index).size()-1 ).getTime().getTime());
            }
            String keyword = _search_text.getText().toString();
            getData(index , lastDate, keyword, operateType, listener );
        }

//        protected void getData_0( OperateTypeEnum operateType  ){
//            String lastDate="";
//            if( operateType == OperateTypeEnum.LOADMORE && _datas.get(0) !=null && _datas.get(0).size()>0 ){
//                lastDate = String.valueOf( _datas.get(0).get( _datas.get(0).size()-1 ).getTime().getTime());
//            }
//
//            String keyword = _search_text.getText().toString();
//            getData(0, lastDate, keyword, operateType, listener_0 );
//        }
//        protected void getData_1(OperateTypeEnum operateType){
//            String lastDate="";
//            if( operateType == OperateTypeEnum.LOADMORE && _datas.get(1) !=null && _datas.get(1).size()>0 ){
//                lastDate = String.valueOf( _datas.get(1).get( _datas.get(1).size()-1 ).getTime().getTime());
//            }
//
//            String keyword = _search_text.getText().toString();
//            getData( 1 , lastDate ,keyword , operateType , listener_1 );
//        }
//        protected void getData_2(OperateTypeEnum operateType){
//            String lastDate="";
//            if( operateType == OperateTypeEnum.LOADMORE && _datas.get(2) !=null && _datas.get(2).size()>0 ){
//                lastDate = String.valueOf( _datas.get(2).get( _datas.get(2).size()-1 ).getTime().getTime());
//            }
//
//            String keyword = _search_text.getText().toString();
//            getData( 2 ,lastDate,keyword , operateType , listener_2 );
//        }
//        protected void getData_3(OperateTypeEnum operateType){
//            String lastDate="";
//            if( operateType == OperateTypeEnum.LOADMORE && _datas.get(3) !=null && _datas.get(3).size()>0 ){
//                lastDate = String.valueOf( _datas.get(3).get( _datas.get(3).size()-1 ).getTime().getTime());
//            }
//
//            String keyword = _search_text.getText().toString();
//            getData( 3 ,lastDate,keyword , operateType, listener_3 );
//        }

        Response.ErrorListener errorListener=new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                OrderActivity.this.closeProgressDialog();
                _lv.get(0).onRefreshComplete();
                _lv.get(1).onRefreshComplete();
                _lv.get(2).onRefreshComplete();
                _lv.get(3).onRefreshComplete();

                String message="";
                if( null != volleyError.networkResponse){
                    message=new String( volleyError.networkResponse.data);
                }else{
                    message = volleyError.getMessage();
                }
                if( message.length()<1){
                    message = "网络请求失败，请检查网络状态";
                }
                DialogUtils.showDialog(OrderActivity.this, OrderActivity.this.getSupportFragmentManager(), "错误信息", message, "关闭");
            }
        };

        protected List<OrderTestModel> changeData( List<OrderListModel> orders){
            List<OrderTestModel> list=new ArrayList<>();

            for( int k = 0; k< orders.size() ; k++) {
                OrderListModel order= orders.get(k);

                OrderTestModel item = new OrderTestModel();
                item.setViewType(1);
                item.setMainOrderNO(order.getMainOrderNo());
                item.setChildOrderNO(order.getOrderNo());
                item.setStatus(String.valueOf(order.getStatus()));
                list.add(item);

                if (order.getList() != null) {
                    for (int i = 0; i < order.getList().size(); i++) {
                        item = new OrderTestModel();
                        item.setViewType(2);
                        item.setChildOrderNO(order.getOrderNo());
                        item.setMainOrderNO(order.getMainOrderNo());
                        item.setCount(order.getList().get(i).getAmount());
                        item.setPrice(order.getList().get(i).getMoney());
                        item.setGoodsName(order.getList().get(i).getTitle());
                        item.setPictureUrl(order.getList().get(i).getPictureUrl());
                        item.setSpec(order.getList().get(i).getSpec());
                        list.add(item);
                    }
                }

                item = new OrderTestModel();
                item.setViewType(3);
                item.setMainOrderNO(order.getMainOrderNo());
                item.setChildOrderNO(order.getOrderNo());
                item.setCount(order.getAmount());
                item.setTotalPrice(order.getPaid());
                list.add(item);

                item = new OrderTestModel();
                item.setViewType(0);
                item.setMainOrderNO(order.getMainOrderNo());
                item.setTime(order.getTime());
                list.add(item);
            }

            return list;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //return super.instantiateItem(container, position);
            //ToastUtils.showLong(String.valueOf( position )+ String.valueOf( System.currentTimeMillis()) );

//            if( position == 0 ) {
//                _operateTypes.set(0,OperateTypeEnum.REFRESH);
//                getData_0(OperateTypeEnum.REFRESH);
//            }else if(position==1){
//                _operateTypes.set(1,OperateTypeEnum.REFRESH);
//                getData_1(OperateTypeEnum.REFRESH);
//            }else if(position ==2 ){
//                _operateTypes.set(2,OperateTypeEnum.REFRESH);
//                getData_2(OperateTypeEnum.REFRESH);
//            }else if(position==3){
//                _operateTypes.set(3, OperateTypeEnum.REFRESH);
//                getData_3(OperateTypeEnum.REFRESH);
//            }

            container.addView(_lv.get(position));
            return _lv.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
            container.removeView(_lv.get(position));
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return Titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return Titles[ position%Titles.length];
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            if( _tabInit.get(position) == false ) {
                _tabInit.put(position,true);
                _operateTypes.set(position, OperateTypeEnum.REFRESH);
                OrderActivity.this.showProgressDialog("", "正在获取数据，请稍等...");
                getData(position,OperateTypeEnum.REFRESH, _listeners.get(position));
//                if (position == 0) {
//                    OrderActivity.this.showProgressDialog("","正在获取数据，请稍等...");
//                    _operateTypes.set(0, OperateTypeEnum.REFRESH);
//                    getData_0(OperateTypeEnum.REFRESH);
//                } else if (position == 1) {
//                    OrderActivity.this.showProgressDialog("","正在获取数据，请稍等...");
//                    _operateTypes.set(1, OperateTypeEnum.REFRESH);
//                    getData_1(OperateTypeEnum.REFRESH);
//                } else if (position == 2) {
//                    OrderActivity.this.showProgressDialog("","正在获取数据，请稍等...");
//                    _operateTypes.set(2, OperateTypeEnum.REFRESH);
//                    getData_2(OperateTypeEnum.REFRESH);
//                } else if (position == 3) {
//                    OrderActivity.this.showProgressDialog("","正在获取数据，请稍等...");
//                    _operateTypes.set(3, OperateTypeEnum.REFRESH);
//                    getData_3(OperateTypeEnum.REFRESH);
//                }
            }
        }
    }
}
