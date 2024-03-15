package com.mj.framework.util;

import com.mj.common.util.SnowFlake;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author anyang
 * @CreateTime 2019/10/9
 * @Des
 */
@Configuration
public class SnowFlakeBeanConfig {
    @Bean
    public SnowFlake getId() {
        return new SnowFlake(0, 0);
    }
}
