package com.mj.web.big.data.controller;

import com.mj.framework.handler.AbstractCRUDHandler;
import com.mj.web.big.data.domain.dto.BdEtlTaskInfoDTO;
import com.mj.web.big.data.service.BdEtlTaskInfoService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/bdEtlTaskInfo")
public class BdEtlTaskInfoController extends AbstractCRUDHandler<Long, BdEtlTaskInfoDTO, BdEtlTaskInfoService> {

}