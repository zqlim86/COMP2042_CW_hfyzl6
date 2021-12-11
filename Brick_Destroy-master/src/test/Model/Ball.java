package test.Model;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author ziqin
 *
 */


/**
 * Ball is an abstract class that handles the ball movement in game.
 */
abstract public class Ball {

    private Shape ballFace;

    private Point2D center;

    Point2D up;
    Point2D down;
    Point2D left;
    Point2D right;

    private Color border;
    private Color inner;

    private int speedX;
    private int speedY;
    
    private Timer timer;
    private boolean setFire;
    private int setFireTime;
    

    /**
     * Ball constructor creates the ball as well as handles the ball movement 
     * Set the initial speed of ball of x and y direction to 0 at the start of the game. 
     *
     * @param center	initial location of ball.
     * @param radiusA	width of ball.
     * @param radiusB	height of ball.
     * @param inner		inner color of ball.
     * @param border	border color of ball.
     */
    public Ball(Point2D center,int radiusA,int radiusB,Color inner,Color border){
        this.center = center;

        up = new Point2D.Double();
        down = new Point2D.Double();
        left = new Point2D.Double();
        right = new Point2D.Double();

        up.setLocation(center.getX(),center.getY()-(radiusB / 2));
        down.setLocation(center.getX(),center.getY()+(radiusB / 2));

        left.setLocation(center.getX()-(radiusA /2),center.getY());
        right.setLocation(center.getX()+(radiusA /2),center.getY());


        ballFace = makeBall(center,radiusA,radiusB);
        this.border = border;
        this.inner  = inner;
        speedX = 0;
        speedY = 0;
        
        timer = new Timer();
    }

    /**
     * makeBall is an abstract method that creates the ball.
     * 
     * @param center	initial location of ball.
     * @param radiusA	width of ball.
     * @param radiusB	height of ball.
     * @return			returns ball.
     */
    protected abstract Shape makeBall(Point2D center,int radiusA,int radiusB);

    public void move(){
        RectangularShape tmp = (RectangularShape) ballFace;
        center.setLocation((center.getX() + speedX),(center.getY() + speedY));
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((center.getX() -(w / 2)),(center.getY() - (h / 2)),w,h);
        setPoints(w,h);


        ballFace = tmp;
    }
    
    /**
     * setFireball is activated when the ball picks up the fireball powerup.
     * Turns the ball into red color while the ball is still on fire.
     * 
     * @param seconds	time duration of ball being on fire.
     */
    public void setFireball(int seconds) {
    	if(!setFire) {
			setFireTime = seconds;
			setFire = true;
	        timer.schedule(new RemindTask(), seconds*1000);
	        inner = Color.RED;
		}
    }
    
    /**
     * checkFire method is to check whether the current status of the ball is on fire or not.
     * 
     * @return setFire	whether the ball is on fire.
     */
    public boolean checkFire() {
    	return setFire;
    }
    
    
    /**
     * RemindTask method is to set the color of the ball back to yellow after the time duration ends.
     *
     */
    class RemindTask extends TimerTask {
        public void run() {
        	setFire = false;
        	inner = new Color(255, 219, 88);
        }
    }
    
    /**
     * setSpeed is Setter Method to set the speed of the ball in x and y axis direction.
     * 
     * @param x		integer value of speedX.
     * @param y		integer value of speedY.
     */
    public void setSpeed(int x,int y){
        speedX = x;
        speedY = y;
    }

    /**
     * setXSpeed is Setter Method to set the speed of the in x axis direction.
     * 
     * @param s		integer value of speedX
     */
    public void setXSpeed(int s){
        speedX = s;
    }

    /**
     * setYSpeed is Setter Method to set the speed of the in y axis direction.
     * 
     * @param s		integer value of speedY
     */
    public void setYSpeed(int s){
        speedY = s;
    }
    
    /**
     * reverseX is to reverse the ball speed in x axis direction.
     * This method is for making the ball bounce in different direction after impact.
     */
    public void reverseX(){
        speedX *= -1;
    }
    
    
    /**
     * reverseY is to reverse the ball speed in y axis direction.
     * This method is for making the ball bounce in different direction after impact.
     */
    public void reverseY(){
        speedY *= -1;
    }
    
    
    /**
    * getBorderColor is a Getter Method to get the border color of the ball.
    * 
    * @return border	Border Color of the ball.
    */
    public Color getBorderColor(){
        return border;
    }
    
    /**
    * getInnerColor is a Getter Method to get the inner color of the ball.
    * 
    * @return inner	Inner Color of the ball.
    */
    public Color getInnerColor(){
        return inner;
    }
    
    
    /**
    * getPosition is a Getter Method to get the ball's center position.
    * 
    * @return center	Center position for the initial location of the ball.
    */
    public Point2D getPosition(){
        return center;
    }

     /**
     * getBallFace is a Getter Method to get the ballFace.
     * 
     * @return ballFace	Shape and features of the ball.
     */
    public Shape getBallFace(){
        return ballFace;
    }
    
    
    /**
     * moveTo method will implements the ball shape and location when ball resets.
     * 
     * @param p
     */
    public void moveTo(Point p){
        center.setLocation(p);

        RectangularShape tmp = (RectangularShape) ballFace;
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((center.getX() -(w / 2)),(center.getY() - (h / 2)),w,h);
        ballFace = tmp;
    }

    /**
     * setPoints is a Method that sets the Screen points
     * @param width     the width of the screen.
     * @param height    the height of the screen
     */
    private void setPoints(double width,double height){
        up.setLocation(center.getX(),center.getY()-(height / 2));
        down.setLocation(center.getX(),center.getY()+(height / 2));

        left.setLocation(center.getX()-(width / 2),center.getY());
        right.setLocation(center.getX()+(width / 2),center.getY());
    }
    
    /**
     * getSpeedX is a Getter Method to get the speedX
     * 
     * @return speedX	speedX value.
     */
    public int getSpeedX(){
        return speedX;
    }

    
    /**
     * getSpeedY is a Getter Method to get the speedY
     * 
     * @return speedY	speedY value.
     */
    public int getSpeedY(){
        return speedY;
    }


}
