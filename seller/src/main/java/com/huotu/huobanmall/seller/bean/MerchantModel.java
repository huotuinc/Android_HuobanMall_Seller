package com.huotu.huobanmall.seller.bean;

/**
 * Created by Administrator on 2015/8/28.
 * 商家信息
 */
public class MerchantModel extends BaseModel {
    /**
     * 权限，控制app端的内容显示 以,隔开 如 11,33,55
     */
    private String authority;
    /**
     * 店铺名称
     */
    private String title;

    /**
     * 店铺描述
     */
    private String discription;
    /**
     *订单支付成功通知（0关闭,1开启）
     */
    private int enableBillNotice;
    /**
     *新增小伙伴通知（0关闭，1开启）
     */
    private int enablePartnerNotice;
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
    private int noDisturbed;
    /**
     * 身份验证 服务端负责生成 负责验证；app端只需要保存 传递 每次App获得新的Token,旧Token就弃用。
     */
    private String token;
    /**
     * 欢迎提示
     */
    private String welcomeTip;
    /**
     * 商家首页url
     */
    private String indexUrl;

    public String getIndexUrl() {
        return indexUrl;
    }

    public void setIndexUrl(String indexUrl) {
        this.indexUrl = indexUrl;
    }

    public String getOperatored() {
        return operatored;
    }

    public void setOperatored(String operatored) {
        this.operatored = operatored;
    }

    /**
     * 操作者
     */
    private String operatored;

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public int getEnableBillNotice() {
        return enableBillNotice;
    }

    public void setEnableBillNotice(int enableBillNotice) {
        this.enableBillNotice = enableBillNotice;
    }

    public int getEnablePartnerNotice() {
        return enablePartnerNotice;
    }

    public void setEnablePartnerNotice(int enablePartnerNotice) {
        this.enablePartnerNotice = enablePartnerNotice;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int isNoDisturbed() {
        return noDisturbed;
    }

    public void setNoDisturbed(int noDisturbed) {
        this.noDisturbed = noDisturbed;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getWelcomeTip() {
        return welcomeTip;
    }

    public void setWelcomeTip(String welcomeTip) {
        this.welcomeTip = welcomeTip;
    }

//    public String getOperator() {
//        return operator;
//    }

//    public void setOperator(String operator) {
//        this.operator = operator;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
