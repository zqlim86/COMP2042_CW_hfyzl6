package test.java.model;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import org.junit.jupiter.api.Test;
import main.java.model.PlayerPowerup;
import main.java.model.RubberBall;


class PlayerPowerupTest {

	private final Color inner = Color.BLUE; 
    private final Color border = inner.darker().darker();
    private boolean showFireFace = true;
    private PlayerPowerup pPowerup = new PlayerPowerup();
    private Point2D center = new Point(150, 250);
    RubberBall rubberBall = new RubberBall(new Point());
    
	
	@Test
	void getPosition(){
		assertEquals(new Point(150, 250), center);
	}
	
	@Test
	void getInnerColor() {
		assertEquals(inner,pPowerup.getInnerColor());
	}
	
	@Test
	void getBorderColor() {
		assertEquals(border,pPowerup.getBorderColor());
	}
	
	@Test
	void impact(){
		assertFalse(pPowerup.impact(rubberBall));
	}
	
	@Test
	void getFireFace() {
		assertTrue(pPowerup.getPlayerFace());
	}

}
