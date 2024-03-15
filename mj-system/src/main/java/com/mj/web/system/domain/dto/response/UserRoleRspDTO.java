package com.mj.web.system.domain.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author anyang
 * @CreateTime 2023/2/6
 * @Des
 */
@Data
@ApiModel(value = "UserRoleRspDTO")
public class UserRoleRspDTO {
    @ApiModelProperty(value = "角色id")
    private String id;
    @ApiModelProperty(value = "角色名")
    private String name;
    @ApiModelProperty(value = "描述")
    private String description;
    @ApiModelProperty(value = "排序")
    private Integer sort;
    @ApiModelProperty(value = "角色是否绑定")
    private Integer flag;
}
