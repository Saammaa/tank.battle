package MVC;

import Entity.*;
import MVC.Model.*;

import MVC.Renderer.Content;
import Utilities.Coordinate;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Controller extends Thread {
    Content view;

    Battleground battleground;

    public void start(Content content, Battleground battleground) {
        this.view = content;
        this.battleground = battleground;

        Tank userTank = battleground.userTank;

        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (battleground.userTank.isDestroyed()) return;

                double x = e.getX() - 9;
                double y = e.getY() - 38;

                userTank.turretDirection = Coordinate.getDirectionBetweenPoints(
                        userTank.view.x, userTank.view.y, x, y
                ) + 90;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (userTank.isDestroyed()) {
                    userTank.view.setMoving(false);
                    return;
                }

                Bullet bullet = new Bullet(userTank, 200);
                battleground.bullets.add(bullet);
            }
        };

        content.addMouseListener(adapter);
        content.addMouseMotionListener(adapter);
        content.addMouseWheelListener(adapter);

        content.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (userTank.isDestroyed()) {
                    userTank.view.setMoving(false);
                    return;
                }

                switch (e.getKeyChar()) {
                    case 'w':
                    case 'W':
                        userTank.view.setDirection(Directions.UP.getAngleValue());
                        userTank.view.setMoving(true);
                        break;
                    case 'a':
                    case 'A':
                        userTank.view.setDirection(Directions.LEFT.getAngleValue());
                        userTank.view.setMoving(true);
                        break;
                    case 's':
                    case 'S':
                        userTank.view.setDirection(Directions.DOWN.getAngleValue());
                        userTank.view.setMoving(true);
                        break;
                    case 'd':
                    case 'D':
                        userTank.view.setDirection(Directions.RIGHT.getAngleValue());
                        userTank.view.setMoving(true);
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyChar()) {
                    case 'w':
                    case 'W':
                    case 'a':
                    case 'A':
                    case 's':
                    case 'S':
                    case 'd':
                    case 'D':
                        userTank.view.setMoving(false);
                        break;
                }
            }
        });

        this.start();
    }

    @Override
    public void run() {
        super.run();

        int fps = 60;
        long lastUpdate = System.currentTimeMillis();

        while (true) {
            long interval = 1000 / fps;
            long currentTime = System.currentTimeMillis();
            long _time = currentTime - lastUpdate;

            if (_time < interval) {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {}
            } else {
                lastUpdate = currentTime;

                // 流逝时间
                float dt = _time * 0.001f;

                // 调度任务，如果有些任务计算量大，可以开线程池
                battleground.updateEnemies(view.width, view.height);

                battleground.updateTankPositions(dt);
                battleground.updateBulletPositions(dt);

                // 碰撞检测
                battleground.detectCollision();

                // 依据元素的状态，更新数据区
                battleground.updateDataset();

                // 刷新界面
                view.update(dt);
            }
        }
    }
}