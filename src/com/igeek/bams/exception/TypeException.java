package com.igeek.bams.exception;

/**
 * @version 1.0
 * @Description 是否匹配对应操作的账户类型
 * @Author chenmin
 * @Date 2020/11/9 9:39
 */
public class TypeException extends ATMException{

    public TypeException() {
        super();
    }

    public TypeException(String message) {
        super(message);
    }
}
