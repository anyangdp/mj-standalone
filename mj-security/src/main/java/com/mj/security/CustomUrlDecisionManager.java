package com.mj.security;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;

/**
 * @Author anyang
 * @CreateTime 2023/1/3
 * @Des
 */
@Slf4j
@Component
public class CustomUrlDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        log.info("执行：CustomUrlDecisionManager");
        if (configAttributes == null) {
            return;
        }
        SecurityUserDetails user = JSON.parseObject(authentication.getPrincipal().toString(), SecurityUserDetails.class);
        if (user.getUsername().equals("admin")) {
            return;
        }
        Iterator<ConfigAttribute> iterator = configAttributes.iterator();
        // 原始匹配
        while (iterator.hasNext()) {
            ConfigAttribute c = iterator.next();
            String needPerm = c.getAttribute();
            for (GrantedAuthority ga : authentication.getAuthorities()) {
                // 匹配用户拥有的ga 和 系统中的needPerm
                if (needPerm.trim().equals(ga.getAuthority())) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("抱歉，您没有访问权限");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
