package com.huotu.huobanmall.seller.utils;


import android.net.Uri;
import android.util.Log;

import com.huotu.huobanmall.seller.common.Constant;
import com.huotu.huobanmall.seller.common.SellerApplication;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/8/25.
 */
public class HttpParaUtils {
    private String TAG= HttpParaUtils.class.getName();
    private String PARA_NAME1="appKey";
    private String PARA_NAME2="cityCode";
    private String PARA_NAME3 ="cpaCode";
    private String PARA_NAME4="imei";
    //private String PARA_NAME5="ip";
    private String PARA_NAME6="operation";
    private String PARA_NAME7="timestamp";
    private String PARA_NAME8="version";
    private String PARA_NAME9="token";

    private String PARA_SIGN="sign";
    private String timestamp;

    private String PARA_APPSECURE = "appSecret";


    public HttpParaUtils(){
        timestamp ="1441762623123"; //String.valueOf(System.currentTimeMillis());
    }

    protected String getCommonPara(Uri.Builder builder ){
        builder.appendQueryParameter(PARA_NAME1, Constant.APPKEY);
        String cityCode = PreferenceHelper.readString(SellerApplication.getInstance(), Constant.LOCATION_INFO, Constant.PRE_LOCATION_CITY_CODE,"");
        builder.appendQueryParameter(PARA_NAME2, cityCode);
        builder.appendQueryParameter(PARA_NAME3, Constant.CAP_CODE);
        builder.appendQueryParameter(PARA_NAME4, SystemUtils.getPhoneIMEI(SellerApplication.getInstance()));
        //builder.appendQueryParameter(PARA_NAME5, "127.0.0.1");
        builder.appendQueryParameter(PARA_NAME6, Constant.OPERATION_CODE);
        builder.appendQueryParameter(PARA_NAME8, SystemUtils.getAppVersion(SellerApplication.getInstance()));
        String token = PreferenceHelper.readString( SellerApplication.getInstance() , Constant.LOGIN_USER_INFO , Constant.PRE_USER_TOKEN , "");
        builder.appendQueryParameter(PARA_NAME9, token);
        return  builder.build().toString();
    }

    public Map<String,String> getParaMaps( Map<String,String> map ){
        Map<String,String> paras = new HashMap<>();
        paras.put(PARA_NAME1, Constant.APPKEY);
        String cityCode = PreferenceHelper.readString(SellerApplication.getInstance(), Constant.LOCATION_INFO, Constant.PRE_LOCATION_CITY_CODE,"");
        paras.put(PARA_NAME2, cityCode);
        paras.put(PARA_NAME3, Constant.CAP_CODE);
        paras.put(PARA_NAME4, SystemUtils.getPhoneIMEI ( SellerApplication.getInstance ( ) ) );
        paras.put(PARA_NAME7, timestamp);
        paras.put(PARA_NAME6, Constant.OPERATION_CODE);
        paras.put(PARA_NAME8, SystemUtils.getAppVersion(SellerApplication.getInstance()));
        String token = PreferenceHelper.readString( SellerApplication.getInstance() , Constant.LOGIN_USER_INFO , Constant.PRE_USER_TOKEN , "");
        paras.put(PARA_NAME9, token);
        //paras.put(PARA_NAME5, "127.0.0.1");

        if(map!=null){
            paras.putAll(map);
        }
        return paras;
    }

    private String doSort(Map<String,String> paras ){
        //Map<String,String> map = getParaMaps(paras);
        Map<String, String> signMap = new HashMap<String, String> ( paras );
        signMap.put(PARA_APPSECURE, Constant.APP_SECRET);

        List sortList = new ArrayList(signMap.entrySet());
        Collections.sort(sortList, new Comparator() {
            @Override
            public int compare(Object arg1, Object arg2) {
                Map.Entry obj1 = (Map.Entry) arg1;
                Map.Entry obj2 = (Map.Entry) arg2;
                return (obj1.getKey()).toString().compareTo((String) obj2.getKey());
            }
        });

        StringBuilder buffer=new StringBuilder();
        for (Iterator iter = sortList.iterator(); iter.hasNext();)
        {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            // Log.i("key", key);
            // Log.i("value", resultMap.get(key));
            buffer.append(signMap.get(key));
        }
        return buffer.toString();
    }

    private String getSign( Map<String,String> paras ) {
        String values = this.doSort(paras);
        Log.i("sign", values);
        //String signHex = EncryptUtils.getInstance().encryptMd532(values);
        String signHex = "";
        signHex = EncryptUtil.getInstance().encryptMd532(values);
        Log.i("signHex", signHex);
        return signHex;
    }


    public String getHttpGetUrl(String url , Map<String,String> paras ){
        Uri.Builder builder = Uri.parse(url).buildUpon();

        Map<String,String> map = getParaMaps(paras);

        String sign = getSign(map);

        map.put(PARA_SIGN, sign);

        for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey().toString();
                String value = entry.getValue().toString();
                //System.out.println("key=" + key + " value=" + value);
                try {
                    String valueEncode= URLEncoder.encode( value,"utf-8");
                    builder.appendQueryParameter(key, valueEncode);
                }catch (UnsupportedEncodingException ex){
                    Log.e(TAG, ex.getMessage());
                }
        }

        return  builder.build().toString();
    }

    public Map<String,String> getHttpPost( Map<String,String> paras ){
        Map<String,String> map = getParaMaps( paras);
        String sign = getSign(map);
        map.put(PARA_SIGN , sign);
        return map;
    }
}
