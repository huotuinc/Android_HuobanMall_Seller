package com.huotu.huobanmall.seller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.avast.android.dialogs.fragment.ProgressDialogFragment;
import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.avast.android.dialogs.iface.ISimpleDialogCancelListener;
import com.huotu.android.library.libedittext.EditText;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.bean.MerchantModel;
import com.huotu.huobanmall.seller.common.Constants;
import com.huotu.huobanmall.seller.utils.ActivityUtils;
import com.huotu.huobanmall.seller.utils.DigestUtils;
import com.huotu.huobanmall.seller.utils.GsonRequest;
import com.huotu.huobanmall.seller.utils.HttpParaUtils;
import com.huotu.huobanmall.seller.utils.VolleyRequestManager;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends BaseFragmentActivity implements
        View.OnClickListener  {
    @Bind(R.id.backImage)
    public Button titleBack;
<<<<<<< HEAD

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

=======
    // 用户名
    @Bind(R.id.edtUserName)
    public EditText userName;
    // 密码
    @Bind(R.id.edtPwd)
    public EditText passWord;
    @Bind(R.id.btnLogin)
    // 登录按钮
    public Button loginBtn;
    // 忘记密码
    @Bind(R.id.forgetpsw)
    public TextView forgetPsw;
>>>>>>> 338cfba8b01439cb4b33f87541fe86a0adfcbfc3
    // 界面名称
    @Bind(R.id.title)
    public TextView titleName;
    // 返回文字事件
<<<<<<< HEAD
    public TextView backText;


=======
    @Bind(R.id.backtext)
    public TextView backText;
    public ProgressDialogFragment progressDialog;
>>>>>>> 338cfba8b01439cb4b33f87541fe86a0adfcbfc3

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
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    protected void Login(){
        ProgressDialogFragment.ProgressDialogBuilder builder = ProgressDialogFragment.createBuilder(this, getSupportFragmentManager())
                .setMessage("正在登录，请稍等...")
                .setCancelable(false)
                .setCancelableOnTouchOutside(false);
        progressDialog = (ProgressDialogFragment) builder.show();

        String url = Constants.LOGIN_INTERFACE;
        HttpParaUtils httpUtils = new HttpParaUtils();
        Map<String,String> paras = new HashMap<>();

        String pwd = passWord.getText().toString();
        String pwdEncy="";
        try {
            pwdEncy = DigestUtils.md5DigestAsHex(pwd.getBytes("utf-8"));
        }catch (UnsupportedEncodingException ex){

        }

        paras.put("username","test");
        paras.put("password", pwdEncy);
        url = httpUtils.getHttpGetUrl(url,paras);

        GsonRequest<MerchantModel> loginRequest = new GsonRequest<MerchantModel>(
                Request.Method.GET
                ,url
                ,MerchantModel.class
                ,null
                ,loginListener
                ,errorListener
        );

        VolleyRequestManager.getRequestQueue().add(loginRequest);
    }

    Response.Listener loginListener = new Response.Listener() {
        @Override
        public void onResponse(Object o) {

            ActivityUtils.getInstance().showActivity(LoginActivity.this,MainActivity.class);

        }
    };

    Response.ErrorListener errorListener=new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            if(progressDialog!=null){
                progressDialog.dismiss();
            }
            SimpleDialogFragment.createBuilder( LoginActivity.this , LoginActivity.this.getSupportFragmentManager())
                    .setTitle("错误信息")
                    .setMessage( volleyError.getMessage())
                    .setNegativeButtonText("关闭")
                    .show();
        }
    };

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
//                Intent intent=new Intent(this, MainActivity.class);
//                startActivity(intent);
                Login();
            }
            break;

            }
        }

}
