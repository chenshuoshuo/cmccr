package com.lqkj.web.cmccr2.modules.record.doamin;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * 菜单点击记录
 */
@Entity
@Table(name = "ccr_menu_click_record")
public class CcrMenuClickRecord implements Serializable {

    @Id
    @Column(name = "record_id")
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    private UUID recordId;

    @Column(name = "menu_id")
    private Long menuId;

    @Column
    private String name;

    @CreationTimestamp
    @Column(name = "create_time")
    private Timestamp createTime;

    public CcrMenuClickRecord() {
        this.recordId = UUID.randomUUID();
    }

    public UUID getRecordId() {
        return recordId;
    }

    public void setRecordId(UUID recordId) {
        this.recordId = recordId;
    }

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

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
