package com.java.snake.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.util.Map;
import java.util.Properties;

/**
 * 游戏结束界面
 * @author Aragaki9527
 * @date 02/01/2018*/
class GameOver extends JFrame {

    GameOver(int score, Body body) {
        JPanel panel = new JPanel();
        JButton back = new JButton("重新开始");

        add(panel, BorderLayout.SOUTH);

        add(new GameOverComponent(score));

        panel.add(back);

        writeScore(score);

        back.addActionListener(e -> {
            MoveDia.setFlag(true);
            body.setVisible(true);
            this.dispose();
        });

        pack();
    }

    /**
     * 将得分写入配置文件中*/
    private static void writeScore(int score) {
        FileReader fr = null;
        FileWriter fw = null;
        try {
            Properties prop = new Properties();
            File file = new File("src\\rankInfo.properties");

            fr = new FileReader(file);

            prop.load(fr);
            for (Map.Entry entry :
                    prop.entrySet()) {
                if (score > Integer.parseInt((String)entry.getValue())) {
                    prop.setProperty(entry.getKey() + "", score + "");
                    break;
                }
            }

            fw = new FileWriter(file);
            prop.store(fw, null);

        } catch (IOException e) {
            throw new RuntimeException("文件读取失败.");
        } finally {
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

class GameOverComponent extends JComponent {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;
    private static int score;

    GameOverComponent(int score) {
        GameOverComponent.score = score;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        Font font = new Font("微软雅黑", Font.BOLD, 32);
        Graphics2D g2 = (Graphics2D)graphics;
        String message = "得分：" + score;

        g2.setFont(font);

        FontRenderContext frc = g2.getFontRenderContext();
        Rectangle2D rect = font.getStringBounds(message, frc);

        double x = (getWidth() - rect.getWidth()) / 2;
        double y = (getHeight() - rect.getHeight()) / 2;

        double ascent = -rect.getY();
        double baseY = y + ascent;

        g2.drawString(message, (int)x, (int)baseY);
        g2.setPaint(Color.LIGHT_GRAY);
    }

    @Override
    public Dimension getPreferredSize() {return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);}
}