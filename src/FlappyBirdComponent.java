import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;


public class FlappyBirdComponent extends JComponent {
	private final Rectangle2D.Double topRect, bottRect;
	private final int SPACING, WIDTH;
	private double INCREMENT;
	
	private final PointerComponent pointer;
	
	private int score;
	private boolean scoreGiven;
	
	private boolean isGameOver, resume, isPaused;
	private static int resumeCountDown;
	private final javax.swing.Timer resumeTimer;
	
	FlappyBirdComponent(double width, double height) {
		SPACING = 100;
		INCREMENT = 5;
		WIDTH = 150;
		
		pointer = new PointerComponent(getWidth(), getHeight()/5);
			
		score = 0;
		
		topRect = new Rectangle2D.Double(width, 0, WIDTH, Math.random()*(height-SPACING));
		bottRect = new Rectangle2D.Double(topRect.getX(), topRect.getY()+SPACING, WIDTH, height-(topRect.getY()+SPACING));
		
		resumeCountDown = 3;
		resumeTimer = new javax.swing.Timer((1000/60), new ActionListener() {
			public void actionPerformed(ActionEvent evnt) {
				if(resumeCountDown == 0) resumeCountDown = 3;
				else resumeCountDown--;
				repaint();
				if(resumeCountDown == 0) {
					resume = false;
					((javax.swing.Timer) evnt.getSource()).stop();
				}
			}
		});
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(Color.BLUE);
		g2d.draw(pointer.getPointer());
		
		g2d.setColor(Color.GREEN);
		g2d.fill(topRect);
		g2d.fill(bottRect);
		
		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 45));
		g2d.drawString("POINTS: " + score, 300, 100);
		
		if(isGameOver) {
			g2d.setColor(Color.RED);
			g2d.drawString("GAME OVER", 275, 300);
		}
		if(resume) {
			g2d.setColor(Color.RED);
			g2d.drawString(""+resumeCountDown, 475, 300);
		}
		if(isPaused) {
			g2d.setColor(Color.orange);
			g2d.drawString("PAUSED", 275, 325);
		}
	}
	
	public void gameOver() {
		isGameOver = true;
	}
	public boolean isGameOver() {
		return isGameOver;
	}
	
	public void movePointer(double width, double height, float pitch) {
		pointer.move(width, height, 0, pitch);
		repaint();
	}
	
	public boolean intersects() {
		System.out.println("\n\n\n\n\n\n\n" + "Pos X: " + pointer.getPointer().getX() + "  Pos Y: " + pointer.getPointer().getY());
		System.out.println("Range X: " + topRect.getX() + " - " + (topRect.getX()+topRect.getWidth()));
		System.out.println("Top Range Y: " + topRect.getY() + " - " + (topRect.getY()+topRect.getHeight()));
		System.out.println("Bott Range Y: " + bottRect.getY() + " - " + (bottRect.getY()+bottRect.getHeight()));
		if(pointer.getPointer().getX() >= topRect.getX() && pointer.getPointer().getX() <= topRect.getX()+topRect.getWidth()) {
			if(pointer.getPointer().getY() >= topRect.getY() && pointer.getPointer().getY() <= topRect.getY()+topRect.getHeight()) {
				return true;
			} 
			if((pointer.getPointer().getY() + pointer.getPointer().getHeight()) >= bottRect.getY() && (pointer.getPointer().getY() + pointer.getPointer().getHeight()) <= bottRect.getY()+bottRect.getHeight()) {
				return true;
			}
		}
		return false;
	}
	
	public void addScore() {
		if(pointer.getPointer().getX() > topRect.getX()+topRect.getWidth()) {
			if(!scoreGiven) {
				score++;
				scoreGiven = true;
			}
		}		
		INCREMENT += 0.001;
		repaint();
	}
	
	public void resume() {
		resumeTimer.start();
		isPaused = false;
	}
	
	public void pause() {
		isPaused = true;
	}
	
	
	public void animate(double width, double height) {
		if(topRect.getX()+topRect.getWidth()-INCREMENT <= 0) {
			topRect.setFrame(width, 0, WIDTH, Math.random()*(height-SPACING));
			bottRect.setFrame(topRect.getX(), topRect.getHeight()+SPACING, WIDTH, height-(topRect.getHeight()+SPACING));
			scoreGiven = false;
		} else { 
			topRect.setFrame(topRect.getX()-INCREMENT, topRect.getY(), topRect.getWidth(), topRect.getHeight());
			bottRect.setFrame(topRect.getX(), topRect.getHeight()+SPACING, WIDTH, height-(topRect.getHeight()+SPACING));
		}		
		repaint();
	}
}
