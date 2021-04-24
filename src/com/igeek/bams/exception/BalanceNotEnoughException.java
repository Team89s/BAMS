package com.igeek.bams.exception;

/**
 * @version 1.0
 * @Description 余额不足异常
 * @Author chenmin
 * @Date 2020/11/9 9:32
 */
public class BalanceNotEnoughException extends ATMException {

    public BalanceNotEnoughException() {
        super();
    }

    public BalanceNotEnoughException(String message) {
        super(message);
    }
}
