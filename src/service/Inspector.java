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

	public Inspector() {
		this.app = App.getInstance();

		this.buildBaseMouseAdaptor();
		this.buildKeyListener();

		this.registerService();
	}

	public void registerService() {
		this.app.gameFrame.addMouseListener( this.baseMouseAdapter );
		this.app.gameFrame.addMouseMotionListener( this.baseMouseAdapter );
		this.app.gameFrame.addMouseWheelListener( this.baseMouseAdapter );

		this.app.gameFrame.addKeyListener( this.keyAdapter );
	}

	private void buildBaseMouseAdaptor() {
		Tank player = this.app.getPlayer();

		this.baseMouseAdapter = new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				if ( player.isDestroyed() ) return;

				double x = e.getX() - 9;
				double y = e.getY() - 38;

				player.turretDirection = Coordinate.getDirectionBetweenPoints(
						player.view.x,
						player.view.y,
						x, y
				) + 90;
			}

			public void mousePressed(MouseEvent e) {
				if ( player.isDestroyed() ) {
					player.view.setMoving(false);
					return;
				}

				Bullet bullet = new Bullet( player, 200 );
				app.battle.bullets.add( bullet );
			}
		};
	}

	private void buildKeyListener() {
		Tank player = this.app.getPlayer();

		this.keyAdapter = new KeyAdapter() {
			public void keyPressed( KeyEvent e ) {
				if ( player.isDestroyed() ) {
					player.view.setMoving( false );
					return;
				}

				switch ( e.getKeyCode() ) {
					case 38:	// ↑
						player.view.readyToMove( Directions.UP.getAngleValue() );
						break;
					case 37:	// ←
						player.view.readyToMove( Directions.LEFT.getAngleValue() );
						break;
					case 40:	// ↓
						player.view.readyToMove( Directions.DOWN.getAngleValue() );
						break;
					case 39:	// →
						player.view.readyToMove( Directions.RIGHT.getAngleValue() );
						break;
				}
			}

			public void keyReleased( KeyEvent e ) {
				if ( 37 <= e.getKeyCode() && e.getKeyCode() <= 40 ) {
					player.view.setMoving( false );
				}
			}
		};
	}
}
