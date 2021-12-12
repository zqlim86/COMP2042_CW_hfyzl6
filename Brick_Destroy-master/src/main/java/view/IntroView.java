package main.java.view;

import javax.swing.*;

import main.java.controller.GameController;
import main.java.model.BackgroundMusic;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

/**
 * Intro class is the class for the implementation of the starting page when the game begins.
 */
public class IntroView extends JComponent implements KeyListener {
	
	private Rectangle introFace;
	private GameController frame;
	private static final String CONTINUE = "HIT ENTER TO CONTINUE...";
    private static final Color TEXT_COLOR = Color.WHITE.darker().darker(); 
    private Font textFont;
	
    private static final Color BG_COLOR = Color.BLACK;

	Image icon = new ImageIcon(getClass().getResource("/resources/Intro.gif")).getImage();
	
	/**
	 * Intro constructor will play background music, create a rectangle, set the text font.
	 * @param owner		GameFrame
	 * @param area		area Dimension size for Intro
	 */
	public IntroView(GameController owner, Dimension area) {
		
			BackgroundMusic.init();
			BackgroundMusic.load("/resources/Intro.mp3", "Intro");
			BackgroundMusic.loop("Intro");
		
 	        this.setFocusable(true);
	        this.requestFocusInWindow();

	        this.frame = owner;
	        
	        introFace = new Rectangle(new Point(0,0),area);
	        this.setPreferredSize(area);
	        this.addKeyListener(this);
	        
	        textFont = new Font("Helvetica Neue",Font.BOLD,12);
	        
	}
	

	
	 /**
     * paint is an Overridden Method from the JComponent class.
     * Method to invoke the painting of the Intro page.
     * Calls the drawMenu method.
     * @param g
     */
    public void paint(Graphics g){
        drawIntro((Graphics2D)g);
    }
	
    /**
     * draws component on introFace by calling the following method: drawContainer, drawText
     * set background gif.
     * @param g2d graphics2D object
     */
	public void drawIntro(Graphics2D g2d) {
		drawContainer(g2d);
		g2d.drawImage(icon, 1, 1,(int)(introFace.getWidth()), (int)(introFace.getHeight()), this);
		
        Color prevColor = g2d.getColor();
        Font prevFont = g2d.getFont();

        double x = introFace.getX();
        double y = introFace.getY();

        g2d.translate(x,y);

        //methods calls
        drawText(g2d);
        //end of methods calls

        g2d.translate(-x,-y);
        g2d.setFont(prevFont);
        g2d.setColor(prevColor);
    }
	
	 /**
     * draw background color for Intro
     * @param g2d graphics2D object
     */
    private void drawContainer(Graphics2D g2d){
        Color prev = g2d.getColor();

        g2d.setColor(BG_COLOR);
        g2d.fill(introFace);
        g2d.setColor(prev);
        
        drawText(g2d);
    }
    
    /**
     * draw text for Intro
     * @param g2d graphics2D object
     */
    private void drawText(Graphics2D g2d){

        g2d.setColor(TEXT_COLOR);

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D continueRect = textFont.getStringBounds(CONTINUE,frc);

        g2d.setFont(textFont);
        g2d.drawString(CONTINUE,179,380);
    }


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


    /**
     * The keyPressed method implements the method in KeyListener.
     * Listen and act when a key is pressed.
     * Enter = enableHomeMenu, also changing the background music.
     * 
     * @param keyEvent To detect if key has acted.
     */
	@Override
	public void keyPressed(KeyEvent keyEvent) {
		 switch(keyEvent.getKeyCode()){
		 	case KeyEvent.VK_ENTER :
		 		BackgroundMusic.stop("Intro");
		    	BackgroundMusic.load("/resources/MenuMusic.mp3", "MenuMusic");
		    	BackgroundMusic.load("/resources/ButtonEffect.mp3", "ButtonEffect");
		    	BackgroundMusic.setVolume("MenuMusic",-15);
		    	BackgroundMusic.loop("MenuMusic", 1000, 1000, BackgroundMusic.getFrames("MenuMusic") - 1000);
		 		frame.enableHomeMenu();
		 		break;
		 }
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	

	


}
