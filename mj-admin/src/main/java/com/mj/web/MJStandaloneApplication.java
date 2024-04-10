package com.mj.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @author  anyang
 * @since  2020/2/17
 */
// 开启缓存注解
@EnableCaching
@EnableOpenApi
@Slf4j
@SpringBootApplication(scanBasePackages = {"com.mj"})
public class MJStandaloneApplication {

    public static void main(String[] args) {
        SpringApplication.run(MJStandaloneApplication.class, args);
    }
}
