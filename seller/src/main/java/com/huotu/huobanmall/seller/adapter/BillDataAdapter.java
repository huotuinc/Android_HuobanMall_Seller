package com.huotu.huobanmall.seller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.activity.BillActivity;
import com.huotu.huobanmall.seller.bean.GoodsModel;
import com.huotu.huobanmall.seller.bean.OrderListModel;
import com.huotu.huobanmall.seller.bean.OrderStatusEnum;
import com.huotu.huobanmall.seller.utils.ToastUtils;
import com.huotu.huobanmall.seller.widget.MJListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/15.
 */
public class BillDataAdapter extends RecyclerView.Adapter< BillDataAdapter.OrderListViewHolder> {
    private Context _context;
    private List<OrderListModel> _list;
    private LayoutInflater _inflater;
    private ILogisticListener _seeLogisticsListener = null;
    private ISeeOrderDetailListener _seeOrderDetailListener=null;
    private OrderGoodsAdapter _goodsAdapter=null;
    private List<GoodsModel> _goodsList=null;

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

    public void setListViewHeightBasedOnChildren( ListView lv ) {
        ListAdapter adapter = lv.getAdapter();
        if (adapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, lv);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = lv.getLayoutParams();
        params.height = totalHeight
                + (lv.getDividerHeight() * (adapter.getCount() - 1));
        lv.setLayoutParams(params);
    }


    @Override
    public OrderListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = _inflater.inflate(R.layout.layout_bill_item,null);
        OrderListViewHolder holder=new OrderListViewHolder( view );

        return holder;
    }

    @Override
    public void onBindViewHolder(OrderListViewHolder holder, int position) {
        OrderListModel model = _list.get(position);
        //OrderGoodsAdapter goodsAdapter = new OrderGoodsAdapter( _context , model.getGoods() );
        _goodsList.clear();
        _goodsList.addAll( model.getGoods());
        _goodsAdapter.notifyDataSetChanged();
        //holder.lv.setAdapter( _goodsAdapter );
        //setListViewHeightBasedOnChildren( holder.lv );

        holder.tvOrderNo.setText(model.getOrderNo());
        holder.tvStatus.setText(OrderStatusEnum.getName(model.getStatus()) );
        holder.position = position;
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
        private Button btnSeeLogistics;
        private int position;

        public OrderListViewHolder(View view ){
            super(view );

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
        }
    }

    public interface ILogisticListener {
        void onClick(View view , OrderListModel model );
    }
    public interface ISeeOrderDetailListener{
        void onSeeOrderDetail(View view, OrderListModel model);
    }
}
