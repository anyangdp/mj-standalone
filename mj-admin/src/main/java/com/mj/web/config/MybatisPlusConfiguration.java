package com.mj.web.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author anyang
 * @CreateTime 2019/10/8
 * @Des
 */
@Configuration
@MapperScan(basePackages = {"com.mj.web.*.mapper"})
@EnableTransactionManagement(proxyTargetClass = true)
// 启动事务管理。为什么使用 proxyTargetClass 参数，参见 https://blog.csdn.net/huang_550/article/details/76492600
public class MybatisPlusConfiguration {

    public static ThreadLocal<String> tableSuffix = new ThreadLocal<>();

    // @Bean
    // public PaginationInterceptor paginationInterceptor() {
    //     PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
    //     // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
    //     // paginationInterceptor.setOverflow(false);
    //     // 设置最大单页限制数量，默认 500 条，-1 不受限制
    //     // paginationInterceptor.setLimit(500);
    //     return paginationInterceptor;
    // }

    /**
     * 添加分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
