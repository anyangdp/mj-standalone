package com.mj.web.system.domain.dobj;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 */
@TableName("s_role_menu_permission")
@Accessors(chain = true)
@Data
public class SRoleMenuPermissionDO {

    // 
    private String roleId;
    // 
    private String permissionId;

}
