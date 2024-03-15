package com.mj.web.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mj.framework.annotation.PermissionActionResource;
import com.mj.framework.constants.ErrorCodeEnum;
import com.mj.framework.handler.AbstractCRUDHandler;
import com.mj.framework.handler.ControllerTemplate;
import com.mj.framework.handler.ErrorDTO;
import com.mj.framework.handler.GenericResponse;
import com.mj.framework.util.ValueUtil;
import com.mj.web.system.domain.dobj.SPermissionDO;
import com.mj.web.system.domain.dto.SPermissionDTO;
import com.mj.web.system.domain.dto.request.SPermissionSortDTO;
import com.mj.web.system.domain.dto.response.SMenuPermissionRspDTO;
import com.mj.web.system.service.SPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Api(tags = "菜单管理")
@RestController
@RequestMapping("/s/permission")
public class SPermissionController extends AbstractCRUDHandler<Long, SPermissionDTO, SPermissionService> {

    @ApiOperation(value = "添加", notes = "菜单添加")
    @PermissionActionResource(id ="menu-create", name = "添加", des = "菜单添加")
    @PostMapping("/create")
    public GenericResponse<SPermissionDTO> create(@RequestBody @Valid SPermissionDTO request, BindingResult bindingResult) throws Exception {
        return ControllerTemplate.call(bindingResult, response -> {
            if (request.getLevel() > 1 && StringUtils.isEmpty(request.getParentId())) {
                response.setError(new ErrorDTO(ErrorCodeEnum.PARAM_IS_INVALID.getCode(), "子菜单，必须指定上级"));
                return;
            }
            SPermissionDO dump = ValueUtil.dump(request, SPermissionDO.class);
            boolean save = getService().save(dump);
            // if (save && request.getLevel() > 1) {
            //     sMenuPermissionService.save(new SMenuPermissionDO().setMenuId(request.getParentId()).setPermissionId(request.getId()));
            // }
            response.setResult(save);
        });
    }

    @ApiOperation(value = "更新", notes = "菜单更新")
    @PermissionActionResource(id ="menu-update", name = "修改", des = "菜单修改")
    @PutMapping("/update")
    public GenericResponse<Void> update(@RequestBody @Valid SPermissionDTO request, BindingResult bindingResult) throws Exception {
        return ControllerTemplate.call(bindingResult, response -> {
            if (request.getLevel() > 1 && StringUtils.isEmpty(request.getParentId())) {
                response.setError(new ErrorDTO(ErrorCodeEnum.PARAM_IS_INVALID.getCode(), "子菜单，必须指定上级"));
                return;
            }
            SPermissionDO byId = getService().getById(request.getId());
            boolean update = getService().updateById(ValueUtil.dump(request, SPermissionDO.class));
            response.setResult(update);
        });
    }

    @ApiOperation(value = "菜单树排序", notes = "菜单树排序")
    @PermissionActionResource(id ="menu-sort", name = "修改", des = "菜单修改")
    @PutMapping("/sort")
    public GenericResponse<Void> sort(@RequestBody @Valid SPermissionSortDTO request, BindingResult bindingResult) throws Exception {
        return ControllerTemplate.call(bindingResult, response -> {
            SPermissionDTO dropNode = request.getDropNode();
            SPermissionDTO draggingNode = request.getDraggingNode();
            if (request.getDropType().equals("before")) {
                draggingNode.setSort(request.getDropNode().getSort() + 1).setLevel(request.getDropNode().getLevel()).setParentId(request.getDropNode().getParentId());
                getService().updateById(ValueUtil.dump(draggingNode, SPermissionDO.class));
                List<SPermissionDO> list = getService().list(new LambdaQueryWrapper<SPermissionDO>().eq(SPermissionDO::getLevel, dropNode.getLevel()).orderByDesc(SPermissionDO::getSort));
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getId().equals(dropNode.getId())) {
                        List<SPermissionDO> sPermissionDOS = list.subList(0, i);
                        for (int j = i - 1; j >=0; j--) {
                            sPermissionDOS.get(j).setSort(draggingNode.getSort() + j * 10);
                        }
                        response.setResult(getService().updateBatchById(sPermissionDOS));
                        break;
                    }
                }
            } else {
                draggingNode.setSort(request.getDropNode().getSort() - 1).setLevel(request.getDropNode().getLevel()).setParentId(request.getDropNode().getParentId());
                getService().updateById(ValueUtil.dump(draggingNode, SPermissionDO.class));
                List<SPermissionDO> list = getService().list(new LambdaQueryWrapper<SPermissionDO>().eq(SPermissionDO::getLevel, dropNode.getLevel()).orderByDesc(SPermissionDO::getSort));
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getId().equals(draggingNode.getId())) {
                        List<SPermissionDO> sPermissionDOS = list.subList(0, i);
                        for (int j = i - 1; j >=0; j--) {
                            sPermissionDOS.get(j).setSort(draggingNode.getSort() + j * 10);
                        }
                        response.setResult(getService().updateBatchById(sPermissionDOS));
                        break;
                    }
                }
            }
        });
    }

    @ApiOperation(value = "删除", notes = "菜单删除")
    @PermissionActionResource(id ="menu-delete", name = "删除", des = "菜单删除")
    @DeleteMapping("/{id}")
    @Override
    public GenericResponse<Void> delete(@PathVariable String id) throws Exception {
        return ControllerTemplate.call(response -> {
            if (null == getService().getById(id)) {
                response.setError(new ErrorDTO(ErrorCodeEnum.DATA_NOT_EXIST.getCode(), "菜单不存在"));
                return;
            }
            boolean b = getService().removeById(id);
            response.setResult(b);
        });
    }

    @ApiOperation(value = "批量删除", notes = "菜单批量删除")
    @PermissionActionResource(id ="menu-delete", name = "删除", des = "菜单删除")
    @DeleteMapping("/batch")
    public GenericResponse<Void> delete(@RequestBody List<String> ids) throws Exception {
        return ControllerTemplate.call(response -> {
            boolean b = getService().removeBatchByIds(ids);
            response.setResult(b);
        });
    }

    @ApiOperation(value = "查询", notes = "菜单树查询")
    @PermissionActionResource(id ="menu-tree", name = "菜单树查询", des = "菜单树查询")
    @PostMapping(value = "/list")
    public GenericResponse<List<SMenuPermissionRspDTO>> list(@RequestBody @Valid SPermissionDTO request, BindingResult bindingResult) throws Exception {
        return ControllerTemplate.call(bindingResult, response -> {
            response.setData(getService().findMenuPermissionTree(request));
            response.setResult(true);
        });
    }
}