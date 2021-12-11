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
package test.View;

import javax.swing.*;

import test.Controller.GameFrame;
import test.Model.BackgroundMusic;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;


/**
 * HomeMenu class comes after Intro class.
 * Displays the Game Title, Start, Instruction, Scoreboard, Exit;
 */
public class HomeMenu extends JComponent implements MouseListener, MouseMotionListener {
	
    private static final String GAME_TITLE = "BRICK DESTROY";
    private static final String CREDITS = "Version Beta";
    private static final String START_TEXT = "START";
    private static final String INSTRUCT_TEXT = "INSTRUCTION";
    private static final String SCORE_TEXT = "SCOREBOARD";
    private static final String MENU_TEXT = "EXIT";

    private static final Color BG_COLOR = Color.BLACK;
    private static final Color BORDER_COLOR = new Color(200,8,21); //Venetian Red
    private static final Color DASH_BORDER_COLOR = new  Color(255, 216, 0);//school bus yellow
    private static final Color TEXT_COLOR = Color.GRAY.brighter();
    private static final Color CLICKED_BUTTON_COLOR = Color.GRAY;
    private static final Color CLICKED_TEXT = Color.GRAY.darker();
    private static final int BORDER_SIZE = 5;
    private static final float[] DASHES = {12,6};

    private Rectangle menuFace;
    private Rectangle startButton;
    private Rectangle instructButton;
    private Rectangle scoreButton;
    private Rectangle menuButton;


    private BasicStroke borderStoke;
    private BasicStroke borderStoke_noDashes;

    private Font gameTitleFont;
    private Font creditsFont;
    private Font buttonFont;

    private GameFrame owner;
 
    private boolean startClicked;
    private boolean instructClicked;
    private boolean scoreClicked;
    private boolean menuClicked;
    
    Image icon = new ImageIcon(getClass().getResource("/resources/galaxy1.gif")).getImage();
    
    /**
     * Constructor for class HomeMenu that construct HomeMenu as a JComponent
     * Set the values for buttons size, font size, font style
     *
     * @param owner GameFrame
     * @param area Dimension size for HomeMenu
     */
    public HomeMenu(GameFrame owner,Dimension area){
    	
    	BackgroundMusic.init();
        this.setFocusable(true);
        this.requestFocusInWindow();

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        this.owner = owner;

        

        menuFace = new Rectangle(new Point(0,0),area);
        this.setPreferredSize(area);

        Dimension btnDim = new Dimension(area.width / 3, area.height / 12);
        startButton = new Rectangle(btnDim);
        instructButton =  new Rectangle(btnDim);
        scoreButton =  new Rectangle(btnDim);
        menuButton = new Rectangle(btnDim);

        borderStoke = new BasicStroke(BORDER_SIZE,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,0,DASHES,0);
        borderStoke_noDashes = new BasicStroke(BORDER_SIZE,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);

        gameTitleFont = new Font("Helvetica Neue",Font.BOLD,40);
        creditsFont = new Font("Helvetica Neue",Font.BOLD,12);
        buttonFont = new Font("Helvetica Neue",Font.PLAIN,startButton.height-4);
   
    }

    /**
     * paint is an Overridden Method from the JComponent class.
     * Method to invoke the painting of the HomeMenu page.
     * Calls the drawMenu method.
     * @param g
     */
    public void paint(Graphics g){
        drawMenu((Graphics2D)g);
    }

    /**
     * draws component on menuFace by calling the following method: drawContainer, drawText, drawButton
     * set background image of home menu.
     * @param g2d graphics2D object
     */
    public void drawMenu(Graphics2D g2d){

        drawContainer(g2d);
        
        g2d.drawImage(icon, 1, 1, (int)(menuFace.getWidth()), (int)(menuFace.getHeight()), this);
        /*
        all the following method calls need a relative
        painting directly into the HomeMenu rectangle,
        so the translation is made here so the other methods do not do that.
         */
        Color prevColor = g2d.getColor();
        Font prevFont = g2d.getFont();

        double x = menuFace.getX();
        double y = menuFace.getY();

        g2d.translate(x,y);

        //methods calls
        drawText(g2d);
        drawButton(g2d);
        //end of methods calls

        g2d.translate(-x,-y);
        g2d.setFont(prevFont);
        g2d.setColor(prevColor);
    }
    
    /**
     * draw the background, and border for HomeMenu
     * @param g2d graphics2D object
     */
    private void drawContainer(Graphics2D g2d){
        Color prev = g2d.getColor();

        g2d.setColor(BG_COLOR);
        g2d.fill(menuFace);

        Stroke tmp = g2d.getStroke();

        g2d.setStroke(borderStoke_noDashes);
        g2d.setColor(DASH_BORDER_COLOR);
        g2d.draw(menuFace);

        g2d.setStroke(borderStoke);
        g2d.setColor(BORDER_COLOR);
        g2d.draw(menuFace);

        g2d.setStroke(tmp);

        g2d.setColor(prev);
    }
    
    /**
     * draw the texts, greetings, gameTitle, credits in specific location on HomeMenu
     * @param g2d graphics2D object
     */
    private void drawText(Graphics2D g2d){

        g2d.setColor(TEXT_COLOR);

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D gameTitleRect = gameTitleFont.getStringBounds(GAME_TITLE,frc);
        Rectangle2D creditsRect = creditsFont.getStringBounds(CREDITS,frc);

        int sX,sY;
        
        sY = (int)(menuFace.getHeight() / 4);
        sX = (int)(menuFace.getWidth() - gameTitleRect.getWidth()) / 2;
        sY += (int) gameTitleRect.getHeight() * 1.1;//add 10% of String height between the two strings

        g2d.setFont(gameTitleFont);
        g2d.drawString(GAME_TITLE,sX,sY);

        sX = (int)(menuFace.getWidth() - creditsRect.getWidth()) / 2;
        sY += (int) creditsRect.getHeight() * 2;

        g2d.setFont(creditsFont);
        g2d.drawString(CREDITS,sX,sY);


    }
    
    /**
     * draw the buttons, start, instruction, scoreboard, exit in order on HomeMenu screen
     * @param g2d graphics2D object
     */
    private void drawButton(Graphics2D g2d){

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D txtRect = buttonFont.getStringBounds(START_TEXT,frc);
        Rectangle2D insTxtRect = buttonFont.getStringBounds(INSTRUCT_TEXT,frc);
        Rectangle2D scrTxtRect = buttonFont.getStringBounds(SCORE_TEXT,frc);
        Rectangle2D mTxtRect = buttonFont.getStringBounds(MENU_TEXT,frc);

        g2d.setFont(buttonFont);

        int x = (menuFace.width - startButton.width) / 6;
        int y =(int) ((menuFace.height - startButton.height) * 0.8);

        
        //Start Button
        startButton.setLocation(x,y);

        x = (int)(startButton.getWidth() - txtRect.getWidth()) / 2;
        y = (int)(startButton.getHeight() - txtRect.getHeight()) / 2;

        x += startButton.x;
        y += startButton.y + (startButton.height * 0.9);




        if(startClicked){
            Color tmp = g2d.getColor();
            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(startButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(START_TEXT,x,y);
            g2d.setColor(tmp);
        }
        else{
            g2d.draw(startButton);
            g2d.drawString(START_TEXT,x,y);
        }

        x = startButton.x;
        y = startButton.y;

        y *= 1.2;

        //Instruction Button
        instructButton.setLocation(x,y);
        
        x = (int)(instructButton.getWidth() - insTxtRect.getWidth()) / 2;
        y = (int)(instructButton.getHeight() - insTxtRect.getHeight()) / 2;
        
        x += instructButton.x;
        y += instructButton.y + (instructButton.height * 0.9);
        
        if(instructClicked){
            Color tmp = g2d.getColor();
            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(instructButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(INSTRUCT_TEXT,x,y);
            g2d.setColor(tmp);
        }
        else{
            g2d.draw(instructButton);
            g2d.drawString(INSTRUCT_TEXT,x,y);
        }
      
        
        x = (int) ((menuFace.width - menuButton.width) / 1.3);
        y = (int) ((menuFace.height - menuButton.height) * 0.8);
        
        //Scoreboard Button
        scoreButton.setLocation(x,y);
        
        x = (int)(scoreButton.getWidth() - scrTxtRect.getWidth()) / 2;
        y = (int)(scoreButton.getHeight() - scrTxtRect.getHeight()) / 2;
        
        x += scoreButton.x;
        y += scoreButton.y + (scoreButton.height * 0.9);
        
        if(scoreClicked){
            Color tmp = g2d.getColor();
            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(scoreButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(SCORE_TEXT,x,y);
            g2d.setColor(tmp);
        }
        else{
            g2d.draw(scoreButton);
            g2d.drawString(SCORE_TEXT,x,y);
        }
        
        x = (int) ((menuFace.width - menuButton.width) / 1.3);
        y = (int) ((menuFace.height - menuButton.height) * 0.8);
        
        y *= 1.2;
      
        //Exit Button
        menuButton.setLocation(x,y);
        
        x = (int)(menuButton.getWidth() - mTxtRect.getWidth()) / 2;
        y = (int)(menuButton.getHeight() - mTxtRect.getHeight()) / 2;

        x += menuButton.x;
        y += menuButton.y + (startButton.height * 0.9);

        if(menuClicked){
            Color tmp = g2d.getColor();

            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(menuButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(MENU_TEXT,x,y);
            g2d.setColor(tmp);
        }
        else{
            g2d.draw(menuButton);
            g2d.drawString(MENU_TEXT,x,y);
        }
         
    }
   
    /**
     * method is only activated when buttons are clicked
     * if startButton is clicked then enableGameBoard is activated
     * if instructButton is clicked, then enableInstructMenu is activated
     * if scoreButton is clicked, then enableScoreboardMenu is activated
     * if menuButton is clicked, then print "Goodbye + System.getProperty("user.name")"
     * every click will play button sound effect.
     * @param mouseEvent mouse action
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p)){
        	BackgroundMusic.play("ButtonEffect");
        	BackgroundMusic.stop("MenuMusic");
        	BackgroundMusic.load("/SFX/GameTheme.mp3", "GameTheme");
        	BackgroundMusic.setVolume("GameTheme", -20);
        	BackgroundMusic.loop("GameTheme");
        	owner.enableGameBoard();
        	

        }
        else if(instructButton.contains(p)){
        	BackgroundMusic.play("ButtonEffect");
            owner.enableInstructMenu();

         }
        else if(scoreButton.contains(p)){
        	BackgroundMusic.play("ButtonEffect");
            owner.enableScoreboardMenu();

         }
        else if(menuButton.contains(p)){
        	BackgroundMusic.play("ButtonEffect");
            System.out.println("Goodbye " + System.getProperty("user.name"));
            System.exit(0);
        }
    }
    
    /**
     * if either buttons are pressed, then set (start/menu/info)Clicked to true, then repaint the button to white
     * @param mouseEvent mouse action
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p)){
            startClicked = true;
            repaint(startButton.x,startButton.y,startButton.width+1,startButton.height+1);

        }
        else if(instructButton.contains(p)) {
        	instructClicked = true;
        	repaint(instructButton.x,instructButton.y,instructButton.width+1,instructButton.height+1);
        }
        else if(scoreButton.contains(p)) {
        	scoreClicked = true;
        	repaint(scoreButton.x,scoreButton.y,scoreButton.width+1,scoreButton.height+1);
        }
        
        else if(menuButton.contains(p)){
            menuClicked = true;
            repaint(menuButton.x,menuButton.y,menuButton.width+1,menuButton.height+1);
        }
    }
    
    /**
     * mouseReleased Method invoked when the mouse is released.
     * @param mouseEvent    to indicate if a mouse action has occurred or not.
     */
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if(startClicked ){
            startClicked = false;
            repaint(startButton.x,startButton.y,startButton.width+1,startButton.height+1);
        }
        else if(instructClicked){
        	instructClicked = false;
            repaint(instructButton.x,instructButton.y,instructButton.width+1,instructButton.height+1);
        }
        else if(scoreClicked){
        	scoreClicked = false;
            repaint(scoreButton.x,scoreButton.y,scoreButton.width+1,scoreButton.height+1);
        }
        else if(menuClicked){
            menuClicked = false;
            repaint(menuButton.x,menuButton.y,menuButton.width+1,menuButton.height+1);
        }
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
     * mouseMoved Method implements what should happen when the mouse hovers over the START or EXIT button
     * and what the cursor should look like otherwise.
     * @param mouseEvent    to indicate if a mouse action has occurred or not.
     */
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p) || menuButton.contains(p)||instructButton.contains(p)||scoreButton.contains(p))
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        else
            this.setCursor(Cursor.getDefaultCursor());

    }
}
