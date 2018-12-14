package com.lqkj.web.cmccr2.modules.menu.domain;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * 菜单
 */
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
                Objects.equals(updateTime, ccrMenu.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuId, name, icon, type, sort, status, url, updateTime);
    }

    public enum IpsMenuType {
        embed, url, extend, application
    }
}
