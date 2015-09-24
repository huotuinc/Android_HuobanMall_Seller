package com.huotu.huobanmall.seller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.bean.TopConsumeModel;
import com.huotu.huobanmall.seller.utils.BitmapLoader;

import java.util.List;

/**
 * Created by Administrator on 2015/9/15.
 */
public class ConsumeStatisticsAdapter extends BaseAdapter {

    private List<TopConsumeModel> _list;
    private Context _context;

    public ConsumeStatisticsAdapter(Context context, List<TopConsumeModel> list) {
        _list = list;
        _context = context;
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
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(_context, R.layout.layout_consumestatistics_item, null);

            holder.tvMoney = (TextView) convertView.findViewById(R.id.consumestatistics_money);
            holder.tvName = (TextView) convertView.findViewById(R.id.consumestatistics_name);
            holder.tvLabel1 = (TextView)convertView.findViewById(R.id.consumestatistics_label1);
            holder.ivPicture =(NetworkImageView) convertView.findViewById(R.id.consumestatistics_imageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvMoney.setText(String.valueOf(_list.get(position).getMoney()));
        holder.tvLabel1.setText("");
        holder.tvName.setText(_list.get(position).getName());
        BitmapLoader.create().displayUrl(_context, holder.ivPicture, _list.get(position).getPictureUrl(), R.mipmap.zchyzrs , R.mipmap.zchyzrs);

        return convertView;
    }

    class ViewHolder
    {
        TextView tvLabel1;

        TextView tvMoney;


        TextView tvName;


        NetworkImageView ivPicture;
    }
}


