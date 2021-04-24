/*
 * Created by JFormDesigner on Mon Dec 14 16:33:17 CST 2020
 */

package com.igeek.bams.server;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 * 服务器的界面
 */
public class ServerPanel extends JPanel {

    //控制是否可以无限接入客户端  true可以无限接入客户端
    private boolean flag;
    //是否成功接入客户端  true已成功接入客户端
    private boolean accept;
    //服务端套接字
    ServerSocket ss;
    //客户端套接字
    Socket s;
    //对象锁
    byte[] lock = new byte[0];
    //创建存储Tasker的容器
    List<Tasker> list = new ArrayList<>();

    public ServerPanel() {
        initComponents();
    }

    //启动服务器
    private void startButtonActionPerformed(ActionEvent e) {
        //可以无限接入客户端
        flag = true;
        //改变按钮的状态
        startButton.setEnabled(false);
        closeButton.setEnabled(true);

        try {
            ss = new ServerSocket(5566);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //启动一条副线程
        new Thread(()->{
            //若flag为true时，可以无限接入客户端
            while (flag){
                synchronized (lock){
                    try {
                        accept = false;  //此时客户端未接入
                        s = ss.accept();  //接入客户端，阻塞状态
                        System.out.println("客户端成功接入...");
                        accept = true;  //客户端已接入

                        //通知关闭服务器的按钮，随时可以关闭
                        lock.notify();

                        if(ss!=null && !ss.isClosed()){
                            if(s!=null && !ss.isClosed()){
                                Tasker tasker = new Tasker(s);
                                list.add(tasker);
                                //启动一条线程，来进行维护客户端与服务端的通信
                                new Thread(tasker).start();
                            }
                        }

                        //服务器关闭资源的过程耗时,给你3s足够时间关闭服务器
                        if(ss!=null){
                            lock.wait(3000);
                        }

                        if(ss==null){
                            JOptionPane.showMessageDialog(null,"服务器关闭");
                        }

                    } catch (IOException | InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }).start();
    }

    //关闭服务器
    private void closeButtonActionPerformed(ActionEvent e) {
        //不允许无限接入客户端
        flag = false;
        //改变按钮的状态
        startButton.setEnabled(true);
        closeButton.setEnabled(false);

        //创建一个客户端Socket，用来响应副线程中的accept()，让其不要一直无限阻塞
        try {
            new Socket("127.0.0.1",5566);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //线程通信
        synchronized (lock){
            try {
                if(accept==false){
                    lock.wait();
                }

                //客户端有接入情况，关闭服务器
                if(ss!=null){
                    ss.close();
                    ss = null;
                }

                //关闭客户端
                for (Tasker tasker : list) {
                    if(tasker.socket!=null){
                        tasker.socket.close();
                    }
                }
                //清空集合
                list.clear();

                //通知副线程，已经关闭资源了，可以弹窗了
                lock.notify();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    //查询银行信息
    private void queryButtonActionPerformed(ActionEvent e) {
        //跳转至RankPanel
        CardLayout card = (CardLayout)this.getParent().getLayout();
        card.show(getParent(),"rank");
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        label = new JLabel();
        startButton = new JButton();
        closeButton = new JButton();
        queryButton = new JButton();

        //======== this ========
        setLayout(null);

        //---- label ----
        label.setText("\u670d  \u52a1  \u5668");
        label.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label);
        label.setBounds(85, 125, 200, 40);

        //---- startButton ----
        startButton.setText("\u542f\u52a8\u670d\u52a1\u5668");
        startButton.addActionListener(e -> startButtonActionPerformed(e));
        add(startButton);
        startButton.setBounds(40, 220, 110, 35);

        //---- closeButton ----
        closeButton.setText("\u5173\u95ed\u670d\u52a1\u5668");
        closeButton.addActionListener(e -> closeButtonActionPerformed(e));
        add(closeButton);
        closeButton.setBounds(215, 220, 110, 35);

        //---- queryButton ----
        queryButton.setText("\u4e0a\u5e1d\u89c6\u89d2");
        queryButton.addActionListener(e -> queryButtonActionPerformed(e));
        add(queryButton);
        queryButton.setBounds(260, 75, 93, 35);

        { // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < getComponentCount(); i++) {
                Rectangle bounds = getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            setMinimumSize(preferredSize);
            setPreferredSize(preferredSize);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel label;
    private JButton startButton;
    private JButton closeButton;
    private JButton queryButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
