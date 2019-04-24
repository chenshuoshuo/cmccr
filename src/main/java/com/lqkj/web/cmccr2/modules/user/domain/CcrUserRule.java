package com.lqkj.web.cmccr2.modules.user.domain;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 用户角色
 */
@Cacheable
@Entity
@Table(name = "ccr_user_rule", indexes = {
        @Index(name = "name_index", columnList = "name", unique = true),
        @Index(name = "content_index", columnList = "content", unique = true)
})
public class CcrUserRule implements Serializable {

    @Id
    @Column(name = "rule_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ruleId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ccr_rule_to_authority",
            joinColumns = @JoinColumn(name = "rule_id", referencedColumnName = "rule_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "authority_id")
    )
    private Set<CcrUserAuthority> authorities;

    @UpdateTimestamp
    @Column(name = "update_time")
    private Timestamp updateTime;

    @Column
    private String name;

    @Column
    private String content;

    public CcrUserRule() {
    }

    public CcrUserRule(Set<CcrUserAuthority> authorities, String name, String content) {
        this.authorities = authorities;
        this.name = name;
        this.content = content;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public Set<CcrUserAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<CcrUserAuthority> authorities) {
        this.authorities = authorities;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) return true;
        if (o==null || getClass()!=o.getClass()) return false;
        CcrUserRule rule = (CcrUserRule) o;
        return Objects.equals(ruleId, rule.ruleId) &&
                Objects.equals(authorities, rule.authorities) &&
                Objects.equals(updateTime, rule.updateTime) &&
                Objects.equals(name, rule.name) &&
                Objects.equals(content, rule.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ruleId, authorities, updateTime, name, content);
    }
}
