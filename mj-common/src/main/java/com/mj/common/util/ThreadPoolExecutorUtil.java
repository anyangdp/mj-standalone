package com.mj.common.util;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author anyang
 * @CreateTime 2020/1/16
 * @Des 线程池工具类
 */
public class ThreadPoolExecutorUtil {
    public static ThreadPoolExecutor linkBlockingPool = new ThreadPoolExecutor(5, 10, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
}
