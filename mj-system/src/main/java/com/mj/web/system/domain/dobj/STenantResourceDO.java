package com.mj.web.system.domain.dobj;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 租户资产（数据权限）
 */
@TableName("s_tenant_resource")
@Accessors(chain = true)
@Data
public class STenantResourceDO {

    // 租户id
    private String tenantId;
    // 资产类型
    private String type;
    // 资产id
    private String resourceId;

}
