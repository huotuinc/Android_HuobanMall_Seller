
package com.huotu.huobanmall.seller.activity;


import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.avast.android.dialogs.fragment.ProgressDialogFragment;
import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.huotu.huobanmall.seller.bean.MerchantModel;
import com.huotu.huobanmall.seller.common.Constants;
import com.huotu.android.library.libedittext.EditText;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.utils.ActivityUtils;
import com.huotu.huobanmall.seller.utils.DigestUtils;
import com.huotu.huobanmall.seller.utils.GsonRequest;
import com.huotu.huobanmall.seller.utils.HttpParaUtils;
import com.huotu.huobanmall.seller.utils.VolleyRequestManager;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


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
    private void forgetPassword() {
        ProgressDialogFragment.ProgressDialogBuilder builder = ProgressDialogFragment.createBuilder(this, getSupportFragmentManager())
        .setMessage("正在修改密码，请稍等...")
                .setCancelable(false)
                .setCancelableOnTouchOutside(false);
        progressDialog = (ProgressDialogFragment) builder.show();
        String url = Constants.FORGET_INTERFACE;
        HttpParaUtils httpUtils = new HttpParaUtils();
        Map<String,String> paras = new HashMap<>();

        String pwd = edtPwd.getText().toString();
        String pwdEncy="";
        try {
            pwdEncy = DigestUtils.md5DigestAsHex(pwd.getBytes("utf-8"));
        }catch (UnsupportedEncodingException ex){

        }

        paras.put("username","test");
        paras.put("password", pwdEncy);
        url = httpUtils.getHttpGetUrl(url,paras);

        GsonRequest<MerchantModel> forgetPasswordRequest = new GsonRequest<MerchantModel>(
                Request.Method.GET
                ,url
                ,MerchantModel.class
                ,null
                ,forgetPasswordListener
                ,errorListener
        );

        VolleyRequestManager.getRequestQueue().add(forgetPasswordRequest);
    }

    Response.Listener forgetPasswordListener = new Response.Listener() {
        @Override
        public void onResponse(Object o) {


        }
    };

    Response.ErrorListener errorListener=new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            if(progressDialog!=null){
                progressDialog.dismiss();
            }
            SimpleDialogFragment.createBuilder(ForgetActivity.this, ForgetActivity.this.getSupportFragmentManager())
                    .setTitle("错误信息")
                    .setMessage( volleyError.getMessage())
                    .setNegativeButtonText("关闭")
                    .show();
        }
    };
}




