

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.swing.JComponent;

public class PointerComponent extends JComponent {
	private final Ellipse2D.Double pointer;
	private double POINTER_SIZE;
	
	PointerComponent(double width, double height) {
		POINTER_SIZE = 50;
		pointer = new Ellipse2D.Double((width/2) - (POINTER_SIZE/2), (height/2) - (POINTER_SIZE/2), POINTER_SIZE, POINTER_SIZE);
	}
	PointerComponent(double width, double height, double pointerSize) {
		POINTER_SIZE = pointerSize;
		pointer = new Ellipse2D.Double((width/2) - (POINTER_SIZE/2), (height/2) - (POINTER_SIZE/2), POINTER_SIZE, POINTER_SIZE);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fill(pointer);
	}
	
	public double getPointerSize() {
		return POINTER_SIZE;
	}
	public void setPointerSize(double size) {
		POINTER_SIZE = size;
		pointer.height = POINTER_SIZE;
		pointer.width = POINTER_SIZE;
		repaint();
	}
	
	public void move(double width, double height, double roll, double pitch) {
		double rollVal = (roll/45)*180, pitchVal = (pitch/45)*180;
		
		double x = (width/2) - (POINTER_SIZE/2) + (rollVal/180)*(width/2);
		double y = (height/2) - (POINTER_SIZE/2) + (pitchVal/180)*(height/2);
		
		pointer.setFrame(x, y, POINTER_SIZE, POINTER_SIZE);
		repaint();
	}
	
	public Ellipse2D.Double getPointer() {
		return pointer;
	}
}
