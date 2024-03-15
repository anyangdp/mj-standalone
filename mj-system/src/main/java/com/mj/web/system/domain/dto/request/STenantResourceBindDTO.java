package com.mj.web.system.domain.dto.request;

import com.mj.web.system.domain.dto.STenantResourceDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 租户资产（数据权限）
 */
@ApiModel(value = "STenantResourceBindDTO（数据权限）")
@Accessors(chain = true)
@Data
public class STenantResourceBindDTO {
    @ApiModelProperty(value = "租户id")
    private String tenantId;
    @ApiModelProperty(value = "租户资产列表")
    private List<STenantResourceDTO> list;

}
