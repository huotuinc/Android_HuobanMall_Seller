package com.huotu.huobanmall.seller.bean;

import java.util.List;

/**
 * Created by Administrator on 2015/9/16.
 */
public class MJSaleListModel extends BaseModel {
    public InnerClass getResultData() {
        return resultData;
    }

    public void setResultData(InnerClass resultData) {
        this.resultData = resultData;
    }

    private InnerClass resultData;


    public class InnerClass {
        public List<SalesListModel> getList() {
            return list;
        }

        public void setList(List<SalesListModel> list) {
            this.list = list;
        }

        List<SalesListModel> list;

    }
}
