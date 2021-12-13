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
package main.java.view;

import javax.swing.*;

import main.java.controller.GameController;
import main.java.controller.TextFileController;
import main.java.debug.DebugConsole;
import main.java.model.BackgroundMusic;
import main.java.model.Ball;
import main.java.model.Brick;
import main.java.model.FirePowerup;
import main.java.model.Player;
import main.java.model.PlayerPowerup;
import main.java.model.GameModel;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.io.IOException;

/**
 * GameView class is responsible for displaying the in-game view.
 */
public class GameView extends JComponent implements KeyListener, MouseListener, MouseMotionListener {

	private static final String CONTINUE = "Continue";
	private static final String RESTART = "Restart";
	private static final String MENU = "Main Menu";
	private static final String PAUSE = "Pause Menu";
	private static final int TEXT_SIZE = 30;
	private static final Color MENU_COLOR = new Color(0, 255, 0);

	private static final int DEF_WIDTH = 600;
	private static final int DEF_HEIGHT = 450;

	private Timer gameTimer;

	private GameModel wall;

	private String message;

	private boolean showPauseMenu;

	private Font menuFont;

	private Rectangle continueButtonRect;
	private Rectangle exitButtonRect;
	private Rectangle restartButtonRect;
	private int strLen;
	private GameController owner;

	private DebugConsole debugConsole;
	Image icon = new ImageIcon(getClass().getResource("/resources/galaxy2.gif")).getImage();

	public GameView(JFrame owner) {
		super();

		strLen = 0;
		showPauseMenu = false;

		this.owner = (GameController) owner;

		menuFont = new Font("Monospaced", Font.PLAIN, TEXT_SIZE);

		this.initialize();
		message = "";
		wall = new GameModel(new Rectangle(0, 0, DEF_WIDTH, DEF_HEIGHT), 30, 3, 6 / 2, new Point(300, 430));

		debugConsole = new DebugConsole(owner, wall, this);
		// initialize the first level
		wall.nextLevel();

		gameTimer = new Timer(10, e -> {
			wall.move();
			wall.findImpacts();

			message = String.format("Bricks: %d Balls: %d Score: %d", wall.getBrickCount(), wall.getBallCount(),
					wall.getScoreCount()); // Add Score View
			if (wall.isBallLost()) {
				if (wall.ballEnd()) {

					wall.wallReset();
					message = "Game over. Score:" + wall.getScoreCount();

					String name = JOptionPane.showInputDialog("Please Enter Your Name:");

					try {
						TextFileController.nAppendToFile(name);
						TextFileController.appendToFile(wall.getScoreCount());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				wall.player.resetWidth(155);
				wall.resetScoreCount();
				wall.ballReset();
				gameTimer.stop();
			} else if (wall.isDone()) {
				if (wall.hasLevel()) {
					message = "Go to Next Level";
					gameTimer.stop();
					wall.ballReset();
					wall.wallReset();
					wall.nextLevel();
				} else {
					message = "ALL WALLS DESTROYED! Score:" + wall.getScoreCount();

					String name = JOptionPane.showInputDialog("Please Enter Your Name:");

					try {
						TextFileController.nAppendToFile(name);
						TextFileController.appendToFile(wall.getScoreCount());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					wall.player.resetWidth(155);
					wall.ballReset();
					gameTimer.stop();
					wall.resetScoreCount();
				}
			}

			repaint();
		});

	}

	/**
	 * Initialize GameView as JComponent with DEF_WIDTH and DEF_HEIGHT, also adding
	 * key,mouse,mouseMotion Listener as well.
	 */
	private void initialize() {
		this.setPreferredSize(new Dimension(DEF_WIDTH, DEF_HEIGHT));
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	/**
	 * paint is an Overridden Method from the JComponent class. paint method is to
	 * draw the GameView. This method will draw the ball, player wall and pause
	 * screen in the game-screen.
	 * 
	 * @param g Graphic.
	 */
	public void paint(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		drawBackground(g2d);

		g2d.setColor(Color.GREEN);
		g2d.drawString(message, 250, 225);

		drawBall(wall.ball, g2d);

		drawPowerup(wall.fPowerup, wall.pPowerup, g2d);

		for (Brick b : wall.bricks)
			if (!b.isBroken())
				drawBrick(b, g2d);

		drawPlayer(wall.player, g2d);

		if (showPauseMenu)
			drawMenu(g2d);

		Toolkit.getDefaultToolkit().sync();
	}

	/**
	 * The drawBackground method is to draw the background image for the game.
	 * 
	 * @param g2d Graphics2D
	 */
	private void drawBackground(Graphics2D g2d) {
		g2d.drawImage(icon, 1, 1, getWidth(), getHeight(), this);
	}

	/**
	 * The drawBrick method is to draw the brick in the game. The drawBrick method
	 * also sets and draw the brick color, shape and type.
	 * 
	 * @param brick Brick
	 * @param g2d   Graphics2D
	 */
	private void drawBrick(Brick brick, Graphics2D g2d) {
		Color tmp = g2d.getColor();

		g2d.setColor(brick.getInnerColor());
		g2d.fill(brick.getBrick());

		g2d.setColor(brick.getBorderColor());
		g2d.draw(brick.getBrick());

		g2d.setColor(tmp);
	}

	/**
	 * The drawPowerup method is to draw both (Fire and Paddle) Powerup in the
	 * screen.
	 * 
	 * @param powerup  FirePowerup
	 * @param pPowerup PlayerPowerup
	 * @param g2d      Graphics2D
	 */
	private void drawPowerup(FirePowerup powerup, PlayerPowerup pPowerup, Graphics2D g2d) {
		Color tmp = g2d.getColor();

		Shape s = powerup.getMultiFace();
		Shape s2 = pPowerup.getMultiFace();

		g2d.drawImage(powerup.getImage(), 150, 250, 25, 25, null);
		g2d.drawImage(pPowerup.getImage(), 400, 250, 25, 25, null);

		g2d.setColor(tmp);
	}

	/**
	 * The drawBall method is to draw the ball to the in-game screen. Draws the
	 * ball's color and shape
	 * 
	 * @param ball Ball Object Variable.
	 * @param g2d
	 */
	private void drawBall(Ball ball, Graphics2D g2d) {
		Color tmp = g2d.getColor();

		Shape s = ball.getBallFace();

		g2d.setColor(ball.getInnerColor());
		g2d.fill(s);

		g2d.setColor(ball.getBorderColor());
		g2d.draw(s);

		g2d.setColor(tmp);
	}

	/**
	 * The drawPlayer method is to draw the player paddle to the in-game screen.
	 * Draws the player paddle's color and shape.
	 * 
	 * @param p   Player Object Variable.
	 * @param g2d
	 */
	private void drawPlayer(Player p, Graphics2D g2d) {
		Color tmp = g2d.getColor();

		Shape s = p.getPlayerFace();
		g2d.setColor(Player.INNER_COLOR);
		g2d.fill(s);

		g2d.setColor(Player.BORDER_COLOR);
		g2d.draw(s);

		g2d.setColor(tmp);
	}

	/**
	 * The drawMenu method calls obscureGameBoard and drawPauseMenu method to draw
	 * the Pause Menu screen. Draws the Pause Menu screen and background. Draws the
	 * text and buttons as well.
	 * 
	 * @param g2d
	 */
	private void drawMenu(Graphics2D g2d) {
		g2d.drawImage(icon, 1, 1, DEF_WIDTH, DEF_HEIGHT, this);
		obscureGameBoard(g2d);
		drawPauseMenu(g2d);
	}

	/**
	 * The obscureGameBoard method draws te Pause Menu container and set the
	 * properties. Handles the transparency of the screen.
	 * 
	 * @param g2d
	 */
	private void obscureGameBoard(Graphics2D g2d) {

		Composite tmp = g2d.getComposite();
		Color tmpColor = g2d.getColor();

		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.55f);
		g2d.setComposite(ac);

		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, DEF_WIDTH, DEF_HEIGHT);

		g2d.setComposite(tmp);
		g2d.setColor(tmpColor);
	}

	/**
	 * drawPauseMenu is a private method that draws the fonts on the Pause Menu
	 * Screen. Draws the CONTINUE, RESTART and MAIN MENU buttons.
	 * 
	 * @param g2d
	 */
	private void drawPauseMenu(Graphics2D g2d) {
		Font tmpFont = g2d.getFont();
		Color tmpColor = g2d.getColor();

		g2d.setFont(menuFont);
		g2d.setColor(MENU_COLOR);

		if (strLen == 0) {
			FontRenderContext frc = g2d.getFontRenderContext();
			strLen = menuFont.getStringBounds(PAUSE, frc).getBounds().width;
		}

		int x = (this.getWidth() - strLen) / 2;
		int y = this.getHeight() / 10;

		g2d.drawString(PAUSE, x, y);

		x = this.getWidth() / 8;
		y = this.getHeight() / 4;

		if (continueButtonRect == null) {
			FontRenderContext frc = g2d.getFontRenderContext();
			continueButtonRect = menuFont.getStringBounds(CONTINUE, frc).getBounds();
			continueButtonRect.setLocation(x, y - continueButtonRect.height);
		}

		g2d.drawString(CONTINUE, x, y);

		y *= 2;

		if (restartButtonRect == null) {
			restartButtonRect = (Rectangle) continueButtonRect.clone();
			restartButtonRect.setLocation(x, y - restartButtonRect.height);
		}

		g2d.drawString(RESTART, x, y);

		y *= 3.0 / 2;

		if (exitButtonRect == null) {
			exitButtonRect = (Rectangle) continueButtonRect.clone();
			exitButtonRect.setLocation(x, y - exitButtonRect.height);
		}

		g2d.drawString(MENU, x, y);

		g2d.setFont(tmpFont);
		g2d.setColor(tmpColor);
	}

	@Override
	public void keyTyped(KeyEvent keyEvent) {
	}

	/**
	 * The keyPressed method implements the method in KeyListener. Listen and act
	 * when a key is pressed. A = Player move left D = Player move right SPACE =
	 * Start/Pause ESC = Pause Menu
	 * 
	 * @param keyEvent To detect if key has acted.
	 */
	@Override
	public void keyPressed(KeyEvent keyEvent) {
		switch (keyEvent.getKeyCode()) {
		case KeyEvent.VK_A:
			wall.player.moveLeft();
			break;
		case KeyEvent.VK_D:
			wall.player.movRight();
			break;
		case KeyEvent.VK_ESCAPE:
			showPauseMenu = !showPauseMenu;
			repaint();
			gameTimer.stop();
			break;
		case KeyEvent.VK_SPACE:
			if (!showPauseMenu)
				if (gameTimer.isRunning())
					gameTimer.stop();
				else
					gameTimer.start();
			break;
		case KeyEvent.VK_F1:
			if (keyEvent.isAltDown() && keyEvent.isShiftDown())
				debugConsole.setVisible(true);
		default:
			wall.player.stop();
		}
	}

	/**
	 * The keyReleased method implements the method in KeyListener. To detect when
	 * to stop the player paddle's movement.
	 * 
	 * @param keyEvent To detect if the key has acted.
	 */
	@Override
	public void keyReleased(KeyEvent keyEvent) {
		wall.player.stop();
	}

	/**
	 * The mouseClicked method implements the method in MouseListener. To act when
	 * player's mouse click on either buttons.
	 * 
	 * @param mouseEvent To detect if mouse has acted.
	 */
	@Override
	public void mouseClicked(MouseEvent mouseEvent) {
		Point p = mouseEvent.getPoint();
		if (!showPauseMenu)
			return;
		if (continueButtonRect.contains(p)) {
			BackgroundMusic.play("ButtonEffect");
			showPauseMenu = false;
			repaint();
		} else if (restartButtonRect.contains(p)) {
			BackgroundMusic.play("ButtonEffect");
			message = "Restarting Game...";
			wall.ballReset();
			wall.resetScoreCount();
			wall.player.width = 155;
			wall.wallReset();
			showPauseMenu = false;
			repaint();
		} else if (exitButtonRect.contains(p)) {
			BackgroundMusic.stop("GameTheme");
			BackgroundMusic.loop("MenuMusic");
			BackgroundMusic.play("ButtonEffect");
			owner.enableHomeMenu();
		}

	}

	@Override
	public void mousePressed(MouseEvent mouseEvent) {

	}

	@Override
	public void mouseReleased(MouseEvent mouseEvent) {

	}

	@Override
	public void mouseEntered(MouseEvent mouseEvent) {

	}

	@Override
	public void mouseExited(MouseEvent mouseEvent) {

	}

	@Override
	public void mouseDragged(MouseEvent mouseEvent) {

	}

	/**
	 * The mouseMoved method implements the method in MouseListener. To set how a
	 * mouse should look on screen when it hovers over any buttons in the pause menu
	 * screen.
	 * 
	 * @param mouseEvent To detect if a mouse acted or not.
	 */
	@Override
	public void mouseMoved(MouseEvent mouseEvent) {
		Point p = mouseEvent.getPoint();
		if (exitButtonRect != null && showPauseMenu) {
			if (exitButtonRect.contains(p) || continueButtonRect.contains(p) || restartButtonRect.contains(p))
				this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			else
				this.setCursor(Cursor.getDefaultCursor());
		} else {
			this.setCursor(Cursor.getDefaultCursor());
		}
	}

	/**
	 * The onLostFocus method implements what should appear during focus lost. Game
	 * Timer will be stopped. Display Focus Lost on screen. Repaint the screen.
	 * 
	 */
	public void onLostFocus() {
		gameTimer.stop();
		message = "Focus Lost";
		repaint();
	}

}
