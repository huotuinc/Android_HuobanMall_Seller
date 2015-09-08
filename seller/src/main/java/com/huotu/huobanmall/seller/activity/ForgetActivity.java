
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
import com.huotu.huobanmall.seller.bean.HTForget;
import com.huotu.huobanmall.seller.bean.HTMerchantModel;
import com.huotu.huobanmall.seller.bean.MerchantModel;
import com.huotu.huobanmall.seller.common.Constant;
import com.huotu.android.library.libedittext.EditText;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.common.SellerApplication;
import com.huotu.huobanmall.seller.utils.ActivityUtils;
import com.huotu.huobanmall.seller.utils.EncryptUtil;
import com.huotu.huobanmall.seller.utils.HttpParaUtils;
import com.huotu.huobanmall.seller.utils.JSONUtil;
import com.huotu.huobanmall.seller.utils.KJLoger;
import com.huotu.huobanmall.seller.utils.ObtainParamsMap;
import com.huotu.huobanmall.seller.utils.PreferenceHelper;
import com.huotu.huobanmall.seller.utils.VolleyRequestManager;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

    public
    SellerApplication application;

    public ProgressDialogFragment progressDialog;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = ( SellerApplication ) ForgetActivity.this.getApplication ();
        this.setContentView(R.layout.activity_forget);
        initView ( );
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
        ObtainParamsMap obtainMap = new ObtainParamsMap(ForgetActivity.this);
        String url = Constant.GET_VD_INTERFACE;
        try
        {
            url = Constant.GET_VD_INTERFACE + "?phone="
                         + URLEncoder.encode(edtPhone.getText().toString(), "UTF-8")
                         + "&type="
                         + URLEncoder.encode(Constant.GET_VD_TYPE_FORGET, "UTF-8")
                         + "&codeType=" + btnGet.getTag();
            url += obtainMap.getMap();
            //封装sign
            Map<String, String> signMap = new HashMap<String, String>();
            signMap.put("phone", edtPhone.getText().toString());
            signMap.put("type", Constant.GET_VD_TYPE_FORGET);
            signMap.put("codeType", (String) btnGet.getTag());
            String signStr = obtainMap.getSign(signMap);
            url += "&sign=" + signStr;
        } catch (UnsupportedEncodingException e)
        {
            // TODO Auto-generated catch block
            KJLoger.errorLog(e.getMessage());
        }

        VolleyRequestManager.getRequestQueue().add(new JsonObjectRequest(Request.Method.GET, url, null,
                                                                         new Response.Listener<JSONObject>() {
                                                                             @Override
                                                                             public void onResponse(JSONObject response) {
                                                                                 HTForget forget = new HTForget();
                                                                                 JSONUtil<HTForget> jsonStr = new JSONUtil<HTForget>();
                                                                                 forget = jsonStr.toBean(response.toString(), forget);

                                                                             }
                                                                         }, new Response.ErrorListener() {
                                                       @Override
                                                       public void onErrorResponse(VolleyError error) {
                                                           if(progressDialog!=null){
                                                               progressDialog.dismiss();
                                                           }
                                                           SimpleDialogFragment.createBuilder( ForgetActivity.this , ForgetActivity.this.getSupportFragmentManager())
                                                                               .setTitle("错误信息")
                                                                               .setMessage( error.getMessage())
                                                                               .setNegativeButtonText("关闭")
                                                                               .show ( );
                                                       }
                                                   }));
    }

    private void forgetPassword() {

        ObtainParamsMap obtainMap = new ObtainParamsMap(ForgetActivity.this);
        String url = Constant.FORGET_INTERFACE;

        try
        {
            url = url
                  + "?phone="
                  + URLEncoder.encode(edtPhone.getText().toString(),
                                      "UTF-8")
                  + "&password="
                  + URLEncoder.encode(EncryptUtil.getInstance()
                                                 .encryptMd532(edtPwd.getText().toString()),
                                      "UTF-8") + "&authcode=" + URLEncoder.encode(edtCode.getText().toString(),
                                                                                  "UTF-8");
            String paramMap = obtainMap.getMap();
            // 忘记密码是get方式提交
            //封装sign
            Map<String, String> signMap = new HashMap<String, String>();
            signMap.put("phone", edtPhone.getText().toString());
            signMap.put("password", EncryptUtil.getInstance().encryptMd532(edtPwd.getText().toString()));
            signMap.put("authcode", edtCode.getText().toString());
            String signStr = obtainMap.getSign(signMap);
            url += paramMap;
            url += "&sign=" + URLEncoder.encode(signStr, "UTF-8");

        } catch (UnsupportedEncodingException e)
        {
            // TODO Auto-generated catch block
            KJLoger.errorLog(e.getMessage());
        }

        VolleyRequestManager.getRequestQueue().add(new JsonObjectRequest(Request.Method.GET, url, null,
                                                                         new Response.Listener<JSONObject>() {
                                                                             @Override
                                                                             public void onResponse(JSONObject response) {
                                                                                 HTForget forget = new HTForget();
                                                                                 JSONUtil<HTForget> jsonStr = new JSONUtil<HTForget>();
                                                                                 forget = jsonStr.toBean(response.toString(), forget);

                                                                             }
                                                                         }, new Response.ErrorListener() {
                                                       @Override
                                                       public void onErrorResponse(VolleyError error) {
                                                           if(progressDialog!=null){
                                                               progressDialog.dismiss();
                                                           }
                                                           SimpleDialogFragment.createBuilder( ForgetActivity.this , ForgetActivity.this.getSupportFragmentManager())
                                                                               .setTitle("错误信息")
                                                                               .setMessage( error.getMessage())
                                                                               .setNegativeButtonText("关闭")
                                                                               .show ( );
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




