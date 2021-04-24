package com.igeek.bams.entity;

import com.igeek.bams.exception.BalanceNotEnoughException;
import com.igeek.bams.exception.LoanException;

/**
 * 贷款的接口
 */
public interface Loanable {

    /**
     * 贷款
     * @param money  贷款金额
     * @return 银行卡本身
     */
    Account requestLoan(double money) throws LoanException;

    /**
     * 还贷
     * @param money  还贷款金额
     * @return 银行卡本身
     */
    Account payLoan(double money) throws LoanException, BalanceNotEnoughException;

}
