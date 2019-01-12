package com.lqkj.web.cmccr2.modules.application.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class CcrApplicationHasUsersPK implements Serializable {
    private long ccrPcApplicationAppId;
    private String hasUsersUserCode;

    @Column(name = "ccr_pc_application_app_id")
    @Id
    public long getCcrPcApplicationAppId() {
        return ccrPcApplicationAppId;
    }

    public void setCcrPcApplicationAppId(long ccrPcApplicationAppId) {
        this.ccrPcApplicationAppId = ccrPcApplicationAppId;
    }

    @Column(name = "has_users_user_code")
    @Id
    public String getHasUsersUserCode() {
        return hasUsersUserCode;
    }

    public void setHasUsersUserCode(String hasUsersUserCode) {
        this.hasUsersUserCode = hasUsersUserCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) return true;
        if (o==null || getClass()!=o.getClass()) return false;
        CcrApplicationHasUsersPK that = (CcrApplicationHasUsersPK) o;
        return ccrPcApplicationAppId==that.ccrPcApplicationAppId &&
                Objects.equals(hasUsersUserCode, that.hasUsersUserCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ccrPcApplicationAppId, hasUsersUserCode);
    }
}
