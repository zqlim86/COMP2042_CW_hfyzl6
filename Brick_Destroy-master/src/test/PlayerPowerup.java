package test;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PlayerPowerup extends Powerup {
	
	
	private final Color inner = Color.BLUE; 
    private final Color border = inner.darker().darker();
    

    private Shape paddleFace;
    private boolean showPlayerFace = true;
    
    private Point2D center;
    private Image image;
    
    public PlayerPowerup(){
    	this.center = new Point(400, 250);
        resetFace(getPosition());
        paddleFace = makePowerup(getPosition());
        try {
			image = ImageIO.read(getClass().getResource("/resources/PUGrowth.png"));
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
        return paddleFace;
    }


    public Color getBorderColor(){
        return border;
    }


    public Color getInnerColor(){
        return inner;
    }

    public boolean impact(Ball b){
        if (paddleFace.getBounds().contains(b.getPosition())){
            return true;
        }
        return false;
    }
    
    
    @Override
    public void resetFace(Point2D point){
        paddleFace = makePowerup(point);
    }

   public boolean getPlayerFace(){
	   return showPlayerFace;
	   }
   
   public Image getImage() {
   	return image;
   }


}
