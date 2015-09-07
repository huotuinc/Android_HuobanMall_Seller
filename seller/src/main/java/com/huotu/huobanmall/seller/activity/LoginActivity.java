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
import com.avast.android.dialogs.iface.ISimpleDialogListener;
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
        View.OnClickListener , ISimpleDialogListener{
    private final static int REQUEST_UPDATE=2045;

    @Bind(R.id.backImage)
    public Button titleBack;

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
    // 界面名称
    @Bind(R.id.title)
    public TextView titleName;
    // 返回文字事件
    @Bind(R.id.backtext)
    public TextView backText;
    public ProgressDialogFragment progressDialog;

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

            ActivityUtils.getInstance().showActivity(LoginActivity.this, MainActivity.class);

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

    protected void testAppUpdate(){
        String tips="本文版权归作者和博客园共有，欢迎转载，但未经作者同意必须保留此段声明，且在文章页面明显位置给出原文连接，否则保留追究法律责任的权利.本文版权归作者和博客园共有，欢迎转载，但未经作者同意必须保留此段声明，且在文章页面明显位置给出原文连接，否则保留追究法律责任的权利.本文版权归作者和博客园共有，欢迎转载，但未经作者同意必须保留此段声明，且在文章页面明显位置给出原文连接，否则保留追究法律责任的权利.本文版权归作者和博客园共有，欢迎转载，但未经作者同意必须保留此段声明，且在文章页面明显位置给出原文连接，否则保留追究法律责任的权利.本文版权归作者和博客园共有，欢迎转载，但未经作者同意必须保留此段声明，且在文章页面明显位置给出原文连接，否则保留追究法律责任的权利.本文版权归作者和博客园共有，欢迎转载，但未经作者同意必须保留此段声明，且在文章页面明显位置给出原文连接，否则保留追究法律责任的权利.";
        SimpleDialogFragment.createBuilder(this,getSupportFragmentManager())
                .setTitle("温馨提示")
                .setMessage("发现新版本，马上更新?\n"+tips )
                .setPositiveButtonText("马上更新")
                .setNegativeButtonText("跳过该版本")
                .setRequestCode(REQUEST_UPDATE)
                .show();
    }

    protected void updateApp(){
        boolean isForce=false;
        AppUpdateActivity.UpdateType type= AppUpdateActivity.UpdateType.FullUpate;
        String md5="sadfsafsafafd121";
        String url="http://cdn4.ops.baidu.com/new-repackonline/baidunuomi/AndroidPhone/5.12.0.1/1/1009769b/20150810142355/baidunuomi_AndroidPhone_5-12-0-1_1009769b.apk"; //"http://newresources.fanmore.cn/fanmore/fanmore3.0.apk";
        String tips="本文版权归作者和博客园共有，欢迎转载，但未经作者同意必须保留此段声明，且在文章页面明显位置给出原文连接，否则保留追究法律责任的权利.";

        Intent intent = new Intent( this, AppUpdateActivity.class);
        intent.putExtra("isForce", isForce);
        intent.putExtra("type", type);
        intent.putExtra("md5", md5);
        intent.putExtra("url", url);
        intent.putExtra("tips", tips);
        startActivityForResult(intent, Constants.REQUEST_CODE_CLIENT_DOWNLOAD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent arg2) {
        if (requestCode == Constants.REQUEST_CODE_CLIENT_DOWNLOAD
                && resultCode == Constants.RESULT_CODE_CLIENT_DOWNLOAD_FAILED) {
            Bundle extra = arg2.getExtras();
            if (extra != null) {
                boolean isForce = extra.getBoolean("isForce");
                if (isForce) {
                    finish();
                } else {
                    //toHome();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, arg2);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forgetpsw:
            {

                Intent intent=new Intent(this, ForgetActivity.class);
                startActivity(intent);

//                Intent intent=new Intent(this, ForgetActivity.class);
//                startActivity(intent);
                testAppUpdate();

            }
            break;
            case R.id.btnLogin:
            {

                Login();

                Intent intent=new Intent(this, MainActivity.class);
                startActivity(intent);
                //Login();

            }
            break;
            case R.id.backtext:
            {
                finish();
            }

            }
        }



    @Override
    public void onNegativeButtonClicked(int requestCode) {

    }

    @Override
    public void onNeutralButtonClicked(int requestCode) {

    }

    @Override
    public void onPositiveButtonClicked(int requestCode) {
        if( requestCode == REQUEST_UPDATE){
            updateApp();
        }
    }
}