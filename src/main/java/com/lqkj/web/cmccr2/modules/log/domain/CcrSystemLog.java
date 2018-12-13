package com.lqkj.web.cmccr2.modules.log.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.springframework.cache.annotation.Cacheable;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * 系统日志
 */
@Entity
@Table(name = "ccr_sys_log")
public class CcrSystemLog implements Serializable {

    @Id
    @Column(name = "log_id")
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    private UUID logId;

    @Column(name = "user_id")
    private Long userId;

    @Column
    private String source;

    @Column
    private String method;

    @Column
    private String description;

    @CreationTimestamp
    @Column(name = "create_time")
    private Timestamp createTime;

    public CcrSystemLog() {
        this.logId = UUID.randomUUID();
    }

    public CcrSystemLog(String source, String method, String description) {
        this.logId = UUID.randomUUID();
        this.source = source;
        this.method = method;
        this.description = description;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UUID getLogId() {
        return logId;
    }

    public void setLogId(UUID logId) {
        this.logId = logId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CcrSystemLog that = (CcrSystemLog) o;
        return Objects.equals(logId, that.logId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(source, that.source) &&
                Objects.equals(method, that.method) &&
                Objects.equals(description, that.description) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logId, userId, source, method, description, createTime);
    }
}
