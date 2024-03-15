package com.mj.security;

import com.mj.web.system.domain.dobj.STenantResourceDO;
import com.mj.web.system.domain.dobj.UserDO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author anyang
 * @CreateTime 2022/12/27
 * @Des
 */
@Setter
@Getter
public class SecurityUserDetails extends UserDO implements UserDetails {
    private boolean saveLogin;

    private String accountType;// admin,tenant ，账号类型，用于数据权限及菜单权限过滤，admin账号忽略所有限制

    private List<STenantResourceDO> resources;// 租户资源

    private List<CustomGrantedAuthority> authorities; // 用户权限

    public SecurityUserDetails() {
        super();
    }

    public SecurityUserDetails(UserBO user) {
        if (user != null) {
            this.setId(user.getId());
            this.setUsername(user.getUsername());
            this.setNickname(user.getNickname());
            this.setPassword(user.getPassword());
            this.setActive(user.getActive());
            this.setDeleted(user.isDeleted());
            this.saveLogin = user.isSaveLogin();
            this.accountType = user.getAccountType();
            this.resources = user.getResources();
            this.authorities = CollectionUtils.isEmpty(user.getAuthorities()) ? new ArrayList<>() : user.getAuthorities();
        }
    }

    @Override
    public boolean isAccountNonExpired() {
        return getActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return getActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return getActive();
    }

    @Override
    public boolean isEnabled() {
        return getActive();
    }
}
