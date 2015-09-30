package com.huotu.huobanmall.seller.common;

import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2015/8/25.
 */
public class Constant {


    /**
     * 操作平台码
     *
     */

    public static final String APPKEY = "b73ca64567fb49ee963477263283a1bf";
    // 平台安全码
    public static final String APP_SECRET = "1165a8d240b29af3f418b8d10599d0da";

    public static final String OPERATION_CODE = "HB2015AD";
    // 定位信息文件
    public final static String LOCATION_INFO = "location_info";

    public static final boolean IS_PRODUCTION_ENVIRONMENT = true;// 切换测试环境和生产环境阀值
    // 获取验证码类型
    public static final String GET_VD_TYPE_REG = "2";//

    public static final String GET_VD_TYPE_FORGET = "1";//

    public static final String GET_VD_TYPE_BINDLE = "3";//

    /**
     * capCode
     */
    public static final String CAP_CODE = "default";

    // 用户姓名
    public final static String LOGIN_USER_ACCOUNT = "account_name";
    // 登录信息文件
    public final static String LOGIN_USER_INFO = "login_user_info";
    //公共信息
    public final static String LOGIN_GLOBAL_INFO="login_global_info";
    // 用户Token
    public final static String PRE_USER_TOKEN="user_token";
    // 城市码
    public final static String  PRE_LOCATION_CITY_CODE = "cityCode";
    // url请求前缀
    public final static String BASE_ROOT_URL ="http://apitest.51flashmall.com:8080/huobanmall/";
    //public final static String BASE_ROOT_URL ="http://192.168.1.57:8080/huobanmall/";
    //public final static String BASE_ROOT_URL ="http://192.168.1.48:8080/huobanmall/";
    public static final String BASE_URL = BASE_ROOT_URL + "app/";// 基础URL

    public static final String GET_VD_INTERFACE = BASE_URL + "sendSMS";// 获取验证码

    public static final String GOODSLIST_INTERFACE=BASE_URL+"goodsList";//商品列表

    public static final String LOGIN_INTERFACE=BASE_URL+"login";//登录接口

    public static final String FORGET_INTERFACE=BASE_URL+"forgetPassword";//忘记密码接口

    public final static String MODIFYPSW_INTEFACE = BASE_URL + "modifyPassword";// 修改密码

    public static final String INIT_INTERFACE= BASE_URL+"init";//初始化接口

    public static final String NEWTODAY_INTERFACE=BASE_URL+"newToday";//今日新增接口

    public static final String UPDATEPROFILE_INTERFACE = BASE_URL + "updateMerchantProfile"; //更新商家信息

    public static final String OPERGOODS_INTERFACE = BASE_URL +"operGoods";//操作商品接口

    public static final String USERREPORT_INTERFACE =BASE_URL +"userReport";//会员量统计报表

    public static final String ORDERREPORT_INTERFACE = BASE_URL +"orderReport";//订单统计报表

    public static final String SALESREPORT_INTERFACE =BASE_URL + "salesReport";//销售额统计报表

    public static final String OTHERSTATISTIC_INTERFACE = BASE_URL + "otherStatistics"; //更多统计

    public static final String SALESLIST_INTERFACE = BASE_URL+"salesList";//销售明细

    public static final String TOPSCORE_INTERFACE =BASE_URL+"topScore";//返利积分统计

    public static final String USERSCORELIST_INTERFACE = BASE_URL + "userScoreList";//返利积分明细

    public static final String TOPSALES_INTERFACE =BASE_URL+"topSales";//销售统计，最高消费额订单(前10条)

    public static final String TOPCONSUME_INTERFACE =BASE_URL +"topConsume";//消费统计

    public static final String TOPGOODS_INTERFACE =BASE_URL +"topGoods";//商品销售前10排行

    public static final String USERCONSUMELIST_INTERFACE = BASE_URL + "userConsumeList";//消费明细列表

    public static final String LOGISTICSDETAIL_INTERFACE = BASE_URL +"logisticsDetail";//物流详情

    public static final String ORDERDETAIL_INTERFACE = BASE_URL +"orderDetail";//订单详情

    public static final String ORDERLIST_INTERFACE=BASE_URL+"orderList";//订单列表

    public static final String UPDATEDEVICETOKEN_INTERFACE = BASE_URL +"updateDeviceToken";// app请求设置该设备(imei)的deviceToken

    public static final String MESSAGE_INTERFACE = BASE_URL + "messages";// 获取消息列表 ---GET

    public static final String FEEDBACK_INTERFACE = BASE_URL +"feedback";// 反馈意见 ---POST
    /**
     * token添加的类型
     */
    public final static String TOKEN_ADD = "add";// 添加token

    public final static String TOKEN_CLEAR = "clear";// 清空token

    // 口令文件
    public final static String LOGIN_AUTH_INFO = "login_auth_info";
    // 推送信息本地别名
    public final static String PUSH_INFO = "push_info";
    // 推送信息本地别名
    public final static String PUSH_INFO_ALIAS = "push_info_alias";

    // 口令参数
    public final static String LOGIN_AUTH_TOKEN = "user_token";
    //商户权限
    public final static String LOGIN_AUTH_AUTHORITY = "authority";
    //店铺描述
    public final static String LOGIN_AUTH_DISCRIPTION = "discription";
    //支付订单通知
    public final static String LOGIN_AUTH_ENABLE_BILL_NOTICE = "enableBillNotice";
    //新增小伙伴通知（0关闭，1开启）
    public final static String LOGIN_AUTH_ENABLE_PARTNER_NOTICE = "enablePartnerNotice";
    //店铺logo
    public final static String LOGIN_AUTH_LOGO = "logo";
    //手机号mobile
    public final static String LOGIN_AUTH_MOBILE = "mobile";
    //登录名
    public final static String LOGIN_AUTH_NAME = "name";
    //昵称
    public final static String LOGIN_AUTH_NICKNAME = "nickName";
    //夜间免打扰
    public final static String LOGIN_AUTH_NO_DISTURBD = "noDisturbed";
    //欢迎提示
    public final static String LOGIN_AUTH_WELCOME_TIP = "welcomeTip";
    //操作者
    public final static String LOGIN_AUTH_OPERATOR = "operator";
    //商铺名称
    public final static String LOGIN_AUTH_SHOPNAME="title";
    //店铺首页地址
    public final static String LOGIN_AUTH_SHOPURL="shopUrl";
    //关于我们
    public final static String LOGIN_GLOBAL_ABOUTURL = "aboutURL";
    //客服电话号码
    public final static String LOGIN_GLOBAL_SERVICEPHONE = "customerServicePhone";
    //帮助
    public final static String LOGIN_GLOBAL_HELPURL = "helpURL";
    //服务地址
    public final static String LOGIN_GLOBAL_SERVERURL = "serverUrl";
    //验证码是否支持语音播报
    public final static String LOGIN_GLOBAL_VOICESUPPORTED="voiceSupported";

    // 短信获取方式:文本
    public final static String SMS_TYPE_TEXT = "0";

    // 短信获取方式:语音
    public final static String SMS_TYPE_VOICE = "1";

    //Activity之间传递参数的参数名称
    public final static String Extra_Url="url";
    public final static String Extra_Title="title";
    //
    public final static String Extra_OrderNo="orderNo";
    public static final String UPDATE_PROFILE = BASE_URL + "updateProfile";// 修改资料
    // ---POST


    public static final String BASE_IMAGE_PATH = Environment.getExternalStorageDirectory().toString() + File.separator + "hbimage";

    public static final String PATH_PKG_TEMP = BASE_IMAGE_PATH + File.separator + "pkgtemp";
    public final static int RESULT_CODE_CLIENT_DOWNLOAD_FAILED = -8000;
    public final static int REQUEST_CODE_CLIENT_DOWNLOAD = 8000;

    //loading动画加载时间
    public final static int ANIMATION_COUNT = 2000;

    // token过期返回码
    public final static int TOKEN_OVERDUE = 56001;
    //用户名或密码错误
    public final static int ERROR_USER_PASSWORD=56000;
    //请求超时时间
    public final static int REQUEST_TIMEOUT=20*1000;
    /**
     * 订单Tab页
     */
    public final static int TAB_ORDER=1;
    /**
     * 销售额Tab页
     */
    public final static int TAB_SALE =2;
    /**
     * 会员Tab页
     */
    public final static int TAB_MEMBER=3;

    /**
     * 消息类列表每页显示10条
      */
    public final static int PAGES_COMMON = 10;

    /**
     * 系统消息
    */
    public static int MESSAGE_TYPE_SYSTEMMESSAGE=2;
}


