/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2017  Filippo Ranza
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package main.java.model;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

/**
 * SteelBrick class is Child Class of the Brick class. Responsible for all the
 * implementations regarding the Clay Brick.
 */
public class SteelBrick extends Brick {

	private static final String NAME = "Steel Brick";
	private static final Color DEF_INNER = new Color(203, 203, 201);
	private static final Color DEF_BORDER = Color.BLACK;
	private static final int STEEL_STRENGTH = 1;
	private static final double STEEL_PROBABILITY = 0.4;

	private Random rnd;
	private Shape brickFace;

	/**
	 * CementBrick constructor runs parant class's (Brick class) constructor and
	 * Crack class constructor as well. Get brickface from the parent class.
	 * 
	 * @param point brick position
	 * @param size  brick size
	 */
	public SteelBrick(Point point, Dimension size) {
		super(NAME, point, size, DEF_BORDER, DEF_INNER, STEEL_STRENGTH);
		rnd = new Random();
		brickFace = super.brickFace;
	}

	/**
	 * makeBrickFace override parent class's makeBrickFace makeBrickFace method
	 * creates a steel brick.
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
	 * @return returns steel brick
	 */
	@Override
	public Shape getBrick() {
		return brickFace;
	}

	/**
	 * setImpact override parent class's setImpact setImpact method detect if impact
	 * has occurred.
	 * 
	 * @param point point of impact
	 * @param dir   direction of impact.
	 */
	public boolean setImpact(Point2D point, int dir) {
		if (super.isBroken())
			return false;
		impact();
		return super.isBroken();
	}

	/**
	 * impact is an Overrides the Method in the parent class, Brick class.
	 * Responsible for deducting the strength of a brick when an impact has
	 * occurred.
	 */
	@Override
	public void impact() {
		if (rnd.nextDouble() < STEEL_PROBABILITY) {
			super.impact();
		}
	}

}
