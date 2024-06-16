package data;

import engine.entity.*;

import java.util.HashSet;

public class Battleground {
	public HashSet<Tank> tanks = new HashSet<>();

	public HashSet<Bullet> bullets = new HashSet<>();

	public Tank player;

	public void setPlayer(Tank player) {
		if ( this.player != null ) {
			this.tanks.remove( this.player);
		}

		this.player = player;
		this.tanks.add(player);
	}

	public java.util.HashSet<Tank> getTanks() {
		return this.tanks;
	}

	public java.util.HashSet<Bullet> getBullets() {
		return this.bullets;
	}

	public void createBullet(Tank shooter, double bulletSpeed ) {
		Bullet bullet = new Bullet( shooter, bulletSpeed );
		this.bullets.add( bullet );
	}
}
