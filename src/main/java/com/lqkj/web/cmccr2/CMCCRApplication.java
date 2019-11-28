package com.lqkj.web.cmccr2;

import com.lqkj.web.cmccr2.config.PropertyDeploy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCaching
public class CMCCRApplication {

    public static void main(String[] args) {
        PropertyDeploy deploy=new PropertyDeploy();
        deploy.deploy();
        SpringApplication.run(CMCCRApplication.class, args);
    }
}
