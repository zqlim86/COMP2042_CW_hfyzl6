package main.java.model;

import java.awt.*;
import java.awt.Point;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.Random;


/**
 * Brick is an abstract class that is responsible for the bricks is the game.
 */
abstract public class Brick  {

    public static final int MIN_CRACK = 1;
    public static final int DEF_CRACK_DEPTH = 1;
    public static final int DEF_STEPS = 35;


    public static final int UP_IMPACT = 100;
    public static final int DOWN_IMPACT = 200;
    public static final int LEFT_IMPACT = 300;
    public static final int RIGHT_IMPACT = 400;

    
    
    /**
     * Crack class is a Nested Class under the Brick abstract class as it is a feature of the Brick class.
     * Responsible for showing crack when ball impacts the bricks.
     */
    public class Crack{

        private static final int CRACK_SECTIONS = 3;
        private static final double JUMP_PROBABILITY = 0.7;

        public static final int LEFT = 10;
        public static final int RIGHT = 20;
        public static final int UP = 30; 
        public static final int DOWN = 40;
        public static final int VERTICAL = 100;
        public static final int HORIZONTAL = 200;



        private GeneralPath crack;

        private int crackDepth;
        private int steps;

        
        /**
         * Crack is a constructor that creates the crack effect.
         * Sets a randomly generated path from the point of impact.
         * Sets the depth of the crack.
         * Sets the crack steps.
         * @param crackDepth
         * @param steps
         */
        public Crack(int crackDepth, int steps){

            crack = new GeneralPath();
            this.crackDepth = crackDepth;
            this.steps = steps;

        }


        
        /**
         * draw method is for drawing the crack on bricks.
         * 
         * @return crack
         */
        public GeneralPath draw(){

            return crack;
        }

        /**
         * reset method is to reset the crack.
         */
        public void reset(){
            crack.reset();
        }

        
        /**
         * makeCrack is for setting and start and end location of cracks.
         * 
         * @param point			point of impact.
         * @param direction		direction of impact.
         */
        protected void makeCrack(Point2D point, int direction){
            Rectangle bounds = Brick.this.brickFace.getBounds();

            Point impact = new Point((int)point.getX(),(int)point.getY());
            Point start = new Point();
            Point end = new Point();


            switch(direction){
                case LEFT:
                    start.setLocation(bounds.x + bounds.width, bounds.y);
                    end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                    Point tmp = makeRandomPoint(start,end,VERTICAL);
                    makeCrack(impact,tmp);

                    break;
                case RIGHT:
                    start.setLocation(bounds.getLocation());
                    end.setLocation(bounds.x, bounds.y + bounds.height);
                    tmp = makeRandomPoint(start,end,VERTICAL);
                    makeCrack(impact,tmp);

                    break;
                case UP:
                    start.setLocation(bounds.x, bounds.y + bounds.height);
                    end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                    tmp = makeRandomPoint(start,end,HORIZONTAL);
                    makeCrack(impact,tmp);
                    break;
                case DOWN:
                    start.setLocation(bounds.getLocation());
                    end.setLocation(bounds.x + bounds.width, bounds.y);
                    tmp = makeRandomPoint(start,end,HORIZONTAL);
                    makeCrack(impact,tmp);

                    break;

            }
        }
 
        /**
         * makeCrack method is for randomly creating cracks from start to end position.
         * 
         * @param start
         * @param end
         */
        protected void makeCrack(Point start, Point end){

            GeneralPath path = new GeneralPath();


            path.moveTo(start.x,start.y);

            double w = (end.x - start.x) / (double)steps;
            double h = (end.y - start.y) / (double)steps;

            int bound = crackDepth;
            int jump  = bound * 5;

            double x,y;

            for(int i = 1; i < steps;i++){

                x = (i * w) + start.x;
                y = (i * h) + start.y + randomInBounds(bound);

                if(inMiddle(i,CRACK_SECTIONS,steps))
                    y += jumps(jump,JUMP_PROBABILITY);

                path.lineTo(x,y);

            }

            path.lineTo(end.x,end.y);
            crack.append(path,true);
        }

        
        /**
         * randomIntBound method is for returning random number between the bound value and negative bound value.
         * 
         * @param bound		bound value
         * @return 			random integer between bound and negative bound value.
         */
        private int randomInBounds(int bound){
            int n = (bound * 2) + 1;
            return rnd.nextInt(n) - bound;
        }

        private boolean inMiddle(int i,int steps,int divisions){
            int low = (steps / divisions);
            int up = low * (divisions - 1);

            return  (i > low) && (i < up);
        }

        private int jumps(int bound,double probability){

            if(rnd.nextDouble() > probability)
                return randomInBounds(bound);
            return  0;

        }

        private Point makeRandomPoint(Point from,Point to, int direction){

            Point out = new Point();
            int pos;

            switch(direction){
                case HORIZONTAL:
                    pos = rnd.nextInt(to.x - from.x) + from.x;
                    out.setLocation(pos,to.y);
                    break;
                case VERTICAL:
                    pos = rnd.nextInt(to.y - from.y) + from.y;
                    out.setLocation(to.x,pos);
                    break;
            }
            return out;
        }

    }

    private static Random rnd;

    private String name;
    Shape brickFace;

    private Color border;
    private Color inner;

    private int fullStrength;
    private int strength;

    private boolean broken;

    
    /**
     * Brick Constructor if for handling the initial implementation of the brick.
     * Set brick name.
     * Set brick status as NOT BROKEN.
     * Creates the brick.
     * @param name      brick name
     * @param pos       brick position/location
     * @param size      size of the brick
     * @param border    brick border color
     * @param inner     brick inner color
     * @param strength  brick's strength
     */
    public Brick(String name, Point pos,Dimension size,Color border,Color inner,int strength){
        rnd = new Random();
        broken = false;
        this.name = name;
        brickFace = makeBrickFace(pos,size);
        this.border = border;
        this.inner = inner;
        this.fullStrength = this.strength = strength;

    }
    
    /**
     * makeBrickFace is an Abstract Method which creates the ball.
     * This method will later be implemented by the Cement, Clay and Steel brick classes or any other extensions the programmer wishes to add
     * @param pos       the position/location of the brick.
     * @param size      size of the brick.
     * @return          returns the brick.
     */
    protected abstract Shape makeBrickFace(Point pos,Dimension size);
    
    
    
    /**
     * setImpact method is for detecting if an impact has occured.
     * If a brick is broken, return false, meaning no impact made.
     * If a brick is not broken, run impact method and return if the brick is broken or not.
     * 
     * @param point		point of impact.
     * @param dir		direction of impact.
     * @return			returns boolean value to check if brick is broken or not.
     */
    public  boolean setImpact(Point2D point , int dir){
        if(broken)
        	return false;
        impact();
        return  broken;
    }

    /**
     * getBrick is an abstract method that will implement later.
     * @return		returns brick.
     */
    public abstract Shape getBrick();


    
    /**
     * getBorderColor method is for returning border color of brick.
     * @return		returns border color of brick.
     */
    public Color getBorderColor(){
        return  border;
    }
    
    
    /**
     * getInnerColor method is for returning inner color of brick.
     * @return		returns inner color of brick.
     */
    public Color getInnerColor(){
        return inner;
    }

    
    /**
     * findImpact method is to determine the direction of impact from the ball to the brick.
     * @param b		ball
     * @return		
     */
    public final int findImpact(Ball b){
        if(broken)
            return 0;
        int out  = 0;
        if(brickFace.contains(b.right))
            out = LEFT_IMPACT;
        else if(brickFace.contains(b.left))
            out = RIGHT_IMPACT;
        else if(brickFace.contains(b.up))
            out = DOWN_IMPACT;
        else if(brickFace.contains(b.down))
            out = UP_IMPACT;
        return out;
    }
    
    
    /**
     * isBroken method is for returning if brick is broken or not.
     * @return		returns a boolean value of if brick is broken.
     */
    public final boolean isBroken(){
        return broken;
    }

    /**
     * repair method is for repairing the brick.
     * Set broken back to false and set strength of brick back to fullStrength.
     */
    public void repair() {
        broken = false;
        strength = fullStrength;
    }

    
    /**
     * impact method is to set the broken variable based on brick's state
     * impact method is also reponsible for decrementing brick's strength when impact happens. 
     */
    public void impact(){
        strength--;
        broken = (strength == 0);
    }
    


}





