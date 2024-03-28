package com.mj.activiti.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @Author anyang
 * @CreateTime 2022/4/28
 * @Des
 */
@Configuration
public class StaticResourceConfigurer extends WebMvcConfigurationSupport {
    @Autowired
    private WebProperties webProperties;
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/file/**", "/**")
                .addResourceLocations(webProperties.getResources().getStaticLocations());
    }

    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 拦截所有的请求
                //.allowedOrigins("*")  // 可跨域的域名，可以为 *
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedMethods("*")   // 允许跨域的方法，可以单独配置
                .allowedHeaders("*");  // 允许跨域的请求头，可以单独配置

    }
}
