package cn.edu.ncepu.sa.GameView;

import javax.swing.*;

public class GameView extends JFrame {

    GamePanel panel = new GamePanel();

    public GameView() {
        this.setSize(860, 640);
        this.setLocationRelativeTo(null);
        //this.setResizable(false);
        this.setTitle("坦克大战 V1.0");
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);

        int w = this.getContentPane().getSize().width;
        int h = this.getContentPane().getSize().height;
        //System.out.println(w);
        //System.out.println(h);
        int cw = 860 - w;
        int ch = 640 - h;
        this.setSize(860 + cw, 640 + ch);
    }

    static int _frames = 0;//累计帧数
    static float _dt = 0;//累计时长
    static float _frameRate = 0.0f;//帧率

    public void update(float dt) {

        _frames++;
        _dt += dt;
        if (_dt >= 1.0f) {
            _frameRate = _frames / _dt;
            _frames = 0;
            _dt = 0f;
        }

        panel.repaint();
    }
}
