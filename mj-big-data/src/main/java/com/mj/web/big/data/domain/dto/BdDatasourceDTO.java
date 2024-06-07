package com.mj.web.big.data.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mj.framework.handler.AbstractDTO;
import lombok.Data;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 数据源
 */
@ApiModel(value = "数据源")
@Accessors(chain = true)
@Data
public class BdDatasourceDTO extends AbstractDTO<String> {

    @ApiModelProperty(value = "数据源类型")
    private String type;
    @ApiModelProperty(value = "驱动连接串")
    private String driver;
    @ApiModelProperty(value = "连接url")
    private String url;
    @ApiModelProperty(value = "主机")
    private String host;
    @ApiModelProperty(value = "端口")
    private String port;
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "数据源说明")
    private String description;

}
