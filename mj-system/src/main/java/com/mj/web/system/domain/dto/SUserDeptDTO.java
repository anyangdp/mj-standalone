package com.mj.web.system.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mj.framework.handler.AbstractDTO;
import lombok.Data;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户部门
 */
@ApiModel(value = "用户部门")
@Accessors(chain = true)
@Data
public class SUserDeptDTO extends AbstractDTO<String> {

    @ApiModelProperty(value = "用户id")
    private String userId;
    @ApiModelProperty(value = "部门id")
    private String deptId;

}
