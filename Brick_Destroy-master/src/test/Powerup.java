package test;

import java.awt.*;
import java.awt.geom.Point2D;


public abstract class Powerup {

	Shape powerupFace;
	private Point2D center;
	
	 private Color border;
	 private Color inner;
	   
	 public Powerup() {
		 resetPowerup();
		 
	 }  
	 
	 public Point2D getPosition(){
	        return center;
	    }
	 
	 public void resetPowerup(){
	        /*
	        int minX = 50;
	        int maxX = 550;
	        int point_x = (int)(Math.random() * (maxX - minX)) + minX;
	        int minY = 100;
	        int maxY = 300;
	        int point_y = (int)(Math.random() * (maxY - minY)) + minY;
	        this.center = new Point(150, 280);
	        resetFace(getPosition());
	   		*/
	    }
	 
	 protected abstract void resetFace(Point2D point);
	 
	 protected abstract Shape getMultiFace();
	 
	 protected abstract Color getInnerColor();
	 
	 protected abstract Color getBorderColor();
	 

	
}
