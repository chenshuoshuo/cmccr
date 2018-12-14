package com.lqkj.web.cmccr2.config;

import com.google.common.collect.Lists;
import com.lqkj.web.cmccr2.modules.user.service.CcrUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.*;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * http://192.168.4.105:8100/cmccr/oauth/token?grant_type=client_credentials&client_id=demo&client_secret=demo
 * </p>
 * <p>
 * http://192.168.4.105:8100/cmccr/oauth/authorize?response_type=token&client_id=demo&client_secret=demo&scope=demo&redirect_uri=http://baidu.com
 * </p>
 * <p>
 * http://192.168.4.105:8100/cmccr/oauth/token?client_id=cmccr-h5&client_secret=cmccr-h5&grant_type=password&username=free&password=123456
 * </p>
 * <p>
 * http://192.168.4.105:8100/cmccr/oauth/token?grant_type=refresh_token&refresh_token=fbde81ee-f419-42b1-1234-9191f1f95be9&client_id=cmccr-h5&client_secret=cmccr-h5
 * </p>
 * 2018001004/123456
 */
@Configuration
@EnableAuthorizationServer
@EnableResourceServer
public class Oauth2SecurityConfig implements AuthorizationServerConfigurer, ResourceServerConfigurer {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CcrUserService userService;

    @Autowired
    DataSource dataSource;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("cmccr-h5")
                .scopes("js")
                .resourceIds("cmccr-server")
                .authorizedGrantTypes("password", "refresh_token")
                .secret(passwordEncoder.encode("cmccr-h5"))
                .accessTokenValiditySeconds((int) TimeUnit.MINUTES.toSeconds(10))
                .refreshTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(7))
                    .and()
                .withClient("cmips-h5")
                .scopes("js")
                .resourceIds("cmccr-server")
                .authorizedGrantTypes("password", "refresh_token")
                .secret(passwordEncoder.encode("cmips-h5"))
                .accessTokenValiditySeconds((int) TimeUnit.MINUTES.toSeconds(10))
                .refreshTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(7))
        ;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST, HttpMethod.OPTIONS)
                .tokenStore(new JdbcTokenStore(dataSource))
                .userDetailsService(userService)
                .authenticationManager(authenticationManager)
        ;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("cmccr-server")
                .stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .and()
                .authorizeRequests()
                .antMatchers("/center/user/register")
                .permitAll()
                .antMatchers("/center/application/**",
                        "/center/menu/**",
                        "/center/request/**",
                        "/center/sensitivity/**",
                        "/center/store/**",
                        "/center/user/**")
                .authenticated()
        ;
    }
}
