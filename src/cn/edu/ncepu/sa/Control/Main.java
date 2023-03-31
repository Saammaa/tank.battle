package cn.edu.ncepu.sa.Control;

import cn.edu.ncepu.sa.GameView.GameView;
import cn.edu.ncepu.sa.Model.Tank;
import cn.edu.ncepu.sa.Model.WarData;

/**
 * 主程序入口
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        //构造系统资源

        // 构造数据组件
        WarData warData = new WarData();

        //构造显示组件，并告知显示组件要显示的数据
        GameView win = new GameView(warData);

        // 构造控制器组件
        WarControl warControl = new WarControl();

        // 依据用户输入刷新显示
        warControl.StartWar(win, warData);
    }
}