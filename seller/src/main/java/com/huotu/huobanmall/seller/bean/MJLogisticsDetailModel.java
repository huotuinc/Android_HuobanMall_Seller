package com.huotu.huobanmall.seller.bean;

/**
 * Created by Administrator on 2015/9/16.
 */
public class MJLogisticsDetailModel extends BaseModel {
    public InnerClass getResultData() {
        return resultData;
    }

    public void setResultData(InnerClass resultData) {
        this.resultData = resultData;
    }

    private InnerClass resultData;

    public class InnerClass{
        public LogisticsDetailModel getData() {
            return data;
        }

        public void setData(LogisticsDetailModel data) {
            this.data = data;
        }

        private LogisticsDetailModel data;
    }
}
