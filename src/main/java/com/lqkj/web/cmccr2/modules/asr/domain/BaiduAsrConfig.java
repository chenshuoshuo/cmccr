package com.lqkj.web.cmccr2.modules.asr.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

/**
 * 百度asr配置信息
 *
 */
@Entity
@Table(name = "ccr_baidu_asr_config")
public class BaiduAsrConfig {
    /**
     * 配置ID
     */
    @Id
    @Column(name = "config_id")
    private Integer configId;
    /**
     * 应用的APP KEY
     */
    @Column(name = "client_id")
    private String clientId;
    /**
     * 应用的APP SECRET
     */
    @Column(name = "client_secret")
    private String clientSecret;
    /**
     * 应用的ACCESS TOKEN
     */
    @Column(name = "access_token")
    private String accessToken;
    /**
     * ACCESS TOKEN 的更新时间
     */
    @UpdateTimestamp
    @Column(name = "update_time")
    private Date updateTime;
    /**
     * ACCESS TOKEN 的失效时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "invalid_time")
    private Date invalidTime;

    public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(Date invalidTime) {
        this.invalidTime = invalidTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaiduAsrConfig that = (BaiduAsrConfig) o;
        return Objects.equals(configId, that.configId) &&
                Objects.equals(clientId, that.clientId) &&
                Objects.equals(clientSecret, that.clientSecret) &&
                Objects.equals(accessToken, that.accessToken) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(invalidTime, that.invalidTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(configId, clientId, clientSecret, accessToken, updateTime, invalidTime);
    }
}
