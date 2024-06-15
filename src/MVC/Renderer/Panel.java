package MVC.Renderer;

import MVC.Model.Battle;
import MVC.View;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
    private Battle battle;

    private double frameRate = 0.0;

    public void setWarData(Battle battle) {
        this.battle = battle;
    }

    public void setFrameRate(double frameRate) {
        this.frameRate = frameRate;
    }

    public void paint(Graphics graphics) {
        super.paint(graphics);

        Graphics2D graphics2D = (Graphics2D) graphics;

        // 令每个游戏元素节点自我绘制
        if (battle != null && !battle.views.isEmpty()) {
            for (View view : battle.views) view.draw(graphics2D);
        }

        // 显示帧率
        String frameString = String.format("fps:%.2f", frameRate);
        graphics2D.drawString(frameString, 10, 15);
    }
}