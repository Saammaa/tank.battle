package cn.edu.ncepu.sa.GameView;

import cn.edu.ncepu.sa.Model.Element;
import cn.edu.ncepu.sa.Model.WarData;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    public void paint(Graphics g) {
        super.paint(g);//保留原来的paint，g相当于画笔
        Graphics2D g2 = (Graphics2D) g;
/*            g.drawString("坦克大战", 100, 50);
            g.drawRect(0, 0, 4, 4);
            g.drawRect(860 - 4, 640 - 4, 4, 4);

            ImageIcon icon = new ImageIcon("assets/tank/bodyu2.png");
            g.drawImage(ImageCache.get("tank_red"), 200 - 18, 200 - 19, null);

            //ImageIcon icon2 = new ImageIcon("assets/tank/turret2_1.png");
            //g.drawImage(icon2.getImage(), 200-32,200-32,null);

            BufferedImage buff = ImageCache.get("turret_red");
            //buff = Utils.rotateImage(buff,90);
            //g.drawImage(buff, 200-32,200-32,null);


            g2.translate(200, 200);
            g2.rotate(Math.toRadians(90));
            g2.drawImage(buff, -32, -32, null);
*/
        //tank.draw(g);
        //shot.draw(g);


        for (Element element : WarData.elements) {
            element.draw(g2);//让每个节点都自我绘制
        }


        String str = String.format("fps:%.2f", 100.0/*_frameRate*/);
        g.drawString(str, 10, 15);

    }
}
