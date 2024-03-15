package com.mj.web.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mj.framework.util.PageUtil;
import com.mj.framework.util.ValueUtil;
import com.mj.web.system.domain.dobj.SRoleUserDO;
import com.mj.web.system.domain.dto.request.RoleUserRspDTO;
import com.mj.web.system.domain.dto.request.SRoleQueryDTO;
import com.mj.web.system.domain.dto.response.UserRoleRspDTO;
import com.mj.web.system.mapper.SRoleUserMapper;
import com.mj.web.system.service.SRoleUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class SRoleUserServiceImpl extends ServiceImpl<SRoleUserMapper, SRoleUserDO> implements SRoleUserService {
    @Override
    public IPage<UserRoleRspDTO> userRolePage(Integer page, Integer pageSize, SRoleQueryDTO sRoleQueryDTO) {
        return PageUtil.convertMybatisPlus(baseMapper.userRolePage(new Page<>(page, pageSize), sRoleQueryDTO), UserRoleRspDTO.class);
    }
    @Override
    public List<RoleUserRspDTO> roleUser(SRoleQueryDTO sRoleQueryDTO) {
        return ValueUtil.dumpList(baseMapper.roleUser(sRoleQueryDTO), RoleUserRspDTO.class);
    }
}
