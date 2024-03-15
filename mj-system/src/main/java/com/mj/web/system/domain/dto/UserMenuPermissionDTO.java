package com.mj.web.system.domain.dto;

import com.mj.framework.handler.AbstractDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户菜单权限关联表
 */
@ApiModel(value = "用户菜单权限关联表")
@Accessors(chain = true)
@Data
public class UserMenuPermissionDTO extends AbstractDTO<String> {

    @ApiModelProperty(value = "用户id")
    private String userId;
    @ApiModelProperty(value = "权限id")
    private String permissionId;

}
