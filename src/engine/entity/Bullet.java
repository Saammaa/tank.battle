package engine.entity;

import mvc.Entity;
import engine.view.BulletView;

public class Bullet extends Entity<BulletView> {
    public Tank shooterTank;

    /**
     * 构造函数。
     *
     * @param shooterTank  发射该子弹的父坦克对象
     * @param speed         子弹速度
     */
    public Bullet(Tank shooterTank, double speed) {
        this.type = "projectile";

        // 初始化伤害与速度属性
        this.speed = speed;
        this.damage = 20.0;

        // 初始化方向与生成子弹的对象
        this.shooterTank = shooterTank;
        this.direction = shooterTank.turretDirection;

        // 初始化子弹视图
        this.view = new BulletView(
                this,
                shooterTank.view.x,
                shooterTank.view.y,
                this.direction,
                this.speed
        );
    }
}