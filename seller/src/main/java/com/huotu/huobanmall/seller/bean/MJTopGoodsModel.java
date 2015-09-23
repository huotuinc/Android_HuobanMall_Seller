package com.huotu.huobanmall.seller.bean;

import java.util.List;

/**
 * Created by Administrator on 2015/9/23.
 */
public class MJTopGoodsModel extends BaseModel {
    public InnerClass getResultData() {
        return resultData;
    }

    public void setResultData(InnerClass resultData) {
        this.resultData = resultData;
    }

    private InnerClass resultData;

    public class InnerClass{
        public List<TopGoodsModel> getList() {
            return list;
        }

        public void setList(List<TopGoodsModel> list) {
            this.list = list;
        }

        private List<TopGoodsModel> list;
    }
}
