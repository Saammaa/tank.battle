package cn.edu.ncepu.sa;

import cn.edu.ncepu.sa.GameView.GameView;
import cn.edu.ncepu.sa.Model.Tank;
import cn.edu.ncepu.sa.Model.WarData;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        GameView win = new GameView();

        Tank t = new Tank();
        t.team = 2;
        t.x = 500;
        t.y = 500;
        WarData.elements.add(t);

        Tank tank = WarData.userTank;
        tank.x = 200;
        tank.y = 200;
        tank.hp = 10;
        tank.hpmax = 100;
        tank.hphf = 10;//每秒回复
    }
}