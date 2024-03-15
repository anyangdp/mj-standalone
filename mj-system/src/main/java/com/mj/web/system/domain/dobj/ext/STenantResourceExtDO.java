package com.mj.web.system.domain.dobj.ext;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mj.framework.handler.AbstractDTO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 租户资产（数据权限）
 */
@TableName("s_tenant_resource")
@Accessors(chain = true)
@Data
public class STenantResourceExtDO extends AbstractDTO<String> {

    // 资产类型
    private String name;
    // 资产类型
    private String type;
    // 资产id
    private String resourceId;
    // 资产名
    private String resourceName;

}
