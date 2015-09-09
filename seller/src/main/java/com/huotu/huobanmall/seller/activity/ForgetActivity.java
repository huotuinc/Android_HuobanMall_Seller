
package com.huotu.huobanmall.seller.activity;


import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.avast.android.dialogs.fragment.ProgressDialogFragment;
import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.avast.android.dialogs.iface.ISimpleDialogListener;
import com.huotu.huobanmall.seller.bean.HTForget;
import com.huotu.huobanmall.seller.bean.HTMerchantModel;
import com.huotu.huobanmall.seller.bean.MJInitData;
import com.huotu.huobanmall.seller.bean.MJSendSMSModel;
import com.huotu.huobanmall.seller.bean.MerchantModel;
import com.huotu.huobanmall.seller.common.Constant;
import com.huotu.android.library.libedittext.EditText;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.common.SellerApplication;
import com.huotu.huobanmall.seller.utils.EncryptUtil;
import com.huotu.huobanmall.seller.utils.GsonRequest;
import com.huotu.huobanmall.seller.utils.HttpParaUtils;
import com.huotu.huobanmall.seller.utils.VolleyRequestManager;
import com.huotu.huobanmall.seller.widget.CountDownTimerButton;
import java.util.HashMap;
import java.util.Map;

/**
 * @类名称：ForgetActivity
 * @类描述：忘记密码界面
 * @创建人：aaron
 * @修改人：
 * @修改时间：2015年6月10日 上午10:02:25
 * @修改备注：
 * @version:
 */
public class ForgetActivity extends BaseFragmentActivity implements OnClickListener ,ISimpleDialogListener {
    private int REQUEST_CODE= 3001;

    private TextView titleName;

    private EditText edtPhone;

    private EditText edtCode;

    private TextView btnGet;

    private EditText edtPwd;

    private EditText edtRePwd;

    private Button btnComplete;
    //返回文字事件
    private TextView backText;

    public SellerApplication application;

    public ProgressDialogFragment progressDialog;

    private CountDownTimerButton countDownTimerButton;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = ( SellerApplication ) ForgetActivity.this.getApplication ();
        this.setContentView(R.layout.activity_forget);
        initView ( );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimerButton != null) {
            countDownTimerButton.Stop();
        }
    }

    private void initView() {
        titleName = (TextView) this.findViewById(R.id.title);
        titleName.setText("用户忘记密码");
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtCode = (EditText) this.findViewById ( R.id.edtCode );
        btnGet = (TextView) this.findViewById(R.id.btnGet);
        btnGet.setTag(Constant.SMS_TYPE_TEXT);
        btnGet.setText ( "获取验证码" );
        edtPwd = (EditText) this.findViewById(R.id.edtPwd);
        edtRePwd = (EditText) this.findViewById(R.id.edtRePwd);
        btnComplete = (Button) this.findViewById(R.id.btnComplete);
        backText = (TextView) this.findViewById(R.id.backtext);
        backText.setOnClickListener ( this );
        btnGet.setOnClickListener ( this );
        btnComplete.setOnClickListener ( this );

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backtext: {
                finish();
            }
            break;
            case R.id.btnComplete: {

                if(canSubmit())
                {
                    forgetPassword();
                }
            }
            break;
            case R.id.btnGet: {

                if(hasPhone ( ))
                {
                    sendSMS();

                    countDownTimerButton=new CountDownTimerButton( btnGet , "%d秒重新发送", "获取验证码", 60000,null);
                    countDownTimerButton.start();

                } else
                {
                    edtPhone.setError ( "请输入手机号" );
                }

            }
            break;
            default:
                break;
        }


    }

    private boolean hasPhone()
    {
        if ( TextUtils.isEmpty ( edtPhone.getText ( ) ))
        {

            return false;
        } else
        {
            return true;
        }
    }

    private boolean canSubmit()
    {
        if (!hasPhone())
        {
            edtPhone.setError("手机不能为空");
            return false;
        } else if (TextUtils.isEmpty(edtCode.getText()))
        {
            edtCode.setError("验证码不能为空");
            return false;
        } else if (TextUtils.isEmpty(edtPwd.getText()))
        {
            edtPwd.setError("密码不能为空");
            return false;
        } else if (TextUtils.isEmpty(edtRePwd.getText()))
        {
            edtRePwd.setError("密码确认不能为空");
            return false;
        } else if (!edtPwd.getText().toString()
                          .equals(edtRePwd.getText().toString()))
        {
            edtRePwd.setError("两次密码输入不同");
            return false;
        } else
        {
            return true;
        }
    }

    private void sendSMS()
    {
        String url = Constant.GET_VD_INTERFACE;
        HttpParaUtils httpParaUtils = new HttpParaUtils();
        Map<String , String> map = new HashMap<>();
        map.put("phone", edtPhone.getText().toString());
        map.put("type", Constant.GET_VD_TYPE_FORGET);
        map.put("codeType", btnGet.getTag().toString());
        url = httpParaUtils.getHttpGetUrl( url , map );

        GsonRequest<MJSendSMSModel> sendSMSRequest = new GsonRequest<MJSendSMSModel>(Request.Method.GET,
                url ,
                MJSendSMSModel.class,
                null,
                sendSMSListener,
                errorListener
                );

        VolleyRequestManager.getRequestQueue().add( sendSMSRequest );
    }

    private void forgetPassword() {
        String url = Constant.FORGET_INTERFACE;
        Map<String, String> paras = new HashMap<>();
        paras.put("phone", edtPhone.getText().toString());
        paras.put("password", EncryptUtil.getInstance().encryptMd532(edtPwd.getText().toString()));
        paras.put("authcode",edtCode.getText().toString());
        HttpParaUtils utils = new HttpParaUtils();
        url = utils.getHttpGetUrl(url, paras);

        if( progressDialog !=null ) {
            progressDialog.dismiss();
            progressDialog=null;
        }
        ProgressDialogFragment.ProgressDialogBuilder builder = ProgressDialogFragment.createBuilder(this, getSupportFragmentManager())
                    .setMessage("正在登录，请稍等...")
                    .setCancelable(false)
                    .setCancelableOnTouchOutside(false);
        progressDialog = (ProgressDialogFragment) builder.show();

        GsonRequest<HTForget> forgetGsonRequest =new GsonRequest<HTForget>(
                Request.Method.GET,
                url ,
                HTForget.class,
                null,
                forgetPasswordListener,
                errorListener
        );

        VolleyRequestManager.getRequestQueue().add(forgetGsonRequest);
    }

    Response.Listener<MJSendSMSModel> sendSMSListener = new Response.Listener<MJSendSMSModel>() {
        @Override
        public void onResponse(MJSendSMSModel sendSMSModel ) {
           if( sendSMSModel.getSystemResultCode() != 1){
               SimpleDialogFragment.createBuilder( ForgetActivity.this , ForgetActivity.this.getSupportFragmentManager())
                       .setTitle("错误信息")
                       .setMessage(sendSMSModel.getSystemResultDescription() )
                       .setNegativeButtonText("关闭")
                       .show();
               return;
           }
            if( sendSMSModel.getResultCode() != 1 ){
                SimpleDialogFragment.createBuilder( ForgetActivity.this , ForgetActivity.this.getSupportFragmentManager())
                        .setTitle("错误信息")
                        .setMessage(sendSMSModel.getResultDescription() )
                        .setNegativeButtonText("关闭")
                        .show();
                return;
            }

            Toast.makeText(ForgetActivity.this, ""+ sendSMSModel.getResultData().getVoiceAble() ,Toast.LENGTH_LONG).show();
        }
    };

    Response.Listener<HTForget> forgetPasswordListener = new Response.Listener<HTForget>() {
        @Override
        public void onResponse(HTForget htForget ) {
            if(progressDialog!=null){
                progressDialog.dismiss();
                progressDialog=null;
            }

            if(  htForget.getSystemResultCode() != 1 ) {
                SimpleDialogFragment.createBuilder(ForgetActivity.this, ForgetActivity.this.getSupportFragmentManager())
                        .setTitle("系统错误")
                        .setMessage(htForget.getSystemResultDescription())
                        .setNegativeButtonText("关闭")
                        .show();
                return;
            }
            if( htForget.getResultCode() !=1 ){
                SimpleDialogFragment.createBuilder(ForgetActivity.this, ForgetActivity.this.getSupportFragmentManager())
                        .setTitle("系统错误")
                        .setMessage(htForget.getResultDescription())
                        .setNegativeButtonText("关闭")
                        .show();
                return;
            }

            SimpleDialogFragment.createBuilder(ForgetActivity.this, ForgetActivity.this.getSupportFragmentManager())
                    .setTitle("找回密码")
                    .setMessage("找回密码成功")
                    .setNegativeButtonText("关闭")
                    .setRequestCode( REQUEST_CODE)
                    .show();
            return;
        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            SimpleDialogFragment.createBuilder(ForgetActivity.this, ForgetActivity.this.getSupportFragmentManager())
                    .setTitle("错误信息")
                    .setMessage(volleyError.getMessage())
                    .setNegativeButtonText("关闭")
                    .show();
        }
    };

    @Override
    public void onNegativeButtonClicked(int i) {
        if( i== REQUEST_CODE){
            this.finish();
        }
    }

    @Override
    public void onNeutralButtonClicked(int i) {

    }

    @Override
    public void onPositiveButtonClicked(int i) {

    }
}




