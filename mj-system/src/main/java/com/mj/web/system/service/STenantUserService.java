package com.mj.web.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mj.web.system.domain.dobj.STenantUserDO;
import com.mj.web.system.domain.dobj.ext.STenantUserExtDO;
import com.mj.web.system.domain.dto.request.STenantUserQueryDTO;
import com.mj.web.system.domain.dto.response.STenantUserRspDTO;

public interface STenantUserService extends IService<STenantUserDO> {
    IPage<STenantUserRspDTO> selectPageByCondition(IPage<STenantUserExtDO> page, STenantUserQueryDTO request);
}
