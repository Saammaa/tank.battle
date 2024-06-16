package service;

import engine.App;
import engine.entity.*;
import mvc.model.Directions;
import utilities.Coordinate;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class Inspector {
	App app;

	MouseAdapter baseMouseAdapter;

	KeyAdapter keyAdapter;

	Boolean useTotalMouseControl;

	public Inspector() {
		this.app = App.getInstance();
		this.useTotalMouseControl = app.options.getBoolOption("useTotalMouseControl");

		this.setupBaseMouseAdaptor();
		this.setupKeyListener();

		this.registerService();
	}

	public void registerService() {
		this.app.gameFrame.addMouseListener( this.baseMouseAdapter );
		this.app.gameFrame.addMouseMotionListener( this.baseMouseAdapter );
		this.app.gameFrame.addMouseWheelListener( this.baseMouseAdapter );

		if ( !this.useTotalMouseControl ) {
			this.app.gameFrame.addKeyListener( this.keyAdapter );
		}
	}

	private void setupBaseMouseAdaptor() {
		Tank player = this.app.getPlayer();
		Boolean totalMouseControl = this.useTotalMouseControl;

		this.baseMouseAdapter = new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				if ( player.isDestroyed() ) return;

				double x = e.getX() - 9;
				double y = e.getY() - 38;

				double direction = Coordinate.getDirectionBetweenPoints(
						player.view.x,
						player.view.y,
						x, y
				) + 90;

				if ( totalMouseControl ) {
					player.view.readyToMove(direction);
				}

				player.turretDirection = direction;
			}

			public void mousePressed(MouseEvent e) {
				if ( player.isDestroyed() ) {
					player.view.setMoving(false);
					return;
				}

				app.battle.fireBullet( player, 200 );
			}
		};
	}

	private void setupKeyListener() {
		Tank player = this.app.getPlayer();

		this.keyAdapter = new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (player.isDestroyed()) {
					player.view.setMoving(false);
					return;
				}

				switch (e.getKeyCode()) {
					case KeyEvent.VK_UP:
						player.view.readyToMove(Directions.UP.getAngleValue());
						break;
					case KeyEvent.VK_LEFT:
						player.view.readyToMove(Directions.LEFT.getAngleValue());
						break;
					case KeyEvent.VK_DOWN:
						player.view.readyToMove(Directions.DOWN.getAngleValue());
						break;
					case KeyEvent.VK_RIGHT:
						player.view.readyToMove(Directions.RIGHT.getAngleValue());
						break;
					case KeyEvent.VK_SPACE:
						app.battle.fireBullet(player, 200);
						break;
				}
			}

			public void keyReleased(KeyEvent e) {
				if (37 <= e.getKeyCode() && e.getKeyCode() <= 40) {
					player.view.setMoving(false);
				}
			}
		};
	}
}
