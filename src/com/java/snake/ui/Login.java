package com.java.snake.ui;

import javax.swing.*;
import java.awt.*;

/**
 * 登陆界面
 * @author Aragaki9527
 * @date 01/29/2018*/
public class Login extends JFrame {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;
    private Rank rank;
    private Body body;

    public Login() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        JPanel loginPanel = new JPanel();

        JButton start = new JButton("Start");
        JButton rankButton = new JButton("Rank");

        loginPanel.add(start);
        loginPanel.add(rankButton);

        add(loginPanel, BorderLayout.SOUTH);

        start.addActionListener(event-> {
            /*跳转到游戏页面*/
            this.setVisible(false);
            if (body == null) {
                EventQueue.invokeLater(()-> {
                    body = new Body();
                    body.setTitle("Gluttonous Snake");
                    body.setLocation(500, 200);
                    body.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    body.setResizable(false);
                    body.setVisible(true);
                });
            } else {
                body.setVisible(true);
            }
        });
        rankButton.addActionListener(event-> {
            /*跳转到排行界面*/
            this.setVisible(false);
            if (rank == null) {
                EventQueue.invokeLater(() -> {
                    rank = new Rank(this);
                    rank.setTitle("Gluttonous Snake");
                    rank.setLocation(500, 200);
                    rank.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    rank.setResizable(false);
                    rank.setVisible(true);
                });
            } else {
                rank.setVisible(true);
            }
        });
        pack();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
}