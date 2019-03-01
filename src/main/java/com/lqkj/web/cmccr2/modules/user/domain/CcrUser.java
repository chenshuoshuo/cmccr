package com.lqkj.web.cmccr2.modules.user.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

/**
 * 用户
 */
@Cacheable
@ApiModel(value = "用户")
@Entity
@Table(name = "ccr_user", indexes = {
        @Index(name = "user_code_index", unique = true, columnList = "user_code"),
        @Index(name = "open_id_index", unique = true, columnList = "open_id"),
        @Index(name = "cas_ticket_index", unique = true, columnList = "cas_ticket")
})
public class CcrUser implements Serializable, UserDetails {

    @ApiModelProperty(value = "账号id")
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank(message = "账户民不能为空")
    @ApiModelProperty(value = "账号名")
    @Column(name = "user_code")
    private String userCode;

    @ApiModelProperty(value = "密码")
    @Column(name = "pass_word")
    private String passWord;

    @ApiModelProperty(value = "oauth2.0登录id")
    @Column(name = "open_id")
    private String openId;

    @ApiModelProperty(value = "cas登录凭证")
    @Column(name = "cas_ticket")
    private String casTicket;

    @ApiModelProperty(value = "用户角色")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ccr_user_to_rule",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "rule_id", referencedColumnName = "rule_id")
    )
    private Set<CcrUserRule> rules;

    @ApiModelProperty(value = "用户群体")
    @Column(name = "user_group")
    @Enumerated(EnumType.STRING)
    private CcrUserGroupType userGroup;

    @ApiModelProperty(value = "更新时间")
    @Column(name = "update_time")
    @UpdateTimestamp
    private Timestamp updateTime;

    @ApiModelProperty("是否允许登录后台")
    @Column(name = "is_admin")
    private Boolean isAdmin;

    public CcrUserGroupType getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(CcrUserGroupType userGroup) {
        this.userGroup = userGroup;
    }

    public Set<CcrUserRule> getRules() {
        return rules;
    }

    public void setRules(Set<CcrUserRule> rules) {
        this.rules = rules;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getCasTicket() {
        return casTicket;
    }

    public void setCasTicket(String casTicket) {
        this.casTicket = casTicket;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) return true;
        if (o==null || getClass()!=o.getClass()) return false;
        CcrUser user = (CcrUser) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(userCode, user.userCode) &&
                Objects.equals(passWord, user.passWord) &&
                Objects.equals(openId, user.openId) &&
                Objects.equals(casTicket, user.casTicket) &&
                Objects.equals(rules, user.rules) &&
                userGroup==user.userGroup &&
                Objects.equals(updateTime, user.updateTime) &&
                Objects.equals(isAdmin, user.isAdmin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userCode, passWord, openId, casTicket, rules, userGroup, updateTime, isAdmin);
    }

    @Override
    public Collection<CcrUserAuthority> getAuthorities() {
        Set<CcrUserAuthority> authorities = new HashSet<>();

        if (rules==null) {
            return authorities;
        }

        for (CcrUserRule rule : this.rules) {
            for (CcrUserAuthority authority : rule.getAuthorities()) {
                if (authority.getEnabled()) authorities.add(authority);
            }
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        if (passWord!=null) {
            return passWord;
        } else {
            return casTicket;
        }
    }

    @Override
    public String getUsername() {
        return userCode;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public enum CcrUserGroupType {
        student, teacher, staff, guest, teacher_staff
    }
}
