package com.igeek.bams.exception;

/**
 * @version 1.0
 * @Description 贷款异常
 * @Author chenmin
 * @Date 2020/11/9 9:33
 */
public class LoanException extends ATMException {

    public LoanException() {
        super();
    }

    public LoanException(String message) {
        super(message);
    }
}
