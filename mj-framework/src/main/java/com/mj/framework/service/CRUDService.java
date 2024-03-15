package com.mj.framework.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.List;

/**
 * @Author anyang
 * @CreateTime 2019/11/25
 * @Des
 */
public interface CRUDService<DTO, T> extends IService<T> {
    /**
     * 插入一条记录
     *
     * @param entity 实体对象
     */
    DTO insert(DTO entity);

    DTO retrieve(Serializable id);

    DTO retrieveByCondition(DTO condition);

    boolean update(DTO entity);

    boolean delete(Serializable id);

    boolean logicDelete(Serializable id);

    boolean active(Serializable id);

    boolean deActive(Serializable id);

    List<DTO> list(DTO condition);

    IPage<DTO> page(IPage<T> page, DTO condition);
}
