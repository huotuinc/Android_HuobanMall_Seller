package com.huotu.huobanmall.seller.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huotu.huobanmall.seller.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/8/31.
 */
public class PushActivity extends BaseFragmentActivity {

    // 界面名称
    @Bind(R.id.title)
    public TextView titleName;
    @Bind(R.id.backImage)
    public Button titleBack;

    @Bind(R.id.RLnotice)
    public RelativeLayout RLnotice;
    @Bind(R.id.RLxiaohuoban)
    public RelativeLayout RLxiaohuoban;
    @Bind(R.id.RLmiandarao)
    public RelativeLayout RLmiandarao;
    @Bind(R.id.swit1)
    public com.zcw.togglebutton.ToggleButton swit1;
    @Bind(R.id.swit2)
    public com.zcw.togglebutton.ToggleButton swit2;
    @Bind(R.id.swit3)
    public com.zcw.togglebutton.ToggleButton swit3;
    @Bind(R.id.tip)
    public TextView tip;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_push);



        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
    titleName.setText("推送设置");
    }
}
