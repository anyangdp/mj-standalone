package com.mj.framework.handler;

import com.mj.framework.constants.ErrorCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "错误信息")
public class ErrorDTO {
    @ApiModelProperty(value = "错误码")
	private Integer code;
    @ApiModelProperty(value = "错误描述")
	private String message;
    @ApiModelProperty(value = "英文描述")
    private String enMessage;

    public ErrorDTO(String message) {
        this.message = message;
    }

    public ErrorDTO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorDTO(ErrorCodeEnum error){
        this.code = error.getCode();
        this.message = error.getMessage();
        this.enMessage = error.getEnMessage();
    }
}
