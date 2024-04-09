package com.mj.security.filter;

import com.alibaba.fastjson.JSON;
import com.mj.framework.constants.ErrorCodeEnum;
import com.mj.framework.constants.RedisKeyConstant;
import com.mj.framework.handler.ErrorDTO;
import com.mj.framework.handler.GenericResponse;
import com.mj.framework.util.ResponseUtil;
import com.mj.security.AdminTokenProperties;
import com.mj.security.CustomGrantedAuthority;
import com.mj.security.SecurityConstant;
import com.mj.security.SecurityUserDetails;
import com.mj.security.exception.JwtAuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author anyang
 * @CreateTime 2022/12/28
 * @Des
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final StringRedisTemplate redisTemplate;

    private final AdminTokenProperties adminTokenProperties;

    public JwtAuthenticationFilter(StringRedisTemplate redisTemplate, AdminTokenProperties adminTokenProperties) {
        this.redisTemplate = redisTemplate;
        this.adminTokenProperties = adminTokenProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("执行jwt filter");
        String header = request.getHeader(SecurityConstant.HEADER);
        if (StringUtils.isNotBlank(header) && header.startsWith(SecurityConstant.TOKEN_SPLIT)) {
            String finalHeader = header.substring(SecurityConstant.TOKEN_SPLIT.length());
            try {
                UsernamePasswordAuthenticationToken authentication = getAuthentication(RedisKeyConstant.TOKEN_PREFIX + finalHeader, response);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new JwtAuthenticationException(e.getMessage());
            }
        }

        doFilter(request, response, chain);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String header, HttpServletResponse response) {

        // 用户名
        String username;
        // 权限
        List<CustomGrantedAuthority> authorities = new ArrayList<>();
        // redis
        String v = redisTemplate.opsForValue().get(header);
        if (StringUtils.isBlank(v)) {
            ResponseUtil.out(response, new GenericResponse<Void>(false,
                    new ErrorDTO(ErrorCodeEnum.LOGIN_TOKEN_INVALID.getCode(), ErrorCodeEnum.LOGIN_TOKEN_INVALID.getMessage()), null));
            return null;
        }
        SecurityUserDetails user = JSON.parseObject(v, SecurityUserDetails.class);
        username = user.getUsername();
        if (!CollectionUtils.isEmpty(user.getAuthorities())) {
            authorities.addAll(user.getAuthorities());
        }
        if (!user.isSaveLogin()) {
            // 若未保存登录状态重新设置失效时间
            redisTemplate.opsForValue().set(header, v, adminTokenProperties.getTokenExpireTime(), TimeUnit.MINUTES);
        }
        if (StringUtils.isNotBlank(username)) {
            if (user.isSaveLogin()) {
                redisTemplate.expire(header, adminTokenProperties.getSaveLoginTime(), TimeUnit.DAYS);
            }
            return new UsernamePasswordAuthenticationToken(v, null, authorities);

        }
        return null;
    }
}
