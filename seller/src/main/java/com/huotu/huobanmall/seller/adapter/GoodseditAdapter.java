package com.huotu.huobanmall.seller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.bean.GoodsModel;
import com.huotu.huobanmall.seller.utils.BitmapLoader;

import java.util.List;

/**
 * Created by Administrator on 2015/9/2.
 */
public class GoodseditAdapter extends BaseAdapter {
    private List<GoodsModel> _list;
    private Context _context;
    public GoodseditAdapter(Context context, List<GoodsModel> list){
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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null)
        {
            holder = new ViewHolder();
            convertView = View.inflate(_context,
                    R.layout.layout_goodsedit_item, null);
            holder.tvSelect =(TextView) convertView
                    .findViewById(R.id.goods_select);
            holder.goods_imageView = (NetworkImageView) convertView
                    .findViewById(R.id.goods_imageView);
            holder.goods_name = (TextView) convertView
                    .findViewById(R.id.goods_name);
            holder.goods_num = (TextView) convertView.findViewById(R.id.goods_num);
            holder.goods_price = (TextView) convertView.findViewById(R.id.goods_price);
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvSelect.setVisibility(View.VISIBLE);
        holder.goods_name.setText(String.valueOf(_list.get(position).getTitle()));
        holder.goods_num.setText(String.valueOf(_list.get(position)
                .getStock()));
        holder.goods_price.setText(String.valueOf(_list.get(position).getPrice()));
        BitmapLoader.create().displayUrl( _context , holder.goods_imageView , _list.get(position).getPictureUrl(),R.mipmap.goods,R.mipmap.goods);

        if( _list.get(position).isSelected()){
            holder.tvSelect.setBackgroundResource(R.mipmap.xz);
        }else {
            holder.tvSelect.setBackgroundResource(R.mipmap.wxz);
        }
        return convertView;
    }
    class ViewHolder

    {
        TextView tvSelect;

        NetworkImageView goods_imageView;

        TextView goods_name;

        TextView goods_price;

        TextView goods_num;
    }
}
