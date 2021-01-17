import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.awt.FontMetrics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CircleButton extends JButton{
	
	private boolean mouseOver = false;
	private boolean mousePressed = false;
	private String color = "";

	public CircleButton(String text, boolean btnPressed, String color){
		super(text);
		setOpaque(false);
		setFocusPainted(false);
		setBorderPainted(false);
		if(!btnPressed)
		{
		this.color = color;
		}
		
		MouseAdapter mouseListener = new MouseAdapter(){
			
			@Override
			public void mousePressed(MouseEvent me){
				if(contains(me.getX(), me.getY())){
					//mousePressed = true;
					if(btnPressed)
					{
						mousePressed = true;
					}
					repaint();
				}
			}
			
			@Override
			public void mouseReleased(MouseEvent me){
				// mousePressed = false;
				if(btnPressed)
				{
					mousePressed = true;
				}
				repaint();
			}
			
			@Override
			public void mouseExited(MouseEvent me){
				// mousePressed = false;
				mouseOver = false;
				if(btnPressed)
				{
					mousePressed = true;
				}
				repaint();
			}
			
			@Override
			public void mouseMoved(MouseEvent me){
				mouseOver = contains(me.getX(), me.getY());
				repaint();
			}
			
		};
		
		addMouseListener(mouseListener);
		addMouseMotionListener(mouseListener);		
	}
	
	public void setColor(String color)
	{
		this.color = color;
	}
	
	public void restart()
	{
		this.color = "White";
		repaint();
	}
	
	private int getDiameter(){
		int diameter = Math.min(getWidth(), getHeight());
		return diameter;
	}
	
	@Override
	public Dimension getPreferredSize(){
		FontMetrics metrics = getGraphics().getFontMetrics(getFont());
		int minDiameter = 10 + Math.max(metrics.stringWidth(getText()), metrics.getHeight());
		return new Dimension(minDiameter, minDiameter);
	}
	
	@Override
	public boolean contains(int x, int y){
		int radius = getDiameter()/2;
		return Point2D.distance(x, y, getWidth()/2, getHeight()/2) < radius;
	}
	
	@Override
	public void paintComponent(Graphics g){
		
		int diameter = getDiameter();
		int radius = diameter/2;
		if(color == "White")
		{
			g.setColor(Color.WHITE);
		}
		if(color == "Yellow"){
			g.setColor(Color.YELLOW);
		}
		if(color == "Red"){
			g.setColor(Color.RED);
		}
		g.fillOval(getWidth()/2 - radius, getHeight()/2 - radius, diameter, diameter);
		
		if(mouseOver){
			g.setColor(Color.BLUE);
		}
		else{
			g.setColor(Color.BLACK);
		}
		g.drawOval(getWidth()/2 - radius, getHeight()/2 - radius, diameter, diameter);
		
		g.setColor(Color.BLACK);
		g.setFont(getFont());
		FontMetrics metrics = g.getFontMetrics(getFont());
		int stringWidth = metrics.stringWidth(getText());
		int stringHeight = metrics.getHeight();
		g.drawString(getText(), getWidth()/2 - stringWidth/2, getHeight()/2 + stringHeight/4);
	}
}