package com.lqkj.web.cmccr2.modules.record.doamin;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 键值对对象，用于统计
 * @version 1.0
 * @author cs
 * @since 2019-8-27 10:32:49
 */

@Entity
public class KeyValueVO {
    /**
     * 键
     */
    @Id
    @Column(name = "key_string")
    private String keyString;
    /**
     * 值
     */
    @Basic
    @Column(name = "value_string")
    private String valueString;

    public KeyValueVO() {
    }

    public KeyValueVO(String keyString, String valueString) {
        this.keyString = keyString;
        this.valueString = valueString;
    }

    public String getKeyString() {
        return keyString;
    }

    public void setKeyString(String keyString) {
        this.keyString = keyString;
    }

    public String getValueString() {
        return valueString;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;
    }
}
