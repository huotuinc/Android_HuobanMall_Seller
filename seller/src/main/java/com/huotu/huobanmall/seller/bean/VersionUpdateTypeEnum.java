package com.huotu.huobanmall.seller.bean;

/**
 * Created by Administrator on 2015/8/31.
 */
public enum VersionUpdateTypeEnum {
    //0 无更新 1增量更新 2 整包更新 3 强制增量更新 4 强制整包更新
    NO("无更新",0),
    INCREMENT("1增量更新",1),
    WHOLE("整包更新",2),
    FORCE_INCREMENT("强制增量更新",3),
    FORCE_WHOLE("强制整包更新",4);

    private VersionUpdateTypeEnum(String name , int index){
        this.name=name;
        this.index=index;
    }
    // 普通方法
    public static String getName(int index) {
        for (VersionUpdateTypeEnum c : VersionUpdateTypeEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    private String name;
    private int index;

    }
