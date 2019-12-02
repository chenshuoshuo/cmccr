package com.lqkj.web.cmccr2.modules.record.doamin;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * 网关访问记录统计
 */
@Entity
@Table(name = "ccr_access_log")
public class CcrAccessLogRecord {
    /**
     * 访问记录ID
     */
    @Id
    @Column(name = "log_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer logId;
    /**
     * 访问用户ID
     */
    @Column(name = "user_id")
    private String userId;
    /**
     * 用户组
     */
    @Column(name = "user_group")
    private String userGroup;
    /**
     * IP地址
     */
    @Column(name = "ip_address")
    private String ipAddress;
    /**
     * 访问时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "log_time")
    private Timestamp logTime;

    @Column(name = "create_month")
    private String createMonth;

    @Column(name = "create_date")
    private String createDate;

    @Column(name = "create_hour")
    private String createHour;



    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setLogTime(Timestamp logTime) {
        this.logTime = logTime;
    }

    public Timestamp getLogTime() {
        return logTime;
    }

    public String getCreateMonth() {
        return createMonth;
    }

    public void setCreateMonth(String createMonth) {
        this.createMonth = createMonth;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateHour() {
        return createHour;
    }

    public void setCreateHour(String createHour) {
        this.createHour = createHour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CcrAccessLogRecord that = (CcrAccessLogRecord) o;
        return userGroup == that.userGroup &&
                Objects.equals(logId, that.logId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(ipAddress, that.ipAddress) &&
                Objects.equals(logTime, that.logTime) &&
                Objects.equals(createDate, that.createDate) &&
                Objects.equals(createMonth, that.createMonth) &&
                Objects.equals(createHour, that.createHour)
                ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(logId, userId, userGroup, ipAddress, logTime,createDate,createMonth,createHour);
    }
}
