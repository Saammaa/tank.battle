package cn.edu.ncepu.sa.Model;

import java.awt.*;

/**
 * 地图元素类
 */
public class Element implements IElement {

    /**
     * 位置x
     */
    public double x;

    /**
     * 位置 y
     */
    public double y;


    int width;
    int height;

    public Element() {
    }

    /**
     * @param dt
     */
    public void update(float dt) {

    }


    /**
     * 在地图上绘制该元素
     *
     * @param g
     */
    @Override
    public void draw(Graphics2D g) {

    }

    /**
     * 向某方向移动一段距离
     *
     * @param dir 方向
     * @param len 距离
     */
    public void move(double dir, double len) {
        x = x + len * Math.cos((dir - 90) * Math.PI / 180);
        y = y + len * Math.sin((dir - 90) * Math.PI / 180);

    }

    /**
     * 计算两个结点的距离
     *
     * @param target 目标元素
     * @return 距离
     */
    public double distance(Element target) {
        // this,target
        double a = this.x - target.x;
        double b = this.y - target.y;
        return Math.sqrt(a * a + b * b);

    }

    /*
     * 指定中心点获取矩形
     */
    public Rectangle getRect() {
        return new Rectangle((int) (x - (width / 2)), (int) y - (height / 2), (int) width, (int) height);
    }
}
