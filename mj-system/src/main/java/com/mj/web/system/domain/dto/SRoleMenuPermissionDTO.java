package com.mj.web.system.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 */
@ApiModel(value = "SRoleMenuPermissionDTO")
@Accessors(chain = true)
@Data
public class SRoleMenuPermissionDTO {

    @ApiModelProperty(value = "角色id")
    private String roleId;
    @ApiModelProperty(value = "权限id")
    private String permissionId;

}
