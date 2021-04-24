package com.igeek.bams.entity;

import com.igeek.bams.exception.BalanceNotEnoughException;

/**
 * @version 1.0
 * @Description 信用账户
 * @Author chenmin
 * @Date 2020/10/29 9:29
 */
public class CreditAccount extends Account {

    //透支额度
    private double ceiling;

    public double getCeiling() {
        return ceiling;
    }

    //设置透支额度
    public void setCeiling(double ceiling) {
        this.ceiling = ceiling;
    }

    public CreditAccount() {
    }

    public CreditAccount(Long id, String password, String name, String personId, String email, double balance, double ceiling) {
        super(id, password, name, personId, email, balance);
        this.ceiling = ceiling;
    }

    //重写Account中的取款方法
    @Override
    public Account withdraw(double money) throws BalanceNotEnoughException {
        if(this.getBalance()+this.ceiling>=money){
            if(this.getBalance()>=money){  //余额充足，没有用到透支额度
                this.setBalance(this.getBalance() - money);
            }else{  //余额不充足，用到透支额度
                this.setCeiling(this.getCeiling() - (money - this.getBalance()));
                this.setBalance(0.0);
            }
        }else{
            throw new BalanceNotEnoughException("您的余额不足！");
        }
        return this;
    }

    @Override
    public String toString() {
        return "CreditAccount{" +
                "ceiling=" + ceiling +
                "} " + super.toString();
    }
}
