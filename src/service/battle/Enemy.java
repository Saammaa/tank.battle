package service.battle;

import engine.App;
import engine.entity.*;
import mvc.model.Directions;
import utilities.Coordinate;

import java.util.Timer;
import java.util.TimerTask;

import java.util.Random;

public class Enemy {
	private final App app;

	private Random random;

	private Timer timer;

	public Enemy(App app) {
		this.app = app;
		this.startAddingTanksPeriodically();
	}

	public void run() {
		this.updateEnemies();
	}

	public void addOne(
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

		// 添加至战场
		app.battle.tanks.add(enemyTank);
	}

	public void addRandom() {
		if (this.random == null) {
			this.random = new Random();
		}

		this.addOne(
				(int)(this.app.gameFrame.width * this.random.nextDouble()),
				(int)(this.app.gameFrame.height * this.random.nextDouble()),
				360 * this.random.nextDouble(),
				180.0, 0.5, 1
		);
	}

	private void updateEnemies() {
		Tank player = this.app.getPlayer();

		int viewWidth = app.gameFrame.width;
		int viewHeight = app.gameFrame.height;

		if (player.isDestroyed()) return;

		for (Tank tank : this.app.battle.getTanks()) {
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
					if (tank.view.getDistanceTo(player.view) < 800) {
						// 自动瞄准
						tank.turretDirection = Coordinate.getDirectionBetweenPoints(
								tank.view.x,
								tank.view.y,
								player.view.x,
								player.view.y
						) + 90;

						app.battle.createBullet(tank, 200);
						return;
					}
				}
			}
		}
	}

	public void startAddingTanksPeriodically() {
		if (timer != null) timer.cancel();

		timer = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {
				if (app.battle.getTanks().size() < 5 + 1) addRandom();
			}
		};

		timer.scheduleAtFixedRate(task, 0, 3500);
	}
}
