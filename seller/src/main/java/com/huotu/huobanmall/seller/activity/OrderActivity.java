package com.huotu.huobanmall.seller.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

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
import com.huotu.huobanmall.seller.bean.OrderTestModel;
import com.huotu.huobanmall.seller.common.Constant;
import com.huotu.huobanmall.seller.utils.ActivityUtils;
import com.huotu.huobanmall.seller.utils.DialogUtils;
import com.huotu.huobanmall.seller.utils.GsonRequest;
import com.huotu.huobanmall.seller.utils.HttpParaUtils;
import com.huotu.huobanmall.seller.utils.VolleyRequestManager;
import com.viewpagerindicator.TabPageIndicator;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 订单管理 界面 2
 */
public class OrderActivity extends BaseFragmentActivity implements View.OnClickListener {
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_back: {
                finish();
            }
            break;
            case R.id.header_operate: {
                queryOrder();
                break;
            }
        }
    }

    protected void queryOrder(){

    }

    public class OrderPagerAdapter extends PagerAdapter{
        private final String[] Titles = new String[] { "全部", "待付款","待收货","已完成"};
        List<PullToRefreshListView> _lv;
        List<List<OrderTestModel>> _datas;
        List<OrderDataAdapter> _adapters=null;
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
                intent.putExtra( Constant.Extra_OrderNo , model.getChildOrderNO());
                intent.setClass(_context, OrdermanagementDetailsActivity.class);
                ActivityUtils.getInstance().showActivity((Activity) _context, intent );
            }
        };

        public OrderPagerAdapter(Context context ){
            _context=context;
            _lv = new ArrayList<>();
            _datas = new ArrayList<>();
            _adapters =new ArrayList<>();
            for( int i =0;i<4;i++) {
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
                        if( tabIndex == 0) {
                            getData_0( OperateTypeEnum.REFRESH);
                        }else if( tabIndex ==1){
                            getData_1( OperateTypeEnum.REFRESH);
                        }else if( tabIndex==2){
                            getData_2(OperateTypeEnum.REFRESH);
                        }else if(tabIndex==3){
                            getData_3(OperateTypeEnum.REFRESH);
                        }
                    }

                    @Override
                    public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                        int tabIndex = (Integer) pullToRefreshBase.getTag();
                        if( tabIndex == 0) {
                            getData_0( OperateTypeEnum.LOADMORE);
                        }else if( tabIndex ==1){
                            getData_1( OperateTypeEnum.LOADMORE);
                        }else if( tabIndex==2){
                            getData_2(OperateTypeEnum.LOADMORE);
                        }else if(tabIndex==3){
                            getData_3(OperateTypeEnum.LOADMORE);
                        }
                    }
                });
                _lv.add(lv);

                List<OrderTestModel> data = new ArrayList<>();
                OrderDataAdapter adapter = new OrderDataAdapter(_context , data);
                adapter.setLogisticsListener(_seeLogisticListener);
                adapter.set_seeOrderDetailListener(_seeOrderDetailListener);
                lv.setAdapter(adapter);

                _datas.add(data);
                _adapters.add(adapter);
            }
        }

        protected void getData( int tabIndex ,
                                String lastDate ,
                                String keyword,
                                OperateTypeEnum operateType ,
                                Response.Listener<MJOrderListModel> listener ){
            String url = Constant.ORDERLIST_INTERFACE;
            Map<String,String> paras = new HashMap<>();
            paras.put("status", String.valueOf( tabIndex));
            if( operateType == OperateTypeEnum.REFRESH ){
            }else {
                paras.put("lastDate", lastDate);
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
            VolleyRequestManager.getRequestQueue().add(request);
        }

        Response.Listener<MJOrderListModel> listener_0 =new Response.Listener<MJOrderListModel>() {
            @Override
            public void onResponse(MJOrderListModel mjOrderListModel) {
                _lv.get(0).onRefreshComplete();

            }
        };

        Response.Listener<MJOrderListModel> listener_1 =new Response.Listener<MJOrderListModel>() {
            @Override
            public void onResponse(MJOrderListModel mjOrderListModel) {
                _lv.get(1).onRefreshComplete();

            }
        };

        Response.Listener<MJOrderListModel> listener_2 =new Response.Listener<MJOrderListModel>() {
            @Override
            public void onResponse(MJOrderListModel mjOrderListModel) {
                _lv.get(2).onRefreshComplete();

            }
        };

        Response.Listener<MJOrderListModel> listener_3 =new Response.Listener<MJOrderListModel>() {
            @Override
            public void onResponse(MJOrderListModel mjOrderListModel) {
                _lv.get(3).onRefreshComplete();

            }
        };

        protected void getData_0( OperateTypeEnum operateType){
            String lastDate="";
            getData( 0 , lastDate , "" , operateType , listener_0 );
        }
        protected void getData_1(OperateTypeEnum operateType){
            getData( 1 , "" ,"", operateType , listener_1 );
        }
        protected void getData_2(OperateTypeEnum operateType){
            getData( 2 ,"","", operateType , listener_2 );
        }
        protected void getData_3(OperateTypeEnum operateType){
            getData( 3 ,"","", operateType, listener_3 );
        }

        Response.ErrorListener errorListener=new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //this.closeProgressDialog();



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

        protected void demoAddChild(OrderListModel model){
            model.setHasChildOrder(true);
            model.setChildOrders(new ArrayList<OrderListModel>());
            for( int i=0;i<2;i++){
                OrderListModel child = new OrderListModel();
                child.setOrderNo("AAAAAAA"+i);
                child.setStatus(0);
                child.setGoods( new ArrayList<GoodsModel>());
                for( int k=0;k<4;k++){
                    GoodsModel good = new GoodsModel();
                    good.setGoodsId(i);
                    good.setPrice(22.22F);
                    good.setStock(2);
                    good.setTitle("法规和司法受到犯规地方噶是受到犯规");
                    child.getGoods().add(good);
                }
                model.getChildOrders().add(child);
            }
        }



        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //return super.instantiateItem(container, position);

            if( position == 0 ) {
                getData_0(OperateTypeEnum.REFRESH);

                for( int i=0;i<20;i++){
                    OrderTestModel item = new OrderTestModel();
                    item.setChildOrderNO("3333331111" + i);
                    item.setStatus("1");
                    item.setViewType(1);
                    _datas.get(position).add(item);
                    for( int k=0;k<6;k++) {
                        OrderTestModel g = new OrderTestModel();
                        g.setGoodsName("他人阿的发放阿斯顿飞撒旦法师地方撒旦法是的斯顿飞艾丝凡");
                        g.setCount("32");
                        g.setPrice("423.44");
                        g.setSpec("会碰上艾丝凡啊");
                        g.setPictureUrl("http://images0.cnblogs.com/news_topic/20150202125808578.png");
                        g.setViewType(2);
                        _datas.get(position).add(g);
                    }

                    item =new OrderTestModel();
                    item.setViewType(3);
                    _datas.get(position).add(item);
                    item = new OrderTestModel();
                    item.setMainOrderNO("3223332233" + i);
                    item.setStatus("1");
                    item.setTotalPrice("34343.44");
                    item.setOrderTime("2015-09-22 10:11:44");
                    item.setViewType(0);
                    _datas.get(position).add(item);
                }
                //_adapter1 = new BillDataAdapter(_context, _data1);
                //_adapter1.setLogisticsListener(_seeLogisticListener);
                //_recycleLVs.get(0).setAdapter(_adapter1);
                _adapters.get(position).notifyDataSetChanged();
            }else if(position==1){

                for( int i=0;i<10;i++){
                    OrderTestModel item = new OrderTestModel();
                    item.setChildOrderNO("44444"+i);
                    item.setStatus("1");
                    item.setViewType(1);
                    _datas.get(position).add(item);
                    for(int k=0;k<8;k++){
                        OrderTestModel g=new OrderTestModel();
                        g.setSpec("阿迪沙发阿斯顿飞是的");
                        g.setPrice("322.64");
                        g.setCount("111");
                        g.setGoodsName("如何让熊孩子爱上刷牙？飞利浦新款智能牙刷加入游戏应用");
                        g.setViewType(2);
                        _datas.get(position).add(g);
                    }
                    item =new OrderTestModel();
                    item.setViewType(3);
                    _datas.get(position).add(item);
                    item = new OrderTestModel();
                    item.setMainOrderNO("444444" + i);
                    item.setStatus("1");
                    item.setOrderTime("2015-09-22 10:11:44");
                    item.setTotalPrice("34343.44");
                    item.setViewType(0);
                    _datas.get(position).add(item);

                }

                _adapters.get(position).notifyDataSetChanged();
            }else if(position ==2 ){

//                for( int i=0;i<10;i++){
//                    OrderListModel item = new OrderListModel();
//                    item.setOrderNo("888"+i);
//                    item.setStatus(1);
//                    List<GoodsModel> items= new ArrayList<>();
//                    for(int k=0;k<8;k++){
//                        GoodsModel g=new GoodsModel();
//                        g.setGoodsId(k);
//                        g.setPrice(666.66f);
//                        g.setStock(3434);
//                        g.setTitle("名落孙山之后， 微软Edge浏览器发布一大波新功能微软Edge浏览器发布一大波新功能微软Edge浏览器发布一大波新功能");
//                        items.add(g);
//                    }
//                    item.setGoods(items);
//                    _data3.add(item);
//                }
//                _adapter3.notifyDataSetChanged();
            }else if(position==3){

//                OrderListModel item = new OrderListModel();
//                item.setOrderNo("888");
//                item.setStatus(1);
//                List<GoodsModel> items= new ArrayList<>();
//                for(int k=0;k<8;k++){
//                    GoodsModel g=new GoodsModel();
//                    g.setGoodsId(k);
//                    g.setPrice(666.66f);
//                    g.setStock(3434);
//                    g.setTitle("名落孙山之后， 微软Edge浏览器发布一大波新功能微软Edge浏览器发布一大波新功能微软Edge浏览器发布一大波新功能");
//                    items.add(g);
//                }
//                item.setGoods(items);
//                _data4.add(item);
//
//                _adapter4.notifyDataSetChanged();
            }

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

        //@Override
        //public int getItemPosition(Object object) {
            //return super.getItemPosition(object);
        //}

        @Override
        public int getCount() {
            return Titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return Titles[ position%Titles.length];
        }
    }
}
