package com.lqkj.web.cmccr2.config;

import com.lqkj.web.cmccr2.modules.user.domain.CcrUser;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashSet;
import java.util.Set;

/**
 * 用户信息
 */
public class UserInfoTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Object principal = authentication.getUserAuthentication().getPrincipal();

        if (!(principal instanceof CcrUser)){
            return accessToken;
        }

        Set<String> scopes = new HashSet<>(20);
        scopes.addAll(accessToken.getScope());

        CcrUser user = (CcrUser) principal;

        user.getRules().forEach(v -> scopes.add("role_" + v.getContent()));
        user.getAuthorities().forEach(v -> scopes.add("authority_" + v.getContent()));

        ((DefaultOAuth2AccessToken) accessToken).setScope(scopes);
        return accessToken;
    }
}
