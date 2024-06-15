package MVC.Controller;

import MVC.Model.Directions;
import Entity.Bullet;
import Entity.Tank;
import MVC.Model.BattleData;
import MVC.View.Content;
import Utilities.Coordinate;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 1. 读取键盘、鼠标信息，控制主坦克
 * 2. 线程后台刷新子弹、坦克位置
 * 3. 线程后台计算碰撞检测
 */
public class Battle extends Thread {
    Content view;

    BattleData data;

    public Battle() {}

    public void start(Content content, BattleData battleData) {
        this.view = content;
        this.data = battleData;

        Tank tank = battleData.userTank;
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (battleData.userTank.destroyed) return;
                double x = e.getX() - 9;
                double y = e.getY() - 38;
                tank.turretDir = Coordinate.getDirectionBetweenPoints(tank.x, tank.y, x, y) + 90;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (tank.destroyed) {
                    tank.moving = false;
                    return;
                }
                Bullet bullet = new Bullet(tank, 200);
                battleData.elements.add(bullet);
            }
        };

        content.addMouseListener(adapter);
        content.addMouseMotionListener(adapter);
        content.addMouseWheelListener(adapter);

        content.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (tank.destroyed) {
                    tank.moving = false;
                    return;
                }

                switch (e.getKeyChar()) {
                    case 'w':
                    case 'W':
                        tank.direction = Directions.UP.getAngleValue();
                        tank.moving = true;
                        break;
                    case 'a':
                    case 'A':
                        tank.direction = Directions.LEFT.getAngleValue();
                        tank.moving = true;
                        break;
                    case 's':
                    case 'S':
                        tank.direction = Directions.DOWN.getAngleValue();
                        tank.moving = true;
                        break;
                    case 'd':
                    case 'D':
                        tank.direction = Directions.RIGHT.getAngleValue();
                        tank.moving = true;
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
                        tank.moving = false;
                        break;
                }
            }
        });

        this.start();
    }

    /**
     * 线程任务
     */
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
                data.runEnemyTank(view.width, view.height);

                data.updatePositions(dt);

                // 碰撞检测
                data.collisionDetection();

                // 依据元素的状态，更新数据区
                data.updateDataset();

                // 刷新界面
                view.update(dt);
            }
        }
    }
}