package com.lqkj.web.cmccr2.modules.request.doamin;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * 数据统计结果
 */
public class CcrDateResult implements Serializable {
    private Instant date;

    private Integer successCount;

    private Integer errorCount;

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Integer getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }

    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }
}
