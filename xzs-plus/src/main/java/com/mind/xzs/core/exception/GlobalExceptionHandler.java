package com.mind.xzs.core.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import com.mind.xzs.core.response.RestResponse;
import com.mind.xzs.core.response.SystemCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotLoginException.class)
    public RestResponse handleNotLoginException(NotLoginException e) {
        return RestResponse.fail(SystemCode.UNAUTHORIZED.getCode(),SystemCode.UNAUTHORIZED.getMessage());
    }

    @ExceptionHandler(NotPermissionException.class)
    public RestResponse handleNotPermissionException(NotPermissionException e) {
        return RestResponse.fail(SystemCode.AccessDenied.getCode(),SystemCode.AccessDenied.getMessage());
    }
}
