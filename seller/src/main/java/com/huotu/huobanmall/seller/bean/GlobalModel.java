package com.huotu.huobanmall.seller.bean;

/**
 * Created by Administrator on 2015/8/28.
 */
public class GlobalModel {

    public String getAboutURL() {
        return aboutURL;
    }

    public String getHelpURL() {
        return helpURL;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setAboutURL(String aboutURL) {
        this.aboutURL = aboutURL;
    }

    public void setHelpURL(String helpURL) {
        this.helpURL = helpURL;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    private String aboutURL;
    private String helpURL;
    private String serverUrl;
}
