package com.mj.web.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mj.framework.util.PageUtil;
import com.mj.web.system.domain.dobj.STenantResourceDO;
import com.mj.web.system.domain.dobj.ext.STenantResourceExtDO;
import com.mj.web.system.domain.dto.request.STenantResourceQueryDTO;
import com.mj.web.system.domain.dto.response.STenantResourceRspDTO;
import com.mj.web.system.mapper.STenantResourceMapper;
import com.mj.web.system.service.STenantResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class STenantResourceServiceImpl extends ServiceImpl<STenantResourceMapper, STenantResourceDO> implements STenantResourceService {
    @Override
    public IPage<STenantResourceRspDTO> selectPageByCondition(IPage<STenantResourceExtDO> page, STenantResourceQueryDTO request) {
        return PageUtil.convertMybatisPlus(baseMapper.selectPageByCondition(page, request), STenantResourceRspDTO.class);
    }

    @Override
    public List<STenantResourceDO> selectListByUserId(String userId) {
        return baseMapper.selectListByUserId(userId);
    }
}
