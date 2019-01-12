package com.lqkj.web.cmccr2.modules.application.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lqkj.web.cmccr2.modules.menu.domain.CcrMenu;
import com.lqkj.web.cmccr2.modules.user.domain.CcrUser;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import io.swagger.annotations.ApiModelProperty;
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

    private Long appId;

    private String icon;

    private String name;

    private String nameEn;

    private Integer version;

    private String url;

    private Boolean hasQRCode;

    private String description;

    private Boolean enabled;

    private Integer sort;

    private Timestamp updateTime;

    private IpsApplicationPlatform platform;

    private String[] hasRoles;

    private List<String> hasUsers;

    private Long parentMenu;

    @Column
    @Enumerated(EnumType.STRING)
    public IpsApplicationPlatform getPlatform() {
        return platform;
    }

    public void setPlatform(IpsApplicationPlatform platform) {
        this.platform = platform;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "app_id")
    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    @Column(name = "icon", columnDefinition = " text")
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "name_en")
    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    @Column(name = "v")
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "has_qr_code")
    public Boolean getHasQRCode() {
        return hasQRCode;
    }

    public void setHasQRCode(Boolean hasQRCode) {
        this.hasQRCode = hasQRCode;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "enabled")
    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @OrderBy
    @Column(name = "sort")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @UpdateTimestamp
    @Column(name = "update_time")
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Type(type = "string-array")
    @Column(name = "has_roles", columnDefinition = " text[]")
    public String[] getHasRoles() {
        return hasRoles;
    }

    public void setHasRoles(String[] hasRoles) {
        this.hasRoles = hasRoles;
    }

    @Transient
    public List<String> getHasUsers() {
        return hasUsers;
    }

    public void setHasUsers(List<String> hasUsers) {
        this.hasUsers = hasUsers;
    }

    @Column(name = "parent_menu_id")
    public Long getParentMenu() {
        return parentMenu;
    }

    public void setParentMenu(Long parentMenu) {
        this.parentMenu = parentMenu;
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) return true;
        if (o==null || getClass()!=o.getClass()) return false;
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
                platform==that.platform &&
                Arrays.equals(hasRoles, that.hasRoles) &&
                Objects.equals(hasUsers, that.hasUsers) &&
                Objects.equals(parentMenu, that.parentMenu);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(appId, icon, name, nameEn, version, url, hasQRCode, description,
                enabled, sort, updateTime, platform, hasUsers, parentMenu);
        result = 31 * result + Arrays.hashCode(hasRoles);
        return result;
    }

    public enum IpsApplicationPlatform {
        system, pc, mobile, mobile_android, mobile_ios, mobile_h5, third
    }
}
