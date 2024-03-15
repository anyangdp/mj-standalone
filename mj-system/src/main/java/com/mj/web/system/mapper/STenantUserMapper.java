package com.mj.web.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mj.web.system.domain.dobj.STenantUserDO;
import com.mj.web.system.domain.dobj.ext.STenantUserExtDO;
import com.mj.web.system.domain.dto.request.STenantUserQueryDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface STenantUserMapper extends BaseMapper<STenantUserDO>{
    IPage<STenantUserExtDO> selectPageByCondition(IPage<STenantUserExtDO> page, STenantUserQueryDTO request);
}
