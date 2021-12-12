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


/**
 * Wall class is responsible for all the implementations on the wall,ball and the impacts.
 */
public class Wall {

    private static final int LEVELS_COUNT = 4;

    private static final int CLAY = 1;
    private static final int STEEL = 2;
    private static final int CEMENT = 3;

    private Rectangle area;

    public Brick[] bricks;
    public Ball ball;
    public Player player;
    public FirePowerup fPowerup;
    public PlayerPowerup pPowerup;

    private Brick[][] levels;
    private int level;

    private Point startPoint;
    private int brickCount;
    private int ballCount;
    private int scoreCount;
    private boolean ballLost;
    private boolean pPowerCount = false;
    
    public int speedX = 3;
    public int speedY = -3;
    
    /**
     * construct for class Wall which initialize and set values for startPoint, levels, ballCount, area
     *
     * @param drawArea Rectangle GameFrame where game is rendered/drawn
     * @param brickCount number of bricks in wall(30)
     * @param lineCount lines of bricks in wall(3)
     * @param brickDimensionRatio width-to-height ratio of a single brick
     * @param ballPos position of ball(300, 430)
     */
    public Wall(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, Point ballPos){

        this.startPoint = new Point(ballPos);

        levels = makeLevels(drawArea,brickCount,lineCount,brickDimensionRatio);
        level = 0;
        
        ballCount = 3;
        ballLost = false;



        makeBall(ballPos);
        do{
        	
        	speedX = 3;
        	
        }while(speedX == 0);
        do{
        	
        	speedY = -3;
        	
        }while(speedY == 0);

        ball.setSpeed(speedX,speedY);
        
        fPowerup = new FirePowerup();
        
        pPowerup = new PlayerPowerup();

        player = new Player((Point) ballPos.clone(),10, drawArea);

        area = drawArea;


    }
    
    /**
     * make wall of clay bricks for the first level
     *
     * @param drawArea Rectangle GameFrame where game is rendered/drawn
     * @param brickCnt number of bricks in wall(30)
     * @param lineCnt lines of bricks in wall(3)
     * @param brickSizeRatio width-to-height ratio of a single brick
     * @param type types of brick
     * @return array with 31 bricks
     */
    private Brick[] makeSingleTypeLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int type){
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */
        brickCnt -= brickCnt % lineCnt;

        int brickOnLine = brickCnt / lineCnt;

        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;

        brickCnt += lineCnt / 2;

        Brick[] tmp  = new Brick[brickCnt];

        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt);
        Point p = new Point();

        int i;
        for(i = 0; i < tmp.length; i++){
            int line = i / brickOnLine;
            if(line == lineCnt)
                break;
            double x = (i % brickOnLine) * brickLen;
            x =(line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p.setLocation(x,y);
            tmp[i] = makeBrick(p,brickSize,type);
        }

        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt){
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x,y);
            tmp[i] = new ClayBrick(p,brickSize);
        }
        return tmp;

    }
    
    /**
     * make a wall with 2 types of bricks arranged in a chessboard pattern for level 2, 3, 4
     *
     * @param drawArea Rectangle GameFrame where game is rendered/drawn
     * @param brickCnt number of bricks in wall(30)
     * @param lineCnt lines of bricks in wall(3)
     * @param brickSizeRatio width-to-height ratio of a single brick
     * @param typeA first type of brick
     * @param typeB second type of brick
     * @return array with 31 bricks
     */
    private Brick[] makeChessboardLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int typeA, int typeB){
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */
        brickCnt -= brickCnt % lineCnt;

        int brickOnLine = brickCnt / lineCnt;

        int centerLeft = brickOnLine / 2 - 1;
        int centerRight = brickOnLine / 2 + 1;

        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;

        brickCnt += lineCnt / 2;

        Brick[] tmp  = new Brick[brickCnt];

        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt);
        Point p = new Point();

        int i;
        for(i = 0; i < tmp.length; i++){
            int line = i / brickOnLine;
            if(line == lineCnt)
                break;
            int posX = i % brickOnLine;
            double x = posX * brickLen;
            x =(line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p.setLocation(x,y);

            boolean b = ((line % 2 == 0 && i % 2 == 0) || (line % 2 != 0 && posX > centerLeft && posX <= centerRight));
            tmp[i] = b ?  makeBrick(p,brickSize,typeA) : makeBrick(p,brickSize,typeB);
        }

        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt){
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x,y);
            tmp[i] = makeBrick(p,brickSize,typeA);
        }
        return tmp;
    }
    
    /**
     * Construct a new rubber ball
     * @param ballPos position of ball
     */
    private void makeBall(Point2D ballPos){
        ball = new RubberBall(ballPos);
    }
    
    /**
     * makeStripesLevel creates a level using 3 types of bricks, where each row of bricks is its own type
     * @param drawArea Rectangle GameFrame where game is rendered/drawn
     * @param brickCnt brickCnt number of bricks in wall(30)
     * @param lineCnt lines of bricks in wall(3)
     * @param brickSizeRatio width-to-height ratio of a single brick
     * @param typeA first type of brick
     * @param typeB second type of brick
     * @return array with 31 bricks
     * @return 
     */
    private Brick[] makeStripesLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int typeA, int typeB, int typeC){
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */
        brickCnt -= brickCnt % lineCnt;

        int brickOnLine = brickCnt / lineCnt;


        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;

        brickCnt += lineCnt / 2;

        Brick[] tmp  = new Brick[brickCnt];

        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt);
        Point p = new Point();

        int i;
        for(i = 0; i < tmp.length; i++){
            int line = i / brickOnLine;
            if(line == lineCnt)
                break;
            int posX = i % brickOnLine;
            double x = posX * brickLen;
            x =(line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p.setLocation(x,y);

            if(line % 3 == 0){
                tmp[i] = makeBrick(p,brickSize,typeA);
            }else if(line % 3 == 1){
                tmp[i] = makeBrick(p,brickSize,typeB);
            }else{
                tmp[i] = makeBrick(p,brickSize,typeC);
            }
        }

        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt){
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x,y);
            if(y%3 == 0) {
                tmp[i] = makeBrick(p, brickSize, typeA);
            }else if(y%3 == 1){
                tmp[i] = makeBrick(p, brickSize, typeB);
            }else{
                tmp[i] = makeBrick(p,brickSize, typeC);
            }
        }
        return tmp;
    }
    
    /**
     * makeLevels is a Private Method that creates the wall based on the levels.
     * @param drawArea      a rectangular area for the wall.
     * @param brickCount    the number of bricks.
     * @param lineCount     the number of rows of bricks on the wall.
     * @param brickDimensionRatio   the brick dimension.
     * @return      returns a wall. Either Single type wall or Chessboard wall.
     */
    private Brick[][] makeLevels(Rectangle drawArea,int brickCount,int lineCount,double brickDimensionRatio){
        Brick[][] tmp = new Brick[LEVELS_COUNT][];
        tmp[0] = makeSingleTypeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY);
        tmp[1] = makeChessboardLevel(drawArea,40,4,brickDimensionRatio,CLAY,CEMENT);
        tmp[2] = makeChessboardLevel(drawArea,50,5,brickDimensionRatio,STEEL,CEMENT);
        tmp[3] = makeStripesLevel(drawArea,60,6,brickDimensionRatio,CLAY,STEEL,CEMENT);
        tmp[3] = makeStripesLevel(drawArea,70,7,brickDimensionRatio,CEMENT,STEEL,CEMENT);
        
        return tmp;
    }
    
    /**
     * move Method calls the move methods in the Player and Ball classes.
     */
    public void move(){
        player.move();
        ball.move();
    }

    /**
     * findImpacts Method is responsible for implementing all ball and brick properties when impact is made.
     * Implements when ball makes impact with the player.
     * Implements when ball makes impact with the powerups.
     * Implements when ball makes impact with the wall. Calls the impactWall() method.
     * Implements when ball makes impact with the game frame/border.
     */
    public void findImpacts(){
    	BackgroundMusic.load("/SFX/BrickBounce.mp3", "BrickBounce");
    	BackgroundMusic.setVolume("BrickBounce",-25);
    	BackgroundMusic.load("/SFX/Powerup1.mp3", "Powerup1");
    	BackgroundMusic.setVolume("Powerup1",-15);
    	BackgroundMusic.load("/SFX/Powerup2.mp3", "Powerup2");
    	BackgroundMusic.setVolume("Powerup2",-15);
        if(player.impact(ball)){
            ball.reverseY();
        }
        else if(fPowerup.impact(ball)){
        	BackgroundMusic.play("Powerup1");
        	ball.setFireball(3);
        	
        }
        else if(pPowerup.impact(ball)) {
        	
        	if(player.getWidth()<230){
	        	if(pPowerCount == false) {
	            	BackgroundMusic.play("Powerup2");
	        		player.setWidth(20);
	        		pPowerCount = true;
	        	}
        	}
        	System.out.println(player.width);
        
        }
        else if(impactWall()){
            /*for efficiency reverse is done into method impactWall
            * because for every brick program checks for horizontal and vertical impacts
            */
        	BackgroundMusic.play("BrickBounce");
        	pPowerCount = false;
            brickCount--;
            scoreCount += 5;
            
        }
        else if(impactBorder()) {
        	pPowerCount = false;
            ball.reverseX();
        }
        else if(ball.getPosition().getY() < area.getY()){
            ball.reverseY();
        }
        else if(ball.getPosition().getY() > area.getY() + area.getHeight()){
            ballCount--;
            ballLost = true;
        }
    }
    
    
    /**
     * impactWall is a Private Method that is responsible for when the ball makes impact with the wall.
     * If ball gets the fire powerup, then ball will not bounce back when it impacts the bricks.
     * @return      returns a boolean value to denote if ball made impact with wall or not.
     */
    private boolean impactWall(){
        for(Brick b : bricks){
        	if(ball.checkFire() == false) {
            switch(b.findImpact(ball)) {
                //Vertical Impact
                case Brick.UP_IMPACT:
                    ball.reverseY();
                    return b.setImpact(ball.down, Brick.Crack.UP);
                case Brick.DOWN_IMPACT:
                    ball.reverseY();
                    return b.setImpact(ball.up,Brick.Crack.DOWN);

                //Horizontal Impact
                case Brick.LEFT_IMPACT:
                    ball.reverseX();
                    return b.setImpact(ball.right,Brick.Crack.RIGHT);
                case Brick.RIGHT_IMPACT:
                    ball.reverseX();
                    return b.setImpact(ball.left,Brick.Crack.LEFT);
            }
          }
          if(ball.checkFire() == true)
          {
        	  switch(b.findImpact(ball)) {
              //Vertical Impact
              case Brick.UP_IMPACT:
                  return b.setImpact(ball.down, Brick.Crack.UP);
              case Brick.DOWN_IMPACT:
                  return b.setImpact(ball.up,Brick.Crack.DOWN);

              //Horizontal Impact
              case Brick.LEFT_IMPACT:
                  return b.setImpact(ball.right,Brick.Crack.RIGHT);
              case Brick.RIGHT_IMPACT:
                  return b.setImpact(ball.left,Brick.Crack.LEFT);
          }
        	  
          }
        	
        }
        return false;
    }
    
    /**
     * impactBorder is a Private Method that implements when the ball makes impact with game border.
     * @return      returns a boolean value to denote if ball made impact with the border or not.
     */
    private boolean impactBorder(){
        Point2D p = ball.getPosition();
        return ((p.getX() < area.getX()) ||(p.getX() > (area.getX() + area.getWidth())));
    }
    
    /**
     * getBrickCount is a Getter Method.
     * @return  returns the number of bricks.
     */
    public int getBrickCount(){
        return brickCount;
    }
    
    /**
     * getBallCount is a Getter Method.
     * @return  returns the number of balls.
     */
    public int getBallCount(){
        return ballCount;
    }
    
    /**
     * getScoreCount is a Getter Method.
     * @return  returns the scoreCount.
     */
    public int getScoreCount() {
    	return scoreCount;
    }
    
    /**
     * getter for ballLost
     * @return boolean value of ballLost
     */
    public boolean isBallLost(){
        return ballLost;
    }
    
    /**
     * it resets the position of player bar and ball
     * it then resets the direction of ball movement, and ballLost to false
     */
    public void ballReset(){
        player.moveTo(startPoint);
        ball.moveTo(startPoint);
        ball.setSpeed(speedX,speedY);
        ballLost = false;
    }
    
    /**
     * it restores all the brick
     * it also reset the ballCount to 3 again
     */
    public void wallReset(){
        for(Brick b : bricks)
            b.repair();
        brickCount = bricks.length;
        ballCount = 3;
    }
    
    /**
     * @return true if number of balls is 0
     */
    public boolean ballEnd(){
        return ballCount == 0;
    }
    
    /**
     * @return true if number of bricks is 0
     */
    public boolean isDone(){
        return brickCount == 0;
    }
    
    /**
     * it goes to the next level, and it also restores the brickCount back to 31
     * increases ball speed, but speed cap at 4.
     */
    public void nextLevel(){
        bricks = levels[level++];
        this.brickCount = bricks.length;
        
        if(speedX<4) {        
            speedX += 1;
            speedY -= 1;
            ball.setSpeed(speedX,speedY);
        }

        
        
    }
    
    /**
     * returns True if there is level remaining (4 levels in total)
     * @return boolean True or False
     */
    public boolean hasLevel(){
        return level < levels.length;
    }
    
    /**
     * setter for ball's speed X
     * @param s integer value for speedX
     */
    public void setBallXSpeed(int s){
        ball.setXSpeed(s);
    }
    
    /**
     * setter for ball's speed Y
     * @param s integer value for speedY
     */
    public void setBallYSpeed(int s){
        ball.setYSpeed(s);
    }
    
    /**
     * reset number of balls to 3
     */
    public void resetBallCount(){
        ballCount = 3;
    }
    
    
    /**
     * make brick based on type.
     * 
     * @param point		
     * @param size
     * @param type
     * @return
     */
    private Brick makeBrick(Point point, Dimension size, int type){
        Brick out;
        switch(type){
            case CLAY:
                out = new ClayBrick(point,size);
                break;
            case STEEL:
                out = new SteelBrick(point,size);
                break;
            case CEMENT:
                out = new CementBrick(point, size);
                break;
            default:
                throw  new IllegalArgumentException(String.format("Unknown Type:%d\n",type));
        }
        return  out;
    }

}


