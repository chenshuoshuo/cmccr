package com.lqkj.web.cmccr2.modules.notification.domain;

import com.vladmihalcea.hibernate.type.array.StringArrayType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;

/**
 * 系统通知
 */
@TypeDef(name = "string-array", typeClass = StringArrayType.class)
@Entity
@Table(name = "ccr_notification")
public class CcrNotification implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "info_id")
    private Integer infoId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "target_user_role", columnDefinition = " string[]")
    @Type(type = "string-array")
    private String[] targetUserRole;

    @Column(name = "specify_user_id", columnDefinition = " string[]")
    @Type(type = "string-array")
    private String[] specifyUserId;

    @Column(name = "author_id")
    private String authorId;

    @Column(name = "post_time")
    private Timestamp postTime;

    public Integer getInfoId() {
        return infoId;
    }

    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getTargetUserRole() {
        return targetUserRole;
    }

    public void setTargetUserRole(String[] targetUserRole) {
        this.targetUserRole = targetUserRole;
    }

    public String[] getSpecifyUserId() {
        return specifyUserId;
    }

    public void setSpecifyUserId(String[] specifyUserId) {
        this.specifyUserId = specifyUserId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public Timestamp getPostTime() {
        return postTime;
    }

    public void setPostTime(Timestamp postTime) {
        this.postTime = postTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CcrNotification that = (CcrNotification) o;
        return Objects.equals(infoId, that.infoId) &&
                Objects.equals(title, that.title) &&
                Objects.equals(content, that.content) &&
                Arrays.equals(targetUserRole, that.targetUserRole) &&
                Arrays.equals(specifyUserId, that.specifyUserId) &&
                Objects.equals(authorId, that.authorId) &&
                Objects.equals(postTime, that.postTime);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(infoId, title, content, authorId, postTime);
        result = 31 * result + Arrays.hashCode(targetUserRole);
        result = 31 * result + Arrays.hashCode(specifyUserId);
        return result;
    }
}
