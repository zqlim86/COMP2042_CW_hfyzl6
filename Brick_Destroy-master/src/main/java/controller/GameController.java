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
package main.java.controller;

import javax.swing.*;

import main.java.view.GameView;
import main.java.view.HomeView;
import main.java.view.InstructMenu;
import main.java.view.IntroView;
import main.java.view.ScoreboardMenu;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.io.IOException;

/**
 * GameController is responsible for implementation of GameController window.
 */
public class GameController extends JFrame implements WindowFocusListener {

	private static final String DEF_TITLE = "Brick Destroy";

	private GameView gameBoard;
	private HomeView homeMenu;
	private InstructMenu instructMenu;
	private ScoreboardMenu scoreboardMenu;
	private IntroView intro;

	private boolean gaming;

	/**
	 * GameController is a constructor that add the Intro Page to the game frame.
	 * 
	 * @throws IOException For TextFileController
	 */
	public GameController() throws IOException {
		super();

		gaming = false;

		this.setLayout(new BorderLayout());

		gameBoard = new GameView(this);

		instructMenu = new InstructMenu(this, new Dimension(450, 350));

		scoreboardMenu = new ScoreboardMenu(this, new Dimension(450, 600));

		homeMenu = new HomeView(this, new Dimension(450, 300));

		intro = new IntroView(this, new Dimension(500, 400));

		this.add(intro, BorderLayout.CENTER);

		this.setUndecorated(true);

	}

	/**
	 * Initialize GameController with a Title.
	 */
	public void initialize() {
		this.setTitle(DEF_TITLE);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.autoLocate();
		this.setVisible(true);
	}

	/**
	 * enableHomeMenu after Intro Page. This method also removes intro page and
	 * other pages.
	 */
	public void enableHomeMenu() {
		this.dispose();
		this.remove(intro);
		this.remove(instructMenu);
		this.remove(scoreboardMenu);
		this.remove(gameBoard);
		this.add(homeMenu, BorderLayout.CENTER);
		this.setUndecorated(false);
		initialize();
		/* to avoid problems with graphics focus controller is added here */
		this.addWindowFocusListener(this);

	}

	/**
	 * enableGameBoard method will run after clicking the 'START' Button in Home
	 * Menu. This method will remove homeMenu.
	 */
	public void enableGameBoard() {
		this.dispose();
		this.remove(homeMenu);
		this.add(gameBoard, BorderLayout.CENTER);
		this.setUndecorated(false);
		initialize();
		/* to avoid problems with graphics focus controller is added here */
		this.addWindowFocusListener(this);

	}

	/**
	 * enableInstructMenu method will run after clicking the 'INSTRUCTION' Button in
	 * Home Menu. This method will remove homeMenu.
	 */
	public void enableInstructMenu() {
		this.dispose();
		this.remove(homeMenu);
		this.add(instructMenu, BorderLayout.CENTER);
		this.setUndecorated(false);
		initialize();
		/* to avoid problems with graphics focus controller is added here */
		this.addWindowFocusListener(this);

	}

	/**
	 * enableScoreboardMenu method will run after clicking the 'SCOREBOARD' Button
	 * in Home Menu. This method will remove homeMenu.
	 */
	public void enableScoreboardMenu() {
		this.dispose();
		this.remove(homeMenu);
		this.add(scoreboardMenu, BorderLayout.CENTER);
		this.setUndecorated(false);
		initialize();
		/* to avoid problems with graphics focus controller is added here */
		this.addWindowFocusListener(this);

	}

	/**
	 * Set the location of GameController relative to screenSize.
	 */
	private void autoLocate() {
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (size.width - this.getWidth()) / 2;
		int y = (size.height - this.getHeight()) / 2;
		this.setLocation(x, y);
	}

	/**
	 * If GameController is in use, set gaming to true.
	 * 
	 * @param windowEvent Window's status.
	 */
	@Override
	public void windowGainedFocus(WindowEvent windowEvent) {
		/*
		 * the first time the frame loses focus is because it has been disposed to
		 * install the GameBoard, so went it regains the focus it's ready to play. of
		 * course calling a method such as 'onLostFocus' is useful only if the GameBoard
		 * as been displayed at least once
		 */
		gaming = true;
	}

	/**
	 * If GameController is not in use but gaming is still true, stop timer and
	 * display "Focus Lost" on screen
	 * 
	 * @param windowEvent Window's status.
	 */
	@Override
	public void windowLostFocus(WindowEvent windowEvent) {
		if (gaming)
			gameBoard.onLostFocus();

	}
}
