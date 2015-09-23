package com.huotu.huobanmall.seller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.bean.LogisticsDataModel;
import com.huotu.huobanmall.seller.bean.LogisticsDetailModel;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Administrator on 2015/9/15.
 */
public class LogisticsAdapter extends BaseAdapter{//RecyclerView.Adapter<LogisticsAdapter.LogisticsDetailViewHolder> {
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
            }else{
                convertView = _inflater.inflate(R.layout.layout_logistics_item,null);
                holder.tvLogistics_Context=(TextView)convertView.findViewById(R.id.logistics_item_context);
            }
            convertView.setTag( holder);
        }else{
            holder = (LogisticsDetailViewHolder)convertView.getTag();
        }

        holder.tvLogistics_Context.setText( _list.get(position).getContext() );

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

//    @Override
//    public LogisticsDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LogisticsDetailViewHolder viewHolder;
//        if( viewType == 0) {
//            View view = _inflater.inflate(R.layout.layout_logistics_item_first , null);
//            viewHolder = new LogisticsDetailViewHolder( view );
//        }else {
//            View view = _inflater.inflate( R.layout.layout_logistics_item,null);
//            viewHolder = new LogisticsDetailViewHolder(view);
//        }
//        return viewHolder;
//    }

//    @Override
//    public void onBindViewHolder(LogisticsDetailViewHolder holder, int position) {
//        if(_list==null) return;
//        LogisticsDetailModel model = _list.get(position);
//        holder.tvLogistics_Context.setText( model.getSource());
//    }

//    @Override
//    public int getItemCount() {
//        return _list==null ? 0: _list.size();
//    }

    public class LogisticsDetailViewHolder{ //extends RecyclerView.ViewHolder{
        private TextView tvLogistics_Context;
//        public LogisticsDetailViewHolder(View itemView) {
//            super(itemView);
//
//            tvLogistics_Context = (TextView)itemView.findViewById(R.id.logistics_item_context);
//        }
    }
}
