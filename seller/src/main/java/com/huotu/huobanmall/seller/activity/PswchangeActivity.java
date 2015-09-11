package com.huotu.huobanmall.seller.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.JsonSyntaxException;
import com.huotu.android.library.libedittext.EditText;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.common.Constant;
import com.huotu.huobanmall.seller.utils.ActivityUtils;
import com.huotu.huobanmall.seller.utils.HttpUtil;
import com.huotu.huobanmall.seller.utils.ObtainParamsMap;
import com.huotu.huobanmall.seller.utils.ToastUtils;

import java.util.Map;



/**
 * 
 * @类名称：PswManagentActivity
 * @类描述：密码修改界面
 * @创建人：aaron
 * @修改人：
 * @修改时间：2015年6月10日 上午9:58:49
 * @修改备注：
 * @version:
 */
public class PswchangeActivity extends BaseFragmentActivity implements
        OnClickListener
{


    private TextView titleName;

    private EditText edtOld;// 旧密码

    private EditText edtNew;// 新密码

    private EditText edtNewRe;// 新密码确认

    private Button backImage;

    //private NoticeDialog noticeDialog;

    private TextView commit;

    private TextView forgetPsw;
    //返回文字事件
    private TextView backText;

    @Override
    protected void onCreate(Bundle arg0)
    {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        this.setContentView(R.layout.activity_changepsw);

        initView();
    }

    private void initView()
    {
        titleName = (TextView) this.findViewById(R.id.title);
        backImage = (Button) this.findViewById(R.id.backImage);
        commit = (TextView) this.findViewById(R.id.forgetpsw);
        forgetPsw = (TextView) this.findViewById(R.id.txtForget);

        edtOld = (EditText) this.findViewById(R.id.edtOld);
        edtNew = (EditText) this.findViewById(R.id.edtNew);
        edtNewRe = (EditText) this.findViewById(R.id.edtNewRes);
        commit.setText("保存");
        titleName.setText("修改密码");
        backImage.setOnClickListener(this);
        commit.setOnClickListener(this);
        forgetPsw.setOnClickListener(this);
        backText = (TextView) this.findViewById(R.id.backtext);
        backText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub

        switch (v.getId())
        {
        case R.id.backtext:
        {

           finish();
        }
            break;
        case R.id.forgetpsw:
        {

            //saveNewPsw();
        }
            break;
        case R.id.txtForget:
        {

            ActivityUtils.getInstance().skipActivity(PswchangeActivity.this,
                    ForgetActivity.class);
            finish();
        }
            break;
        case R.id.backImage:
        {
            finish();
        }
            break;

        default:
            break;
        }
    }



//    private void saveNewPsw()
//    {
//        // 判断数据完整性
//        if (canModifyPsw())
//        {
//            String url = Constant.MODIFYPSW_INTEFACE;
//           // new ModifyPswAsyncTask().execute(url);
//        }
//    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            // finish自身
            PswchangeActivity.this.finish();
            return true;
        }
        // TODO Auto-generated method stub
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 
     * @方法描述：判断数据完整性
     * @方法名：canModifyPsw
     * @参数：@return
     * @返回：boolean
     * @exception
     * @since
     */
    private boolean canModifyPsw()
    {
        if (TextUtils.isEmpty(edtOld.getText()))
        {
            edtOld.setError("请输入原密码！");
            return false;
        } else if (TextUtils.isEmpty(edtNew.getText()))
        {
            edtNew.setError("请设置新密码！");
            return false;
        } else if (TextUtils.isEmpty(edtNewRe.getText()))
        {
            edtNewRe.setError("请再输一次新密码！");
            return false;
        }else if( edtNew.getText().toString().trim().length() <4 ){
            edtNew.setError("设置的密码太简单了");
            return false;
        }        
        else if (!edtNew.getText().toString()
                .equals(edtNewRe.getText().toString()))
        {
            edtNewRe.setError("两次输入的密码不一致！");
            return false;
        } else
        {
            return true;
        }
    }

    //class ModifyPswAsyncTask extends AsyncTask<String, Void, FMModifyPsw>
//    {
//
//        Map<String, String> paramMap = null;
//
//
//        @Override
//        protected FMModifyPsw doInBackground(String... params)
//        {
//            // TODO Auto-generated method stub
//            FMModifyPsw modifyPswBean = new FMModifyPsw();
//            JSONUtil<FMModifyPsw> jsonUtil = new JSONUtil<FMModifyPsw>();
//            String url = params[0];
//            String json =  HttpUtil.getInstance().doPost(url, paramMap);
//            try
//            {
//                modifyPswBean =  jsonUtil.toBean(json, modifyPswBean);
//            }
//            catch(JsonSyntaxException e)
//            {
//               Log.e("JSON_ERROR", e.getMessage());
//               modifyPswBean.setResultCode(0);
//               modifyPswBean.setResultDescription("解析json出错");
//            }
//            return modifyPswBean;
//        }
//
//        @Override
//        protected void onPreExecute()
//        {
//            // TODO Auto-generated method stub
//            super.onPreExecute();
//            // 封转参数
//            ObtainParamsMap obtainMap = new ObtainParamsMap(
//                    PswchangeActivity.this);
//            paramMap = obtainMap.obtainMap();
//
//            // 注册是POST提交
//            paramMap.put(
//                    "password",
//                    EncryptUtil.getInstance().encryptMd532(
//                            edtOld.getText().toString()));
//            paramMap.put(
//                    "newPassword",
//                    EncryptUtil.getInstance().encryptMd532(
//                            edtNew.getText().toString()));
//            // 封装sign
//            String signStr = obtainMap.getSign(paramMap);
//            paramMap.put("sign", signStr);
//        }
//
//        @Override
//        protected void onPostExecute(FMModifyPsw result)
//        {
//            // TODO Auto-generated method stub
//            super.onPostExecute(result);
//
//            if (1 == result.getResultCode())
//            {
//
//                // 弹出注册成功提示框
//                noticeDialog = new NoticeDialog(PswchangeActivity.this,
//                        R.style.NoticeDialog, "修改密码", "修改成功",
//                        new NoticeDialog.LeaveMyDialogListener()
//                        {
//
//                            @Override
//                            public void onClick(View view)
//                            {
//                                // TODO Auto-generated method stub
//                                noticeDialog.dismiss();
//                                noticeDialog = null;
//                                finish();
//                            }
//                        });
//                noticeDialog.show();
//            }
//            else if (Constant.TOKEN_OVERDUE == result.getResultCode())
//            {
//                // 提示账号异地登陆，强制用户退出
//                // 并跳转到登录界面
//                ToastUtils.showLongToast(PswchangeActivity.this, "账户登录过期，请重新登录");
//                Handler mHandler = new Handler();
//                mHandler.postDelayed(new Runnable()
//                {
//
//                    @Override
//                    public void run()
//                    {
//                        // TODO Auto-generated method stub
//                        ActivityUtils.getInstance().LoginActivity((Activity) PswchangeActivity.this);
//                    }
//                }, 2000);
//            }
//            else
//            {
//                // 弹出注册失败提示框
//                noticeDialog = new NoticeDialog(PswchangeActivity.this,
//                        R.style.NoticeDialog, "修改密码", "修改失败",
//                        new NoticeDialog.LeaveMyDialogListener()
//                        {
//
//                            @Override
//                            public void onClick(View view)
//                            {
//                                // TODO Auto-generated method stub
//                                noticeDialog.dismiss();
//                                noticeDialog = null;
//                            }
//                        });
//                noticeDialog.show();
//            }
//        }
//
//    }
}
