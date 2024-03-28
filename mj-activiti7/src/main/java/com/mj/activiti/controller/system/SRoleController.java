package com.mj.activiti.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mj.common.exception.exception.BizException;
import com.mj.common.util.SnowFlake;
import com.mj.framework.handler.AbstractCRUDHandler;
import com.mj.framework.handler.ControllerTemplate;
import com.mj.framework.handler.GenericResponse;
import com.mj.framework.util.PageUtil;
import com.mj.framework.util.ValueUtil;
import com.mj.web.system.domain.dobj.SRoleDO;
import com.mj.web.system.domain.dobj.SRoleMenuPermissionDO;
import com.mj.web.system.domain.dobj.SRoleUserDO;
import com.mj.web.system.domain.dobj.UserDO;
import com.mj.web.system.domain.dto.SRoleDTO;
import com.mj.web.system.domain.dto.SRoleMenuPermissionDTO;
import com.mj.web.system.domain.dto.request.BindRoleUserDTO;
import com.mj.web.system.domain.dto.request.RoleAuthorizeDTO;
import com.mj.web.system.domain.dto.request.RoleUserRspDTO;
import com.mj.web.system.domain.dto.request.SRoleQueryDTO;
import com.mj.web.system.service.SRoleMenuPermissionService;
import com.mj.web.system.service.SRoleService;
import com.mj.web.system.service.SRoleUserService;
import com.mj.web.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "角色管理")
@RestController
@RequestMapping("/sRole")
public class SRoleController extends AbstractCRUDHandler<Long, SRoleDTO, SRoleService> {
    @Autowired
    private SRoleService sRoleService;

    @Autowired
    private SnowFlake snowFlake;

    @Autowired
    private SRoleMenuPermissionService sRoleMenuPermissionService;

    @Autowired
    private SRoleUserService sRoleUserService;

    @Autowired
    private UserService userService;


    @Override
    @ApiOperation(value = "创建", notes = "创建")
    @PostMapping("/create")
    public GenericResponse<SRoleDTO> create(@RequestBody @Valid SRoleDTO request, BindingResult bindingResult) throws Exception {
        return super.create(request, bindingResult);
    }

    @Override
    @ApiOperation(value = "修改", notes = "修改")
    @PutMapping("/update")
    public GenericResponse<Void> update(@RequestBody @Valid SRoleDTO request, BindingResult bindingResult) throws Exception {
        return ControllerTemplate.call(bindingResult, (GenericResponse<Void> response) -> {
            boolean save = sRoleService.updateById(ValueUtil.dump(request, SRoleDO.class));
            response.setResult(save);
        });
    }

    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping(value = "/page/{page}/{pageSize}")
    public GenericResponse<IPage<SRoleDTO>> page(@PathVariable Integer page, @PathVariable Integer pageSize,
                                                 @RequestBody @Valid SRoleDTO request, BindingResult bindingResult) throws Exception {
        return ControllerTemplate.call(bindingResult, response -> {
            LambdaQueryWrapper<SRoleDO> sRoleDOLambdaQueryWrapper = new LambdaQueryWrapper<>();
            if (StringUtils.isNotBlank(request.getName())) {
                sRoleDOLambdaQueryWrapper.like(SRoleDO::getName, request.getName());
            }
            sRoleDOLambdaQueryWrapper.orderByDesc(SRoleDO::getCreatedAt);
            IPage<SRoleDTO> rolePage = PageUtil.convertMybatisPlus(getService().page(new Page<>(page, pageSize), sRoleDOLambdaQueryWrapper), SRoleDTO.class);
            rolePage.getRecords().forEach(item -> {
                if (StringUtils.isNotBlank(item.getCreatedBy())) {
                    UserDO byId = userService.getById(item.getCreatedBy());
                    if (null != byId) {
                        item.setCreatedByName(byId.getNickname());
                    }
                }
            });
            response.setResult(true).setData(rolePage);
        });
    }

    @Override
    @ApiOperation(value = "id查询", notes = "id查询")
    @GetMapping("/retrieve/{id}")
    public GenericResponse<SRoleDTO> retrieve(@PathVariable String id) throws Exception {
        return ControllerTemplate.call(response -> {
            SRoleDO byId = sRoleService.getById(id);
            if (null == byId) {
                throw new BizException("不存在");
            } else {
                response.setData(ValueUtil.dump(byId, SRoleDTO.class)).setResult(true);
            }
        });
    }

    @Override
    @ApiOperation(value = "删除", notes = "删除")
    @DeleteMapping(value = "/{id}")
    public GenericResponse<Void> delete(@PathVariable String id) throws Exception {
        return ControllerTemplate.call(response -> {
            SRoleDO byId = sRoleService.getById(id);
            if (null == byId) {
                throw new BizException("不存在");
            } else {
                response.setResult(sRoleService.removeById(id));
            }
        });
    }


    @ApiOperation(value = "启用/禁用", notes = "启用/禁用")
    @GetMapping(value = "/active/{id}/{active}")
    public GenericResponse<SRoleDTO> active(@PathVariable String id, @PathVariable boolean active) throws Exception {
        if (active) {
            return super.active(id);
        }
        return super.deActive(id);
    }

    @ApiOperation(value = "权限配置", notes = "权限配置")
    @PostMapping("/authorize")
    public GenericResponse<Void> authorize(@RequestBody @Valid RoleAuthorizeDTO request, BindingResult bindingResult) throws Exception {
        return ControllerTemplate.call(bindingResult, response -> {
            response.setResult(sRoleMenuPermissionService.remove(new LambdaQueryWrapper<>(new SRoleMenuPermissionDO().setRoleId(request.getRoleId()))));
            if (!CollectionUtils.isEmpty(request.getPermissionList())) {
                List<SRoleMenuPermissionDO> list = new ArrayList<>();
                request.getPermissionList().forEach(item -> list.add(new SRoleMenuPermissionDO().setRoleId(request.getRoleId()).setPermissionId(item)));
                response.setResult(sRoleMenuPermissionService.saveBatch(list));
            }
        });
    }

    @ApiOperation(value = "角色权限", notes = "角色权限")
    @GetMapping("/authority/{roleId}")
    public GenericResponse<List<SRoleMenuPermissionDTO>> authority(@PathVariable String roleId) throws Exception {
        return ControllerTemplate.call(response -> {
            response.setResult(true).setData(ValueUtil.dumpList(sRoleMenuPermissionService.list(new LambdaQueryWrapper<>(new SRoleMenuPermissionDO().setRoleId(roleId))), SRoleMenuPermissionDTO.class));
        });
    }

    @ApiOperation(value = "分配用户", notes = "分配用户")
    @PostMapping(value = "/bind/user")
    public GenericResponse<Void> roleBindUser(@RequestBody @Valid BindRoleUserDTO request, BindingResult bindingResult) throws Exception {
        return ControllerTemplate.call(bindingResult, response -> {
            sRoleUserService.remove(new LambdaQueryWrapper<SRoleUserDO>().eq(SRoleUserDO::getRoleId, request.getRoleId()));
            if (!CollectionUtils.isEmpty(request.getUserList())) {
                List<SRoleUserDO> list = new ArrayList<>();
                for (String s : request.getUserList()) {
                    list.add(new SRoleUserDO().setRoleId(request.getRoleId()).setUserId(s));
                }
                sRoleUserService.saveBatch(list);
            }
            response.setResult(true);
        });
    }

    @ApiOperation(value = "角色用户", notes = "角色用户")
    @PostMapping(value = "/role/user")
    public GenericResponse<List<RoleUserRspDTO>> roleUser(@RequestBody SRoleQueryDTO request, BindingResult bindingResult) throws Exception {
        return ControllerTemplate.call(bindingResult, response -> {
            response.setResult(true).setData(sRoleUserService.roleUser(request));
        });
    }

}