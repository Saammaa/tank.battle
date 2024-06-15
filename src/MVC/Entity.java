package MVC;

public class Entity {
	public View view;

	public double direction;

	public double speed;

	public double damage;

	public double health;

	public double maxHealth;

	public double healthRecoverySpeed;

	public boolean moving;

	public void damage() {}

	public void recover() {}

	public boolean isDestroyed() {
		if (this.view != null) {
			return this.view.destroyed;
		} else {
			return false;
		}
	}
}
