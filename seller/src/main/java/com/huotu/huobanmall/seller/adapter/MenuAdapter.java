package com.huotu.huobanmall.seller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.bean.MenuModel;

import java.util.List;

/**
 * Created by Administrator on 2015/8/27.
 */
public class MenuAdapter extends BaseAdapter {
    private List<MenuModel> list;
    private Context context;
    private LayoutInflater inflater;
    public MenuAdapter(List<MenuModel> list , Context context ){
        this.list= list;
        this.context=context;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return null== list? null: list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MenuModel model = list.get(position);

        convertView = inflater.inflate(R.layout.layout_menuitem, null);
        ImageView iv =(ImageView) convertView.findViewById(R.id.menu_icon);
        TextView tv = (TextView)convertView.findViewById(R.id.menu_name);
        iv.setBackgroundResource(model.getIcon());
        tv.setText(model.getName());

        return convertView;
    }
}
