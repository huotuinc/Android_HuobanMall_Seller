package com.huotu.huobanmall.seller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.bean.GoodsModel;
import com.huotu.huobanmall.seller.bean.OrderListModel;
import com.huotu.huobanmall.seller.utils.BitmapLoader;
import java.util.List;

/**
 * Created by Administrator on 2015/9/21.
 */
public class ChildOrderExpandableAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private List<OrderListModel> _data;
    private LayoutInflater _inflater =null;
    public ChildOrderExpandableAdapter( Context context , List<OrderListModel> data ) {
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
        return _data==null? null: _data.get(groupPosition).getChildOrders().get(childPosition);
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
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        OrderListModel model = _data.get(groupPosition);
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = _inflater.inflate(R.layout.layout_childorder_group , null);
            holder = new ViewHolder();
            holder.tvOrdernNo = (TextView) convertView.findViewById(R.id.child_order_group_orderno);
            holder.tvStatus = (TextView) convertView.findViewById(R.id.child_order_group_status);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvOrdernNo.setText(model.getOrderNo());
        holder.tvStatus.setText( String.valueOf( model.getStatus() ) );

        return convertView;
    }
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        GoodsModel model = _data.get(groupPosition).getList().get(childPosition);
        ViewHolder holder =null;
        if( convertView==null){
            convertView = _inflater.inflate(R.layout.layout_child_order_item ,null);
            holder= new ViewHolder();
            holder.tvGoodsName= (TextView)convertView.findViewById(R.id.child_order_item_goodsName);
            holder.tvCount = (TextView)convertView.findViewById(R.id.child_order_item_count);
            holder.ivPicture = (NetworkImageView) convertView.findViewById(R.id.child_order_item_picture);
            //holder.tvStatus = (TextView)convertView.findViewById(R.id.order_score_item_status);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder)convertView.getTag();
        }

        holder.tvGoodsName.setText( model.getTitle() );
        holder.tvCount.setText( String.valueOf( model.getStock() ));
        BitmapLoader.create().displayUrl(_context, holder.ivPicture , model.getPictureUrl() );

        return convertView;
    }

    public class ViewHolder {
        public TextView tvOrdernNo;
        public TextView tvGoodsName;
        public TextView tvCount;
        public TextView tvGetTime;
        public TextView tvStatus;
        public TextView tvScore;
        public NetworkImageView ivPicture;
    }
}
