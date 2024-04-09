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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author anyang
 * @CreateTime 2022/12/27
 * @Des
 */
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
        UserDO one = userService.getOne(new LambdaQueryWrapper<UserDO>().eq(UserDO::getUsername, username)
                .eq(UserDO::getActive, true).eq(UserDO::isDeleted, false));
        if (null == one) {
            throw new UserPasswordErrorException("用户名或密码错误");
        }
        UserBO dump = ValueUtil.dump(one, UserBO.class);
        List<CustomGrantedAuthority> authorities = new ArrayList<>();
        if (one.getUsername().equals("admin")) {
            dump.setAccountType("admin");
            authorities.add(new CustomGrantedAuthority("admin"));
        } else {
            List<UserMenuPermissionDO> list = userMenuPermissionService.list(new LambdaQueryWrapper<UserMenuPermissionDO>().eq(UserMenuPermissionDO::getUserId, one.getId()));
            if (!CollectionUtils.isEmpty(list)) {
                list.stream().map(m -> m.getPermissionId()).forEach(item -> authorities.add(new CustomGrantedAuthority(item)));
            }
            List<STenantResourceDO> sTenantResourceDOS = sTenantResourceService.selectListByUserId(dump.getId());
            if (CollectionUtils.isEmpty(sTenantResourceDOS)) {
                sTenantResourceDOS.add(new STenantResourceDO().setResourceId("none").setTenantId("none").setType("none"));
            }
            dump.setAccountType("tenant").setResources(sTenantResourceDOS);
        }
        dump.setAuthorities(authorities);
        return new SecurityUserDetails(dump);
    }
}
