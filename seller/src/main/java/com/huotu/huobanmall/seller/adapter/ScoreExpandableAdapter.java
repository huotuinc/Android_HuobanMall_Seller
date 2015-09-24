package com.huotu.huobanmall.seller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.bean.OrderScoreModel;
import com.huotu.huobanmall.seller.bean.UserRebateModel;
import com.huotu.huobanmall.seller.bean.UserScoreModel;
import com.huotu.huobanmall.seller.utils.SystemTools;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Administrator on 2015/9/18.
 */
public class ScoreExpandableAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private List<OrderScoreModel> _data;
    private LayoutInflater _inflater =null;

    public ScoreExpandableAdapter( Context context , List<OrderScoreModel> data ) {
        super();
        _context= context;
        _data =data;
        _inflater = LayoutInflater.from(_context);
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public int getChildType(int groupPosition, int childPosition) {
        return super.getChildType(groupPosition, childPosition);
    }

    @Override
    public int getChildTypeCount() {
        return super.getChildTypeCount();
    }

    @Override
    public int getGroupType(int groupPosition) {
        return super.getGroupType(groupPosition);
    }

    @Override
    public int getGroupTypeCount() {
        return super.getGroupTypeCount();
    }

    @Override
    public int getGroupCount() {
        return _data==null? 0: _data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return _data==null? 0: _data.get( groupPosition ).getList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return _data==null? null : _data.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return _data==null? null: _data.get(groupPosition).getList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        OrderScoreModel model = _data.get(groupPosition);
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = _inflater.inflate(R.layout.layout_score_category, null);
            holder = new ViewHolder();
            holder.tvTitle = (TextView) convertView.findViewById(R.id.order_score_item_title);
            holder.tvArrow = (TextView) convertView.findViewById(R.id.order_score_category_arrow);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvTitle.setText(model.getOrderNo());
        holder.tvArrow.setBackgroundResource( isExpanded? R.mipmap.sjt : R.mipmap.xjt);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        OrderScoreModel model = _data.get(groupPosition).getList().get(childPosition);
        ViewHolder holder =null;
        if( convertView==null){
            convertView = _inflater.inflate(R.layout.layout_score_item,null);
            holder= new ViewHolder();
            holder.tvTitle= (TextView)convertView.findViewById(R.id.order_score_item_no);
            holder.tvGetTime= (TextView)convertView.findViewById(R.id.order_score_item_getTime);
            holder.tvScore= (TextView)convertView.findViewById(R.id.order_score_item_score);
            holder.tvStatus = (TextView)convertView.findViewById(R.id.order_score_item_status);
            holder.tvZZTime = (TextView)convertView.findViewById(R.id.order_score_item_zzTime);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder)convertView.getTag();
        }

        holder.tvTitle.setText( model.getOrderNo() );
        holder.tvScore.setText( model.getScore() +"积分" );
        holder.tvStatus.setText( model.getStatus() );
        holder.tvZZTime.setText( String.valueOf( SystemTools.getDateTime( model.getZzTime() , "yyyy/MM/dd")) );
        holder.tvGetTime.setText( String.valueOf(SystemTools.getDateTime(model.getGetTime(), "yyyy/MM/dd")) );

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    public class ViewHolder {
        public TextView tvTitle;
        public TextView tvArrow;
        public TextView tvGetTime;
        public TextView tvStatus;
        public TextView tvScore;
        public TextView tvZZTime;
    }

}
