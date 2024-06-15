package MVC;

import java.awt.*;

public class View {
	public double x;

	public double y;

	public double direction;

	public double speed;

	public boolean destroyed = false;

	public boolean moving;

	public View(
			double x,
			double y,
			double direction,
			double speed
	) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.direction = direction;
	}

	public void update(double timeFlaps) {}

	public void draw(Graphics2D g) {}

	public void destroy() {
		this.destroyed = true;
	}

	/**
	 * 向某个方向移动一段距离。
	 *
	 * @param direction	方向
	 * @param len		距离
	 */
	public void moveTowards(double direction, double len) {
		x = x + len * Math.cos((direction - 90) * Math.PI / 180);
		y = y + len * Math.sin((direction - 90) * Math.PI / 180);
	}

	/**
	 * 计算两个结点的距离
	 *
	 * @param target 目标元素
	 * @return 距离
	 */
	public double getDistanceTo(View target) {
		double a = this.x - target.x;
		double b = this.y - target.y;
		return Math.sqrt(a * a + b * b);
	}

	public void setMoving(boolean isMoving) {
		this.moving = isMoving;
	}

	public void setDirection(double direction) {
		this.direction = direction;
	}
}