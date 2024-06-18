package engine;

import data.*;
import service.*;

import engine.entity.*;
import engine.entity.block.*;

import renderer.GameFrame;

public class App {
	public Options options;

	public Battleground battle;

	public Game gameService;

	public GameFrame gameFrame;

	private static App instance;

	private App() {
		this.options	= new Options();
		this.battle		= new Battleground();
		this.gameFrame	= new GameFrame(this.battle);

		this.playerInit();
		this.terrainInit();

		this.gameService = new Game(this);
	}

	public static synchronized App getInstance() {
		if (instance == null) instance = new App();
		return instance;
	}

	public Tank getPlayer() {
		return this.battle.player;
	}

	public void playerInit() {
		this.battle.setPlayer( new Tank(
				this.options.getIntOption("playerBornX"),
				this.options.getIntOption("playerBornY"),
				90,
				this.options.getDoubleOption("playerMaxHealth"),
				this.options.getDoubleOption("playerRecoverSpeed"),
				Team.RED.ordinal()
		));
	}

	public void terrainInit() {
		this.battle.blocks.add(new River(0, 0));
		this.battle.blocks.add(new River(200, 400));
	}
}