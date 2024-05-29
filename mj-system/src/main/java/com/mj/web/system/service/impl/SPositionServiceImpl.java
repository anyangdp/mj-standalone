package com.mj.web.system.service.impl;

import com.mj.framework.service.AbstractService;
import com.mj.web.system.domain.dobj.SPositionDO;
import com.mj.web.system.domain.dto.SPositionDTO;
import com.mj.web.system.service.SPositionService;
import com.mj.web.system.mapper.SPositionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class SPositionServiceImpl extends AbstractService<SPositionMapper, SPositionDO, SPositionDTO> implements SPositionService {

}
