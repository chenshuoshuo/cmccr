package com.lqkj.web.cmccr2.modules.application.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * 推荐应用
 */
public class RecommendedApplicationVO{

    /**
     * 推荐应用
     */

    private String appId;
    private String appName;
    private String appUrl;
    private String startTime;
    private String endTime;
    private String appLogo;
    private Integer orderId;
    private String memo;

    public RecommendedApplicationVO() {
    }

    public RecommendedApplicationVO(String appId, String appName, String appUrl, String startTime, String endTime, String appLogo, Integer orderId, String memo) {
        this.appId = appId;
        this.appName = appName;
        this.appUrl = appUrl;
        this.startTime = startTime;
        this.endTime = endTime;
        this.appLogo = appLogo;
        this.orderId = orderId;
        this.memo = memo;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAppLogo() {
        return appLogo;
    }

    public void setAppLogo(String appLogo) {
        this.appLogo = appLogo;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
