package com.lqkj.web.cmccr2.modules.notification.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;

/**
 * 系统通知
 */

public class CcrNotificationVO implements Serializable  {


    private Integer infoId;

    private String title;

    private String content;

    private Boolean checkRead;

    public CcrNotificationVO() {
    }

    public CcrNotificationVO(Integer infoId, String title, String content, Boolean checkRead) {
        this.infoId = infoId;
        this.title = title;
        this.content = content;
        this.checkRead = checkRead;
    }

    public Integer getInfoId() {
        return infoId;
    }

    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getCheckRead() {
        return checkRead;
    }

    public void setCheckRead(Boolean checkRead) {
        this.checkRead = checkRead;
    }
}
