package com.mj.web.aop;

import com.alibaba.fastjson.JSON;
import com.mj.framework.handler.AbstractDTO;
import com.mj.security.SecurityUserDetails;
import com.mj.security.util.SecurityUtil;
import com.mj.web.system.domain.dobj.STenantResourceDO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.stream.Collectors;

/**
 * @Author anyang
 * @CreateTime 2020/7/22
 * @Des 租户数据权限切面
 */
@Slf4j
@Aspect
@Component
@EnableAspectJAutoProxy
public class TenantAspect {

    /**
     * 租户资源过滤，过滤条件：当前用户企业id
     *
     * @param joinPoint
     */
    @Before("@annotation(com.mj.framework.annotation.TenantResource)")
    public void tenantResource(JoinPoint joinPoint) throws NoSuchFieldException, IllegalAccessException {
        SecurityUserDetails securityUserDetails = SecurityUtil.securityUserDetails();
        if (null == securityUserDetails) {
            return;
        }
        tenant(securityUserDetails, joinPoint);
    }

    private void tenant(SecurityUserDetails securityUserDetails, JoinPoint joinPoint) {
        log.info("租户数据过滤：{}", JSON.toJSONString(securityUserDetails));
        if ("tenant".equals(securityUserDetails.getAccountType())) {
            //获取参数并赋值
            Object[] args = joinPoint.getArgs();
            for (Object arg : args) {
                if (arg instanceof AbstractDTO) {
                    Field creator = null;
                    try {
                        creator = arg.getClass().getDeclaredField("resourceIds");
                        creator.setAccessible(true);
                        creator.set(arg, securityUserDetails.getResources().stream().map(STenantResourceDO::getResourceId).collect(Collectors.toList()));
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                //
                // } else {
                //     log.info("类：{}，接口{} 参数实体无companyId", joinPoint.getTarget().getClass(), joinPoint.getSignature().getName());
                // }
            }
        } else if (("admin").equals(securityUserDetails.getAccountType())) {
            // 超级管理员忽略全部数据限制
        } else {
            log.info("用户{},过滤数据异常", JSON.toJSONString(securityUserDetails));
        }
    }
}
