package com.huotu.huobanmall.seller.bean;

import java.util.List;

/**
 * Created by Administrator on 2015/9/15.
 * 物流详情数据
 */
public class LogisticsDetailModel {
    /**
     * 运单编号
     */
    private String no;
    /**
     * 信息来源
     */
    private String source;
    /**
     * 物流状态
     */
    private String status;
    /**
     * 物流跟踪
     */
    private List<LogisticsDataModel> track;
    /**
     * 规格数据
     */
    private List<OrderListProductModel> list;

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    /**
     * 物流(快递)图片
     */
    private String pictureURL;

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

    public List<LogisticsDataModel> getTrack() {
        return track;
    }

    public void setTrack(List<LogisticsDataModel> track) {
        this.track = track;
    }

    public List<OrderListProductModel> getList() {
        return list;
    }

    public void setList(List<OrderListProductModel> list) {
        this.list = list;
    }
}
