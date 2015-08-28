package com.huotu.huobanmall.seller.bean;

/**
 * Created by Administrator on 2015/8/28.
 * 商家信息
 */
public class MerchantModel {
    /**
     * 权限，控制app端的内容显示 以,隔开 如 11,33,55
     */
    private String authority;
    /**
     * 店铺描述
     */
    private String discription;
    /**
     *订单支付成功通知（0关闭,1开启）
     */
    private short enableBillNotice;
    /**
     *新增小伙伴通知（0关闭，1开启）
     */
    private short enablePartnerNotice;
    /**
     * 店铺logo
     */
    private String logo;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 登录名
     */
    private String name;
    /**
     * 昵称 显示在app中
     */
    private String nickName;
    /**
     * 夜间免打扰模式 0 默认开启 1 关闭 （app端维护具体时间22:00-8:00）
     */
    private boolean noDisturbed;
    /**
     * 身份验证 服务端负责生成 负责验证；app端只需要保存 传递 每次App获得新的Token,旧Token就弃用。
     */
    private String token;
    /**
     * 欢迎提示
     */
    private String welcomeTip;
}
