package com.mj.web.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mj.framework.util.PageUtil;
import com.mj.web.system.domain.dobj.STenantUserDO;
import com.mj.web.system.domain.dobj.ext.STenantUserExtDO;
import com.mj.web.system.domain.dto.request.STenantUserQueryDTO;
import com.mj.web.system.domain.dto.response.STenantUserRspDTO;
import com.mj.web.system.mapper.STenantUserMapper;
import com.mj.web.system.service.STenantUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class STenantUserServiceImpl extends ServiceImpl<STenantUserMapper, STenantUserDO> implements STenantUserService {
    @Override
    public IPage<STenantUserRspDTO> selectPageByCondition(IPage<STenantUserExtDO> page, STenantUserQueryDTO request) {
        return PageUtil.convertMybatisPlus(baseMapper.selectPageByCondition(page, request), STenantUserRspDTO.class);
    }
}
