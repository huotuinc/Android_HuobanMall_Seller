package com.huotu.huobanmall.seller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.bean.TopScoreModel;
import com.huotu.huobanmall.seller.bean.UserScoreModel;
import com.huotu.huobanmall.seller.utils.BitmapLoader;
import com.huotu.huobanmall.seller.utils.SystemTools;

import java.util.List;

/**
 * Created by Administrator on 2015/9/15.
 */
public class UserScoreAdapter extends BaseAdapter {

    private List<UserScoreModel> _list;
    private Context _context;

    public UserScoreAdapter(Context context, List<UserScoreModel> list) {
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
            convertView = View.inflate(_context, R.layout.layout_rebatestatistics_item, null);
            holder.ivPicture = (NetworkImageView) convertView.findViewById(R.id.consumestatistics_imageView);
            holder.tvName = (TextView) convertView.findViewById(R.id.consumestatistics_name);
            holder.tvScore = (TextView) convertView.findViewById(R.id.consumestatistics_score);
            holder.tvTime = (TextView)convertView.findViewById(R.id.consumestatistics_time);
            //holder.tvMoblie = (TextView)convertView.findViewById(R.id.consumestatistics_moblie);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvName.setText( _list.get(position).getName() );
        String dateStr = SystemTools.getDateTime( _list.get(position).getTime() , "yyyy-MM-dd HH:mm:ss" );
        holder.tvTime.setText( dateStr );
        holder.tvScore.setText( String.valueOf(_list.get(position).getScore()) );
        BitmapLoader.create().displayUrl( _context , holder.ivPicture , _list.get(position).getPictureUrl() );
        return convertView;

    }

    class ViewHolder
    {
        TextView tvName;

        TextView tvScore;

        TextView tvNickName;

        NetworkImageView ivPicture;

        TextView tvMoblie;

        TextView tvTime;
    }
}
