package com.mj.framework.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @Author anyang
 * @CreateTime 2020/3/19
 * @Des
 */
public class PageUtil<T> {


    public static <T> IPage<T> convertMybatisPlus(Page page, Class dtoClass) {
        IPage<T> iPage = new Page(page.getCurrent(), page.getSize(), page.getTotal());
        iPage.setRecords(ValueUtil.dumpList(page.getRecords(), dtoClass));
        return iPage;
    }

    public static <T> IPage<T> convertMybatisPlus(IPage page, Class dtoClass) {
        IPage<T> iPage = new Page(page.getCurrent(), page.getSize(), page.getTotal());
        iPage.setRecords(ValueUtil.dumpList(page.getRecords(), dtoClass));
        return iPage;
    }
}
