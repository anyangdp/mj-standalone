package com.mj.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Author anyang
 * @CreateTime 2021/11/4
 * @Des
 */
@Configuration
public class HttpConfiguration {
    @Value("${application.version}")
    private String version;

//    @Value("${sql.list_tables.postgresql}")
//    private String sql;
    @Value("${test.version}")
    private String version2;

    @Bean
    public RestTemplate restTemplate() {
        System.out.println("当前版本" + version);

//        System.out.println("sql:" + sql);
        return new RestTemplate();
    }
}
