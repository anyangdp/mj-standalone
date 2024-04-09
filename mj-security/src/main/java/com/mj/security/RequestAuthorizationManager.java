package com.mj.security;

import com.mj.security.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.function.Supplier;

/**
 * @Author anyang
 * @CreateTime 2023/1/3
 * @Des
 */
@Slf4j
@Component
public class RequestAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    @Autowired
    private MySecurityMetadataSource mySecurityMetadataSource;

    @Override
    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        AuthorizationManager.super.verify(authentication, object);
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        log.info("授权处理");
        boolean granted = isGranted(authentication.get(), object.getRequest());
        // 返回授权决策对象，根据权限结果
        return new AuthorizationDecision(granted);
    }

    private boolean isGranted(Authentication authentication, HttpServletRequest request) {
        return authentication != null && isAuthorized(authentication, request);
    }

    private boolean isAuthorized(Authentication authentication, HttpServletRequest request) {
        // authentication 就是我们的认证对象，我们可以直接拿到认证用户的权限
        //TODO 查询缓存中用户的权限
        // anonymousUser 匿名用户 ROLE_ANONYMOUS 默认权限
        String principal = authentication.getPrincipal().toString();
        if (principal.equals("anonymousUser")) {
            return false;
        }

        return true;
    }
}
