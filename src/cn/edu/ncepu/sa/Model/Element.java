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

    /**
     * 宽度
     */
    public int width = 10;

    /**
     * 高度
     */
    public int height = 10;

    /**
     * 是否需要销毁，False：显示； True：删除
     */
    public boolean Destroyed = false;

    public Element() {
    }

    /**
     * 使用子类的位置更新方法
     *
     * @param timeFlaps 流逝时间间隔
     */
    public void update(double timeFlaps) {

    }


    /**
     * 在地图上绘制该元素
     * 使用子类的绘制方法
     *
     * @param g
     */
    @Override
    public void draw(Graphics2D g) {

    }

    /**
     * 该元素生命周期结束，可以回收资源
     */
    public void destroy() {
        Destroyed = true;   // 这会触发数据区中删除，该工作在控制器中完成
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

    /**
     * 与另一个元素的夹角
     *
     * @param target 目标元素
     * @return
     */
    public double angle(Element target) {
        double len_x = target.x - x;
        double len_y = target.y - y;
        double radian = Math.atan2(len_y, len_x);//弧度
        return radian * 180 / Math.PI;//角度

    }

    /*
     * 指定中心点获取矩形，弃用，碰撞检测使用了距离法
     */
    public Rectangle getRect() {
        return new Rectangle((int) (x - (width / 2)), (int) y - (height / 2), (int) width, (int) height);
    }
}
