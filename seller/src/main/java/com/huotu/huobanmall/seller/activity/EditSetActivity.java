package com.huotu.huobanmall.seller.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;;
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
import com.huotu.huobanmall.seller.utils.VolleyRequestManager;

import java.util.HashMap;
import java.util.Map;


import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public class EditSetActivity extends BaseFragmentActivity {
    @Bind(R.id.ET)
    public EditText ET;
    @Bind(R.id.header_title)
    public TextView header_title;
    @Bind(R.id.header_back)
    public Button header_back;
    @Bind(R.id.header_operate)
    public TextView header_operate;

    protected EditSetTypeEnum typeEnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_set);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        header_operate.setText("保存");
        header_title.setText("修改");

        if (getIntent().getExtras() == null) return;


        int temp = getIntent().getIntExtra("type", 1);
        //String tempName = EditSetTypeEnum.getName(temp);
        typeEnum = EditSetTypeEnum.valueOf(temp);
        header_back.setOnClickListener(this);
        header_operate.setOnClickListener(this);
        String context = getIntent().getExtras().getString("text");
        ET.setText(context);
    }

    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    protected void commit() {
        if (ET.getText().toString().trim() == "") {
            return;
        }
        String context = ET.getText().toString().trim();
        String url = Constant.UPDATEPROFILE_INTERFACE;
        Map<String, String> paras = new HashMap<>();
        paras.put("profileType", String.valueOf(typeEnum.getIndex()));
        paras.put("profileData", context);

        HttpParaUtils httpParaUtils = new HttpParaUtils();
        Map<String, String> maps = httpParaUtils.getHttpPost(paras);
        GsonRequest<HTMerchantModel> updateRequest = new GsonRequest<>(
                Request.Method.POST,
                url,
                HTMerchantModel.class,
                null,
                maps,
                updateListener,
                new MJErrorListener(this)
        );

        this.showProgressDialog("", "正在更新数据，请稍等...");

        VolleyRequestManager.getRequestQueue().add(updateRequest);
    }

    Response.Listener<HTMerchantModel> updateListener = new Response.Listener<HTMerchantModel>() {
        @Override
        public void onResponse(HTMerchantModel htMerchantModel) {
            EditSetActivity.this.closeProgressDialog();
            if (null == htMerchantModel) {
                DialogUtils.showDialog(EditSetActivity.this, EditSetActivity.this.getSupportFragmentManager(), "错误信息", "更新失败", "关闭");
                return;
            }
            if (htMerchantModel.getSystemResultCode() != 1) {
                DialogUtils.showDialog(EditSetActivity.this, EditSetActivity.this.getSupportFragmentManager(), "错误信息", htMerchantModel.getSystemResultDescription(), "关闭");
                return;
            }
            if (htMerchantModel.getResultCode() == Constant.TOKEN_OVERDUE) {
                ActivityUtils.getInstance().skipActivity(EditSetActivity.this, LoginActivity.class);
                return;
            }
            if (htMerchantModel.getResultCode() != 1) {
                DialogUtils.showDialog(EditSetActivity.this, EditSetActivity.this.getSupportFragmentManager(), "错误信息", htMerchantModel.getResultDescription(), "关闭");
                return;
            }

            SellerApplication.getInstance().writeMerchantInfo(htMerchantModel.getResultData().getUser());
            //刷新界面数据
            EventBus.getDefault().post(new RefreshSettingEvent());
            EditSetActivity.this.finish();
        }
    };

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_back: {
                commit();
            }
            break;
            case R.id.header_operate: {
                commit();
            }
            break;



            default:
                break;
        }
    }

}
