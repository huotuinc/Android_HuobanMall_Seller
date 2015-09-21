package com.huotu.huobanmall.seller.bean;

import java.util.List;

/**
 * Created by Administrator on 2015/9/21.
 */
public class MJTopSalesModel  extends BaseModel {
    public InnerClass getResultData() {
        return resultData;
    }

    public void setResultData(InnerClass resultData) {
        this.resultData = resultData;
    }

    private InnerClass resultData;

    public class InnerClass{
        public List<TopSalesModel> getList() {
            return list;
        }

        public void setList(List<TopSalesModel> list) {
            this.list = list;
        }

        private List<TopSalesModel> list;
    }
}
