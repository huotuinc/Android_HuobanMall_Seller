package com.huotu.huobanmall.seller.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.adapter.GoodsAdapter;
import com.huotu.huobanmall.seller.bean.GoodsModel;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogRecord;

public class TestActivity extends BaseFragmentActivity {
    PullToRefreshScrollView _sv;
    Handler _handler;

    @Override
    protected void onResume() {
        super.onResume();

        _handler.post(new Runnable() {
            @Override
            public void run() {
                //_sv.getRefreshableView().fullScroll(ScrollView.FOCUS_UP);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        List<GoodsModel> list= new ArrayList<>();
        for(int i =0;i<20;i++){
            GoodsModel item = new GoodsModel();
            item.setTitle("aaaaaa");
            item.setStock(111);
            item.setPrice(345.33f);
            item.setGoodsId(i);
            list.add(item);
        }

        GoodsAdapter adapter= new GoodsAdapter(this, list);


        float scale = this.getResources().getDisplayMetrics().density;
        int temp = (int) ( 100 * scale + 0.5f);

        //float scale = this.getResources().getDisplayMetrics().density;
        int itemh = (int) ( 200 / scale + 0.5f);

        int height = itemh * list.size();

        ListView lv2 = (ListView)this.findViewById(R.id.ttt2);
        lv2.setAdapter(adapter);
        //ViewGroup.LayoutParams paras = lv2.getLayoutParams();
        //paras.height = height;
        //lv2.setLayoutParams(paras);
        //setheight(lv2);

        ListView lv =(ListView) this.findViewById(R.id.ttt1);
        lv.setAdapter(adapter);

        //setheight(lv);
        //paras = lv.getLayoutParams();
        //paras.height = height;
        //lv.setLayoutParams(paras);


        //_sv = (PullToRefreshScrollView)this.findViewById(R.id.ss1);

        _handler= new Handler();

    }

    public static void setheight(ListView listView)
    {
        ListAdapter listAdapter=listView.getAdapter();
        if(listAdapter==null){return;}
        int maxHeight=0;
        int itemNum=listAdapter.getCount();
        for(int i=0;i<itemNum;i++)
        {
            View listItem=listAdapter.getView(i,null,listView);
            listItem.measure(0,0);
            int thisHeight=listItem.getMeasuredHeight();//计算子项View的宽高
            maxHeight=(maxHeight>thisHeight)?(maxHeight):(thisHeight);
        }
        for(int j=0;j<itemNum;j++)
        {
            View listItem=listAdapter.getView(j,null,listView);
            ViewGroup.LayoutParams params=listItem.getLayoutParams();
            params.height=maxHeight;
            listItem.setLayoutParams(params);
        }
    }



}
