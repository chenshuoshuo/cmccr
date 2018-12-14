package com.lqkj.web.cmccr2.modules.user.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 微信oauth登录服务
 */
@Service
public class WeiXinOAuthService {

    @Value("${weixin.client_id}")
    String clientId;

    @Value("${weixin.client_secret}")
    String clientSecret;

    /**
     * 创建授权请求地址
     */
    public String createAuthorizeURL(String baseURL, Long userId) {
        return new StringBuilder()
                .append("https://open.weixin.qq.com/connect/oauth2/authorize?appid=")
                .append(clientId)
                .append("&redirect_uri=")
                .append(baseURL)
                .append("/center/user/weixin/callback")
                .append("?user_id=")
                .append(userId)
                .append("&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect")
                .toString();
    }
}
