package com.huotu.huobanmall.seller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.bean.LogisticsDetailModel;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Administrator on 2015/9/15.
 */
public class LogisticsAdapter extends RecyclerView.Adapter<LogisticsAdapter.LogisticsDetailViewHolder> {
    private List<LogisticsDetailModel> _list=null;
    private LayoutInflater _inflater=null;
    private Context _context;

    public LogisticsAdapter(Context context , List<LogisticsDetailModel> list){
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

    @Override
    public LogisticsDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LogisticsDetailViewHolder viewHolder;
        if( viewType == 0) {
            View view = _inflater.inflate(R.layout.layout_logistics_item_first , null);
            viewHolder = new LogisticsDetailViewHolder( view );
        }else {
            View view = _inflater.inflate( R.layout.layout_logistics_item,null);
            viewHolder = new LogisticsDetailViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LogisticsDetailViewHolder holder, int position) {
        if(_list==null) return;
        LogisticsDetailModel model = _list.get(position);
        holder.tvLogistics_Context.setText( model.get_context());
    }

    @Override
    public int getItemCount() {
        return _list==null ? 0: _list.size();
    }

    public class LogisticsDetailViewHolder extends RecyclerView.ViewHolder{
        private TextView tvLogistics_Context;
        public LogisticsDetailViewHolder(View itemView) {
            super(itemView);

            tvLogistics_Context = (TextView)itemView.findViewById(R.id.logistics_item_context);
        }
    }
}