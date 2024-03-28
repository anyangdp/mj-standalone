package com.mj.activiti.controller.auth;

import com.alibaba.fastjson.JSON;
import com.mj.activiti.domain.LoginDTO;
import com.mj.common.util.SnowFlake;
import com.mj.framework.constants.RedisKeyConstant;
import com.mj.framework.handler.ControllerTemplate;
import com.mj.framework.handler.GenericResponse;
import com.mj.security.AdminTokenProperties;
import com.mj.security.SecurityUserDetails;
import com.mj.security.util.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Author anyang
 * @CreateTime 2022/12/9
 * @Des
 */
@Api(tags = "授权登录")
@RestController
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SnowFlake snowFlake;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private AdminTokenProperties adminTokenProperties;

    @ApiOperation(value = "登录", notes = "登录")
    @PostMapping("/doLogin")
    public GenericResponse<String> doLogin(@RequestBody LoginDTO loginDTO) throws Exception {
        return ControllerTemplate.call(response -> {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);

            if (Objects.isNull(authenticate)) {
                //用户名密码错误
                throw new RuntimeException("用户名密码错误");
            }
            SecurityUserDetails userDTO = (SecurityUserDetails) authenticate.getPrincipal();
            String token = snowFlake.nextIdStr();
            // //将用户存入上下文中
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            stringRedisTemplate.opsForValue().set(RedisKeyConstant.TOKEN_PREFIX + token, JSON.toJSONString(userDTO), adminTokenProperties.getTokenExpireTime(), TimeUnit.MINUTES);
            response.setData(token).setResult(true);
        });
    }

    @ApiOperation(value = "第三方平台获取token", notes = "第三方平台获取token")
    @PostMapping("/do/third/auth")
    public GenericResponse<String> auth(@RequestBody LoginDTO loginDTO) throws Exception {
        return ControllerTemplate.call(response -> {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);
            if (Objects.isNull(authenticate)) {
                //用户名密码错误
                throw new RuntimeException("用户名密码错误");
            }
            SecurityUserDetails userDTO = (SecurityUserDetails) authenticate.getPrincipal();
            String token = snowFlake.nextIdStr();
            // //将用户存入上下文中
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            stringRedisTemplate.opsForValue().set(RedisKeyConstant.TOKEN_PREFIX + token, JSON.toJSONString(userDTO), adminTokenProperties.getTokenExpireTime(), TimeUnit.MINUTES);
            response.setData(token).setResult(true);
        });
    }


    @ApiOperation(value = "获取当前用户信息", notes = "获取当前用户信息")
    @GetMapping("/currentUser")
    public GenericResponse<SecurityUserDetails> userInfo() throws Exception {
        return ControllerTemplate.call(response -> {
            response.setData(SecurityUtil.securityUserDetails());
            response.setResult(true);
        });
    }

    @ApiOperation(value = "退出", notes = "退出")
    @GetMapping("/doLogout")
    public GenericResponse<Void> doLogout() throws Exception {
        return ControllerTemplate.call(response -> {
            SecurityContextHolder.clearContext();
            response.setResult(true);
        });
    }
}
