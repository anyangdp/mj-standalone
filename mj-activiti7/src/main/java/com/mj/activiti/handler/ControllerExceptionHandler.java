package com.mj.activiti.handler;

import com.mj.common.exception.exception.BizException;
import com.mj.framework.constants.ErrorCodeEnum;
import com.mj.framework.handler.ErrorDTO;
import com.mj.framework.handler.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author anyang
 * @CreateTime 2020/1/10
 * @Des
 */
@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {
    // 捕捉数字转换格式异常
    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GenericResponse numberFormatException(HttpServletRequest request, Throwable ex) {
        log.error("请求地址：{}，NumberFormatException：{}", request.getRequestURL(), ex);
        ex.printStackTrace();
        return new GenericResponse<String>(false, new ErrorDTO(ErrorCodeEnum.NUMBER_FORMAT_EXCEPTION.getCode(),
                ErrorCodeEnum.NUMBER_FORMAT_EXCEPTION.getMessage(), ex.getMessage()), null);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public GenericResponse noHandlerFoundException(HttpServletRequest request, Throwable ex) {
        log.error("请求地址：{}，NoHandlerFoundException：{}", request.getRequestURL(), ex);
        ex.printStackTrace();
        return new GenericResponse<String>(false, new ErrorDTO(ErrorCodeEnum.NOT_FOUND_EXCEPTION.getCode(),
                ErrorCodeEnum.NOT_FOUND_EXCEPTION.getMessage(), ex.getMessage()), null);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public GenericResponse httMethodNotSupportException(HttpServletRequest request, Throwable ex) {
        log.error("请求地址：{}，HttpRequestMethodNotSupportedException：{}", request.getRequestURL(), ex);
        ex.printStackTrace();
        return new GenericResponse<String>(false, new ErrorDTO(ErrorCodeEnum.METHOD_NOT_ALLOWED.getCode(),
                ErrorCodeEnum.METHOD_NOT_ALLOWED.getMessage(), ex.getMessage()), null);
    }

    @ExceptionHandler(BizException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GenericResponse adminNotExistException(HttpServletRequest request, Throwable ex) {
        log.error("请求地址：{}，HttpRequestMethodNotSupportedException：{}", request.getRequestURL(), ex);
        ex.printStackTrace();
        return new GenericResponse<String>(false, new ErrorDTO(ErrorCodeEnum.BIZ_OPERATE_ERROR.getCode(),
                ErrorCodeEnum.BIZ_OPERATE_ERROR.getMessage(), ex.getMessage()), null);
    }

    @ExceptionHandler(UncategorizedSQLException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public GenericResponse sqlServerExceptionn(HttpServletRequest request, Throwable ex) {
        log.error("请求地址：{}，UncategorizedSQLException：{}", request.getRequestURL(), ex);
        ex.printStackTrace();
        return new GenericResponse<String>(false, new ErrorDTO(ErrorCodeEnum.PARAM_IS_INVALID.getCode(),
                ErrorCodeEnum.PARAM_IS_INVALID.getMessage(), ex.getMessage()), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public GenericResponse validExceptionHandler(MethodArgumentNotValidException e) {
        e.printStackTrace();
        FieldError fieldError = e.getBindingResult().getFieldError();
        assert fieldError != null;
        // 将错误的参数的详细信息封装到统一的返回实体
        return new GenericResponse<String>(false, new ErrorDTO(ErrorCodeEnum.PARAM_IS_INVALID.getCode(), fieldError.getDefaultMessage()), null);
    }

    @ExceptionHandler(BadSqlGrammarException.class)
    public GenericResponse badSqlExceptionHandler(BadSqlGrammarException e) {
        // 将错误的参数的详细信息封装到统一的返回实体
        e.printStackTrace();
        return new GenericResponse<String>(false, new ErrorDTO(ErrorCodeEnum.PARAM_IS_INVALID.getCode(), e.getMessage()), null);
    }

}
