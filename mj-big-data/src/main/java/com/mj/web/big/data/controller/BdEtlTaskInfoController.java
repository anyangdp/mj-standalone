package com.mj.web.big.data.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mj.common.exception.exception.BizException;
import com.mj.framework.annotation.PermissionActionResource;
import com.mj.framework.handler.AbstractCRUDHandler;
import com.mj.framework.handler.ControllerTemplate;
import com.mj.framework.handler.GenericResponse;
import com.mj.framework.util.PageUtil;
import com.mj.framework.util.ValueUtil;
import com.mj.web.big.data.domain.dobj.BdEtlTaskInfoDO;
import com.mj.web.big.data.domain.dto.BdEtlTaskInfoDTO;
import com.mj.web.big.data.domain.dto.BdEtlTaskInfoDTO;
import com.mj.web.big.data.service.BdEtlTaskInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Connection;
import java.util.List;
@Api(value = "etl任务管理", tags = "etl任务管理")
@RestController
@RequestMapping("/bdEtlTaskInfo")
public class BdEtlTaskInfoController extends AbstractCRUDHandler<Long, BdEtlTaskInfoDTO, BdEtlTaskInfoService> {
    @Override
    @ApiOperation(value = "创建", notes = "创建")
    @PostMapping("/create")
    public GenericResponse<BdEtlTaskInfoDTO> create(@RequestBody @Valid BdEtlTaskInfoDTO request, BindingResult bindingResult) throws Exception {
        return super.create(request, bindingResult);
    }

    @Override
    @ApiOperation(value = "修改", notes = "修改")
    @PutMapping("/update")
    public GenericResponse<Void> update(@RequestBody @Valid BdEtlTaskInfoDTO request, BindingResult bindingResult) throws Exception {
        return ControllerTemplate.call(bindingResult, (GenericResponse<Void> response) -> {
            boolean save = getService().updateById(ValueUtil.dump(request, BdEtlTaskInfoDO.class));
            if (!save) {
                throw new BizException("修改失败");
            }
            response.setResult(save);
        });
    }
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping(value = "/page/{page}/{pageSize}")
    public GenericResponse<IPage<BdEtlTaskInfoDTO>> page(@PathVariable Integer page, @PathVariable Integer pageSize,
                                                        @RequestBody @Valid BdEtlTaskInfoDTO request, BindingResult bindingResult) throws Exception {
        return ControllerTemplate.call(bindingResult, response -> {
            LambdaQueryWrapper<BdEtlTaskInfoDO> condition = new LambdaQueryWrapper<>();
            condition.eq(StringUtils.isNotBlank(request.getType()), BdEtlTaskInfoDO::getType, request.getType())
                    .eq(StringUtils.isNotBlank(request.getDescription()), BdEtlTaskInfoDO::getDescription, request.getDescription())
                    .orderByDesc(BdEtlTaskInfoDO::getCreatedAt);
            IPage<BdEtlTaskInfoDTO> rolePage = PageUtil.convertMybatisPlus(getService().page(new Page<>(page, pageSize), condition), BdEtlTaskInfoDTO.class);
            response.setResult(true).setData(rolePage);
        });
    }

    @Override
    @ApiOperation(value = "id查询", notes = "id查询")
    @GetMapping("/retrieve/{id}")
    public GenericResponse<BdEtlTaskInfoDTO> retrieve(@PathVariable String id) throws Exception {
        return ControllerTemplate.call(response -> {
            BdEtlTaskInfoDO byId = getService().getById(id);
            if (null == byId) {
                throw new BizException("不存在");
            } else {
                response.setData(ValueUtil.dump(byId, BdEtlTaskInfoDTO.class)).setResult(true);
            }
        });
    }

    @Override
    @ApiOperation(value = "删除", notes = "删除")
    @DeleteMapping(value = "/{id}")
    public GenericResponse<Void> delete(@PathVariable String id) throws Exception {
        return ControllerTemplate.call(response -> {
            BdEtlTaskInfoDO byId = getService().getById(id);
            if (null == byId) {
                throw new BizException("不存在");
            } else {
                response.setResult(getService().removeById(id));
            }
        });
    }

    @ApiOperation(value = "批量删除", notes = "菜单批量删除")
    @PermissionActionResource(id ="dept-batch-delete", name = "删除", des = "菜单删除")
    @DeleteMapping("/batch")
    public GenericResponse<Void> delete(@RequestBody List<String> ids) throws Exception {
        return ControllerTemplate.call(response -> {
            boolean b = getService().removeBatchByIds(ids);
            response.setResult(b);
        });
    }


    @ApiOperation(value = "启用/禁用", notes = "启用/禁用")
    @GetMapping(value = "/active/{id}/{active}")
    public GenericResponse<BdEtlTaskInfoDTO> active(@PathVariable String id, @PathVariable boolean active) throws Exception {
        if (active) {
            return super.active(id);
        }
        return super.deActive(id);
    }
}