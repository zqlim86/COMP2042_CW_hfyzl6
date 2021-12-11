package test.Model;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

/**
 * CementBrick class is Child Class of the Brick class.
 * Responsible for all the implementations regarding the Cement Brick.
 */
public class CementBrick extends Brick {


    private static final String NAME = "Cement Brick";
    private static final Color DEF_INNER = new Color(147, 147, 147);
    private static final Color DEF_BORDER = new Color(217, 199, 175);
    private static final int CEMENT_STRENGTH = 2;

    private Crack crack;
    private Shape brickFace;


    /**
     * CementBrick constructor runs parant class's (Brick class) constructor and Crack class constructor as well.
     * Get brickface from the parent class.
     * @param point		brick position
     * @param size		brick size
     */
    public CementBrick(Point point, Dimension size){
        super(NAME,point,size,DEF_BORDER,DEF_INNER,CEMENT_STRENGTH);
        crack = new Crack(DEF_CRACK_DEPTH,DEF_STEPS);
        brickFace = super.brickFace;
    }

    /**
     * makeBrickFace override parent class's makeBrickFace
     * makeBrickFace method creates a cement brick.
     * 
     * @param pos		brick position
     * @param size		brick size
     */
    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos,size);
    }

    /**
     * setImpact override parent class's setImpact
     * setImpact method detect if impact has occurred.
     * 
     * @param point 		point of impact
     * @param dir			direction of impact.
     */
    @Override
    public boolean setImpact(Point2D point, int dir) {
        if(super.isBroken())
            return false;
        super.impact();
        if(!super.isBroken()){
            crack.makeCrack(point,dir);
            updateBrick();
            return false;
        }
        return true;
    }

    
    /**
     * getBrick override parent class's getBrick.
     * @return		returns cement brick
     */
    @Override
    public Shape getBrick() {
        return brickFace;
    }
    
    /**
     * updateBrick is for updating the cement brick if its not broken.
     */
    private void updateBrick(){
        if(!super.isBroken()){
            GeneralPath gp = crack.draw();
            gp.append(super.brickFace,false);
            brickFace = gp;
        }
    }
    
    /**
     * repair method is to repair the Cement Bricks.
     * Call parent class's repair() method.
     * Resets crack of the cement bricks.
     */
    public void repair(){
        super.repair();
        crack.reset();
        brickFace = super.brickFace;
    }
}
