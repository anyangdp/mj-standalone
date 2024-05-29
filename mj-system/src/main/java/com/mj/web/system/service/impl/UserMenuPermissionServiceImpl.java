package com.mj.web.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mj.web.system.domain.dobj.SUserMenuPermissionDO;
import com.mj.web.system.mapper.UserMenuPermissionMapper;
import com.mj.web.system.service.UserMenuPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class UserMenuPermissionServiceImpl extends ServiceImpl<UserMenuPermissionMapper, SUserMenuPermissionDO> implements UserMenuPermissionService {

}
