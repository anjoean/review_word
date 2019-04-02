package com.weixin.common.exception;


/**
 * @author yan.yuchen111@ztesoft.com
 * @create 2019/3/13
 * @since 1.0.0
 */

public class CustomException extends RuntimeException {
    private static final long serialVersionUID = 4564124491192825748L;

    private int code;

    public CustomException() {
        super();
    }

    public CustomException(int code, String message) {
        super(message);
        this.setCode(code);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
