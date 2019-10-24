package com.lqkj.web.cmccr2.modules.menu.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

/**
 * 菜单
 */
//@Cacheable
@Entity
@Table(name = "ccr_menu", indexes = {
        @Index(name = "name_index", columnList = "name", unique = true)
})
public class CcrMenu implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "name")
    private String name;

    @Column(name = "icon", columnDefinition = " text")
    private String icon;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private IpsMenuType type;

    @Column(name = "sort")
    private Integer sort;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "url", length = 1024)
    private String url;

    @UpdateTimestamp
    @Column(name = "update_time")
    private Timestamp updateTime;

    @Column(name = "target_user_role", columnDefinition = " string[]")
    @Type(type = "string-array")
    private String[] targetUserRole;

    @Column(name = "specify_user_id", columnDefinition = " string[]")
    @Type(type = "string-array")
    private String[] specifyUserId;

    @ApiModelProperty(value = "父节点")
    @Column(name = "parent_id")
    private Long parentId;

    @ApiModelProperty(value = "英文名")
    @Column(name = "ename")
    private String ename;

    @ApiModelProperty(value = "菜单模式:right;left;right,left")
    @Column(name = "menu_mode")
    private String menuMode;

    @ApiModelProperty(value = "应用类别:pc,h5")
    @Column(name = "app_type")
    @Enumerated(EnumType.STRING)
    private AppType appType;

    @ApiModelProperty(value = "是否是二维码")
    @Column(name = "has_qr_code")
    private Boolean hasQrCode;

    @ApiModelProperty(value = "移动端排序")
    @Column(name = "mobile_sort")
    private Long mobileSort;

    @ApiModelProperty(value = "是否开启")
    @Column(name = "open")
    private Boolean open;

    @Transient
    private Set<CcrMenu> chCcrMenu;


    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public IpsMenuType getType() {
        return type;
    }

    public void setType(IpsMenuType type) {
        this.type = type;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getMenuMode() {
        return menuMode;
    }

    public void setMenuMode(String menuMode) {
        this.menuMode = menuMode;
    }

    public AppType getAppType() {
        return appType;
    }

    public void setAppType(AppType appType) {
        this.appType = appType;
    }

    public Boolean getHasQrCode() {
        return hasQrCode;
    }

    public void setHasQrCode(Boolean hasQrCode) {
        this.hasQrCode = hasQrCode;
    }

    public Long getMobileSort() {
        return mobileSort;
    }

    public void setMobileSort(Long mobileSort) {
        this.mobileSort = mobileSort;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CcrMenu ccrMenu = (CcrMenu) o;
        return Objects.equals(menuId, ccrMenu.menuId) &&
                Objects.equals(name, ccrMenu.name) &&
                Objects.equals(icon, ccrMenu.icon) &&
                type == ccrMenu.type &&
                Objects.equals(sort, ccrMenu.sort) &&
                Objects.equals(status, ccrMenu.status) &&
                Objects.equals(url, ccrMenu.url) &&
                Objects.equals(updateTime, ccrMenu.updateTime) &&
                Arrays.equals(targetUserRole, ccrMenu.targetUserRole) &&
                Arrays.equals(specifyUserId, ccrMenu.specifyUserId) &&
                Objects.equals(parentId, ccrMenu.parentId) &&
                Objects.equals(ename, ccrMenu.ename) &&
                Objects.equals(menuMode, ccrMenu.menuMode) &&
                appType == ccrMenu.appType &&
                Objects.equals(hasQrCode, ccrMenu.hasQrCode) &&
                Objects.equals(mobileSort, ccrMenu.mobileSort) &&
                Objects.equals(open, ccrMenu.open) &&
                Objects.equals(chCcrMenu, ccrMenu.chCcrMenu);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(menuId, name, icon, type, sort, status, url, updateTime, parentId, ename, menuMode, appType, hasQrCode, mobileSort, open, chCcrMenu);
        result = 31 * result + Arrays.hashCode(targetUserRole);
        result = 31 * result + Arrays.hashCode(specifyUserId);
        return result;
    }

    public Set<CcrMenu> getChCcrMenu() {
        return chCcrMenu;
    }

    public void setChCcrMenu(Set<CcrMenu> chCcrMenu) {
        this.chCcrMenu = chCcrMenu;
    }

    public enum IpsMenuType {
        sysMenu,builtInMenu,builtInApp,extMenu,urlApp,mobApp
    }

    public enum AppType {
        pc,h5,pch5
    }
}
