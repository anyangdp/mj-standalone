package com.mj.framework.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisTemplateUtil {
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 绑定key-value
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        try {
            redisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 添加key-value
     * @param key
     * @param value
     * @param time
     */
    public void set(String key, String value, long time) {
        try {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 设置过期时间
     * @param key
     * @param timeout
     */
    public void expire(String key, long timeout) {
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    public Long getExpire(String key){
        return redisTemplate.getExpire(key,TimeUnit.SECONDS);
    }

    /**
     * 删除
     * @param key
     * @return
     */
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 判断是否存在
     * @param key
     * @return
     */
    public Boolean exist(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 获取值
     * @param key
     * @return
     */
    public String getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
