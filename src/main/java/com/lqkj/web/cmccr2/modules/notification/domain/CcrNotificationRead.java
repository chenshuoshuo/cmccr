package com.lqkj.web.cmccr2.modules.notification.domain;

import com.vladmihalcea.hibernate.type.array.StringArrayType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;

/**
 * 系统通知
 */
@Entity
@Table(name = "ccr_notification_read")
public class CcrNotificationRead implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "read_id")
    private Integer readId;

    @Column(name = "info_id")
    private Integer infoId;

    @Column(name = "user_code")
    private String userCode;

    @Column(name = "read_time")
    private Timestamp readTime;

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "memo")
    private String memo;

    public Integer getReadId() {
        return readId;
    }

    public void setReadId(Integer readId) {
        this.readId = readId;
    }

    public Integer getInfoId() {
        return infoId;
    }

    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Timestamp getReadTime() {
        return readTime;
    }

    public void setReadTime(Timestamp readTime) {
        this.readTime = readTime;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CcrNotificationRead that = (CcrNotificationRead) o;
        return Objects.equals(readId, that.readId) &&
                Objects.equals(infoId, that.infoId) &&
                Objects.equals(userCode, that.userCode) &&
                Objects.equals(readTime, that.readTime) &&
                Objects.equals(orderId, that.orderId) &&
                Objects.equals(memo, that.memo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(readId, infoId, userCode, readTime, orderId, memo);
    }
}
