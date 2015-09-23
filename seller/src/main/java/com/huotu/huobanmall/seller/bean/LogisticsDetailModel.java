package com.huotu.huobanmall.seller.bean;

import java.util.List;

/**
 * Created by Administrator on 2015/9/15.
 */
public class LogisticsDetailModel {


    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public List<OrderListProductModel> getList() {
        return list;
    }

    public void setList(List<OrderListProductModel> list) {
        this.list = list;
    }

    private String no;

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    private String pictureURL;
    private String source;
    private String status;
    private String track;
    private List<OrderListProductModel> list;


}
