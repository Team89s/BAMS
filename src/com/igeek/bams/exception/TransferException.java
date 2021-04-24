package com.igeek.bams.exception;

/**
 * @version 1.0
 * @Description 转账异常
 * @Author chenmin
 * @Date 2020/11/9 9:44
 */
public class TransferException extends ATMException{

    public TransferException() {
        super();
    }

    public TransferException(String message) {
        super(message);
    }
}
