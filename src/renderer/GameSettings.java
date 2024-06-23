package renderer;

import engine.App;

import java.awt.*;
import javax.swing.*;

public class GameSettings extends JDialog {
	App app;

	public GameSettings(App app) {
		this.app = app;

		this.setTitle("游戏设置");
		this.setSize(300, 200);
		this.setLayout(new FlowLayout());
		this.setLocationRelativeTo(null);

		this.setupBasicComponents();
	}

	private void setupBasicComponents() {
		JLabel settingsLabel = new JLabel("游戏设置");
		this.add(settingsLabel);

		JButton closeButton = new JButton("继续游戏");
		closeButton.addActionListener(e -> this.onClose());
		this.add(closeButton);
	}

	private void onClose() {
		this.dispose();
		this.app.gameService.recover();
	}
}