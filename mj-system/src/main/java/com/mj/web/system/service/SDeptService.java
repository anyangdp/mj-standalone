package com.mj.web.system.service;

import com.mj.framework.service.CRUDService;
import com.mj.web.system.domain.dobj.SDeptDO;
import com.mj.web.system.domain.dto.SDeptDTO;
import com.mj.web.system.domain.dto.SPermissionDTO;
import com.mj.web.system.domain.dto.response.SDeptRspDTO;
import com.mj.web.system.domain.dto.response.SMenuPermissionRspDTO;

import java.util.List;

public interface SDeptService extends CRUDService<SDeptDTO, SDeptDO> {
    List<SDeptRspDTO> findTree(SDeptDTO request) throws Exception;
}
