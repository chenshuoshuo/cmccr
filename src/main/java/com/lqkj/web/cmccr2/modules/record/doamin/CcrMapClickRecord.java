package com.lqkj.web.cmccr2.modules.record.doamin;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;

/**
 * 地图点击记录
 */
@Entity
@Table(name = "ccr_click_record")
public class CcrMapClickRecord implements Serializable {

    @Id
    @Column(name = "record_id")
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    private UUID recordId;

    @Column(name = "way_id")
    private Long wayId;

    @Column(name = "node_id")
    private Long nodeId;

    @Column
    private String name;

    public CcrMapClickRecord() {
        this.recordId = UUID.randomUUID();
    }

    public UUID getRecordId() {
        return recordId;
    }

    public void setRecordId(UUID recordId) {
        this.recordId = recordId;
    }

    public Long getWayId() {
        return wayId;
    }

    public void setWayId(Long wayId) {
        this.wayId = wayId;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
