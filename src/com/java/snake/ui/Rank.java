package com.java.snake.ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * 排行界面
 * @author Aragaki9527
 * @date 01/29/2018*/
public class Rank extends JFrame {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;
    private JTextArea rankText;

    Rank(Login login) {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        JPanel backPanel = new JPanel();

        JButton back = new JButton("Back");

        back.addActionListener(event->{
            login.setVisible(true);
            this.setVisible(false);
        });

        backPanel.add(back, BorderLayout.EAST);

        add(backPanel, BorderLayout.SOUTH);

        rankText = new JTextArea(10, 10);
        rankText.setEditable(false);
        setRankText();

        add(rankText);

        pack();
    }

    /**
     * 加载排行信息配置文件*/
    private void setRankText() {
        try {
            File file = new File("src\\rankInfo.properties");
            Properties prop = new Properties();

            FileReader fr = new FileReader(file);
            prop.load(fr);

            for (Map.Entry entry :
                    prop.entrySet()) {
                rankText.append(entry.getKey() + " : " + entry.getValue() + "\r\n");
            }

        } catch (IOException e) {
            throw new RuntimeException("配置文件加载失败.");
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
}
