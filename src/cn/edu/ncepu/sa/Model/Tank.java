package cn.edu.ncepu.sa.Model;

import java.awt.*;

/**
 * 坦克类
 */
public class Tank extends Element {
    /**
     * 坦克方向
     */
    public double dir = 90;

    /**
     * 炮筒方向
     */
    public double turretDir;

    /**
     * 是否在移动
     */
    public boolean moving = false;

    /**
     * 每秒移动速度,注意要比子弹慢一些
     */
    public double speed = 100;

    /**
     * 生命数，装甲
     */
    public double hp = 60;
    public double hpmax = 100;

    /**
     * 每秒回复生命
     */
    public double hp_recovery_per_sec = 0.1;

    /**
     * 队伍，1红，2蓝
     */
    public int team = 1;


    public Tank() {

    }

    /**
     * 构造坦克
     *
     * @param x                   x坐标
     * @param y                   y坐标
     * @param dir                 方向
     * @param hp                  初始血量
     * @param hp_recovery_per_sec 每秒恢复血量
     * @param team                组别
     */
    public Tank(int x, int y, double dir, double hp, double hp_recovery_per_sec, int team) {
        this.x = x;
        this.y = y;

        this.dir = dir;
        this.speed = speed;
        this.hp = hp;
        this.hp_recovery_per_sec = hp_recovery_per_sec;
        this.team = team;
    }

    /**
     * 此坦克受到伤害
     */
    public void damage(double val) {
        this.hp -= val;
        if (this.hp <= 0) {
            System.out.println("坦克销毁");
            this.destroy();
        }
    }

    /**
     * 更新坦克位置
     *
     * @param timeFlaps 流逝时间间隔
     */
    public void update(double timeFlaps) {
        //生命回复
        recoverLife();

        //更新坦克位置
        if (moving) {
            double len = speed * timeFlaps;
            this.move(dir, len);
        }
    }

    /**
     * 定时自动回血
     */
    public void recoverLife() {
        hp += hp_recovery_per_sec;
        if (hp > hpmax) {
            hp = hpmax;
        }
    }


    @Override
    public void draw(Graphics2D g2) {
        //System.out.println("画:"+x+","+y);
        Image img1 = null;
        Image img2 = null;
        if (team == 1) {
            img1 = ImageCache.get("tank_red");
            img2 = ImageCache.get("turret_red");
        }
        if (team == 2) {
            img1 = ImageCache.get("tank_blue");
            img2 = ImageCache.get("turret_blue");
        }
        Graphics2D g = (Graphics2D) g2.create();//复制画笔
        g.translate(x, y);
        //绘制坦克身体
        g.rotate(Math.toRadians(dir));
        g.drawImage(img1, -18, -19, null);
        g.rotate(Math.toRadians(-dir));

        g.drawRect(-22, -34, 44, 8);
        g.setColor(Color.RED);
        int whp = (int) (43.08 * (hp / hpmax));
        g.fillRect(-21, -33, whp, 7);
        g.rotate(Math.toRadians(this.turretDir));
        g.drawImage(img2, -32, -32, null);
    }
}
