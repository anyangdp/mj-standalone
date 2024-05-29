package com.mj.web.system.domain.dobj;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mj.framework.handler.AbstractDO;
import lombok.Data;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;

/**
 * 职位
 */
@TableName("s_position")
@Accessors(chain = true)
@Data
public class SPositionDO extends AbstractDO<String> {

    // 职位
    private String name;

}
