package com.mj.framework.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mj.common.util.CommonUtil;
import com.mj.framework.util.ReflectionUtils;
import com.mj.common.util.SnowFlake;
import com.mj.framework.handler.AbstractDO;
import com.mj.framework.handler.AbstractDTO;
import com.mj.framework.util.ValueUtil;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

/**
 * @Author anyang
 * @CreateTime 2019/11/25
 * @Des
 */
public abstract class AbstractService<M extends BaseMapper<DO>, DO extends AbstractDO, DTO extends AbstractDTO> extends ServiceImpl<M, DO> implements CRUDService<DTO, DO> {

    private Class<DO> doClass;

    private Class<DTO> dtoClass;

    @Resource
    protected SnowFlake snowFlake;

    public AbstractService() {
        super();
        dtoClass = ReflectionUtils.getClassGenricType(getClass(), 2);
        doClass = ReflectionUtils.getClassGenricType(getClass(), 1);
    }

    @Override
    public DTO insert(DTO entity) {
        DO target = ValueUtil.dump(entity, doClass);
        if (StringUtils.isEmpty(entity.getId())) {
            target.setId(snowFlake.nextIdStr());
        }
        save(target);
        entity = ValueUtil.dump(target, dtoClass);
        return entity;
    }

    @Override
    public DTO retrieve(Serializable id) {
        DO byId = super.getById(id);
        if (null == byId) {
            return null;
        }
        return ValueUtil.dump(byId, dtoClass);
    }

    @Override
    public DTO retrieveByCondition(DTO condition) {
        QueryWrapper<DO> queryWrapper = new QueryWrapper<>();
        queryWrapper.allEq(CommonUtil.objectToMapLowerUnderScoreKey(condition), false);
        DO one = getOne(queryWrapper);
        return ValueUtil.dump(one, dtoClass);
    }

    @Override
    public boolean update(DTO entity) {
        DO dump = ValueUtil.dump(entity, doClass);
        return updateById(dump);
    }

    @Override
    public boolean delete(Serializable id) {
        return removeById(id);
    }

    @Override
    public boolean logicDelete(Serializable id) {
        try {
            DO aDo = doClass.newInstance();
            aDo.setDeleted(true);
            aDo.setId(id);
            boolean b = updateById(aDo);
            return b;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean active(Serializable id) {
        try {
            DO aDo = doClass.newInstance();
            aDo.setActive(true);
            aDo.setId(id);
            boolean b = updateById(aDo);
            return b;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean deActive(Serializable id) {
        try {
            DO aDo = doClass.newInstance();
            aDo.setActive(false);
            aDo.setId(id);
            boolean b = updateById(aDo);
            return b;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<DTO> list(DTO condition) {
        DO dump = ValueUtil.dump(condition, doClass);
        QueryWrapper<DO> queryWrapper = new QueryWrapper<>();
        queryWrapper.allEq(CommonUtil.objectToMapLowerUnderScoreKey(dump), false);
        return ValueUtil.dumpList(list(queryWrapper), dtoClass);
    }

    @Override
    public IPage<DTO> page(IPage<DO> page, DTO condition) {
        DO dump = ValueUtil.dump(condition, doClass);
        QueryWrapper<DO> queryWrapper = new QueryWrapper<>();
        queryWrapper.allEq(CommonUtil.objectToMapLowerUnderScoreKey(dump), false);
        Function<DO, DTO> fcPage = (n) -> ValueUtil.dump(n, dtoClass);
        IPage<DTO> dtoIPage = page(page, queryWrapper).convert(fcPage);
        return dtoIPage;
    }
}
