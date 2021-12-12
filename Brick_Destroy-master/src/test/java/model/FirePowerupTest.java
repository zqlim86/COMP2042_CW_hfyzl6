package test.java.model;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import org.junit.jupiter.api.Test;
import main.java.model.FirePowerup;
import main.java.model.RubberBall;

class FirePowerupTest {
	
	private final Color inner = Color.RED; 
    private final Color border = inner.darker().darker();
    private boolean showFireFace = true;
    private FirePowerup fPowerup = new FirePowerup();
    private Point2D center = new Point(150, 250);
    RubberBall rubberBall = new RubberBall(new Point());
    
	
	@Test
	void getPosition(){
		assertEquals(new Point(150, 250), center);
	}
	
	@Test
	void getInnerColor() {
		assertEquals(inner,fPowerup.getInnerColor());
	}
	
	@Test
	void getBorderColor() {
		assertEquals(border,fPowerup.getBorderColor());
	}
	
	@Test
	void impact(){
		assertFalse(fPowerup.impact(rubberBall));
	}
	
	@Test
	void getFireFace() {
		assertTrue(fPowerup.getFireFace());
	}
	
	
}
