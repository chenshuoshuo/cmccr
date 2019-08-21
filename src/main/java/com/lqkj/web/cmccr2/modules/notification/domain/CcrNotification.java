package com.lqkj.web.cmccr2.modules.notification.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "消息ID,添加时可不填", required = false)
    private Integer infoId;

    @Column(name = "title")
    @ApiModelProperty(value = "标题", required = true)
    private String title;

    @Column(name = "content")
    @ApiModelProperty(value = "内容", required = true)
    private String content;

    @Column(name = "target_user_role", columnDefinition = " string[]")
    @Type(type = "string-array")
    @ApiModelProperty(value = "面向角色，可添加多个", required = true)
    private String[] targetUserRole;

    @Column(name = "specify_user_id", columnDefinition = " string[]")
    @Type(type = "string-array")
    @ApiModelProperty(value = "指定用户，可添加多个", required = false)
    private String[] specifyUserId;

    @Column(name = "author_id")
    @ApiModelProperty(value = "发布用户", required = true)
    private String authorId;

    @Column(name = "post_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "发布时间，添加时可不填", required = false)
    private Timestamp postTime;

    @Transient
    private String auth;

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


    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
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
                Objects.equals(postTime, that.postTime) &&
                Objects.equals(auth, that.auth);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(infoId, title, content, authorId, postTime, auth);
        result = 31 * result + Arrays.hashCode(targetUserRole);
        result = 31 * result + Arrays.hashCode(specifyUserId);
        return result;
    }
}
