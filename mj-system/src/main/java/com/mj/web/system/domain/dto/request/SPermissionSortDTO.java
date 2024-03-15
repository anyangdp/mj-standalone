package com.mj.web.system.domain.dto.request;

import com.mj.web.system.domain.dto.SPermissionDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 排序
 */
@ApiModel(value = "SPermissionSortDTO")
@Accessors(chain = true)
@Data
public class SPermissionSortDTO {
    @ApiModelProperty(value = "拖拽目标")
    private SPermissionDTO draggingNode;
    @ApiModelProperty(value = "放置的目标位置")
    private SPermissionDTO dropNode;
    @ApiModelProperty(value = "放置在目标位置前后位置：before，after")
    private String dropType;

}
