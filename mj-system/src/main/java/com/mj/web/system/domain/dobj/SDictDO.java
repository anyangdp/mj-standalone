package com.mj.web.system.domain.dobj;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mj.framework.handler.AbstractDO;
import lombok.Data;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;

/**
 * 系统字典
 */
@TableName("s_dict")
@Accessors(chain = true)
@Data
public class SDictDO extends AbstractDO<String> {

    // 名称
    private String name;
    // 字典明细
    private String metadata;

}
