package com.huotu.huobanmall.seller.bean;

import java.util.List;

/**
 * Created by Administrator on 2015/9/17.
 */
public class MJTopConsumeModel extends BaseModel {
    public InnerClass getResultData() {
        return resultData;
    }

    public void setResultData(InnerClass resultData) {
        this.resultData = resultData;
    }

    private InnerClass resultData;

    public class InnerClass{
        public List<TopConsumeModel> getList() {
            return list;
        }

        public void setList(List<TopConsumeModel> list) {
            this.list = list;
        }

        private List<TopConsumeModel> list;
    }
}
