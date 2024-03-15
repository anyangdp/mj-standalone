package com.mj.web.controller.system;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mj.framework.constants.ErrorCodeEnum;
import com.mj.framework.handler.AbstractCRUDHandler;
import com.mj.framework.handler.ControllerTemplate;
import com.mj.framework.handler.ErrorDTO;
import com.mj.framework.handler.GenericResponse;
import com.mj.framework.util.ValueUtil;
import com.mj.security.SecurityUserDetails;
import com.mj.web.system.domain.dobj.UserDO;
import com.mj.web.system.domain.dobj.UserMenuPermissionDO;
import com.mj.web.system.domain.dto.UserDTO;
import com.mj.web.system.domain.dto.UserMenuPermissionDTO;
import com.mj.web.system.domain.dto.request.ChangePasswordDTO;
import com.mj.web.system.domain.dto.request.ResetPasswordDTO;
import com.mj.web.system.domain.dto.request.UserAuthorizeDTO;
import com.mj.web.system.service.UserMenuPermissionService;
import com.mj.web.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController extends AbstractCRUDHandler<Long, UserDTO, UserService> {
    @Autowired
    private UserMenuPermissionService userMenuPermissionService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @ApiOperation(value = "创建", notes = "创建")
    @PostMapping("/create")
    @Override
    public GenericResponse<UserDTO> create(@RequestBody @Valid UserDTO request, BindingResult bindingResult) throws Exception {
        return super.create(request, bindingResult);
    }

    @ApiOperation(value = "修改", notes = "修改")
    @PutMapping("/update")
    @Override
    public GenericResponse<Void> update(@RequestBody @Valid UserDTO request, BindingResult bindingResult) throws Exception {
        return super.update(request, bindingResult);
    }


    @ApiOperation(value = "重设密码", notes = "重设密码")
    @PutMapping("/reset/password")
    public GenericResponse<Void> resetPassword(@RequestBody @Valid ResetPasswordDTO request, BindingResult bindingResult) throws Exception {
        return ControllerTemplate.call(bindingResult, response -> {
            SecurityUserDetails user = JSON.parseObject(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString(), SecurityUserDetails.class);
            if (!user.getUsername().equals("admin")) {
                response.setError(new ErrorDTO(ErrorCodeEnum.BIZ_OPERATE_ERROR.getCode(), "禁止操作"));
                return;
            }
            response.setResult(getService().updateById((UserDO) new UserDO().setPassword(bCryptPasswordEncoder.encode(request.getNewPassword())).setId(request.getId())));
        });
    }

    @ApiOperation(value = "修改密码", notes = "修改密码")
    @PutMapping("/change/password")
    public GenericResponse<Void> changePassword(@RequestBody @Valid ChangePasswordDTO request, BindingResult bindingResult) throws Exception {
        return ControllerTemplate.call(bindingResult, response -> {
            SecurityUserDetails user = JSON.parseObject(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString(), SecurityUserDetails.class);
            if (bCryptPasswordEncoder.matches(request.getOldPassword(), user.getPassword())) {
                response.setResult(getService().updateById((UserDO) new UserDO()
                        .setPassword(bCryptPasswordEncoder.encode(request.getNewPassword()))
                        .setId(user.getId())));
            } else {
                response.setError(new ErrorDTO(ErrorCodeEnum.BIZ_OPERATE_ERROR.getCode(), "密码错误"));
            }
        });
    }

    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping(value = "/page/{page}/{pageSize}")
    @Override
    public GenericResponse<Page<UserDTO>> doPage(@PathVariable Integer page, @PathVariable Integer pageSize,
                                                       @RequestBody @Valid UserDTO request, BindingResult bindingResult) throws Exception {
        return super.doPage(page, pageSize, request, bindingResult);
    }

    @ApiOperation(value = "id查询", notes = "id查询")
    @GetMapping("/retrieve/{id}")
    @Override
    public GenericResponse<UserDTO> retrieve(@PathVariable String id) throws Exception {
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
    public GenericResponse<UserDTO> active(@PathVariable String id, @PathVariable boolean active) throws Exception {
        if (active) {
            return super.active(id);
        }
        return super.deActive(id);
    }

    @ApiOperation(value = "权限配置", notes = "权限配置")
    @PostMapping("/authorize")
    public GenericResponse<Void> authorize(@RequestBody @Valid UserAuthorizeDTO request, BindingResult bindingResult) throws Exception {
        return ControllerTemplate.call(bindingResult, response -> {
            response.setResult(userMenuPermissionService.remove(new LambdaQueryWrapper<>(new UserMenuPermissionDO().setUserId(request.getUserId()))));
            if (!CollectionUtils.isEmpty(request.getPermissionList())) {
                List<UserMenuPermissionDO> list = new ArrayList<>();
                request.getPermissionList().forEach(item -> list.add(new UserMenuPermissionDO().setUserId(request.getUserId()).setPermissionId(item)));
                response.setResult(userMenuPermissionService.saveBatch(list));
            }
        });
    }

    @ApiOperation(value = "用户权限", notes = "用户权限")
    @GetMapping("/authority/{userId}")
    public GenericResponse<List<UserMenuPermissionDTO>> authority(@PathVariable String userId) throws Exception {
        return ControllerTemplate.call(response -> {
            response.setResult(true).setData(ValueUtil.dumpList(userMenuPermissionService.list(new LambdaQueryWrapper<>(new UserMenuPermissionDO().setUserId(userId))), UserMenuPermissionDTO.class));
        });
    }
}