package com.huotu.huobanmall.seller.bean;

import java.util.List;

/**
 * Created by Administrator on 2015/9/9.
 */
public class MJGoodModel extends  BaseModel{
    public InnerClass getResultData() {
        return resultData;
    }

    public void setResultData(InnerClass resultData) {
        this.resultData = resultData;
    }

    private InnerClass resultData;

   public class InnerClass {
       public List<GoodsModel> getList() {
           return list;
       }

       public void setList(List<GoodsModel> list) {
           this.list = list;
       }

       List<GoodsModel> list;
   }

}
