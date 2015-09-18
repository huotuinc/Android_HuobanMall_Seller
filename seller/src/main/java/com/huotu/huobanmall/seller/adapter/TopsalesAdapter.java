package com.huotu.huobanmall.seller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.bean.TopSalesModel;

import java.util.List;

/**
 * Created by Administrator on 2015/9/18.
 */
public class TopsalesAdapter extends BaseAdapter {
    private List<TopSalesModel> _list;
    private Context _context;

    public TopsalesAdapter(Context context, List<TopSalesModel> list) {
        _list = list;
        _context = context;
    }

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
                    R.layout.activity_topsales_item, null);
            holder.amount = (TextView) convertView.findViewById(R.id.topsales_item_amount);
            holder.name=(TextView) convertView.findViewById(R.id.topsales_item_goodsName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.amount.setText(_list.get(position).getAmount()+"人已购买");
        holder.name.setText(_list.get(position).getName());
        return convertView;
    }
    class ViewHolder

    {

        TextView amount;

        TextView name;

    }
}

