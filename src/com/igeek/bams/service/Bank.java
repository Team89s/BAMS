package com.igeek.bams.service;

import com.igeek.bams.dao.ArrayDao;
import com.igeek.bams.dao.FileDao;
import com.igeek.bams.dao.IDao;
import com.igeek.bams.dao.ListDao;
import com.igeek.bams.entity.*;
import com.igeek.bams.exception.*;
import com.igeek.bams.vo.RankVO;

import javax.swing.*;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @version 1.0
 * @Description 银行类  业务逻辑层
 * @Author chenmin
 * @Date 2020/10/29 11:22
 */
public class Bank {

    //一对一关联
    private IDao<Account, List<Account>> dao;  //null

    //单例模式
    private static Bank bank;

    private Bank(){
        //dao = new ArrayDao();
        //dao = new ListDao();
        dao = new FileDao();
    }

    public static Bank getInstance(){
        if(bank==null){
            bank = new Bank();
        }
        return bank;
    }

    /**
     * 用户开户
     * @param password    密码
     * @param repassword  确认密码
     * @param name        姓名
     * @param personID    身份证号码
     * @param email       邮箱
     * @param type        账户类型
     * @return   银行卡本类
     *
     * 项目需求规定账户类型：0 – 储蓄账户  1 – 信用账户 2 – 可贷款储蓄账户 3– 可贷款信用账户
     */
    public Account register(String password, String repassword, String name,
                            String personID, String email, int type) throws RegisterException {
        Account acc = null;
        if(password.equals(repassword)){
            switch (type){
                case 0:  //储蓄账户
                    Long id = dao.createId();
                    if(!id.equals(-1L)){
                        acc = new SavingAccount(id,password,name,personID,email,0.0);
                    }else{
                        throw new RegisterException("当前id未获取到，注册失败！");
                    }
                    break;
                case 1:  //信用账户
                    id = dao.createId();
                    if(!id.equals(-1L)){
                        acc = new CreditAccount(id,password,name,personID,email,0.0,500.0);
                    }else{
                        throw new RegisterException("当前id未获取到，注册失败！");
                    }
                    break;
                case 2:  //可贷款储蓄账户
                    id = dao.createId();
                    if(!id.equals(-1L)){
                        acc = new LoanSavingAccount(id,password,name,personID,email,0.0,0);
                    }else{
                        throw new RegisterException("当前id未获取到，注册失败！");
                    }
                    break;
                case 3:  //可贷款信用账户
                    id = dao.createId();
                    if(!id.equals(-1L)){
                        acc = new LoanCreditAccount(id,password,name,personID,email,0.0,500.0,0);
                    }else{
                        throw new RegisterException("当前id未获取到，注册失败！");
                    }
                    break;
                default:
                    throw new RegisterException("当前账户类型选择不匹配！");
            }
        }else{
            throw new RegisterException("密码与重复密码不相同！");
        }

        if(acc!=null){
            //插入
            dao.insert(acc);
            JOptionPane.showMessageDialog(null,"开户成功！");
        }else{
            throw new RegisterException("开户失败！");
        }
        return acc;
    }

    /**
     * 用户登录
     * @param id        账号
     * @param password  密码
     * @return  银行卡本类
     */
    public Account login(Long id, String password) throws LoginException {
        Account acc = null;
        if(id!=null && password!=null && !"".equals(password)){
            //查询
            acc = dao.selectOne(id,password);
            if(acc!=null){
                JOptionPane.showMessageDialog(null,"登录成功！");
            }else{
                throw new LoginException("登录失败,当前id与密码不匹配！");
            }
        }else{
            throw new LoginException("当前id或者密码为空！");
        }
        return acc;
    }

    /**
     * 用户存款
     * @param id    账号
     * @param money 存款金额
     * @return
     */
    public Account deposit(Long id, double money) throws LoginException {
        Account acc = null;
        if(id!=null){
            //查询
            acc = dao.selectOne(id);
            if(acc!=null){
                //存款
                acc.deposit(money);
                //更新账户
                dao.update(acc);
            }else{
                throw new LoginException("当前id不存在！");
            }
        }else{
            throw new LoginException("当前id为空！");
        }
        return acc;
    }

    /**
     * 用户取款
     * @param id       账号
     * @param password 密码
     * @param money    取款金额
     * @return   银行卡本类
     */
    public Account withdraw(Long id, String password, double money)
            throws LoginException, BalanceNotEnoughException {
        Account acc = null;
        if(id!=null && password!=null && !password.equals("")){
            acc = dao.selectOne(id, password);
            if(acc!=null){
                //取款
                acc.withdraw(money);
                //更新账户
                dao.update(acc);
            }else{
                throw new LoginException("当前id与密码不匹配！");
            }
        }else{
            throw new LoginException("当前id或者密码为空！");
        }
        return acc;
    }


    /**
     * 设置透支额度
     * @param id        账号
     * @param password  密码
     * @param money     透支额度金额
     * @return  银行卡类本身
     * 提示：这个方法需要验证账户是否是信用账户  instanceof
     */
    public Account updateCeiling(Long id, String password, double money) throws TypeException, LoginException {
        Account acc = null;
        if(id!=null && password!=null && !password.equals("")){
            acc = dao.selectOne(id, password);
            if(acc!=null){
                //修改透支额度
                if(acc instanceof CreditAccount){
                    ((CreditAccount)acc).setCeiling(money);
                    //更新账户
                    dao.update(acc);
                }else{
                    throw new TypeException("当前账户未开通修改透支额度功能");
                }
            }else{
                throw new LoginException("当前id与密码不匹配！");
            }
        }else{
            throw new LoginException("当前id或者密码为空！");
        }
        return acc;
    }

    /**
     * 转账功能
     * @param from          转出账户
     * @param passwordFrom  转出账号的密码
     * @param to            转入账户
     * @param money         转账的金额
     * @return  转账是否成功
     */
    public boolean transfer(Long from, String passwordFrom, Long to, double money)
            throws LoginException, TransferException, BalanceNotEnoughException {
        //转出账户
        Account fromAcc = null;
        //转入账户
        Account toAcc = null;
        if(from!=null && passwordFrom!=null && !passwordFrom.equals("") && to!=null){

            fromAcc = dao.selectOne(from,passwordFrom);
            if(fromAcc!=null){
                //转出账户原来的余额
                double oldBalance = fromAcc.getBalance();

                toAcc = dao.selectOne(to);
                if(toAcc!=null){

                    //转入账户与转出账户不一样，再发起转账
                    if(!fromAcc.equals(toAcc)){
                        //当前若是储蓄账户
                        if(fromAcc instanceof SavingAccount){
                            if(fromAcc.getBalance()<money){
                                throw new BalanceNotEnoughException("转出账户余额不足！");
                                //return false;
                            }
                        }

                        //当前若是信用账户
                        if(fromAcc instanceof CreditAccount){
                            if(fromAcc.getBalance() + ((CreditAccount)fromAcc).getCeiling()<money){
                                throw new BalanceNotEnoughException("转出账户余额不足！");
                                //return false;
                            }
                        }

                        //转账
                        //转出账户取钱
                        fromAcc.withdraw(money);

                        /*
                        //发起转账之后，转出账户的余额
                        double newBalance = fromAcc.getBalance();
                        //若此时转账前后，账户余额相等
                        if(oldBalance==newBalance){
                            //转账失败（转出账户余额不足）
                            return false;
                        }*/

                        //转入账户存钱
                        toAcc.deposit(money);
                        //更新账户
                        dao.update(fromAcc);
                        //更新账户
                        dao.update(toAcc);
                        //转账成功
                        JOptionPane.showMessageDialog(null,"转账成功！");
                        return true;
                    }else{
                        throw new TransferException("转入账户与转出账户相同！");
                        //return false;
                    }

                }else {
                    throw new LoginException("转入账户不存在！");
                    //return false;
                }
            }else{
                throw new LoginException("转出账户与密码不匹配！");
                //return false;
            }

        }else{
            throw new LoginException("转入账户或转出账户信息为空！");
            //return false;
        }

    }

    /**
     * 贷  款
     * @param id    账户
     * @param money 贷款金额
     * @return
     */
    public Account requestLoan(Long id , double money)
            throws TypeException, LoanException {
        Account acc = null;
        if(id!=null){
            acc = dao.selectOne(id);
            if(acc!=null){
                if(acc instanceof Loanable){
                    ((Loanable)acc).requestLoan(money);
                    //更新账户
                    dao.update(acc);
                }else{
                    throw new TypeException("您当前账户不是可贷款的账户类型！");
                }
            }
        }
        return acc;
    }

    /**
     * 还贷款
     * @param id     账户
     * @param money  还贷款金额
     * @return
     */
    public Account payLoan(Long id , double money)
            throws TypeException, LoanException, BalanceNotEnoughException {
        Account acc = null;
        if(id!=null){
            acc = dao.selectOne(id);
            if(acc!=null){
                if(acc instanceof Loanable){
                    ((Loanable)acc).payLoan(money);
                    //更新账户
                    dao.update(acc);
                }else{
                    throw new TypeException("您当前账户不是可贷款的账户类型！");
                }
            }
        }
        return acc;
    }

    /**
     * 统计银行所有账户余额总数
     * @return
     */
    public double totalBalance(){
        double sum = 0.0;
        for (Account acc : dao.selectAll()) {
            if(acc!=null){
                sum+=acc.getBalance();
            }
        }
        return sum;
    }

    /**
     * 统计所有信用账户透支额度总数
     * @return
     */
    public double totalCeiling(){
        double sum = 0.0;
        for (Account acc : dao.selectAll()) {
            if(acc!=null){
                if(acc instanceof CreditAccount){
                    sum+=((CreditAccount)acc).getCeiling();
                }
            }
        }
        return sum;
    }

    /**
     * 统计所有账户贷款的总额
     * @return
     */
    public double totalLoan(){
        double sum = 0.0;
        for (Account acc : dao.selectAll()) {
            if(acc!=null){
                if(acc instanceof LoanSavingAccount){
                    sum+=((LoanSavingAccount)acc).getLoanAmount();
                }
                if(acc instanceof LoanCreditAccount){
                    sum+=((LoanCreditAccount)acc).getLoanAmount();
                }
            }
        }
        return sum;
    }

    /**
     * 打印所有用户的总资产排名
     */
    public Set<RankVO> rank(){
        Set<RankVO> set = new TreeSet<>();
        for (Account acc : dao.selectAll()) {
            if(acc!=null){
                double totalBalance = this.getTotalBalanceByPersonId(acc.getPersonId());
                set.add(new RankVO(acc.getPersonId(),acc.getName(),totalBalance));
            }
        }
        //打印排行榜
        set.forEach(vo->{
            System.out.println("身份证号："+vo.getPersonId()+"，姓名："+vo.getName()+"，总资产："+vo.getTotalBalance());
        });
        return set;
    }

    /**
     * 通过身份证号统计总余额
     * @param personId
     * @return
     */
    public double getTotalBalanceByPersonId(String personId){
        double sum = 0.0;
        for (Account acc : dao.selectAll()) {
            if(acc!=null){
                if(acc.getPersonId().equals(personId)){
                    sum+=acc.getBalance();
                }
            }
        }
        return sum;
    }
}
