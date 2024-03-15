package com.mj.web.system.domain.dobj;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mj.framework.handler.AbstractDO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 租户
 */
@TableName("s_tenant")
@Accessors(chain = true)
@Data
public class STenantDO extends AbstractDO<String> {

    // 租户名称
    private String name;
    // 描述
    private String description;

}
