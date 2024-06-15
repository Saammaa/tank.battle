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

    Battle battleData;

    public Controller() {}

    public void start(Content content, Battle battle) {
        this.view = content;
        this.battleData = battle;

        Tank tank = battle.userTank;
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (battle.userTank.isDestroyed()) return;
                double x = e.getX() - 9;
                double y = e.getY() - 38;
                tank.turretDirection = Coordinate.getDirectionBetweenPoints(tank.view.x, tank.view.y, x, y) + 90;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (tank.isDestroyed()) {
                    tank.moving = false;
                    return;
                }
                Bullet bullet = new Bullet(tank, 200);
                battle.views.add(bullet.view);
            }
        };

        content.addMouseListener(adapter);
        content.addMouseMotionListener(adapter);
        content.addMouseWheelListener(adapter);

        content.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (tank.isDestroyed()) {
                    tank.setMoving(false);
                    return;
                }

                switch (e.getKeyChar()) {
                    case 'w':
                    case 'W':
                        tank.direction = Directions.UP.getAngleValue();
                        tank.setMoving(true);
                        break;
                    case 'a':
                    case 'A':
                        tank.direction = Directions.LEFT.getAngleValue();
                        tank.setMoving(true);
                        break;
                    case 's':
                    case 'S':
                        tank.direction = Directions.DOWN.getAngleValue();
                        tank.setMoving(true);
                        break;
                    case 'd':
                    case 'D':
                        tank.direction = Directions.RIGHT.getAngleValue();
                        tank.setMoving(true);
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
                        tank.setMoving(false);
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
                battleData.runEnemyTank(view.width, view.height);

                battleData.updatePositions(dt);

                // 碰撞检测
                battleData.collisionDetection();

                // 依据元素的状态，更新数据区
                battleData.updateDataset();

                // 刷新界面
                view.update(dt);
            }
        }
    }
}