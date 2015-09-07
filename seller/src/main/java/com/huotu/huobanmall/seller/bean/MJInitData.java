package com.huotu.huobanmall.seller.bean;

/**
 * Created by Administrator on 2015/8/31.
 */
public class MJInitData extends BaseModel{

    private InnerData resultData;

    public InnerData getResultData() {
        return resultData;
    }

    public void setResultData(InnerData resultData) {
        this.resultData = resultData;
    }

    public class InnerData {
        public GlobalModel getGlobal() {
            return global;
        }

        public MerchantModel getUser() {
            return user;
        }

        public UpdateModel getUpdate() {
            return update;
        }

        public void setGlobal(GlobalModel global) {
            this.global = global;
        }

        public void setUser(MerchantModel user) {
            this.user = user;
        }

        public void setUpdate(UpdateModel update) {
            this.update = update;
        }

        private GlobalModel global;
        private MerchantModel user;
        private UpdateModel update;
    }
}
