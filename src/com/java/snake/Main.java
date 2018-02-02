package com.java.snake;

import com.java.snake.ui.Login;

import javax.swing.*;
import java.awt.*;

/**
 * @author Aragaki9527
 * @date 01/29/2018*/
public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            Login frame = new Login();
            frame.setTitle("Gluttonous Snake");
            frame.setLocation(500, 200);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.setVisible(true);
        });
    }
}
