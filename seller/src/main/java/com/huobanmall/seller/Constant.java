package com.huobanmall.seller;

/**
 * Created by Administrator on 2015/8/26.
 */
public class Constant {
    public static final String BASE_ROOT_URL ="http://newtask.fanmore.cn/";
    public static final String BASE_URL = BASE_ROOT_URL + "app/";// 基础URL

    public static final String GET_VD_INTERFACE = BASE_URL + "sendSMS";// 获取验证码

    // 短信获取方式:文本
    public final static String SMS_TYPE_TEXT = "0";

    // 短信获取方式:语音
    public final static String SMS_TYPE_VOICE = "1";
}
