package com.mj.web.system.service;


import com.mj.framework.service.CRUDService;
import com.mj.web.system.domain.dobj.SPermissionDO;
import com.mj.web.system.domain.dto.SPermissionDTO;
import com.mj.web.system.domain.dto.response.SMenuPermissionRspDTO;

import java.util.List;

public interface SPermissionService extends CRUDService<SPermissionDTO, SPermissionDO> {
    List<SMenuPermissionRspDTO> findMenuPermissionTree(SPermissionDTO request);

    List<SPermissionDO> findAllMenu();

    List<SPermissionDO> findByType(Integer type);
}
