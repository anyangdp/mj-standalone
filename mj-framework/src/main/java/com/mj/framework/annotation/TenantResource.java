package com.mj.framework.annotation;


import com.mj.framework.handler.AbstractDTO;

import java.lang.annotation.*;

/**
 * @Author anyang
 * @CreateTime 2020/7/22
 * @Des 租户数据查询
 * 1. 指定查询接口添加注解@TenantResource
 * 2. 分页查询或者列表查询等等对的查询条件必须继承 {@link ( AbstractDTO )}
 * 3. mybatis-mapper查询增加，自定sql必须要增加租户id
 * 4.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TenantResource {
}
