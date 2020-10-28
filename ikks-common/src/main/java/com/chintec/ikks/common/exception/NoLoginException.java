package com.chintec.ikks.common.exception;

/**
 * @author rubin·lv
 * @version 1.0
 * @date 2020/10/27 16:39
 */
public class NoLoginException extends RuntimeException {

    private Integer code = 5001;
    private String msg = "Unauthorized!";

    public NoLoginException() {
        super("Unauthorized");
    }

    public NoLoginException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public NoLoginException(Integer code) {
        super("Unauthorized");
        this.code = code;
    }

    public NoLoginException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
