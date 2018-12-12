package com.lqkj.web.cmccr2.modules.sensitivity.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "检查结果")
public class CheckResult {

    @ApiModelProperty(value = "被检查的内容",example = "曹尼玛")
    private List<String> content;

    @ApiModelProperty(value = "是否违禁", example = "false")
    private Boolean isSensitivity;

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public Boolean getSensitivity() {
        return isSensitivity;
    }

    public void setSensitivity(Boolean sensitivity) {
        isSensitivity = sensitivity;
    }
}
