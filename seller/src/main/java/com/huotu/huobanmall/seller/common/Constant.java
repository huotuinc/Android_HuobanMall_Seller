package com.huotu.huobanmall.seller.common;

import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2015/8/25.
 */
public class Constant {


    // 平台安全码
    public static final String APP_SECRET = "1165a8d240b29af3f418b8d10599d0da";
    /**
     * 操作平台码
     *
     */

    public static final String APPKEY = "b73ca64567fb49ee963477263283a1bf";

    public static final String OPERATION_CODE = "FM2015AD";
    // 定位信息文件
    public final static String LOCATION_INFO = "location_info";

    public static final boolean IS_PRODUCTION_ENVIRONMENT = true;// 切换测试环境和生产环境阀值
    // 获取验证码类型
    public static final String GET_VD_TYPE_REG = "1";//

    public static final String GET_VD_TYPE_FORGET = "2";//

    public static final String GET_VD_TYPE_BINDLE = "3";//

    /**
     * capCode
     */
    public static final String CAP_CODE = "default";

    // 用户姓名
    public final static String LOGIN_USER_ACCOUNT = "account_name";
    // 登录信息文件
    public final static String LOGIN_USER_INFO = "login_user_info";

    public final static String PRE_USER_TOKEN="user_token";

    // 纬度
    public final static String  PRE_LOCATION_LATITUDE = "latitude";

    // 经度
    public final static String PRE_LOCATION_LONGITUDE = "Longitude";
    // 城市码
    public final static String  PRE_LOCATION_CITY_CODE = "cityCode";

    public final static String BASE_ROOT_URL ="http://apitest.51flashmall.com:8080/huobanmall/";

    public static final String BASE_URL = BASE_ROOT_URL + "app/";// 基础URL

    public static final String GET_VD_INTERFACE = BASE_URL + "sendSMS";// 获取验证码

    public static final String GOODSLIST_INTERFACE=BASE_URL+"goodsList";//商品列表

    public static final String LOGIN_INTERFACE=BASE_URL+"login";//登录接口

    public static final String FORGET_INTERFACE=BASE_URL+"forgetPassword";//忘记密码接口

    public static final String INIT_INTERFACE= BASE_URL+"init";//初始化接口
    /**
     * token添加的类型
     */
    public final static String TOKEN_ADD = "add";// 添加token

    public final static String TOKEN_CLEAR = "clear";// 清空token

    // 口令文件
    public final static String LOGIN_AUTH_INFO = "login_auth_info";

    // 口令参数
    public final static String LOGIN_AUTH_TOKEN = "user_token";



    // 短信获取方式:文本
    public final static String SMS_TYPE_TEXT = "0";

    // 短信获取方式:语音
    public final static String SMS_TYPE_VOICE = "1";

    //Activity之间传递参数的参数名称
    public final static String Extra_Url="url";


    public static final String BASE_IMAGE_PATH = Environment.getExternalStorageDirectory().toString() + File.separator + "hbimage";

    public static final String PATH_PKG_TEMP = BASE_IMAGE_PATH + File.separator + "pkgtemp";
    public final static int RESULT_CODE_CLIENT_DOWNLOAD_FAILED = -8000;
    public final static int REQUEST_CODE_CLIENT_DOWNLOAD = 8000;
}


