package com.mj.web.big.data.domain.dobj;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mj.framework.handler.AbstractDO;
import lombok.Data;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;

/**
 * etl任务
 */
@TableName("bd_etl_task_info")
@Accessors(chain = true)
@Data
public class BdEtlTaskInfoDO extends AbstractDO<String> {

    // 任务类型
    private String type;
    // 配置
    private String configuration;
    // 数据源说明
    private String description;

}
