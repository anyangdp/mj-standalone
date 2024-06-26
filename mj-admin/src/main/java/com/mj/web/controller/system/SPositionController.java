package com.mj.web.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mj.common.exception.exception.BizException;
import com.mj.framework.handler.AbstractCRUDHandler;
import com.mj.framework.handler.ControllerTemplate;
import com.mj.framework.handler.GenericResponse;
import com.mj.framework.util.PageUtil;
import com.mj.framework.util.ValueUtil;
import com.mj.web.system.domain.dobj.SPositionDO;
import com.mj.web.system.domain.dto.SPositionDTO;
import com.mj.web.system.service.SPositionService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/s/position")
public class SPositionController extends AbstractCRUDHandler<Long, SPositionDTO, SPositionService> {
    @Override
    @ApiOperation(value = "创建", notes = "创建")
    @PostMapping("/create")
    public GenericResponse<SPositionDTO> create(@RequestBody @Valid SPositionDTO request, BindingResult bindingResult) throws Exception {
        return super.create(request, bindingResult);
    }

    @Override
    @ApiOperation(value = "修改", notes = "修改")
    @PutMapping("/update")
    public GenericResponse<Void> update(@RequestBody @Valid SPositionDTO request, BindingResult bindingResult) throws Exception {
        return ControllerTemplate.call(bindingResult, (GenericResponse<Void> response) -> {
            boolean save = getService().updateById(ValueUtil.dump(request, SPositionDO.class));
            response.setResult(save);
        });
    }

    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping(value = "/page/{page}/{pageSize}")
    public GenericResponse<IPage<SPositionDTO>> page(@PathVariable Integer page, @PathVariable Integer pageSize,
                                                 @RequestBody @Valid SPositionDTO request, BindingResult bindingResult) throws Exception {
        return ControllerTemplate.call(bindingResult, response -> {
            LambdaQueryWrapper<SPositionDO> sRoleDOLambdaQueryWrapper = new LambdaQueryWrapper<>();
            if (StringUtils.isNotBlank(request.getName())) {
                sRoleDOLambdaQueryWrapper.like(SPositionDO::getName, request.getName());
            }
            sRoleDOLambdaQueryWrapper.orderByDesc(SPositionDO::getCreatedAt);
            IPage<SPositionDTO> rolePage = PageUtil.convertMybatisPlus(getService().page(new Page<>(page, pageSize), sRoleDOLambdaQueryWrapper), SPositionDTO.class);
            response.setResult(true).setData(rolePage);
        });
    }

    @Override
    @ApiOperation(value = "id查询", notes = "id查询")
    @GetMapping("/retrieve/{id}")
    public GenericResponse<SPositionDTO> retrieve(@PathVariable String id) throws Exception {
        return ControllerTemplate.call(response -> {
            SPositionDO byId = getService().getById(id);
            if (null == byId) {
                throw new BizException("不存在");
            } else {
                response.setData(ValueUtil.dump(byId, SPositionDTO.class)).setResult(true);
            }
        });
    }

    @Override
    @ApiOperation(value = "删除", notes = "删除")
    @DeleteMapping(value = "/{id}")
    public GenericResponse<Void> delete(@PathVariable String id) throws Exception {
        return ControllerTemplate.call(response -> {
            SPositionDO byId = getService().getById(id);
            if (null == byId) {
                throw new BizException("不存在");
            } else {
                response.setResult(getService().removeById(id));
            }
        });
    }


    @ApiOperation(value = "启用/禁用", notes = "启用/禁用")
    @GetMapping(value = "/active/{id}/{active}")
    public GenericResponse<SPositionDTO> active(@PathVariable String id, @PathVariable boolean active) throws Exception {
        if (active) {
            return super.active(id);
        }
        return super.deActive(id);
    }
}