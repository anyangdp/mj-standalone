package com.mj.web.system.domain.dobj.ext;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author anyang
 * @CreateTime 2023/2/6
 * @Des
 */
@Data
@ApiModel(value = "RoleUserExtDO")
public class RoleUserExtDO {
    @ApiModelProperty(value = "用户id")
    private String userId;
}
