package cn.edu.ncepu.sa.Model;

import cn.edu.ncepu.sa.utils.Utils;

import java.util.HashSet;
import java.util.Iterator;

/**
 * 数据组件，除了引用传递还可以使用单例类
 */
public class WarData {

    public HashSet<Element> elements = new HashSet<>();
    public Tank userTank = new Tank(600, 200, 0, 110, 0.5, TankTeam.RED.ordinal());

    public WarData() {
        elements.add(userTank);

        AddSomeEnemyTanks();
    }

    public void AddSomeEnemyTanks() {
// 建议写法
        Tank t = new Tank(0, 500, 0, 200, 0.1, TankTeam.BLUE.ordinal());
        t.moving = true;
        elements.add(t);

        Tank t2 = new Tank(500, 0, 0, 200, 0.1, TankTeam.BLUE.ordinal());
        t2.moving = true;
        elements.add(t2);

        Tank t3 = new Tank(600, 600, 0, 200, 0.1, TankTeam.BLUE.ordinal());
        t3.moving = true;
        elements.add(t3);

        Tank t4 = new Tank(600, 200, 0, 200, 0.1, TankTeam.BLUE.ordinal());
        t4.moving = true;
        elements.add(t4);

        Tank t5 = new Tank(500, 300, 0, 200, 0.1, TankTeam.BLUE.ordinal());
        t5.moving = true;
        elements.add(t5);
    }

    /**
     * 敌方坦克动起来
     */
    public void runEnemyTank(int viewWidth, int viewHeight) {
        if (userTank.Destroyed) {
            return;
        }

        for (Element elemnet : elements) {
            // 找坦克
            if (elemnet instanceof Tank) {
                // 找敌方坦克
                if (((Tank) elemnet).team == TankTeam.BLUE.ordinal()) {
                    Tank t = (Tank) elemnet;
                    if (t.x < 0) {
                        t.dir = Directions.RIGHT.getAngleValue();
                    }
                    if (t.y < 0) {
                        t.dir = Directions.DOWN.getAngleValue();
                    }
                    if (t.x >= viewWidth) {
                        t.dir = Directions.LEFT.getAngleValue();
                    }
                    if (t.y >= viewHeight) {
                        t.dir = Directions.UP.getAngleValue();
                    }
                    if (t.moveSteps > 50) {
                        double random = Math.random() * 360;
                        t.dir = random;
                        t.turretDir = random;
                        t.moving = true;
                        t.moveSteps = 0;
                        if (t.distance(userTank) < 800) {
                            t.turretDir = Utils.ppDir(t.x, t.y, userTank.x, userTank.y) + 90;
                            Shot shot = new Shot(t, 200);
                            elements.add(shot);
                            return;
                        }
                    }
                }
            }
        }
    }

    /**
     * 更新坦克的位置
     *
     * @param timeFlaps
     */
    public void updatePositions(double timeFlaps) {
        for (Element elemnet : elements) {
            elemnet.update(timeFlaps);
        }

    }

    /**
     * 碰撞检测
     */
    public void CollisionDetection() {
        //遍历每一个子弹
        for (Element shot : elements) {
            if (shot instanceof Shot) {
                // 寻找每一辆坦克
                for (Element tank : elements) {
                    //进行敌我识别
                    if ((tank instanceof Tank) && (tank != ((Shot) shot).tank)) {
                        // 距离过近，则认为打中
                        if (shot.distance(tank) < 20) {
                            ((Tank) tank).damage(((Shot) shot).damage); //使坦克受到伤害
                            shot.destroy(); //销毁当前子弹
                        }
                    }
                }
            }
        }
    }

    /**
     * 依据元素的状态，处理是否还保留在数据区中
     */
    public void updateDataSet() {
        // 删除中间的元素会有问题
        /*for (Element element : elements) {
            if (element.Destroyed) {
                elements.remove(element);
            } else {

            }
        }*/

        Iterator<Element> it = elements.iterator();
        while (it.hasNext()) {
            Element tmp = it.next();
            if (tmp.Destroyed) {
                if (tmp != userTank) {
                    it.remove();
                }
            }
        }
    }
}
