package com.huotu.huobanmall.seller.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.bean.EditSetTypeEnum;
import com.huotu.huobanmall.seller.bean.HTMerchantModel;
import com.huotu.huobanmall.seller.bean.RefreshSettingEvent;
import com.huotu.huobanmall.seller.common.Constant;
import com.huotu.huobanmall.seller.common.SellerApplication;
import com.huotu.huobanmall.seller.utils.ActivityUtils;
import com.huotu.huobanmall.seller.utils.DialogUtils;
import com.huotu.huobanmall.seller.utils.GsonRequest;
import com.huotu.huobanmall.seller.utils.HttpParaUtils;
import com.huotu.huobanmall.seller.utils.PreferenceHelper;
import com.huotu.huobanmall.seller.utils.VolleyRequestManager;
import com.zcw.togglebutton.ToggleButton;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2015/8/31.
 */
public class PushActivity extends BaseFragmentActivity implements View.OnClickListener {

    // 界面名称
    @Bind(R.id.title)
    public TextView titleName;

    @Bind(R.id.backImage)
    public Button titleBack;

    @Bind(R.id.backtext)
    public TextView backtext;

    @Bind(R.id.RLnotice)
    public RelativeLayout RLnotice;

    @Bind(R.id.RLxiaohuoban)
    public RelativeLayout RLxiaohuoban;

    @Bind(R.id.RLmiandarao)
    public RelativeLayout RLmiandarao;

    @Bind(R.id.swit1)
    public com.zcw.togglebutton.ToggleButton swit1;

    @Bind(R.id.swit2)
    public com.zcw.togglebutton.ToggleButton swit2;

    @Bind(R.id.swit3)
    public com.zcw.togglebutton.ToggleButton swit3;

    @Bind(R.id.tip)
    public TextView tip;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_push);

        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleName.setText("推送设置");
        titleBack.setOnClickListener(this);
        backtext.setOnClickListener(this);
        swit1.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean b) {
                updateSetting( EditSetTypeEnum.BILLNOTICE , b);
            }
        });
        swit2.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean b) {
                updateSetting( EditSetTypeEnum.ADDHUOBANNOTICE , b);
            }
        });
        swit3.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean b) {
                updateSetting( EditSetTypeEnum.NIGHT , b);
            }
        });

        Integer billNotice = PreferenceHelper.readInt(this.getApplicationContext(), Constant.LOGIN_USER_INFO,
                Constant.LOGIN_AUTH_ENABLE_BILL_NOTICE, 0);
        Integer addfirstNotice= PreferenceHelper.readInt(this.getApplicationContext(), Constant.LOGIN_USER_INFO,
                Constant.LOGIN_AUTH_ENABLE_PARTNER_NOTICE, 0);
        Integer night = PreferenceHelper.readInt(this.getApplicationContext(),Constant.LOGIN_USER_INFO,
                Constant.LOGIN_AUTH_NO_DISTURBD,0);

        if( billNotice ==1 ){
            swit1.setToggleOn();
        }else{
            swit1.setToggleOff();
        }
        if(addfirstNotice ==1 ){
            swit2.setToggleOn();
        }else {
            swit2.setToggleOff();
        }
        if(night==1){
            swit3.setToggleOn();
        }else{
            swit3.setToggleOff();
        }
    }
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backtext:{
                finish();
            }
            break;
            case R.id.backImage:{
                finish();
            }
            break;
        }
    }


    protected void updateSetting( EditSetTypeEnum type , boolean value){
        String url = Constant.UPDATEPROFILE_INTERFACE;
        Map<String,String> paras =new HashMap<>();
        paras.put("profileType", String.valueOf( type.getIndex()));
        paras.put("profileData", value? "1":"0");
        HttpParaUtils httpParaUtils =new HttpParaUtils();
        Map<String,String > maps = httpParaUtils.getHttpPost(paras);
        GsonRequest<HTMerchantModel> request =new GsonRequest<HTMerchantModel>(
                Request.Method.POST,
                url,
                HTMerchantModel.class,
                null,
                maps,
                updateListener,
                errorListener
        );

        VolleyRequestManager.getRequestQueue().add(request);
    }

    Response.Listener<HTMerchantModel> updateListener = new Response.Listener<HTMerchantModel>() {
        @Override
        public void onResponse(HTMerchantModel htMerchantModel) {
            PushActivity.this.closeProgressDialog();
            if( null == htMerchantModel){
                DialogUtils.showDialog(PushActivity.this, PushActivity.this.getSupportFragmentManager(), "错误信息", "更新失败", "关闭");
                return;
            }
            if( htMerchantModel.getSystemResultCode()!=1){
                DialogUtils.showDialog(PushActivity.this,PushActivity.this.getSupportFragmentManager(),"错误信息",htMerchantModel.getSystemResultDescription(),"关闭");
                return;
            }
            if( htMerchantModel.getResultCode() == Constant.TOKEN_OVERDUE){
                ActivityUtils.getInstance().skipActivity(PushActivity.this,LoginActivity.class);
                return;
            }
            if( htMerchantModel.getResultCode() != 1){
                DialogUtils.showDialog(PushActivity.this,PushActivity.this.getSupportFragmentManager(),"错误信息",htMerchantModel.getResultDescription(),"关闭");
                return;
            }
            SellerApplication.getInstance().writeMerchantInfo(htMerchantModel.getResultData().getUser());
            //刷新界面数据
            EventBus.getDefault().post( new RefreshSettingEvent());
            //PushActivity.this.finish();
        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            PushActivity.this.closeProgressDialog();
            String message="";
            if( null != volleyError.networkResponse){
                message=new String( volleyError.networkResponse.data);
            }
            DialogUtils.showDialog(PushActivity.this, PushActivity.this.getSupportFragmentManager(), "错误信息", message, "关闭");
        }
    };
}
