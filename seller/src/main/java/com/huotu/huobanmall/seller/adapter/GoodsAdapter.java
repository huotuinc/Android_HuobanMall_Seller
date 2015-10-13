package com.huotu.huobanmall.seller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.android.volley.toolbox.NetworkImageView;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.bean.GoodsModel;
import com.huotu.huobanmall.seller.utils.BitmapLoader;

import java.util.List;


/**
 * Created by Administrator on 2015/9/2.
 */
public class GoodsAdapter extends BaseAdapter{

    private List<GoodsModel> _list;
    private Context _context;

    public GoodsAdapter(Context context, List<GoodsModel> list){
        _list=list;
        _context=context;
    }



    @Override
    public int getCount() {
        return _list.size();
    }

    @Override
    public Object getItem(int position) {
        return _list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null)
        {
            holder = new ViewHolder();
            convertView = View.inflate(_context,
                    R.layout.layout_goodsedit_item, null);
            holder.goods_imageView = (NetworkImageView) convertView
                    .findViewById(R.id.goods_imageView);
            holder.goods_name = (TextView) convertView
                    .findViewById(R.id.goods_name);
            holder.goods_cplx = (TextView) convertView.findViewById(R.id.goods_cplx);
            holder.goods_num = (TextView) convertView.findViewById(R.id.goods_num);
            holder.goods_price = (TextView) convertView.findViewById(R.id.goods_price);
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.goods_name.setText(String.valueOf(_list.get(position).getTitle()));
        String numString = "";
        if( _list.get(position).getStock() >= 0 && _list.get(position).getStock() < 5 ){
            numString="不充足";
        }else{
            numString = "充足";//String.valueOf( _list.get(position).getStock());
        }
        holder.goods_num.setText( numString );
        holder.goods_price.setText(String.valueOf(_list.get(position).getPrice()));
        holder.goods_cplx.setText( _list.get(position).getCategory() );
        BitmapLoader.create().displayUrl( _context , holder.goods_imageView , _list.get(position).getPictureUrl() ,R.mipmap.goods,R.mipmap.goods);

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    class ViewHolder
    {
        NetworkImageView goods_imageView;
        TextView goods_cplx;

        TextView goods_name;

        TextView goods_price;

        TextView goods_num;
    }
}
