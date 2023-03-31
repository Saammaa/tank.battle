package cn.edu.ncepu.sa.Model;

import java.awt.*;

/**
 * 地图元素接口
 */
public interface IElement {
    /**
     * 绘制图元
     *
     * @param g
     */
    public void draw(Graphics2D g);

    /**
     * 回收资源
     */
    public void destroy();
}
