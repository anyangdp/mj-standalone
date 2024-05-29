package com.mj.web.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mj.framework.service.AbstractService;
import com.mj.web.system.domain.dobj.SUserPositionDO;
import com.mj.web.system.domain.dto.SUserPositionDTO;
import com.mj.web.system.service.SUserPositionService;
import com.mj.web.system.mapper.SUserPositionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class SUserPositionServiceImpl extends ServiceImpl<SUserPositionMapper, SUserPositionDO> implements SUserPositionService {

}
