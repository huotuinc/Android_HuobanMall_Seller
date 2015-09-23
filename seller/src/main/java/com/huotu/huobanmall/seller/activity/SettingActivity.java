package com.huotu.huobanmall.seller.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.NetworkImageView;
import com.google.gson.JsonSyntaxException;
import com.huotu.huobanmall.seller.R;
import com.huotu.huobanmall.seller.bean.CloseEvnt;
import com.huotu.huobanmall.seller.bean.EditSetTypeEnum;
import com.huotu.huobanmall.seller.bean.HTMerchantModel;

import com.huotu.huobanmall.seller.bean.RefreshSettingEvent;
import com.huotu.huobanmall.seller.utils.DialogUtils;
import com.huotu.huobanmall.seller.utils.GsonRequest;
import com.huotu.huobanmall.seller.utils.HttpParaUtils;
import com.huotu.huobanmall.seller.utils.HttpUtil;
import com.huotu.huobanmall.seller.utils.JSONUtil;
import com.huotu.huobanmall.seller.utils.ObtainParamsMap;
import com.huotu.huobanmall.seller.utils.Util;

import com.huotu.huobanmall.seller.common.Constant;
import com.huotu.huobanmall.seller.common.SellerApplication;
import com.huotu.huobanmall.seller.utils.ActivityUtils;
import com.huotu.huobanmall.seller.utils.BitmapLoader;
import com.huotu.huobanmall.seller.utils.PreferenceHelper;
import com.huotu.huobanmall.seller.utils.ToastUtils;
import com.huotu.huobanmall.seller.utils.VolleyRequestManager;
import com.huotu.huobanmall.seller.widget.PhotoSelectView;
import com.huotu.huobanmall.seller.widget.PhotoSelectView.OnPhotoSelectBackListener;
import com.huotu.huobanmall.seller.widget.PhotoSelectView.SelectType;
import com.huotu.huobanmall.seller.widget.CropperView;
import com.huotu.huobanmall.seller.widget.CropperView.OnCropperBackListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2015/8/31.
 * 设置界面
 */
public class SettingActivity extends BaseFragmentActivity
        implements View.OnClickListener,OnPhotoSelectBackListener, OnCropperBackListener {

    public SellerApplication application;
    @Bind(R.id.header_title)
    public TextView header_title;
    @Bind(R.id.header_back)
    public Button header_back;

    @Bind(R.id.shopNameLabel)
    public LinearLayout shopNameLabel;

    @Bind(R.id.shopdescriptionLabel)
    public LinearLayout shopdescriptionLabel;

    @Bind(R.id.imgLabel)
    public LinearLayout imgLabel;

    @Bind(R.id.idLabel)
    public LinearLayout idLabel;

    @Bind(R.id.nameLabel)
    public LinearLayout nameLabel;

    @Bind(R.id.changePswLabel)
    public LinearLayout changePswLabel;

    @Bind(R.id.pushLabel)
    public LinearLayout pushLabel;

    @Bind(R.id.aboutusLabel)
    public LinearLayout aboutusLabel;

    @Bind(R.id.helpLabel)
    public LinearLayout helpLabel;

    @Bind(R.id.FeedbackLabel)
    public LinearLayout FeedbackLabel;

    @Bind(R.id.quit)
    public Button quitbtn;
    @Bind(R.id.shopName)
    TextView shopName;
    @Bind(R.id.Shopdescription)
    TextView ShopDescription;
    @Bind(R.id.logo)
    NetworkImageView logo;
    @Bind(R.id.txtId)
    TextView tvUserId;
    @Bind(R.id.txtName)
    TextView tvNickName;
    private PhotoSelectView pop;
    private CropperView cropperView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setting);
        application = ( SellerApplication ) SettingActivity.this.getApplication ();
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        pushLabel.setOnClickListener(this);
        header_title.setText("用户设置");
        header_back.setOnClickListener(this);
        quitbtn.setOnClickListener(this);

        if( EventBus.getDefault().isRegistered(this)==false) {
            EventBus.getDefault().register(this);
        }

       setData();
    }

    protected void setData(){
        String shopNameStr = PreferenceHelper.readString( application , Constant.LOGIN_USER_INFO , Constant.LOGIN_AUTH_SHOPNAME );
        shopName.setText(shopNameStr);
        String shopDescription = PreferenceHelper.readString(application , Constant.LOGIN_USER_INFO, Constant.LOGIN_AUTH_DISCRIPTION);
        ShopDescription.setText(shopDescription);
        String logoUrl = PreferenceHelper.readString(application, Constant.LOGIN_USER_INFO , Constant.LOGIN_AUTH_LOGO);
        BitmapLoader.create().displayUrl(SettingActivity.this, logo, logoUrl);
        String userId = PreferenceHelper.readString(application, Constant.LOGIN_USER_INFO,Constant.LOGIN_AUTH_MOBILE);
        tvUserId.setText(userId);
        String nickname=PreferenceHelper.readString(application,Constant.LOGIN_USER_INFO , Constant.LOGIN_AUTH_NICKNAME);
        tvNickName.setText(nickname);
    }

    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);

        if( true == EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgLabel:{
                if (null == pop)
                    pop = new PhotoSelectView(this, this);
                pop.show();
            }
            break;
            case R.id.shopNameLabel:{
                Bundle bd = new Bundle();
                bd.putInt("type", EditSetTypeEnum.SHOPNAME.getIndex() );
                bd.putString("text", shopName.getText().toString());
                ActivityUtils.getInstance().showActivity(SettingActivity.this, EditSetActivity.class , bd);
            }
            break;
            case R.id.shopdescriptionLabel:{
                Bundle bd = new Bundle();
                bd.putInt("type", EditSetTypeEnum.SHOPDESCRIPTION.getIndex());
                bd.putString("text", ShopDescription.getText().toString());
                ActivityUtils.getInstance().showActivity(SettingActivity.this, EditSetActivity.class,bd);
            }
            break;
            case R.id.nameLabel:{
                Bundle bd = new Bundle();
                bd.putInt("type", EditSetTypeEnum.NICKNAME.getIndex());
                bd.putString("text", tvNickName.getText().toString());
                ActivityUtils.getInstance().showActivity(SettingActivity.this, EditSetActivity.class,bd );
            }
            break;

            case R.id.changePswLabel: {
                ActivityUtils.getInstance().showActivity ( SettingActivity.this, PswchangeActivity.class );
            }
            break;
            case R.id.pushLabel: {
                ActivityUtils.getInstance().showActivity(SettingActivity.this, PushActivity.class);
            }
            break;
            case R.id.header_back: {
                finish();
            }
            break;

            case R.id.aboutusLabel: {
                String url = PreferenceHelper.readString(this.getApplicationContext(), Constant.LOGIN_GLOBAL_INFO,Constant.LOGIN_GLOBAL_ABOUTURL);
                Intent intent=new Intent();
                intent.setClass(SettingActivity.this,WebViewActivity.class);
                intent.putExtra(Constant.Extra_Url, url);
                ActivityUtils.getInstance().showActivity(SettingActivity.this, intent );
            }
            break;
            case R.id.helpLabel: {
                String url = PreferenceHelper.readString(this.getApplicationContext(), Constant.LOGIN_GLOBAL_INFO,Constant.LOGIN_GLOBAL_HELPURL);
                Intent intent=new Intent();
                intent.setClass(SettingActivity.this,WebViewActivity.class);
                intent.putExtra(Constant.Extra_Url, url);
                 ActivityUtils.getInstance().showActivity(SettingActivity.this, intent );

            }
            break;
            case R.id.FeedbackLabel: {
                String url = PreferenceHelper.readString(this.getApplicationContext(), Constant.LOGIN_GLOBAL_INFO,Constant.LOGIN_GLOBAL_HELPURL);
                Intent intent=new Intent();
                intent.setClass(SettingActivity.this,WebViewActivity.class);
                intent.putExtra(Constant.Extra_Url, url);
                ActivityUtils.getInstance().showActivity(SettingActivity.this, intent );
            }
            break;
            case R.id.quit: {
                 //ActivityUtils.getInstance().showActivity(SettingActivity.this, MainActivity.class);

                //清空token等用户信息
                application.cleanMerchantInfo ();
                //关闭MainActivity 界面,防止 按 返回建，回到 mainActivity界面
                EventBus.getDefault().post(new CloseEvnt());
                //跳转到
                ActivityUtils.getInstance ().skipActivity ( SettingActivity.this, LoginActivity.class );
            }
            break;
        }

    }
    @Override
    public void onPhotoSelectBack(SelectType type) {
        // TODO Auto-generated method stub
        if (null == type)
            return;
        getPhotoByType(type);
    }

    private void getPhotoByType(SelectType type) {
        switch (type) {
            case Camera:
                getPhotoByCamera();
                break;
            case File:
                getPhotoByFile();
                break;

            default:
                break;
        }
    }

    private String imgPath;

    public void getPhotoByCamera() {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            Log.v("TestFile", "SD card is not avaiable/writeable right now.");
            return;
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss",
                Locale.CHINA);
        String imageName = "fm" + sdf.format(date) + ".jpg";
        imgPath = Environment.getExternalStorageDirectory() + "/" + imageName;
        File out = new File(imgPath);
        Uri uri = Uri.fromFile(out);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("fileName", imageName);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 0);
    }

    public void getPhotoByFile() {
        Intent intent2 = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent2, 1);
    }


    public void onDateBack(String date) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK)
            return;
        if (requestCode == 0) {// camera back
            Bitmap bitmap = Util.readBitmapByPath(imgPath);
            if (bitmap == null) {
                ToastUtils.showLongToast(SettingActivity.this, "未获取到图片!");
                return;
            }
            if (null == cropperView)
                cropperView = new CropperView(this, this);
            cropperView.cropper(bitmap);
        } else if (requestCode == 1) {// file back
            if (data != null) {
                Bitmap bitmap = null;
                Uri uri = data.getData();
                // url是content开头的格式
                if (uri.toString().startsWith("content://")) {
                    String path = null;
                    String[] pojo = { MediaStore.Images.Media.DATA };
                    Cursor cursor = getContentResolver().query(uri, pojo, null,
                            null, null);
                    // managedQuery(uri, pojo, null, null, null);

                    if (cursor != null) {
                        // ContentResolver cr = this.getContentResolver();
                        int colunm_index = cursor
                                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        cursor.moveToFirst();
                        path = cursor.getString(colunm_index);

                        bitmap = Util.readBitmapByPath(path);
                    }

                    if (bitmap == null) {
                        ToastUtils.showLongToast(SettingActivity.this,
                                "未获取到图片!");
                        return;
                    }
                } else if (uri.toString().startsWith("file:///")) {
                    String path = uri.toString().substring(8,
                            uri.toString().length());
                    bitmap = Util.readBitmapByPath(path);
                    if (bitmap == null) {
                        ToastUtils.showLongToast(SettingActivity.this,
                                "未获取到图片!");
                        return;
                    }

                }
                if (null == cropperView)
                    cropperView = new CropperView(this, this);
                cropperView.cropper(bitmap);
            }

        }

    }
    private Bitmap cropBitmap;
    @Override
    public void OnCropperBack(Bitmap bitmap) {
        if (null == bitmap)
            return;
        cropBitmap = bitmap;

        // 上传头像
        //new UserLogoAsyncTask().execute();

        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        cropBitmap.compress(Bitmap.CompressFormat.PNG, 90, bao);
        byte[] buffer = bao.toByteArray();
        String imgStr = Base64.encodeToString(buffer, 0, buffer.length,
                Base64.DEFAULT);
        String profileData = imgStr;

        String url = Constant.UPDATEPROFILE_INTERFACE;
        Map<String,String> paras =new HashMap<>();
        paras.put("profileType", String.valueOf(EditSetTypeEnum.LOGO.getIndex()));
        paras.put("profileData", profileData );
        HttpParaUtils httpParaUtils =new HttpParaUtils();
        Map<String,String > maps = httpParaUtils.getHttpPost(paras);
        GsonRequest<HTMerchantModel> request = new GsonRequest<HTMerchantModel>(
                Request.Method.POST,
                url ,
                HTMerchantModel.class,
                null,
                maps,
                updateListener,
                this
        );

        this.showProgressDialog("","正在上传，请稍等...");

        VolleyRequestManager.getRequestQueue().add(request);
    }

    Response.Listener<HTMerchantModel> updateListener = new Response.Listener<HTMerchantModel>() {
        @Override
        public void onResponse(HTMerchantModel htMerchantModel) {
            SettingActivity.this.closeProgressDialog();
            if( null == htMerchantModel){
                DialogUtils.showDialog(SettingActivity.this, SettingActivity.this.getSupportFragmentManager(), "错误信息", "更新失败", "关闭");
                return;
            }
            if( htMerchantModel.getSystemResultCode()!=1){
                DialogUtils.showDialog(SettingActivity.this,SettingActivity.this.getSupportFragmentManager(),"错误信息",htMerchantModel.getSystemResultDescription(),"关闭");
                return;
            }
            if( htMerchantModel.getResultCode() == Constant.TOKEN_OVERDUE){
                ActivityUtils.getInstance().skipActivity(SettingActivity.this,LoginActivity.class);
                return;
            }
            if( htMerchantModel.getResultCode() != 1){
                DialogUtils.showDialog(SettingActivity.this,SettingActivity.this.getSupportFragmentManager(),"错误信息",htMerchantModel.getResultDescription(),"关闭");
                return;
            }
            SellerApplication.getInstance().writeMerchantInfo(htMerchantModel.getResultData().getUser());
            //刷新界面数据
            EventBus.getDefault().post(new RefreshSettingEvent());
        }
    };


    public class UserLogoAsyncTask extends
    AsyncTask<Void, Void, HTMerchantModel>
    {
        private int profileType;

        private Object profileData;

        @Override
        protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        profileType = 0;
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        cropBitmap.compress(Bitmap.CompressFormat.PNG, 90, bao);
        byte[] buffer = bao.toByteArray();
        String imgStr = Base64.encodeToString(buffer, 0, buffer.length,
                Base64.DEFAULT);
        profileData = imgStr;
    }

        @Override
        protected void onPostExecute(HTMerchantModel result) {

        }

        @Override
        protected HTMerchantModel doInBackground(Void... params) {
        // TODO Auto-generated method stub
            HTMerchantModel registerBean = new HTMerchantModel();
        JSONUtil<HTMerchantModel> jsonUtil = new JSONUtil<HTMerchantModel>();
        String url;
        ObtainParamsMap obtainMap = new ObtainParamsMap(
                SettingActivity.this);
        Map<String, String> paramMap = obtainMap.obtainMap();

        // 拼接注册url
        url = Constant.UPDATE_PROFILE;
        // 注册是POST提交
        paramMap.put("profileType", String.valueOf(profileType));
        paramMap.put("profileData", String.valueOf(profileData));
        // 封装sign
        String signStr = obtainMap.getSign(paramMap);
        paramMap.put("sign", signStr);
        if (Constant.IS_PRODUCTION_ENVIRONMENT) {
            String jsonStr = HttpUtil.getInstance().doPost(url, paramMap);
            try {
                registerBean = jsonUtil.toBean(jsonStr, registerBean);
            } catch (JsonSyntaxException e) {
                Log.e("JSON_ERROR", e.getMessage());
                registerBean.setResultCode(0);
                registerBean.setResultDescription("解析json出错");
            }
        } else {
        }

        return registerBean;
    }

    }


    public void onEventMainThread(RefreshSettingEvent event){
        setData();
    }
}