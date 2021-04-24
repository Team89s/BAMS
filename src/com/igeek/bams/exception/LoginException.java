package com.igeek.bams.exception;

/**
 * @version 1.0
 * @Description 登录异常
 * @Author chenmin
 * @Date 2020/11/9 9:32
 */
public class LoginException extends ATMException {

    public LoginException() {
        super();
    }

    public LoginException(String message) {
        super(message);
    }
}
