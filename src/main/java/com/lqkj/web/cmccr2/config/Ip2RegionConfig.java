package com.lqkj.web.cmccr2.config;

import org.apache.commons.io.IOUtils;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbMakerConfigException;
import org.lionsoul.ip2region.DbSearcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * ip地址转换器配置
 */
@Configuration
public class Ip2RegionConfig {

    @Bean(destroyMethod = "close")
    public DbSearcher searcher() throws DbMakerConfigException, IOException {
        ClassPathResource classPathResource = new ClassPathResource("location/ip2region.db");

        return new DbSearcher(dbConfig(),IOUtils.toByteArray(classPathResource.getInputStream()));
    }

    private DbConfig dbConfig() throws DbMakerConfigException {
        return new DbConfig(8 * 2048);
    }
}
