package com.hahaha.musicshare.common.exception;

import com.hahaha.musicshare.common.result.Result;
import lombok.extern.slf4j.Slf4j;


import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class ServerExceptionHandler {
    /**
     * 处理自定义异常
     *
     * @param exception 异常
     * @return Result<String>
     */
    @ExceptionHandler(ServerException.class)
    public Result<String> handleException(ServerException exception) {
        return Result.error(exception.getCode(), exception.getMsg());

    }

    /**
     * 数据校验异常
     *
     * @param exception 异常
     * @return Result<String>
     */
//    TODO 存在类型接收转换问题，自行解决报错后，需要验证
    @ExceptionHandler(BindException.class)
    public Result<String> bindException(BindException exception) {
        exception.getProperty();
        return Result.error(exception.getMessage());
    }

    /**
     * 其他异常情况
     *
     * @param ex 异常
     * @return Result<String>
     */
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return Result.error(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
