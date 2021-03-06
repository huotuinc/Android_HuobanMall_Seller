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
import com.huotu.huobanmall.seller.utils.GsonRequest;
import com.huotu.huobanmall.seller.utils.HttpParaUtils;
import com.huotu.huobanmall.seller.utils.ObtainParamsMap;
import com.huotu.huobanmall.seller.utils.SystemTools;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

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
            String x = "";// EncryptUtil.getInstance().encryptMd532(s);
            String y = DigestUtils.md5DigestAsHex(s.getBytes("utf-8"));

            Map<String,String> p = new HashMap<>();
            p.put("keyword", "杭州东阳");
            p.put("version","1.0.0");


            ObtainParamsMap i1=new ObtainParamsMap(  this.getContext() );
            String xx= i1.getSign(p);

            HttpParaUtils i2=new HttpParaUtils();
           String yy = i2.getSign(p);

            int o=1;
            o=1+1;

            x=x;
            //boolean b = x.equals(y);
        }catch (UnsupportedEncodingException ex){
            Log.e("test",ex.getMessage());
        }
    }

    public class  t {

        public Double getF() {
            return f;
        }

        public void setF(Double f) {
            this.f = f;
        }

        public Double f;

        public Date getD() {
            return d;
        }

        public void setD(Date d) {
            this.d = d;
        }

        private Date d; }

    public void testJsonDateLong(){
//        Number ddd= 1252113.52;
//        double dfds = ddd.floatValue();
//        double dfasf = ddd.doubleValue();
//        Number dfsddf=111111.52;
//        float d2222= dfsddf.floatValue();
//        double d333 = dfsddf.floatValue();


        String s1 = "{d:1442160000000,f=1252113.52}";
        String s2 = "{d:1442376000000,f=52113.52}";
        String s3 = "{d:1442419200000,f=12.521}";
        String s4 = "{d:1442592000000,f=12.4}";
        t tt = new t();
        tt.setD(new Date(System.currentTimeMillis()));

        Date y1= new Date(1442376000000L);


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(1442419200000L));

        Date d= calendar.getTime();
        Date y2 = new Date( 1442419200000L);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        String dddd = format.format(y2);

        Gson ss = new GsonBuilder()
                    .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                        @Override
                        public Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                            return new Date(jsonElement.getAsJsonPrimitive().getAsLong() );
                        }
                    })
                .registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
                    @Override
                    public JsonElement serialize(Date date, Type type, JsonSerializationContext jsonSerializationContext) {
                        return new JsonPrimitive( date.getTime());
                    }
                })
                //.registerTypeAdapter(Float.class, new FloatGsonAdapter())
                .setDateFormat(DateFormat.LONG).create();

        //String sd= ss.toJson(tt, t.class);

        Float d2 = Float.MAX_VALUE;

        t t1 = ss.fromJson(s1, tt.getClass());
        t t2 = ss.fromJson(s2, tt.getClass());
        t t3 = ss.fromJson(s3,tt.getClass());
        t t4 = ss.fromJson( s4 , tt.getClass());
        String temp1 = SystemTools.getDateTime(t1.d, "yyyy-MM-dd");
        String temp2  =SystemTools.getDateTime(t2.d, "yyyy-MM-dd");
        String temp3 = SystemTools.getDateTime( t3.d,"yyyy-MM-dd");
        String temp4 = SystemTools.getDateTime(t4.d ,"yyyy-MM-dd");

        String yyy1 = SystemTools.getDateTime(y1,"yyyy-MM-dd");
        String yyy2 = SystemTools.getDateTime(y2,"yyyy-MM-dd");
    }

    public void test()  {

        Date date = new Date(1442419200000L
        );
        System.out.print(date);
    }




    public void testfloat(){
        DecimalFormat f = new DecimalFormat("0.00");
        float s = 0.0000f;
        String temp = f.format(s);
        temp +="fff";
        s = 1232.002342f;
        temp = f.format(s);
        temp ="sdfsd";
    }


    public class FloatGsonAdapter implements JsonSerializer<Float> , JsonDeserializer<Float>{
        @Override
        public JsonElement serialize(Float aFloat, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive( aFloat);
        }

        @Override
        public Float deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            // jsonElement.getAsJsonPrimitive().getAsDouble() ;
            //DecimalFormat format = new DecimalFormat("0.00");
            return (float)jsonElement.getAsJsonPrimitive().getAsDouble();
        }
    }
}