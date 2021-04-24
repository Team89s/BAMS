/*
 * Created by JFormDesigner on Thu Nov 19 20:48:16 CST 2020
 */

package com.igeek.bams.server;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

/**
 * @author 11
 */
public class ATMServer extends JFrame {

    private ServerPanel serverPanel;
    private RankPanel rankPanel;

    public static void main(String[] args) {
        new ATMServer().setVisible(true);
    }

    public ATMServer() {
        initComponents();

        serverPanel = new ServerPanel();
        rankPanel = new RankPanel();

        Container container = this.getContentPane();
        container.setLayout(new CardLayout());
        this.add(serverPanel,"server");
        this.add(rankPanel,"rank");

        //关闭事件
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        contentPane.setPreferredSize(new Dimension(405, 460));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // JFormDesigner - End of variables declaration  //GEN-END:variables

}
