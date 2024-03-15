package com.mj.web.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mj.web.system.domain.dobj.SRoleUserDO;
import com.mj.web.system.domain.dto.request.RoleUserRspDTO;
import com.mj.web.system.domain.dto.request.SRoleQueryDTO;
import com.mj.web.system.domain.dto.response.UserRoleRspDTO;

import java.util.List;

public interface SRoleUserService extends IService<SRoleUserDO> {
    IPage<UserRoleRspDTO> userRolePage(Integer page, Integer pageSize, SRoleQueryDTO sRoleQueryDTO);
    List<RoleUserRspDTO> roleUser(SRoleQueryDTO sRoleQueryDTO);
}
