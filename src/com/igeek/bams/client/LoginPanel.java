/*
 * Created by JFormDesigner on Thu Nov 19 11:43:13 CST 2020
 */

package com.igeek.bams.client;

import java.awt.event.*;
import com.igeek.bams.entity.Account;
import com.igeek.bams.exception.LoginException;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;

/**
 * @author chenmin
 */
public class LoginPanel extends JPanel {
    private ATMClient atm;

    public LoginPanel(ATMClient atm) {
        this.atm = atm;
        initComponents();

        //调用国际化的初始化界面操作
        initLoginBundle();
    }

    //取消按钮
    private void cancelButtonActionPerformed(ActionEvent e) {
        CardLayout card = (CardLayout)this.getParent().getLayout();
        card.show(this.getParent(),"main");
        clearLogin();
    }

    //登录按钮
    private void loginButtonActionPerformed(ActionEvent e) {
        //添加网络编程
        String idStr = idTextField.getText();
        String pwd = new String(passwordField.getPassword());

        //发送指令
        String line = "login#"+idStr+"#"+pwd;
        atm.pw.println(line);
        atm.pw.flush();

        //读取服务端传回的对象信息
        try {
            Object o = atm.ois.readObject();
            if(o instanceof Account){
                //执行正常
                CardLayout card = (CardLayout)this.getParent().getLayout();
                card.show(this.getParent(),"business");
                //初始化业务界面
                atm.businessPanel.initBusiness((Account) o);
                clearLogin();
            }else if(o instanceof String){
                JOptionPane.showMessageDialog(null,o);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        /*String idStr = idTextField.getText();
        String pwd = new String(passwordField.getPassword());
        try {
            Long id = Long.valueOf(idStr);
            //执行Bank的登录操作
            Account acc = atm.bank.login(id, pwd);
            CardLayout card = (CardLayout)this.getParent().getLayout();
            card.show(this.getParent(),"business");
            //初始化业务界面
            atm.businessPanel.initBusiness(acc);
            clearLogin();*/

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        titleLabel = new JLabel();
        cancelButton = new JButton();
        loginButton = new JButton();
        idLabel = new JLabel();
        idTextField = new JTextField();
        pwdLabel = new JLabel();
        passwordField = new JPasswordField();

        //======== this ========
        setLayout(null);

        //---- titleLabel ----
        titleLabel.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel);
        titleLabel.setBounds(55, 45, 270, 60);

        //---- cancelButton ----
        cancelButton.addActionListener(e -> cancelButtonActionPerformed(e));
        add(cancelButton);
        cancelButton.setBounds(240, 290, 90, 39);

        //---- loginButton ----
        loginButton.addActionListener(e -> loginButtonActionPerformed(e));
        add(loginButton);
        loginButton.setBounds(55, 290, 90, 39);

        //---- idLabel ----
        idLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(idLabel);
        idLabel.setBounds(55, 155, 85, 20);
        add(idTextField);
        idTextField.setBounds(180, 150, 145, 24);

        //---- pwdLabel ----
        pwdLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(pwdLabel);
        pwdLabel.setBounds(55, 200, 85, 20);
        add(passwordField);
        passwordField.setBounds(180, 195, 145, 25);

        setPreferredSize(new Dimension(400, 435));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel titleLabel;
    private JButton cancelButton;
    private JButton loginButton;
    private JLabel idLabel;
    private JTextField idTextField;
    private JLabel pwdLabel;
    private JPasswordField passwordField;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    //国际化- 登录界面的初始化
    public void initLoginBundle(){
        titleLabel.setText(atm.bundle.getString("login.title"));
        cancelButton.setText(atm.bundle.getString("cancelButton"));
        loginButton.setText(atm.bundle.getString("loginButton"));
        idLabel.setText(atm.bundle.getString("login.id"));
        pwdLabel.setText(atm.bundle.getString("login.pwd"));
    }

    //清空登录界面
    public void clearLogin(){
        idTextField.setText("");
        passwordField.setText("");
    }

}
