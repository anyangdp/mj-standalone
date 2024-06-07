package com.mj.security;

import com.mj.framework.constants.ErrorCodeEnum;
import com.mj.framework.handler.ErrorDTO;
import com.mj.framework.handler.GenericResponse;
import com.mj.framework.util.ResponseUtil;
import com.mj.security.exception.JwtAuthenticationException;
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
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @Author anyang
 * @CreateTime 2022/12/9
 * @Des
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

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
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .antMatchers("/bullet/**").permitAll()
                        .antMatchers("/doLogout").authenticated()
                .antMatchers("/doLogin", "/do/third/auth").permitAll()
                .anyRequest().access(requestAuthorizationManager)
                .and()
                .exceptionHandling(exception ->
                    exception.authenticationEntryPoint((req, res, e) -> handleAuthenticationException(e, res))
                            .accessDeniedHandler(accessDeniedHandler))
                .addFilterBefore(new JwtAuthenticationFilter(stringRedisTemplate, adminTokenProperties), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(customFilterSecurityInterceptor, FilterSecurityInterceptor.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // 允许从任何源
        configuration.setAllowedOrigins(Arrays.asList("*"));
        // 允许任何头
        configuration.setAllowedHeaders(Arrays.asList("*"));
        // 允许任何方法 (post, get, put, delete, options)
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE", "OPTIONS"));
        // 为所有路径配置应用此源
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private void handleAuthenticationException(Exception e, HttpServletResponse res) {
        ErrorCodeEnum error;
        if (e instanceof UserPasswordErrorException || e instanceof BadCredentialsException) {
            error = ErrorCodeEnum.LOGIN_ERROR;
        } else if (e instanceof JwtAuthenticationException) {
            error = ErrorCodeEnum.TOKEN_ERROR;
        } else {
            error = ErrorCodeEnum.PERMISSION_NOT_ACCESS;
        }
        ResponseUtil.out(res, new GenericResponse<Void>(false, new ErrorDTO(error.getCode(), error.getMessage()), null));
    }

    /**
     * 忽略地址，不被spring security过滤器拦截
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .antMatchers("/doc.html",
                  "/swagger-ui.html",
                  "/v2/api-docs/**",
                "/v3/api-docs/**",
                "/swagger-resources/**",
                "/webjars/**",
                "/processDefinition/getDefinitionXML",
                "/**/*.js",
                "/**/*.css",
                "/**/*.png",
                "/**/*.ico",
                "/file/**");
    }
}
