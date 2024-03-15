package com.mj.web.system.domain.dto;

import com.mj.framework.handler.AbstractDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 平台菜单/权限表
 */
@ApiModel(value = "平台菜单/权限表")
@Accessors(chain = true)
@Data
public class SPermissionDTO extends AbstractDTO<String> {

    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "0默认显示，1 只对超级管理显示")
    private Integer showAlways;
    @ApiModelProperty(value = "层级：1，2，3")
    private Integer level;
    @ApiModelProperty(value = "类型 0顶部菜单 1页面 2具体操作")
    private Integer type;
    @ApiModelProperty(value = "页面标题")
    private String title;
    @ApiModelProperty(value = "页面路径/资源链接url")
    private String path;
    @ApiModelProperty(value = "图标")
    private String icon;
    @ApiModelProperty(value = "http请求方法")
    private String httpMethod;
    @ApiModelProperty(value = "父id")
    private String parentId;
    @ApiModelProperty(value = "描述")
    private String description;
    @ApiModelProperty(value = "排序")
    private Integer sort;

}
