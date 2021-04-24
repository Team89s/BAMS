package com.igeek.bams.entity;

import com.igeek.bams.exception.BalanceNotEnoughException;

import java.io.Serializable;

/**
 * @version 1.0
 * @Description 账户类 Account
 * @Author chenmin
 * @Date 2020/10/28 10:26
 */
public abstract class Account implements Serializable {
    //账户号码
    private Long id;
    //账户密码
    private String password;
    //真实姓名
    private String name;
    //身份证号码
    private String personId;
    //客户的电子邮箱
    private String email;
    //账户余额
    private double balance;

    //无参构造方法
    public Account() {
    }

    //有参构造方法
    public Account(Long id, String password, String name, String personId, String email, double balance) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.personId = personId;
        this.email = email;
        this.balance = balance;
    }

    //公开的getter和setter方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * 存款方法
     * @param money 存的钱
     * @return 当前正在使用的银行卡
     */
    public final Account deposit(double money){
        balance+=money;
        return this;
    }

    /**
     * 取款方法
     * @param money 取的钱
     * @return  当前正在使用的银行卡
     */
    public abstract Account withdraw(double money) throws BalanceNotEnoughException;

    /**
     * 为了测试查看方便，提供一个显示账户信息的方法
     */
    public void show(){
        System.out.println("当前账户id:"+id);
        System.out.println("当前账户姓名:"+name);
        System.out.println("当前账户身份证号:"+personId);
        System.out.println("当前账户邮箱:"+email);
        System.out.println("当前账户余额:"+balance);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", personId='" + personId + '\'' +
                ", email='" + email + '\'' +
                ", balance=" + balance +
                '}';
    }
}
