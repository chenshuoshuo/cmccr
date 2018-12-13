package com.lqkj.web.cmccr2.modules.sensitivity.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * 违禁字
 */
@ApiModel(description = "违禁字")
@Entity
@Table(name = "ccr_sensitivity_word", indexes = {@Index(name = "word_index", columnList = "word", unique = true)})
public class CcrSensitivityWord implements Serializable {

    @ApiModelProperty(value = "违禁字id", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "违禁字内容")
    @Column(length = 64)
    private String word;

    @CreationTimestamp
    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "replace_content")
    private String replaceContent;

    @Column(name = "use_count")
    private Long useCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "handle_type")
    private HandleType handleType;

    public CcrSensitivityWord() {
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getReplaceContent() {
        return replaceContent;
    }

    public void setReplaceContent(String replaceContent) {
        this.replaceContent = replaceContent;
    }

    public Long getUseCount() {
        return useCount;
    }

    public void setUseCount(Long useCount) {
        this.useCount = useCount;
    }

    public HandleType getHandleType() {
        return handleType;
    }

    public void setHandleType(HandleType handleType) {
        this.handleType = handleType;
    }

    public CcrSensitivityWord(String word) {
        this.word = word;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CcrSensitivityWord that = (CcrSensitivityWord) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(word, that.word) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(replaceContent, that.replaceContent) &&
                Objects.equals(useCount, that.useCount) &&
                handleType == that.handleType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, word, createTime, replaceContent, useCount, handleType);
    }

    @Override
    public String toString() {
        return "CcrSensitivityWord{" +
                "id=" + id +
                ", word='" + word + '\'' +
                ", createTime=" + createTime +
                ", replaceContent='" + replaceContent + '\'' +
                ", useCount=" + useCount +
                ", handleType=" + handleType +
                '}';
    }

    public enum HandleType {
        prevent, replace
    }
}
