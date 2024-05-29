package com.mj.web.system.service.impl;

import com.mj.framework.service.AbstractService;
import com.mj.web.system.domain.dobj.SDeptDO;
import com.mj.web.system.domain.dto.SDeptDTO;
import com.mj.web.system.service.SDeptService;
import com.mj.web.system.mapper.SDeptMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class SDeptServiceImpl extends AbstractService<SDeptMapper, SDeptDO, SDeptDTO> implements SDeptService {

}
