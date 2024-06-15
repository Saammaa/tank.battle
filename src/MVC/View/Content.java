package MVC.View;

import javax.swing.*;
import MVC.Model.BattleData;

public class Content extends JFrame {
    BattleData battleData;

    Panel panel = new Panel();

    public int width = 860;
    public int height = 640;

    /**
     * 初始化显示组件
     *
     * @param battleData 数据区引用
     */
    public Content(BattleData battleData) {
        this.battleData = battleData;
        panel.setWarData(battleData);

        // 初始化窗口
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setTitle("坦克大战");
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);

        int contentPaneWidth    = this.getContentPane().getSize().width;
        int contentPanelHeight  = this.getContentPane().getSize().height;

        int cw = width - contentPaneWidth;
        int ch = height - contentPanelHeight;
        this.setSize(width + cw, height + ch);
    }

    // 帧数，自主选择是否显示
    private int _frames = 0;
    // 累计时长
    private float _dt = 0;
    // 帧率
    private float _frameRate = 0.0f;

    /**
     * 更新画板。
     *
     * @param timeFlaps 流逝时间
     */
    public void update(double timeFlaps) {
        _frames++;
        _dt += (float) timeFlaps;

        if (_dt >= 1.0f) {
            _frameRate = _frames / _dt;
            _frames = 0;
            _dt = 0f;
        }

        panel.setFrameRate(_frameRate);
        panel.repaint();
    }
}
