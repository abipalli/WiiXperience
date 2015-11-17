import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

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


public class FlappyBird extends JFrame {
	//wiimote
	private final Wiimote remote;
	//flappybird game component
	private FlappyBirdComponent flappyComponent;
	//bird listener
	private final WiimoteListener birdListener;
	//animate Timer
	private final javax.swing.Timer gameTimer;
	
	
	FlappyBird(Wiimote wiimote) {
		//INITIALIZERS
		super("Flappy Bird");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setResizable(false);
		//wiimote
		remote = wiimote;
			birdListener = new BirdListener();
		//game component
		flappyComponent = new FlappyBirdComponent(getWidth(), getHeight());
			add(flappyComponent);
		//animation timer
		gameTimer = new javax.swing.Timer((1000/60), new ActionListener() {
			public void actionPerformed(ActionEvent evnt) {
				if(!flappyComponent.intersects() && !flappyComponent.isGameOver()) {
					flappyComponent.animate(getWidth(), getHeight());
					flappyComponent.addScore();
				} else {
					System.out.println("GAME OVER");
					((javax.swing.Timer) evnt.getSource()).stop();
					flappyComponent.gameOver();
					flappyComponent.repaint();
				}
			}
		});
	}
	
	public void restart() {
		flappyComponent = new FlappyBirdComponent(getWidth(), getHeight());
	}
	
	
	public void start() {
		if(!flappyComponent.isGameOver()) gameTimer.start();
	}
	public void stop() {
		gameTimer.stop();
	}
	
	public boolean canResume() {
		return (!flappyComponent.isGameOver());
	}
	public void resume() {
		flappyComponent.resume();
	}
	public WiimoteListener getWiimoteListener() {
		return birdListener;
	}
	
	class BirdListener implements WiimoteListener {

		@Override
		public void onButtonsEvent(WiimoteButtonsEvent arg0) {
			// TODO Auto-generated method stub
			if(arg0.isButtonAJustPressed()) {
				start();
			}
			if(arg0.isButtonBJustPressed()) {
				stop();
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
			
			flappyComponent.movePointer(200, getHeight(), orient.getPitch());
			repaint();
		}

		@Override
		public void onNunchukInsertedEvent(NunchukInsertedEvent arg0) {			
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
