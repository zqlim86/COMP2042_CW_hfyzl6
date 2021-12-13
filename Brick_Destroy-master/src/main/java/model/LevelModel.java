package main.java.model;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * LevelModel class is responsible for handling different levels in the game.
 */
public class LevelModel {

	private static final int CLAY = 1;
	private static final int STEEL = 2;
	private static final int CEMENT = 3;
	private static final int LEVELS_COUNT = 5;

	/**
	 * makeLevels is a Private Method that creates the GameModel based on the
	 * levels.
	 * 
	 * @param drawArea            a rectangular area for the GameModel.
	 * @param brickCount          the number of bricks.
	 * @param lineCount           the number of rows of bricks on the GameModel.
	 * @param brickDimensionRatio the brick dimension.
	 * @return returns a GameModel. Either Single type GameModel or Chessboard
	 *         GameModel.
	 */
	public Brick[][] makeLevel(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio) {
		Brick[][] tmp = new Brick[LEVELS_COUNT][];
		tmp[0] = makeSingleTypeLevel(drawArea, brickCount, lineCount, brickDimensionRatio, CLAY);
		tmp[1] = makeChessboardLevel(drawArea, 40, 4, brickDimensionRatio, CLAY, CEMENT);
		tmp[2] = makeChessboardLevel(drawArea, 50, 5, brickDimensionRatio, STEEL, CEMENT);
		tmp[3] = makeStripesLevel(drawArea, 60, 6, brickDimensionRatio, CLAY, STEEL, CEMENT);
		tmp[4] = makeStripesLevel(drawArea, 70, 7, brickDimensionRatio, CEMENT, STEEL, CEMENT);

		return tmp;
	}

	/**
	 * make GameModel of clay bricks for the first level
	 *
	 * @param drawArea       Rectangle GameFrame where game is rendered/drawn
	 * @param brickCnt       number of bricks in GameModel
	 * @param lineCnt        lines of bricks in GameModel
	 * @param brickSizeRatio width-to-height ratio of a single brick
	 * @param type           types of brick
	 * @return array with 31 bricks
	 */
	private Brick[] makeSingleTypeLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio,
			int type) {
		/*
		 * if brickCount is not divisible by line count,brickCount is adjusted to the
		 * biggest multiple of lineCount smaller then brickCount
		 */
		brickCnt -= brickCnt % lineCnt;

		int brickOnLine = brickCnt / lineCnt;

		double brickLen = drawArea.getWidth() / brickOnLine;
		double brickHgt = brickLen / brickSizeRatio;

		brickCnt += lineCnt / 2;

		Brick[] tmp = new Brick[brickCnt];

		Dimension brickSize = new Dimension((int) brickLen, (int) brickHgt);
		Point p = new Point();

		int i;
		for (i = 0; i < tmp.length; i++) {
			int line = i / brickOnLine;
			if (line == lineCnt)
				break;
			double x = (i % brickOnLine) * brickLen;
			x = (line % 2 == 0) ? x : (x - (brickLen / 2));
			double y = (line) * brickHgt;
			p.setLocation(x, y);
			tmp[i] = makeBrick(p, brickSize, type);
		}

		for (double y = brickHgt; i < tmp.length; i++, y += 2 * brickHgt) {
			double x = (brickOnLine * brickLen) - (brickLen / 2);
			p.setLocation(x, y);
			tmp[i] = new ClayBrick(p, brickSize);
		}
		return tmp;

	}

	/**
	 * make a GameModel with 2 types of bricks arranged in a chessboard pattern for
	 * level 2, 3, 4
	 *
	 * @param drawArea       Rectangle GameFrame where game is rendered/drawn
	 * @param brickCnt       number of bricks in GameModel
	 * @param lineCnt        lines of bricks in GameModel
	 * @param brickSizeRatio width-to-height ratio of a single brick
	 * @param typeA          first type of brick
	 * @param typeB          second type of brick
	 * @return array with 31 bricks
	 */
	private Brick[] makeChessboardLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int typeA,
			int typeB) {
		/*
		 * if brickCount is not divisible by line count,brickCount is adjusted to the
		 * biggest multiple of lineCount smaller then brickCount
		 */
		brickCnt -= brickCnt % lineCnt;

		int brickOnLine = brickCnt / lineCnt;

		int centerLeft = brickOnLine / 2 - 1;
		int centerRight = brickOnLine / 2 + 1;

		double brickLen = drawArea.getWidth() / brickOnLine;
		double brickHgt = brickLen / brickSizeRatio;

		brickCnt += lineCnt / 2;

		Brick[] tmp = new Brick[brickCnt];

		Dimension brickSize = new Dimension((int) brickLen, (int) brickHgt);
		Point p = new Point();

		int i;
		for (i = 0; i < tmp.length; i++) {
			int line = i / brickOnLine;
			if (line == lineCnt)
				break;
			int posX = i % brickOnLine;
			double x = posX * brickLen;
			x = (line % 2 == 0) ? x : (x - (brickLen / 2));
			double y = (line) * brickHgt;
			p.setLocation(x, y);

			boolean b = ((line % 2 == 0 && i % 2 == 0) || (line % 2 != 0 && posX > centerLeft && posX <= centerRight));
			tmp[i] = b ? makeBrick(p, brickSize, typeA) : makeBrick(p, brickSize, typeB);
		}

		for (double y = brickHgt; i < tmp.length; i++, y += 2 * brickHgt) {
			double x = (brickOnLine * brickLen) - (brickLen / 2);
			p.setLocation(x, y);
			tmp[i] = makeBrick(p, brickSize, typeA);
		}
		return tmp;
	}

	private Brick[] makeStripesLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int typeA,
			int typeB, int typeC) {
		/*
		 * if brickCount is not divisible by line count,brickCount is adjusted to the
		 * biggest multiple of lineCount smaller then brickCount
		 */
		brickCnt -= brickCnt % lineCnt;

		int brickOnLine = brickCnt / lineCnt;

		double brickLen = drawArea.getWidth() / brickOnLine;
		double brickHgt = brickLen / brickSizeRatio;

		brickCnt += lineCnt / 2;

		Brick[] tmp = new Brick[brickCnt];

		Dimension brickSize = new Dimension((int) brickLen, (int) brickHgt);
		Point p = new Point();

		int i;
		for (i = 0; i < tmp.length; i++) {
			int line = i / brickOnLine;
			if (line == lineCnt)
				break;
			int posX = i % brickOnLine;
			double x = posX * brickLen;
			x = (line % 2 == 0) ? x : (x - (brickLen / 2));
			double y = (line) * brickHgt;
			p.setLocation(x, y);

			if (line % 3 == 0) {
				tmp[i] = makeBrick(p, brickSize, typeA);
			} else if (line % 3 == 1) {
				tmp[i] = makeBrick(p, brickSize, typeB);
			} else {
				tmp[i] = makeBrick(p, brickSize, typeC);
			}
		}

		for (double y = brickHgt; i < tmp.length; i++, y += 2 * brickHgt) {
			double x = (brickOnLine * brickLen) - (brickLen / 2);
			p.setLocation(x, y);
			if (y % 3 == 0) {
				tmp[i] = makeBrick(p, brickSize, typeA);
			} else if (y % 3 == 1) {
				tmp[i] = makeBrick(p, brickSize, typeB);
			} else {
				tmp[i] = makeBrick(p, brickSize, typeC);
			}
		}
		return tmp;
	}

	/**
	 * make brick based on type.
	 * 
	 * @param point
	 * @param size
	 * @param type
	 * @return
	 */
	private Brick makeBrick(Point point, Dimension size, int type) {
		Brick out;
		switch (type) {
		case CLAY:
			out = new ClayBrick(point, size);
			break;
		case STEEL:
			out = new SteelBrick(point, size);
			break;
		case CEMENT:
			out = new CementBrick(point, size);
			break;
		default:
			throw new IllegalArgumentException(String.format("Unknown Type:%d\n", type));
		}
		return out;
	}
}