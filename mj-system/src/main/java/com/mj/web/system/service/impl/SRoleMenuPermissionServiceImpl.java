package com.mj.web.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mj.web.system.domain.dobj.SRoleMenuPermissionDO;
import com.mj.web.system.mapper.SRoleMenuPermissionMapper;
import com.mj.web.system.service.SRoleMenuPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class SRoleMenuPermissionServiceImpl extends ServiceImpl<SRoleMenuPermissionMapper, SRoleMenuPermissionDO> implements SRoleMenuPermissionService {

}
