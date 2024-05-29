package com.mj.security;

import com.mj.framework.handler.AbstractDTO;
import com.mj.web.system.domain.dobj.STenantResourceDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户表
 */
@ApiModel(value = "用户表")
@Accessors(chain = true)
@Data
public class SUserBO extends AbstractDTO<String> {

    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "昵称")
    private String nickname;
    private Boolean active = null;
    private boolean deleted = false;
    private boolean saveLogin = false;
    private String accountType;
    private List<STenantResourceDO> resources;
    private List<CustomGrantedAuthority> authorities = new ArrayList<>();
}
