package com.mj.web.config;

import com.google.common.collect.Lists;
import com.mj.framework.util.ReflectionUtils;
import com.mj.security.SecurityConstant;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.SpringfoxWebMvcConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Configuration
@EnableOpenApi
@ConditionalOnClass(SpringfoxWebMvcConfiguration.class)
public class SwaggerConfiguration {
    private List<Parameter> getHeader(){
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        //配置token显示
        tokenPar.name(SecurityConstant.HEADER).description("密钥").modelRef(new ModelRef("string")).parameterType("header").required(false);
        pars.add(tokenPar.build());
        return pars;
    }

    @Profile("dev")
    @Bean(value = "authGroup")
    @Order(value = 1)
    public Docket authGroupDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(inventoryGroupApiInfo())
                .groupName("授权登录")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mj.web.controller.auth"))
                .paths(PathSelectors.any())
                .build().securityContexts(Lists.newArrayList(securityContext())).securitySchemes(Lists.<SecurityScheme>newArrayList(apiKey())).globalOperationParameters(getHeader());
    }

    @Profile("dev")
    @Bean(value = "systemGroup")
    @Order(value = 1)
    public Docket systemGroupDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(inventoryGroupApiInfo())
                .groupName("系统管理")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mj.web.controller.system"))
                .paths(PathSelectors.any())
                .build().securityContexts(Lists.newArrayList(securityContext())).securitySchemes(Lists.<SecurityScheme>newArrayList(apiKey())).globalOperationParameters(getHeader());
    }

    private ApiInfo inventoryGroupApiInfo(){
        return new ApiInfoBuilder()
                .title("mj-后台管理")
                .description("<div style='font-size:14px;color:red;'>后台管理</div>")
                .termsOfServiceUrl("")
                .contact(new Contact("", "", ""))
                .version("1.0")
                .build();
    }


    private ApiKey apiKey() {
        return new ApiKey("Authorization", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/.*"))
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(new SecurityReference("Authorization", authorizationScopes));
    }

    @Bean
    public static BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof WebMvcRequestHandlerProvider) {
                    customizeSpringfoxHandlerMappings(getHandlerMappings(bean));
                }
                return bean;
            }

            private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(
                    List<T> mappings) {
                List<T> copy = mappings.stream().filter(mapping -> mapping.getPatternParser() == null)
                        .collect(Collectors.toList());
                mappings.clear();
                mappings.addAll(copy);
            }

            @SuppressWarnings("unchecked")
            private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {
                try {
                    Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
                    field.setAccessible(true);
                    return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
                }
                catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new IllegalStateException(e);
                }
            }
        };
    }

}
