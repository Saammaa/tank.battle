package MVC.Model;

import View.*;
import Entity.*;

import MVC.View;
import Utilities.Coordinate;

import java.util.HashSet;

public class Battle {
	public HashSet<View> views = new HashSet<>();

	public HashSet<Tank> tanks = new HashSet<>();

	public HashSet<Bullet> bullets = new HashSet<>();

	public Tank userTank;

	public Battle() {
		this.userTank = new Tank(600, 200, 0, 110, 0.5, Team.RED.ordinal());

		views.add(userTank.view);
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
		enemyTank.setMoving(true);

		tanks.add(enemyTank);
		views.add(enemyTank.view);
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

	public void runEnemyTank(int viewWidth, int viewHeight) {
		if (userTank.view.destroyed) return;

		for (Tank tank : tanks) {
			if (tank.team == Team.BLUE.ordinal()) {
				TankView tankView = tank.view;

				// 防止跑出地图
				if (tankView.x < 0) {
					tankView.direction = Directions.RIGHT.getAngleValue();
				}
				if (tankView.y < 0) {
					tankView.direction = Directions.DOWN.getAngleValue();
				}
				if (tankView.x >= viewWidth) {
					tankView.direction = Directions.LEFT.getAngleValue();
				}
				if (tankView.y >= viewHeight) {
					tankView.direction = Directions.UP.getAngleValue();
				}

				// 运动几步随机开炮，50 应设置为参数或者常量
				if (tank.moveSteps > 50) {
					// 方向随机
					double random = Math.random() * 360;
					tankView.direction = random;
					tank.turretDirection = random;

					tank.setMoving(true);
					tank.moveSteps = 0;

					// 如果我方坦克进入射程，800 应该设置为常量
					if (tankView.getDistanceTo(userTank.view) < 800) {
						// 自动瞄准
						tank.turretDirection = Coordinate.getDirectionBetweenPoints(
								tankView.x, tankView.y, userTank.view.x, userTank.view.y
						) + 90;

						// 开炮
						Bullet bullet = new Bullet(tank, 200);
						views.add(bullet.view);
						return;
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
		for (View view : views) view.update(timeFlaps);
	}

	/**
	 * 碰撞检测
	 */
	public void collisionDetection() {
		// 遍历每一个子弹
		for (Bullet bullet : bullets) {
			// 寻找每一辆坦克
			for (Tank tank : tanks) {
				if (
						tank != bullet.shooterTank &&
						bullet.view.getDistanceTo(tank.view) < 20
				) {
					tank.damage(bullet.damage);
					bullet.view.destroy();
				}
			}
		}
	}

	public void updateDataset() {
		views.removeIf(nextEl -> nextEl.destroyed && nextEl != userTank.view);
	}
}
