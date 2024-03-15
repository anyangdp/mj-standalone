package com.mj.web.system.domain.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @Author anyang
 * @CreateTime 2023/2/6
 * @Des
 */
@Data
@ApiModel(value = "BindUserRoleDTO")
public class BindRoleUserDTO {
    @NotBlank(message = "角色id不能为空")
    @ApiModelProperty(value = "角色id")
    private String roleId;
    @ApiModelProperty(value = "用户列表")
    private List<String> userList;
}
