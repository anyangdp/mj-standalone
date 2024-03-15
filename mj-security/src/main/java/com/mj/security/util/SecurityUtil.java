package com.mj.security.util;

import com.alibaba.fastjson.JSON;
import com.mj.security.SecurityUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

/**
 * @author anyang
 * @since 2024/3/15
 */
public class SecurityUtil {
    public static SecurityUserDetails securityUserDetails() {
        return JSON.parseObject(JSON.toJSONString(SecurityContextHolder.getContext().getAuthentication().getPrincipal()), SecurityUserDetails.class);
    }

    public static String name() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static String principal() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

    public static Collection<? extends GrantedAuthority> authorities() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }
}
