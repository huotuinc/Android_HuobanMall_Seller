
package com.huotu.huobanmall.seller.activity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.avast.android.dialogs.fragment.ProgressDialogFragment;
import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.huotu.huobanmall.seller.bean.HTMerchantModel;
import com.huotu.huobanmall.seller.common.Constant;
import com.huotu.android.library.libedittext.EditText;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.utils.ActivityUtils;
import com.huotu.huobanmall.seller.utils.EncryptUtil;
import com.huotu.huobanmall.seller.utils.HttpParaUtils;
import com.huotu.huobanmall.seller.utils.JSONUtil;
import com.huotu.huobanmall.seller.utils.PreferenceHelper;
import com.huotu.huobanmall.seller.utils.VolleyRequestManager;
import org.json.JSONObject;
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

public class ForgetActivity extends BaseFragmentActivity implements OnClickListener {

    private TextView titleName;

    private EditText edtPhone;

    private EditText edtCode;

    private TextView btnGet;

    private EditText edtPwd;

    private EditText edtRePwd;

    private Button btnComplete;
    //返回文字事件
    private TextView backText;

    public ProgressDialogFragment progressDialog;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_forget);
        initView();
    }


    private void initView() {
        titleName = (TextView) this.findViewById(R.id.title);
        titleName.setText("用户忘记密码");
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtCode = (EditText) this.findViewById(R.id.edtCode);
        btnGet = (TextView) this.findViewById(R.id.btnGet);
        btnGet.setTag(Constant.SMS_TYPE_TEXT);
        btnGet.setText("获取验证码");
        edtPwd = (EditText) this.findViewById(R.id.edtPwd);
        edtRePwd = (EditText) this.findViewById(R.id.edtRePwd);
        btnComplete = (Button) this.findViewById(R.id.btnComplete);
        backText = (TextView) this.findViewById(R.id.backtext);
        backText.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backtext: {
                finish();

            }
            break;
            case R.id.btnComplete: {
                forgetPassword();

            }
            break;
            case R.id.btnGet: {
                //sendSMS();

            }
            break;

        }


    }




    private void forgetPassword() {
        ProgressDialogFragment.ProgressDialogBuilder builder = ProgressDialogFragment.createBuilder(this, getSupportFragmentManager())
                .setMessage("正在修改密码，请稍等...")
                .setCancelable(false)
                .setCancelableOnTouchOutside(false);
        progressDialog = (ProgressDialogFragment) builder.show();
        String url = Constant.FORGET_INTERFACE;
        HttpParaUtils httpUtils = new HttpParaUtils();
        Map<String, String> paras = new HashMap<>();

        String pwd = edtPwd.getText().toString();
        String pwdEncy = "";
        pwdEncy = EncryptUtil.getInstance().encryptMd532(pwd);
        String repwd =edtRePwd.getText().toString();
        String repwdEncy="";
        repwdEncy = EncryptUtil.getInstance().encryptMd532(repwd);

        paras.put("phone", edtPhone.getText().toString());
        paras.put("edCode",edtCode.getText().toString());
        paras.put("password", pwdEncy);
        paras.put("repassword",repwdEncy);
        url = httpUtils.getHttpGetUrl(url, paras);


        VolleyRequestManager.getRequestQueue().add(new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        HTMerchantModel htMerchantModel = new HTMerchantModel();
                        JSONUtil<HTMerchantModel> jsonStr = new JSONUtil<HTMerchantModel>();
                        htMerchantModel = jsonStr.toBean(response.toString(), htMerchantModel);
                        String token = htMerchantModel.getResultData().getUser().getToken();

                        //记录token
                        PreferenceHelper.writeString(ForgetActivity.this, "loginInfo", "login_token", token);
                        ActivityUtils.getInstance().showActivity(ForgetActivity.this, MainActivity.class);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String err = error.getMessage();
                Log.e("", "err : " + err);
            }
        }));

    }

    Response.Listener forgetPasswordListener = new Response.Listener() {
        @Override
        public void onResponse(Object o) {


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
}




