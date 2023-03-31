package cn.edu.ncepu.sa.Control;

import cn.edu.ncepu.sa.GameView.GameView;
import cn.edu.ncepu.sa.Model.*;
import cn.edu.ncepu.sa.utils.Utils;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 游戏控制器
 * 读取键盘、鼠标信息，控制主坦克
 * 线程后台刷新子弹、坦克位置
 * 线程后台计算碰撞检测
 */
public class WarControl extends Thread {

    /**
     * 显示组件引用
     */
    GameView win;

    /**
     * 数据组件引用
     */
    WarData warData;

    /**
     * 默认构造函数
     */
    public WarControl() {

    }

    /**
     * 初始化控制器
     *
     * @param win     显示组件引用
     * @param warData 数据组件引用
     */
    public void StartWar(GameView win, WarData warData) {
        this.win = win;
        this.warData = warData;

        /*Tank t = new Tank();  老式写法
        t.team = 2;
        t.hp = 10;
        t.x = 500;
        t.y = 500;
        t.hp_recovery_per_sec = 0.1;//每秒回复*/
        // 建议写法
        Tank t = new Tank(500, 500, 0, 50, 0.1, TankTeam.BLUE.ordinal());
        t.moving = true;
        warData.elements.add(t);

        Tank t2 = new Tank(300, 300, 0, 50, 0.1, TankTeam.BLUE.ordinal());
        t2.moving = true;
        warData.elements.add(t2);

        Tank tank = warData.userTank;
        /*tank.x = 200;
        tank.y = 200;
        tank.hp = 10;
        tank.hpmax = 100;
        tank.hp_recovery_per_sec = 0.1;//每秒回复*/

        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (warData.userTank.Destroyed) {
                    return;
                }
//                System.out.println(e.getX()+","+e.getY());
                double x = e.getX() - 9;
                double y = e.getY() - 38;
                tank.turretDir = Utils.ppDir(tank.x, tank.y, x, y) + 90;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (tank.Destroyed) {
                    tank.moving = false;
                    return;
                }
                Shot shot = new Shot(tank, 200);
                warData.elements.add(shot);
            }
        };

        win.addMouseListener(adapter); //点击事件
        win.addMouseMotionListener(adapter); //移动事件
        win.addMouseWheelListener(adapter); //滚轮事件

        win.addKeyListener(new KeyAdapter() {
            @Override //键盘按下
            public void keyPressed(KeyEvent e) {
                System.out.println(e.getKeyChar());
                if (tank.Destroyed) {
                    tank.moving = false;
                    return;
                }
                switch (e.getKeyChar()) {
                    case 'w':
                    case 'W':
                        tank.dir = Directions.UP.getAngleValue();
                        tank.moving = true;
                        break;
                    case 'a':
                    case 'A':
                        tank.dir = Directions.LEFT.getAngleValue();
                        tank.moving = true;
                        break;
                    case 's':
                    case 'S':
                        tank.dir = Directions.DOWN.getAngleValue();
                        tank.moving = true;
                        break;
                    case 'd':
                    case 'D':
                        tank.dir = Directions.RIGHT.getAngleValue();
                        tank.moving = true;
                        break;
                }
            }

            @Override //键盘抬起
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
     * 碰撞检测，在线程中运行
     */
    private void CollisionDetection() {
        //遍历每一个子弹
        for (Element shot : warData.elements) {
            if (shot instanceof Shot) {
                // 寻找每一辆坦克
                for (Element tank : warData.elements) {
                    //进行敌我识别
                    if ((tank instanceof Tank) && (tank != ((Shot) shot).tank)) {
                        // 距离过近，则认为打中
                        if (shot.distance(tank) < 20) {
                            ((Tank) tank).damage(((Shot) shot).damage); //使坦克受到伤害
                            shot.destroy(); //销毁当前子弹
                        }
                    }
                }
            }
        }
    }

    // 地方坦克动起来
    private void runEnemyTank() {
        if (warData.userTank.Destroyed) {
            return;
        }

        for (Element elemnet : warData.elements) {
            // 找坦克
            if (elemnet instanceof Tank) {
                // 找敌方坦克
                if (((Tank) elemnet).team == TankTeam.BLUE.ordinal()) {
                    Tank t = (Tank) elemnet;
                    if (t.x < 0) {
                        t.dir = Directions.RIGHT.getAngleValue();
                    }
                    if (t.y < 0) {
                        t.dir = Directions.DOWN.getAngleValue();
                    }
                    if (t.x >= win.width) {
                        t.dir = Directions.LEFT.getAngleValue();
                    }
                    if (t.y >= win.height) {
                        t.dir = Directions.UP.getAngleValue();
                    }
                    if (t.moveSteps > 50) {
                        double random = Math.random() * 360;
                        t.dir = random;
                        t.turretDir = random;
                        t.moving = true;
                        t.moveSteps = 0;
                        if (t.distance(warData.userTank) < 400) {
                            t.turretDir = Utils.ppDir(t.x, t.y, warData.userTank.x, warData.userTank.y) + 90;
                            Shot shot = new Shot(t, 200);
                            warData.elements.add(shot);
                            return;
                        }
                    }
                }
            }
        }
    }

    /**
     * 地图元素位置更新
     *
     * @param timeFlaps 时间间隔
     */
    private void updatePositions(double timeFlaps) {
        for (Element elemnet : warData.elements) {
            elemnet.update(timeFlaps);
        }

    }

    /**
     * 线程任务
     */
    @Override
    public void run() {
        super.run();

        long lastUpdate = System.currentTimeMillis();//当前系统时间
        int fps = 60;//理论帧数
        while (true) {
            long interval = 1000 / fps;//理论间隔
            long curr = System.currentTimeMillis();
            long _time = curr - lastUpdate;
            if (_time < interval) {
                // 不到刷新时间，休眠
                try {
                    Thread.sleep(1);
                } catch (Exception e) {

                }

            } else {
                // 更新游戏状态

                lastUpdate = curr;

                // 流逝时间
                float dt = _time * 0.001f;

                //调度任务，如果有些任务计算量大，可以开线程池
                runEnemyTank();
                updatePositions(dt);

                // 碰撞检测
                CollisionDetection();

                // 依据元素的状态，更新数据区
                warData.updateDataSet();

                // 刷新界面
                win.update(dt);
            }

        }
    }
}
