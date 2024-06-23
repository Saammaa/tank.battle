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
		this.gameFrame	= new GameFrame(this);

		this.layoutInit();
		this.playerInit();
		this.terrainInit();

		this.gameService = new Game(this);
	}

	public static synchronized void getInstance() {
		if (instance == null) instance = new App();
	}

	public Tank getPlayer() {
		return this.battle.player;
	}

	private void playerInit() {
		this.battle.setPlayer( new Tank(
				this.options.getIntOption("playerBornX"),
				this.options.getIntOption("playerBornY"),
				90,
				this.options.getDoubleOption("playerMaxHealth"),
				this.options.getDoubleOption("playerRecoverSpeed"),
				Team.RED.ordinal()
		));
	}

	private void terrainInit() {
		this.battle.blocks.add(new River(240, 500));
		this.battle.blocks.add(new Grass(80, 500));
	}

	private void layoutInit() {
		this.gameFrame.settingsButton.addActionListener(e -> gameService.pause());
	}
}