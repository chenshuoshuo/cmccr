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

    /**
     * 返回用IP地址
     */
    public String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP ");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
