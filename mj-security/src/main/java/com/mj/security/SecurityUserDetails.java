package com.mj.security;

import com.mj.web.system.domain.dobj.STenantResourceDO;
import com.mj.web.system.domain.dobj.SUserDO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @Author anyang
 * @CreateTime 2022/12/27
 * @Des
 */
@Setter
@Getter
public class SecurityUserDetails implements UserDetails {

    private boolean saveLogin;

    private String accountType;// admin,tenant ，账号类型，用于数据权限及菜单权限过滤，admin账号忽略所有限制

    private List<STenantResourceDO> resources;// 租户资源

    private List<CustomGrantedAuthority> authorities; // 用户权限

    private Set<String> role; // 用户角色

    private SUserDO user;

    public SecurityUserDetails() {
        super();
    }

    public SecurityUserDetails(SUserDO user, List<CustomGrantedAuthority> authorities, Set<String> role, List<STenantResourceDO> resources) {
        this.authorities = CollectionUtils.isEmpty(authorities) ? Collections.emptyList() : authorities;
        this.role = CollectionUtils.isEmpty(role) ? Collections.emptySet() : role;
        this.user = user;
        this.resources = resources;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
