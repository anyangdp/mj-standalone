package com.mj.web.system.domain.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 用户表
 */
@ApiModel(value = "UserAuthorizeDTO")
@Accessors(chain = true)
@Data
public class UserAuthorizeDTO {

    @ApiModelProperty(value = "用户名")
    private String userId;
    @ApiModelProperty(value = "菜单权限")
    private List<String> permissionList;

}
