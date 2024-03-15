package com.mj.security.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.InternalAuthenticationServiceException;

/**
 * 管理员账号不存在
 */
@Setter
@Getter
public class UserPasswordErrorException extends InternalAuthenticationServiceException {

    private static final long serialVersionUID = 3228262779033056857L;
    private String msg;

    public UserPasswordErrorException(String msg){
        super(msg);
        this.msg = msg;
    }

    public UserPasswordErrorException(String msg, Throwable t) {
        super(msg, t);
        this.msg = msg;
    }
}
