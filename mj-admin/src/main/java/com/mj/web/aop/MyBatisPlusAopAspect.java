package com.mj.web.aop;

import com.alibaba.fastjson.JSON;
import com.mj.framework.handler.AbstractDO;
import com.mj.security.SecurityUserDetails;
import com.mj.security.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @Author anyang
 * @CreateTime 2023/12/4
 * @Des
 */
@Slf4j
@Aspect
@Component
@EnableAspectJAutoProxy
public class MyBatisPlusAopAspect {

    // 切入点表达式，匹配 MyBatis-Plus 的 BaseMapper 中的所有 insert 方法
    @Before(value = "execution(* com.baomidou.mybatisplus.core.mapper..*.insert*(..)) && args(entity,..)")
    public void beforeSave(Object entity) throws IllegalAccessException {
        if (entity instanceof AbstractDO) {
            SecurityUserDetails securityUserDetails = SecurityUtil.securityUserDetails();
            if (null == securityUserDetails) {
                return;
            }
            // 获取实体的所有字段
            Field[] fields = entity.getClass().getSuperclass().getDeclaredFields();
            // 在这里进行通用的处理，例如遍历字段并修改值
            for (Field field : fields) {
                // 设置字段可访问，以便修改值
                if ("createdBy".equals(field.getName())) {
                    field.setAccessible(true);
                    field.set(entity, securityUserDetails.getId());
                    break;
                }
            }
        }
    }

    // 切入点表达式，匹配 MyBatis-Plus 的 BaseMapper 中的所有 delete 方法
    @Before("execution(* com.baomidou.mybatisplus.core.mapper.*.delete*(..)) && args(id,..)")
    public void beforeRemove(Object id) {
        System.out.println("Before remove operation: " + id);
        // 在删除数据前添加逻辑
    }

    // 切入点表达式，匹配 MyBatis-Plus 的 BaseMapper 中的所有 update 方法
    @Before("execution(* com.baomidou.mybatisplus.core.mapper.*.update*(..)) && args(entity,..)")
    public void beforeUpdate(Object entity) throws IllegalAccessException {
        System.out.println("Before update operation: " + entity);
        // 在更新数据前添加逻辑
        if (entity instanceof AbstractDO) {
            SecurityUserDetails securityUserDetails = SecurityUtil.securityUserDetails();
            if (null == securityUserDetails) {
                return;
            }
            // 获取实体的所有字段
            Field[] fields = entity.getClass().getSuperclass().getDeclaredFields();

            // 在这里进行通用的处理，例如遍历字段并修改值
            for (Field field : fields) {
                // 设置字段可访问，以便修改值
                if ("updatedBy".equals(field.getName())) {
                    field.setAccessible(true);
                    field.set(entity, securityUserDetails.getId());
                    break;
                }
            }
        }
    }
}
