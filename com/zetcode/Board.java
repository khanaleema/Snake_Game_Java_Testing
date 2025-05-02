package com.zetcode;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Board extends JPanel implements ActionListener {

    private final int B_WIDTH = 300;
    private final int B_HEIGHT = 300;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 900;
    private final int RAND_POS = 29;
    private final int DELAY = 140;

    private final int[] x = new int[ALL_DOTS];
    private final int[] y = new int[ALL_DOTS];

    private int dots;
    private int apple_x;
    private int apple_y;
    private int score = 0;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;
    private boolean paused = false;

    private Timer timer;

    public Board() {
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        initGame();
    }

    private void initGame() {
        dots = 3;
        score = 0;
        inGame = true;
        paused = false;

        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * DOT_SIZE;
            y[z] = 50;
        }

        locateApple();
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        if (inGame) {
            g.setColor(Color.red);
            g.fillRect(apple_x, apple_y, DOT_SIZE, DOT_SIZE);

            g.setColor(Color.green);
            for (int z = 0; z < dots; z++) {
                g.fillRect(x[z], y[z], DOT_SIZE, DOT_SIZE);
            }

            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.PLAIN, 14));
            g.drawString("Score: " + score, 10, B_HEIGHT - 10);

        } else {
            gameOver(g);
        }
    }

    private void gameOver(Graphics g) {
        String msg = "Game Over - Score: " + score;
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
        g.drawString("Press ENTER to Restart", (B_WIDTH - 150) / 2, B_HEIGHT / 2 + 20);
    }

    private void checkApple() {
        if ((x[0] == apple_x) && (y[0] == apple_y)) {
            dots++;
            score++;
            locateApple();
        }
    }

    private void move() {
        for (int z = dots; z > 0; z--) {
            x[z] = x[z - 1];
            y[z] = y[z - 1];
        }

        if (leftDirection) {
            x[0] -= DOT_SIZE;
        }
        if (rightDirection) {
            x[0] += DOT_SIZE;
        }
        if (upDirection) {
            y[0] -= DOT_SIZE;
        }
        if (downDirection) {
            y[0] += DOT_SIZE;
        }
    }

    private void checkCollision() {
        for (int z = dots; z > 0; z--) {
            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
            }
        }

        if (y[0] >= B_HEIGHT || y[0] < 0 || x[0] >= B_WIDTH || x[0] < 0) {
            inGame = false;
        }

        if (!inGame) {
            timer.stop();
        }
    }

    private void locateApple() {
        int r = (int) (Math.random() * RAND_POS);
        apple_x = r * DOT_SIZE;
        r = (int) (Math.random() * RAND_POS);
        apple_y = r * DOT_SIZE;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame && !paused) {
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

            if (key == KeyEvent.VK_P) {
                paused = true;
            }
            if (key == KeyEvent.VK_R) {
                paused = false;
            }

            if (key == KeyEvent.VK_ENTER && !inGame) {
                initGame();
            }
        }
    }

    // --------- Public Test Helper Methods ------------

    public void setRightDirection(boolean val) {
        rightDirection = val;
    }

    public void setLeftDirection(boolean val) {
        leftDirection = val;
    }

    public void setUpDirection(boolean val) {
        upDirection = val;
    }

    public void setDownDirection(boolean val) {
        downDirection = val;
    }

    public int getHeadX() {
        return x[0];
    }

    public int getHeadY() {
        return y[0];
    }

    public void triggerMove() {
        move();
    }

    public int getDots() {
        return dots;
    }

    public void simulateFoodEaten() {
        dots++;
        score++;
    }

    public void pauseGame() {
        paused = true;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setApplePosition(int ax, int ay) {
        apple_x = ax;
        apple_y = ay;
    }

    public void triggerCheckApple() {
        checkApple();
    }
}
