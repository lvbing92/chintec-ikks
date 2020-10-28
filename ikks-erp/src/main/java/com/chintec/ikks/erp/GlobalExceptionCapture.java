package com.chintec.ikks.erp;

import com.chintec.ikks.common.exception.NoLoginException;
import com.chintec.ikks.common.exception.ParamsException;
import com.chintec.ikks.common.util.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
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

    @ExceptionHandler(NoLoginException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResultResponse noLoginException(NoLoginException e) {
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
        String message = "网络错误，请重试！！！";
        if (e != null) {
            log.error(e.getMessage());
        }
        return ResultResponse.failResponse(message);
    }
}
