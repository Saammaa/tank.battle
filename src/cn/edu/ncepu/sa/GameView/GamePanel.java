package cn.edu.ncepu.sa.GameView;

import cn.edu.ncepu.sa.Model.Element;
import cn.edu.ncepu.sa.Model.WarData;
import cn.edu.ncepu.sa.Model.WarDataSingleton;

import javax.swing.*;
import java.awt.*;

/**
 * 游戏画板
 */
public class GamePanel extends JPanel {
    /**
     * 数据区引用,
     * 放到参数区也可以
     */
    private WarData warData;

    /**
     * 游戏帧率
     */
    private double frameRate = 0.0;

    /**
     * 初始化数据引用
     *
     * @param warData 注意是引用传递
     */
    public void setWarData(WarData warData) {
        this.warData = warData;

        // 单例类的用法
        // this.warData = WarDataSingleton.getInstance();
    }

    public void setFrameRate(double frameRate) {
        this.frameRate = frameRate;
    }


    public void paint(Graphics g) {
        super.paint(g);//保留原来的paint，g相当于画笔
        Graphics2D g2 = (Graphics2D) g;

        // 绘制每一个游戏元素
        if (warData != null && warData.elements.size() > 0) {
            for (Element element : warData.elements) {
                element.draw(g2);//让每个节点都自我绘制
            }
        }

        // 显示帧率
        String str = String.format("fps:%.2f", frameRate);
        g.drawString(str, 10, 15);

    }
}
