package com.mj.web.system.service.impl;

import com.mj.framework.service.AbstractService;
import com.mj.web.system.domain.dobj.SDictDO;
import com.mj.web.system.domain.dto.SDictDTO;
import com.mj.web.system.service.SDictService;
import com.mj.web.system.mapper.SDictMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class SDictServiceImpl extends AbstractService<SDictMapper, SDictDO, SDictDTO> implements SDictService {

}
