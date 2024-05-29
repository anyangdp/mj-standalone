package com.mj.web.system.domain.dobj;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户菜单权限关联表
 */
@TableName("s_user_menu_permission")
@Accessors(chain = true)
@Data
public class SUserMenuPermissionDO {

    // 用户id
    private String userId;
    // 权限id
    private String permissionId;

}
