package com.igeek.bams.entity;

import com.igeek.bams.exception.BalanceNotEnoughException;
import com.igeek.bams.exception.LoanException;

/**
 * @version 1.0
 * @Description 可贷款的信用卡账户
 * @Author chenmin
 * @Date 2020/11/3 10:28
 */
public class LoanCreditAccount extends CreditAccount implements Loanable {

    private long loanAmount;

    public LoanCreditAccount() {
    }

    public LoanCreditAccount(Long id, String password, String name, String personId, String email, double balance, double ceiling, long loanAmount) {
        super(id, password, name, personId, email, balance, ceiling);
        this.loanAmount = loanAmount;
    }

    public long getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(long loanAmount) {
        this.loanAmount = loanAmount;
    }

    /**
     * 贷款
     * @param money  贷款金额
     * @return
     */
    @Override
    public Account requestLoan(double money) throws LoanException {
        if(money>0){
            //增加贷款金额
            this.loanAmount+=money;
            //将贷款金额增加至账户余额中
            this.setBalance(this.getBalance()+money);
        }else{
            throw new LoanException("贷款额不能为负数");
        }
        return this;
    }

    /**
     * 还款
     * @param money  还贷款金额
     * @return
     */
    @Override
    public Account payLoan(double money) throws LoanException, BalanceNotEnoughException {
        if(money>0){
            if(money<=(this.getBalance()+this.getCeiling())){
                if(money<=this.loanAmount){
                    if(this.getBalance()>=money){
                        //直接从账户中扣除
                        //减少账户余额
                        this.setBalance(this.getBalance()-money);
                        //减少贷款金额
                        this.loanAmount-=money;
                    }else{
                        //考虑账户及透支额度中一起扣除
                        //减少透支额度
                        this.setCeiling(this.getCeiling() - (money-this.getBalance()));
                        //减少账户余额
                        this.setBalance(0.0);
                        //减少贷款金额
                        this.loanAmount-=money;
                    }
                }else{
                    throw new LoanException("您还款金额超过了贷款金额，你亏了");
                }
            }else{
                throw new BalanceNotEnoughException("当前账户余额不足以支付贷款金额");
            }
        }else{
            throw new LoanException("还款金额不能为负数");
        }
        return this;
    }

    @Override
    public String toString() {
        return "LoanCreditAccount{" +
                "loanAmount=" + loanAmount +
                "} " + super.toString();
    }
}
