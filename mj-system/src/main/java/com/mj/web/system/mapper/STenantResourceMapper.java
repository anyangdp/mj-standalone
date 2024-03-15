package com.mj.web.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mj.web.system.domain.dobj.STenantResourceDO;
import com.mj.web.system.domain.dobj.ext.STenantResourceExtDO;
import com.mj.web.system.domain.dto.request.STenantResourceQueryDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface STenantResourceMapper extends BaseMapper<STenantResourceDO>{
    IPage<STenantResourceExtDO> selectPageByCondition(IPage<STenantResourceExtDO> page, STenantResourceQueryDTO request);

    List<STenantResourceDO> selectListByUserId(String userId);
}
