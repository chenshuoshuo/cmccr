package com.lqkj.web.cmccr2.utils;

import javax.servlet.http.HttpServletRequest;

public class ServletUtils {
    /**
     * 获取请求地址
     */
    public static String createBaseUrl(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() //服务器地址
                + ":" + request.getServerPort()//端口号
                + request.getContextPath();//项目名称
    }
}
