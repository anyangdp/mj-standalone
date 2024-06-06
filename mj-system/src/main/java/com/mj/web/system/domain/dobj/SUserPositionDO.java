package com.mj.web.system.domain.dobj;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 职位
 */
@TableName("s_user_position")
@Accessors(chain = true)
@Data
public class SUserPositionDO {

    // 用户id
    private String userId;
    // 用户id
    private String positionId;

}
