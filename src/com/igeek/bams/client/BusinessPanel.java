/*
 * Created by JFormDesigner on Thu Nov 19 11:44:13 CST 2020
 */

package com.igeek.bams.client;

import java.awt.event.*;

import com.igeek.bams.entity.*;
import com.igeek.bams.exception.*;

import java.awt.*;
import javax.swing.*;

/**
 * @author 11
 */
public class BusinessPanel extends JPanel {
    private ATMClient atm;

    public BusinessPanel(ATMClient atm) {
        this.atm = atm;
        initComponents();

        //调用国际化的初始化界面操作
        initBusinessBundle();
    }

    //返回到登录界面
    private void backButtonActionPerformed(ActionEvent e) {
        CardLayout card = (CardLayout)this.getParent().getLayout();
        card.show(this.getParent(),"login");
    }

    //提交业务操作
    private void submitButtonActionPerformed(ActionEvent e) {
        Account account = null;
        Long idL = Long.valueOf(id.getText());
        int action = comboBox.getSelectedIndex();

        double moneyL = 0.0;
        try {
            moneyL = Double.valueOf(money.getText());
            if(moneyL>0){
                switch (action){
                    case 0: //存款
                        try {
                            account = atm.bank.deposit(idL,moneyL);
                            System.out.println(account);
                        } catch (LoginException ex) {
                            JOptionPane.showMessageDialog(null,ex.getMessage());
                        }
                        break;
                    case 1: //取款
                        String password = JOptionPane.showInputDialog(null, "请输入密码");
                        try {
                            account = atm.bank.withdraw(idL, password, moneyL);
                        } catch (LoginException | BalanceNotEnoughException ex) {
                            JOptionPane.showMessageDialog(null,ex.getMessage());
                        }
                        break;
                    case 2://转账
                        String toId = JOptionPane.showInputDialog(null, "请输入对方账号");
                        if(toId!=null && !toId.equals("")){
                            int i = JOptionPane.showConfirmDialog(null, "对方账号为" + toId + "，您确定么？");
                            if(i==0){ //确定
                                password = JOptionPane.showInputDialog(null, "请输入密码");
                                try {
                                    boolean flag = atm.bank.transfer(idL, password, Long.valueOf(toId), moneyL);
                                    if(flag){
                                        account = atm.bank.login(idL,password);
                                    }
                                } catch (LoginException | TransferException | BalanceNotEnoughException ex) {
                                    JOptionPane.showMessageDialog(null,ex.getMessage());
                                } catch (NumberFormatException ex){
                                    JOptionPane.showMessageDialog(null,"对方账户输入不合法！");
                                }
                            }
                        }else{
                            JOptionPane.showMessageDialog(null,"对方账户不能为空！");
                        }
                        break;
                    case 3: //设置透支额度
                        password = JOptionPane.showInputDialog(null, "请输入密码");
                        try {
                            account = atm.bank.updateCeiling(idL,password,moneyL);
                        } catch (TypeException |LoginException ex) {
                            JOptionPane.showMessageDialog(null,ex.getMessage());
                        }
                        break;
                    case 4: //贷款
                        try {
                            account = atm.bank.requestLoan(idL,moneyL);
                        } catch (TypeException | LoanException ex) {
                            JOptionPane.showMessageDialog(null,ex.getMessage());
                        }
                        break;
                    case 5: //还贷
                        try {
                            account = atm.bank.payLoan(idL,moneyL);
                        } catch (TypeException | LoanException | BalanceNotEnoughException ex) {
                            JOptionPane.showMessageDialog(null,ex.getMessage());
                        }
                        break;
                }

                //初始化界面
                if(account!=null){
                    initBusiness(account);
                }else{
                    comboBox.setSelectedIndex(0);
                    money.setText("0.0");
                }
            }else{
                JOptionPane.showMessageDialog(null,"操作金额必须大于0！");
            }
        }catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(null,"操作金额不合法！");
        }

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        titleLabel = new JLabel();
        idLabel = new JLabel();
        comboBox = new JComboBox();
        nameLabel = new JLabel();
        balanceLabel = new JLabel();
        ceilLabel = new JLabel();
        loanLabel = new JLabel();
        submitButton = new JButton();
        backButton = new JButton();
        id = new JLabel();
        name = new JLabel();
        balance = new JLabel();
        ceil = new JLabel();
        loan = new JLabel();
        money = new JTextField();

        //======== this ========
        setLayout(null);

        //---- titleLabel ----
        titleLabel.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel);
        titleLabel.setBounds(100, 30, 185, 35);

        //---- idLabel ----
        idLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(idLabel);
        idLabel.setBounds(45, 90, 85, 20);
        add(comboBox);
        comboBox.setBounds(45, 325, 105, 24);

        //---- nameLabel ----
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(nameLabel);
        nameLabel.setBounds(45, 140, 85, 20);

        //---- balanceLabel ----
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(balanceLabel);
        balanceLabel.setBounds(45, 185, 85, 20);

        //---- ceilLabel ----
        ceilLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(ceilLabel);
        ceilLabel.setBounds(45, 230, 85, 20);

        //---- loanLabel ----
        loanLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(loanLabel);
        loanLabel.setBounds(45, 280, 90, 20);

        //---- submitButton ----
        submitButton.addActionListener(e -> submitButtonActionPerformed(e));
        add(submitButton);
        submitButton.setBounds(55, 380, 90, 34);

        //---- backButton ----
        backButton.addActionListener(e -> backButtonActionPerformed(e));
        add(backButton);
        backButton.setBounds(225, 380, 90, 34);

        //---- id ----
        id.setHorizontalAlignment(SwingConstants.CENTER);
        add(id);
        id.setBounds(190, 90, 165, 20);

        //---- name ----
        name.setHorizontalAlignment(SwingConstants.CENTER);
        add(name);
        name.setBounds(190, 140, 165, 20);

        //---- balance ----
        balance.setHorizontalAlignment(SwingConstants.CENTER);
        add(balance);
        balance.setBounds(190, 185, 165, 20);

        //---- ceil ----
        ceil.setHorizontalAlignment(SwingConstants.CENTER);
        add(ceil);
        ceil.setBounds(190, 230, 165, 20);

        //---- loan ----
        loan.setHorizontalAlignment(SwingConstants.CENTER);
        add(loan);
        loan.setBounds(190, 280, 165, 20);
        add(money);
        money.setBounds(190, 325, 165, money.getPreferredSize().height);

        setPreferredSize(new Dimension(400, 450));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel titleLabel;
    private JLabel idLabel;
    private JComboBox comboBox;
    private JLabel nameLabel;
    private JLabel balanceLabel;
    private JLabel ceilLabel;
    private JLabel loanLabel;
    private JButton submitButton;
    private JButton backButton;
    private JLabel id;
    private JLabel name;
    private JLabel balance;
    private JLabel ceil;
    private JLabel loan;
    private JTextField money;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    //国际化业务界面
    public void initBusinessBundle(){
        titleLabel.setText(atm.bundle.getString("bus.title"));
        submitButton.setText(atm.bundle.getString("submitButton"));
        backButton.setText(atm.bundle.getString("backButton"));
        idLabel.setText(atm.bundle.getString("bus.id"));
        ceilLabel.setText(atm.bundle.getString("bus.ceiling"));
        nameLabel.setText(atm.bundle.getString("bus.name"));
        balanceLabel.setText(atm.bundle.getString("bus.balance"));
        loanLabel.setText(atm.bundle.getString("bus.loan"));
    }

    //初始化业务界面
    public void initBusiness(Account acc){
        id.setText(acc.getId().toString());
        name.setText(acc.getName());
        balance.setText(acc.getBalance()+"");
        if(acc instanceof CreditAccount){
            ceil.setText((((CreditAccount) acc).getCeiling())+"");
        }else{
            ceil.setText("不支持设置透支额度");
        }
        if(acc instanceof LoanCreditAccount){
            loan.setText(((LoanCreditAccount) acc).getLoanAmount()+"");
        }else if(acc instanceof LoanSavingAccount){
            loan.setText(((LoanSavingAccount) acc).getLoanAmount()+"");
        }else {
            loan.setText("不支持贷款");
        }
        //设置操作
        if(acc instanceof LoanSavingAccount){
            comboBox.setModel(new DefaultComboBoxModel<>(new String[] {
                    "存款", "取款", "转账", "贷款", "还贷"
            }));
        }else if(acc instanceof LoanCreditAccount){
            comboBox.setModel(new DefaultComboBoxModel<>(new String[] {
                    "存款", "取款", "转账", "修改透支额度", "贷款", "还贷"
            }));
        }else if(acc instanceof CreditAccount){
            comboBox.setModel(new DefaultComboBoxModel<>(new String[] {
                    "存款", "取款", "转账", "修改透支额度"
            }));
        }else if(acc instanceof SavingAccount){
            comboBox.setModel(new DefaultComboBoxModel<>(new String[] {
                    "存款", "取款", "转账"
            }));
        }

        money.setText("0.0");
    }
}
