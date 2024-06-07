package com.mj.web.big.data.service.impl;

import com.mj.framework.service.AbstractService;
import com.mj.web.big.data.domain.dobj.BdEtlTaskInfoDO;
import com.mj.web.big.data.domain.dto.BdEtlTaskInfoDTO;
import com.mj.web.big.data.service.BdEtlTaskInfoService;
import com.mj.web.big.data.mapper.BdEtlTaskInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class BdEtlTaskInfoServiceImpl extends AbstractService<BdEtlTaskInfoMapper, BdEtlTaskInfoDO, BdEtlTaskInfoDTO> implements BdEtlTaskInfoService {

}
