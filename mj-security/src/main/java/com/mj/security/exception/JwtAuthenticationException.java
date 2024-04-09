package com.mj.security.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.InternalAuthenticationServiceException;

/**
 * 管理员账号不存在
 */
@Setter
@Getter
public class JwtAuthenticationException extends InternalAuthenticationServiceException {

    private String msg;

    public JwtAuthenticationException(String msg){
        super(msg);
        this.msg = msg;
    }

    public JwtAuthenticationException(String msg, Throwable t) {
        super(msg, t);
        this.msg = msg;
    }
}
