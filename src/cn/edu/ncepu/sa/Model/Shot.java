package cn.edu.ncepu.sa.Model;

import java.awt.*;

public class Shot extends Element {
    public double dir;//移动方向
    public double speed = 200.0;//移动速度（秒）
    public Tank tank;//打出这个子弹的坦克

    public Shot(Tank tank, double speed) {
        //super();//会默认调用父类的无参构造
        this.tank = tank;
        this.x = tank.x;
        this.y = tank.y;
        this.dir = tank.turretDir;
        this.speed = speed;
        //Scheduler.getInstance().scheduleUpdate(this,0,false);
    }

    //每一帧都会执行
    public void update(float dt) {
        //计算新坐标
        double len = speed * dt;
        this.move(dir, len);

        //超出范围的无效子弹
        if (x < -100 || x > 2000 || y < -100 || y > 2000) {
            this.destroy();
        }
    }



   /* public void destroy(){
        Director.nodes.remove(this);
        Scheduler.getInstance().unscheduleUpdate(this);
    }*/

    @Override
    public void draw(Graphics2D g2) {
        Graphics2D g = (Graphics2D) g2.create();//复制画笔
        g.translate(x, y);
        g.drawImage(ImageCache.get("shot"), -6, -6, null);
    }
}
