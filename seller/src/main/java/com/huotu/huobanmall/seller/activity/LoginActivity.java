package com.huotu.huobanmall.seller.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.huotu.android.library.libedittext.EditText;
import com.huotu.huobanmall.seller.R;

import butterknife.Bind;
import butterknife.ButterKnife;


public class LoginActivity extends BaseFragmentActivity implements View.OnClickListener {
    @Bind(R.id.backImage)
    public Button titleBack;

    @Bind(R.id.edtUserName)
    // 用户名
    public EditText userName;

    @Bind(R.id.edtPwd)
    // 密码
    public EditText passWord;

    @Bind(R.id.btnLogin)
    // 登录按钮
    public Button loginBtn;

    @Bind(R.id.forgetpsw)
    // 忘记密码
    public TextView forgetPsw;

    // 界面名称
    @Bind(R.id.title)
    public TextView titleName;

    @Bind(R.id.backtext)
    // 返回文字事件
    public TextView backText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        initView();

    }
    private void initView()
    {
        titleName.setText("用户登录");
        loginBtn.setOnClickListener(this);
        forgetPsw.setOnClickListener(this);
        forgetPsw.setText("忘记密码？");
        backText.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.forgetpsw:
            {
                Intent intent=new Intent(this, ForgetActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.btnLogin:
            {
                Intent intent=new Intent(this, MainActivity.class);
                startActivity(intent);
            }
            break;

            }
        }
}
