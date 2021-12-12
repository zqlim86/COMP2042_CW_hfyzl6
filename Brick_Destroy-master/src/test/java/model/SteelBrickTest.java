package test.java.model;

import main.java.model.SteelBrick;
import main.java.model.RubberBall;
import org.junit.jupiter.api.Test;
import java.awt.*;
import java.awt.geom.Point2D;
import static org.junit.jupiter.api.Assertions.*;

class SteelBrickTest {

    private static final Color DEF_INNER = new Color(203, 203, 201);
    private static final Color DEF_BORDER = Color.BLACK;
    private static final int CEMENT_STRENGTH = 1;
    private static final double STEEL_PROBABILITY = 0.4;
    
    public static final int LEFT_IMPACT = 300;
    public static final int LEFT = 10;

    private Point point = new Point(200,400);
    private Dimension size = new Dimension(450, 300);
    SteelBrick steelBrick = new SteelBrick(point, size);

    private final Point2D center = new Point2D.Double(300, 430 );
    RubberBall rubberBall = new RubberBall(center);
    

    @Test
    void impact() {
        steelBrick.impact();
        assertFalse(steelBrick.isBroken());
    }

    @Test
    void setImpact() {
        assertFalse(steelBrick.setImpact(point , LEFT));
    }

    @Test
    void findImpact() {
        steelBrick.impact();
        assertEquals(LEFT_IMPACT, steelBrick.findImpact(rubberBall));
    }

    @Test
    void repair() {
        steelBrick.repair();
        assertFalse(steelBrick.isBroken());
    }

    @Test
    void makeBrickFace() {
        assertEquals(new Rectangle(new Point(200,400),new Dimension(450, 300)), steelBrick.makeBrickFace(point, size));
    }

    @Test
    void getBorderColor() {
        assertEquals(DEF_BORDER, steelBrick.getBorderColor());
    }

    @Test
    void getInnerColor() {
        assertEquals(DEF_INNER, steelBrick.getInnerColor());
    }

    @Test
    void isBroken() {
        assertFalse(steelBrick.isBroken());
    }

    @Test
    void getBrickFace() {
        assertEquals(new Rectangle(new Point(200,400),new Dimension(450, 300)), steelBrick.getBrick());
    }

    @Test
    void getBrick() {
        assertEquals(new Rectangle(new Point(200,400),new Dimension(450, 300)), steelBrick.getBrick());
    }

}
