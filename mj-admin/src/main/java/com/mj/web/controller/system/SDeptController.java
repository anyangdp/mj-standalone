package com.mj.web.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mj.common.exception.exception.BizException;
import com.mj.framework.annotation.PermissionActionResource;
import com.mj.framework.constants.ErrorCodeEnum;
import com.mj.framework.handler.AbstractCRUDHandler;
import com.mj.framework.handler.ControllerTemplate;
import com.mj.framework.handler.ErrorDTO;
import com.mj.framework.handler.GenericResponse;
import com.mj.framework.util.PageUtil;
import com.mj.framework.util.ValueUtil;
import com.mj.web.system.domain.dobj.SDeptDO;
import com.mj.web.system.domain.dobj.SRoleDO;
import com.mj.web.system.domain.dobj.SUserDO;
import com.mj.web.system.domain.dto.SDeptDTO;
import com.mj.web.system.domain.dto.SPermissionDTO;
import com.mj.web.system.domain.dto.SRoleDTO;
import com.mj.web.system.domain.dto.response.SDeptRspDTO;
import com.mj.web.system.domain.dto.response.SMenuPermissionRspDTO;
import com.mj.web.system.service.SDeptService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/s/dept")
public class SDeptController extends AbstractCRUDHandler<Long, SDeptDTO, SDeptService> {
    @Override
    @ApiOperation(value = "创建", notes = "创建")
    @PostMapping("/create")
    public GenericResponse<SDeptDTO> create(@RequestBody @Valid SDeptDTO request, BindingResult bindingResult) throws Exception {
        return super.create(request, bindingResult);
    }

    @Override
    @ApiOperation(value = "修改", notes = "修改")
    @PutMapping("/update")
    public GenericResponse<Void> update(@RequestBody @Valid SDeptDTO request, BindingResult bindingResult) throws Exception {
        return ControllerTemplate.call(bindingResult, (GenericResponse<Void> response) -> {
            boolean save = getService().updateById(ValueUtil.dump(request, SDeptDO.class));
            if (!save) {
                throw new BizException("修改失败");
            }
            response.setResult(save);
        });
    }

    @ApiOperation(value = "查询", notes = "部门树查询")
    @PermissionActionResource(id ="dept-tree", name = "部门树查询", des = "部门树查询")
    @PostMapping(value = "/list")
    public GenericResponse<List<SDeptRspDTO>> list(@RequestBody @Valid SDeptDTO request, BindingResult bindingResult) throws Exception {
        return ControllerTemplate.call(bindingResult, response -> {
            response.setData(getService().findTree(request));
            response.setResult(true);
        });
    }

    @Override
    @ApiOperation(value = "id查询", notes = "id查询")
    @GetMapping("/retrieve/{id}")
    public GenericResponse<SDeptDTO> retrieve(@PathVariable String id) throws Exception {
        return ControllerTemplate.call(response -> {
            SDeptDO byId = getService().getById(id);
            if (null == byId) {
                throw new BizException("不存在");
            } else {
                response.setData(ValueUtil.dump(byId, SDeptDTO.class)).setResult(true);
            }
        });
    }

    @Override
    @ApiOperation(value = "删除", notes = "删除")
    @DeleteMapping(value = "/{id}")
    public GenericResponse<Void> delete(@PathVariable String id) throws Exception {
        return ControllerTemplate.call(response -> {
            SDeptDO byId = getService().getById(id);
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
    public GenericResponse<SDeptDTO> active(@PathVariable String id, @PathVariable boolean active) throws Exception {
        if (active) {
            return super.active(id);
        }
        return super.deActive(id);
    }
}