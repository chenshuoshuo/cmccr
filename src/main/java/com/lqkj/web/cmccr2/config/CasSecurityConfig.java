package com.lqkj.web.cmccr2.config;

import com.lqkj.web.cmccr2.modules.user.service.CcrUserService;
import org.jasig.cas.client.proxy.ProxyGrantingTicketStorageImpl;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.validation.Cas20ProxyTicketValidator;
import org.jasig.cas.client.validation.Cas30ProxyTicketValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenEndpointFilter;

@Configuration
@EnableWebSecurity
public class CasSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CcrUserService ccrUserService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    Environment environment;

    @Value("${cas.base}")
    String casBaseUrl;

    @Value("${cas.login}")
    String casLoginUrl;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers(HttpMethod.OPTIONS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(casAuthenticationProvider(serviceProperties()));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .and()
                .authorizeRequests()
                .antMatchers("/center/cas/user").authenticated()
                .and()
                .userDetailsService(ccrUserService)
                .addFilter(casFilter(serviceProperties()))
                .addFilterBefore(singleSignOutFilter(), CasAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(casAuthenticationEntryPoint(serviceProperties()));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public ServiceProperties serviceProperties() {
        ServiceProperties serviceProperties = new ServiceProperties();
        serviceProperties.setAuthenticateAllArtifacts(true);
        serviceProperties.setService("http://" + environment.getProperty("server.address") +
                ":" + environment.getProperty("server.port") + "/cmccr/center/cas/callback");
        return serviceProperties;
    }

    @Bean
    public CasAuthenticationEntryPoint casAuthenticationEntryPoint(ServiceProperties serviceProperties) {
        CasAuthenticationEntryPoint entryPoint = new CasAuthenticationEntryPoint();
        entryPoint.setLoginUrl(casLoginUrl);
        entryPoint.setServiceProperties(serviceProperties);
        return entryPoint;
    }

    @Bean
    public CasAuthenticationProvider casAuthenticationProvider(ServiceProperties serviceProperties) {
        Cas20ProxyTicketValidator ticketValidator = new Cas20ProxyTicketValidator(
                casBaseUrl);
        ticketValidator.setAcceptAnyProxy(true);
        ticketValidator.setProxyGrantingTicketStorage(new ProxyGrantingTicketStorageImpl());

        CasAuthenticationProvider authenticationProvider = new CasAuthenticationProvider();
        authenticationProvider.setKey("casProvider");
        authenticationProvider.setServiceProperties(serviceProperties);
        authenticationProvider.setTicketValidator(ticketValidator);
        authenticationProvider.setAuthenticationUserDetailsService(ccrUserService);

        return authenticationProvider;
    }

    public SingleSignOutFilter singleSignOutFilter() {
        SingleSignOutFilter singleSignOutFilter = new SingleSignOutFilter();
        singleSignOutFilter.setCasServerUrlPrefix(casBaseUrl);
        return singleSignOutFilter;
    }

    @Bean
    public CasAuthenticationFilter casFilter(ServiceProperties serviceProperties) throws Exception {
        CasAuthenticationFilter filter = new CasAuthenticationFilter();

        filter.setAuthenticationManager(authenticationManager);
        filter.setServiceProperties(serviceProperties);

        return filter;
    }
}
