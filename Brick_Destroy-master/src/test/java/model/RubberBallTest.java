package test.java.model;


import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

import main.java.model.RubberBall;


class RubberBallTest {

	 RubberBall rubberBall = new RubberBall(new Point());
	    // Test the speed of the ball in all situation
	    @Test
	    void testRubberBallSpeedX() {
	        rubberBall.setXSpeed(5);
	        assertEquals(5, rubberBall.getSpeedX());
	    }

	    @Test
	    void testRubberBallSpeedY() {
	        rubberBall.setYSpeed(2);
	        assertEquals(2, rubberBall.getSpeedY());
	    }

	    @Test
	    void testreverseRubberBallSpeedX() {
	        rubberBall.setXSpeed(6);
	        rubberBall.reverseX();
	        assertEquals(-6, rubberBall.getSpeedX());
	        rubberBall.reverseX();
	        assertEquals(6, rubberBall.getSpeedX());
	    }

	    @Test
	    void testreverseRubberBallSpeedY() {
	        rubberBall.setYSpeed(3);
	        rubberBall.reverseY();
	        assertEquals(-3, rubberBall.getSpeedY());
	        rubberBall.reverseY();
	        assertEquals(3, rubberBall.getSpeedY());
	    }


}
