package com.mj.web.system.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mj.framework.handler.AbstractDTO;
import lombok.Data;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 系统字典
 */
@ApiModel(value = "系统字典")
@Accessors(chain = true)
@Data
public class SDictDTO extends AbstractDTO<String> {

    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "字典明细")
    private String metadata;

}
