package service.battle;

import engine.App;
import engine.entity.*;

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
			tank.view.update(this.timeFlaps);
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
