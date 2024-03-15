package com.mj.web.system.domain.dto;

import com.mj.framework.handler.AbstractDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 角色用户
 */
@ApiModel(value = "角色用户")
@Accessors(chain = true)
@Data
public class SRoleUserDTO extends AbstractDTO<String> {

    @ApiModelProperty(value = "角色id")
    private String roleId;
    @ApiModelProperty(value = "用户id")
    private String userId;

}
