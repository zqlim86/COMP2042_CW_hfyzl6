package test;

import java.awt.*;
import java.awt.geom.*;
import java.awt.geom.Point2D;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FirePowerup extends Powerup{
	
    private final Color inner = Color.RED; 
    private final Color border = inner.darker().darker();
    

    private Shape fireFace;
    private boolean showFireFace = true;
    
    private Point2D center;
    
    private Image image;
    
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
    
    public Point2D getPosition(){
        return center;
    }

    public Shape makePowerup(Point2D center){
        double x = center.getX();
        double y = center.getY(); 
         
        return new Rectangle2D.Double(x,y,25,25);
    }
    
    public Shape getMultiFace(){
        return fireFace;
    }


    public Color getBorderColor(){
        return border;
    }


    public Color getInnerColor(){
        return inner;
    }

    public boolean impact(Ball b){
        if (fireFace.getBounds().contains(b.getPosition())){
            return true;
        }
        return false;
    }

    @Override
    public void resetFace(Point2D point){
        fireFace = makePowerup(point);
    }

    public boolean getFireFace(){return showFireFace;}

    public Image getImage() {
    	return image;
    }
    

}


