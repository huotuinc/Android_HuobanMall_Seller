package com.huobanmall.seller;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

//import com.huotu.huobanmall.seller.utils.EncryptUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.huotu.huobanmall.seller.utils.DigestUtils;
import com.huotu.huobanmall.seller.utils.HttpParaUtils;
import com.huotu.huobanmall.seller.utils.SystemTools;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.Date;

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

    public class  t {
        public Date getD() {
            return d;
        }

        public void setD(Date d) {
            this.d = d;
        }

        private Date d; }

    public void testJsonDateLong(){
        String s="{d:1442160000000}";
        t tt = new t();
        tt.setD(new Date(System.currentTimeMillis()));



        Gson ss = new GsonBuilder()
                    .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                        @Override
                        public Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                            return new Date(jsonElement.getAsJsonPrimitive().getAsLong());
                        }
                    })
                .registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
                    @Override
                    public JsonElement serialize(Date date, Type type, JsonSerializationContext jsonSerializationContext) {
                        return new JsonPrimitive( date.getTime());
                    }
                })
                .setDateFormat(DateFormat.LONG).create();

        String sd= ss.toJson( tt , t.class);

        t sdfs = ss.fromJson(s, tt.getClass());

        String temp = sdfs.d.toString();
    }

    public void test()  {

        Date date = new Date(1442419200000L
        );
        System.out.print(date);
    }
}