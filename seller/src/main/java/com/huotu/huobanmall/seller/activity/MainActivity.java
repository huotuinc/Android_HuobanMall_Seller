package com.huotu.huobanmall.seller.activity;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import com.huotu.huobanmall.seller.adapter.MenuAdapter;
import com.huotu.huobanmall.seller.bean.MenuModel;
import com.huotu.huobanmall.seller.utils.ActivityUtils;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.widget.CountUpTimerView;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseFragmentActivity {

    @Bind(R.id.main_gridView)
    GridView main_gridView;
    @Bind(R.id.main_todyMoney)
    TextView main_todyMoney;

    MenuAdapter mAdapter;
    List<MenuModel> mMenus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        String formatText = "￥%.2f";
        //CountUpTimerView countUpView =new CountUpTimerView( main_todyMoney , formatText , 1000.33f,5000.45f, 3000,100);
        //countUpView.start();

        mMenus = new ArrayList<>();
        final String[] menus = getResources().getStringArray(R.array.main_menu_name);
        TypedArray iconsTA = getResources().obtainTypedArray(R.array.main_menu_icon);

        for (int i = 0; i < menus.length; i++) {
            MenuModel item = new MenuModel();
            item.setName(menus[i]);
            item.setIcon(iconsTA.getResourceId(i, 0));
            mMenus.add(item);
        }
        mAdapter = new MenuAdapter(mMenus, this);
        main_gridView.setAdapter(mAdapter);
        main_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MenuModel item = mMenus.get(position);
                if (item.getIcon() == R.mipmap.cpgl) {
                    ActivityUtils.getInstance().showActivity(MainActivity.this, GoodsEditActivity.class);
                } else if (item.getIcon() == R.mipmap.ddgl) {
                    //ActivityUtils.getInstance().showActivity(this , GoodsEditActivity.class);
                } else if (item.getIcon() == R.mipmap.zytj) {
                    ActivityUtils.getInstance().showActivity(MainActivity.this, DataStatisticActivity.class);
                } else if (item.getIcon() == R.mipmap.szgl) {
                    ActivityUtils.getInstance().showActivity(MainActivity.this, SettingActivity.class);
                } else if (item.getIcon() == R.mipmap.gdsj) {
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
