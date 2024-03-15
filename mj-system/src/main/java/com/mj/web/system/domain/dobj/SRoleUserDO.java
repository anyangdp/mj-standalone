package com.mj.web.system.domain.dobj;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 角色用户
 */
@TableName("s_role_user")
@Accessors(chain = true)
@Data
public class SRoleUserDO {

    // 角色id
    private String roleId;
    // 用户id
    private String userId;

}
