package com.mj.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mj.security.exception.UserPasswordErrorException;
import com.mj.web.system.domain.dobj.STenantResourceDO;
import com.mj.web.system.domain.dobj.SUserDO;
import com.mj.web.system.domain.dobj.SUserMenuPermissionDO;
import com.mj.web.system.service.STenantResourceService;
import com.mj.web.system.service.UserMenuPermissionService;
import com.mj.web.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @Author anyang
 * @CreateTime 2022/12/27
 * @Des
 */
@Slf4j
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMenuPermissionService userMenuPermissionService;

    @Autowired
    private STenantResourceService sTenantResourceService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SUserDO SUserDO = userService.getOne(new LambdaQueryWrapper<SUserDO>().eq(SUserDO::getUsername, username)
                .eq(SUserDO::getActive, true).eq(SUserDO::isDeleted, false));
        if (null == SUserDO) {
            log.info("用户名或密码错误");
            throw new UserPasswordErrorException("用户名或密码错误");
        }
        if (SUserDO.getUsername().equals("admin")) {
            List<CustomGrantedAuthority> authorities = new ArrayList<>();
            Set<String> role = new HashSet<>();
            role.add("ROLE_" + SUserDO.getUsername());
            return createSecurityUser(SUserDO, authorities, role, Collections.emptyList());
        } else {
            return createSecurityUser(SUserDO);
        }
    }

    private UserDetails createSecurityUser(SUserDO one) {
        return createSecurityUser(one, loadAuthorities(one.getId()), loadRole(one.getId()), loadResource(one.getId()));
    }

    private UserDetails createSecurityUser(SUserDO user, List<CustomGrantedAuthority> authorities, Set<String> role, List<STenantResourceDO> resources) {
        return new SecurityUserDetails(user, authorities, role, resources);
    }

    private List<CustomGrantedAuthority> loadAuthorities(String id) {
        List<CustomGrantedAuthority> authorities = new ArrayList<>();
        List<SUserMenuPermissionDO> list = userMenuPermissionService.list(new LambdaQueryWrapper<SUserMenuPermissionDO>().eq(SUserMenuPermissionDO::getUserId, id));
        if (!CollectionUtils.isEmpty(list)) {
            list.stream().map(SUserMenuPermissionDO::getPermissionId).forEach(item -> authorities.add(new CustomGrantedAuthority(item)));
        }
        return authorities;
    }
    private List<STenantResourceDO> loadResource(String id) {
        List<STenantResourceDO> sTenantResourceDOS = sTenantResourceService.selectListByUserId(id);
        if (CollectionUtils.isEmpty(sTenantResourceDOS)) {
            sTenantResourceDOS.add(new STenantResourceDO().setResourceId("none").setTenantId("none").setType("none"));
        }
        return sTenantResourceDOS;
    }

    private Set<String> loadRole(String id) {
        return new HashSet<>(16);
    }

    private void loadPosition() {

    }
}
