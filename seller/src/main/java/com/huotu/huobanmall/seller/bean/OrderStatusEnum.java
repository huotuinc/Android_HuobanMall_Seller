package com.huotu.huobanmall.seller.bean;

/**
 * Created by Administrator on 2015/8/27.
 */
public enum OrderStatusEnum {
    //未发货 = 0,//配货中
    //已发货 = 1,
    //部分发货 = 2,
    //部分退货 = 3,
    //已退货 = 4

    ALL("全部",1),
    NOPAY("待付款",2),
    NODELIVERY("待发货",3),
    FINISH("完成",4);

    private OrderStatusEnum(String name, int index){
        this.name=name;
        this.index=index;
    }
    // 普通方法
    public static String getName(int index) {
        for (OrderStatusEnum c : OrderStatusEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    private String name;

    public void setIndex(int index) {
        this.index = index;
    }

    public void setName(String name) {
        this.name = name;
    }

    private int index;




}
