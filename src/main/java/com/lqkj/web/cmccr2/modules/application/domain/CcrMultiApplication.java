package com.lqkj.web.cmccr2.modules.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * 联合应用信息
 */
@Entity
@Table(name = "ccr_multi_application")
public class CcrMultiApplication implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 128)
    private String name;

    @JsonIgnore
    @Column(name = "icon_path", nullable = false, length = 1024)
    private String iconPath;

    @Column(name = "android_url")
    private String androidURL;

    @Column(name = "ios_url")
    private String iosURL;

    @Column(name = "web_url")
    private String webURL;

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAndroidURL() {
        return androidURL;
    }

    public void setAndroidURL(String androidURL) {
        this.androidURL = androidURL;
    }

    public String getIosURL() {
        return iosURL;
    }

    public void setIosURL(String iosURL) {
        this.iosURL = iosURL;
    }

    public String getWebURL() {
        return webURL;
    }

    public void setWebURL(String webURL) {
        this.webURL = webURL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CcrMultiApplication that = (CcrMultiApplication) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(iconPath, that.iconPath) &&
                Objects.equals(androidURL, that.androidURL) &&
                Objects.equals(iosURL, that.iosURL) &&
                Objects.equals(webURL, that.webURL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, iconPath, androidURL, iosURL, webURL);
    }
}
