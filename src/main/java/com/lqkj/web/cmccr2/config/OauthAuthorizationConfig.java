package com.lqkj.web.cmccr2.config;

import com.lqkj.web.cmccr2.modules.user.service.CcrUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.io.IOException;
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
@EnableWebSecurity
public class OauthAuthorizationConfig extends WebSecurityConfigurerAdapter implements AuthorizationServerConfigurer {

    @Autowired
    CcrUserService userService;

    @Autowired
    DataSource dataSource;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
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
                .and()
                .withClient("cmccr-guest")
                .scopes("guest")
                .resourceIds("cmccr-server")
                .authorizedGrantTypes("client_credentials")
                .secret(passwordEncoder.encode("cmccr-guest"))
                .accessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(365))
        ;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST, HttpMethod.OPTIONS)
                .tokenStore(tokenStore())
                .tokenServices(tokenServices())
                .userDetailsService(userService)
                .authenticationManager(authenticationManager);
    }

    @Bean
    @Primary
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    @Primary
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();

        KeyStoreKeyFactory keyStoreKeyFactory =
                new KeyStoreKeyFactory(new ClassPathResource("key/jwt.jks"), "lqkj007".toCharArray());

        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("oauth"));

        return converter;
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(new UserInfoTokenEnhancer(), accessTokenConverter()));

        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setTokenEnhancer(tokenEnhancerChain);
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setAccessTokenValiditySeconds((int) TimeUnit.MINUTES.toSeconds(10));
        defaultTokenServices.setRefreshTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(7));
        return defaultTokenServices;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
