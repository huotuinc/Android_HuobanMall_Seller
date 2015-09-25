package com.huotu.huobanmall.seller.activity;


import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;

import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.common.SellerApplication;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 *
 * @类名称：MessageActivity
 * @类描述：消息中心界面
 * @创建人：
 * @修改人：
 * @修改时间：
 * @修改备注：
 * @version:
 */
public class MessageActivity extends BaseFragmentActivity implements View.OnClickListener {

    public SellerApplication application;
    @Bind(R.id.header_title)
    public TextView header_title;
    @Bind(R.id.header_back)
    public Button header_back;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_message);
        application = ( SellerApplication ) MessageActivity.this.getApplication ();
        ButterKnife.bind(this);

        initView();
    }
    private void initView() {

        header_title.setText("消息中心");
        header_back.setOnClickListener(this);

    }





        @Override
        public void onClick(View v)
        {
            // TODO Auto-generated method stub
            switch (v.getId()) {
                case R.id.header_back:{
                    finish();
            }
                    break;


                default:
                    break;
            }
        }




}
