package com.lqkj.web.cmccr2.modules.application.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;

/**
 * web应用
 */
@ApiModel(description = "web应用")
@Entity
@Table(name = "ccr_web_application")
public class CcrWebApplication extends CcrVersionApplication {

    /**
     * api网关应用id
     */
    @ApiModelProperty("api网关应用id")
    @Column(name = "gateway_forward_id")
    private Long gatewayForwardId;

    /**
     * 跳转地址
     */
    @ApiModelProperty("跳转地址")
    @Column(name = "url", length = 1024)
    @URL
    private String url;

    public Long getGatewayForwardId() {
        return gatewayForwardId;
    }

    public void setGatewayForwardId(Long gatewayForwardId) {
        this.gatewayForwardId = gatewayForwardId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
