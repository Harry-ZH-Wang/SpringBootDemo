package com.wzh.config.utils;

/**
 * @author wzh
 * @create 2018-05-19 23:21
 * @desc ${自定义业务异常}
 **/
public class BusinessException extends Exception{

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

}
