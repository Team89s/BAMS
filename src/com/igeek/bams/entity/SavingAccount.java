package com.igeek.bams.entity;

import com.igeek.bams.exception.BalanceNotEnoughException;

/**
 * @version 1.0
 * @Description 储蓄账户
 * @Author chenmin
 * @Date 2020/10/29 9:28
 */
public class SavingAccount extends Account {

    public SavingAccount() {
    }

    public SavingAccount(Long id, String password, String name, String personId, String email, double balance) {
        super(id, password, name, personId, email, balance);
    }

    @Override
    public Account withdraw(double money) throws BalanceNotEnoughException {
        if(this.getBalance()>=money){
            this.setBalance(this.getBalance()-money);
        }else{
            throw new BalanceNotEnoughException("您当前余额不足！");
        }
        return this;
    }


}
