package com.lqkj.web.cmccr2.utils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @ClassName CookieFilter
 * @Description TODO
 * @Author Administrator
 * @Date 2019/9/26 13:59
 * @Version 1.0
 **/
@WebFilter(urlPatterns = "/*", filterName = "cookieFilter")
public class CookieHttpOnlyFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest Hrequest = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse) response;
        Cookie[] cookies=Hrequest.getCookies();
        if(cookies != null && cookies.length > 0) {
            for(Cookie cookie : cookies) {
                String value = cookie.getValue();
                StringBuilder builder = new StringBuilder();
                builder.append("JSESSIONID=" + value + "; ");
                builder.append("Secure; ");
                builder.append("HttpOnly; ");
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.HOUR, 1);
                Date date = cal.getTime();
                Locale locale = Locale.CHINA;
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", locale);
                builder.append("Expires=" + sdf.format(date));
                resp.setHeader("Set-Cookie", builder.toString());
            }
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub
    }
}
