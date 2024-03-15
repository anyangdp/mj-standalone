package com.mj.web.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mj.web.system.domain.dobj.STenantResourceDO;
import com.mj.web.system.domain.dobj.ext.STenantResourceExtDO;
import com.mj.web.system.domain.dto.request.STenantResourceQueryDTO;
import com.mj.web.system.domain.dto.response.STenantResourceRspDTO;

import java.util.List;

public interface STenantResourceService extends IService<STenantResourceDO>{
    IPage<STenantResourceRspDTO> selectPageByCondition(IPage<STenantResourceExtDO> page, STenantResourceQueryDTO request);

    List<STenantResourceDO> selectListByUserId(String userId);
}
