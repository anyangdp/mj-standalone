package com.mj.web.system.domain.dobj;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mj.framework.handler.AbstractDO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 平台菜单/权限表
 */
@TableName("s_permission")
@Accessors(chain = true)
@Data
public class SPermissionDO extends AbstractDO<String> {

    // 名称
    private String name;
    // 0默认显示，1 只对超级管理显示
    private Integer showAlways;
    // 层级：1，2，3
    private Integer level;
    // 类型 0顶部菜单 1页面 2具体操作
    private Integer type;
    // 页面标题
    private String title;
    // 页面路径/资源链接url
    private String path;
    // 图标
    private String icon;
    // http请求方法
    private String httpMethod;
    // 父id
    private String parentId;
    // 描述
    private String description;
    // 排序
    private Integer sort;
}
