package com.mj.security.handler;


import com.mj.framework.constants.ErrorCodeEnum;
import com.mj.framework.handler.ErrorDTO;
import com.mj.framework.handler.GenericResponse;
import com.mj.framework.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@Slf4j
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        ResponseUtil.out(response, new GenericResponse(false,
                new ErrorDTO(ErrorCodeEnum.PERMISSION_NOT_ACCESS.getCode(), ErrorCodeEnum.PERMISSION_NOT_ACCESS.getMessage()), null));
    }

}
