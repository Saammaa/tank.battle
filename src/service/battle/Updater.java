package service.battle;

import engine.App;
import engine.entity.*;
import mvc.Entity;

public class Updater {
	private final App app;

	private float timeFlaps;

	public Updater(App app) {
		this.app = app;
	}

	public void run(float timeFlaps) {
		this.timeFlaps = timeFlaps;

		this.updateTankPositions();
		this.updateBulletPositions();

		this.detectCollision();
		this.updateDataset();
	}

	private void updateTankPositions() {
		for (Tank tank : this.app.battle.getTanks()) {
			// 假设坦克的下一步位置，这里简化处理，不考虑坦克的朝向和速度
			float nextX = (float) (tank.view.x + 10);
			float nextY = (float) (tank.view.y + 10);

			boolean canMove = true;

			for (Entity block : this.app.battle.blocks) {
				if ("river".equals(block.type)) {
					// 检查坦克的下一步位置是否会进入河流图块的区域
					if (nextX < block.view.x + 64 &&
								nextX + 64 > block.view.x &&
								nextY < block.view.x + 64 &&
								nextY + 64 > block.view.y) {
						// 如果是，则阻止移动
						canMove = false;
						break;
					}
				}
			}

			// 如果坦克可以移动，则更新位置
			if (canMove) {
				tank.view.update(this.timeFlaps);
			}
		}
	}

	private void updateBulletPositions() {
		for (Bullet bullet : this.app.battle.getBullets()) {
			bullet.view.update(this.timeFlaps);
		}
	}

	private void updateDataset() {
		app.battle.tanks.removeIf(nextEl -> nextEl.view.destroyed);
		app.battle.bullets.removeIf(nextEl -> nextEl.view.destroyed);
	}

	private void detectCollision() {
		for (Bullet bullet : this.app.battle.getBullets()) {
			for (Tank tank : this.app.battle.getTanks()) {
				if (tank != bullet.shooterTank && bullet.view.getDistanceTo(tank.view) < 20) {
					bullet.view.destroy();
					tank.damage(bullet.damage);
				}
			}
		}
	}
}
