package MVC.Model;

import Entity.Bullet;
import Entity.Tank;
import Entity.Team;
import Utilities.Coordinate;

import java.util.HashSet;

public class BattleData {
	public HashSet<Element> elements = new HashSet<>();
	public Tank userTank = new Tank(600, 200, 0, 110, 0.5, Team.RED.ordinal());

	public BattleData() {
		elements.add(userTank);
		AddSomeEnemyTanks();
	}

	/**
	 * 增加一辆敌方坦克。
	 *
	 * @param x               x
	 * @param y               y
	 * @param direction       方向
	 * @param hp              初始血量
	 * @param hpRecoverySpeed 修复血量
	 * @param team            分组
	 */
	public void addSingleEnemy(
			int x,
			int y,
			double direction,
			double hp,
			double hpRecoverySpeed,
			int team
	) {
		Tank enemyTank = new Tank(x, y, direction, hp, hpRecoverySpeed, team);

		// 默认为移动状态
		enemyTank.moving = true;
		elements.add(enemyTank);
	}

	/**
	 * 构造敌方坦克。
	 */
	public void AddSomeEnemyTanks() {
		// 构造一些敌方坦克
		addSingleEnemy(0, 500, 0, 200, 0.1, Team.BLUE.ordinal());
		addSingleEnemy(500, 0, 0, 200, 0.1, Team.BLUE.ordinal());
		addSingleEnemy(600, 600, 0, 200, 0.1, Team.BLUE.ordinal());
		addSingleEnemy(600, 200, 0, 200, 0.1, Team.BLUE.ordinal());
		addSingleEnemy(500, 300, 0, 200, 0.1, Team.BLUE.ordinal());

	}

	/**
	 * 敌方坦克动起来，请尝试修改为每个坦克独立线程控制，自主活动
	 */
	public void runEnemyTank(int viewWidth, int viewHeight) {
		if (userTank.destroyed) return;

		for (Element element : elements) {
			// 找坦克
			if (element instanceof Tank) {
				// 找敌方坦克
				if (((Tank) element).team == Team.BLUE.ordinal()) {
					Tank t = (Tank) element;

					// 防止跑出地图
					if (t.x < 0) {
						t.direction = Directions.RIGHT.getAngleValue();
					}
					if (t.y < 0) {
						t.direction = Directions.DOWN.getAngleValue();
					}
					if (t.x >= viewWidth) {
						t.direction = Directions.LEFT.getAngleValue();
					}
					if (t.y >= viewHeight) {
						t.direction = Directions.UP.getAngleValue();
					}

					// 运动几步随机开炮，50 应设置为参数或者常量
					if (t.moveSteps > 50) {
						// 方向随机
						double random = Math.random() * 360;
						t.direction = random;
						t.turretDir = random;
						t.moving = true;
						t.moveSteps = 0;

						// 如果我方坦克进入射程，800 应该设置为常量
						if (t.distance(userTank) < 800) {
							// 自动瞄准
							t.turretDir = Coordinate.getDirectionBetweenPoints(t.x, t.y, userTank.x, userTank.y) + 90;

							// 开炮
							Bullet bullet = new Bullet(t, 200);
							elements.add(bullet);
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
		for (Element element : elements) {
			element.update(timeFlaps);
		}
	}

	/**
	 * 碰撞检测
	 */
	public void collisionDetection() {
		// 遍历每一个子弹
		for (Element shot : elements) {
			if (shot instanceof Bullet) {
				// 寻找每一辆坦克
				for (Element tank : elements) {
					// 进行敌我识别
					if ((tank instanceof Tank) && (tank != ((Bullet) shot).tank)) {
						// 距离过近，则认为打中，20 应设置为常量
						if (shot.distance(tank) < 20) {
							// 使坦克受到伤害
							((Tank) tank).damage(((Bullet) shot).damage);
							// 销毁当前子弹
							shot.destroy();
						}
					}
				}
			}
		}
	}

	public void updateDataset() {
		elements.removeIf(nextEl -> nextEl.destroyed && nextEl != userTank);
	}
}
