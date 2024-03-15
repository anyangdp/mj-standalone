package com.mj.web.system.domain.dto.response;

import com.mj.framework.handler.AbstractDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@ApiModel(value = "菜单")
@Accessors(chain = true)
@Data
public class SMenuPermissionRspDTO extends AbstractDTO<String> {
    private String id;
    @ApiModelProperty(value = "菜单名")
    private String name;
    @ApiModelProperty(value = "是否总是显示：0不显示，1 显示", hidden = true)
    private Integer showAlways = 1;
    @ApiModelProperty(value = "类型 0顶部菜单 1页面")
    private Integer type = 0;
    @ApiModelProperty(value = "菜单层次：1，2，3...")
    private Integer level;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "资源url")
    private String path;
    @ApiModelProperty(value = "前端组件")
    private String component;
    @ApiModelProperty(value = "父id")
    private String parentId;
    @ApiModelProperty(value = "图标")
    private String icon;
    @ApiModelProperty(value = "排序值")
    private Integer sort;
    @ApiModelProperty(value = "状态:是否启用 1启用 0禁用")
    private Integer status = 1;
    @ApiModelProperty(value = "子菜单")
    private List<SMenuPermissionRspDTO> children;
}
