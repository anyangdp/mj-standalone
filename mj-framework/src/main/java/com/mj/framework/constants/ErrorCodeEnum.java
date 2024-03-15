package com.mj.framework.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 权限错误：1000~1999
 * 业务相关：
     * 用户错误 10000~19999
     * 参数错误 20000~29999
     * 接口异常 30000~39999
 * @Author anyang
 * @CreateTime 2019/10/31
 * @Des 错误code枚举
 */
@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {
    /*权限错误 1000~1999*/
    TOKEN_ERROR(1000, "身份令牌有误", "token invalid"),
    LOGIN_ERROR(1000, "用户名或密码错误", "user or password invalid"),
    ADMIN_NOT_EXIST_ERROR(1000, "用户不存在", "admin user not exist"),
    ACCOUNT_DISABLE(1000, "账户已被禁用", "account is disable"),
    LOGIN_OTHER_ERROR(1000, "登录失败，内部错误", "login fail, inner error"),
    LOGIN_COUNT_OUT_LIMIT(1000, "登录错误次数超过限制", "login out of limit"),
    PLATFORM_ERROR(1000, "该平台或系统不允许接入积分系统", "platform or project error"),
    PERMISSION_NOT_ACCESS(1001, "权限不足，禁止访问", "insufficient permissions to access"),
    LOGIN_TOKEN_INVALID(1002, "登录已失效，请重新登录", "login token invalid"),
    TOKEN_PARSE_ERROR(1003, "解析token错误", "token parse error"),
    NEED_LOGIN(1004, "您还未登录", "not login"),
    /*网关等等系统平台级别异常 2000~2999*/
    SERVICE_EXCEPTION(2000, "服务异常，稍候重试", "service exception"),
    /*用户错误 10000~19999*/
    USER_NOT_EXIST(10000, "用户不存在", "user not exist"),
    /*参数错误 20000~29999*/
    PARAM_IS_INVALID(20000, "参数无效", "param is invalid"),
    NUMBER_FORMAT_EXCEPTION(20001, "数字格式异常", "number format exception"),
    NOT_FOUND_EXCEPTION(20001, "接口地址错误", "Interface address error"),
    /*接口异常, 30000~39999*/
    PARAM_IS_BLANK(30000, "接口异常", "interface exception"),
    /**
     * HttpStatus
     * {@link (org.springframework.http.HttpStatus)}
     */
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED.value(), "请求方法不允许", HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase()),
    DATA_NOT_EXIST(30002, "数据不存在","This data does not exist in the database"),
    BIZ_OPERATE_ERROR(30003, "操作失败", "operate error");// 通用的业务处理code，代码中写具体的错误message
    ;
    private Integer code;
    private String message;
    private String enMessage;

}
