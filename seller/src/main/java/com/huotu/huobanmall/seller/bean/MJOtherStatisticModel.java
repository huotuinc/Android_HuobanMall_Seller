package com.huotu.huobanmall.seller.bean;

/**
 * Created by Administrator on 2015/9/11.
 */
public class MJOtherStatisticModel extends BaseModel{
    public InnerClass getResultData() {
        return resultData;
    }

    public void setResultData(InnerClass resultData) {
        this.resultData = resultData;
    }

    private InnerClass resultData;

    public class InnerClass {
        public OtherStatisticModel getOtherInfoList() {
            return otherInfoList;
        }

        public void setOtherInfoList(OtherStatisticModel otherInfoList) {
            this.otherInfoList = otherInfoList;
        }

        private OtherStatisticModel otherInfoList;

    }
}
