package com.huotu.huobanmall.seller.bean;

/**
 * Created by Administrator on 2015/9/22.
 */
public class MJOrderDetailModel extends BaseModel {
    private InnerClass resultData;

    public InnerClass getResultData() {
        return resultData;
    }

    public void setResultData(InnerClass resultData) {
        this.resultData = resultData;
    }

    public class InnerClass{
        public OrderDetailModel getData() {
            return data;
        }

        public void setData(OrderDetailModel data) {
            this.data = data;
        }

        private OrderDetailModel data;
    }
}
