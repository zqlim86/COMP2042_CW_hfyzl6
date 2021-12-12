package main.java.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.*;
import org.apache.commons.lang3.StringUtils;

import main.java.controller.GameFrame;
import main.java.controller.TextFileController;
import main.java.model.BackgroundMusic;


/**
 * Displays the scoreboard.
 */
public class ScoreboardMenu extends JComponent implements MouseListener, MouseMotionListener{
		
	 	private static final String BACK_TEXT = "Back";
	    private static final String TITLE = "LEADERBOARD";
	    private Font titleFont;
	    private Font listFont;

	    private static final Color BORDER_COLOR = new Color(200,8,21); //Venetian Red
	    private static final Color DASH_BORDER_COLOR = new  Color(255, 216, 0);//school bus yellow
	    private static final Color BG_COLOR = Color.BLACK;
	    private static final Color TEXT_COLOR = Color.GRAY.brighter();
	    private static final Color CLICKED_BUTTON_COLOR = Color.GRAY;
	    private static final Color CLICKED_TEXT = Color.GRAY.darker();
	    private static final int BORDER_SIZE = 5;
	    private static final float[] DASHES = {12,6};
	    
	    private int printscores = 8;
	    private String nameMessage;
	    private String scoreMessage;
	    private boolean backClicked;

	    private BasicStroke borderStoke;
	    private BasicStroke borderStoke_noDashes;
	    
	    private Rectangle menuFace;
	    private Rectangle backButton;

	    private GameFrame owner;

	    private Font buttonFont;
	    Image icon = new ImageIcon(getClass().getResource("/resources/galaxy1.gif")).getImage();
 

	    

	    /**
	     * Set the position of scoreboard menu.
	     * Set the font style and size.
	     * Set the back button's dimension.
	     * 
	     * @param owner
	     * @param area
	     */
	    public ScoreboardMenu(GameFrame owner, Dimension area) throws IOException {
	    	
	    	BackgroundMusic.init();
	    	BackgroundMusic.load("/SFX/ButtonEffect.mp3", "ButtonEffect");
	    	
	    	nameMessage = displayNames();
	    	scoreMessage = displayScores();
	        this.setFocusable(true);
	        this.requestFocusInWindow();

	        this.addMouseListener(this);
	        this.addMouseMotionListener(this);

	        this.owner = owner;
	        
	        menuFace = new Rectangle(new Point(0,0),area);
	        this.setPreferredSize(area);

	        Dimension btnDim = new Dimension(area.width / 3, area.height / 12);
	        backButton = new Rectangle(btnDim);

	        borderStoke = new BasicStroke(BORDER_SIZE,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,0,DASHES,0);
	        borderStoke_noDashes = new BasicStroke(BORDER_SIZE,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
	        
	        buttonFont = new Font("Helvetica Neue",Font.PLAIN,backButton.height-2);
	        titleFont = new Font("Helvetica Neue", Font.BOLD, 30);
	        listFont = new Font("Helvetica Neue", Font.BOLD, 20);


	    }
	    
	    /**
	     * paint is an Overridden Method from the JComponent class.
	     * Method to invoke the painting of the ScoreboardMenu page.
	     * Calls the drawMenu method.
	     * @param g
	     */
	    public void paint(Graphics g){
	        drawMenu((Graphics2D)g);
	    }
	    
	    /**
	     * draws component on menuFace by calling the following method: drawContainer, drawText, drawButton
	     * set background image of home menu.
	     * @param g2d graphics2D object
	     */
	    public void drawMenu(Graphics2D g2d){

	        drawContainer(g2d);
	        g2d.drawImage(icon, 1, 1, (int)(menuFace.getWidth()), (int)(menuFace.getHeight()), this);

	        /*
	        all the following method calls need a relative
	        painting directly into the HomeMenu rectangle,
	        so the translation is made here so the other methods do not do that.
	         */
	        Color prevColor = g2d.getColor();
	        Font prevFont = g2d.getFont();

	        double x = menuFace.getX();
	        double y = menuFace.getY();

	        g2d.translate(x,y);

	        //methods calls
	        drawText(g2d);
	        drawButton(g2d);
	        //end of methods calls

	        g2d.translate(-x,-y);
	        g2d.setFont(prevFont);
	        g2d.setColor(prevColor);
	    }
	    
	    /**
	     * draw the background, and border for ScoreboardMenu
	     * @param g2d graphics2D object
	     */
	    private void drawContainer(Graphics2D g2d){
	        Color prev = g2d.getColor();

	        g2d.setColor(BG_COLOR);
	        g2d.fill(menuFace);

	        Stroke tmp = g2d.getStroke();

	        g2d.setStroke(borderStoke_noDashes);
	        g2d.setColor(DASH_BORDER_COLOR);
	        g2d.draw(menuFace);

	        g2d.setStroke(borderStoke);
	        g2d.setColor(BORDER_COLOR);
	        g2d.draw(menuFace);

	        g2d.setStroke(tmp);

	        g2d.setColor(prev);
	    }
	    
	    private void drawString(Graphics g, String text, int x, int y) {
	        for (String line : text.split("\n"))
	            g.drawString(line, x, y += g.getFontMetrics().getHeight());
	    }
	    
	    
	    /**
	     * draw the titleRect in specific location on ScoreboardMenu
	     * @param g2d graphics2D object
	     */
	    private void drawText(Graphics2D g2d){

	        g2d.setColor(TEXT_COLOR);

	        FontRenderContext frc = g2d.getFontRenderContext();

	        Rectangle2D titleRect = titleFont.getStringBounds(TITLE,frc);
	        

	        int sX,sY;

	        sY = (int)(menuFace.getHeight() / 6);
	        sX = (int)(menuFace.getWidth() - titleRect.getWidth()) / 2;
	        sY += (int) titleRect.getHeight() * 1.1;//add 10% of String height between the two strings
	       

	        g2d.setFont(titleFont);
	        g2d.drawString(TITLE,sX,sY);
	        
	        g2d.setColor(Color.GRAY.brighter());
	        g2d.setFont(listFont);
	        drawString(g2d,nameMessage,180,180);
	        
	        g2d.setColor(Color.GRAY.brighter());
	        g2d.setFont(listFont);
	        drawString(g2d,scoreMessage,260,180);
	        }
	    
	    /**
	     * draw the button 'back' on Scoreboard screen
	     * @param g2d graphics2D object
	     */
	    private void drawButton(Graphics2D g2d){

	        FontRenderContext frc = g2d.getFontRenderContext();

	        Rectangle2D txtRect = buttonFont.getStringBounds(BACK_TEXT,frc);


	        g2d.setFont(buttonFont);

	        int x = 10;
	        int y =(int) ((menuFace.height - backButton.height) * 0.95);

	        backButton.setLocation(x,y);

	        x = (int)(backButton.getWidth() - txtRect.getWidth()) / 2;
	        y = (int)(backButton.getHeight() - txtRect.getHeight()) / 2;

	        x += backButton.x;
	        y += backButton.y + (backButton.height * 0.9);


	        if(backClicked){
	            Color tmp = g2d.getColor();
	            g2d.setColor(CLICKED_BUTTON_COLOR);
	            g2d.draw(backButton);
	            g2d.setColor(CLICKED_TEXT);
	            g2d.drawString(BACK_TEXT,x,y);
	            g2d.setColor(tmp);
	        }
	        else{
	            g2d.draw(backButton);
	            g2d.drawString(BACK_TEXT,x,y);
	        }
	    }
	    
	    
	    /**
	     * Store the name read from file into an array and return the array.
	     * 
	     * @return result		String array
	     * @throws IOException
	     */
	    private String displayNames() throws IOException{
	    	String[] names = TextFileController.nReadFromFile();
	    	
	    	String result = StringUtils.join(names,"\n");
	    	
	    	return result;
	    }
	    
	    /**
	     * Store the score read from file into an array and return the array.
	     * 
	     * @return result		String array
	     * @throws IOException
	     */
	    private String displayScores() throws IOException {
	        Integer[] scores = TextFileController.readFromFile();
	        
	        String result = StringUtils.join(scores,"\n");
	        
	        return result; 
	    }
	    
	    
	    /**
	     * method is only activated when buttons are clicked	    
	     * if backButton is clicked, then play button sound effect and enableHomeMenu.
	     * every click will play button sound effect.
	     * @param mouseEvent mouse action
	     */
	    @Override
	    public void mouseClicked(MouseEvent mouseEvent) {
	        Point p = mouseEvent.getPoint();
	        if(backButton.contains(p)){
	        	BackgroundMusic.play("ButtonEffect");
	            owner.enableHomeMenu();
	        }
	    }
	    
	    /**
	     * if buttons are pressed, then set backClicked to true, then repaint the button to white
	     * @param mouseEvent mouse action
	     */
	    @Override
	    public void mousePressed(MouseEvent mouseEvent) {
	        Point p = mouseEvent.getPoint();
	        if(backButton.contains(p)){
	            backClicked = true;
	            repaint(backButton.x,backButton.y,backButton.width+1,backButton.height+1);
	        }
	    }
	    
	    /**
	     * mouseReleased Method invoked when the mouse is released.
	     * @param mouseEvent    to indicate if a mouse action has occurred or not.
	     */
	    @Override
	    public void mouseReleased(MouseEvent mouseEvent) {
	        if(backClicked ){
	            backClicked = false;
	            repaint(backButton.x,backButton.y,backButton.width+1,backButton.height+1);
	        }
	    }

	    @Override
	    public void mouseEntered(MouseEvent e) {

	    }

	    @Override
	    public void mouseExited(MouseEvent e) {

	    }

	    @Override
	    public void mouseDragged(MouseEvent e) {

	    }
	    
	    /**
	     * mouseMoved Method implements what should happen when the mouse hovers over the START or EXIT button
	     * and what the cursor should look like otherwise.
	     * @param mouseEvent    to indicate if a mouse action has occurred or not.
	     */
	    @Override
	    public void mouseMoved(MouseEvent mouseEvent) {
	        Point p = mouseEvent.getPoint();
	        if(backButton.contains(p))
	            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	        else
	            this.setCursor(Cursor.getDefaultCursor());

	    }
	

}
