/*
 * Created by JFormDesigner on Thu Nov 19 11:43:00 CST 2020
 */

package com.igeek.bams.client;

import com.igeek.bams.entity.Account;
import com.igeek.bams.exception.RegisterException;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author 11
 */
public class RegisterPanel extends JPanel {
    private ATMClient atm;

    public RegisterPanel(ATMClient atm) {
        this.atm = atm;
        initComponents();

        //调用国际化的初始化界面操作
        initRegisterBundle();
    }

    //提交按钮触发的操作
    private void submitButtonActionPerformed(ActionEvent e) {
        String name = nameTextField.getText();
        String pwd = new String(passwordField.getPassword());
        String repwd = new String(repasswordField.getPassword());
        String personId = personIdTextField.getText();
        String address = addressTextField.getText();
        String email = emailTextField.getText();
        int type = comboBox.getSelectedIndex();

        if(name!=null && pwd!=null && repwd!=null && personId!=null
                && !"".equals(name) && !"".equals(pwd) && !"".equals(repwd) && !"".equals(personId)){
            try {
                Account acc = atm.bank.register(pwd, repwd, name, personId, email, type);
                //跳转到业务界面
                CardLayout card = (CardLayout)this.getParent().getLayout();
                card.show(this.getParent(),"business");
                //初始化业务界面
                atm.businessPanel.initBusiness(acc);
                //清空注册
                clearRegister();
            } catch (RegisterException ex) {
                JOptionPane.showMessageDialog(null,ex.getMessage());
                passwordField.setText("");
                repasswordField.setText("");
            }
        }else{
            JOptionPane.showMessageDialog(null,"信息为空，请填写信息！");
        }

    }

    //返回按钮触发的操作
    private void backButtonActionPerformed(ActionEvent e) {
        //跳转到业务界面
        CardLayout card = (CardLayout)this.getParent().getLayout();
        card.show(this.getParent(),"main");
        clearRegister();
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        titleLabel = new JLabel();
        submitButton = new JButton();
        backButton = new JButton();
        typeLabel = new JLabel();
        nameLabel = new JLabel();
        pwdLabel = new JLabel();
        repwdLabel = new JLabel();
        personIdLabel = new JLabel();
        addressLabel = new JLabel();
        emailLabel = new JLabel();
        nameTextField = new JTextField();
        comboBox = new JComboBox();
        passwordField = new JPasswordField();
        repasswordField = new JPasswordField();
        personIdTextField = new JTextField();
        addressTextField = new JTextField();
        emailTextField = new JTextField();

        //======== this ========
        setLayout(null);

        //---- titleLabel ----
        titleLabel.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel);
        titleLabel.setBounds(100, 20, 185, 35);

        //---- submitButton ----
        submitButton.addActionListener(e -> submitButtonActionPerformed(e));
        add(submitButton);
        submitButton.setBounds(65, 395, 90, 34);

        //---- backButton ----
        backButton.addActionListener(e -> backButtonActionPerformed(e));
        add(backButton);
        backButton.setBounds(245, 395, 90, 34);

        //---- typeLabel ----
        typeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(typeLabel);
        typeLabel.setBounds(45, 80, 85, 20);

        //---- nameLabel ----
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(nameLabel);
        nameLabel.setBounds(45, 130, 85, 20);

        //---- pwdLabel ----
        pwdLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(pwdLabel);
        pwdLabel.setBounds(45, 175, 85, 20);

        //---- repwdLabel ----
        repwdLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(repwdLabel);
        repwdLabel.setBounds(50, 215, 85, 20);

        //---- personIdLabel ----
        personIdLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(personIdLabel);
        personIdLabel.setBounds(50, 265, 85, 20);

        //---- addressLabel ----
        addressLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(addressLabel);
        addressLabel.setBounds(50, 305, 85, 20);

        //---- emailLabel ----
        emailLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(emailLabel);
        emailLabel.setBounds(50, 350, 85, 20);
        add(nameTextField);
        nameTextField.setBounds(170, 125, 145, nameTextField.getPreferredSize().height);
        add(comboBox);
        comboBox.setBounds(170, 80, 145, comboBox.getPreferredSize().height);
        add(passwordField);
        passwordField.setBounds(170, 170, 145, passwordField.getPreferredSize().height);
        add(repasswordField);
        repasswordField.setBounds(170, 215, 145, 25);
        add(personIdTextField);
        personIdTextField.setBounds(170, 260, 145, 24);
        add(addressTextField);
        addressTextField.setBounds(170, 300, 145, 24);
        add(emailTextField);
        emailTextField.setBounds(170, 345, 145, 24);

        setPreferredSize(new Dimension(400, 450));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel titleLabel;
    private JButton submitButton;
    private JButton backButton;
    private JLabel typeLabel;
    private JLabel nameLabel;
    private JLabel pwdLabel;
    private JLabel repwdLabel;
    private JLabel personIdLabel;
    private JLabel addressLabel;
    private JLabel emailLabel;
    private JTextField nameTextField;
    private JComboBox comboBox;
    private JPasswordField passwordField;
    private JPasswordField repasswordField;
    private JTextField personIdTextField;
    private JTextField addressTextField;
    private JTextField emailTextField;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    //国际化- 主界面的初始化
    public void initRegisterBundle(){
        titleLabel.setText(atm.bundle.getString("register.title"));
        submitButton.setText(atm.bundle.getString("submitButton"));
        backButton.setText(atm.bundle.getString("backButton"));
        typeLabel.setText(atm.bundle.getString("register.type"));
        nameLabel.setText(atm.bundle.getString("register.name"));
        pwdLabel.setText(atm.bundle.getString("register.pwd"));
        repwdLabel.setText(atm.bundle.getString("register.repwd"));
        personIdLabel.setText(atm.bundle.getString("register.personId"));
        addressLabel.setText(atm.bundle.getString("register.address"));
        emailLabel.setText(atm.bundle.getString("register.email"));
        comboBox.setModel(new DefaultComboBoxModel(new Object[]{
                atm.bundle.getString("register.type01"),
                atm.bundle.getString("register.type02"),
                atm.bundle.getString("register.type03"),
                atm.bundle.getString("register.type04")
        }));
    }

    //清空注册界面
    public void clearRegister(){
        nameTextField.setText("");
        passwordField.setText("");
        repasswordField.setText("");
        personIdTextField.setText("");
        addressTextField.setText("");
        emailTextField.setText("");
        comboBox.setSelectedIndex(0);
    }
}
