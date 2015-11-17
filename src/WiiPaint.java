import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;

import wiiusej.Wiimote;
import wiiusej.values.Orientation;
import wiiusej.wiiusejevents.physicalevents.ExpansionEvent;
import wiiusej.wiiusejevents.physicalevents.IREvent;
import wiiusej.wiiusejevents.physicalevents.MotionSensingEvent;
import wiiusej.wiiusejevents.physicalevents.WiimoteButtonsEvent;
import wiiusej.wiiusejevents.utils.WiimoteListener;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.DisconnectionEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.StatusEvent;


public class WiiPaint extends JFrame {	
	private final Wiimote remote;	
	private final WiimoteListener cursorListener;
	
	private final WiiPaintComponent frameComponent;
	private final JLabel currColorLabel;
	
	WiiPaint(Wiimote wiimote) {
		super("Wii Pain");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setResizable(false);
		
		//INITIALIZE
		remote = wiimote;
		cursorListener = new CursorListener();
		
		JLayeredPane layeredPane = getLayeredPane();
		
		frameComponent = new WiiPaintComponent(getWidth(), getHeight());
			add(frameComponent, BorderLayout.CENTER);
		
		currColorLabel = new JLabel("Current Color");
			currColorLabel.setHorizontalAlignment(SwingUtilities.CENTER);
			currColorLabel.setBackground(frameComponent.getCurrentColor());
			currColorLabel.setOpaque(true);
			add(currColorLabel, BorderLayout.NORTH);
	}
	
	public WiimoteListener getWiimoteListener() {
		return cursorListener;
	}
	
	class CursorListener implements WiimoteListener {
		private boolean canDraw, canErase;
		
		@Override
		public void onButtonsEvent(WiimoteButtonsEvent arg0) {
			// TODO Auto-generated method stub
			if(arg0.isButtonAHeld())
				canDraw = true;
			if(arg0.isButtonAJustReleased()) {
				canDraw = false;
				frameComponent.drawNewLine();
			}
			
			if(arg0.isButtonBJustPressed())
				canErase = true;
			if(arg0.isButtonBJustReleased())
				canErase = false;
			
			if(arg0.isButtonUpPressed()) {
				frameComponent.increasePointerWidth();
			}
			if(arg0.isButtonDownPressed()) {
				frameComponent.decreasePointerWidth();
			}
			
			if(arg0.isButtonRightJustPressed()) {
				frameComponent.changeUpColor();
				currColorLabel.setBackground(frameComponent.getCurrentColor());
				repaint();
			}
			if(arg0.isButtonLeftJustPressed()) {
				frameComponent.changeDownColor();
				currColorLabel.setBackground(frameComponent.getCurrentColor());
				repaint();
			}
			
			if(arg0.isButtonBHeld() && arg0.isButtonLeftHeld()) {
				frameComponent.undo();
				repaint();
			}
		}

		@Override
		public void onClassicControllerInsertedEvent(
				ClassicControllerInsertedEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onClassicControllerRemovedEvent(
				ClassicControllerRemovedEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onDisconnectionEvent(DisconnectionEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onExpansionEvent(ExpansionEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGuitarHeroInsertedEvent(GuitarHeroInsertedEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGuitarHeroRemovedEvent(GuitarHeroRemovedEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onIrEvent(IREvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onMotionSensingEvent(MotionSensingEvent arg0) {
			// TODO Auto-generated method stub
			Orientation orient = arg0.getOrientation();
			double x = (orient.getRoll()/45)*180+getWidth()/2;
			double y = (orient.getPitch()/45)*180+getHeight()/2;

			if(canDraw) frameComponent.draw(x, y);
			if(canErase) frameComponent.erase(x, y);
			frameComponent.move(x, y);
			frameComponent.repaint();
		}

		@Override
		public void onNunchukInsertedEvent(NunchukInsertedEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onNunchukRemovedEvent(NunchukRemovedEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusEvent(StatusEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
