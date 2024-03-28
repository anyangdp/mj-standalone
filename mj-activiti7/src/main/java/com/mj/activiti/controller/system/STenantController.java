package com.mj.activiti.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mj.framework.handler.AbstractCRUDHandler;
import com.mj.framework.handler.ControllerTemplate;
import com.mj.framework.handler.GenericResponse;
import com.mj.framework.util.ValueUtil;
import com.mj.web.system.domain.dobj.STenantResourceDO;
import com.mj.web.system.domain.dobj.STenantUserDO;
import com.mj.web.system.domain.dto.STenantDTO;
import com.mj.web.system.domain.dto.STenantResourceDTO;
import com.mj.web.system.domain.dto.STenantUserDTO;
import com.mj.web.system.domain.dto.request.STenantResourceBindDTO;
import com.mj.web.system.domain.dto.request.STenantResourceQueryDTO;
import com.mj.web.system.domain.dto.request.STenantUserBindDTO;
import com.mj.web.system.domain.dto.request.STenantUserQueryDTO;
import com.mj.web.system.domain.dto.response.STenantResourceRspDTO;
import com.mj.web.system.domain.dto.response.STenantUserRspDTO;
import com.mj.web.system.service.STenantResourceService;
import com.mj.web.system.service.STenantService;
import com.mj.web.system.service.STenantUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Api(tags = "租户管理")
@RestController
@RequestMapping("/s/tenant")
public class STenantController extends AbstractCRUDHandler<Long, STenantDTO, STenantService> {
    @Autowired
    private STenantUserService sTenantUserService;

    @Autowired
    private STenantResourceService sTenantResourceService;

    @ApiOperation(value = "创建", notes = "创建")
    @PostMapping("/create")
    @Override
    public GenericResponse<STenantDTO> create(@RequestBody @Valid STenantDTO request, BindingResult bindingResult) throws Exception {
        return super.create(request, bindingResult);
    }

    @ApiOperation(value = "租户用户绑定", notes = "租户用户绑定")
    @PostMapping("/user/bind")
    public GenericResponse<Void> bind(@RequestBody @Valid STenantUserDTO request, BindingResult bindingResult) throws Exception {
        return ControllerTemplate.call(bindingResult,response -> {
            boolean save = sTenantUserService.save(ValueUtil.dump(request, STenantUserDO.class));
            response.setResult(save);
        });
    }

    @ApiOperation(value = "租户批量用户绑定", notes = "租户批量用户绑定（替代数据库中原始绑定列表）")
    @PostMapping("/user/batch/bind")
    public GenericResponse<Void> batchBind(@RequestBody @Valid STenantUserBindDTO request, BindingResult bindingResult) throws Exception {
        return ControllerTemplate.call(bindingResult,response -> {
            response.setResult(sTenantUserService.remove(new LambdaQueryWrapper<>(new STenantUserDO().setTenantId(request.getTenantId()))));
            if (!CollectionUtils.isEmpty(request.getUserId())) {
                List<STenantUserDO> list = new ArrayList<>();
                request.getUserId().forEach(item -> list.add(new STenantUserDO().setUserId(item).setTenantId(request.getTenantId())));
                response.setResult(sTenantUserService.saveBatch(list));
            }
        });
    }

    @ApiOperation(value = "租户用户解绑", notes = "租户用户解绑")
    @PostMapping("/user/unbind")
    public GenericResponse<Void> unbind(@RequestBody @Valid STenantUserDTO request, BindingResult bindingResult) throws Exception {
        return ControllerTemplate.call(bindingResult,response -> {
            boolean remove = sTenantUserService.remove(new LambdaQueryWrapper<>(ValueUtil.dump(request, STenantUserDO.class)));
            response.setResult(remove);
        });
    }


    @ApiOperation(value = "租户用户分页查询", notes = "租户用户分页查询")
    @PostMapping(value = "/user/page/{page}/{pageSize}")
    public GenericResponse<IPage<STenantUserRspDTO>> doUserPage(@PathVariable Integer page, @PathVariable Integer pageSize,
                                                                @RequestBody @Valid STenantUserQueryDTO request, BindingResult bindingResult) throws Exception {
        return ControllerTemplate.call(bindingResult,response -> {
            response.setData(sTenantUserService.selectPageByCondition(new Page<>(page, pageSize), request)).setResult(true);
        });
    }
    @ApiOperation(value = "租户资产绑定", notes = "租户资产绑定")
    @PostMapping("/resource/bind")
    public GenericResponse<Void> bind(@RequestBody @Valid STenantResourceDTO request, BindingResult bindingResult) throws Exception {
        return ControllerTemplate.call(bindingResult,response -> {
            boolean save = sTenantResourceService.save(ValueUtil.dump(request, STenantResourceDO.class));
            response.setResult(save);
        });
    }


    @ApiOperation(value = "租户批量资产绑定", notes = "租户批量资产绑定（替代数据库中原始绑定列表）")
    @PostMapping("/resource/batch/bind")
    public GenericResponse<Void> batchBind(@RequestBody @Valid STenantResourceBindDTO request, BindingResult bindingResult) throws Exception {
        return ControllerTemplate.call(bindingResult,response -> {
            response.setResult(sTenantResourceService.remove(new LambdaQueryWrapper<>(new STenantResourceDO().setTenantId(request.getTenantId()))));
            if (!CollectionUtils.isEmpty(request.getList())) {
                List<STenantResourceDO> list = new ArrayList<>();
                request.getList().forEach(item -> list.add(new STenantResourceDO().setType(item.getType()).setResourceId(item.getResourceId()).setTenantId(request.getTenantId())));
                response.setResult(sTenantResourceService.saveBatch(list));
            }
        });
    }

    @ApiOperation(value = "租户资产解绑", notes = "租户资产解绑")
    @PostMapping("/resource/unbind")
    public GenericResponse<Void> unbind(@RequestBody @Valid STenantResourceDTO request, BindingResult bindingResult) throws Exception {
        return ControllerTemplate.call(bindingResult,response -> {
            boolean remove = sTenantResourceService.remove(new LambdaQueryWrapper<>(ValueUtil.dump(request, STenantResourceDO.class)));
            response.setResult(remove);
        });
    }


    @ApiOperation(value = "租户资产分页查询", notes = "租户资产分页查询")
    @PostMapping(value = "/resource/page/{page}/{pageSize}")
    public GenericResponse<IPage<STenantResourceRspDTO>> doUserPage(@PathVariable Integer page, @PathVariable Integer pageSize,
                                                                    @RequestBody @Valid STenantResourceQueryDTO request, BindingResult bindingResult) throws Exception {
        return ControllerTemplate.call(bindingResult,response -> {
            response.setData(sTenantResourceService.selectPageByCondition(new Page<>(page, pageSize), request)).setResult(true);
        });
    }

    @ApiOperation(value = "修改", notes = "修改")
    @PutMapping("/update")
    @Override
    public GenericResponse<Void> update(@RequestBody @Valid STenantDTO request, BindingResult bindingResult) throws Exception {
        return super.update(request, bindingResult);
    }

    @ApiOperation(value = "分页查询", notes = "租户分页查询")
    @PostMapping(value = "/page/{page}/{pageSize}")
    @Override
    public GenericResponse<Page<STenantDTO>> doPage(@PathVariable Integer page, @PathVariable Integer pageSize,
                                                 @RequestBody @Valid STenantDTO request, BindingResult bindingResult) throws Exception {
        return super.doPage(page, pageSize, request, bindingResult);
    }

    // @ApiOperation(value = "租户资产分页查询", notes = "租户资产分页查询")
    // @PostMapping(value = "/page/{page}/{pageSize}")
    // @Override
    // public GenericResponse<Page<STenantDTO>> doPage(@PathVariable Integer page, @PathVariable Integer pageSize,
    //                                              @RequestBody @Valid STenantDTO request, BindingResult bindingResult) throws Exception {
    //     return super.doPage(page, pageSize, request, bindingResult);
    // }
    //
    // @ApiOperation(value = "租户用户分页查询", notes = "租户用户分页查询")
    // @PostMapping(value = "/page/{page}/{pageSize}")
    // @Override
    // public GenericResponse<Page<STenantDTO>> doPage(@PathVariable Integer page, @PathVariable Integer pageSize,
    //                                              @RequestBody @Valid STenantDTO request, BindingResult bindingResult) throws Exception {
    //     return super.doPage(page, pageSize, request, bindingResult);
    // }

    @ApiOperation(value = "id查询", notes = "id查询")
    @GetMapping("/retrieve/{id}")
    @Override
    public GenericResponse<STenantDTO> retrieve(@PathVariable String id) throws Exception {
        return super.retrieve(id);
    }

    @ApiOperation(value = "删除", notes = "删除")
    @DeleteMapping(value = "/{id}")
    @Override
    public GenericResponse<Void> logicDelete(@PathVariable String id) throws Exception {
        return super.logicDelete(id);
    }

    @ApiOperation(value = "启用/禁用", notes = "启用/禁用")
    @GetMapping(value = "/active/{id}/{active}")
    public GenericResponse<STenantDTO> active(@PathVariable String id, @PathVariable boolean active) throws Exception {
        if (active) {
            return super.active(id);
        }
        return super.deActive(id);
    }
}