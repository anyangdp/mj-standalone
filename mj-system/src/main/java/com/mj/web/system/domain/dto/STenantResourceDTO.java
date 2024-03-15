package com.mj.web.system.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 租户资产（数据权限）
 */
@ApiModel(value = "租户资产（数据权限）")
@Accessors(chain = true)
@Data
public class STenantResourceDTO {

    @ApiModelProperty(value = "租户id")
    private String tenantId;
    @ApiModelProperty(value = "资产类型")
    private String type;
    @ApiModelProperty(value = "资产id")
    private String resourceId;

}
