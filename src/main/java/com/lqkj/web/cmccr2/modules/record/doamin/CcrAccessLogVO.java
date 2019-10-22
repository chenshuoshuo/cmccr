package com.lqkj.web.cmccr2.modules.record.doamin;

import java.util.List;
import java.util.Map;


/**
 * PV使用统计对象
 * @version 1.0
 * @author cs
 * @since 2019-8-27 10:32:49
 */

public class CcrAccessLogVO {
    /**
     * 当周统计数
     */
    private Integer weekUseCount;
    /**
     * 当月统计数
     */
    private Integer monthUseCount;
    /**
     * 当年统计数
     */
    private Integer thisYearUseCount;
    /**
     * IP总数
     */
    private Integer ipCount;
    /**
     * PV总数
     */
    private Integer pvCount;
    /**
     * 用户组比例
     */
    private List<Map<String,Object>> UserGroupCountList;

    public Integer getWeekUseCount() {
        return weekUseCount;
    }

    public void setWeekUseCount(Integer weekUseCount) {
        this.weekUseCount = weekUseCount;
    }

    public Integer getMonthUseCount() {
        return monthUseCount;
    }

    public void setMonthUseCount(Integer monthUseCount) {
        this.monthUseCount = monthUseCount;
    }

    public Integer getThisYearUseCount() {
        return thisYearUseCount;
    }

    public void setThisYearUseCount(Integer thisYearUseCount) {
        this.thisYearUseCount = thisYearUseCount;
    }

    public Integer getIpCount() {
        return ipCount;
    }

    public void setIpCount(Integer ipCount) {
        this.ipCount = ipCount;
    }

    public Integer getPvCount() {
        return pvCount;
    }

    public void setPvCount(Integer pvCount) {
        this.pvCount = pvCount;
    }

    public List<Map<String,Object>> getUserGroupCountList() {
        return UserGroupCountList;
    }

    public void setUserGroupCountList(List<Map<String,Object>> userGroupCountList) {
        UserGroupCountList = userGroupCountList;
    }
}
