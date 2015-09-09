package com.huotu.huobanmall.seller.bean;

/**
 * Created by Administrator on 2015/9/9.
 */
public class MJSendSMSModel extends BaseModel {

    public InnerClass getResultData() {
        return resultData;
    }

    public void setResultData(InnerClass resultData) {
        this.resultData = resultData;
    }

    private InnerClass resultData;


   public class InnerClass{
        public Boolean getVoiceAble() {
            return voiceAble;
        }

        public void setVoiceAble(Boolean voiceAble) {
            this.voiceAble = voiceAble;
        }

        private Boolean voiceAble;
    }
}
