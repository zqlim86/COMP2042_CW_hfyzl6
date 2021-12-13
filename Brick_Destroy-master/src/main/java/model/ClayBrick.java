package main.java.model;

import java.awt.*;
import java.awt.Point;
import java.awt.geom.Point2D;

/**
 * ClayBrick class is Child Class of the Brick class. Responsible for all the
 * implementations regarding the Clay Brick.
 */
public class ClayBrick extends Brick {

	private static final String NAME = "Clay Brick";
	private static final Color DEF_INNER = new Color(178, 34, 34).darker();
	private static final Color DEF_BORDER = Color.GRAY;
	private static final int CLAY_STRENGTH = 1;

	/**
	 * ClayBrick constructor runs parant class's (Brick class) constructor and Crack
	 * class constructor as well.
	 * 
	 * @param point brick position
	 * @param size  brick size
	 */
	public ClayBrick(Point point, Dimension size) {
		super(NAME, point, size, DEF_BORDER, DEF_INNER, CLAY_STRENGTH);
	}

	/**
	 * makeBrickFace override parent class's makeBrickFace makeBrickFace method
	 * creates a clay brick.
	 * 
	 * @param pos  brick position
	 * @param size brick size
	 */
	@Override
	public Shape makeBrickFace(Point pos, Dimension size) {
		return new Rectangle(pos, size);
	}

	/**
	 * getBrick override parent class's getBrick.
	 * 
	 * @return returns clay brick
	 */
	@Override
	public Shape getBrick() {
		return super.brickFace;
	}

}
