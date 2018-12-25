package com.lqkj.web.cmccr2.modules.application.domain;

import com.lqkj.web.cmccr2.modules.user.domain.CcrUser;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 门户应用
 */
@TypeDef(name = "string-array", typeClass = StringArrayType.class)
@Entity
@Table(name = "ccr_application", indexes = {
        @Index(name = "name_index", columnList = "name", unique = true),
        @Index(name = "name_en_index", columnList = "name_en", unique = true)
})
public class CcrPcApplication implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "app_id")
    private Long appId;

    @Column(name = "icon",columnDefinition = " text")
    private String icon;

    @Column(name = "name")
    private String name;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "v")
    private Integer version;

    @Column(name = "url")
    private String url;

    @Column(name = "has_qr_code")
    private Boolean hasQRCode;

    @Column(name = "description")
    private String description;

    @Column(name = "enabled")
    private Boolean enabled;

    @OrderBy
    @Column(name = "sort")
    private Integer sort;

    @UpdateTimestamp
    @Column(name = "update_time")
    private Timestamp updateTime;

    @Column
    @Enumerated(EnumType.STRING)
    private IpsApplicationPlatform platform;

    @Type(type = "string-array")
    @Column(name = "has_roles", columnDefinition = " text[]")
    @Enumerated(EnumType.STRING)
    private String[] hasRoles;

    @Column(name = "has_users")
    @ManyToMany(targetEntity = CcrUser.class)
    private List<Long> hasUsers;

    public IpsApplicationPlatform getPlatform() {
        return platform;
    }

    public void setPlatform(IpsApplicationPlatform platform) {
        this.platform = platform;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getHasQRCode() {
        return hasQRCode;
    }

    public void setHasQRCode(Boolean hasQRCode) {
        this.hasQRCode = hasQRCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String[] getHasRoles() {
        return hasRoles;
    }

    public void setHasRoles(String[] hasRoles) {
        this.hasRoles = hasRoles;
    }

    public List<Long> getHasUsers() {
        return hasUsers;
    }

    public void setHasUsers(List<Long> hasUsers) {
        this.hasUsers = hasUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CcrPcApplication that = (CcrPcApplication) o;
        return Objects.equals(appId, that.appId) &&
                Objects.equals(icon, that.icon) &&
                Objects.equals(name, that.name) &&
                Objects.equals(nameEn, that.nameEn) &&
                Objects.equals(version, that.version) &&
                Objects.equals(url, that.url) &&
                Objects.equals(hasQRCode, that.hasQRCode) &&
                Objects.equals(description, that.description) &&
                Objects.equals(enabled, that.enabled) &&
                Objects.equals(sort, that.sort) &&
                Objects.equals(updateTime, that.updateTime) &&
                platform == that.platform &&
                Arrays.equals(hasRoles, that.hasRoles) &&
                Objects.equals(hasUsers, that.hasUsers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appId, icon, name, nameEn, version, url, hasQRCode, description,
                enabled, sort, updateTime, platform, hasRoles, hasUsers);
    }

    public enum IpsApplicationPlatform {
        system, pc, mobile, mobile_android, mobile_ios, mobile_h5, third
    }
}
