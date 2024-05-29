package com.mj.web.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mj.common.exception.exception.BizException;
import com.mj.framework.service.AbstractService;
import com.mj.web.system.domain.dobj.SUserDO;
import com.mj.web.system.domain.dto.UserDTO;
import com.mj.web.system.mapper.UserMapper;
import com.mj.web.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class UserServiceImpl extends AbstractService<UserMapper, SUserDO, UserDTO> implements UserService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDTO insert(UserDTO entity) {
        entity.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));
        if (count(new LambdaQueryWrapper<SUserDO>().eq(SUserDO::getUsername, entity.getUsername())) > 0) {
            throw new BizException("账号已存在");
        }
        return super.insert(entity);
    }
}
