package com.mj.security;

import com.mj.framework.util.ValueUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mj.security.exception.UserPasswordErrorException;
import com.mj.web.system.domain.dobj.STenantResourceDO;
import com.mj.web.system.domain.dobj.UserDO;
import com.mj.web.system.domain.dobj.UserMenuPermissionDO;
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
        UserDO userDO = userService.getOne(new LambdaQueryWrapper<UserDO>().eq(UserDO::getUsername, username)
                .eq(UserDO::getActive, true).eq(UserDO::isDeleted, false));
        if (null == userDO) {
            log.info("用户名或密码错误");
            throw new UserPasswordErrorException("用户名或密码错误");
        }
        if (userDO.getUsername().equals("admin")) {
            List<CustomGrantedAuthority> authorities = new ArrayList<>();
            Set<String> role = new HashSet<>();
            role.add("ROLE_" + userDO.getUsername());
            return createSecurityUser(userDO, authorities, role, Collections.emptyList());
        } else {
            return createSecurityUser(userDO);
        }
    }

    private UserDetails createSecurityUser(UserDO one) {
        return createSecurityUser(one, loadAuthorities(one.getId()), loadRole(one.getId()), loadResource(one.getId()));
    }

    private UserDetails createSecurityUser(UserDO user, List<CustomGrantedAuthority> authorities, Set<String> role, List<STenantResourceDO> resources) {
        return new SecurityUserDetails(user, authorities, role, resources);
    }

    private List<CustomGrantedAuthority> loadAuthorities(String id) {
        List<CustomGrantedAuthority> authorities = new ArrayList<>();
        List<UserMenuPermissionDO> list = userMenuPermissionService.list(new LambdaQueryWrapper<UserMenuPermissionDO>().eq(UserMenuPermissionDO::getUserId, id));
        if (!CollectionUtils.isEmpty(list)) {
            list.stream().map(UserMenuPermissionDO::getPermissionId).forEach(item -> authorities.add(new CustomGrantedAuthority(item)));
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
