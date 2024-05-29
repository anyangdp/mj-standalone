package com.mj.web.system.domain.dobj;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mj.framework.handler.AbstractDO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户表
 */
@TableName("s_user")
@Accessors(chain = true)
@Data
public class SUserDO extends AbstractDO<String> {

    // 用户名
    private String username;
    // 密码
    private String password;
    // 昵称
    private String nickname;
    // 头像
    private String avatar;
}
