package com.huotu.huobanmall.seller.bean;

import java.util.Date;

/**
 * Created by Administrator on 2015/8/26.
 */
public class BillReportModel {
    public Integer getAmount() {
        return amount;
    }

    public Date getTime() {
        return time;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    private Integer amount;
    private Date time;
}
