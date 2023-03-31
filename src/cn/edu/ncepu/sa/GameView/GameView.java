package cn.edu.ncepu.sa.GameView;

import cn.edu.ncepu.sa.Model.WarData;

import javax.swing.*;

/**
 * 游戏窗口
 */
public class GameView extends JFrame {
    /**
     * 数据区引用
     */
    WarData warData;

    /**
     * 游戏画板
     */
    GamePanel panel = new GamePanel();

    /**
     * 初始化显示组件
     *
     * @param warData 数据区引用
     */
    public GameView(WarData warData) {
        this.warData = warData;
        panel.setWarData(warData);

        // 窗体初始化和
        this.setSize(860, 640);
        this.setLocationRelativeTo(null);
        //this.setResizable(false);
        this.setTitle("坦克大战 V1.0");
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);

        int w = this.getContentPane().getSize().width;
        int h = this.getContentPane().getSize().height;

        int cw = 860 - w;
        int ch = 640 - h;
        this.setSize(860 + cw, 640 + ch);
    }

    // 私有数据区

    private int _frames = 0;//累计帧数，自主选择是否显示
    private float _dt = 0;//累计时长
    private float _frameRate = 0.0f;//帧率

    /**
     * 更新画板
     *
     * @param timeFlaps 流逝时间
     */
    public void update(double timeFlaps) {

        _frames++;
        _dt += timeFlaps;
        if (_dt >= 1.0f) {
            _frameRate = _frames / _dt;
            _frames = 0;
            _dt = 0f;
        }
        panel.setFrameRate(_frameRate);
        panel.repaint();
    }
}
