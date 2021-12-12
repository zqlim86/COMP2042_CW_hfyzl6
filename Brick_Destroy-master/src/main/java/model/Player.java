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
import java.awt.geom.Rectangle2D;

 
/**
 * Player is an abstract class that handles the player's paddle in game.
 */
public class Player {


    public static final Color BORDER_COLOR = Color.GREEN.darker().darker();
    public static final Color INNER_COLOR = Color.GREEN;

    private static final int DEF_MOVE_AMOUNT = 5;

    public Rectangle playerFace;
    private Point ballPoint;
    private int moveAmount;
    private int min;
    private int max;
    public int width = 150;

    /**
     * Constructor of Player class
     * initialize and set value for ballpoint, moveAmount, playerFace, min, max
     *
     * @param ballPoint Position of the ball (which is always the startPoint (300, 430))
     * @param width Width of player paddle
     * @param height Height of player paddle
     * @param container Rectangle GameFrame where game is rendered/drawn
     */
    public Player(Point ballPoint,int height,Rectangle container) {
        this.ballPoint = ballPoint;
        moveAmount = 0; 
        playerFace = makeRectangle(width, height);
        min = container.x + (width / 2);
        max = min + container.width - width;

    }
    
    /**
     * Constructs a Rectangle player paddle at default location p(which is based on ballPoint),
     * so that ball is always at the middle of player paddle when program starts
     *
     * @param width Width of player paddle
     * @param height Height of player paddle
     * @return Rectangle shaped player paddle
     */
    private Rectangle makeRectangle(int width,int height){
        Point p = new Point((int)(ballPoint.getX() - (width / 2)),(int)ballPoint.getY());
        return  new Rectangle(p,new Dimension(width,height));
    }

    
    /**
     *returns True if ball impacted player paddle (ball's downside impacted player bar)
     * @param b ball, b
     * @return boolean True or False
     */
    public boolean impact(Ball b){
        return playerFace.contains(b.getPosition()) && playerFace.contains(b.down) ;
    }
    
    
    /**
     * move player paddle
     */
    public void move(){
        double x = ballPoint.getX() + moveAmount;
        if(x < min || x > max)
            return;
        ballPoint.setLocation(x,ballPoint.getY());
        playerFace.setLocation(ballPoint.x - (int)playerFace.getWidth()/2,ballPoint.y);
    }
    
    
    /**
     * move player paddle to specified point P
     * @param p specified location Point p
     */
    public void moveTo(Point p){
        ballPoint.setLocation(p);
        playerFace.setLocation(ballPoint.x - (int)playerFace.getWidth()/2,ballPoint.y);
    }
    
    /**
     * move player paddle towards left
     */
    public void moveLeft(){
        moveAmount = -DEF_MOVE_AMOUNT;
    }
    
    /**
     * move player paddle towards right
     */
    public void movRight(){
        moveAmount = DEF_MOVE_AMOUNT;
    }
    
    /**
     * stop player paddle.
     */
    public void stop(){
        moveAmount = 0;
    }
    
    
    /**
     * get width of player paddle.
     */
    public int getWidth() {
    	return width;
    }
    
    /**
     * set width of player paddle.
     */
    public void setWidth(int newWidth) {
    	width += newWidth;
    	playerFace= makeRectangle(width,10);
    	
    }

    /**
     * getter for player paddle
     * @return Rectangle playerFace
     */
    public Shape getPlayerFace(){
        return  playerFace;
    } 
    

    

}
