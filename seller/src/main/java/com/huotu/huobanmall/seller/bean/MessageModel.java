package com.huotu.huobanmall.seller.bean;

/**
 * Created by Administrator on 2015/9/28.
 */
public class MessageModel {
    private int messageid;
    private long messageOrder;
    private String context;
    private long date;

    public int getMessageid() {
        return messageid;
    }

    public void setMessageid(int messageid) {
        this.messageid = messageid;
    }

    public long getMessageOrder() {
        return messageOrder;
    }

    public void setMessageOrder(long messageOrder) {
        this.messageOrder = messageOrder;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
