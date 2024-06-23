package renderer;

import mvc.Entity;
import engine.entity.*;
import data.Battleground;

import java.awt.*;
import javax.swing.*;

/**
 * 游戏主体渲染区画布。
 */
public class GamePanel extends JPanel {
    private Battleground battleData;

    private double frameRate = 0.0;

    public void loadBattlegroundData( Battleground battleData ) {
        this.battleData = battleData;
    }

    public void setFrameRate( double frameRate ) {
        this.frameRate = frameRate;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2D = (Graphics2D) g;

        if (battleData != null) {
            for (Entity block : battleData.blocks) {
                if (block != null && block.view != null) {
                    block.view.draw(graphics2D);
                }
            }

            for (Tank tank : battleData.tanks) tank.view.draw(graphics2D);
            for (Bullet bullet : battleData.bullets) bullet.view.draw(graphics2D);
        }

        String frameString = String.format("FPS: %.2f", frameRate);
        graphics2D.drawString(frameString, 10, 15);
    }
}