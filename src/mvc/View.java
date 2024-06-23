package mvc;

import java.awt.*;

public class View {
	public double x;

	public double y;

	public double direction;

	public double speed;

	public boolean destroyed = false;

	public boolean moving;

	public boolean visible;

	public int width;

	public int height;

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
	 * @param length		距离
	 */
	public void moveTowards(double direction, double length) {
		this.x = this.x + length * Math.cos((direction - 90) * Math.PI / 180);
		this.y = this.y + length * Math.sin((direction - 90) * Math.PI / 180);
	}

	public void readyToMove(double direction) {
		this.moving = true;
		this.direction = direction;
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

	public boolean isEnteredTargetArea(View target) {
		Rectangle thisRect		= new Rectangle( (int) this.x, (int) this.y, this.width, this.height );
		Rectangle targetRect	= new Rectangle( (int) target.x, (int) target.y, target.width, target.height );

		return thisRect.intersects(targetRect);
	}

	public boolean isVisible() {
		return this.visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void setMoving(boolean isMoving) {
		this.moving = isMoving;
	}

	public void setDirection(double direction) {
		this.direction = direction;
	}
}