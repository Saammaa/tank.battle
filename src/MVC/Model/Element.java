package MVC.Model;

import java.awt.*;

public class Element implements ElementInterface {
	public double x;

	public double y;

	/**
	 * 是否需要销毁，False：显示； True：删除
	 */
	public boolean destroyed = false;

	public Element() {}

	public void update(double timeFlaps) {}

	@Override
	public void draw(Graphics2D g) {}

	/**
	 * 该元素生命周期结束，可以回收资源
	 */
	public void destroy() {
		destroyed = true;
	}

	/**
	 * 向某方向移动一段距离
	 *
	 * @param dir 方向
	 * @param len 距离
	 */
	public void move(double dir, double len) {
		x = x + len * Math.cos((dir - 90) * Math.PI / 180);
		y = y + len * Math.sin((dir - 90) * Math.PI / 180);
	}

	/**
	 * 计算两个结点的距离
	 *
	 * @param target 目标元素
	 * @return 距离
	 */
	public double distance(Element target) {
		double a = this.x - target.x;
		double b = this.y - target.y;
		return Math.sqrt(a * a + b * b);
	}

	/**
	 * 与另一个元素的夹角
	 *
	 * @param target 目标元素
	 */
	public double angle(Element target) {
		double len_x = target.x - x;
		double len_y = target.y - y;
		double radian = Math.atan2(len_y, len_x);
		return radian * 180 / Math.PI;
	}
}