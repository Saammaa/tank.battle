package mvc;

public abstract class Entity<T extends View>  {
	public T view;

	public String type = "block";

	public double direction;

	public double speed;

	public double damage;

	public double health;

	public double maxHealth;

	public double healthRecoverySpeed;

	public boolean tough;

	public boolean moving;

	public void damage() {}

	public void recover() {}

	public T getView() {
		return this.view;
	}

	public boolean isDestroyed() {
		if (this.view != null) {
			return this.view.destroyed;
		} else {
			return false;
		}
	}
}
