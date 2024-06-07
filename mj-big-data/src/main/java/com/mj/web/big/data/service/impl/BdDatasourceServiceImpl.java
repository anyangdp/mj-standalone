package com.mj.web.big.data.service.impl;

import com.mj.framework.service.AbstractService;
import com.mj.web.big.data.domain.dobj.BdDatasourceDO;
import com.mj.web.big.data.domain.dto.BdDatasourceDTO;
import com.mj.web.big.data.service.BdDatasourceService;
import com.mj.web.big.data.mapper.BdDatasourceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class BdDatasourceServiceImpl extends AbstractService<BdDatasourceMapper, BdDatasourceDO, BdDatasourceDTO> implements BdDatasourceService {

}
