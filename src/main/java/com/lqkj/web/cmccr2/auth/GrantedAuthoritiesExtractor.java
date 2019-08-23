package com.lqkj.web.cmccr2.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


/**
 *
 */
public class GrantedAuthoritiesExtractor extends JwtAuthenticationConverter {
    Logger logger = LoggerFactory.getLogger("权限拉取");

    protected Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        List<String> authorities = (List<String>) jwt.getClaims().get("authorities");

        Collection<GrantedAuthority> oldAuthorities = super.extractAuthorities(jwt);

        if (authorities == null) return oldAuthorities;

        try {
            List<GrantedAuthority> grantedAuthorities = authorities
                    .stream()
                    .map(v -> new SimpleGrantedAuthority("cmccr_auth_" + v))
                    .collect(Collectors.toList());
            oldAuthorities.addAll(grantedAuthorities);

            List<String> rules = (List<String>) jwt.getClaims().get("rules");

            if(rules == null || rules.size() == 0){
                //TODO 这一行为开发时使用，为所有找不到角色的用户赋予admin角色，方便调试
//                rules = new ArrayList<>(Arrays.asList(new String[]{"admin"}));
                rules = new ArrayList<>();
            }

            List<GrantedAuthority> grantedRules = rules
                    .stream()
                    .map(v -> new SimpleGrantedAuthority("cmccr_rules_" + v))
                    .collect(Collectors.toList());
            oldAuthorities.addAll(grantedRules);
        } catch (Exception e) {
            logger.error("拉取权限异常", e);
        }

        return oldAuthorities;

    }
}
