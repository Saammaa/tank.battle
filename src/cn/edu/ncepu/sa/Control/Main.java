package cn.edu.ncepu.sa.Control;

import cn.edu.ncepu.sa.GameView.GameView;
import cn.edu.ncepu.sa.Model.Tank;
import cn.edu.ncepu.sa.Model.WarData;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        WarData warData = new WarData();

        GameView win = new GameView(warData);

        WarControl warControl = new WarControl();

        warControl.StartWar(win, warData);
    }
}