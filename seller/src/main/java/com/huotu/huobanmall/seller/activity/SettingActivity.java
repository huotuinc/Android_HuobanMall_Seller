package com.huotu.huobanmall.seller.activity;

import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.common.Constant;
import com.huotu.huobanmall.seller.common.SellerApplication;
import com.huotu.huobanmall.seller.utils.ActivityUtils;
import com.huotu.huobanmall.seller.utils.BitmapLoader;
import com.huotu.huobanmall.seller.utils.PreferenceHelper;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/8/31.
 */
public class SettingActivity extends BaseFragmentActivity
        implements View.OnClickListener {

    public
    SellerApplication application;
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
    @Bind(R.id.shopName)
    TextView shopName;
    @Bind(R.id.Shopdescription)
    TextView ShopDescription;
    @Bind(R.id.logo)
    NetworkImageView logo;
    @Bind(R.id.txtId)
    TextView tvUserId;
    @Bind(R.id.txtName)
    TextView tvNickName;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView ( R.layout.activity_setting );
        application = ( SellerApplication ) SettingActivity.this.getApplication ();
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        pushLabel.setOnClickListener(this);
        titleName.setText("用户设置");
        backText.setOnClickListener(this);
        backImage.setOnClickListener(this);
        quitbtn.setOnClickListener(this);

        String shopNameStr = PreferenceHelper.readString( application , Constant.LOGIN_USER_INFO , Constant.LOGIN_AUTH_SHOPNAME );
        shopName.setText( shopNameStr);
        String shopDescription = PreferenceHelper.readString(application , Constant.LOGIN_USER_INFO, Constant.LOGIN_AUTH_DISCRIPTION);
        ShopDescription.setText(shopDescription);
        String logoUrl = PreferenceHelper.readString(application, Constant.LOGIN_USER_INFO , Constant.LOGIN_AUTH_LOGO);
        BitmapLoader.create().displayUrl(SettingActivity.this, logo, logoUrl);
        String userId = PreferenceHelper.readString(application, Constant.LOGIN_USER_INFO,Constant.LOGIN_AUTH_MOBILE);
        tvUserId.setText( userId );
        String nickname=PreferenceHelper.readString(application,Constant.LOGIN_USER_INFO , Constant.LOGIN_AUTH_NICKNAME);
        tvNickName.setText(nickname);
    }

    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pushLabel: {
                ActivityUtils.getInstance().showActivity(SettingActivity.this, PushActivity.class);

            }
            break;
            case R.id.backtext: {
                finish ( );
            }
            break;
            case R.id.backImage: {
                finish();
            }
            break;
            case R.id.changePswLabel: {
                ActivityUtils.getInstance().showActivity ( SettingActivity.this, ForgetActivity.class );

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
            case R.id.quit: {
                // ActivityUtils.getInstance().showActivity(SettingActivity.this, MainActivity.class);

                //清空token等用户信息
                application.cleanMerchantInfo ();
                //跳转到
                ActivityUtils.getInstance ().skipActivity ( SettingActivity.this, LoginActivity.class );
            }
            break;
        }

    }
}