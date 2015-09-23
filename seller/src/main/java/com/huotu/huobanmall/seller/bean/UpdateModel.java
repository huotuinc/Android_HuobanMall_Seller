package com.huotu.huobanmall.seller.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/8/28.
 */
public class UpdateModel implements Serializable{

    private String updateTips;
    private UpdateType updateType;
    private String updateUrl;
    private String updateMD5;

    public String getUpdateMD5() {
        return updateMD5;
    }

    public String getUpdateTips() {
        return updateTips;
    }


    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateMD5(String updateMD5) {
        this.updateMD5 = updateMD5;
    }

    public UpdateType getUpdateType ( ) {
        return updateType;
    }

    public  void setUpdateType ( UpdateType updateType ) {
        this.updateType = updateType;
    }

    public void setUpdateTips(String updateTips) {

        this.updateTips = updateTips;
    }


    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }

    public class UpdateType
    {
        private String name;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private int value;
    }
}
