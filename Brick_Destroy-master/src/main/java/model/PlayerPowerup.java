package main.java.model;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * PlayerPowerup class is Child Class of the Powerup class. Responsible for all
 * the implementations regarding the Player Powerup.
 */
public class PlayerPowerup extends Powerup {

	private final Color inner = Color.BLUE;
	private final Color border = inner.darker().darker();

	private Shape paddleFace;
	private boolean showPlayerFace = true;

	private Point2D center;
	private Image image;

	/**
	 * PlayerPowerup constructor will create and set a position for the player
	 * powerup. It will also read an image for the player powerup's in game visual.
	 */
	public PlayerPowerup() {
		this.center = new Point(400, 250);
		resetFace(getPosition());
		paddleFace = makePowerup(getPosition());
		try {
			image = ImageIO.read(getClass().getResource("/resources/PUGrowth.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * getPosition returns the center of the powerup's position.
	 * 
	 * @return center of the powerup's position.
	 */
	public Point2D getPosition() {
		return center;
	}

	/**
	 * makePowerup will return a rectangle shape for the player powerup.
	 * 
	 * @param center Point.
	 * @return New rectangle shape with the value of x,y,25,25.
	 */
	public Shape makePowerup(Point2D center) {
		double x = center.getX();
		double y = center.getY();

		return new Rectangle2D.Double(x, y, 25, 25);
	}

	/**
	 * getMultiFace is a Getter Method will return the paddleFace variable.
	 * 
	 * @return returns fireFace variable.
	 */
	public Shape getMultiFace() {
		return paddleFace;
	}

	/**
	 * getBorderColor is a Getter Method will return the border variable.
	 * 
	 * @return returns border variable.
	 */
	public Color getBorderColor() {
		return border;
	}

	/**
	 * getInnerColor is a Getter Method will return the inner variable.
	 * 
	 * @return returns inner variable.
	 */
	public Color getInnerColor() {
		return inner;
	}

	/**
	 * impact method will detect if the ball has impacted the player powerup. If
	 * True then returns true, else return false.
	 *
	 * @param b ball object.
	 * @return boolean value to see if ball has impacted player powerup.
	 */
	public boolean impact(Ball b) {
		if (paddleFace.getBounds().contains(b.getPosition())) {
			return true;
		}
		return false;
	}

	/**
	 * resetFace overrides parent class's (Powerup) resetFace(). This method will
	 * set the paddleFace variable into a rectangle shape by calling the
	 * makePowerup() method.
	 */
	@Override
	public void resetFace(Point2D point) {
		paddleFace = makePowerup(point);
	}

	/**
	 * getPlayerFace is a Getter Method to return the showPlayerFace variable.
	 * 
	 * @return returns showPlayerFace boolean value.
	 */
	public boolean getPlayerFace() {
		return showPlayerFace;
	}

	/**
	 * getImage method will return image type variable.
	 * 
	 * @return returns image type value;
	 */
	public Image getImage() {
		return image;
	}

}
