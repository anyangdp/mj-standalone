package com.mj.web.system.controller;

import com.mj.framework.handler.AbstractCRUDHandler;
import com.mj.web.system.domain.dto.SDictDTO;
import com.mj.web.system.service.SDictService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/sDict")
public class SDictController extends AbstractCRUDHandler<Long, SDictDTO, SDictService> {

}