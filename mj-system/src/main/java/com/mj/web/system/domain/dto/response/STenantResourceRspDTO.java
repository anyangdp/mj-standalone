package com.mj.web.system.domain.dto.response;

import com.mj.framework.handler.AbstractDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 租户
 */
@ApiModel(value = "租户")
@Accessors(chain = true)
@Data
public class STenantResourceRspDTO extends AbstractDTO<String> {

    @ApiModelProperty(value = "租户名称")
    private String name;
    @ApiModelProperty(value = "描述")
    private String description;
    @ApiModelProperty(value = "资产类型")
    private String type;
    @ApiModelProperty(value = "资产id")
    private String resourceId;
    @ApiModelProperty(value = "资产名称")
    private String resourceName;

}
