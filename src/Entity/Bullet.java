package Entity;

import MVC.Model.Element;
import MVC.View.Image;

import java.awt.*;

public class Bullet extends Element {
    // 移动方向
    public double direction;

    // 移动速度（秒）
    public double speed;

    // 子弹伤害
    public double damage = 20.0;

    // 子弹发出者
    public Tank tank;

    /**
     * 构造函数
     *
     * @param tank  发射子弹的坦克
     * @param speed 子弹速度
     */
    public Bullet(Tank tank, double speed) {
        this.tank = tank;
        this.x = tank.x;
        this.y = tank.y;
        this.direction = tank.turretDir;
        this.speed = speed;
    }

    /**
     * 于每一帧更新子弹位置。
     */
    public void update(double timeFlaps) {
        // 计算新坐标
        double len = speed * timeFlaps;
        this.move(direction, len);

        // 超出范围的无效子弹
        if (x < -100 || x > 2000 || y < -100 || y > 2000) {
            this.destroy();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        Graphics2D g = (Graphics2D) g2.create();
        g.translate(x, y);
        g.drawImage(Image.get("shot"), -6, -6, null);
    }
}