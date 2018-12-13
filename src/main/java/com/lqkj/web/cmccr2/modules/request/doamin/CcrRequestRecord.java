package com.lqkj.web.cmccr2.modules.request.doamin;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * 请求记录
 */
@Entity
@Table(name = "ccr_request_record")
public class CcrRequestRecord implements Serializable {

    @Id
    @Column(name = "record_id")
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    private UUID recordId;

    @Column(nullable = false)
    private String ip;

    @CreationTimestamp
    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String method;

    @Column(nullable = false)
    private Boolean successed;

    @Column
    private String exception;

    public CcrRequestRecord() {
        this.recordId = UUID.randomUUID();
    }

    public UUID getRecordId() {
        return recordId;
    }

    public void setRecordId(UUID recordId) {
        this.recordId = recordId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Boolean getSuccessed() {
        return successed;
    }

    public void setSuccessed(Boolean successed) {
        this.successed = successed;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CcrRequestRecord that = (CcrRequestRecord) o;
        return Objects.equals(recordId, that.recordId) &&
                Objects.equals(ip, that.ip) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(url, that.url) &&
                Objects.equals(method, that.method) &&
                Objects.equals(successed, that.successed) &&
                Objects.equals(exception, that.exception);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recordId, ip, createTime, url, method, successed, exception);
    }
}
