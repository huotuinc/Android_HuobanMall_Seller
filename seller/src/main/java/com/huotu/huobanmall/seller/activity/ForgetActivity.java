
package com.huotu.huobanmall.seller.activity;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.avast.android.dialogs.iface.ISimpleDialogListener;
import com.huotu.huobanmall.seller.bean.MJForget;
import com.huotu.huobanmall.seller.bean.MJSendSMSModel;
import com.huotu.huobanmall.seller.common.Constant;
import com.huotu.android.library.libedittext.EditText;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.common.SellerApplication;
import com.huotu.huobanmall.seller.utils.DialogUtils;
import com.huotu.huobanmall.seller.utils.DigestUtils;
import com.huotu.huobanmall.seller.utils.GsonRequest;
import com.huotu.huobanmall.seller.utils.HttpParaUtils;
import com.huotu.huobanmall.seller.utils.VolleyRequestManager;
import com.huotu.huobanmall.seller.widget.CountDownTimerButton;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @类名称：ForgetActivity
 * @类描述：忘记密码界面
 * @创建人：aaron
 * @修改人：
 * @修改时间：2015年6月10日 上午10:02:25
 * @修改备注：
 * @version:
 */
public class ForgetActivity extends BaseFragmentActivity implements OnClickListener, ISimpleDialogListener {
    private final int REQUEST_CODE = 3001;
    @Bind(R.id.title)
    TextView titleName;
    @Bind(R.id.edtPhone)
    EditText edtPhone;
    @Bind(R.id.edtCode)
    EditText edtCode;
    @Bind(R.id.btnGet)
    TextView btnGet;
    @Bind(R.id.edtPwd)
    EditText edtPwd;
    @Bind(R.id.edtRePwd)
    EditText edtRePwd;
    @Bind(R.id.btnComplete)
    Button btnComplete;
    //返回文字事件
    @Bind(R.id.backtext)
    TextView backText;

    public SellerApplication application;

    private CountDownTimerButton countDownTimerButton;

    private TimeCountDownListener timeCountDownListener;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timeCountDownListener = new TimeCountDownListener();
        application = (SellerApplication) ForgetActivity.this.getApplication();
        this.setContentView(R.layout.activity_forget);
        initView();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimerButton != null) {
            countDownTimerButton.Stop();
        }
    }

    private void initView() {
        ButterKnife.bind(this);
        titleName.setText("用户忘记密码");
        btnGet.setTag(Constant.SMS_TYPE_TEXT);
        btnGet.setText("获取验证码");
        backText.setOnClickListener(this);
        btnGet.setOnClickListener(this);
        btnComplete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backtext: {
                finish();
            }
            break;
            case R.id.btnComplete: {

                if (canSubmit()) {
                    forgetPassword();
                }
            }
            break;
            case R.id.btnGet: {

                if (hasPhone()) {
                    sendSMS();

                    countDownTimerButton = new CountDownTimerButton(btnGet, "%d秒重新发送", "获取验证码", 60000, timeCountDownListener);
                    countDownTimerButton.start();

                } else {
                    edtPhone.setError("请输入手机号");
                }

            }
            break;
            default:
                break;
        }


    }

    private boolean hasPhone() {
        if (TextUtils.isEmpty(edtPhone.getText())) {
            return false;
        } else {
            return true;
        }
    }

    private boolean canSubmit() {
        if (!hasPhone()) {
            edtPhone.setError("手机不能为空");
            return false;
        } else if (TextUtils.isEmpty(edtCode.getText())) {
            edtCode.setError("验证码不能为空");
            return false;
        } else if (TextUtils.isEmpty(edtPwd.getText())) {
            edtPwd.setError("密码不能为空");
            return false;
        } else if (TextUtils.isEmpty(edtRePwd.getText())) {
            edtRePwd.setError("密码确认不能为空");
            return false;
        } else if (!edtPwd.getText().toString()
                .equals(edtRePwd.getText().toString())) {
            edtRePwd.setError("两次密码输入不同");
            return false;
        } else {
            return true;
        }
    }

    private void sendSMS() {
        String url = Constant.GET_VD_INTERFACE;
        HttpParaUtils httpParaUtils = new HttpParaUtils();
        Map<String, String> map = new HashMap<>();
        map.put("phone", edtPhone.getText().toString());
        map.put("type", Constant.GET_VD_TYPE_FORGET);
        map.put("codeType", btnGet.getTag().toString());
        url = httpParaUtils.getHttpGetUrl(url, map);

        GsonRequest<MJSendSMSModel> sendSMSRequest = new GsonRequest<MJSendSMSModel>(Request.Method.GET,
                url,
                MJSendSMSModel.class,
                null,
                sendSMSListener,
                errorListener
        );

        VolleyRequestManager.getRequestQueue().add(sendSMSRequest);
    }

    private void forgetPassword() {
        String url = Constant.FORGET_INTERFACE;
        Map<String, String> paras = new HashMap<>();
        paras.put("phone", edtPhone.getText().toString());

        String pwdEnc = "";
        try {
            pwdEnc = DigestUtils.md5DigestAsHex(edtPwd.getText().toString().getBytes("utf-8"));
        } catch (UnsupportedEncodingException ex) {
        }

        paras.put("password", pwdEnc);
        paras.put("authcode", edtCode.getText().toString());
        HttpParaUtils utils = new HttpParaUtils();
        url = utils.getHttpGetUrl(url, paras);

        showProgressDialog("", "正在获取数据，请稍等...");

        GsonRequest<MJForget> forgetGsonRequest = new GsonRequest<MJForget>(
                Request.Method.GET,
                url,
                MJForget.class,
                null,
                forgetPasswordListener,
                errorListener
        );

        VolleyRequestManager.getRequestQueue().add(forgetGsonRequest);
    }

    Response.Listener<MJSendSMSModel> sendSMSListener = new Response.Listener<MJSendSMSModel>() {
        @Override
        public void onResponse(MJSendSMSModel sendSMSModel) {
            ForgetActivity.this.closeProgressDialog();

            if (sendSMSModel.getSystemResultCode() != 1) {
//               SimpleDialogFragment.createBuilder( ForgetActivity.this , ForgetActivity.this.getSupportFragmentManager())
//                       .setTitle("错误信息")
//                       .setMessage(sendSMSModel.getSystemResultDescription() )
//                       .setNegativeButtonText("关闭")
//                       .show();
                DialogUtils.showDialog(ForgetActivity.this, ForgetActivity.this.getSupportFragmentManager(), "错误信息", sendSMSModel.getSystemResultDescription(), "关闭");
                return;
            }
            if (sendSMSModel.getResultCode() != 1) {
//                SimpleDialogFragment.createBuilder( ForgetActivity.this , ForgetActivity.this.getSupportFragmentManager())
//                        .setTitle("错误信息")
//                        .setMessage(sendSMSModel.getResultDescription() )
//                        .setNegativeButtonText("关闭")
//                        .show();
                DialogUtils.showDialog(ForgetActivity.this, ForgetActivity.this.getSupportFragmentManager(), "错误信息", sendSMSModel.getResultDescription(), "关闭");
                return;
            }

            timeCountDownListener.setEnableVoice(sendSMSModel.getResultData().getVoiceAble());
        }
    };

    Response.Listener<MJForget> forgetPasswordListener = new Response.Listener<MJForget>() {
        @Override
        public void onResponse(MJForget MJForget) {
            ForgetActivity.this.closeProgressDialog();

            if (MJForget.getSystemResultCode() != 1) {
                SimpleDialogFragment.createBuilder(ForgetActivity.this, ForgetActivity.this.getSupportFragmentManager())
                        .setTitle("系统错误")
                        .setMessage(MJForget.getSystemResultDescription())
                        .setNegativeButtonText("关闭")
                        .show();
                return;
            }
            if (MJForget.getResultCode() != 1) {
                SimpleDialogFragment.createBuilder(ForgetActivity.this, ForgetActivity.this.getSupportFragmentManager())
                        .setTitle("系统错误")
                        .setMessage(MJForget.getResultDescription())
                        .setNegativeButtonText("关闭")
                        .show();
                return;
            }

            SimpleDialogFragment.createBuilder(ForgetActivity.this, ForgetActivity.this.getSupportFragmentManager())
                    .setTitle("找回密码")
                    .setMessage("找回密码成功")
                    .setNegativeButtonText("关闭")
                    .setRequestCode(REQUEST_CODE)
                    .show();
            return;
        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            ForgetActivity.this.closeProgressDialog();
            String message = "";
            if (null != volleyError.networkResponse) {
                message = new String(volleyError.networkResponse.data);
            }
            DialogUtils.showDialog(ForgetActivity.this, ForgetActivity.this.getSupportFragmentManager(), "错误信息", message, "关闭");
        }
    };

    @Override
    public void onNegativeButtonClicked(int i) {
        if (i == REQUEST_CODE) {
            this.finish();
        }
    }

    @Override
    public void onNeutralButtonClicked(int i) {

    }

    @Override
    public void onPositiveButtonClicked(int i) {

    }

//    public void onEvent(String enableVoice ){
//        Toast.makeText(this, "called",Toast.LENGTH_LONG).show();
//    }

    public class TimeCountDownListener implements CountDownTimerButton.CountDownFinishListener {
        public boolean getEnableVoice() {
            return enableVoice;
        }

        public void setEnableVoice(boolean enableVoice) {
            this.enableVoice = enableVoice;
        }

        private boolean enableVoice = false;

        @Override
        public void finish() {
            if (enableVoice == false) return;
            //刷新获取按钮状态，设置为可获取语音
            btnGet.setText("尝试语音获取");
            btnGet.setTag(Constant.SMS_TYPE_VOICE);
            //ToastUtils.showLongToast(ForgetActivity.this, "还没收到短信，请尝试语音获取");
            Toast.makeText(ForgetActivity.this, "还没收到短信，请尝试语音获取", Toast.LENGTH_LONG).show();
        }
    }
}




