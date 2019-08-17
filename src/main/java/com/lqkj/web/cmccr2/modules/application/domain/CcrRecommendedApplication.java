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
@ApiModel(description = "推荐应用")
@Entity
@Table(name = "ccr_recommended_application")
public class CcrRecommendedApplication implements Serializable {

    /**
     * 推荐应用
     */
    @ApiModelProperty(value = "应用ID,(使用UUID)",hidden = true)
    @Column(name = "app_id", length = 256, nullable = false, unique = true)
    @Id
    private String appId;

    @ApiModelProperty(value = "应用名称", required = true)
    @Column(name = "app_name",length = 10,nullable = false)
    private String appName;

    @ApiModelProperty(value = "应用连接", required = true)
    @Column(name = "app_url",nullable = false)
    private String appUrl;

    @ApiModelProperty(value = "推荐开始时间,格式：年月日时", required = true)
    @Column(name = "start_time",nullable = false)
    private Timestamp startTime;

    @ApiModelProperty(value = "推荐结束时间，格式：年月日时", required = true)
    @Column(name = "end_time",nullable = false)
    private Timestamp endTime;

    @ApiModelProperty(value = "应用Logo", required = false)
    @Column(name = "app_logo")
    private String appLogo;

    @ApiModelProperty(value = "排序,只能正整数", required = true)
    @Column(name = "order_id",nullable = false)
    private Integer orderId;

    @ApiModelProperty(value = "备注", required = false)
    @Column(name = "memo")
    private String memo;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
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

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CcrRecommendedApplication that = (CcrRecommendedApplication) o;
        return Objects.equals(appId, that.appId) &&
                Objects.equals(appName, that.appName) &&
                Objects.equals(appUrl, that.appUrl) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime) &&
                Objects.equals(appLogo, that.appLogo) &&
                Objects.equals(orderId, that.orderId) &&
                Objects.equals(memo, that.memo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), appId, appName, appUrl, startTime, endTime, appLogo, orderId, memo);
    }
}
