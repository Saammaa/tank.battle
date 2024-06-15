package View;

import Entity.Team;
import MVC.View;

import java.awt.*;

public class TankView extends View {
	private final Entity.Tank tank;

	public TankView(
			Entity.Tank tank,
			double x,
			double y,
			double direction,
			double speed
	) {
		super(x, y, direction, speed);
		this.tank = tank;
	}

	public void update(double timeFlaps) {
		if (destroyed) return;

		this.tank.recoverHealth();

		if (moving) {
			double len = speed * timeFlaps;
			this.tank.moveSteps++;
			this.moveTowards(direction, len);
		}
	}

	public void draw(Graphics2D g2) {
		java.awt.Image imageA = null;
		java.awt.Image imageB = null;

		if (this.tank.team == Team.RED.ordinal()) {
			imageA = MVC.Renderer.Image.get("tank_red");
			imageB = MVC.Renderer.Image.get("turret_red");
		}

		if (this.tank.team == Team.BLUE.ordinal()) {
			imageA = MVC.Renderer.Image.get("tank_blue");
			imageB = MVC.Renderer.Image.get("turret_blue");
		}

		Graphics2D g = (Graphics2D) g2.create();
		g.translate(x, y);

		g.rotate(Math.toRadians(direction));
		g.drawImage(imageA, -18, -19, null);
		g.rotate(Math.toRadians(-direction));

		g.drawRect(-22, -34, 44, 8);
		g.setColor(Color.RED);

		int currentHPWidth = (int) (43.08 * (this.tank.health / this.tank.maxHealth));

		g.fillRect(-21, -33, currentHPWidth, 7);
		g.rotate(Math.toRadians(this.tank.turretDirection));
		g.drawImage(imageB, -32, -32, null);
	}
}
