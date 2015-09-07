package com.huotu.huobanmall.seller.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/8/28.
 */
public class UpdateModel implements Serializable{

    private String updateTips;
    private VersionUpdateTypeEnum updateType;
    private String updateUrl;
    private String updateMD5;

    public String getUpdateMD5() {
        return updateMD5;
    }

    public String getUpdateTips() {
        return updateTips;
    }

    public VersionUpdateTypeEnum getUpdateType() {
        return updateType;
    }

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateMD5(String updateMD5) {
        this.updateMD5 = updateMD5;
    }

    public void setUpdateTips(String updateTips) {
        this.updateTips = updateTips;
    }

    public void setUpdateType(VersionUpdateTypeEnum updateType) {
        this.updateType = updateType;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }
}
