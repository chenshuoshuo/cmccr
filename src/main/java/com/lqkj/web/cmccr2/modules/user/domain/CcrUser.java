package com.lqkj.web.cmccr2.modules.user.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.*;

/**
 * 用户
 */
@ApiModel(value = "用户")
@Entity
@Table(name = "ccr_user")
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
    @JoinTable(name = "ccr_user_to_rule", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "rule_id", referencedColumnName = "rule_id")
    )
    private List<CcrUserRule> rules;

    public List<CcrUserRule> getRules() {
        return rules;
    }

    public void setRules(List<CcrUserRule> rules) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CcrUser ccrUser = (CcrUser) o;
        return Objects.equals(userId, ccrUser.userId) &&
                Objects.equals(userCode, ccrUser.userCode) &&
                Objects.equals(passWord, ccrUser.passWord) &&
                Objects.equals(openId, ccrUser.openId) &&
                Objects.equals(casTicket, ccrUser.casTicket) &&
                Objects.equals(rules, ccrUser.rules);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userCode, passWord, openId, casTicket, rules);
    }



    @Override
    public Collection getAuthorities() {
        List<CcrUserAuthority> authorities = new ArrayList<>();

        for (CcrUserRule rule : this.rules) {
            authorities.addAll(rule.getAuthorities());
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return passWord;
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
}
