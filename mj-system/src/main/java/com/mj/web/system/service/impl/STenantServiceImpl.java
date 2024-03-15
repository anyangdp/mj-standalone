package com.mj.web.system.service.impl;

import com.mj.framework.service.AbstractService;
import com.mj.web.system.domain.dobj.STenantDO;
import com.mj.web.system.domain.dto.STenantDTO;
import com.mj.web.system.mapper.STenantMapper;
import com.mj.web.system.service.STenantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class STenantServiceImpl extends AbstractService<STenantMapper, STenantDO, STenantDTO> implements STenantService {

}
