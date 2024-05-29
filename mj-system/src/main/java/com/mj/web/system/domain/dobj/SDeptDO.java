package com.mj.web.system.domain.dobj;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mj.framework.handler.AbstractDO;
import lombok.Data;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;

/**
 * 部门
 */
@TableName("s_dept")
@Accessors(chain = true)
@Data
public class SDeptDO extends AbstractDO<String> {

    // 部门名
    private String name;
    // 上级部门
    private String parentId;

}
