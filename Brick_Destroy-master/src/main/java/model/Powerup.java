package main.java.model;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Powerup is an Abstract Class that handles all the implementations regarding
 * the Powerup in the game.
 **/
public abstract class Powerup {

	Shape powerupFace;
	private Point2D center;

	/**
	 * Powerup constructor calls resetPowerup method.
	 */
	public Powerup() {
		resetPowerup();

	}

	/**
	 * getPosition returns the center of the powerup's position.
	 * 
	 * @return center of the powerup's position.
	 */
	public Point2D getPosition() {
		return center;
	}

	public void resetPowerup() {
	}

	protected abstract void resetFace(Point2D point);

	protected abstract Shape getMultiFace();

	protected abstract Color getInnerColor();

	protected abstract Color getBorderColor();

}
