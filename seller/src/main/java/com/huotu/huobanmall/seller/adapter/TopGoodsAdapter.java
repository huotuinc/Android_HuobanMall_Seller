package com.huotu.huobanmall.seller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.bean.TopGoodsModel;
import com.huotu.huobanmall.seller.bean.TopSalesModel;
import com.huotu.huobanmall.seller.utils.BitmapLoader;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Administrator on 2015/9/18.
 */
public class TopGoodsAdapter extends BaseAdapter {
    private List<TopGoodsModel> _list;
    private Context _context;

    public TopGoodsAdapter(Context context, List<TopGoodsModel> list) {
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
                    R.layout.activity_topsales_item , null);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.topgoods_item_price);
            holder.tvTitle =(TextView) convertView.findViewById(R.id.topgoods_item_goodsName);
            holder.tvAmount =(TextView) convertView.findViewById(R.id.topgoods_item_amount);
            holder.ivPicture = (NetworkImageView) convertView.findViewById(R.id.topgoods_item_picture);
            holder.tvTop = (TextView)convertView.findViewById(R.id.topgoods_item_top);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvPrice.setText( "￥"+String.valueOf( _list.get(position).getPrice() ) );
        holder.tvTitle.setText(_list.get(position).getTitle());
        holder.tvAmount.setText( String.valueOf( "已售出"+_list.get(position).getAmount() ) +"件");
        BitmapLoader.create().displayUrl(_context, holder.ivPicture, _list.get(position).getPicture());
        holder.tvTop.setText( String.valueOf((position + 1)) );

        if(position==0){
            holder.tvTop.setBackgroundResource(R.mipmap.yellow);
        }else if(position==1){
            holder.tvTop.setBackgroundResource(R.mipmap.orange);
        }else if(position==2){
            holder.tvTop.setBackgroundResource(R.mipmap.red);
        }else{
            holder.tvTop.setBackgroundResource(R.mipmap.deeporange);
        }

        return convertView;
    }
    class ViewHolder

    {
        TextView tvAmount;

        TextView tvPrice;

        TextView tvTitle;

        TextView tvTop;

        NetworkImageView ivPicture;
    }
}

