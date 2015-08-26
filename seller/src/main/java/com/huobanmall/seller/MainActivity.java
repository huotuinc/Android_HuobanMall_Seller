package com.huobanmall.seller;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.huotu.android.library.libedittext.EditText;




public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button titleBack;

    // 用户名
    private EditText userName;

    // 密码
    private EditText passWord;

    // 登录按钮
    private Button loginBtn;

    // 忘记密码
    private TextView forgetPsw;

    // 界面名称
    private TextView titleName;


    // 返回文字事件
    private TextView backText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);
        initView();

    }
    private void initView()
    {
        titleName = (TextView) this.findViewById(R.id.title);
        titleName.setText("用户登录");
        userName = (EditText) this.findViewById(R.id.edtUserName);
        passWord = (EditText) this.findViewById(R.id.edtPwd);
        loginBtn = (Button) this.findViewById(R.id.btnLogin);
        forgetPsw = (TextView) this.findViewById(R.id.forgetpsw);
        forgetPsw.setOnClickListener(this);
        forgetPsw.setText("忘记密码？");
        titleBack = (Button) this.findViewById(R.id.backImage);
        backText = (TextView) this.findViewById(R.id.backtext);
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

            }
        }
}
