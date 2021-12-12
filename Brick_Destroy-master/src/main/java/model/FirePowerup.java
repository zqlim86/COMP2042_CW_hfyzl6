package main.java.model;

import java.awt.*;
import java.awt.geom.*;
import java.awt.geom.Point2D;
import java.io.IOException;

import javax.imageio.ImageIO;


/**
 * FirePowerup class is Child Class of the Powerup class.
 * Responsible for all the implementations regarding the Fire Powerup.
 */
public class FirePowerup extends Powerup{
	
    private final Color inner = Color.RED; 
    private final Color border = inner.darker().darker();
    

    private Shape fireFace;
    private boolean showFireFace = true;
    
    private Point2D center;
    
    private Image image;
    
    
    
    /**
     * FirePowerup constructor will create and set a position for the fire powerup.
     * It will also read an image for the fire powerup's in game visual. 
     */
    public FirePowerup(){
    	
    	
    	this.center = new Point(150, 250);
        resetFace(getPosition());
        fireFace = makePowerup(getPosition());
        try {
			image = ImageIO.read(getClass().getResource("/resources/PUFireball.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    /**
	 * getPosition returns the center of the powerup's position.
	 * @return		center of the powerup's position.
	 */
	public Point2D getPosition(){
        return center;
    }
    
    /**
     * makePowerup will return a rectangle shape for the fire powerup.
     * 
     * @param center
     * @return
     */
    public Shape makePowerup(Point2D center){
        double x = center.getX();
        double y = center.getY(); 
         
        return new Rectangle2D.Double(x,y,25,25);
    }
    
    
    /**
     * getMultiFace is a Getter Method will return the fireFace variable.
     * @return		returns fireFace variable.
     */
    public Shape getMultiFace(){
        return fireFace;
    }

    /**
     * getBorderColor is a Getter Method will return the border variable.
     * @return		returns border variable.
     */
    public Color getBorderColor(){
        return border;
    }

    /**
     * getInnerColor is a Getter Method will return the inner variable.
     * @return		returns inner variable.
     */
    public Color getInnerColor(){
        return inner;
    }
    
    
    /**
     * impact method will detect if the ball has impacted the fire powerup.
     * If True then returns true, else return false.
     *
     * @param b		ball object.
     * @return		boolean value to see if ball has impacted fire powerup.
     */
    public boolean impact(Ball b){
        if (fireFace.getBounds().contains(b.getPosition())){
            return true;
        }
        return false;
    }
    
    
    /**
     * resetFace overrides parent class's (Powerup) resetFace().
     * This method will set the fireFace variable into a rectangle shape by calling the makePowerup() method.
     */
    @Override
    public void resetFace(Point2D point){
        fireFace = makePowerup(point);
    }
    
    
    /**
     * getFireFace is a Getter Method to return the showFireFace variable.
     * 
     * @return		returns showFireFace boolean value.
     */
    public boolean getFireFace(){return showFireFace;}
    
    
    /**
     * getImage method will return image type variable.
     * 
     * @return		returns image type value;
     */
    public Image getImage() {
    	return image;
    }
    

}


