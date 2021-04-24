/*
 * Created by JFormDesigner on Thu Nov 19 11:43:28 CST 2020
 */

package com.igeek.bams.client;

import java.awt.*;
import java.awt.event.*;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.*;

/**
 * @author 11
 */
public class MainPanel extends JPanel {

    private ATMClient atm;

    public MainPanel(ATMClient atm) {
        this.atm = atm;
        initComponents();
        //调用国际化的初始化界面操作
        initMainBundle();
    }

    //中英文切换的按钮
    private void changeButtonActionPerformed(ActionEvent e) {
        atm.flag=!atm.flag;
        if(atm.flag){ //true  中文
            atm.locale = new Locale("zh","CN");
        }else{  //false  英文
            atm.locale = new Locale("en","US");
        }
        atm.bundle = ResourceBundle.getBundle("message",atm.locale);

        //各个界面国际化
        atm.mainPanel.initMainBundle();
        atm.registerPanel.initRegisterBundle();
        atm.loginPanel.initLoginBundle();
        atm.businessPanel.initBusinessBundle();
    }

    //点击注册按钮触发跳转
    private void registButtonActionPerformed(ActionEvent e) {
        //跳转注册界面
        CardLayout card = (CardLayout)this.getParent().getLayout();
        card.show(this.getParent(),"register");
    }

    //点击登录按钮触发跳转
    private void loginButtonActionPerformed(ActionEvent e) {
        CardLayout card = (CardLayout)this.getParent().getLayout();
        card.show(this.getParent(),"login");
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        titleLabel = new JLabel();
        registButton = new JButton();
        loginButton = new JButton();
        changeButton = new JButton();

        //======== this ========
        setLayout(null);

        //---- titleLabel ----
        titleLabel.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel);
        titleLabel.setBounds(60, 60, 270, 60);

        //---- registButton ----
        registButton.addActionListener(e -> registButtonActionPerformed(e));
        add(registButton);
        registButton.setBounds(55, 230, 90, 39);

        //---- loginButton ----
        loginButton.addActionListener(e -> {
			loginButtonActionPerformed(e);
			loginButtonActionPerformed(e);
		});
        add(loginButton);
        loginButton.setBounds(240, 230, 90, 39);

        //---- changeButton ----
        changeButton.setText("\u4e2d\u82f1\u6587\u5207\u6362");
        changeButton.addActionListener(e -> changeButtonActionPerformed(e));
        add(changeButton);
        changeButton.setBounds(110, 315, 145, changeButton.getPreferredSize().height);

        setPreferredSize(new Dimension(400, 390));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel titleLabel;
    private JButton registButton;
    private JButton loginButton;
    private JButton changeButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    //国际化- 主界面的初始化
    public void initMainBundle(){
        titleLabel.setText(atm.bundle.getString("main.title"));
        registButton.setText(atm.bundle.getString("registButton"));
        loginButton.setText(atm.bundle.getString("loginButton"));
    }
}
