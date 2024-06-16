package data;

import engine.entity.*;
import mvc.model.Directions;
import utilities.Coordinate;

import java.util.HashSet;

public class Battleground {
	public HashSet<Tank> tanks = new HashSet<>();

	public HashSet<Bullet> bullets = new HashSet<>();

	public Tank playerTank;

	public Battleground() {
		AddSomeEnemyTanks();
	}

	public void setPlayerTank(Tank playerTank) {
		if ( this.playerTank != null ) {
			this.tanks.remove( this.playerTank);
		}

		this.playerTank = playerTank;
		this.tanks.add(playerTank);
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
		if (playerTank.view.destroyed) return;

		for (Tank tank : this.tanks) {
			if (tank.team == Team.BLUE.ordinal()) {
				// 防止跑出地图
				if (tank.view.x < 0) {
					tank.view.setDirection(Directions.RIGHT.getAngleValue());
				}
				if (tank.view.y < 0) {
					tank.view.setDirection(Directions.DOWN.getAngleValue());
				}
				if (tank.view.x >= viewWidth) {
					tank.view.setDirection(Directions.LEFT.getAngleValue());
				}
				if (tank.view.y >= viewHeight) {
					tank.view.setDirection(Directions.UP.getAngleValue());
				}

				// 运动几步随机开炮，50 应设置为参数或者常量
				if (tank.moveSteps > 50) {
					double random = Math.random() * 360;

					tank.view.setDirection(random);
					tank.turretDirection = random;

					tank.moveSteps = 0;
					tank.view.setMoving(true);

					// 我方坦克进入射程
					if (tank.view.getDistanceTo(playerTank.view) < 800) {
						// 自动瞄准
						tank.turretDirection = Coordinate.getDirectionBetweenPoints(
								tank.view.x, tank.view.y, playerTank.view.x, playerTank.view.y
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
