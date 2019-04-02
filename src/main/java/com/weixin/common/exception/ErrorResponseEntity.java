package com.weixin.common.exception;
import lombok.Data;

/**
 * @author yan.yuchen111@ztesoft.com
 * @create 2019/3/13
 * @since 1.0.0
 */

@Data
public class ErrorResponseEntity {
    private int code;
    private String message;

    public ErrorResponseEntity(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
