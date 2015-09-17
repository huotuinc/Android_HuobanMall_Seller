package com.huotu.huobanmall.seller.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.adapter.BillDataAdapter;
import com.huotu.huobanmall.seller.bean.GoodsModel;
import com.huotu.huobanmall.seller.bean.OrderListModel;
import com.huotu.huobanmall.seller.utils.ActivityUtils;
import com.huotu.huobanmall.seller.utils.ToastUtils;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 订单管理 界面
 */
public class BillActivity extends BaseFragmentActivity implements View.OnClickListener {
    @Bind(R.id.header_back)
    Button _headerBack;
    @Bind(R.id.bill_indicator)
    TabPageIndicator _indicator;
    @Bind(R.id.bill_ViewPager)
    ViewPager _bill_ViewPager;
    @Bind(R.id.header_operate)
    Button header_operate;

    BillAdapter _adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        initView();
    }

    protected void initView(){
        ButterKnife.bind(this);
        _headerBack.setOnClickListener(this);

        _adapter = new BillAdapter(this);

        _bill_ViewPager.setAdapter( _adapter );

        _indicator.setViewPager( _bill_ViewPager );
        header_operate.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            }


    }

    public class BillAdapter extends PagerAdapter{
        private final String[] Titles = new String[] { "全部", "待付款","待收货","已完成"};
        List<RecyclerView> _recycleLVs;
        List<OrderListModel> _data1;
        List<OrderListModel> _data2;
        List<OrderListModel> _data3;
        List<OrderListModel> _data4;
        BillDataAdapter _adapter1=null;
        BillDataAdapter _adapter2=null;
        BillDataAdapter _adapter3=null;
        BillDataAdapter _adapter4=null;
        Context _context;
        BillDataAdapter.ILogisticListener _seeLogisticListener = new BillDataAdapter.ILogisticListener() {
            @Override
            public void onClick(View view, OrderListModel model) {
                //ToastUtils.showLongToast(_context, model.getOrderNo());
                ActivityUtils.getInstance().showActivity( (Activity)_context, LogisticsActivity.class);
            }
        };

        public BillAdapter(Context context ){
            _context=context;
            _recycleLVs = new ArrayList<>();
            for( int i =0;i<4;i++) {
                RecyclerView lv = new RecyclerView(context);
                lv.setLayoutManager(new LinearLayoutManager(context));
                //设置行间距
                lv.addItemDecoration(new RecyclerView.ItemDecoration() {
                    @Override
                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                        super.getItemOffsets(outRect, view, parent, state);
                        outRect.set(0,0,0,15);
                    }
                });

                _recycleLVs.add(lv);
            }

            _data1 = new ArrayList<>();
            _adapter1 = new BillDataAdapter(_context,_data1);
            _adapter1.setLogisticsListener(_seeLogisticListener);
            _recycleLVs.get(0).setAdapter(_adapter1);
            _data2 = new ArrayList<>();
            _adapter2 = new BillDataAdapter(_context,_data2);
            _adapter2.setLogisticsListener(_seeLogisticListener);
            _recycleLVs.get(1).setAdapter(_adapter2);
            _data3 = new ArrayList<>();
            _adapter3 = new BillDataAdapter(_context, _data3);
            _adapter3.setLogisticsListener(_seeLogisticListener);
            _recycleLVs.get(2).setAdapter(_adapter3);
            _data4 = new ArrayList<>();
            _adapter4 = new BillDataAdapter(_context,_data4);
            _adapter4.setLogisticsListener(_seeLogisticListener);
            _recycleLVs.get(3).setAdapter(_adapter4);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //return super.instantiateItem(container, position);

            if( position == 0 ) {
                //_data1 = new ArrayList<>();
                for( int i=0;i<10;i++){
                    OrderListModel item = new OrderListModel();
                    item.setOrderNo("333"+i);
                    item.setStatus(1);
                    List<GoodsModel> items= new ArrayList<>();
                    for(int k=0;k<5;k++){
                        GoodsModel g=new GoodsModel();
                        g.setGoodsId(k);
                        g.setPrice(234.33f);
                        g.setStock(34);
                        g.setTitle("谷歌董事长发话了：人工智能会很友善");
                        items.add(g);
                    }
                    item.setGoods(items);
                    _data1.add(item);
                }
                //_adapter1 = new BillDataAdapter(_context, _data1);
                //_adapter1.setLogisticsListener(_seeLogisticListener);
                //_recycleLVs.get(0).setAdapter(_adapter1);
                _adapter1.notifyDataSetChanged();
            }else if(position==1){
                //_data2 = new ArrayList<>();
                for( int i=0;i<10;i++){
                    OrderListModel item = new OrderListModel();
                    item.setOrderNo("44444"+i);
                    item.setStatus(1);
                    List<GoodsModel> items= new ArrayList<>();
                    for(int k=0;k<8;k++){
                        GoodsModel g=new GoodsModel();
                        g.setGoodsId(k);
                        g.setPrice(322.64f);
                        g.setStock(111);
                        g.setTitle("如何让熊孩子爱上刷牙？飞利浦新款智能牙刷加入游戏应用");
                        items.add(g);
                    }
                    item.setGoods(items);
                    _data2.add(item);
                }
                //_adapter2 = new BillDataAdapter(_context, _data2);
                //_adapter2.setLogisticsListener(_seeLogisticListener);
                //_recycleLVs.get(1).setAdapter(_adapter2);
                _adapter2.notifyDataSetChanged();
            }else if(position ==2 ){
                //_data3 = new ArrayList<>();
                for( int i=0;i<10;i++){
                    OrderListModel item = new OrderListModel();
                    item.setOrderNo("888"+i);
                    item.setStatus(1);
                    List<GoodsModel> items= new ArrayList<>();
                    for(int k=0;k<8;k++){
                        GoodsModel g=new GoodsModel();
                        g.setGoodsId(k);
                        g.setPrice(666.66f);
                        g.setStock(3434);
                        g.setTitle("名落孙山之后， 微软Edge浏览器发布一大波新功能微软Edge浏览器发布一大波新功能微软Edge浏览器发布一大波新功能");
                        items.add(g);
                    }
                    item.setGoods(items);
                    _data3.add(item);
                }
                //_adapter3 = new BillDataAdapter(_context, _data3);
                //_adapter3.setLogisticsListener(_seeLogisticListener);
                //_recycleLVs.get(2).setAdapter(_adapter3);
                _adapter3.notifyDataSetChanged();
            }else if(position==3){
                //_data4=new ArrayList<>();
                //_data4.clear();

                //_adapter4 = new BillDataAdapter(_context, _data4);
                //_adapter4.setLogisticsListener(_seeLogisticListener);
                //_recycleLVs.get(3).setAdapter(_adapter4);
                OrderListModel item = new OrderListModel();
                item.setOrderNo("888");
                item.setStatus(1);
                List<GoodsModel> items= new ArrayList<>();
                for(int k=0;k<8;k++){
                    GoodsModel g=new GoodsModel();
                    g.setGoodsId(k);
                    g.setPrice(666.66f);
                    g.setStock(3434);
                    g.setTitle("名落孙山之后， 微软Edge浏览器发布一大波新功能微软Edge浏览器发布一大波新功能微软Edge浏览器发布一大波新功能");
                    items.add(g);
                }
                item.setGoods(items);
                _data4.add(item);

                _adapter4.notifyDataSetChanged();
            }

            container.addView(_recycleLVs.get(position));
            return _recycleLVs.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
            container.removeView(_recycleLVs.get(position) );
            //container.removeView();
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
