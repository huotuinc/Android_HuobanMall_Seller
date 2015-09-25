package com.huotu.huobanmall.seller.bean;

/**
 * Created by Administrator on 2015/9/25.
 */
public enum RoleEnum {
    产品管理("产品管理",1),
    订单管理("订单管理",2),
    分销商("分销商",3),
    商品("商品",4),
    会员统计("会员统计",5),
    销售额统计("销售额统计",6),
    销售明细("销售明细",7),
    返利积分统计("返利积分统计",8),
    消费统计("消费统计",9),
    设置管理("设置管理",100);

    private String name;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Integer index;

    private RoleEnum(String name , int index){
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (RoleEnum c : RoleEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }


}
