package com.huotu.huobanmall.seller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.bean.LogisticsDataModel;
import java.util.List;

/**
 * Created by Administrator on 2015/9/15.
 */
public class LogisticsAdapter extends BaseAdapter{
    private List<LogisticsDataModel> _list=null;
    private LayoutInflater _inflater=null;
    private Context _context;

    @Override
    public int getCount() {
        return _list==null?0:_list.size();
    }

    @Override
    public Object getItem(int position) {
        return _list==null?null:_list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LogisticsDetailViewHolder holder;
        if( convertView==null){
            holder=new LogisticsDetailViewHolder();
            if( position==0 ) {
                convertView = _inflater.inflate( R.layout.layout_logistics_item_first, null);
                holder.tvLogistics_Context =(TextView) convertView.findViewById(R.id.logistics_item_context);
                holder.tvTime = (TextView) convertView.findViewById(R.id.logistics_item_time);
            }else{
                convertView = _inflater.inflate(R.layout.layout_logistics_item,null);
                holder.tvLogistics_Context=(TextView)convertView.findViewById(R.id.logistics_item_context);
                holder.tvTime = (TextView) convertView.findViewById(R.id.logistics_item_time);
            }
            convertView.setTag( holder);
        }else{
            holder = (LogisticsDetailViewHolder)convertView.getTag();
        }

        holder.tvLogistics_Context.setText( _list.get(position).getContext() );
        //String dateStr = SystemTools.getDateTime( _list.get(position).getTimes() , "yyyy-MM-dd HH:mm:ss" );
        holder.tvTime.setText( _list.get(position).getTimes()  );

        return convertView;
    }

    public LogisticsAdapter(Context context , List<LogisticsDataModel> list){
        _list=list;
        _context=context;
        _inflater = LayoutInflater.from(context);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if( position == 0){
            return 0;
        }else {
            return 1;
        }
    }

    public class LogisticsDetailViewHolder{ //extends RecyclerView.ViewHolder{
        private TextView tvLogistics_Context;
        private TextView tvTime;

    }
}
