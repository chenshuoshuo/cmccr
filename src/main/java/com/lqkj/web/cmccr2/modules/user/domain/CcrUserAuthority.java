package com.lqkj.web.cmccr2.modules.user.domain;

import com.vladmihalcea.hibernate.type.array.StringArrayType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * 用户权限
 */
@TypeDef(name = "string-array", typeClass = StringArrayType.class)
@ApiModel(description = "用户权限")
@Entity
@Table(name = "ccr_user_authority", indexes = {
        @Index(name = "name_index", columnList = "name", unique = true),
        @Index(name = "content_index", columnList = "content", unique = true)
})
public class CcrUserAuthority implements Serializable, GrantedAuthority {

    @ApiModelProperty(value = "权限id")
    @Id
    @Column(name = "authority_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorityId;

    @NotBlank(message = "显示名称不能为空")
    @ApiModelProperty(value = "权限显示名称")
    @Column
    private String name;

    @NotBlank(message = "权限不能为空")
    @ApiModelProperty(value = "权限")
    @Column
    private String content;

    @ApiModelProperty(value = "路由地址")
    @Column
    private String route;

    @ApiModelProperty(value = "图标")
    @Column(columnDefinition = " text")
    private String icon;

    @ApiModelProperty(value = "父节点")
    @Column(name = "parent_id")
    private Long parentId;

    @ApiModelProperty(value = "权限类型")
    @Enumerated(EnumType.STRING)
    private UserAuthorityType type;

    @Column
    @ApiModelProperty(value = "是否开发该功能")
    private Boolean enabled;

    @ApiModelProperty(value = "支持的http方法")
    @Type(type = "string-array")
    @Column(name = "http_method", columnDefinition = " text[]")
    private String[] httpMethod;

    public CcrUserAuthority() {
    }

    public CcrUserAuthority(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Long authorityId) {
        this.authorityId = authorityId;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public UserAuthorityType getType() {
        return type;
    }

    public void setType(UserAuthorityType type) {
        this.type = type;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String[] getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String[] httpMethod) {
        this.httpMethod = httpMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) return true;
        if (o==null || getClass()!=o.getClass()) return false;
        CcrUserAuthority authority = (CcrUserAuthority) o;
        return Objects.equals(authorityId, authority.authorityId) &&
                Objects.equals(name, authority.name) &&
                Objects.equals(content, authority.content) &&
                Objects.equals(route, authority.route) &&
                Objects.equals(icon, authority.icon) &&
                Objects.equals(parentId, authority.parentId) &&
                type==authority.type &&
                Objects.equals(enabled, authority.enabled) &&
                Arrays.equals(httpMethod, authority.httpMethod);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(authorityId, name, content, route, icon, parentId, type, enabled);
        result = 31 * result + Arrays.hashCode(httpMethod);
        return result;
    }

    @Override
    public String getAuthority() {
        return content;
    }

    public enum UserAuthorityType {
        menu, home_menu, ips_menu, normal
    }
}
