package MVC.Renderer;

import Entity.Bullet;
import Entity.Tank;
import MVC.Model.Battleground;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
    private Battleground battle;

    private double frameRate = 0.0;

    public void setWarData(Battleground battle) {
        this.battle = battle;
    }

    public void setFrameRate(double frameRate) {
        this.frameRate = frameRate;
    }

    public void paint(Graphics graphics) {
        super.paint(graphics);

        Graphics2D graphics2D = (Graphics2D) graphics;

        // 令每个游戏元素节点自我绘制
        if (battle != null) {
            for (Tank tank : battle.tanks) tank.view.draw(graphics2D);
            for (Bullet bullet : battle.bullets) bullet.view.draw(graphics2D);
        }

        // 显示帧率
        String frameString = String.format("fps:%.2f", frameRate);
        graphics2D.drawString(frameString, 10, 15);
    }
}