package com.huotu.huobanmall.seller.bean;

/**
 * Created by Administrator on 2015/9/7.
 */
public
class InitBean {

    private GlobalModel global;
    private MerchantModel user;
    private UpdateModel update;

    public
    GlobalModel getGlobal ( ) {
        return global;
    }

    public
    void setGlobal ( GlobalModel global ) {
        this.global = global;
    }

    public
    MerchantModel getUser ( ) {
        return user;
    }

    public
    void setUser ( MerchantModel user ) {
        this.user = user;
    }

    public
    UpdateModel getUpdate ( ) {
        return update;
    }

    public
    void setUpdate ( UpdateModel update ) {
        this.update = update;
    }
}
