package com.huotu.huobanmall.seller.activity;




import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.adapter.LogisticsAdapter;
import com.huotu.huobanmall.seller.bean.LogisticsDetailModel;
import com.huotu.huobanmall.seller.utils.ActivityUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OrdermanagementDetailsActivity extends BaseFragmentActivity implements View.OnClickListener {

    @Bind(R.id.header_back)
    TextView header_back;
    @Bind(R.id.header_title)
    TextView header_title;
    @Bind(R.id.header_operate)
    TextView header_operate;
    @Bind(R.id.moblic)
    TextView moblic;
    @Bind(R.id.logistics)
    TextView logistics;

    List<LogisticsDetailModel> _list=null;
    LogisticsAdapter _adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordermanagement_details);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
    protected void initView() {
        ButterKnife.bind(this);
        header_back.setOnClickListener(this);
        header_title.setText("订单管理详情");
        moblic.setOnClickListener(this);
        logistics.setOnClickListener(this);
    }
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.header_back: {
                finish();
            }
            break;
            case R.id.logistics: {
                ActivityUtils.getInstance().showActivity(OrdermanagementDetailsActivity.this, LogisticsActivity.class);
            }
            break;
        }

    }
}


