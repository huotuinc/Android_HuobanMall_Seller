package com.huotu.huobanmall.seller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.bean.OrderTestModel;
import com.huotu.huobanmall.seller.utils.BitmapLoader;

import java.util.List;

/**
 * Created by Administrator on 2015/9/21.
 */
public class OrderDataAdapter extends RecyclerView.Adapter<OrderDataAdapter.ViewHolder> {
    private Context _context;
    private List<OrderTestModel> _data;
    private LayoutInflater _inflater =null;
    private ILogisticListener _seeLogisticsListener = null;
    private ISeeOrderDetailListener _seeOrderDetailListener=null;

    public OrderDataAdapter ( Context context , List<OrderTestModel> data ) {
        super();
        _context= context;
        _data =data;
        _inflater = LayoutInflater.from(_context);
    }


    public void setLogisticsListener(ILogisticListener listener ){
        _seeLogisticsListener = listener;
    }

    public void set_seeOrderDetailListener(ISeeOrderDetailListener listener){
        _seeOrderDetailListener = listener;
    }

    protected void onSeeOrderDetailListener(View view , OrderTestModel model){
        if(_seeOrderDetailListener!=null){
            _seeOrderDetailListener.onSeeOrderDetail(view, model);
        }
    }
    protected void onLogisticListener( View view , OrderTestModel model ){
        if( _seeLogisticsListener!=null){
            _seeLogisticsListener.onClick(view, model);
        }
    }

    @Override
    public int getItemViewType(int position) {
        //return super.getItemViewType(position);
        return _data.get(position).getViewType();
    }

    @Override
    public long getItemId(int position) {
        //return super.getItemId(position);
        return position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if( viewType==0 ){
            View view = _inflater.inflate(R.layout.layout_order_main_item, null);
            ViewHolder holder=new ViewHolder( view , viewType );
            return holder;
        }else if( viewType==1){
            View view = _inflater.inflate(R.layout.layout_order_child_item,null);
            ViewHolder holder= new ViewHolder(view , viewType );
            return holder;
        }else if( viewType==2){
            View view = _inflater.inflate(R.layout.layout_order_goods_item,null);
            ViewHolder holder= new ViewHolder(view , viewType );
            return holder;
        }else if( viewType==3){
            View view = _inflater.inflate(R.layout.layout_order_logistic_item,null);
            ViewHolder holder =new ViewHolder(view , viewType );
            return holder;
        }else if( viewType==4){

        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OrderTestModel model=_data.get(position);
        int viewType= model.getViewType();
        if( viewType== 0){
            holder.tvOrdernNo.setText( model.getMainOrderNO() );
            holder.tvTotalPrice.setText( String.valueOf(model.getTotalPrice()));
        }else if ( viewType==1){
            holder.tvOrdernNo.setText( model.getChildOrderNO() );
            holder.tvStatus.setText( model.getStatus() );
        }else if( viewType==2){
            holder.tvGoodsName.setText( model.getGoodsName() );
            holder.tvPrice.setText( model.getPrice());
            holder.tvCount.setText(model.getCount());
            holder.tvSpec.setText( model.getSpec());
            BitmapLoader.create().displayUrl(_context,holder.ivPicture, model.getPictureUrl());
        }else if( viewType==3){
            //holder.btnLogistics
        }
    }

    @Override
    public int getItemCount() {
        return _data==null?0:_data.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {

        public TextView tvOrdernNo;
        public TextView tvGoodsName;
        public TextView tvCount;
        public TextView tvSpec;
        public TextView tvStatus;
        public TextView tvTotalPrice;
        public TextView tvScore;
        public TextView tvPrice;
        public NetworkImageView ivPicture;
        public Button btnLogistics;
        public RelativeLayout rlGoods;
        public int position;

        public ViewHolder(View itemView , int viewType ) {
            super(itemView);

            if( viewType== 0){
                tvOrdernNo = (TextView)itemView.findViewById(R.id.test_order_item_orderNo);
                tvTotalPrice = (TextView)itemView.findViewById(R.id.test_order_item_totalPrice);
            }else if( viewType==1){
                tvOrdernNo = (TextView)itemView.findViewById(R.id.test_order_child_item_childno);
                tvStatus = (TextView)itemView.findViewById(R.id.test_order_child_item_status);
            }else if(viewType==2){
                tvGoodsName = (TextView)itemView.findViewById(R.id.test_order_goods_item_goodsName);
                tvPrice = (TextView)itemView.findViewById(R.id.test_order_goods_item_price);
                tvCount = (TextView)itemView.findViewById(R.id.test_order_goods_item_count);
                tvSpec = (TextView)itemView.findViewById(R.id.test_order_goods_item_spec);
                ivPicture =(NetworkImageView)itemView.findViewById(R.id.test_order_goods_item_picture);
                rlGoods=(RelativeLayout)itemView.findViewById(R.id.test_order_child_item_rl_goods);
                rlGoods.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onSeeOrderDetailListener(v, _data.get(position));
                    }
                });
            }else if( viewType==3){
                btnLogistics=(Button)itemView.findViewById(R.id.test_order_logistic_item_btn);
                btnLogistics.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onLogisticListener(v, _data.get(position));
                    }
                });
            }
        }
    }

    public interface ILogisticListener {
        void onClick(View view , OrderTestModel model );
    }
    public interface ISeeOrderDetailListener{
        void onSeeOrderDetail(View view, OrderTestModel model);
    }
}
