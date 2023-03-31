package cn.edu.ncepu.sa.Control;

import cn.edu.ncepu.sa.GameView.GameView;
import cn.edu.ncepu.sa.Model.Element;
import cn.edu.ncepu.sa.Model.Shot;
import cn.edu.ncepu.sa.Model.Tank;
import cn.edu.ncepu.sa.Model.WarData;
import cn.edu.ncepu.sa.utils.Utils;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WarControl extends Thread {

    GameView win;

    WarData warData;

    public WarControl() {

    }

    public void StartWar(GameView win, WarData warData) {
        this.win = win;
        this.warData = warData;

        Tank t = new Tank();
        t.team = 2;
        t.x = 500;
        t.y = 500;
        warData.elements.add(t);

        Tank tank = warData.userTank;
        tank.x = 200;
        tank.y = 200;
        tank.hp = 10;
        tank.hpmax = 100;
        tank.hphf = 10;//每秒回复

        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
//                System.out.println(e.getX()+","+e.getY());
                double x = e.getX() - 9;
                double y = e.getY() - 38;
                tank.turretDir = Utils.ppDir(tank.x, tank.y, x, y) + 90;
            }

            @Override
            public void mousePressed(MouseEvent e) {
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
                switch (e.getKeyChar()) {
                    case 'w':
                    case 'W':
                        tank.dir = 0;
                        tank.moving = true;
                        break;
                    case 'a':
                    case 'A':
                        tank.dir = 270;
                        tank.moving = true;
                        break;
                    case 's':
                    case 'S':
                        tank.dir = 180;
                        tank.moving = true;
                        break;
                    case 'd':
                    case 'D':
                        tank.dir = 90;
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

    private void CollisionDetection() {
        for (Element shot : warData.elements) {
            if (shot instanceof Shot) {
                for (Element tank : warData.elements) {
                    //如果node是坦克，且不是这个子弹的主人
                    if (tank instanceof Tank && tank != shot) {
                        if (shot.distance(tank) < 20) {
                            Tank t = (Tank) tank;
                            t.damage(10); //使坦克受到伤害
                            shot.destroy(); //销毁当前子弹
                        }
                    }
                }
            }
        }
    }

    @Override
    public void run() {
        super.run();

        long lastUpdate = System.currentTimeMillis();//当前系统时间
        int fps = 60;//理论帧数
        while (true) {
            long interval = 1000 / 60;//理论间隔
            long curr = System.currentTimeMillis();
            long _time = curr - lastUpdate;
            if (_time < interval) {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {

                }

            } else {
                lastUpdate = curr;
                float dt = _time * 0.001f;
                //推动调度器
                CollisionDetection();
                //Scheduler.getInstance().tick(dt);  //两帧之间的间隔 （秒）
                win.update(dt);
            }

        }
    }
}
