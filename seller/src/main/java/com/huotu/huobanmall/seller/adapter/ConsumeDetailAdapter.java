package com.huotu.huobanmall.seller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.bean.ConsumeListModel;
import com.huotu.huobanmall.seller.utils.BitmapLoader;
import com.huotu.huobanmall.seller.utils.SystemTools;

import java.util.List;

/**
 * Created by Administrator on 2015/9/15.
 */
public class ConsumeDetailAdapter extends BaseAdapter {

    private List<ConsumeListModel> _list;
    private Context _context;

    public ConsumeDetailAdapter(Context context, List<ConsumeListModel> list) {
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
            convertView = View.inflate(_context,
                    R.layout.layout_consumestatistics_item, null);

            holder.tvMoney = (TextView) convertView.findViewById(R.id.consumestatistics_money);
            holder.tvName = (TextView) convertView.findViewById(R.id.consumestatistics_name);
            holder.tvAmount = (TextView) convertView.findViewById(R.id.consumestatistics_amount);
            holder.ivPicture = (NetworkImageView) convertView.findViewById(R.id.consumestatistics_imageView);
            holder.tvTime =(TextView) convertView.findViewById(R.id.consumestatistics_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvName.setText(String.valueOf(_list.get(position).getName()));
        String dateStr = SystemTools.getDateTime( _list.get(position).getTime() , "yyyy-MM-dd HH:mm:ss" );
        holder.tvTime.setText( dateStr);
        holder.tvMoney.setText( String.valueOf( _list.get(position).getMoney() ) );

        Integer amount = _list.get(position).getAmount();
        String amountStr = amount==null ? "0" : amount.toString();

        holder.tvAmount.setText( amountStr +"单" );

        BitmapLoader.create().displayUrl(_context, holder.ivPicture,_list.get(position).getPictureUrl());

        return convertView;
    }

    class ViewHolder
    {
        TextView tvMoney;

        TextView tvAmount;

        TextView tvName;

        TextView tvTime;

        NetworkImageView ivPicture;
    }
}


