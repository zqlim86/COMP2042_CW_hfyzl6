/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2017  Filippo Ranza
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package main.java.debug;

import javax.swing.*;
import javax.swing.event.ChangeListener;

import main.java.model.Wall;

import java.awt.*;
import java.awt.event.ActionListener;



/**
 * @author ziqin
 *
 */
public class DebugPanel extends JPanel {

    private static final Color DEF_BKG = Color.WHITE;

    
    private JButton skipLevel;
    private JButton resetBalls;

    private JSlider ballXSpeed;
    private JSlider ballYSpeed;

    private Wall wall;

    /**
     * DebugPanel Constructor that makes 2 Buttons and Sliders for player to choose levels and reset their ball chance.
     * @param wall
     */
    public DebugPanel(Wall wall){

        this.wall = wall;

        initialize();

        skipLevel = makeButton("Skip Level",e -> wall.nextLevel());
        resetBalls = makeButton("Reset Balls",e -> wall.resetBallCount());

        ballXSpeed = makeSlider(-4,4,e -> wall.setBallXSpeed(ballXSpeed.getValue()));
        ballYSpeed = makeSlider(-4,4,e -> wall.setBallYSpeed(ballYSpeed.getValue()));

        this.add(skipLevel);
        this.add(resetBalls);

        this.add(ballXSpeed);
        this.add(ballYSpeed);

    }

    /**
     * Initialize and set layout of DebugPanel with 2 Buttons and Sliders.
     */
    private void initialize(){
        this.setBackground(DEF_BKG);
        this.setLayout(new GridLayout(2,2));
    }

    /**
     * Make Buttons
     * @param title Button's title
     * @param e Action event
     * @return Jbutton
     */
    private JButton makeButton(String title, ActionListener e){
        JButton out = new JButton(title);
        out.addActionListener(e);
        return  out;
    }

    /**
     * Make Sliders with min/max values.
     * 
     * @param min Minimum value of the slider.
     * @param max Maximum value of the slider.
     * @param e Change event.
     * @return JSlider.
     */
    private JSlider makeSlider(int min, int max, ChangeListener e){
        JSlider out = new JSlider(min,max);
        out.setMajorTickSpacing(1);
        out.setSnapToTicks(true);
        out.setPaintTicks(true);
        out.addChangeListener(e);
        return out;
    }

    /**
     * The Slider is automatically set to the value of the ball when DebugPanel is activated.
     * 
     * @param x Ball's X Speed.
     * @param y Ball's Y Speed.
     */
    public void setValues(int x,int y){
        ballXSpeed.setValue(x);
        ballYSpeed.setValue(y);
    }

}
