package com.huotu.huobanmall.seller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.bean.OrderListProductModel;

import com.huotu.huobanmall.seller.utils.BitmapLoader;


import java.util.List;

/**
 * Created by Administrator on 2015/8/24.
 */
public class LogisticsGoodsAdapter extends BaseAdapter {
    List<OrderListProductModel> _data;
    Context _context;

    public LogisticsGoodsAdapter(Context context, List<OrderListProductModel> data){
        _data=data;
        _context=context;
    }

    @Override
    public int getCount() {
        return _data==null?0:_data.size();
    }

    @Override
    public Object getItem(int position) {
        return _data==null?null:_data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GoodsViewHolder holder;
        if( convertView==null){
            convertView= View.inflate( _context, R.layout.layout_bill_goods_item,null);
            holder= new GoodsViewHolder();
            holder.tvPrice = (TextView)convertView.findViewById(R.id.bill_item_price);
            holder.tvCounts = (TextView)convertView.findViewById(R.id.bill_item_count);
            holder.tvTitle=(TextView)convertView.findViewById(R.id.bill_item_goodsName);
            holder.tvColorSize = (TextView)convertView.findViewById(R.id.bill_item_ColorSize);
            holder.ivPicture = (NetworkImageView) convertView.findViewById(R.id.bill_item_picture);
            convertView.setTag(holder);
        }else{
            holder = (GoodsViewHolder)convertView.getTag();
        }
        OrderListProductModel model=_data.get(position);
        holder.tvColorSize.setText( model.getSpec() );
        holder.tvPrice.setText( "￥" + String.valueOf(model.getMoney()) );
        holder.tvTitle.setText( model.getTitle() );
        holder.tvCounts.setText( "X "+ String.valueOf(model.getAmount()) );

        BitmapLoader.create().displayUrl( _context , holder.ivPicture , model.getPictureUrl() ,R.mipmap.goods , R.mipmap.goods );

        return convertView;
    }

//    @Override
//    public int getItemCount() {
//        return _data==null?0:_data.size();
//    }

//    @Override
//    public void onBindViewHolder(LogisticsGoodsAdapter.GoodsViewHolder viewHolder, int i) {
//        if(_data==null)return;
//        viewHolder.tvTitle.setText( _data.get(i).getTitle());
//        viewHolder.tvCounts.setText(_data.get(i).getAmount());
//        viewHolder.tvColorSize.setText( _data.get(i).getSpec() );
//        viewHolder.tvPrice.setText( "￥" + String.valueOf( _data.get(i).getMoney() ) );
//
//    }

//    @Override
//    public LogisticsGoodsAdapter.GoodsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//        LogisticsGoodsAdapter.GoodsViewHolder viewHolder= null;
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate( R.layout.layout_bill_goods_item ,null);
//        viewHolder = new GoodsViewHolder(view);
//        return viewHolder;
//    }

    class GoodsViewHolder {//extends  RecyclerView.ViewHolder{
        public TextView tvTitle;
        public TextView tvCounts;
        public TextView tvPrice;
        public TextView tvColorSize;
        public NetworkImageView ivPicture;

//        public GoodsViewHolder(View itemView) {
//            super(itemView);
//            tvTitle =  (TextView)itemView.findViewById(R.id.order_purchaseofgoods_name);
//            tvCounts = (TextView)itemView.findViewById(R.id.order_purchaseofgoods_count);
//        }
    }
}
