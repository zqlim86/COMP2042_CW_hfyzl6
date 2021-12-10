package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;


public class Intro extends JComponent implements KeyListener {
	
	private Rectangle introFace;
	private GameFrame frame;
	private static final String CONTINUE = "HIT ENTER TO CONTINUE...";
    private static final Color TEXT_COLOR = Color.WHITE.darker().darker(); 
    private Font textFont;
	
    private static final Color BG_COLOR = Color.BLACK;

	Image icon = new ImageIcon(getClass().getResource("/resources/Intro.gif")).getImage();
	
	public Intro(GameFrame owner, Dimension area) {
		
			BackgroundMusic.init();
			BackgroundMusic.load("/SFX/Intro.mp3", "Intro");
			BackgroundMusic.loop("Intro");
		
 	        this.setFocusable(true);
	        this.requestFocusInWindow();

	        this.frame = owner;
	        
	        introFace = new Rectangle(new Point(0,0),area);
	        this.setPreferredSize(area);
	        this.addKeyListener(this);
	        
	        textFont = new Font("Helvetica Neue",Font.BOLD,12);
	        
	}
	

	
	
    public void paint(Graphics g){
        drawIntro((Graphics2D)g);
    }
	
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
	
	
    private void drawContainer(Graphics2D g2d){
        Color prev = g2d.getColor();

        g2d.setColor(BG_COLOR);
        g2d.fill(introFace);
        g2d.setColor(prev);
        
        drawText(g2d);
    }
    
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


	@Override
	public void keyPressed(KeyEvent keyEvent) {
		 switch(keyEvent.getKeyCode()){
		 	case KeyEvent.VK_ENTER :
		 		BackgroundMusic.stop("Intro");
		    	BackgroundMusic.load("/SFX/MenuMusic.mp3", "MenuMusic");
		    	BackgroundMusic.load("/SFX/ButtonEffect.mp3", "ButtonEffect");
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
