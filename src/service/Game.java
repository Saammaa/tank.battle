package service;

import engine.App;
import service.battle.Enemy;
import service.battle.Updater;

public class Game extends Thread {
	public App app;

	private final Enemy enemyService;

	private final Updater updaterService;

	private final Inspector inspectorService;

	public boolean running;

	public Game(App app) {
		this.app = app;
		this.running = true;

		this.inspectorService = new Inspector(app);

		this.enemyService = new Enemy(app);
		this.updaterService = new Updater(app);
	}

	public void pause() {
		this.running = false;
		this.enemyService.disableFire();
	}

	public void recover() {
		this.running = true;
		this.enemyService.enableFire();
	}

	public void run() {
		super.run();

		int fps = 60;
		long lastUpdate = System.currentTimeMillis();

		while (true) {
			long interval = 1000 / fps;
			long currentTime = System.currentTimeMillis();
			long _time = currentTime - lastUpdate;

			if (_time < interval) {
				try {
					Thread.sleep(1);
				} catch (Exception e) {}
			} else {
				lastUpdate = currentTime;

				// 根据this.running的状态调整dt的值
				float dt = this.running ? (_time * 0.001f) : (_time * 0.001f / 25);

				this.enemyService.run();
				this.updaterService.run(dt);

				this.app.gameFrame.update(dt);
			}
		}
	}
	public Inspector getInspectorService() {
		return this.inspectorService;
	}

	public Updater getUpdaterService() {
		return this.updaterService;
	}

	public Enemy getEnemyService() {
		return this.enemyService;
	}
}
