package com.mj.activiti;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @author anyang
 * @since 2024/3/18
 */
@EnableCaching
@EnableOpenApi
@Slf4j
@SpringBootApplication(scanBasePackages = {"com.mj"})
public class Activiti7Application {
    public static void main(String[] args) {
        SpringApplication.run(Activiti7Application.class, args);
    }
}
