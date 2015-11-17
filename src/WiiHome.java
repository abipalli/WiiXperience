import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JButton;
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


public class WiiHome extends JFrame {

	//wiiremote
	private final Wiimote remote;	
	//layered pane
	private final JLayeredPane layeredPane;
	//pointer component
	private final PointerComponent pointer;
	private final WiimoteListener pointerListener;	
	//new applications
	private boolean inAnotherApp;
		private int debugNum = 0;
	private final Map<String, JFrame> apps;
	private final ArrayList<JButton> buttons;
	
	class PointerListener implements WiimoteListener {
		@Override
		public void onButtonsEvent(WiimoteButtonsEvent arg0) {
			// TODO Auto-generated method stub
			if(!inAnotherApp) {
				if(arg0.isButtonAJustPressed()) {
					System.out.println("\n\n\n" + debugNum + ". Button A Pressed");
						debugNum++;
					
					double x = pointer.getPointer().getCenterX();
						System.out.println("\n\n\n" + debugNum + ". Pos X = " + x);
						debugNum++;
					double y = pointer.getPointer().getCenterY();
						System.out.println(debugNum + ". Pos Y = " + y);
						debugNum++;
					
					for(JButton button: buttons) {
						System.out.println(debugNum + ". X-Range: " + button.getX() + " " + (button.getX()+button.getWidth()));
							debugNum++;
						System.out.println(debugNum + ". Y-Range: " + button.getY() + " " + (button.getY()+button.getHeight()));
							debugNum++;
						if(x >= button.getX() && x <= button.getX()+button.getWidth()) 
							if(y >= button.getY() && y <= button.getY()+button.getHeight()) {
								button.doClick();															
								inAnotherApp = true;
								
								System.out.println("\n\n\n" + debugNum + "Application " + button.getText().toUpperCase() + " Opened");
									debugNum++;
							}
					}
				}
			} else if(arg0.isButtonMinusHeld() && !inAnotherApp) {
				System.exit(0);				
			} else {
				if(arg0.isButtonHomeJustPressed()) {
					inAnotherApp = false;
					
					setVisible(true);
					apps.get("statsApp").setVisible(false);
						remote.removeWiiMoteEventListeners(((WiimoteStatistics) apps.get("statsApp")).getWiimoteListener());
					apps.get("flappyApp").setVisible(false);
						((FlappyBird) apps.get("flappyApp")).stop(); 
						remote.removeWiiMoteEventListeners(((FlappyBird) apps.get("flappyApp")).getWiimoteListener());
					apps.get("paintApp").setVisible(false);
						remote.removeWiiMoteEventListeners(((WiiPaint) apps.get("paintApp")).getWiimoteListener());

					((DoodleJump) apps.get("doodleApp")).uponApplicationPaused();
						remote.removeWiiMoteEventListeners(((DoodleJump) apps.get("doodleApp")).getWiimoteListener());
				}
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
			pointer.move(getWidth(), getHeight(), orient.getRoll(), orient.getPitch());
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
	
	WiiHome(Wiimote wiimote) {
		//INITIALIZE
		super("Wii Home");
		//wiimote
		remote = wiimote;		
		//layered pane
		layeredPane = getLayeredPane();
			layeredPane.setPreferredSize(new Dimension(getWidth(), getHeight()));
		//pointer component
		pointer = new PointerComponent(getWidth(), getHeight());
		//pointer listener
		pointerListener = new PointerListener();
		//new apps
		apps = new TreeMap<String, JFrame>();		
		//buttons
		buttons = new ArrayList<JButton>();
		
		//CUSTOMIZE
		//customize JFrame
		setSize(800, 600);
		setLocationRelativeTo(null);
		setResizable(false);
		//pointer component
		remote.addWiiMoteEventListeners(pointerListener);
		//new apps
		WiimoteStatistics statsApp = new WiimoteStatistics(remote);
			apps.put("statsApp", statsApp);
		FlappyBird flappyApp = new FlappyBird(remote);
			apps.put("flappyApp", flappyApp);
		WiiPaint paintApp = new WiiPaint(remote);
			apps.put("paintApp", paintApp);
		DoodleJump doodleApp = new DoodleJump(remote, getWidth(), getHeight());
			apps.put("doodleApp", doodleApp);
		//buttons
		JButton statsButton = new JButton("Wiimote Statistics");
			statsButton.setBackground(Color.gray);
			statsButton.setHorizontalAlignment(SwingUtilities.CENTER);
			buttons.add(statsButton);
			statsButton.setBounds(0, 0, getWidth()/2, getHeight()/2);
			statsButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evnt) {
					setVisible(false);					
					WiimoteStatistics statsApp = (WiimoteStatistics) apps.get("statsApp");
					statsApp.setVisible(true);
					remote.addWiiMoteEventListeners(statsApp.getWiimoteListener());
				}
			});
			layeredPane.add(statsButton, new Integer(0));
		JButton flappyButton = new JButton("Flappy Bird");
			flappyButton.setBackground(Color.GREEN);
			flappyButton.setHorizontalAlignment(SwingUtilities.CENTER);
			buttons.add(flappyButton);
			flappyButton.setBounds(getWidth()/2, 0, getWidth()/2, getHeight()/2);
			flappyButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evnt) {
					setVisible(false);					
					FlappyBird flappyApp = (FlappyBird) apps.get("flappyApp");
					
					if(flappyApp.canResume()) flappyApp.resume();
					else flappyApp.restart();
					flappyApp.setVisible(true);
					remote.addWiiMoteEventListeners(flappyApp.getWiimoteListener());
				}
			});
			layeredPane.add(flappyButton, new Integer(0));
		JButton paintButton = new JButton("Wii Paint");
			paintButton.setBackground(Color.WHITE);
			paintButton.setHorizontalAlignment(SwingUtilities.CENTER);
			buttons.add(paintButton);
			paintButton.setBounds(0, getHeight()/2, getWidth()/2, getHeight()/2);
			paintButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evnt) {
					setVisible(false);
					WiiPaint paintApp = (WiiPaint) apps.get("paintApp");
					paintApp.setVisible(true);
					remote.addWiiMoteEventListeners(paintApp.getWiimoteListener());
				}
			});
			layeredPane.add(paintButton, new Integer(0));
		JButton doodleButton = new JButton("Doodle Jump");
			doodleButton.setBackground(Color.green);
			doodleButton.setHorizontalAlignment(SwingUtilities.CENTER);
			buttons.add(doodleButton);
			doodleButton.setBounds(getWidth()/2, getHeight()/2, getWidth()/2, getHeight()/2);
			doodleButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evnt) {
					setVisible(false);
					DoodleJump doodleApp = (DoodleJump) apps.get("doodleApp");
					doodleApp.setVisible(true);
					remote.addWiiMoteEventListeners(doodleApp.getWiimoteListener());
					if(doodleApp.isGameOver()) doodleApp.reset();
				}
			});
			layeredPane.add(doodleButton, new Integer(0));
		//pointer
		pointer.setBounds((int) 0, (int) 0, (int) getWidth(), (int) getHeight());
			layeredPane.add(pointer, new Integer(1));
	}
	
	public void run() {
		setVisible(true);
	}
}
