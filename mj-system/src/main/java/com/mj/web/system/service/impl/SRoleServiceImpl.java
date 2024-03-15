package com.mj.web.system.service.impl;

import com.mj.framework.service.AbstractService;
import com.mj.web.system.domain.dobj.SRoleDO;
import com.mj.web.system.domain.dto.SRoleDTO;
import com.mj.web.system.mapper.SRoleMapper;
import com.mj.web.system.service.SRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class SRoleServiceImpl extends AbstractService<SRoleMapper, SRoleDO, SRoleDTO> implements SRoleService {

}
