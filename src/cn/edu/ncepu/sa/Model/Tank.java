package cn.edu.ncepu.sa.Model;

import java.awt.*;

public class Tank extends Element {
    /**
     * 坦克方向
     */
    public double dir = 90;
    public double turretDir;//炮筒方向
    public boolean moving = false;//是否在移动
    public double speed = 100;//每秒移动速度

    public double hp = 60;
    public double hpmax = 100;
    public double hphf = 1;//每秒回复生命
    public int team = 1; //队伍，1红，2蓝

    public Tank() {

        //Scheduler.getInstance().scheduleUpdate(this,0,false);
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


    public void update(float dt) {
        //生命回复
        hp += (hphf * dt);
        if (hp > hpmax) {
            hp = hpmax;
        }

        //更新坦克位置
        if (moving) {
            double len = speed * dt;
            this.move(dir, len);
        }
    }


    public void recoverLife(float dt) {
        hp += 5;
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
