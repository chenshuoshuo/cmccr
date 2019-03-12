package com.lqkj.web.cmccr2.config;

import com.lqkj.web.cmccr2.modules.user.domain.CcrUser;
import com.lqkj.web.cmccr2.modules.user.domain.CcrUserRule;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class UserRulesAccessTokenConverter extends DefaultAccessTokenConverter {
    @Override
    public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        Map<String, Object> response = new HashMap<>();

        Object principal = authentication.getUserAuthentication().getPrincipal();

        if (principal instanceof CcrUser) {
            CcrUser user = (CcrUser) principal;

            Set<String> rules = user.getRules().stream()
                    .map(CcrUserRule::getContent)
                    .collect(Collectors.toSet());

            response.put("rules", rules);
        }

        response.putAll(super.convertAccessToken(token, authentication));

        return response;
    }
}
