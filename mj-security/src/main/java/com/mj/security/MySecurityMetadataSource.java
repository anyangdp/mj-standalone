package com.mj.security;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mj.web.system.domain.dobj.SPermissionDO;
import com.mj.web.system.service.SPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;

import java.util.*;

/**
 * 权限资源管理器
 * 为权限决断器提供支持
 */
@Slf4j
@Component
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private PathMatcher pathMatcher;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${permission.decide.type}")
    private Integer decideType;

    private Map<String, Collection<ConfigAttribute>> map = null;
    private Map<String, String> permissionMethodMap = null;

    @Autowired
    private SPermissionService permissionService;

    /**
     * 加载权限表中所有操作请求权限
     */
    public void loadResourceDefine() {
        map = new HashMap<>(16);
        permissionMethodMap = new HashMap<>(16);
        Collection<ConfigAttribute> configAttributes;
        ConfigAttribute cfg;
        /**
         * 关闭权限
         */
        // 获取启用的权限操作请求
        List<SPermissionDO> permissions = permissionService.list(new LambdaQueryWrapper<SPermissionDO>().eq(SPermissionDO::getType, 2).eq(SPermissionDO::getActive, true));
        // 测试环境权限过滤关闭
        // permissions.clear();
        for (SPermissionDO permission : permissions) {
            if (StringUtils.isNotBlank(permission.getPath())) {
                configAttributes = new ArrayList<>();
                cfg = new SecurityConfig(permission.getPath());
                //作为MyAccessDecisionManager类的decide的第三个参数
                configAttributes.add(cfg);
                //用权限的path作为map的key，用ConfigAttribute的集合作为value
                map.put(permission.getPath(), configAttributes);
                permissionMethodMap.put(permission.getPath(), "POST");
            }
        }


        redisTemplate.opsForValue().set("system:resource", JSON.toJSONString(map));
    }

    /**
     * 判定用户请求的url是否在权限表中
     * 如果在权限表中，则返回给decide方法，用来判定用户是否有此权限
     * 如果不在权限表中则放行
     *
     * @param o
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {


        //Object中包含用户请求request
        String url = ((FilterInvocation) o).getRequestUrl();
        String method = ((FilterInvocation) o).getHttpRequest().getMethod();
        Iterator<String> iterator;


        if (decideType == 0) {
            if (CollectionUtils.isEmpty(map)) {
                loadResourceDefine();
            }
            //1. 内存处理方式
            iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                String resURL = iterator.next();
                if (StringUtils.isNotBlank(resURL) && pathMatcher.match(resURL, url)) {
                    if (StringUtils.isNotBlank(permissionMethodMap.get(resURL)) && permissionMethodMap.get(resURL).equalsIgnoreCase(method)) {
                        return map.get(resURL);
                    }
                }
            }
        } else {
            if (!redisTemplate.hasKey("system:resource") && null == redisTemplate.opsForValue().get("system:resource")) {
                loadResourceDefine();
            }
            // redis处理方式
            if (redisTemplate.hasKey("system:resource")) {
                String members = redisTemplate.opsForValue().get(("system:resource"));
                Map<String, Collection<ConfigAttribute>> redisMap = JSON.parseObject(members, Map.class);
                iterator = redisMap.keySet().iterator();
                while (iterator.hasNext()) {
                    String resURL = iterator.next();
                    if (StringUtils.isNotBlank(resURL) && pathMatcher.match(resURL, url)) {
                        if (StringUtils.isNotBlank(permissionMethodMap.get(resURL)) && permissionMethodMap.get(resURL).equalsIgnoreCase(method)) {
                            return map.get(resURL);
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
