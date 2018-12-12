package com.lqkj.web.cmccr2.modules.application.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

/**
 * ios应用程序
 */
@ApiModel(description = "ios应用")
@Entity
@Table(name = "ccr_ios_application")
public class CcrIosApplication extends CcrVersionApplication {

    /**
     * app store id，用于web页面跳转
     */
    @ApiModelProperty(value = "app store应用市场id", required = true)
    @Column(name = "app_store_id", length = 256, nullable = false, unique = true)
    private Long appStoreId;

    @ApiModelProperty(value = "是否强制更新", required = true)
    @Column(name = "force_update")
    private Boolean forceUpdate;

    public Boolean getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(Boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public Long getAppStoreId() {
        return appStoreId;
    }

    public void setAppStoreId(Long appStoreId) {
        this.appStoreId = appStoreId;
    }
}
