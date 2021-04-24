package com.igeek.bams.exception;

/**
 * @version 1.0
 * @Description ATM业务异常基类
 * @Author chenmin
 * @Date 2020/11/9 9:31
 */
public class ATMException extends Exception {

    public ATMException() {
        super();
    }

    public ATMException(String message) {
        super(message);
    }
}
