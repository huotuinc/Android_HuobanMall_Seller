package com.huotu.huobanmall.seller.bean;

import java.util.List;

/**
 * Created by Administrator on 2015/9/22.
 */
public class MJOrderListModel extends BaseModel {
    public InnerClass getResultData() {
        return resultData;
    }

    public void setResultData(InnerClass resultData) {
        this.resultData = resultData;
    }

    private InnerClass resultData;

    public class InnerClass{
        public List<OrderListModel> getList() {
            return list;
        }

        public void setList(List<OrderListModel> list) {
            this.list = list;
        }

        private List<OrderListModel> list;
    }
}
