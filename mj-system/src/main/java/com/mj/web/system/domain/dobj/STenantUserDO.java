package com.mj.web.system.domain.dobj;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 租户用户
 */
@TableName("s_tenant_user")
@Accessors(chain = true)
@Data
public class STenantUserDO {

    // 租户id
    private String tenantId;
    // 用户id
    private String userId;

}
