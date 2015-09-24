package com.huotu.huobanmall.seller.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.huotu.android.library.libedittext.EditText;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.bean.HTMerchantModel;
import com.huotu.huobanmall.seller.common.Constant;
import com.huotu.huobanmall.seller.common.SellerApplication;
import com.huotu.huobanmall.seller.utils.ActivityUtils;
import com.huotu.huobanmall.seller.utils.DialogUtils;
import com.huotu.huobanmall.seller.utils.DigestUtils;
import com.huotu.huobanmall.seller.utils.GsonRequest;
import com.huotu.huobanmall.seller.utils.HttpParaUtils;
import com.huotu.huobanmall.seller.utils.ToastUtils;
import com.huotu.huobanmall.seller.utils.VolleyRequestManager;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


/**
 * @类名称：PswManagentActivity
 * @类描述：密码修改界面
 * @创建人：aaron
 * @修改人：
 * @修改时间：2015年6月10日 上午9:58:49
 * @修改备注：
 * @version:
 */
public class PswchangeActivity extends BaseFragmentActivity implements
        OnClickListener {
    private final int REQUEST_CODE= 3001;

    private TextView titleName;

    private EditText edtOld;// 旧密码

    private EditText edtNew;// 新密码

    private EditText edtNewRe;// 新密码确认

    private Button backImage;

    private TextView commit;

    private TextView forgetPsw;
    //返回文字事件
    private TextView backText;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        this.setContentView(R.layout.activity_changepsw);

        initView();
    }

    private void initView() {
        titleName = (TextView) this.findViewById(R.id.header_title);
        backImage = (Button) this.findViewById(R.id.header_back);
        commit =(TextView)this.findViewById(R.id.header_operate);
        forgetPsw = (TextView) this.findViewById(R.id.txtForget);
        edtOld = (EditText) this.findViewById(R.id.edtOld);
        edtNew = (EditText) this.findViewById(R.id.edtNew);
        edtNewRe = (EditText) this.findViewById(R.id.edtNewRes);
        commit.setText("保存");
        titleName.setText("修改密码");
        backImage.setOnClickListener(this);
        commit.setOnClickListener(this);
        forgetPsw.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        switch (v.getId()) {
            case R.id.header_operate: {

               if (canModifyPsw()==true){
                   modifyPassword();
               }

            }
            break;
            case R.id.txtForget: {

                ActivityUtils.getInstance().skipActivity(PswchangeActivity.this,
                        ForgetActivity.class);
                finish();
            }
            break;
            case R.id.header_back: {
                finish();
            }
            break;

            default:
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            // finish自身
            PswchangeActivity.this.finish();
            return true;
        }
        // TODO Auto-generated method stub
        return super.onKeyDown(keyCode, event);
    }

    /**
     * @throws
     * @方法描述：判断数据完整性
     * @方法名：canModifyPsw
     * @参数：@return
     * @返回：boolean
     */
    private boolean canModifyPsw() {
        if (TextUtils.isEmpty(edtOld.getText())) {
            edtOld.setError("请输入原密码！");
            return false;
        } else if (TextUtils.isEmpty(edtNew.getText())) {
            edtNew.setError("请设置新密码！");
            return false;
        } else if (TextUtils.isEmpty(edtNewRe.getText())) {
            edtNewRe.setError("请再输一次新密码！");
            return false;
        } else if (edtNew.getText().toString().trim().length() < 4) {
            edtNew.setError("设置的密码太简单了");
            return false;
        } else if (!edtNew.getText().toString()
                .equals(edtNewRe.getText().toString())) {
            edtNewRe.setError("两次输入的密码不一致！");
            return false;
        } else {
            return true;
        }
    }

    private void modifyPassword() {
        String url = Constant.MODIFYPSW_INTEFACE;
        Map<String, String> paras = new HashMap<>();
        String oldpwd = edtOld.getText().toString();
        String newpwd = edtNew.getText().toString();
        String newrepwd = edtNewRe.getText().toString();
        String oldpwdEnc = "";
        String newpwdEnc = "";
        String newrepwdEnc = "";
        try {
            oldpwdEnc = DigestUtils.md5DigestAsHex(edtOld.getText().toString().getBytes("utf-8"));
        } catch (UnsupportedEncodingException ex) {
        }
        try {
            newpwdEnc = DigestUtils.md5DigestAsHex(edtNew.getText().toString().getBytes("utf-8"));
        } catch (UnsupportedEncodingException ex) {
        }
        try {
            newrepwdEnc = DigestUtils.md5DigestAsHex(edtNewRe.getText().toString().getBytes("utf-8"));
        } catch (UnsupportedEncodingException ex) {
        }

        paras.put("oldPassword", oldpwdEnc);
        paras.put("newPassword", newpwdEnc);

        HttpParaUtils utils = new HttpParaUtils();
        url = utils.getHttpGetUrl(url, paras);

        GsonRequest<HTMerchantModel> loginRequest = new GsonRequest<HTMerchantModel>(
                Request.Method.GET,
                url,
                HTMerchantModel.class,
                null,
                modifyPasswordListener,
                errorListener
        );

        VolleyRequestManager.getRequestQueue().add(loginRequest);
    }
    Response.Listener<HTMerchantModel> modifyPasswordListener = new Response.Listener<HTMerchantModel>() {
        @Override
        public void onResponse(HTMerchantModel htMerchantModel ) {
            PswchangeActivity.this.closeProgressDialog();

            if(  htMerchantModel.getSystemResultCode() != 1 ) {
                SimpleDialogFragment.createBuilder(PswchangeActivity.this, PswchangeActivity.this.getSupportFragmentManager())
                        .setTitle("系统错误")
                        .setMessage(htMerchantModel.getSystemResultDescription())
                        .setNegativeButtonText("关闭")
                        .show();
                return;
            }
            if( htMerchantModel.getResultCode() !=1 ){
                SimpleDialogFragment.createBuilder(PswchangeActivity.this, PswchangeActivity.this.getSupportFragmentManager())
                        .setTitle("系统错误")
                        .setMessage(htMerchantModel.getResultDescription())
                        .setNegativeButtonText("关闭")
                        .show();
                return;
            }

            ToastUtils.showLong("修改密码成功");

            SellerApplication.getInstance().writeMerchantInfo( htMerchantModel.getResultData().getUser() );

            PswchangeActivity.this.finish();
        }
    };
    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            PswchangeActivity.this.closeProgressDialog();
            String message="";
            if( null != volleyError.networkResponse){
                message=new String( volleyError.networkResponse.data);
            }
            DialogUtils.showDialog(PswchangeActivity.this,PswchangeActivity.this.getSupportFragmentManager(),"错误信息",message,"关闭");
        }
    };

}
