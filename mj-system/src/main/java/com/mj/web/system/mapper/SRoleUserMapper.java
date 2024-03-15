package com.mj.web.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mj.web.system.domain.dobj.SRoleUserDO;
import com.mj.web.system.domain.dobj.ext.RoleUserExtDO;
import com.mj.web.system.domain.dobj.ext.UserRoleExtDO;
import com.mj.web.system.domain.dto.request.SRoleQueryDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SRoleUserMapper extends BaseMapper<SRoleUserDO>{
    IPage<UserRoleExtDO> userRolePage(IPage<UserRoleExtDO> page, SRoleQueryDTO request);

    List<RoleUserExtDO> roleUser(SRoleQueryDTO request);
}
