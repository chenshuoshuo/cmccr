package com.lqkj.web.cmccr2.modules.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

/**
 * android apk信息
 */
@ApiModel(description = "android应用信息")
@Entity
@Table(name = "ccr_android_application")
public class CcrAndroidApplication extends CcrVersionApplication {

    @ApiModelProperty(value = "版本名称", required = true)
    @Column(name = "version_name", nullable = false, length = 128)
    private String versionName;

    @ApiModelProperty(value = "版本编号", required = true)
    @Column(name = "version_code", nullable = false, length = 32)
    private Integer versionCode;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    @Column(name = "apk_path", nullable = false, length = 1024)
    private String apkPath;

    @ApiModelProperty(value = "是否强制更新", required = true)
    @Column(name = "force_update")
    private Boolean forceUpdate;

    @ApiModelProperty(value = "下载次数", readOnly = true)
    @Column(name = "download_count")
    private Long downloadCount;

    public Long getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Long downloadCount) {
        this.downloadCount = downloadCount;
    }

    public Boolean getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(Boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    public String getApkPath() {
        return apkPath;
    }

    public void setApkPath(String apkPath) {
        this.apkPath = apkPath;
    }
}
