package com.mj.web.big.data.controller;

import com.alibaba.fastjson2.JSON;
import com.mj.common.exception.exception.BizException;
import com.mj.framework.handler.ControllerTemplate;
import com.mj.framework.handler.GenericResponse;
import com.mj.framework.util.ValueUtil;
import com.mj.web.big.data.domain.bo.etl.EtlConfiguration;
import com.mj.web.big.data.domain.dobj.BdEtlTaskInfoDO;
import com.mj.web.big.data.domain.dto.BdEtlTaskInfoDTO;
import com.mj.web.big.data.service.BdEtlTaskInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(value = "测试接口", tags = "测试接口")
@AllArgsConstructor
@RestController
@RequestMapping("/test")
public class TestController {
    private final BdEtlTaskInfoService bdEtlTaskInfoService;
    @ApiOperation(value = "测试调度etl任务", notes = "测试调度etl任务")
    @GetMapping("/etl/{id}")
    public GenericResponse<Void> test1(@PathVariable String id) throws Exception {
        return ControllerTemplate.call(response -> {
            BdEtlTaskInfoDO byId = bdEtlTaskInfoService.getById(id);
            EtlConfiguration etlConfiguration = JSON.parseObject(byId.getDescription(), EtlConfiguration.class);
            log.info(JSON.toJSONString(etlConfiguration));
        });
    }
}