package com.igeek.bams.test;

import com.igeek.bams.entity.Account;
import com.igeek.bams.entity.CreditAccount;
import com.igeek.bams.entity.SavingAccount;
import com.igeek.bams.exception.*;
import com.igeek.bams.service.Bank;

/**
 * @version 1.0
 * @Description TODO
 * @Author chenmin
 * @Date 2020/10/28 10:26
 */
public class Test {

    public static void main(String[] args) {

        /*Account acc1 = new Account(1L,"123","张三","1234567890","123@qq.com",0.0);
        acc1.deposit(1000);
        acc1.show();
        System.out.println("=================");
        acc1.withdraw(200);
        acc1.show();*/

        System.out.println("-------------------");

        //储蓄账户
        /*SavingAccount acc2 = new SavingAccount(2L,"123","李四","1234567890","123@qq.com",0.0);
        acc2.deposit(3000);
        System.out.println(acc2);
        System.out.println("=================");
        acc2.withdraw(300);
        System.out.println(acc2);*/

        System.out.println("-------------------");
        //信用账户
        /*CreditAccount acc3 = new CreditAccount(3L,"123","王五","1234567890","123@qq.com",0.0,500.0);
        acc3.deposit(1000);
        System.out.println(acc3);  //1000  500
        acc3.withdraw(500);
        System.out.println(acc3); //500  500
        acc3.withdraw(800);
        System.out.println(acc3); //0 200
        acc3.withdraw(500);
        System.out.println(acc3); //余额不足  0  200*/


        System.out.println("-------加上Bank之后------------");
        Bank bank = Bank.getInstance();
        Bank bank1 = Bank.getInstance();

        //注册
        System.out.println("---------注册----------");
        Account account1 = null;
        try {
            account1 = bank.register("123","123","张三",
                    "1234567","123@qq.com",0);
        } catch (RegisterException e) {
            System.out.println(e.getMessage());
        }
        Account account3 = null;
        try {
            account3 = bank.register("123","123","李四",
                    "1111111","123@qq.com",1);
        } catch (RegisterException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(account1);
        System.out.println(account3);

        //登录
        /*System.out.println("---------登录----------");
        Account account2 = null;
        try {
            account2 = bank1.login(1L, "123");
        } catch (LoginException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("account2 = "+account2);

        //存款
        System.out.println("---------存款----------");
        try {
            account1 = bank.deposit(1L,1000);
        } catch (LoginException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(account1);

        //取款
        System.out.println("---------取款----------");
        try {
            bank.deposit(2L,1000);
        } catch (LoginException e) {
            System.out.println(e.getMessage());
        }
        try {
            bank.withdraw(2L,"123",1600);
        } catch (LoginException | BalanceNotEnoughException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(account3);

        //设置透支额度
        System.out.println("---------设置透支额度----------");
        try {
            bank.updateCeiling(2L,"123",1000);
        } catch (TypeException | LoginException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(account3);

        //转账
        System.out.println("---------转账----------");
        boolean flag = false;
        try {
            flag = bank.transfer(2L,"123",1L,2100);
        } catch (LoginException | TransferException | BalanceNotEnoughException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(flag?"成功":"失败");
        System.out.println("account1:"+account1);
        System.out.println("account3:"+account3);

        //总额
        System.out.println(bank.totalBalance());

        //总透支额度
        System.out.println(bank.totalCeiling());*/

        //测试贷款
        //注册
        Account account4 = null;
        try {
            account4 = bank.register("123","123","张三",
                    "1234567","123@qq.com",3);
        } catch (RegisterException e) {
            System.out.println(e.getMessage());
        }
        Account account5 = null;
        try {
            account5 = bank.register("123","123","王二",
                    "2222222","123@qq.com",2);
        } catch (RegisterException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(account4);
        System.out.println(account5);

        try {
            bank.deposit(862150212020110001L,5000);
            bank.deposit(862150212020110002L,8000);
            bank.deposit(862150212020110003L,3000);
            bank.deposit(862150212020110004L,6000);
        } catch (LoginException e) {
            e.printStackTrace();
        }

        //总资产排行
        bank.rank();


        /*System.out.println("--------贷款-----------");
        //贷款
        try {
            bank.requestLoan(1L,10000.0);
        } catch (TypeException | LoanException e) {
            System.out.println(e.getMessage());
        }
        try {
            bank.requestLoan(4L,10000.0);
        } catch (TypeException | LoanException e) {
            System.out.println(e.getMessage());
        }
        try {
            bank.requestLoan(5L,20000.0);
        } catch (TypeException | LoanException e) {
            e.printStackTrace();
        }
        System.out.println(account1);
        System.out.println(account4);
        System.out.println(account5);


        System.out.println("--------还贷-----------");
        //还贷
        try {
            bank.updateCeiling(4L,"123",20000.0);
        } catch (TypeException | LoginException e) {
            System.out.println(e.getMessage());
        }
        try {
            bank.withdraw(4L,"123",1000);
        } catch (LoginException |BalanceNotEnoughException e) {
            System.out.println(e.getMessage());
        }
        try {
            bank.payLoan(4L,10000.0);
        } catch (TypeException | LoanException | BalanceNotEnoughException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(account4);

        //总贷款
        System.out.println(bank.totalLoan());*/
    }

}
