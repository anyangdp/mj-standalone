package com.mj.web.system.domain.dto;

import com.mj.framework.handler.AbstractDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 部门
 */
@ApiModel(value = "部门")
@Accessors(chain = true)
@Data
public class SDeptDTO extends AbstractDTO<String> {

    @ApiModelProperty(value = "部门名")
    private String name;
    @ApiModelProperty(value = "上级部门")
    private String parentId;

}
