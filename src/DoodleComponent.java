import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JComponent;


public class DoodleComponent extends JComponent {
	private final ArrayList<Rectangle2D.Double> platforms;
	private final double PLATFORM_WIDTH, PLATFORM_HEIGHT;
	
	private final Rectangle2D.Double doodle;
	private final PointerComponent pointer;
	private final double DOODLE_WIDTH, DOODLE_HEIGHT;
	
	private double vel = 19.6;
	private final double ACCELERATION = 0.98;
	
	private final int INCREMENT = -4;
	private boolean isGameOver;
	
	private int score = 0;
	
	/**
	 * Pre-Condition: numOnScreen > 0
	 * @param width is the width of the JFrame that the component is being applied to
	 * @param height is the height of the JFrame that the component is being applied to
	 * @param numOnScreen is the number of platforms visible on the screen at a time
	 */
	public DoodleComponent(double width, double height) {		
		PLATFORM_HEIGHT = 20;
		PLATFORM_WIDTH = PLATFORM_HEIGHT*5;
		platforms = new ArrayList<Rectangle2D.Double>();
		for(int i = 0; i < 7; i++) {
			double yVal = Math.random()*(height-PLATFORM_HEIGHT)+PLATFORM_HEIGHT;
			platforms.add(new Rectangle2D.Double(Math.random()*(width-PLATFORM_WIDTH), yVal, PLATFORM_WIDTH, PLATFORM_HEIGHT));
		}
		
		doodle = new Rectangle2D.Double();
		pointer = new PointerComponent(width, height);
		DOODLE_WIDTH = PLATFORM_WIDTH/2;
		DOODLE_HEIGHT = PLATFORM_HEIGHT*6;
		doodle.setFrame(platforms.get(0).getX()+(PLATFORM_WIDTH)/2-(DOODLE_WIDTH)/2, platforms.get(0).getY()-DOODLE_HEIGHT, DOODLE_WIDTH, DOODLE_HEIGHT);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(Color.GREEN);
		for(Rectangle2D.Double platform: platforms) {
			g2d.fill(platform);
		}
		
		g2d.setColor(Color.DARK_GRAY);
		g2d.fill(doodle);
		
		
		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 45));
		g2d.drawString("POINTS: " + score, 300, 75);
		if(isGameOver) {
			g2d.setColor(Color.RED);
			g2d.drawString("GAME OVER", 275, 300);
		}
	}
	
	public void move(double roll, double width, double height) {
		if(score > 0) {
			pointer.move(width, height, roll, 0);
			doodle.setFrame(pointer.getPointer().getX(), doodle.getY(), doodle.getWidth(), doodle.getHeight());
		}
		repaint();
	}
	
	public boolean isGameOver() {
		return isGameOver;
	}
	
	public void actionPerformed(double width, double height) {
		boolean didIntersect = false;
		for(Rectangle2D.Double platform: platforms) {
			platform.setFrame(platform.getX(), platform.getY()-INCREMENT, platform.getWidth(), platform.getHeight());
			if(platform.getY() >= height) {
				platform.setFrame(Math.random()*(width-PLATFORM_WIDTH), -PLATFORM_HEIGHT, PLATFORM_WIDTH, PLATFORM_HEIGHT);
			}
			System.out.println(""+Math.abs(doodle.getMaxY()-platform.getY()));
			if(/*Math.abs(doodle.getMaxY()-platform.getY()) <= 7 &&*/ doodle.intersects(platform) && vel < 0) {
				didIntersect = true;
			}
			
			System.out.println(""+doodle.getMaxY() + " " + platform.getY());
		}
		
		if(didIntersect) {
			vel = 19.6;
		} else {
			vel -= ACCELERATION;
		}
		System.out.println("CURRENT VELOCITY VALUE: " + vel);
		if(!isGameOver) score += 1;
		doodle.setFrame(doodle.getX(), doodle.getY()-vel, doodle.getWidth(), doodle.getHeight());
		
		if(doodle.getY()+doodle.getHeight() > height) isGameOver = true;
		
		repaint();
	}
	
	public double getCurrVel() {
		return vel;
	}
	
	public void reset(double width, double height) {
		isGameOver = false;
		score = 0;
		doodle.setFrame(platforms.get(0).getX()+(PLATFORM_WIDTH)/2-(DOODLE_WIDTH)/2, platforms.get(0).getY()-DOODLE_HEIGHT, DOODLE_WIDTH, DOODLE_HEIGHT);
	}
}
