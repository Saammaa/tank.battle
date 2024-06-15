package MVC.View;

import MVC.Model.Element;
import MVC.Model.BattleData;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
    private BattleData battleData;

    private double frameRate = 0.0;

    public void setWarData(BattleData battleData) {
        this.battleData = battleData;
    }

    public void setFrameRate(double frameRate) {
        this.frameRate = frameRate;
    }

    public void paint(Graphics graphics) {
        super.paint(graphics);

        Graphics2D graphics2D = (Graphics2D) graphics;

        // 令每个游戏元素节点自我绘制
        if (battleData != null && !battleData.elements.isEmpty()) {
            for (Element element : battleData.elements) element.draw(graphics2D);
        }

        // 显示帧率
        String frameString = String.format("fps:%.2f", frameRate);
        graphics2D.drawString(frameString, 10, 15);
    }
}