package com.mj.web.system.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mj.framework.handler.AbstractDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 角色
 */
@ApiModel(value = "角色")
@Accessors(chain = true)
@Data
public class SRoleDTO extends AbstractDTO<String> {

    @ApiModelProperty(value = "")
    private String name;
    @ApiModelProperty(value = "")
    private String description;
    @ApiModelProperty(value = "排序")
    private Integer sort;
    @ApiModelProperty(value = "启用/停用")
    private Boolean active;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    private String createdBy;
    private String createdByName;

}
