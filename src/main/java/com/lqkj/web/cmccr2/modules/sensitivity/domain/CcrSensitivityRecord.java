package com.lqkj.web.cmccr2.modules.sensitivity.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/**
 * 敏感词过滤记录
 */
@Entity
@Table(name = "ccr_sensitivity_record")
public class CcrSensitivityRecord implements Serializable {

    @Id
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    @Column(name = "record_id")
    private UUID recordId;

    @Column
    private String source;

    @Column(name = "user_name")
    private String userName;

    @Column
    private String content;

    @Column(name = "sensitivity_words")
    private String sensitivityWords;

    @Enumerated(EnumType.STRING)
    @Column(name = "handle_type")
    private CcrSensitivityWord.HandleType handleType;

    @Column
    @CreationTimestamp
    private Timestamp createTime;

    public CcrSensitivityRecord(String source, String content, String sensitivityWords,
                                CcrSensitivityWord.HandleType handleType) {
        this.recordId = UUID.randomUUID();
        this.source = source;
        this.content = content;
        this.sensitivityWords = sensitivityWords;
        this.handleType = handleType;
    }

    public CcrSensitivityRecord() {
        this.recordId = UUID.randomUUID();
    }

    public UUID getRecordId() {
        return recordId;
    }

    public void setRecordId(UUID recordId) {
        this.recordId = recordId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSensitivityWords() {
        return sensitivityWords;
    }

    public void setSensitivityWords(String sensitivityWords) {
        this.sensitivityWords = sensitivityWords;
    }

    public CcrSensitivityWord.HandleType getHandleType() {
        return handleType;
    }

    public void setHandleType(CcrSensitivityWord.HandleType handleType) {
        this.handleType = handleType;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) return true;
        if (o==null || getClass()!=o.getClass()) return false;
        CcrSensitivityRecord that = (CcrSensitivityRecord) o;
        return Objects.equals(recordId, that.recordId) &&
                Objects.equals(source, that.source) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(content, that.content) &&
                Objects.equals(sensitivityWords, that.sensitivityWords) &&
                handleType==that.handleType &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recordId, source, userName, content, sensitivityWords, handleType, createTime);
    }
}
