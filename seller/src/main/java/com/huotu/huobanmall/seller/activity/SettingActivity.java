package com.huotu.huobanmall.seller.activity;

import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.utils.ActivityUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/8/31.
 */
public class SettingActivity extends BaseFragmentActivity
        implements View.OnClickListener {

    @Bind(R.id.title)
    public TextView titleName;

    @Bind(R.id.backImage)
    public Button backImage;

    @Bind(R.id.backtext)
    public TextView backText;

    @Bind(R.id.shopNameLabel)
    public LinearLayout shopNameLabel;

    @Bind(R.id.shopdescriptionLabel)
    public LinearLayout shopdescriptionLabel;

    @Bind(R.id.imgLabel)
    public LinearLayout imgLabel;

    @Bind(R.id.idLabel)
    public LinearLayout idLabel;

    @Bind(R.id.nameLabel)
    public LinearLayout nameLabel;

    @Bind(R.id.changePswLabel)
    public LinearLayout changePswLabel;

    @Bind(R.id.pushLabel)
    public LinearLayout pushLabel;

    @Bind(R.id.aboutusLabel)
    public LinearLayout aboutusLabel;

    @Bind(R.id.helpLabel)
    public LinearLayout helpLabel;

    @Bind(R.id.FeedbackLabel)
    public LinearLayout FeedbackLabel;

    @Bind(R.id.quit)
    public Button quitbtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setting);

        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        pushLabel.setOnClickListener(this);
        titleName.setText("用户设置");
        backText.setOnClickListener(this);
        backImage.setOnClickListener(this);
    }

    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


//    public boolean handleMessage(Message msg) {
//        return false;
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pushLabel: {
                ActivityUtils.getInstance().showActivity(SettingActivity.this, PushActivity.class);

            }
            break;
            case R.id.backtext: {
                finish();
            }
            break;
            case R.id.backImage: {
                finish();
            }
            break;
            case R.id.changePswLabel: {
                ActivityUtils.getInstance().showActivity(SettingActivity.this, ForgetActivity.class);

            }
            break;
            case R.id.aboutusLabel: {
                // ActivityUtils.getInstance().showActivity(SettingActivity.this,"www.baidu.com");

            }
            break;
            case R.id.helpLabel: {
                // ActivityUtils.getInstance().showActivity(SettingActivity.this, MainActivity.class);

            }
            break;
            case R.id.FeedbackLabel: {
                // ActivityUtils.getInstance().showActivity(SettingActivity.this, MainActivity.class);

            }
            break;
        }

    }
}