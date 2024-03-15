package com.mj.common.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @Author anyang
 * @CreateTime 2022/9/29
 * @Des
 */
@Slf4j
public class ClassLoaderUtil {
    public static ClassLoader getClassLoader(String url) {
        try {
            Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            // URLClassLoader classLoader = new URLClassLoader(new URL[]{}, ClassLoader.getSystemClassLoader());
            URLClassLoader classLoader = new URLClassLoader(new URL[]{}, ClassLoaderUtil.class.getClassLoader());
            method.invoke(classLoader, new URL(url));
            return classLoader;
        } catch (Exception e) {
            log.error("getClassLoader-error", e);
            return null;
        }
    }
}
