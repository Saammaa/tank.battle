package engine.entity;

import mvc.Entity;
import engine.view.TankView;

public class Tank extends Entity {
	// 炮筒方向
	public double turretDirection;

	// 移动步长
	public long moveSteps = 0;

	public TankView view;

	/**
	 * 队伍，一红二蓝
	 */
	public int team;

	public Tank(
			int x,
			int y,
			double direction,
			double health,
			double healthRecoverySpeed,
			int team
	) {
		this.team = team;
		this.speed = 160;
		this.maxHealth = 200;

		this.health = health;
		this.direction = direction;
		this.healthRecoverySpeed = healthRecoverySpeed;

		this.view = new TankView(this, x, y, direction, speed);
	}

	public void damage(double damageCount) {
		this.health -= damageCount;
		if (this.health <= 0) this.view.destroy();
	}

	public void recoverHealth() {
		this.health += healthRecoverySpeed;

		if (this.health > this.maxHealth) {
			this.health = this.maxHealth;
		}
	}
}
