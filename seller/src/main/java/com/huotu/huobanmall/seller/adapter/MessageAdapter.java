package com.huotu.huobanmall.seller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.bean.MessageModel;
import com.huotu.huobanmall.seller.utils.SystemTools;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Administrator on 2015/9/28.
 */
public class MessageAdapter extends BaseAdapter {
    Context _context;
    List<MessageModel> _list;
    public MessageAdapter( Context context,List<MessageModel> list){
        _context=context;
        _list=list;
    }

    @Override
    public int getCount() {
        return _list==null?0:_list.size();
    }

    @Override
    public Object getItem(int position) {
        return _list==null?null:_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if( convertView==null){
            LayoutInflater inflater = LayoutInflater.from(_context);
            convertView = inflater.inflate(R.layout.layout_message_item,null);
            holder = new ViewHolder();
            holder.tvMsgCon = (TextView)convertView.findViewById(R.id.msgCon);
            holder.tvMsgTime =(TextView)convertView.findViewById(R.id.msgTime);
            convertView.setTag( holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }

        MessageModel model = _list.get(position);

        holder.tvMsgCon.setText( model.getContext()==null?"":model.getContext().trim() );
        String dateStr = SystemTools.getDateTime( model.getDate() ,"yyyy-MM-dd HH:mm:ss" );
        holder.tvMsgTime.setText( dateStr );

        return convertView;
    }

    class ViewHolder{
        TextView tvMsgCon;
        TextView tvMsgTime;
    }
}
