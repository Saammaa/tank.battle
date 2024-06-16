package engine.view;

import engine.entity.Bullet;
import service.ImageCache;
import mvc.View;

import java.awt.*;

public class BulletView extends View {
	private final Bullet bullet;

	public BulletView(Bullet bullet, double x, double y, double direction, double speed) {
		super(x, y, direction, speed);

		this.bullet = bullet;
	}

	public void update(double timeFlaps) {
		// 计算新坐标
		double len = speed * timeFlaps;
		this.moveTowards(direction, len);

		// 超出范围的无效子弹
		if (x < -100 || x > 2000 || y < -100 || y > 2000) {
			this.destroy();
		}
	}

	public void draw(Graphics2D g2) {
		Graphics2D g = (Graphics2D) g2.create();
		g.translate(x, y);
		g.drawImage(ImageCache.get("shot"), -6, -6, null);
	}
}