package renderer;

import engine.App;
import data.Battleground;

import javax.swing.*;

public class GameFrame extends JFrame {
	App app;

	Battleground battleData;

	GamePanel gamePanel;

	public JButton settingsButton;

	public JButton startButton;

	public JButton saveButton;

	public int width = 860;
	public int height = 640;

	// 帧数，自主选择是否显示
	private int _frames = 0;
	// 累计时长
	private float _dt = 0;
	// 帧率
	private float _frameRate = 0.0f;

	/**
	 * 初始化显示组件。
	 */
	public GameFrame(App app) {
		this.app = app;
		this.battleData = this.app.battle;

		this.setLayout(null);
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		this.setTitle("Tank.battle, by yours, Saammaa");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setupGamePanel();
		this.setupUtilityButtons();
		this.setupSettingsButton();

		this.setupFrameSize();
	}

	private void setupUtilityButtons() {
		this.startButton = new JButton("开始");

		startButton.setBounds(120, 0, 100, 30);
		startButton.addActionListener(e -> {
			app.gameService.start();
			startButton.setEnabled(false);
		});

		this.add(startButton);
	}

	private void setupSettingsButton() {
		this.settingsButton = new JButton("设置");

		settingsButton.setBounds(0, 0, 100, 30);
		settingsButton.addActionListener(e -> this.openSettingsWindow());

		this.add(settingsButton);
	}

	private void setupGamePanel() {
		this.gamePanel = new GamePanel();

		gamePanel.loadBattlegroundData(this.battleData);
		gamePanel.setBounds(0, 30, this.width, this.height - 30);

		this.add(gamePanel);
	}

	private void setupFrameSize() {
		int contentPaneWidth = this.getContentPane().getSize().width;
		int contentPanelHeight = this.getContentPane().getSize().height;

		int contentWidth = this.width - contentPaneWidth;
		int contentHeight = this.height - contentPanelHeight;

		this.setSize(this.width + contentWidth, this.height + contentHeight);
	}

	/**
	 * 更新画板。
	 *
	 * @param timeFlaps 流逝时间
	 */
	public void update(double timeFlaps) {
		_frames++;
		_dt += (float) timeFlaps;

		if (_dt >= 1.0f) {
			_frameRate = _frames / _dt;
			_frames = 0;
			_dt = 0f;
		}

		gamePanel.setFrameRate(_frameRate);
		gamePanel.repaint();
	}

	private void openSettingsWindow() {
		GameSettings settingsWindow = new GameSettings(this.app);

		settingsWindow.setModal(true);
		settingsWindow.setVisible(true);
	}
}