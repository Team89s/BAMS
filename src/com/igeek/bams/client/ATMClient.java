/*
 * Created by JFormDesigner on Thu Nov 19 11:42:26 CST 2020
 */

package com.igeek.bams.client;

import com.igeek.bams.service.Bank;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.*;

/**
 * @author chenmin
 */
public class ATMClient extends JFrame {
    //界面
    MainPanel mainPanel;
    RegisterPanel registerPanel;
    LoginPanel loginPanel;
    BusinessPanel businessPanel;

    //国际化
    Locale locale;
    ResourceBundle bundle;
    boolean flag = true;

    //银行
    Bank bank;
    Container contentPane;

    //网络编程
    Socket s;
    PrintWriter pw;
    ObjectInputStream ois;

    public static void main(String[] args) {
        new ATMClient().setVisible(true);
    }

    public ATMClient() {
        initComponents();
        bank = Bank.getInstance();

        //初始化网络编程需要的资源
        try {
            s = new Socket("127.0.0.1",5566);
            pw = new PrintWriter(s.getOutputStream());
            ois = new ObjectInputStream(s.getInputStream());
        } catch (ConnectException e){  //连接异常
            JOptionPane.showMessageDialog(null,"服务器未启动");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //国际化的资源
        locale = new Locale("zh","CN");
        bundle = ResourceBundle.getBundle("message",locale);

        contentPane.setLayout(new CardLayout());

        mainPanel = new MainPanel(this);
        registerPanel = new RegisterPanel(this);
        loginPanel = new LoginPanel(this);
        businessPanel = new BusinessPanel(this);

        contentPane.add(mainPanel,"main");
        contentPane.add(registerPanel,"register");
        contentPane.add(loginPanel,"login");
        contentPane.add(businessPanel,"business");

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //写回关闭
                pw.println("close");
                pw.flush();
                //读取空串
                try {
                    ois.readObject();
                    //关闭资源
                    s.close();
                } catch (SocketException ex){
                    JOptionPane.showMessageDialog(null,"客户端退出");
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents

        //======== this ========
        setTitle("BAMS Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = getContentPane();
        contentPane.setLayout(null);

        contentPane.setPreferredSize(new Dimension(400, 450));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
