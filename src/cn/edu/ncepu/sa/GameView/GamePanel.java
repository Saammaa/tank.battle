package cn.edu.ncepu.sa.GameView;

import cn.edu.ncepu.sa.Model.Element;
import cn.edu.ncepu.sa.Model.WarData;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    WarData warData;

    public void setWarData(WarData warData) {
        this.warData = warData;
    }

    public void paint(Graphics g) {
        super.paint(g);//保留原来的paint，g相当于画笔
        Graphics2D g2 = (Graphics2D) g;

        if (warData != null) {
            for (Element element : warData.elements) {
                element.draw(g2);//让每个节点都自我绘制
            }
        }

        String str = String.format("fps:%.2f", 100.0/*_frameRate*/);
        g.drawString(str, 10, 15);

    }
}
