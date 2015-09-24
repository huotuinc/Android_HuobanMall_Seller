package com.huotu.huobanmall.seller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.bean.SalesListModel;
import com.huotu.huobanmall.seller.utils.BitmapLoader;
import com.huotu.huobanmall.seller.utils.SystemTools;

import java.util.List;

/**
 * Created by Administrator on 2015/9/14.
 */
public class SalesDetailAdapter extends BaseAdapter {

    private List<SalesListModel> _list;
    private Context _context;

    public SalesDetailAdapter(Context context, List<SalesListModel> list) {
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
                    R.layout.layout_salesdetail_item, null);
            holder.tvOrderNo = (TextView) convertView
                    .findViewById(R.id.salesdetail_orderNo);
            holder.tvTime = (TextView) convertView.findViewById(R.id.salesdetail_salestime);
            holder.tvMoney = (TextView) convertView.findViewById(R.id.salesdetail_money);
            holder.ivPicture =(NetworkImageView) convertView.findViewById(R.id.salesdetail_imageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvMoney.setText(String.valueOf(_list.get(position).getMoney()));
        holder.tvOrderNo.setText(String.valueOf(_list.get(position).getOrderNo()));
        String dateStr="";
        dateStr = SystemTools.getDateTime( _list.get(position).getTime() ,"yyyy-MM-dd HH:mm:ss" );
        holder.tvTime.setText( dateStr );

        BitmapLoader.create().displayUrl(_context, holder.ivPicture , _list.get(position).getPictureUrl(),R.mipmap.ddzs,R.mipmap.ddzs);

        return convertView;

    }

    class ViewHolder
    {
        TextView tvMoney;

        TextView tvOrderNo;

        TextView tvTime;

        NetworkImageView ivPicture;

    }
}


