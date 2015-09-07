package com.huotu.huobanmall.seller.bean;

/**
 * Created by Administrator on 2015/8/27.
 */
public class MenuModel {
    public void setName(String name) {
        this.name = name;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    private String name;

    public String getName() {
        return name;
    }

    public int getIcon() {
        return icon;
    }

    private int icon;

}
