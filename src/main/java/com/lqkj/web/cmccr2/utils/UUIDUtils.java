package com.lqkj.web.cmccr2.utils;

import java.util.UUID;

/**
 *
 * @作者: cs
 * @项目: mail--cc.ccoder.mail.utils
 * @时间: 2019年8月17日下午1:05:14
 * @TODO： 生成随机字符串的工具类 uuid
 */
public class UUIDUtils {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
