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
        // 构造我方坦克
        elements.add(userTank);

        // 构造敌方坦克
        AddSomeEnemyTanks();
    }


    /**
     * 增加一辆敌方坦克
     *
     * @param x                   x
     * @param y                   y
     * @param dir                 方向
     * @param hp                  初始血量
     * @param hp_recovery_per_sec 修复血量
     * @param team                分组
     */
    public void AddAEnemyTank(int x, int y, double dir, double hp, double hp_recovery_per_sec, int team) {
        Tank t = new Tank(x, y, dir, hp, hp_recovery_per_sec, team);
        t.moving = true;    // 默认是移动状态
        elements.add(t);
    }

    /**
     * 构造敌方坦克，之后要依据配置/地图来构造tank
     */
    public void AddSomeEnemyTanks() {
        // 构造一些敌方坦克
        AddAEnemyTank(0, 500, 0, 200, 0.1, TankTeam.BLUE.ordinal());
        AddAEnemyTank(500, 0, 0, 200, 0.1, TankTeam.BLUE.ordinal());
        AddAEnemyTank(600, 600, 0, 200, 0.1, TankTeam.BLUE.ordinal());
        AddAEnemyTank(600, 200, 0, 200, 0.1, TankTeam.BLUE.ordinal());
        AddAEnemyTank(500, 300, 0, 200, 0.1, TankTeam.BLUE.ordinal());

    }

    /**
     * 敌方坦克动起来，请尝试修改为每个坦克独立线程控制，自主活动
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

                    // 防止跑出地图
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

                    // 运动几步随机开炮，50应该设置为参数或者常量
                    if (t.moveSteps > 50) {
                        // 方向随机
                        double random = Math.random() * 360;
                        t.dir = random;
                        t.turretDir = random;
                        t.moving = true;
                        t.moveSteps = 0;

                        // 如果我方坦克进入射程，800应该设置为常量
                        if (t.distance(userTank) < 800) {
                            // 自动瞄准
                            t.turretDir = Utils.ppDir(t.x, t.y, userTank.x, userTank.y) + 90;

                            // 开炮
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
     * @param timeFlaps 运行时间间隔
     */
    public void updatePositions(double timeFlaps) {
        // 所有元素依据流逝时间更新状态
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
                        // 距离过近，则认为打中，20应该设置为常量
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

        // 请大家思考，为什么要采取这种方法
        Iterator<Element> it = elements.iterator();
        while (it.hasNext()) {
            Element tmp = it.next();
            if (tmp.Destroyed) {
                if (tmp != userTank) {
                    it.remove();
                }
            }
        }
        // 上文方法依然存在数据并行操作问题，如何加锁？
    }
}
