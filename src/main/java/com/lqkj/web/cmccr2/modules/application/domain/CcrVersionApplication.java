package com.lqkj.web.cmccr2.modules.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * 版本管理的应用
 */
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "ccr_version_application", indexes = {
        @Index(name = "name_index", columnList = "name", unique = true)
})
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes(value = {
        @JsonSubTypes.Type(name = "ios", value = CcrIosApplication.class),
        @JsonSubTypes.Type(name = "android", value = CcrAndroidApplication.class),
        @JsonSubTypes.Type(name = "web", value = CcrWebApplication.class)
})
@ApiModel(description = "应用信息")
public class CcrVersionApplication implements Serializable {

    @ApiModelProperty(value = "应用id", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 应用名称
     */
    @ApiModelProperty(value = "应用名称", required = true)
    @Column(length = 128, nullable = false)
    private String name;

    /**
     * 应用介绍
     */
    @ApiModelProperty("应用介绍")
    @Column(length = 512)
    private String description;

    /**
     * 应用类型
     */
    @ApiModelProperty(value = "应用类型", allowableValues = "ios,android,web", required = true)
    @Column(length = 128, nullable = false)
    private String type;

    /**
     * logo路径
     */
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    @Column(name = "icon_path", length = 1024)
    private String iconPath;

    @ApiModelProperty(value = "图片URL地址")
    private String iconURL;

    /**
     * 更新说明
     */
    @ApiModelProperty("更新说明")
    @Column(name = "update_description", length = 1024)
    private String updateDescription;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    @CreationTimestamp
    @Column(name = "create_time")
    private Timestamp createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    @UpdateTimestamp
    @Column(name = "update_time")
    private Timestamp updateTime;

    public String getIconURL() {
        return iconURL;
    }

    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getUpdateDescription() {
        return updateDescription;
    }

    public void setUpdateDescription(String updateDescription) {
        this.updateDescription = updateDescription;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CcrVersionApplication that = (CcrVersionApplication) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
