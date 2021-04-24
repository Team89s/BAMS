/*
 * Created by JFormDesigner on Thu Nov 19 20:48:38 CST 2020
 */

package com.igeek.bams.server;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author 11
 */
public class RankPanel extends JPanel {
    public RankPanel() {
        initComponents();
    }

    //点击返回按钮，跳转至ServerPanel
    private void backButtonActionPerformed(ActionEvent e) {
        CardLayout card = (CardLayout)this.getParent().getLayout();
        card.show(getParent(),"server");
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        totalBalanceButton = new JButton();
        totalCeilingButton = new JButton();
        totalLoanButton = new JButton();
        rankButton = new JButton();
        backButton = new JButton();
        scrollPane = new JScrollPane();
        label = new JLabel();

        //======== this ========
        setLayout(null);

        //---- totalBalanceButton ----
        totalBalanceButton.setText("\u67e5\u770b\u603b\u4f59\u989d");
        add(totalBalanceButton);
        totalBalanceButton.setBounds(20, 50, 105, 30);

        //---- totalCeilingButton ----
        totalCeilingButton.setText("\u67e5\u770b\u900f\u652f\u603b\u989d");
        add(totalCeilingButton);
        totalCeilingButton.setBounds(20, 120, 105, 30);

        //---- totalLoanButton ----
        totalLoanButton.setText("\u67e5\u770b\u8d37\u6b3e\u603b\u989d");
        add(totalLoanButton);
        totalLoanButton.setBounds(20, 190, 105, 30);

        //---- rankButton ----
        rankButton.setText("\u67e5\u770b\u603b\u8d44\u4ea7\u6392\u540d");
        add(rankButton);
        rankButton.setBounds(20, 255, 105, 30);

        //---- backButton ----
        backButton.setText("\u8fd4\u56de");
        backButton.addActionListener(e -> backButtonActionPerformed(e));
        add(backButton);
        backButton.setBounds(20, 325, 105, 30);

        //======== scrollPane ========
        {

            //---- label ----
            label.setHorizontalAlignment(SwingConstants.CENTER);
            scrollPane.setViewportView(label);
        }
        add(scrollPane);
        scrollPane.setBounds(155, 50, 230, 300);

        setPreferredSize(new Dimension(400, 425));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JButton totalBalanceButton;
    private JButton totalCeilingButton;
    private JButton totalLoanButton;
    private JButton rankButton;
    private JButton backButton;
    private JScrollPane scrollPane;
    private JLabel label;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
