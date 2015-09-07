package com.huobanmall.seller;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

//import com.huotu.huobanmall.seller.utils.EncryptUtils;
import com.huotu.huobanmall.seller.utils.HttpParaUtils;

import java.io.UnsupportedEncodingException;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testSign(){
        HttpParaUtils utils = new HttpParaUtils();
        String url = utils.getHttpGetUrl("http://www.baidu.com", null);

        String ff =url;
        ff = url+ url;

        try {
            String s = "金向东金向东1013&appkey=23423sdfasdfg?token=fsdfasf";
            //String x = EncryptUtils.getInstance().encryptMd532(s);
            String y = DigestUtils.md5DigestAsHex(s.getBytes("utf-8"));

            //boolean b = x.equals(y);
        }catch (UnsupportedEncodingException ex){
            Log.e("test",ex.getMessage());
        }
    }
}