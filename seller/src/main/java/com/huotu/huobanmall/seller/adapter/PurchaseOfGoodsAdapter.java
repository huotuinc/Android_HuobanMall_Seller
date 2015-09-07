package com.huotu.huobanmall.seller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.bean.PurchaseOfGoods;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2015/8/24.
 */
public class PurchaseOfGoodsAdapter extends RecyclerView.Adapter< PurchaseOfGoodsAdapter.GoodsViewHolder> {
    List<PurchaseOfGoods> _data;
    Context _context;
    public PurchaseOfGoodsAdapter(Context context , List<PurchaseOfGoods> data){
        _data=data;
        _context=context;
    }

    @Override
    public int getItemCount() {
        return _data==null?0:_data.size();
    }

    @Override
    public void onBindViewHolder(PurchaseOfGoodsAdapter.GoodsViewHolder viewHolder, int i) {
        if(_data==null)return;
        viewHolder.tvGoods.setText( _data.get(i).getName());
        viewHolder.tvCounts.setText(_data.get(i).getCounts());
    }

    @Override
    public PurchaseOfGoodsAdapter.GoodsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        PurchaseOfGoodsAdapter.GoodsViewHolder viewHolder= null;
        View view = LayoutInflater.from(viewGroup.getContext()).inflate( R.layout.layout_purchaseofgoods_item,null);
        viewHolder = new GoodsViewHolder(view);
        return viewHolder;
    }

    class GoodsViewHolder extends RecyclerView.ViewHolder{
        public TextView tvGoods;
        public TextView tvCounts;

        public GoodsViewHolder(View itemView) {
            super(itemView);
            tvGoods=  (TextView)itemView.findViewById(R.id.order_purchaseofgoods_name);
            tvCounts = (TextView)itemView.findViewById(R.id.order_purchaseofgoods_count);
        }
    }
}
