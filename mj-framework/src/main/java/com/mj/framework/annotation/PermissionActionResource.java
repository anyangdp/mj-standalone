package com.mj.framework.annotation;

import java.lang.annotation.*;

/**
 * @Author anyang
 * @CreateTime 2020/4/9
 * @Des 资源操作注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionActionResource {
    String id();
    String name();
    String des() default "";
}
