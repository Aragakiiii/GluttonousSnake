package com.java.snake.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 游戏界面
 * @author Aragaki9527
 * @date 01/30/2018*/
class Body extends JFrame {

    Body() {
        add(new MoveDia(this));
        pack();
    }
}

class MoveDia extends JComponent {
    private MoveDia moveDia = this;

    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;
    private static final double WIDTH = 10;
    private static final double HEIGHT = 10;
    private static String direction = null;
    private static double x = 0;
    private static double y = 0;
    private static int score = 0;
    private static boolean flag = true;
    private LinkedList<MyRect2D> list;
    private MyRect2D randomRect;

    MoveDia(Body body) {
        JPanel panel = new JPanel();

        //define actions
        Action actionUp = new MoveAction("up");
        Action actionDown = new MoveAction("down");
        Action actionLeft = new MoveAction("left");
        Action actionRight = new MoveAction("right");

        //add button
        panel.add(new JButton(actionUp));
        panel.add(new JButton(actionDown));
        panel.add(new JButton(actionLeft));
        panel.add(new JButton(actionRight));

        add(panel);

        //associate Keys with name
        InputMap inputMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke("pressed UP"), "up");
        inputMap.put(KeyStroke.getKeyStroke("pressed DOWN"), "down");
        inputMap.put(KeyStroke.getKeyStroke("pressed LEFT"), "left");
        inputMap.put(KeyStroke.getKeyStroke("pressed RIGHT"), "right");

        ActionMap actionMap = panel.getActionMap();
        actionMap.put("up", actionUp);
        actionMap.put("down", actionDown);
        actionMap.put("left", actionLeft);
        actionMap.put("right", actionRight);

        list = new LinkedList<>();
        list.add(new MyRect2D(0, 0 ));

        setRandom();

        Timer timer = new Timer(100, event-> {
            if (flag) {
                if (direction != null) {
                    switch (direction) {
                        case "up":
                            x = 0;
                            y = -HEIGHT;
                            break;
                        case "down":
                            x = 0;
                            y = HEIGHT;
                            break;
                        case "left":
                            x = -WIDTH;
                            y = 0;
                            break;
                        case "right":
                            x = WIDTH;
                            y = 0;
                            break;
                        default:
                            throw new RuntimeException("键盘响应异常.");
                    }
                    MyRect2D firstRect = list.peekFirst();
                    double resultX = firstRect.getX() + x;
                    double resultY = firstRect.getY() + y;

                    /*检测是否吃到自己*/
                    Iterator<MyRect2D> it = list.iterator();
                    it.next();
                    while (it.hasNext()) {
                        MyRect2D tmpRect = it.next();
                        if (resultX == tmpRect.getX() && resultY == tmpRect.getY()) {
                            toGameOver(body);
                        }
                    }

                    if (((resultX < 0) || (resultX > DEFAULT_WIDTH - WIDTH) || (resultY < 0.0) || (resultY > DEFAULT_HEIGHT - HEIGHT))) {
                        toGameOver(body);
                        /*GAME OVER*/
                    } else if (resultX == randomRect.getX() && resultY == randomRect.getY()) {
                        list.addFirst(randomRect);
                        score++;
                        setRandom();
                    } else {
                        /*移除最后一个节点，重新赋值后，加入到头结点*/
                        MyRect2D addRect = list.removeLast();
                        addRect.setRect(resultX, resultY);
                        list.addFirst(addRect);
                    }
                    moveDia.repaint();
                }
            }
        });
        timer.start();
    }

    /**
     * 从游戏结束界面重新开始游戏时调用，用来使定时器重新工作
     * @param flag 计时器标志*/
    public static void setFlag(boolean flag) {
        MoveDia.flag = flag;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        Graphics2D g2 = (Graphics2D)graphics;

        g2.setPaint(Color.black);
        for (MyRect2D rect :
                list) {
            g2.fill(rect);
        }

        g2.setPaint(Color.BLUE);
        g2.fill(randomRect);
    }

    /**
     * 隐藏当前界面并初始化，打开游戏结束界面
     * @param body 将当前对象传递给GameOver类*/
    private void toGameOver(Body body) {
        EventQueue.invokeLater(() -> {
            GameOver frame = new GameOver(score, body);
            score = 0;
            frame.setTitle("Gluttonous Snake");
            frame.setLocation(500, 200);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.setVisible(true);
            body.setVisible(false);
            flag = false;
            list = new LinkedList<>();
            list.add(new MyRect2D(0, 0 ));
            direction = null;
        });
    }

    /**
     * 随机产生蓝色小方块的位置*/
    private void setRandom() {
        double randomX = Math.floor(Math.random()*30);
        double randomY = Math.floor(Math.random()*20);

        boolean flag = true;

        while (flag) {
            randomX = Math.floor(Math.random()*30)*10;
            randomY = Math.floor(Math.random()*20)*10;
            flag = false;
            for (MyRect2D rect :
                    list) {
                if (randomX == rect.getX() && randomY == rect.getY()) {
                    flag = true;
                }
            }
        }
        randomRect = new MyRect2D(randomX, randomY);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /*以上的部分负责界面重绘*/

    /**
     * 内部类继承了AbstractAction接口，用来响应键盘输入事件*/
    class MoveAction extends AbstractAction {

        private static final String UP = "up";
        private static final String DOWN = "down";
        private static final String LEFT = "left";
        private static final String RIGHT = "right";

        MoveAction(String s) {
            putValue("direction", s);
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            String thisDirection = (String)getValue("direction");
            if (UP.equals(direction) && !DOWN.equals(thisDirection)) {
                direction = thisDirection;
            }
            else if (DOWN.equals(direction) && !UP.equals(thisDirection)) {
                direction = thisDirection;
            }
            else if (LEFT.equals(direction) && !RIGHT.equals(thisDirection)) {
                direction = thisDirection;
            }
            else if (RIGHT.equals(direction) && !LEFT.equals(thisDirection)) {
                direction = thisDirection;
            }
            else if (direction == null) {
                direction = thisDirection;
            }
        }
    }
}

/**
 * Rect辅助类，定义了坐标覆盖了setRect方法*/
class MyRect2D extends Rectangle2D.Double {
    private static final double DIA_WIDTH = 10;
    private static final double DIA_HEIGHT = 10;

    MyRect2D(double x, double y) {
        super(x, y, DIA_WIDTH, DIA_HEIGHT);
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    /**
     * 覆盖setRect方法*/
    public void setRect(double x, double y) {
        super.setRect(x, y, DIA_WIDTH, DIA_HEIGHT);
    }
}