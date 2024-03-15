package com.mj.web.system.domain.dto.request;

import lombok.Data;

/**
 * @Author anyang
 * @CreateTime 2023/2/6
 * @Des
 */
@Data
public class ChangePasswordDTO {
    private String oldPassword;
    private String newPassword;
}
