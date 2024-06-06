package com.mj.web.system.domain.dobj;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户部门
 */
@TableName("s_user_dept")
@Accessors(chain = true)
@Data
public class SUserDeptDO {

    // 用户id
    private String userId;
    // 部门id
    private String deptId;

}
