package com.lqkj.web.cmccr2.modules.application.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ccr_application_has_users")
@IdClass(CcrApplicationHasUsersPK.class)
public class CcrApplicationHasUsers {
    private Long ccrPcApplicationAppId;
    private String hasUsersUserCode;

    public CcrApplicationHasUsers() {
    }

    public CcrApplicationHasUsers(Long ccrPcApplicationAppId, String hasUsersUserCode) {
        this.ccrPcApplicationAppId = ccrPcApplicationAppId;
        this.hasUsersUserCode = hasUsersUserCode;
    }

    @Id
    @Column(name = "ccr_pc_application_app_id")
    public Long getCcrPcApplicationAppId() {
        return ccrPcApplicationAppId;
    }

    public void setCcrPcApplicationAppId(Long ccrPcApplicationAppId) {
        this.ccrPcApplicationAppId = ccrPcApplicationAppId;
    }

    @Id
    @Column(name = "has_users_user_code")
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
        CcrApplicationHasUsers that = (CcrApplicationHasUsers) o;
        return ccrPcApplicationAppId==that.ccrPcApplicationAppId &&
                Objects.equals(hasUsersUserCode, that.hasUsersUserCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ccrPcApplicationAppId, hasUsersUserCode);
    }
}
