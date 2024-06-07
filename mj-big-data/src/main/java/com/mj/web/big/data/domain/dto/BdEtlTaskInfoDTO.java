package com.mj.web.big.data.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mj.framework.handler.AbstractDTO;
import lombok.Data;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * etl任务
 */
@ApiModel(value = "etl任务")
@Accessors(chain = true)
@Data
public class BdEtlTaskInfoDTO extends AbstractDTO<String> {

    @ApiModelProperty(value = "任务类型")
    private String type;
    @ApiModelProperty(value = "配置")
    private String configuration;
    @ApiModelProperty(value = "数据源说明")
    private String description;

}
