package com.huotu.huobanmall.seller.activity;


import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.avast.android.dialogs.iface.ISimpleDialogListener;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.bean.MJFeedbackModel;
import com.huotu.huobanmall.seller.common.Constant;
import com.huotu.huobanmall.seller.utils.ActivityUtils;
import com.huotu.huobanmall.seller.utils.DialogUtils;
import com.huotu.huobanmall.seller.utils.GsonRequest;
import com.huotu.huobanmall.seller.utils.HttpParaUtils;
import com.huotu.huobanmall.seller.utils.PreferenceHelper;
import com.huotu.huobanmall.seller.utils.VolleyRequestManager;


import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 
 * @类名称：FeedBackActivity
 * @类描述：意见反馈界面
 * @创建人：
 * @修改人：
 * @修改时间：2015年6月10日 上午9:57:54
 * @修改备注：
 * @version:
 */
public class FeedBackActivity extends BaseFragmentActivity implements OnClickListener , ISimpleDialogListener{
    @Bind(R.id.edtContent)
    EditText edtContent;
    @Bind(R.id.btnCommit)
    Button btnCommit;
    @Bind(R.id.header_title)
    TextView header_title;
    @Bind(R.id.header_back)
    Button header_back;
    //Handler mHandler = new Handler(this);
    protected final int REQUESTCODE_CLOSE=3333;

    @Override
    //设置监听
    public void onClick(View v)
    {
        switch (v.getId())
        {
        //提交反馈
        case R.id.btnCommit:
        {
            if(canCommit())
            {
                this.doCommit();
            }
            else
            {
                edtContent.setError("反馈意见不能为空");
            }
        }
            break;
        case R.id.header_back:
        {
            this.finish();
        }
            break;

        default:
            break;
        }
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.feedback);

        ButterKnife.bind(this);
        initView();
    }

    private void initView()
    {
       header_title.setText("意见反馈");
       header_back.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
    }



    private boolean canCommit()
    {
        if(TextUtils.isEmpty(edtContent.getText()))
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    
    private void doCommit()
    {
        if( false == canConnect()){
            this.closeProgressDialog();
            return;
        }

        String url = Constant.FEEDBACK_INTERFACE;
        Map<String,String> maps = new HashMap<>();
        String username = PreferenceHelper.readString( FeedBackActivity.this , Constant.LOGIN_USER_INFO , Constant.LOGIN_AUTH_NAME ,"" );
        String contact = PreferenceHelper.readString( FeedBackActivity.this , Constant.LOGIN_USER_INFO , Constant.LOGIN_AUTH_MOBILE , "");
        String context = edtContent.getText().toString().trim();

        maps.put("name" , username);
        maps.put("contact", contact);
        maps.put("content",context);
        HttpParaUtils httpParaUtils =new HttpParaUtils();
        Map<String ,String > paras = httpParaUtils.getHttpPost(maps);

        GsonRequest<MJFeedbackModel> request = new GsonRequest<MJFeedbackModel>(
                Request.Method.POST,
                url ,
                MJFeedbackModel.class,
                null,
                paras,
                listener,
                new MJErrorListener(this)
        );

        this.showProgressDialog("", "正在提交数据，请稍等...");

        VolleyRequestManager.AddRequest(request);
    }

    Response.Listener<MJFeedbackModel> listener =new Response.Listener<MJFeedbackModel>() {
        @Override
        public void onResponse(MJFeedbackModel mjFeedbackModel) {
           if( FeedBackActivity.this.isFinishing() ) return;
            FeedBackActivity.this.closeProgressDialog();
            if(!validateData(mjFeedbackModel)) {return;}
            SimpleDialogFragment.createBuilder( FeedBackActivity.this,
                    FeedBackActivity.this.getSupportFragmentManager())
                    .setTitle("意见反馈")
                    .setMessage("反馈成功")
                    .setNegativeButtonText("关闭")
                    .setRequestCode( REQUESTCODE_CLOSE).show();
        }
    };
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            FeedBackActivity.this.finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onNegativeButtonClicked(int i) {
        if( i == REQUESTCODE_CLOSE ){
            FeedBackActivity.this.finish();
        }
    }

    @Override
    public void onNeutralButtonClicked(int i) {

    }

    @Override
    public void onPositiveButtonClicked(int i) {

    }
}
