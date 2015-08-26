
package com.huotu.huobanmall.seller.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.huotu.huobanmall.seller.common.Constants;
import com.huotu.android.library.libedittext.EditText;
import com.huotu.huobanmall.seller.R;


/**
 *
 * @类名称：ForgetActivity
 * @类描述：忘记密码界面
 * @创建人：aaron
 * @修改人：
 * @修改时间：2015年6月10日 上午10:02:25
 * @修改备注：
 * @version:
 */

public class ForgetActivity extends AppCompatActivity implements OnClickListener {

    private TextView titleName;

    private EditText edtPhone;

    private EditText edtCode;

    private TextView btnGet;

    private EditText edtPwd;

    private EditText edtRePwd;

    private Button btnComplete;
    //返回文字事件
    private TextView backText;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.user_forget);
        initView();
    }



    private void initView() {
        titleName = (TextView) this.findViewById(R.id.title);
        titleName.setText("用户忘记密码");
        edtPhone=(EditText)findViewById(R.id.edtPhone);
        edtCode = (EditText) this.findViewById(R.id.edtCode);
        btnGet = (TextView) this.findViewById(R.id.btnGet);
        btnGet.setTag(Constants.SMS_TYPE_TEXT);
        btnGet.setText("获取验证码");
        edtPwd = (EditText) this.findViewById(R.id.edtPwd);
        edtRePwd = (EditText) this.findViewById(R.id.edtRePwd);
        btnComplete = (Button) this.findViewById(R.id.btnComplete);
        backText = (TextView) this.findViewById(R.id.backtext);
        backText.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

    }
}




