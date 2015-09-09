package com.huotu.huobanmall.seller.bean;

/**
 * 商户登录返回json对应的model
 */
public class HTMerchantModel extends BaseModel {

    private InnerClass resultData;


    public InnerClass getResultData() {
        return resultData;
    }

    public void setResultData(InnerClass resultData) {
        this.resultData = resultData;
    }

    public class InnerClass
    {
        private MerchantModel user;

        public MerchantModel getUser() {
            return user;
        }

        public void setUser(MerchantModel user) {
            this.user = user;
        }
    }


}
