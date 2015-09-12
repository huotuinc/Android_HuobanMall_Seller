package com.huotu.huobanmall.seller.bean;

/**
 * Created by Administrator on 2015/9/11.
 */
public enum  EditSetTypeEnum {
    SHOPNAME("店铺名称",0),
    SHOPDESCRIPTION("店铺描述",1),
    NICKNAME("昵称",3),
    LOGO("店铺LOGO",2),
    BILLNOTICE("订单支付成功通知（0关闭,1开启）",4),
    ADDHUOBANNOTICE("新增小伙伴通知（0关闭，1开启）",5),
    NIGHT("夜间免打扰模式 0 关闭 1 默认开启",6);

    private EditSetTypeEnum(String name , int index){
        this.name=name;
        this.index=index;
    }

    public static EditSetTypeEnum valueOf(int value){
        switch (value){
            case 0:
                return EditSetTypeEnum.SHOPNAME;
            case 1:
                return EditSetTypeEnum.SHOPDESCRIPTION;
            case 2:
                return EditSetTypeEnum.LOGO;
            case 3:
                return EditSetTypeEnum.NICKNAME;
            case 4 :
                return EditSetTypeEnum.BILLNOTICE;
            case 5:
                return EditSetTypeEnum.ADDHUOBANNOTICE;
            case 6:
                return EditSetTypeEnum.NIGHT;
            default:
                return EditSetTypeEnum.SHOPNAME;
        }
    }

    // 普通方法
    public static String getName(int index) {
        for (EditSetTypeEnum c : EditSetTypeEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private int index;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

}
