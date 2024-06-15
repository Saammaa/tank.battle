package MVC.Model;

import Entity.*;
import Utilities.Coordinate;
import View.TankView;

import java.util.HashSet;

public class Battleground {
	public HashSet<Tank> tanks = new HashSet<>();

	public HashSet<Bullet> bullets = new HashSet<>();

	public Tank userTank;

	public Battleground() {
		this.userTank = new Tank(600, 200, 0, 110, 0.5, Team.RED.ordinal());

		this.tanks.add(userTank);
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
		enemyTank.view.setMoving(true);

		this.tanks.add(enemyTank);
	}

	/**
	 * 构造敌方坦克。
	 */
	public void AddSomeEnemyTanks() {
		// 构造一些敌方坦克
		addSingleEnemy(0, 500, 0, 200, 0.1, Team.BLUE.ordinal());
	}

	public void updateEnemies(int viewWidth, int viewHeight) {
		if (userTank.view.destroyed) return;

		for (Tank tank : this.tanks) {
			if (tank.team == Team.BLUE.ordinal()) {
				TankView tankView = tank.view;

				// 防止跑出地图
				if (tankView.x < 0) {
					tankView.setDirection(Directions.RIGHT.getAngleValue());
				}
				if (tankView.y < 0) {
					tankView.setDirection(Directions.DOWN.getAngleValue());
				}
				if (tankView.x >= viewWidth) {
					tankView.setDirection(Directions.LEFT.getAngleValue());
				}
				if (tankView.y >= viewHeight) {
					tankView.setDirection(Directions.UP.getAngleValue());
				}

				// 运动几步随机开炮，50 应设置为参数或者常量
				if (tank.moveSteps > 50) {
					double random = Math.random() * 360;

					tankView.setDirection(random);
					tank.turretDirection = random;

					tank.moveSteps = 0;
					tankView.setMoving(true);

					// 我方坦克进入射程
					if (tankView.getDistanceTo(userTank.view) < 800) {
						// 自动瞄准
						tank.turretDirection = Coordinate.getDirectionBetweenPoints(
								tankView.x, tankView.y, userTank.view.x, userTank.view.y
						) + 90;

						// 开炮
						Bullet bullet = new Bullet(tank, 200);
						this.bullets.add(bullet);

						return;
					}
				}
			}
		}
	}

	/**
	 * 更新视图位置
	 *
	 * @param timeFlaps 运行时间间隔
	 */
	public void updateTankPositions(double timeFlaps) {
		for (Tank tank : this.tanks) tank.view.update(timeFlaps);
	}

	public void updateBulletPositions(double timeFlaps) {
		for (Bullet bullet : this.bullets) bullet.view.update(timeFlaps);
	}

	/**
	 * 碰撞检测
	 */
	public void detectCollision() {
		// 遍历每一个子弹
		for (Bullet bullet : this.bullets) {
			// 寻找每一辆坦克
			for (Tank tank : this.tanks) {
				if (tank != bullet.shooterTank && bullet.view.getDistanceTo(tank.view) < 20) {
					bullet.view.destroy();
					tank.damage(bullet.damage);
				}
			}
		}
	}

	public void updateDataset() {
		tanks.removeIf(nextEl -> nextEl.view.destroyed);
		bullets.removeIf(nextEl -> nextEl.view.destroyed);
	}
}
