package Entity;

import MVC.Model.Element;
import MVC.View.Image;

import java.awt.*;

public class Tank extends Element {
	// 坦克方向
	public double direction;

	// 炮筒方向
	public double turretDir;

	// 是否在移动
	public boolean moving = false;

	// 移动步数
	public long moveSteps = 0;

	// 每秒移动速度，注意要比子弹慢一些
	public double speed = 200;

	public double hp;
	public double maxHP = 200;

	// 每秒回复生命
	public double hpRecoverySpeed;

	/**
	 * 队伍，一红二蓝
	 */
	public int team;

	public Tank(
			int x,
			int y,
			double direction,
			double hp,
			double hpRecoverySpeed,
			int team
	) {
		this.x = x;
		this.y = y;

		this.direction = direction;
		this.hp = hp;
		this.hpRecoverySpeed = hpRecoverySpeed;
		this.team = team;
	}

	public void damage(double damageCount) {
		this.hp -= damageCount;
		if (this.hp <= 0) this.destroy();
	}

	/**
	 * 更新坦克位置。
	 *
	 * @param timeFlaps 流逝时间间隔
	 */
	public void update(double timeFlaps) {
		if (destroyed) return;

		recoverLife();

		if (moving) {
			double len = speed * timeFlaps;
			moveSteps++;
			this.move(direction, len);
		}
	}

	/**
	 * 定时自动回血
	 */
	public void recoverLife() {
		hp += hpRecoverySpeed;
		if (hp > maxHP) hp = maxHP;
	}

	@Override
	public void draw(Graphics2D g2) {
		java.awt.Image img1 = null;
		java.awt.Image img2 = null;

		if (team == Team.RED.ordinal()) {
			img1 = Image.get("tank_red");
			img2 = Image.get("turret_red");
		}

		if (team == Team.BLUE.ordinal()) {
			img1 = Image.get("tank_blue");
			img2 = Image.get("turret_blue");
		}

		Graphics2D g = (Graphics2D) g2.create();
		g.translate(x, y);

		g.rotate(Math.toRadians(direction));
		g.drawImage(img1, -18, -19, null);
		g.rotate(Math.toRadians(-direction));

		g.drawRect(-22, -34, 44, 8);
		g.setColor(Color.RED);

		int currentHPWidth = (int) (43.08 * (hp / maxHP));

		g.fillRect(-21, -33, currentHPWidth, 7);
		g.rotate(Math.toRadians(this.turretDir));
		g.drawImage(img2, -32, -32, null);
	}
}
