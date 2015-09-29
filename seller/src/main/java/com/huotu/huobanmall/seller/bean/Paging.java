package com.huotu.huobanmall.seller.bean;

/**
 * Created by Administrator on 2015/9/28.
 */
public class Paging {
    private Integer pagingSize;
    private String pagingTag;

    public String getPagingTag()
    {
        return pagingTag;
    }
    public void setPagingTag(String pagingTag)
    {
        this.pagingTag = pagingTag;
    }
    public Integer getPagingSize()
    {
        return pagingSize;
    }
    public void setPagingSize(Integer pagingSize)
    {
        this.pagingSize = pagingSize;
    }

}
