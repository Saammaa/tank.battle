package renderer;

import engine.entity.*;
import data.Battleground;

import javax.swing.*;
import java.awt.*;

/**
 * 游戏主体渲染区画布。
 */
public class GamePanel extends JPanel {
    private Battleground battleground;

    private double frameRate = 0.0;

    public void loadBattlegroundData( Battleground battleground ) {
        this.battleground = battleground;
    }

    public void setFrameRate( double frameRate ) {
        this.frameRate = frameRate;
    }

    public void paint( Graphics graphics ) {
        super.paint( graphics );

        Graphics2D graphics2D = (Graphics2D) graphics;

        // 令每个游戏元素节点自我绘制
        if ( battleground != null ) {
            for ( Tank tank : battleground.tanks ) tank.view.draw(graphics2D);
            for ( Bullet bullet : battleground.bullets ) bullet.view.draw(graphics2D);
        }

        // 显示帧率
        String frameString = String.format("fps:%.2f", frameRate);
        graphics2D.drawString(frameString, 10, 15);
    }
}