package com.huotu.huobanmall.seller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.bean.GoodsModel;
import com.huotu.huobanmall.seller.bean.OrderListProductModel;

import java.util.List;

/**
 * Created by Administrator on 2015/9/15.
 */
public class OrderGoodsAdapter extends BaseAdapter{
    private List<OrderListProductModel> _list;
    private Context _context;
    public OrderGoodsAdapter(Context context, List<OrderListProductModel> list){
        _list=list;
        _context=context;
    }
    @Override
    public int getCount() {
        return _list==null? 0: _list.size();
    }

    @Override
    public Object getItem(int position) {
        return _list==null? null : _list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null)
        {
            holder = new ViewHolder();
            convertView = View.inflate(_context, R.layout.layout_bill_goods_item , null);
            holder.goods_imageView = (ImageView) convertView.findViewById(R.id.bill_item_picture);
            holder.goods_name = (TextView) convertView.findViewById(R.id.bill_item_goodsName);
            //holder.goods_color = (TextView) convertView.findViewById(R.id.bill_item_color);
            holder.goods_num = (TextView) convertView.findViewById(R.id.bill_item_count);
            holder.goods_price = (TextView) convertView.findViewById(R.id.bill_item_price);
            holder.goods_colorSize = (TextView) convertView.findViewById(R.id.bill_item_ColorSize);
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.goods_colorSize.setText( "颜色：红色；尺寸：20X30" );// TODO: 2015/9/16
        holder.goods_name.setText(String.valueOf(_list.get(position).getTitle()));
        holder.goods_num.setText("X "+String.valueOf(_list.get(position).getAmount()));
        holder.goods_price.setText("￥"+String.valueOf(_list.get(position).getMoney()));
        //holder.goods_imageView.get(_list.get(position).getPictureUrl());
        holder.goods_imageView.setBackgroundResource(R.mipmap.ic_launcher);
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder
    {
        ImageView goods_imageView;

        TextView goods_name;

        TextView goods_price;

        TextView goods_num;

        TextView goods_colorSize;

        //TextView goods_size;
    }
}
