package com.mj.common.exception.exception;

import lombok.Data;

/**
 *  数据异常
 */
@Data
public class BizException extends RuntimeException {

    private String msg;
    private String code;

    public BizException(String msg){
        super(msg);
        this.msg = msg;
    }

    public BizException(String msg, String code){
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public BizException(String msg, String code, Throwable t) {
        super(msg, t);
        this.code = code;
        this.msg = msg;
    }
}
