package com.mj.web.system.domain.dobj;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mj.framework.handler.AbstractDO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 角色
 */
@TableName("s_role")
@Accessors(chain = true)
@Data
public class SRoleDO extends AbstractDO<String> {

    // 
    private String name;
    // 
    private String description;
    // 排序
    private Integer sort;

}
