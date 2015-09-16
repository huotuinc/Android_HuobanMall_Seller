package com.huotu.huobanmall.seller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.bean.SalesListModel;

import java.util.List;

/**
 * Created by Administrator on 2015/9/15.
 */
public class RebateStatisticsAdapter extends BaseAdapter {

    private List<SalesListModel> _list;
    private Context _context;

    public RebateStatisticsAdapter(Context context, List<SalesListModel> list) {
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
                    R.layout.layout_rebatestatistics_item, null);
            holder.moblie = (TextView) convertView.findViewById(R.id.moblie);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.moblie.setText(String.valueOf(_list.get(position).getMoblie()));
        return convertView;

    }

    class ViewHolder

    {

        TextView moblie;

        TextView orderNo;

        TextView time;
    }
}
