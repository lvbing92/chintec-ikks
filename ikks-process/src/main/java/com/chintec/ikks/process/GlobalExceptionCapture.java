package com.chintec.ikks.process;

import com.chintec.ikks.common.exception.ParamsException;
import com.chintec.ikks.common.util.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常
 *
 * @author rubin·lv
 * @version 1.0
 * @date 2020/9/25 17:37
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionCapture {
    /**
     * 自定义异常拦截
     *
     * @param e 异常信息
     * @return ResultResponse
     */
    @ExceptionHandler(ParamsException.class)
    public ResultResponse paramException(ParamsException e) {
        log.error(e.getMsg());
        return ResultResponse.failResponse(e.getCode(), e.getMsg());
    }

    /**
     * 系统异常拦截
     *
     * @param e 异常信息
     * @return ResultResponse
     */
    @ExceptionHandler(RuntimeException.class)
    public ResultResponse runtimeException(RuntimeException e) {
        log.error(e.getMessage());
        return ResultResponse.failResponse(e.getMessage());
    }
}
