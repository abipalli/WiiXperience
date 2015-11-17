import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

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


public class DoodleJump extends JFrame {
	private final Wiimote remote;
	private final WiimoteListener doodleListener;
	
	private final DoodleComponent dood;
	
	private final javax.swing.Timer gameTimer;
	
	class DoodleListener implements WiimoteListener {

		@Override
		public void onButtonsEvent(WiimoteButtonsEvent arg0) {
			// TODO Auto-generated method stub
			if(arg0.isButtonAJustPressed()) {
				gameTimer.start();
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
			dood.move(orient.getRoll(), getWidth(), getHeight());
			repaint();
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
	
	DoodleJump(Wiimote remote, double width, double height) {
		super("Doodle Jump");
		super.setSize((int) width, (int) height);
		super.setLocationRelativeTo(null);
		super.setResizable(false);
		
		this.remote = remote;
		
		dood = new DoodleComponent(getWidth(), getHeight());
			add(dood);
		doodleListener = new DoodleListener();
		
		gameTimer = new javax.swing.Timer((1000/60), new ActionListener() {
			public void actionPerformed(ActionEvent evnt) {
				dood.actionPerformed(getWidth(), getHeight());
				if(dood.isGameOver()) ((javax.swing.Timer) evnt.getSource()).stop();
			}
		});
	}
	
	public WiimoteListener getWiimoteListener() {
		return doodleListener;
	}
	
	public void uponApplicationPaused() {
		setVisible(false);
		gameTimer.stop();
	}
	
	public void reset() {
		dood.reset(getWidth(), getHeight());
	}
	
	public boolean isGameOver() {
		return dood.isGameOver();
	}
}
