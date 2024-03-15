package com.mj.web.system.domain.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户表
 */
@ApiModel(value = "SRoleQueryDTO")
@Accessors(chain = true)
@Data
public class SRoleQueryDTO implements Serializable {

    private static final long serialVersionUID = 7926316033525480186L;
    @ApiModelProperty(value = "用户id")
    private String userId;
    @ApiModelProperty(value = "角色id")
    private String roleId;

}
