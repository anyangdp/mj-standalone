package com.mj.security;

import com.mj.framework.constants.ErrorCodeEnum;
import com.mj.framework.handler.ErrorDTO;
import com.mj.framework.handler.GenericResponse;
import com.mj.framework.util.ResponseUtil;
import com.mj.security.exception.UserPasswordErrorException;
import com.mj.security.filter.CustomFilterSecurityInterceptor;
import com.mj.security.filter.JwtAuthenticationFilter;
import com.mj.security.handler.RestAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Author anyang
 * @CreateTime 2022/12/9
 * @Des
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    // @Lazy
    // @Autowired
    // public UserDetailsService userDetailServiceImpl;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private AdminTokenProperties adminTokenProperties;

    @Autowired
    private RestAccessDeniedHandler accessDeniedHandler;

    @Autowired
    RequestAuthorizationManager requestAuthorizationManager;

    @Autowired
    private CustomFilterSecurityInterceptor customFilterSecurityInterceptor;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManagerBean(ObjectPostProcessor<Object> objectPostProcessor) throws Exception {
        return new AuthenticationManagerBuilder(objectPostProcessor)
                .userDetailsService(userDetailsService())
                .passwordEncoder(bCryptPasswordEncoder())
                .and().build();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http.formLogin().disable().csrf().disable()
                .sessionManagement().disable()
                // .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //  .and()
                .authorizeHttpRequests()
                .antMatchers("/doc.html",
                        "/v2/api-docs/**",
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/**/*.js",
                        "/**/*.css",
                        "/**/*.png",
                        "/**/*.ico",
                        "/file/**",
                        "/bullet/**").permitAll()
                .antMatchers("/doLogout").authenticated()
                .antMatchers("/doLogin").permitAll()
                .antMatchers("/do/third/auth").permitAll()
                .anyRequest().access(requestAuthorizationManager)
                .and()
                .exceptionHandling(exception -> {
                    exception.authenticationEntryPoint((httpServletRequest, response, e) -> {
                        if (e instanceof UserPasswordErrorException) {
                            ResponseUtil.out(response, new GenericResponse(false, new ErrorDTO(ErrorCodeEnum.LOGIN_ERROR.getCode(), ErrorCodeEnum.LOGIN_ERROR.getMessage()), null));
                        } else if (e instanceof BadCredentialsException) {
                            ResponseUtil.out(response, new GenericResponse(false, new ErrorDTO(ErrorCodeEnum.LOGIN_ERROR.getCode(), ErrorCodeEnum.LOGIN_ERROR.getMessage()), null));
                        } else {
                            ResponseUtil.out(response, new GenericResponse(false, new ErrorDTO(ErrorCodeEnum.PERMISSION_NOT_ACCESS.getCode(),
                                    ErrorCodeEnum.PERMISSION_NOT_ACCESS.getMessage()), null));
                        }
                    });
                })
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(stringRedisTemplate, adminTokenProperties), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(customFilterSecurityInterceptor, FilterSecurityInterceptor.class);
        return http.build();
    }



}
