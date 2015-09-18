package com.huotu.huobanmall.seller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.bean.SalesListModel;
import com.huotu.huobanmall.seller.bean.TopConsumeModel;

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
            convertView = View.inflate(_context,
                    R.layout.layout_consumestatistics_item, null);

            holder.money = (TextView) convertView.findViewById(R.id.money);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.money.setText(String.valueOf(_list.get(position).getMoney()));

        return convertView;

    }

    class ViewHolder

    {

        TextView money;

        TextView orderNo;

        TextView time;
    }
}


