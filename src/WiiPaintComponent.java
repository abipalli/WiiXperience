import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.JComponent;


public class WiiPaintComponent extends JComponent {
	private final ArrayList<ArrayList<Ellipse2D.Double>> lines;
	private final ArrayList<Color> lineColors;
	private final PointerComponent cursor;
	private boolean drawNewLine;
	private double CURSOR_SIZE;
	
	private final Color[] colors;
	private int currColorInd;
	
	WiiPaintComponent(double width, double height) {
		CURSOR_SIZE = 50;
		lines = new ArrayList<ArrayList<Ellipse2D.Double>>();
		lineColors = new ArrayList<Color>();
		cursor = new PointerComponent(width, height, CURSOR_SIZE);
		drawNewLine = true;
		
		colors = new Color[8];
			colors[0] = Color.red;
			colors[1] = Color.orange;
			colors[2] = Color.yellow;
			colors[3] = Color.green;
			colors[4] = Color.blue;
			colors[5] = Color.pink;
			colors[6] = Color.black;
			colors[7] = Color.WHITE;
		currColorInd = 0;	
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
			
		
		
		for(int i = 0; i < lines.size(); i++) {
			ArrayList<Ellipse2D.Double> line = lines.get(i);
			g2d.setColor(lineColors.get(i));
			for(Ellipse2D.Double circle: line) {
				g2d.fill(circle);
			}
		}
		
		g2d.setColor(Color.black);
		g2d.draw(cursor.getPointer());
	}
	
	public void increasePointerWidth() {
		cursor.setPointerSize(cursor.getPointerSize()+10);
		CURSOR_SIZE = cursor.getPointerSize();
	}
	public void decreasePointerWidth() {
		if(cursor.getPointerSize()-10 >= 0) {
			cursor.setPointerSize(cursor.getPointerSize()-10);
			CURSOR_SIZE = cursor.getPointerSize();
		}
	}
	
	public void erase(double x, double y) {
		for(int o = 0; o < lines.size(); o++) {
			for(int i = 0; i < lines.get(o).size(); i++) {
				if(lines.get(o).get(i).intersects(cursor.getPointer().getX(), cursor.getPointer().getY(), CURSOR_SIZE, CURSOR_SIZE)) {
					lines.remove(o);
					lineColors.remove(o);
					break;
				}
			}
		}
	}
	
	public void changeUpColor() {
		if(currColorInd == colors.length-3) currColorInd = 0;
		else if(currColorInd > colors.length-3) currColorInd = 0;
		else currColorInd++;
		repaint();
	}
	public void changeDownColor() {
		if(currColorInd == 0) currColorInd = colors.length-3;
		else if(currColorInd > colors.length-3) currColorInd = 0;
		else currColorInd--;
		repaint();
	}
	public Color getCurrentColor() {
		return colors[currColorInd];
	}
	
	public void drawNewLine() {
		drawNewLine = true;
	}
	
	public void draw(double x, double y) {
		move(x, y);

		if(drawNewLine) {
			drawNewLine = false;
			lines.add(new ArrayList<Ellipse2D.Double>());
			lineColors.add(colors[currColorInd]);
		}
		ArrayList<Ellipse2D.Double> line = lines.get(lines.size()-1);
		line.add(new Ellipse2D.Double(cursor.getPointer().getCenterX()-(cursor.getPointerSize()/2), cursor.getPointer().getCenterY()-(cursor.getPointerSize()/2), CURSOR_SIZE, CURSOR_SIZE));
		
		repaint();
	}
	
	public void move(double x, double y) {
		cursor.getPointer().x = x-(cursor.getPointer().getWidth()/2);
		cursor.getPointer().y = y-(cursor.getPointer().getHeight()/2);
		repaint();
	}

	public void undo() {
		// TODO Auto-generated method stub
		if(lines.size() > 1) {
			lines.remove(lines.size()-1);
			lineColors.remove(lineColors.size()-1);
		}
		repaint();
	}
}
