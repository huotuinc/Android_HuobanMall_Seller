package com.huotu.huobanmall.seller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.activity.BillActivity;
import com.huotu.huobanmall.seller.bean.GoodsModel;
import com.huotu.huobanmall.seller.bean.OrderListModel;
import com.huotu.huobanmall.seller.bean.OrderListProductModel;
import com.huotu.huobanmall.seller.bean.OrderStatusEnum;
import com.huotu.huobanmall.seller.utils.BitmapLoader;
import com.huotu.huobanmall.seller.utils.ToastUtils;
import com.huotu.huobanmall.seller.widget.MJExpandableListView;
import com.huotu.huobanmall.seller.widget.MJListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/15.
 */
public class BillDataAdapter extends RecyclerView.Adapter<BillDataAdapter.OrderListViewHolder> {
    private Context _context;
    private List<OrderListModel> _list;
    private LayoutInflater _inflater;
    private ILogisticListener _seeLogisticsListener = null;
    private ISeeOrderDetailListener _seeOrderDetailListener=null;
    private OrderGoodsAdapter _goodsAdapter=null;
    private List<GoodsModel> _goodsList=null;
    private ChildOrderExpandableAdapter _childOrderAdapter=null;

    public void setLogisticsListener(ILogisticListener listener ){
        _seeLogisticsListener = listener;
    }

    public void set_seeOrderDetailListener(ISeeOrderDetailListener listener){
        _seeOrderDetailListener = listener;
    }

    protected void onLogisticListener( View view , OrderListModel model ){
        if( _seeLogisticsListener!=null){
            _seeLogisticsListener.onClick(view , model);
        }
    }

    protected void onSeeOrderDetailListener(View view , OrderListModel model){
        if(_seeOrderDetailListener!=null){
            _seeOrderDetailListener.onSeeOrderDetail(view, model);
        }
    }


    public BillDataAdapter( Context context , List<OrderListModel> list  ){
        _context= context;
        _list=list;
        _inflater = LayoutInflater.from(context);
        _goodsList = new ArrayList<>();
        _goodsAdapter = new OrderGoodsAdapter(_context, _goodsList);
    }

    @Override
    public OrderListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = _inflater.inflate(R.layout.layout_bill_item, null);
        OrderListViewHolder holder=new OrderListViewHolder( view );

        return holder;
    }

    @Override
    public void onBindViewHolder(OrderListViewHolder holder, int position) {
        OrderListModel model = _list.get(position);
        //OrderGoodsAdapter goodsAdapter = new OrderGoodsAdapter( _context , model.getGoods() );
        //_goodsList.clear();
        //_goodsList.addAll( model.getGoods());
        //_goodsAdapter.notifyDataSetChanged();
        //holder.lv.setAdapter( _goodsAdapter );
        //setListViewHeightBasedOnChildren( holder.lv );

        holder.tvOrderNo.setText(model.getOrderNo());
        holder.tvStatus.setText(OrderStatusEnum.getName(model.getStatus()) );
        holder.position = position;

        if( false == model.getHasChildOrder() ){
            holder.lv.setVisibility(View.VISIBLE);
            holder.lvChildOrder.setVisibility(View.GONE);
            _goodsList.clear();
            _goodsList.addAll(model.getList());
            _goodsAdapter.notifyDataSetChanged();
            //holder.lv.setAdapter(_goodsAdapter);
        }else {
            holder.lv.setVisibility(View.GONE);
            holder.lvChildOrder.setVisibility(View.VISIBLE);

            List<OrderListModel> childOrders = model.getChildOrders();
            _childOrderAdapter = new ChildOrderExpandableAdapter(_context, childOrders );
            holder.lvChildOrder.setAdapter( _childOrderAdapter);
        }

        //addGoods( holder.llGoods , model.getGoods() );
    }

    @Override
    public int getItemViewType(int position) {
        //return super.getItemViewType(position);
        return _goodsList.get(position).getViewType();
    }

    protected void addGoods( LinearLayout ll , List<GoodsModel> list){
        ll.removeAllViews();
        for( int i=0;i<list.size();i++){
            View view = View.inflate(_context, R.layout.layout_bill_goods_item,null);
            TextView tvTitle = (TextView)view.findViewById(R.id.bill_item_goodsName);
            TextView tvPrice =  (TextView)view.findViewById(R.id.bill_item_price);
            TextView tvCount =  (TextView)view.findViewById(R.id.bill_item_count);
            TextView tvSpec =  (TextView)view.findViewById(R.id.bill_item_ColorSize);
            NetworkImageView iv = (NetworkImageView)view.findViewById(R.id.bill_item_picture);
            tvTitle.setText( list.get(i).getTitle());
            tvCount.setText( String.valueOf(list.get(i).getStock()));
            tvPrice.setText( String.valueOf(list.get(i).getPrice()) );
            //tvSpec.setText( list.get(i).get() );
            BitmapLoader.create().displayUrl(_context, iv, list.get(i).getPictureUrl());
            ll.addView(view);
        }
    }

    @Override
    public long getItemId(int position) {
        return  position;
    }

    @Override
    public int getItemCount() {
        return _list==null? 0 : _list.size();
    }

    public class OrderListViewHolder extends RecyclerView.ViewHolder{
        private TextView tvOrderNo;
        private TextView tvStatus;
        private MJListView lv;
        private ExpandableListView lvChildOrder;
        private Button btnSeeLogistics;
        private int position;
        private LinearLayout llGoods;

        public OrderListViewHolder(View view ){
            super(view );

            llGoods = (LinearLayout)view.findViewById(R.id.bill_item_ll4);
//            llGoods.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onSeeOrderDetailListener(v , _list.get(position));
//                }
//            });
            tvOrderNo = (TextView)view.findViewById(R.id.bill_item_orderNo);
            tvStatus = (TextView) view.findViewById(R.id.bill_item_status);

            lv = (MJListView) view.findViewById(R.id.bill_item_goodsList);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    onSeeOrderDetailListener(view , _list.get(position));
                }
            });
            lv.setAdapter(_goodsAdapter);
            btnSeeLogistics =(Button)view.findViewById(R.id.bill_item_logistics);
            btnSeeLogistics.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onLogisticListener( v , _list.get(position));
                }
            });

            //
            lvChildOrder = (ExpandableListView) view.findViewById(R.id.bill_item_childOrder);

        }
    }

    public interface ILogisticListener {
        void onClick(View view , OrderListModel model );
    }
    public interface ISeeOrderDetailListener{
        void onSeeOrderDetail(View view, OrderListModel model);
    }
}
