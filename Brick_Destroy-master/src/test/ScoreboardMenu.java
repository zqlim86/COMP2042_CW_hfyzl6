package test;

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
	    private String scoreMessage;
	    private boolean backClicked;

	    private BasicStroke borderStoke;
	    private BasicStroke borderStoke_noDashes;
	    
	    private Rectangle menuFace;
	    private Rectangle backButton;

	    private GameFrame owner;

	    private Font buttonFont;
	    Image icon = new ImageIcon(getClass().getResource("/resources/galaxy1.gif")).getImage();
 

	    


	    public ScoreboardMenu(GameFrame owner, Dimension area) throws IOException {
	    	
	    	BackgroundMusic.init();
	    	BackgroundMusic.load("/SFX/ButtonEffect.mp3", "ButtonEffect");
	    	
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

	    public void paint(Graphics g){
	        drawMenu((Graphics2D)g);
	    }

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

	    private void drawText(Graphics2D g2d){

	        g2d.setColor(TEXT_COLOR);

	        FontRenderContext frc = g2d.getFontRenderContext();

	        Rectangle2D titleRect = titleFont.getStringBounds(TITLE,frc);
	        //Rectangle2D listRect = titleFont.getStringBounds(,frc);

	        int sX,sY;

	        sY = (int)(menuFace.getHeight() / 6);
	        sX = (int)(menuFace.getWidth() - titleRect.getWidth()) / 2;
	        sY += (int) titleRect.getHeight() * 1.1;//add 10% of String height between the two strings
	       

	        g2d.setFont(titleFont);
	        g2d.drawString(TITLE,sX,sY);
	        
	        
	        g2d.setColor(Color.GRAY.brighter());
	        g2d.setFont(listFont);
	        drawString(g2d,scoreMessage,220,225);
	        }

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
	    
	    
	    
	    private String displayScores() throws IOException {
	        Integer[] scores = TextFileController.readFromFile();
	        Arrays.sort(scores, Collections.reverseOrder());
	        
	        String result = StringUtils.join(scores,"\n");
	        
	        return result; 
	    }
	    
	    @Override
	    public void mouseClicked(MouseEvent mouseEvent) {
	        Point p = mouseEvent.getPoint();
	        if(backButton.contains(p)){
	        	BackgroundMusic.play("ButtonEffect");
	            owner.enableHomeMenu();
	        }
	    }

	    @Override
	    public void mousePressed(MouseEvent mouseEvent) {
	        Point p = mouseEvent.getPoint();
	        if(backButton.contains(p)){
	            backClicked = true;
	            repaint(backButton.x,backButton.y,backButton.width+1,backButton.height+1);
	        }
	    }

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

	    @Override
	    public void mouseMoved(MouseEvent mouseEvent) {
	        Point p = mouseEvent.getPoint();
	        if(backButton.contains(p))
	            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	        else
	            this.setCursor(Cursor.getDefaultCursor());

	    }
	

}
