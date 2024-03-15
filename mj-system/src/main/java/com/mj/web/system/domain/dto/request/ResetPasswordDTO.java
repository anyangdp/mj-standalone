package com.mj.web.system.domain.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Pattern;

/**
 * @Author anyang
 * @CreateTime 2023/2/6
 * @Des
 */
@Data
@ApiModel(value = "ResetPasswordDTO")
public class ResetPasswordDTO {
    @ApiModelProperty(value = "用户id")
    private String id;
    @Pattern(regexp = "^(?:(?=.*[A-Z])|(?=.*[a-z])|(?=.*[@$!%*?&])|(?=.*\\d)).{8,}$", message = "新密码至少8位且包含大小写字母，数字，特殊符号中的三种以上")
    @ApiModelProperty(value = "新密码")
    private String newPassword;
}
