package com.lqkj.web.cmccr2.modules.request.doamin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "网关地理统计结果")
public class CcrLocationRecord implements Serializable {

    @ApiModelProperty(value = "区域名称")
    private String region;

    @ApiModelProperty(value = "城市id")
    private int cityId;

    @ApiModelProperty(value = "访问数")
    private int count;

    public CcrLocationRecord() {
    }

    public CcrLocationRecord(String region, int cityId, int count) {
        this.region = region;
        this.cityId = cityId;
        this.count = count;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "CcrLocationRecord{" +
                "region='" + region + '\'' +
                ", cityId=" + cityId +
                ", count=" + count +
                '}';
    }
}
