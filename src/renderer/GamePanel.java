package renderer;

import mvc.Entity;
import engine.entity.*;
import data.Battleground;

import javax.swing.*;
import java.awt.*;

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

    public void paint( Graphics graphics ) {
        super.paint( graphics );

        Graphics2D graphics2D = (Graphics2D) graphics;

        // 令每个游戏元素节点自我绘制
        if ( battleData != null ) {
            // TODO：形参化类的原始使用。避免出现此情况。
            for ( Entity block: battleData.blocks ) {
                if ( block != null && block.view != null ) {
                    block.view.draw(graphics2D);
                }
            }

            for ( Tank tank : battleData.tanks ) tank.view.draw(graphics2D);
            for ( Bullet bullet : battleData.bullets ) bullet.view.draw(graphics2D);
        }

        // 显示帧率
        String frameString = String.format("FPS: %.2f", frameRate);
        graphics2D.drawString(frameString, 10, 15);
    }
}