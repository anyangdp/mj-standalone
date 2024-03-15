package com.mj.web.system.domain.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 角色权限表
 */
@ApiModel(value = "RoleAuthorizeDTO")
@Accessors(chain = true)
@Data
public class RoleAuthorizeDTO {

    @NotBlank(message = "角色id不能为空")
    @ApiModelProperty(value = "角色id")
    private String roleId;
    @ApiModelProperty(value = "菜单权限")
    private List<String> permissionList;

}
