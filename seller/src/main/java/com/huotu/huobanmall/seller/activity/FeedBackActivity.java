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

import com.huotu.huobanmall.seller.R;




import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 
 * @类名称：FeedBackActivity
 * @类描述：意见反馈界面
 * @创建人：aaron
 * @修改人：
 * @修改时间：2015年6月10日 上午9:57:54
 * @修改备注：
 * @version:
 */
public class FeedBackActivity extends BaseFragmentActivity implements Callback,
        OnClickListener
{
    @Bind(R.id.edtContent)
    EditText edtContent;
    @Bind(R.id.btnCommit)
    Button btnCommit;
    @Bind(R.id.header_title)
    TextView header_title;
    @Bind(R.id.header_back)
    Button header_back;
    Handler mHandler = new Handler(this);

    


    @Override
    //设置监听
    public void onClick(View v)
    {
        // TODO Auto-generated method stub

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
    }



    @Override
    public boolean handleMessage(Message msg)
    {
        // TODO Auto-generated method stub
        return false;
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

    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            // finish自身
            FeedBackActivity.this.finish();
            return true;
        }
        // TODO Auto-generated method stub
        return super.onKeyDown(keyCode, event);
    }
}
