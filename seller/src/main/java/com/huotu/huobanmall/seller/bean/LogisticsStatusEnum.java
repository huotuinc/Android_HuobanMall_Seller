package com.huotu.huobanmall.seller.bean;

/**
 * Created by Administrator on 2015/9/24.
 */
public enum LogisticsStatusEnum {
    SUCC("成功到达","succ"),
    FAILED("发货失败","failed"),
    CANCEL("已取消","cancel"),
    LOST("货物丢失","lost"),
    PROGRESS("运送中","progress"),
    TIMEOUT("超时","timeout"),
    READY("准备发货","ready");

    private String name;
    private String index;

    public String getName() {
        return name;
    }

    public String getIndex() {
        return index;
    }
    public void setIndex(String index) {
        this.index = index;
    }

    public void setName(String name) {
        this.name = name;
    }

    private LogisticsStatusEnum(String name , String index ){
        this.name = name;
        this.index = index;
    }

    // 普通方法
    public static String getName(String index) {
        for (LogisticsStatusEnum c : LogisticsStatusEnum.values()) {
            if (c.getIndex().equals(index)) {
                return c.name;
            }
        }
        return null;
    }
}
