package com.mj.web.system.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 租户用户
 */
@ApiModel(value = "租户用户")
@Accessors(chain = true)
@Data
public class STenantUserDTO {

    @ApiModelProperty(value = "租户id")
    private String tenantId;
    @ApiModelProperty(value = "用户id")
    private String userId;

}
