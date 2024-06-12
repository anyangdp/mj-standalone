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
import com.mj.web.big.data.config.SqlQuery;
import com.mj.web.big.data.domain.dobj.BdDatasourceDO;
import com.mj.web.big.data.domain.dto.BdDatasourceDTO;
import com.mj.web.big.data.service.BdDatasourceService;
import com.mj.web.big.data.service.DbOperateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

@Api(value = "数据源管理", tags = "数据源管理")
@RestController
@RequestMapping("/bdDatasource")
public class BdDatasourceController extends AbstractCRUDHandler<Long, BdDatasourceDTO, BdDatasourceService> {

    @Autowired
    private DbOperateService dbOperateService;

    @Override
    @ApiOperation(value = "创建", notes = "创建")
    @PostMapping("/create")
    public GenericResponse<BdDatasourceDTO> create(@RequestBody @Valid BdDatasourceDTO request, BindingResult bindingResult) throws Exception {
        return super.create(request, bindingResult);
    }

    @Override
    @ApiOperation(value = "修改", notes = "修改")
    @PutMapping("/update")
    public GenericResponse<Void> update(@RequestBody @Valid BdDatasourceDTO request, BindingResult bindingResult) throws Exception {
        return ControllerTemplate.call(bindingResult, (GenericResponse<Void> response) -> {
            boolean save = getService().updateById(ValueUtil.dump(request, BdDatasourceDO.class));
            if (!save) {
                throw new BizException("修改失败");
            }
            response.setResult(save);
        });
    }
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping(value = "/page/{page}/{pageSize}")
    public GenericResponse<IPage<BdDatasourceDTO>> page(@PathVariable Integer page, @PathVariable Integer pageSize,
                                                 @RequestBody @Valid BdDatasourceDTO request, BindingResult bindingResult) throws Exception {
        return ControllerTemplate.call(bindingResult, response -> {
            LambdaQueryWrapper<BdDatasourceDO> condition = new LambdaQueryWrapper<>();
            condition.eq(StringUtils.isNotBlank(request.getType()), BdDatasourceDO::getType, request.getType())
                    .eq(StringUtils.isNotBlank(request.getDescription()), BdDatasourceDO::getDescription, request.getDescription())
                    .orderByDesc(BdDatasourceDO::getCreatedAt);
            IPage<BdDatasourceDTO> rolePage = PageUtil.convertMybatisPlus(getService().page(new Page<>(page, pageSize), condition), BdDatasourceDTO.class);
            response.setResult(true).setData(rolePage);
        });
    }

    @Override
    @ApiOperation(value = "id查询", notes = "id查询")
    @GetMapping("/retrieve/{id}")
    public GenericResponse<BdDatasourceDTO> retrieve(@PathVariable String id) throws Exception {
        return ControllerTemplate.call(response -> {
            BdDatasourceDO byId = getService().getById(id);
            if (null == byId) {
                throw new BizException("不存在");
            } else {
                response.setData(ValueUtil.dump(byId, BdDatasourceDTO.class)).setResult(true);
            }
        });
    }

    @ApiOperation(value = "连通性测试", notes = "连通性测试")
    @GetMapping("/connect/test/{id}")
    public GenericResponse<Void> test(@PathVariable String id) throws Exception {
        return ControllerTemplate.call(response -> {
            Connection connection = dbOperateService.getConnection(id).getConnection();
            if (null == connection) {
                throw new BizException("连接测试失败");
            }
            response.success();
        });
    }

    @ApiOperation(value = "数据表", notes = "数据表")
    @GetMapping("/table/{id}")
    public GenericResponse<List<String>> table(@PathVariable String id) throws Exception {
        return ControllerTemplate.call(response -> {
            response.setData(dbOperateService.showTables(id)).success();
        });
    }

    @Override
    @ApiOperation(value = "删除", notes = "删除")
    @DeleteMapping(value = "/{id}")
    public GenericResponse<Void> delete(@PathVariable String id) throws Exception {
        return ControllerTemplate.call(response -> {
            BdDatasourceDO byId = getService().getById(id);
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
    public GenericResponse<BdDatasourceDTO> active(@PathVariable String id, @PathVariable boolean active) throws Exception {
        if (active) {
            return super.active(id);
        }
        return super.deActive(id);
    }
}