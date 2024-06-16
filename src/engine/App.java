package engine;

import data.*;
import engine.entity.*;
import renderer.GameFrame;
import service.Inspector;

public class App extends Thread {
	public Options options;

	public Battleground battle;

	public GameFrame gameFrame;

	public Inspector inspectorService;

	private static App instance;

	private App() {
		this.options	= new Options();
		this.battle		= new Battleground();
		this.gameFrame	= new GameFrame( this.battle);

		this.playerInit();
	}

	public static synchronized App getInstance() {
		if (instance == null) instance = new App();
		return instance;
	}

	public Tank getPlayer() {
		return this.battle.playerTank;
	}

	public void playerInit() {
		this.battle.setPlayerTank( new Tank(
				this.options.getIntOption("playerBornX"),
				this.options.getIntOption("playerBornY"),
				90,
				this.options.getDoubleOption("playerMaxHealth"),
				this.options.getDoubleOption("playerRecoverSpeed"),
				Team.RED.ordinal()
		));
	}

	public void start() {
		if ( this.inspectorService == null ) {
			this.inspectorService = new Inspector();
		}

		this.run();
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

				// 流逝时间
				float dt = _time * 0.001f;

				// 调度任务，如果有些任务计算量大，可以开线程池
				this.battle.updateEnemies(
						this.gameFrame.width,
						this.gameFrame.height
				);

				this.battle.updateTankPositions(dt);
				this.battle.updateBulletPositions(dt);

				// 碰撞检测
				this.battle.detectCollision();

				// 依据元素的状态，更新数据区
				this.battle.updateDataset();

				// 刷新界面
				this.gameFrame.update(dt);
			}
		}
	}
}