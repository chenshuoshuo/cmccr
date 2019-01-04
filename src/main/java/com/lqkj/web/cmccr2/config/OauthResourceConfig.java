package com.lqkj.web.cmccr2.config;

import com.lqkj.web.cmccr2.APIVersion;
import com.lqkj.web.cmccr2.modules.user.service.CcrUserService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.charset.Charset;
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
@EnableResourceServer
public class OauthResourceConfig implements ResourceServerConfigurer {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("cmccr-server")
                .tokenStore(tokenStore())
                .tokenServices(tokenServices())
                .stateless(true);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/center/user/register")
                .permitAll()
                .antMatchers(HttpMethod.GET,"/center/store/" + APIVersion.V1 + "/*/*")
                .permitAll()
                .antMatchers("/center/application/**",
                        "/center/menu/**",
                        "/center/request/**",
                        "/center/sensitivity/**",
                        "/center/store/**",
                        "/center/user/**",
                        "/center/sys/log/**"
                        )
                .authenticated()
        ;
    }

    @Bean
    @Primary
    public TokenStore tokenStore() throws IOException {
        return new JwtTokenStore(accessTokenConverter());
    }

    /**
     * keytool -genkeypair -alias oauth -keyalg RSA -keypass lqkj007 -keystore jwt.jks -storepass lqkj007
     * keytool -list -rfc --keystore jwt.jks | openssl x509 -inform pem -pubkey
     */
    @Bean
    @Primary
    public JwtAccessTokenConverter accessTokenConverter() throws IOException {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();

        KeyStoreKeyFactory keyStoreKeyFactory =
                new KeyStoreKeyFactory(new ClassPathResource("key/jwt.jks"), "lqkj007".toCharArray());

        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("oauth"));

        return converter;
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() throws IOException {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setTokenEnhancer(accessTokenConverter());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }
}
