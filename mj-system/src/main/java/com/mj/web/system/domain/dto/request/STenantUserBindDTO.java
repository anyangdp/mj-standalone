package com.mj.web.system.domain.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 租户用户
 */
@ApiModel(value = "STenantUserBindDTO")
@Accessors(chain = true)
@Data
public class STenantUserBindDTO {

    @ApiModelProperty(value = "租户id")
    private String tenantId;
    @ApiModelProperty(value = "用户id")
    private List<String> userId;

}
