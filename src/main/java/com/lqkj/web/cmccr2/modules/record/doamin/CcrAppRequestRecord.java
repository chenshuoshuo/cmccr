package com.lqkj.web.cmccr2.modules.record.doamin;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * 应用访问记录
 */
@Entity
@Table(name = "ccr_app_request_record")
public class CcrAppRequestRecord {
    /**
     * 访问记录ID
     */
    @Id
    @Column(name = "record_id")
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    private UUID recordId;
    /**
     * 创建时间
     */
    @CreationTimestamp
    @Column(name = "create_time")
    private Timestamp createTime;
    /**
     * 应用ID
     */
    @Column(name = "app_id")
    private long appId;

    public CcrAppRequestRecord() {
        this.recordId = UUID.randomUUID();
    }

    public UUID getRecordId() {
        return recordId;
    }

    public void setRecordId(UUID recordId) {
        this.recordId = recordId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CcrAppRequestRecord that = (CcrAppRequestRecord) o;
        return appId == that.appId &&
                Objects.equals(recordId, that.recordId) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recordId, createTime, appId);
    }
}
